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

import { defineComponent, onMounted, ref, watch } from 'vue'
import { useUserStore } from '@/store/user/user'
import { getUserInfo } from '@/service/modules/users'
import { useRouter } from 'vue-router'
import type { Router } from 'vue-router'
import { useRouteStore } from '@/store/route/route'
import { useMessage } from 'naive-ui'
const forError = defineComponent({
  name: 'forError',
  setup() {
    const router: Router = useRouter()
    const userStore = useUserStore()
      const userInfoRes = ref()
      const message = useMessage()
      // const routeStore = useRouteStore()
    const initData = async () => {
        message.error('用户已被停用,不能使用ETL工具')
        setTimeout(()=>{
          router.push({ path: 'login' })

        },3000)
    }

    onMounted(() => {
      initData()
    })


    return { initData }
  },
  render() {
    return (
      <div>
      </div>
    )
  
  }
})

export default forError
