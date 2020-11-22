<template>
  <div class="sidebar">
    <el-menu
      class="sidebar-el-menu"
      :default-active="onRoutes"
      :collapse="collapse"
      background-color="#334256"
      text-color="#ffffff"
      active-text-color="#20a0ff"
      router
    >
      <template v-for="v in item">
        <template>
          <el-menu-item
            :index="v.index"
            :key="v.index"
          >
            <i :class="v.icon"></i>
            {{v.title}}
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>
<script>
import bus from '../assets/js/bus'
export default {
  name: 'TheAside',
  data () {
    return {
      collapse: false,
      item: [{
        icon: 'el-icon-tickets',
        index: 'Info',
        title: '系统首页'
      }, {
        icon: 'el-icon-tickets',
        index: 'user',
        title: '用户管理'
      }, {
        icon: 'el-icon-tickets',
        index: 'songPage',
        title: '歌单管理'
      }, {
        icon: 'el-icon-tickets',
        index: 'singer',
        title: '歌手管理'
      }]
    }
  },
  computed: {
    onRoutes () {
      return this.$route.path.replace('/', '')
    }
  },
  created () {
    // 通过BUS进行组件之间的传信，来折叠侧边栏
    bus.$on('collapse', msg => {
      this.collapse = msg
    })
  }
}
</script>
<style scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 78px;
  bottom: 0px;
  background-color: #334256;
  overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
  width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
  width: 150px;
}
</style>
