<template>
  <div class="header">
    <div
      class="collapse-btn"
      @click="collapseChage()"
    >
      <i class="el-icon-menu"></i>
    </div>
    <div class="logo">music后台管理
      <div class="header-right">
        <div
          class="btn-full"
          @click="handFull"
        >
          <el-tooltip
            :content="fullscree?'取消全屏':'全屏'"
            placement="bottom"
          >
            <i class="el-icon-rank"></i>
          </el-tooltip>
        </div>
        <div class="user-avator">
          <img src="../assets/img/lucky.jpg" />
        </div>
        <el-dropdown
          @command="handleCommand"
          trigger="click"
        >
          <span class="el-dropdown-link">
            {{username}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="loginout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

      </div>
    </div>
  </div>
</template>
<script>
import bus from '../assets/js/bus'
export default {
  name: 'TheHead',
  data () {
    return {
      collapse: false,
      fullscree: false
    }
  },
  methods: {
    // 侧边栏事件
    collapseChage () {
      this.collapse = !this.collapse
      bus.$emit('collapse', this.collapse)
    },
    // 全屏事件
    handFull () {
      if (this.fullscree) {
        if (document.exitFullscreen) {
          document.exitFullscreen()
        } else if (document.webkitCanceFullScree) { // safari、chrome
          document.webkitCanceFullScree()
        } else if (document.mozCanceFullScree) { // firefox
          document.mozCanceFullScree()
        } else if (document.msExitFullScree) { // ie
          document.msExitFullScree()
        }
      } else {
        let elment = document.documentElement
        if (elment.requestFullscree) {
          elment.requestFullscree()
        } else if (elment.webkitCanceFullScree) {
          elment.webkitCanceFullScree()
        } else if (elment.mozCanceFullScree) {
          elment.mozCanceFullScree()
        } else if (elment.msExitFullScree) {
          elment.msExitFullScree()
        }
      }
      this.fullscree = !this.fullscree
    },
    handleCommand (command) {
      if (command === 'loginout') {
        localStorage.removeItem('username')
        this.$router.push('/')
      }
    }
  },
  computed: {
    username () {
      return localStorage.getItem('username')
    }
  }
}
</script>
<style scoped>
.header {
  position: relative;
  background-color: #253041;
  box-sizing: border-box;
  height: 70px;
  font-size: 22px;
  color: #ffffff;
  margin-left: -8px;
  margin-right: -8px;
}
.collapse-btn {
  float: left;
  padding: 0 21px;
  cursor: pointer;
  line-height: 70px;
}
.header .logo {
  position: relative;
  line-height: 70px;
}
.header-right {
  float: right;
  padding-right: 50px;
  display: flex;
  height: 70px;
  align-items: center;
}
.btn-full {
  margin-right: 5px;
  font-size: 22px;
}
.user-avator img {
  margin-left: 20px;
  margin-right: 20px;
  width: 40px;
  height: 40px;
  display: block;
  border-radius: 50%;
}
</style>
