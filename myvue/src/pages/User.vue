<template>
  <div class="table">
    <el-input
      v-model="searchnow"
      size="mini"
      class="hand-input"
      placeholder="请输入用户名"
    ></el-input>
    <el-button
      type="primary"
      size="mini"
      @click="dialogVisible = true"
    >添加用户</el-button>
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
        label="头像"
        width="110"
        align='center'
      >
        <template slot-scope="scope">
          <div class="User-img">
            <img
              :src="getUrl(scope.row.avator)"
              style="width:100%"
            />
            <!-- :src="getUrl(scope.row.pic)" -->
          </div>
          <el-upload
            :action="uploadUrl(scope.row.id)"
            :before-upload="beforeUpload"
            :on-success="successUserupload"
          >
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column
        prop="username"
        label="姓名"
        width="100"
      >
      </el-table-column>

      <el-table-column
        label="性别"
        width="50"
      >
        <template slot-scope="scope">
          {{chageSex(scope.row.sex)}}
        </template>
      </el-table-column>

      <el-table-column label="生日">
        <template slot-scope="scope">
          {{getBirth(scope.row.birth)}}
        </template>
      </el-table-column>

      <el-table-column
        prop="phoneNum"
        label="电话"
        width="100"
      >
      </el-table-column>

      <el-table-column
        prop="email"
        label="邮箱"
        width="100"
      >
      </el-table-column>

      <el-table-column
        prop="location"
        label="地区"
      >
      </el-table-column>
      <el-table-column
        prop="introduction"
        label="签名"
        width="100"
      >
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
        :total="listUser.length"
        :page-size="pagesize"
        :current-page="currentPage"
        @current-change="currentChange"
      >
      </el-pagination>
    </div>
    <el-dialog
      title="添加用户"
      :visible.sync="dialogVisible"
      width="400px"
      center
    >
      <el-form
        :model="form"
        :rules="rules"
        ref="form"
        laber-width="80px"
      >
        <el-form-item
          label="名字"
          prop="username"
        >
          <el-input
            v-model="form.username"
            aria-placeholder="名字"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="密码"
          prop="password"
        >
          <el-input
            v-model="form.passowrd"
            type="password"
            aria-placeholder="密码"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="电话"
          prop="phone"
        >
          <el-input
            v-model="form.phone"
            aria-placeholder="电话"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="电子邮箱"
          prop="email"
        >
          <el-input
            v-model="form.email"
            aria-placeholder="电子邮箱"
          ></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <input
            type="radio"
            value="0"
            name="sex"
            v-model="form.sex"
          />&nbsp;&nbsp;女&nbsp;&nbsp;
          <input
            type="radio"
            value="1"
            name="sex"
            v-model="form.sex"
          />&nbsp;&nbsp;男&nbsp;&nbsp;
        </el-form-item>
        <el-form-item
          label="生日"
          size="mini"
          prop="birth"
        >
          <el-date-picker
            v-model="form.birth"
            type="date"
            aria-placeholder="选则日期"
            style="width:100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item
          label="地区"
          prop="location"
        >
          <el-input
            v-model="form.location"
            aria-placeholder="地区"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="签名"
          prop="introduction"
        >
          <el-input
            v-model="form.introduction"
            type="textarea"
            aria-placeholder="签名"
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
      title="编辑用户"
      :visible.sync="editVisible"
      width="400px"
      center
    >
      <el-form
        :model="editform"
        ref="form"
        laber-width="80px"
      >
        <el-form-item label="名字">
          <el-input
            v-model="editform.username"
            aria-placeholder="名字"
          ></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input
            v-model="editform.phone"
            aria-placeholder="电话"
          ></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱">
          <el-input
            v-model="editform.email"
            aria-placeholder="电子邮箱"
          ></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <input
            type="radio"
            value="0"
            name="sex"
            v-model="editform.sex"
          />&nbsp;&nbsp;女&nbsp;&nbsp;
          <input
            type="radio"
            value="1"
            name="sex"
            v-model="editform.sex"
          />&nbsp;&nbsp;男&nbsp;&nbsp;
        </el-form-item>
        <el-form-item
          label="生日"
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
        <el-form-item label="签名">
          <el-input
            v-model="editform.introduction"
            type="textarea"
            aria-placeholder="签名"
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
      <p>确定删除该用户吗？</p>
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
import { UserListAll, AddUser, EditUser, delUser } from '../api/index'
import { mixin } from '../mixs/index'
export default {
  mixins: [mixin],
  name: 'User',
  data () {
    return {
      editVisible: false,
      dialogVisible: false,
      delVisiable: false,
      multipSelection: [], // 哪些项被选择
      listUser: [], // 全部用户
      searchUser: [], // 所搜用户
      searchnow: '', // 当前输入用户名
      pagesize: 3, // 分页每页大小
      currentPage: 1, // 当前页
      idx: -1,
      form: { // 添加框
        username: '', passowrd: '', sex: '0', location: '', introduction: '', birth: '', email: '', phone: ''
      },
      editform: { // 编辑框
        id: '', username: '', passowrd: '', sex: '', location: '', introduction: '', pic: '', email: '', phone: ''
      },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        location: [{ required: true, message: '请输入地址', trigger: 'blur' }],
        birth: [{ required: true, message: '请输入生日', trigger: 'blur' }],
        email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入电话号码', trigger: 'blur' }]

      }
    }
  },
  computed: {
    // 分页展示
    data () {
      return this.listUser.slice((this.currentPage - 1) * this.pagesize, this.currentPage * this.pagesize)
    }
  },
  created () {
    this.UserAll()
  },
  watch: {
    // 搜索框内容发生变化时，列表值跟着变化
    searchnow: function () {
      if (this.searchnow === '') {
        this.listUser = this.searchUser
      } else {
        this.listUser = []
        for (let item of this.searchUser) {
          if (item.name.includes(this.searchnow)) {
            this.listUser.push(item)
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
    // 查询所有用户
    UserAll () {
      this.listUser = []
      this.searchUser = []
      UserListAll().then(res => {
        console.log(res)
        this.listUser = res
        this.searchUser = res
        this.currentPage = 1
      }).catch(err => {
        console.log(err)
      })
    },
    // 添加用户
    submit () {
      this.$refs['form'].validate(valid => {
        if (valid) {
          let d = this.form.birth
          let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
          let params = new URLSearchParams()
          console.log(datetime)
          params.append('username', this.form.username)
          params.append('sex', this.form.sex)
          params.append('password', this.form.passowrd)
          params.append('phone', this.form.phone)
          params.append('email', this.form.email)
          params.append('birth', datetime)
          params.append('location', this.form.location)
          params.append('introduction', this.form.introduction)
          AddUser(params).then(res => {
            if (res.code === 1) {
              this.UserAll()
              this.notify('添加成功', 'success')
            } else {
              this.notify('添加失败', 'error')
            }
          }).catch(err => {
            console.log(err)
          })
          this.dialogVisible = false
        }
      })
    },
    // 编辑页面
    editsubmit () {
      let d = new Date(this.editform.birth)
      let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
      let params = new URLSearchParams()
      console.log(datetime)
      params.append('id', this.editform.id)
      params.append('username', this.editform.username)
      params.append('sex', this.editform.sex)
      params.append('phone', this.editform.phone)
      params.append('email', this.editform.email)
      params.append('birth', datetime)
      params.append('location', this.editform.location)
      params.append('introduction', this.editform.introduction)
      EditUser(params).then(res => {
        if (res.code === 1) {
          this.UserAll()
          this.notify('修改成功', 'success')
        } else {
          this.notify('修改失败', 'error')
        }
      }).catch(err => {
        console.log(err)
      })
      this.editVisible = false
    },
    // 单击获取用户信息保存到editform
    handEdit (val) {
      this.editVisible = true
      this.editform = {
        id: val.id, username: val.username, sex: val.sex, location: val.location, introduction: val.introduction, email: val.email, phone: val.phoneNum
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
      return `${this.$store.state.HOST}/User/updateUserImg?id=${id}`
    },
    successUserupload (res, file) {
      let that = this
      console.log(res)
      if (res.code === 1) {
        that.UserAll()
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
    // 请求后台删除用户信息
    delsubmit () {
      delUser(this.idx).then(res => {
        if (res) {
          this.UserAll()
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
.User-img {
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
