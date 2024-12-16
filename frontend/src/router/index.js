import { createRouter, createWebHistory } from 'vue-router'
import ChargingStatus from '../views/chargingwebsocket/ChargingStatus.vue'
import PayPrice from '@/views/payment/PayPrice.vue'
import TestAlarm from '@/views/alarm/TestAlarm.vue'
import ReservationAlarm from '@/views/alarm/ReservationAlarm.vue'

import { useAuthStore } from '@/stores/auth'

import ChargerChangeState from '@/views/chargingwebsocket/ChargerChangeState.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/myinfo/dashboard/:id' },
    {
      path: '/myinfo/',
      component: () => import('../layouts/default.vue'),
      props: true,
      meta: { requiresAuth: true, requiresMember: true },
      children: [
        {
          path: 'dashboard/:id',
          component: () => import('../pages/dashboard.vue'),
          props: true,
          meta: { requiresAuth: true, requiresMember: true },
        },
        {
          path: 'account-settings/:id',
          component: () => import('../pages/account-settings.vue'),
          props: true,
          meta: { requiresAuth: true, requiresMember: true },
        },
        { 
          path: 'my-reservation/:id',
          component: () => import('../pages/my-reservation.vue'),
          props: true,
          meta: { requiresAuth: true, requiresMember: true },
        },
        {
          path: 'my-payment/:id',
          component: () => import('../pages/my-payment.vue'),
          props: true,
          meta: { requiresAuth: true, requiresMember: true },
        },
      ],
    },
    {
      path: '/login',
      name: 'Login',
      component : () => import('../views/pages/user-account/Login.vue'),
    },
    {
      path: '/agreement',
      neme: 'Agreement',
      component : () => import('../views/pages/user-account/Agreement.vue'),
    },
    {
      path: '/signup',
      neme: 'Signup',
      component : () => import('../views/pages/user-account/Signup.vue'),
    },
    {
      path: '/find-id',
      neme: 'FindByUserId',
      component : () => import('../views/pages/user-account/FindByUserId.vue'),
    },
    {
      path:'/chargingStatus',
      name: 'ChargingStatus',
      component : ChargingStatus
    },
    { 
      path:'/payPrice',
      name : PayPrice,
      component : PayPrice
    },
    {
      path:'/testalarm',
      name: 'TestAlarm',
      component:TestAlarm
    },
    {
      path:'/reservationalarm',
      name:'ReservationAlarm',
      component:ReservationAlarm
    },
    {
      path:'/chargerstate',
      name:'chargerstate',
      component:ChargerChangeState
    },
    {
      path: '/',
      component: () => import('../layouts/blank.vue'),
      children: [
        //에러페이지
        {
          path: '/:pathMatch(.*)*',
          component: () => import('../pages/[...all].vue'),
        },
      ],
    },
    {
      path:'/Reservation',
      name: 'Reservation',
      component: () => import('../views/payment/Reservation.vue'),
    },
    {
      path: '/find-result',
      neme: 'FindIDResult',
      component : () => import('../views/pages/user-account/FindIDResult.vue'),
    },
    {
      path: '/find-password',
      neme: 'FindPassword',
      component : () => import('../views/pages/user-account/FindPassword.vue'),
    },
  ],
})

  // 라우터 가드 설정
  router.beforeEach((to, from, next) => {
    const authStore = useAuthStore()
    if (to.matched.some(record => record.meta.requiresAuth)) {
      if (!authStore.accessToken) {
        next({ name: 'Login' })
      } else if (to.matched.some(record => record.meta.requiresMember) && authStore.user.role !== 'MEMBER') {
        // 유저 확인
        next({ path: '/dashboard:id' })
      } else {
        next()
      }
    } else {
      next()
    }
  })


export default router
