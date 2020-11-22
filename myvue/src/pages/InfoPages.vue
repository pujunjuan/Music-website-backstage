<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <div>{{usercount}}</div>
          <div>总用户</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <div>{{songcount}}</div>
          <div>歌曲总数</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <div>{{singercount}}</div>
          <div>歌手数量</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <div>{{songListcount}}</div>
          <div>歌单数量</div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <h3>用户男女比例</h3>
        <div style="background-color:white">
          <ve-pie
            :data="userSex"
            :settings="chartSettings"
          ></ve-pie>
        </div>
      </el-col>
      <el-col :span="12">
        <h3>歌曲风格分布</h3>
        <div style="background-color:white">
          <ve-histogram
            :data="chartsongStyle"
            :settings="chartSettings"
          ></ve-histogram>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12">
        <h3>歌手性别比例</h3>
        <div style="background-color:white">
          <ve-pie
            :data="SingerSex"
            :settings="chartSettings"
          ></ve-pie>
        </div>
      </el-col>
      <el-col :span="12">
        <h3>歌手国籍分布</h3>
        <div style="background-color:white">
          <ve-histogram
            :data="singercountry"
            :settings="chartSettings"
          ></ve-histogram>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
//
import { UserListAll, SingerAll, songListAll, songAll } from '../api/index'
export default {
  data () {
    return {
      usercount: 0, // 用户数量
      user: [], // 所有用户
      singercount: 0, // 歌手数量
      singer: [], // 所有歌手
      songcount: 0, // 歌曲数量
      song: [], // 所有歌曲
      songListcount: 0, // 歌单数量
      songList: [], // 所有歌单
      userSex: {// 性别分类
        columns: ['性别', '总数'],
        rows: [{
          '性别': '男',
          '总数': 0
        }, {
          '性别': '女',
          '总数': 0
        }]
      },
      SingerSex: {// 性别分类
        columns: ['性别', '总数'],
        rows: [{
          '性别': '男',
          '总数': 0
        }, {
          '性别': '女',
          '总数': 0
        }, {
          '性别': '组合',
          '总数': 0
        }, {
          '性别': '不明',
          '总数': 0
        }]
      },
      chartsongStyle: {// 歌单风格分类
        columns: ['风格', '总数'],
        rows: [
          { '风格': '华语', '总数': 0 },
          { '风格': '粤语', '总数': 0 },
          { '风格': '欧美', '总数': 0 },
          { '风格': '日韩', '总数': 0 },
          { '风格': 'BGM', '总数': 0 },
          { '风格': '轻音乐', '总数': 0 },
          { '风格': '乐器', '总数': 0 }
        ]
      },
      singercountry: {// 歌单风格分类
        columns: ['国籍', '总数'],
        rows: [
          { '国籍': '中国', '总数': 0 },
          { '国籍': '韩国', '总数': 0 },
          { '国籍': '日本', '总数': 0 },
          { '国籍': '美国', '总数': 0 },
          { '国籍': '新加波', '总数': 0 },
          { '国籍': '意大利', '总数': 0 },
          { '国籍': '马来西亚', '总数': 0 }
        ]
      }
    }
  },
  create () {
    this.Infouser()
  },
  mounted () {
    this.Infouser()
  },
  methods: {
    Infouser () {
      UserListAll().then(res => {
        this.user = res
        this.usercount = res.length
        this.userSex.rows[0]['总数'] = this.setSex(1, this.user)
        this.userSex.rows[1]['总数'] = this.setSex(0, this.user)
      })
      SingerAll().then(res => {
        this.singer = res
        this.singercount = res.length
        this.SingerSex.rows[0]['总数'] = this.setSingerSex(1, this.singer)
        this.SingerSex.rows[1]['总数'] = this.setSingerSex(0, this.singer)
        this.SingerSex.rows[2]['总数'] = this.setSingerSex(3, this.singer)
        this.SingerSex.rows[3]['总数'] = this.setSingerSex(4, this.singer)
        for (let item of res) {
          this.country(item.location)
        }
      })
      songListAll().then(res => {
        this.ussongList = res
        this.songListcount = res.length
        for (let item of res) {
          this.setStyle(item.style)
        }
      })
      songAll().then(res => {
        this.song = res
        this.songcount = res.length
      })
    },
    setSex (sex, consumer) { // 性别获取
      let count = 0
      for (let item of consumer) {
        if (sex === item.sex) {
          count++
        }
      }
      return count
    },
    setSingerSex (sex, consumer) { // 性别获取
      let count = 0
      for (let item of consumer) {
        if (sex === item.sex) {
          count++
        }
      }
      return count
    },
    country (location) { // 国籍分布
      let count = 0
      for (let item of this.singercountry.rows) {
        if (location.includes(item['国籍'])) {
          item['总数']++
        }
      }
      return count
    },
    setStyle (style) { // 风格获取数量
      let count = 0
      for (let item of this.chartsongStyle.rows) {
        if (style.includes(item['风格'])) {
          item['总数']++
        }
      }
      return count
    }
  }
}
</script>
<style scoped>
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.bg-purple {
  background: #d3dce6;
}

.grid-content {
  border-radius: 4px;
  text-align: center;
  line-height: 3em;
  min-height: 100px;
  font-size: 16px;
  font-weight: bold;
  color: white;
}
.ve-pie {
  height: 350px;
}
</style>
