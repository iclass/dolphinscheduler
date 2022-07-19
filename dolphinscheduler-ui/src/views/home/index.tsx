/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { defineComponent, inject, onMounted, ref, toRefs, watch } from 'vue'
import { NGrid, NGi } from 'naive-ui'
import { startOfToday, getTime } from 'date-fns'
import { useI18n } from 'vue-i18n'
import { useTaskState } from './use-task-state'
import { useProcessState } from './use-process-state'
import StateCard from './components/state-card'
import DefinitionCard from './components/definition-card'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user/user'
import { getUserInfo } from '@/service/modules/users'
import { UserInfoRes } from '@/service/modules/users/types'

export default defineComponent({
  name: 'home',
  setup() {
    const { t, locale } = useI18n()
    const dateRef = ref([getTime(startOfToday()), Date.now()])
    const taskStateRef = ref()
    const processStateRef = ref()
    const router = useRouter()
    const onRefresh = inject<Function>('reload')
    const userInfoRes = ref()
    const userStore = useUserStore()
    const { getTaskState, taskVariables } = useTaskState()
    const { getProcessState, processVariables } = useProcessState()

    const initData = async () => {
      if(localStorage.getItem('user')){
        if (location.href.indexOf("#reloaded") === -1) {
          location.href = location.href + "#reloaded";
          location.reload();
      }
      }
      taskStateRef.value = getTaskState(dateRef.value)
      processStateRef.value = getProcessState(dateRef.value)


    }

    const handleTaskDate = (val: any) => {
      taskStateRef.value = getTaskState(val)
    }

    const handleProcessDate = (val: any) => {
      processStateRef.value = getProcessState(val)
    }

    onMounted(() => {
      initData()
    })
    watch(() => router.currentRoute.value.path,async (toPath) => {
      //要执行的方法
      
      if(router.currentRoute.value.query.jsessionid){
        userStore.setSessionId(String(router.currentRoute.value.query.jsessionid))
        userInfoRes.value = await getUserInfo()
        await userStore.setUserInfo(userInfoRes)
      }
      console.log(router.currentRoute.value)
   },{immediate: true,deep: true})
    watch(
      () => locale.value,
      () => initData(), 
    )

    return {
      t,
      dateRef,
      onRefresh,
      handleTaskDate,
      handleProcessDate,
      taskStateRef,
      processStateRef,
      ...toRefs(taskVariables),
      ...toRefs(processVariables)
    }
  },
  render() {
    const {
      t,
      dateRef,
      handleTaskDate,
      handleProcessDate,
      taskLoadingRef,
      processLoadingRef
    } = this

    return (
      <div>
        <NGrid x-gap={12} cols={2}>
          <NGi>
            <StateCard
              title={t('home.task_state_statistics')}
              date={dateRef}
              tableData={this.taskStateRef?.value.table}
              chartData={this.taskStateRef?.value.chart}
              onUpdateDatePickerValue={handleTaskDate}
              loadingRef={taskLoadingRef}
            />
          </NGi>
          <NGi>
            <StateCard
              title={t('home.process_state_statistics')}
              date={dateRef}
              tableData={this.processStateRef?.value.table}
              chartData={this.processStateRef?.value.chart}
              onUpdateDatePickerValue={handleProcessDate}
              loadingRef={processLoadingRef}
            />
          </NGi>
        </NGrid>
        <NGrid cols={1} style='margin-top: 12px;'>
          <NGi>
            <DefinitionCard title={t('home.process_definition_statistics')} />
          </NGi>
        </NGrid>
      </div>
    )
  }
})

