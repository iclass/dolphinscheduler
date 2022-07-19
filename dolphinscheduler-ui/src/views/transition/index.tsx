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
const transition = defineComponent({
  name: 'transition',
  setup() {
    const router: Router = useRouter()
    const userStore = useUserStore()
      const userInfoRes = ref()
      // const routeStore = useRouteStore()
    const initData = async () => {
      console.log('执行')
      userInfoRes.value = await getUserInfo()
            // userInfoRes.value = {"id":1,"userName":"admin","userPassword":"7ad2410b2f4c074479a8937a28a22b8f","email":"xxx@qq.com","phone":"","userType":"ADMIN_USER","tenantId":0,"state":1,"tenantCode":null,"queueName":null,"alertGroup":null,"queue":null,"timeZone":"Asia/Shanghai","createTime":"2018-03-28 04:48:50","updateTime":"2018-10-25 06:40:22","personId":1}
      console.log('35',userInfoRes.value)
      await userStore.setUserInfo(userInfoRes.value)
      // const path = routeStore.lastRoute

      router.push({ path: 'home' })
    }
    watch(() => router.currentRoute.value.path,async () => {
      //要执行的方法
      
      if(router.currentRoute.value.query.jsessionid){
        userStore.setSessionId(String(router.currentRoute.value.query.jsessionid))
        
      }
      console.log(router.currentRoute.value)
   },{immediate: true,deep: true})
    onMounted(() => {
      console.log('执行1')
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

export default transition
