<template>
  <div class="table">
    <div class="crums">
      <i class="el-icon-tickets">歌单歌曲信息</i>
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
      :data="listsongList"
      @selection-change="selectionChange"
      style="width: 100%;padding-left:10px;"
      :row-class-name="tableRowClassName"
    >
      <el-table-column
        type="selection"
        width="50"
      ></el-table-column>
      <el-table-column
        prop="name"
        label="歌手-歌名"
        align="center"
        width="880"
      >
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            @click="handdel(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
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
      >
        <el-form-item
          label="歌手名字"
          prop="singername"
        >
          <el-input
            v-model="form.singername"
            aria-placeholder="歌手名字"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="歌曲名字"
          prop="songName"
        >
          <el-input
            v-model="form.songName"
            aria-placeholder="歌曲名字"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="getSongId"
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
import { ListSong, delListSong, songId, songName, AddListSong } from '../api/index'
import { mixin } from '../mixs/index'
export default {
  mixins: [mixin],
  name: 'song',
  data () {
    return {
      songList: '',
      dialogVisible: false,
      delVisiable: false,
      multipSelection: [], // 哪些项被选择
      listsongList: [], // 全部歌曲
      searchsongList: [], // 所搜歌曲
      searchnow: '', // 当前输入歌曲名
      idx: -1,
      form: { // 添加框
        singername: '', songName: ''
      }
    }
  },
  created () {
    this.songList = this.$route.query.id
    this.songListAll()
  },
  watch: {
    // 搜索框内容发生变化时，列表值跟着变化
    searchnow: function () {
      if (this.searchnow === '') {
        this.listsongList = this.searchsongList
      } else {
        this.listsongList = []
        for (let item of this.searchsongList) {
          if (item.name.includes(this.searchnow)) {
            this.listsongList.push(item)
          }
        }
      }
    }
  },
  methods: {
    getSong (id) {
      this.listsongList = []
      this.searchsongList = []
      songId(id).then(res => {
        this.listsongList.push(res)
        this.searchsongList.push(res)
        console.log(this.listsongList)
      }).catch(err => {
        console.log(err)
      })
    },
    // 查询所有歌曲
    songListAll () {
      ListSong(this.songList).then(res => {
        console.log(res)
        for (let item of res) {
          this.getSong(item.songId)
        }
      })
    },
    getSongId () {
      let that = this
      var songsName = that.form.singername + '-' + that.form.songName
      songName(songsName).then(res => {
        console.log(res)
        that.submit(res[0].id)
      })
    },
    // 添加歌曲id
    submit (id) {
      let params = new URLSearchParams()
      params.append('songId', id)
      params.append('songListId', this.songList)
      AddListSong(params).then(res => {
        if (res.code === 1) {
          this.songListAll()
          this.notify('添加成功', 'success')
        } else {
          this.notify('添加失败', 'error')
        }
      }).catch(err => {
        console.log(err)
      })
      this.dialogVisible = false
    },

    tableRowClassName ({ row, rowIndex }) {
      if (rowIndex === 1) {
        return 'warning-row'
      } else if (rowIndex === 3) {
        return 'success-row'
      }
      return ''
    },
    // 请求后台删除歌曲信息
    delsubmit () {
      var that = this
      delListSong(this.idx, this.songList).then(res => {
        if (res) {
          that.songListAll()
          this.notify('删除成功', 'success')
        } else {
          this.notify('删除失败', 'error')
        }
      }).catch(err => {
        console.log(err)
      })
      this.delVisiable = false
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
.songList-img {
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
