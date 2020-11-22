<template>
  <div class="table">
    <div class="crums">
      <i class="el-icon-tickets">{{ singername}}的·个人歌曲信息</i>
    </div>
    <el-input
      v-model="searchnow"
      size="mini"
      class="hand-input"
      placeholder="请输入歌曲名"
    ></el-input>
    <el-button
      type="primary"
      size="mini"
      @click="dialogVisible = true"
    >添加歌曲</el-button>
    <el-button
      type="primary"
      size="mini"
      @click="delAll"
    >批量删除</el-button>
    <el-table
      :data="data"
      @selection-change="selectionChange"
      style="width: 100%;padding-left:10px;"
      :row-class-name="tableRowClassName"
    >
      <el-table-column
        type="selection"
        width="40"
      ></el-table-column>
      <el-table-column
        label="歌曲图片"
        width="110"
        align='center'
      >
        <template slot-scope="scope">
          <div class="singer-img">
            <img
              :src="getUrl(scope.row.pic)"
              style="width:100%"
            />
          </div>
          <div
            class="play"
            v-if="toggle !=scope.row.name"
          >
            <p class="icon">
              <i class="el-icon-caret-right"></i>
            </p>
          </div>
          <div
            class="play"
            v-if="toggle !=scope.row.name"
          >
            <p class="icon">
              <i class="el-icon-caret-right"></i>
            </p>
          </div>
        </template>
      </el-table-column>

      <el-table-column
        prop="name"
        label="歌曲名"
        width="180"
      >
      </el-table-column>
      <el-table-column
        label="专辑"
        prop="introduction"
        width="150"
      >
      </el-table-column>
      <el-table-column
        label="歌词"
        width="300"
        align="center"
      >
        <template slot-scope="scope">
          <ul style="height:100px;overflow:scroll;">
            <li
              style="list-style:none"
              v-for="(v,index) in parselyric(scope.row.lyric)"
              :key="index"
            >
              {{v}}
            </li>
          </ul>
        </template>
      </el-table-column>
      <el-table-column
        label="资源跟新"
        width="110"
        align='center'
      > <template slot-scope="scope">
          <el-upload
            :action="uploadUrl(scope.row.id)"
            :before-upload="beforeUpload"
            :on-success="successupload"
          >
            <el-button size="mini">更新图片</el-button>
          </el-upload>
          <el-upload
            :action="uploadSongUrl(scope.row.id)"
            :before-upload="beforSongUpload"
            :on-success="successSongupload"
          >
            <el-button size="mini">更新歌曲</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handEdit(scope.row)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handdel(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="pagenation">
      <el-pagination
        background
        layout="total,prev, pager, next"
        :total="listSinger.length"
        :page-size="pagesize"
        :current-page="currentPage"
        @current-change="currentChange"
      >
      </el-pagination>
    </div>
    <el-dialog
      title="添加歌曲"
      :visible.sync="dialogVisible"
      width="400px"
      center
    >
      <el-form
        :model="form"
        ref="form"
        laber-width="80px"
        id="tf"
      >
        <div>
          <label>歌名</label>
          <el-input
            type="text"
            name="name"
          ></el-input>
        </div>
        <div>
          <label>专辑</label>
          <el-input
            type="text"
            name="introduction"
          ></el-input>
        </div>
        <div>
          <label>歌词</label>
          <el-input
            type="textarea"
            name="lyric"
          ></el-input>
        </div>
        <div>
          <label>歌曲文件</label>
          <input
            type="file"
            name="file"
          />
        </div>
      </el-form>
      <span slot="">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="submit"
        >确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="编辑歌曲"
      :visible.sync="editVisible"
      width="400px"
      center
    >
      <el-form
        :model="editform"
        ref="editform"
        laber-width="80px"
      >
        <div>
          <label>歌名</label>
          <el-input
            v-model="editform.name"
            type="text"
            name="name"
          ></el-input>
        </div>
        <div>
          <label>专辑</label>
          <el-input
            type="text"
            v-model="editform.introduction"
            name="introduction"
          ></el-input>
        </div>
        <div>
          <label>歌词</label>
          <el-input
            v-model="editform.lyric"
            type="textarea"
            name="lyric"
          ></el-input>
        </div>
      </el-form>
      <span slot="">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="editsubmit"
        >确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      :visible.sync="delVisiable"
      width="400px"
      center
    >
      <p>这是一段内容这是一段内容确定删除吗？</p>
      <span slot="">
        <el-button @click="delVisiable = false">取 消</el-button>
        <el-button
          type="primary"
          @click="delsubmit"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
// import '@/assets/js/iconfont.js'
import { songSingerid, EditSong, delSong } from '../api/index'
import { mixin } from '../mixs/index'
export default {
  mixins: [mixin],
  name: 'song',
  data () {
    return {
      singerid: '',
      singername: '',
      toggle: false, // 播放器图标显示状态
      editVisible: false,
      dialogVisible: false,
      delVisiable: false,
      multipSelection: [], // 哪些项被选择
      listSinger: [], // 全部歌曲
      searchsinger: [], // 所搜歌曲
      searchnow: '', // 当前输入歌曲名
      pagesize: 4, // 分页每页大小
      currentPage: 1, // 当前页
      idx: -1,
      form: { // 添加框
        name: '', introduction: '', url: '', lyric: ''
      },
      editform: { // 编辑框
        id: '', name: '', introduction: '', url: '', lyric: ''
      }
    }
  },
  computed: {

    // 分页展示
    data () {
      return this.listSinger.slice((this.currentPage - 1) * this.pagesize, this.currentPage * this.pagesize)
    },
    ...mapGetters([
      'id', 'url', 'isPlay'
    ]
    )
  },
  created () {
    this.singerid = this.$route.query.id
    this.singername = this.$route.query.name
    this.SingerAll()
  },
  watch: {
    // 搜索框内容发生变化时，列表值跟着变化
    searchnow: function () {
      if (this.searchnow === '') {
        this.listSinger = this.searchsinger
      } else {
        this.listSinger = []
        for (let item of this.searchsinger) {
          if (item.name.includes(this.searchnow)) {
            this.listSinger.push(item)
          }
        }
      }
    }
  },
  methods: {
    // 获取当前页
    currentChange (val) {
      this.currentPage = val
    },
    // 查询所有歌曲
    SingerAll () {
      this.listSinger = []
      this.searchsinger = []
      songSingerid(this.singerid).then(res => {
        this.listSinger = res
        console.log(res)
        this.searchsinger = res
        this.currentPage = 1
      })
    },
    // 添加歌曲
    submit () {
      let that = this
      var addform = new FormData(document.getElementById('tf'))
      addform.append('singerId', this.singerid)
      addform.set('name', this.singername + '-' + addform.get('name'))
      console.log(this.singername)
      if (!addform.get('lyric')) {
        addform.set('lyric', '[00:00:00]暂无歌词')
      }
      var req = new XMLHttpRequest()
      req.onreadystatechange = function () {
        // 获取完整数据req.readyState === 4
        // req.status === 200和后台交互完成
        if (req.readyState === 4 && req.status === 200) {
          let res = JSON.parse(req.response)
          if (res.code) {
            that.SingerAll()
            that.form = []
            that.notify('保存成功', 'success')
          } else {
            that.notify('保存失败', 'error')
          }
        }
      }
      req.open('post', `${that.$store.state.HOST}/song/add`, false)
      req.send(addform)
      that.dialogVisible = false
    },
    // 编辑页面
    editsubmit () {
      let params = new URLSearchParams()
      params.append('id', this.editform.id)
      params.append('name', this.editform.name)
      params.append('lyric', this.editform.lyric)
      params.append('introduction', this.editform.introduction)
      EditSong(params).then(res => {
        if (res.code === 1) {
          this.SingerAll()
          this.notify('修改成功', 'success')
        } else {
          this.notify('修改失败', 'error')
        }
      }).catch(err => {
        console.log(err)
      })
      this.editVisible = false
    },
    // 单击获取歌曲信息保存到editform
    handEdit (val) {
      console.log(val)
      this.editVisible = true
      this.editform = {
        id: val.id, name: val.name, introduction: val.introduction, lyric: val.lyric
      }
      console.log(val)
    },
    tableRowClassName ({ row, rowIndex }) {
      if (rowIndex === 1) {
        return 'warning-row'
      } else if (rowIndex === 3) {
        return 'success-row'
      }
      return ''
    },
    // 上传
    uploadUrl (id) {
      return `${this.$store.state.HOST}/song/updateSongPic?id=${id}`
    },
    // 请求后台删除歌曲信息
    delsubmit () {
      delSong(this.idx).then(res => {
        if (res) {
          this.SingerAll()
          this.notify('删除成功', 'success')
        } else {
          this.notify('删除失败', 'error')
        }
      }).catch(err => {
        console.log(err)
      })
      this.delVisiable = false
    },
    // 解析歌词
    parselyric (text) {
      let lines = text.split('\n')
      let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g
      let result = []
      for (let item of lines) {
        let value = item.replace(pattern, '')
        result.push(value)
      }
      return result
    },
    // 上传歌曲之前进行判断
    beforSongUpload (file) {
      const img = file.name.substring(file.name.lastIndexOf('.') + 1)
      const last = (img === 'mp3')
      if (!last) {
        this.$message.error('上传图片只能是MP3格式')
        return false
      }
      return true
    },
    // 上传成功之后
    successSongupload (res, file) {
      let that = this
      console.log(res)
      if (res.code === 1) {
        that.SingerAll()
        that.$notify({
          title: '上传成功',
          type: 'success'
        })
      } else {
        that.$notify({
          title: '上传失败',
          type: 'error'
        })
      }
    },
    // 歌曲歌词跟新
    uploadSongUrl (id) {
      return `${this.$store.state.HOST}/song/updateSongUrl?id=${id}`
    },
    setSongUrl (url, name) {
      this.toggle = name
      this.$store.commit('setUrl', this.$store.state.HOST + url)
      if (this.isPlay) {
        this.$store.commit('setIsPlay', false)
      } else {
        this.$store.commit('setIsPlay', true)
      }
    }
  }
}

</script>
<style scoped>
.table {
  min-width: 680px;
  margin: 10px;
}
.el-table {
  margin-top: 10px;
}
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
.singer-img {
  width: 100%;
  height: 80px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}
.hand-input {
  width: 300px;
  display: inline-block;
}
.pagenation {
  text-align: center;
  margin: auto;
}
.play {
  position: absolute;
  z-index: 999;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  top: 40px;
  left: 15px;
}
.icon {
  width: 2em;
  height: 2em;
  color: rgb(134, 25, 25);
  fill: currentColor;
  overflow: hidden;
}
.el-icon-caret-right {
  width: 2em;
  height: 2em;
}
</style>
