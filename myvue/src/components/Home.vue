<template>
  <div>
    <the-head></the-head>
    <the-aside></the-aside>
    <div class="content-box">
      <router-view></router-view>
    </div>

  </div>
</template>
<script>
import TheHead from './TheHead'
import TheAside from './TheAside'
import bus from '../assets/js/bus'
export default {
  name: 'Home',
  data () {
    return {
      collapse: false
    }
  },
  components: { TheHead, TheAside },
  created () {
    // 通过BUS进行组件之间的传信，来折叠侧边栏
    bus.$on('collapse', msg => {
      this.collapse = msg
    })
  }
}
</script>
<style scoped>
.content-box {
  position: absolute;
  left: 150px;
  right: 0;
  top: 78px;
  bottom: 0;
  overflow-y: scroll;
  transition: left 0.3s ease-in-out;
  background-color: #f0f0f0;
}
</style>
