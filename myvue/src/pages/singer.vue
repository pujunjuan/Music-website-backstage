<template>
  <div class="table">
    <el-input
      v-model="searchnow"
      size="mini"
      class="hand-input"
      placeholder="请输入歌手名"
    ></el-input>
    <el-button
      type="primary"
      size="mini"
      @click="dialogVisible = true"
    >添加歌手</el-button>
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
        label="歌手头像"
        width="110"
        align='center'
      >
        <template slot-scope="scope">
          <div class="singer-img">
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
        prop="name"
        label="歌手姓名"
        width="180"
      >
      </el-table-column>

      <el-table-column
        label="歌手性别"
        width="180"
      >
        <template slot-scope="scope">
          {{chageSex(scope.row.sex)}}
        </template>
      </el-table-column>

      <el-table-column label="歌手生日">
        <template slot-scope="scope">
          {{getBirth(scope.row.birth)}}
        </template>
      </el-table-column>
      <el-table-column
        prop="location"
        label="地区"
      >
      </el-table-column>
      <el-table-column label="歌手简介">
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
            @click="songedit(scope.row.id,scope.row.name)"
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
        :total="listSinger.length"
        :page-size="pagesize"
        :current-page="currentPage"
        @current-change="currentChange"
      >
      </el-pagination>
    </div>
    <el-dialog
      title="添加歌手"
      :visible.sync="dialogVisible"
      width="400px"
      center
    >
      <el-form
        :model="form"
        ref="form"
        laber-width="80px"
      >
        <el-form-item label="歌手名字">
          <el-input
            v-model="form.name"
            aria-placeholder="歌手名字"
          ></el-input>
        </el-form-item>

        <el-form-item label="歌手性别">
          <el-radio-group v-model="form.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">组合</el-radio>
            <el-radio :label="3">不明</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          label="歌手生日"
          size="mini"
        >
          <el-date-picker
            v-model="form.birth"
            type="date"
            aria-placeholder="选则日期"
            style="width:100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="地区">
          <el-input
            v-model="form.location"
            aria-placeholder="地区"
          ></el-input>
        </el-form-item>
        <el-form-item label="歌手介绍">
          <el-input
            v-model="form.introduction"
            type="textarea"
            aria-placeholder="歌手介绍"
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
      title="编辑歌手"
      :visible.sync="editVisible"
      width="400px"
      center
    >
      <el-form
        :model="editform"
        ref="editform"
        laber-width="80px"
      >
        <el-form-item label="歌手名字">
          <el-input
            v-model="editform.name"
            aria-placeholder="歌手名字"
          ></el-input>
        </el-form-item>

        <el-form-item label="歌手性别">
          <el-radio-group v-model="editform.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">组合</el-radio>
            <el-radio :label="3">不明</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          label="歌手生日"
          size="mini"
        >
          <el-date-picker
            v-model="editform.birth"
            type="date"
            aria-placeholder="选则日期"
            style="width:100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="地区">
          <el-input
            v-model="editform.location"
            aria-placeholder="地区"
          ></el-input>
        </el-form-item>
        <el-form-item label="歌手介绍">
          <el-input
            v-model="editform.introduction"
            type="textarea"
            aria-placeholder="歌手介绍"
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
import { AddSinger, SingerAll, EditSinger, delSinger } from '../api/index'
import { mixin } from '../mixs/index'
export default {
  mixins: [mixin],
  name: 'singer',
  data () {
    return {
      editVisible: false,
      dialogVisible: false,
      delVisiable: false,
      multipSelection: [], // 哪些项被选择
      listSinger: [], // 全部歌手
      searchsinger: [], // 所搜歌手
      searchnow: '', // 当前输入歌手名
      pagesize: 4, // 分页每页大小
      currentPage: 1, // 当前页
      idx: -1,
      form: { // 添加框
        name: '', sex: '', birth: '', location: '', introduction: ''
      },
      editform: { // 编辑框
        id: '', name: '', sex: '', birth: '', location: '', introduction: ''
      }
    }
  },
  computed: {
    // 分页展示
    data () {
      return this.listSinger.slice((this.currentPage - 1) * this.pagesize, this.currentPage * this.pagesize)
    }
  },
  created () {
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
    // 查询所有歌手
    SingerAll () {
      this.listSinger = []
      this.searchsinger = []
      SingerAll().then(res => {
        this.listSinger = res
        this.searchsinger = res
        this.currentPage = 1
      })
    },
    // 添加歌手
    submit () {
      let d = this.form.birth
      let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
      let params = new URLSearchParams()
      console.log(datetime)
      params.append('name', this.form.name)
      params.append('sex', this.form.sex)
      params.append('pic', '/img/singerPic/hhh.jpg')
      params.append('birth', datetime)
      params.append('location', this.form.location)
      params.append('introduction', this.form.introduction)
      AddSinger(params).then(res => {
        if (res.code === 1) {
          this.SingerAll()
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
      let d = new Date(this.editform.birth)
      let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
      let params = new URLSearchParams()
      console.log(datetime)
      params.append('id', this.editform.id)
      params.append('name', this.editform.name)
      params.append('sex', this.editform.sex)
      params.append('birth', datetime)
      params.append('location', this.editform.location)
      params.append('introduction', this.editform.introduction)
      EditSinger(params).then(res => {
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
    // 单击获取歌手信息保存到editform
    handEdit (val) {
      this.editVisible = true
      this.editform = {
        id: val.id, name: val.name, sex: val.sex, birth: val.birth, location: val.location, introduction: val.introduction
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
      return `${this.$store.state.HOST}/Singer/updateSingerImg?id=${id}`
    },
    // 请求后台删除歌手信息
    delsubmit () {
      delSinger(this.idx).then(res => {
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
    // 转向歌曲管理页面
    songedit (id, name) {
      this.$router.push({ path: `/song`, query: { id, name } })
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
</style>
