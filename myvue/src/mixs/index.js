
export const mixin = {
  methods: {
    notify (title, type) {
      this.$notify({
        title: title,
        type: type
      })
    },
    // 根据相对地址获取绝对地址
    getUrl (url) {
      return `${this.$store.state.HOST}/${url}`
    },
    chageSex (value) {
      if (value === 0) {
        return '女'
      }
      if (value === 1) {
        return '男'
      }
      if (value === 2) {
        return '组合'
      }
      if (value === 3) {
        return '不明'
      } return value
    },
    getBirth (value) {
      return String(value).substr(0, 10)
    }, // 上传之前
    beforeUpload (file) {
      const img = (file.type === 'image/jpeg') || (file.type === 'image/png')
      if (!img) {
        this.$message.error('上传图片只能是jpg和png格式')
        return false
      }
      const imgbig = (file.size / 1024 / 1024) < 2
      if (!imgbig) {
        this.$message.error('图片大小不能超过2M')
        return false
      }
      return true
    },
    // 上传成功之后
    successupload (res, file) {
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
    // 单个删除获取id
    handdel (id) {
      this.idx = id
      this.delVisiable = true
    },
    // 批量删除获取id
    selectionChange (val) {
      this.multipSelection = val
    },
    // 批量删除
    delAll () {
      for (let item of this.multipSelection) {
        this.handdel(item.id)
        this.delsubmit()
      }
      this.multipSelection = []
    }
  }
}
