<template>
  <div class="table">
    <el-input
      v-model="searchnow"
      size="mini"
      class="hand-input"
      placeholder="请输入歌单名"
    ></el-input>
    <el-button
      type="primary"
      size="mini"
      @click="dialogVisible = true"
    >添加歌单</el-button>
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
        label="歌单头像"
        width="110"
        align='center'
      >
        <template slot-scope="scope">
          <div class="songList-img">
            <img
              :src="getUrl(scope.row.pic)"
              style="width:100%"
            />
            <!-- :src="getUrl(scope.row.pic)" -->
          </div>
          <el-upload
            :action="uploadUrl(scope.row.id)"
            :before-upload="beforeUpload"
            :on-success="successupload"
          >
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column
        prop="title"
        label="歌单标题"
        width="180"
      >
      </el-table-column>

      <el-table-column
        label="歌单风格"
        width="180"
        prop="style"
      >
      </el-table-column>

      <el-table-column label="歌单简介">
        <template slot-scope="scope">
          <p style="height:100px;overflow:scroll">{{scope.row.introduction}}</p>
        </template>
      </el-table-column>
      <el-table-column
        label="歌曲管理"
        width="110"
        align="center"
      >>
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="songedit(scope.row.id)"
          >歌曲管理</el-button>
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
        :total="listsongList.length"
        :page-size="pagesize"
        :current-page="currentPage"
        @current-change="currentChange"
      >
      </el-pagination>
    </div>
    <el-dialog
      title="添加歌单"
      :visible.sync="dialogVisible"
      width="400px"
      center
    >
      <el-form
        :model="form"
        ref="form"
        laber-width="80px"
      >
        <el-form-item label="歌单标题">
          <el-input
            v-model="form.title"
            aria-placeholder="歌单标题"
          ></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-input
            v-model="form.style"
            aria-placeholder="类型"
          ></el-input>
        </el-form-item>
        <el-form-item label="歌单简介">
          <el-input
            v-model="form.introduction"
            type="textarea"
            aria-placeholder="歌单简介"
          ></el-input>
        </el-form-item>

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
      title="编辑歌单"
      :visible.sync="editVisible"
      width="400px"
      center
    >
      <el-form
        :model="editform"
        ref="editform"
        laber-width="80px"
      >
        <el-form-item label="歌单标题">
          <el-input
            v-model="editform.title"
            aria-placeholder="歌单标题"
          ></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-input
            v-model="editform.style"
            aria-placeholder="类型"
          ></el-input>
        </el-form-item>
        <el-form-item label="歌单介绍">
          <el-input
            v-model="editform.introduction"
            type="textarea"
            aria-placeholder="歌单介绍"
          ></el-input>
        </el-form-item>

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
import { AddsongList, songListAll, EditsongList, delsongList } from '../api/index'
import { mixin } from '../mixs/index'
export default {
  mixins: [mixin],
  name: 'songList',
  data () {
    return {
      editVisible: false,
      dialogVisible: false,
      delVisiable: false,
      multipSelection: [], // 哪些项被选择
      listsongList: [], // 全部歌单
      searchsongList: [], // 所搜歌单
      searchnow: '', // 当前输入歌单名
      pagesize: 4, // 分页每页大小
      currentPage: 1, // 当前页
      idx: -1,
      form: { // 添加框
        title: '', style: '', introduction: ''
      },
      editform: { // 编辑框
        id: '', title: '', style: '', introduction: ''
      }
    }
  },
  computed: {
    // 分页展示
    data () {
      return this.listsongList.slice((this.currentPage - 1) * this.pagesize, this.currentPage * this.pagesize)
    }
  },
  created () {
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
          if (item.title.includes(this.searchnow)) {
            this.listsongList.push(item)
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
    // 查询所有歌单
    songListAll () {
      this.listsongList = []
      this.searchsongList = []
      songListAll().then(res => {
        this.listsongList = res
        this.searchsongList = res
        this.currentPage = 1
      })
    },
    // 添加歌单
    submit () {
      let params = new URLSearchParams()
      params.append('title', this.form.title)
      params.append('style', this.form.style)
      params.append('introduction', this.form.introduction)
      AddsongList(params).then(res => {
        if (res.code === 1) {
          this.notify('添加成功', 'success')
        } else {
          this.notify('添加失败', 'error')
        }
      }).catch(err => {
        console.log(err)
      })
      this.dialogVisible = false
    },
    // 编辑页面
    editsubmit () {
      let params = new URLSearchParams()
      params.append('id', this.editform.id)
      params.append('title', this.editform.title)
      params.append('style', this.editform.style)
      params.append('introduction', this.editform.introduction)
      EditsongList(params).then(res => {
        if (res.code === 1) {
          this.songListAll()
          console.log(res.code)
          this.notify('修改成功', 'success')
        } else {
          this.notify('修改失败', 'error')
        }
      }).catch(err => {
        console.log(err)
      })
      this.editVisible = false
    },
    // 单击获取歌单信息保存到editform
    handEdit (val) {
      this.editVisible = true
      this.editform = {
        id: val.id, title: val.title, style: val.style, introduction: val.introduction
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
      return `${this.$store.state.HOST}/SongList/updateImg?id=${id}`
    },
    // 请求后台删除歌单信息
    delsubmit () {
      delsongList(this.idx).then(res => {
        if (res) {
          this.songListAll()
          this.notify('删除成功', 'success')
        } else {
          this.notify('删除失败', 'error')
        }
      }).catch(err => {
        console.log(err)
      })
      this.delVisiable = false
    },
    // 转向歌单管理页面id
    songedit (id) {
      this.$router.push({ path: `/songList`, query: { id } })
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
</style>
