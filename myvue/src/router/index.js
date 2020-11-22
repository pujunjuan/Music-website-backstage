import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Home from '@/components/Home'
import Info from '@/pages/InfoPages'
import user from '@/pages/User'
import singer from '@/pages/singer'
import song from '@/pages/song'
import songPage from '@/pages/songPage'
import songList from '@/pages/songList'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: HelloWorld
    }, {
      path: '/Home',
      name: 'Home',
      component: Home,
      children: [{
        path: '/Info',
        component: Info
      }, {
        path: '/user',
        component: user
      }, {
        path: '/singer',
        component: singer
      }, {
        path: '/song',
        component: song
      }, {
        path: '/songPage',
        component: songPage
      }, {
        path: '/songList',
        component: songList
      }]
    }
  ]
})
