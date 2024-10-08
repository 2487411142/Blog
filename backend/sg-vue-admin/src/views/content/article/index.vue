<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form
          v-show="showSearch"
          ref="queryForm"
          :model="queryParams"
          :inline="true"
          label-width="68px"
        >
          <el-form-item label="Title" prop="title">
            <el-input
              v-model="queryParams.title"
              placeholder="Please enter a title."
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="Summary" prop="summary">
            <el-input
              v-model="queryParams.summary"
              placeholder="Please enter a summary"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
            >Search</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
            >Add</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              @click="handleDelete"
            >Delete</el-button>
          </el-col>

          <!-- <right-toolbar
            :show-search.sync="showSearch"
            :columns="columns"
            @queryTable="getList"
          /> -->
        </el-row>

        <el-table :data="articleList" style="width: 100%" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="id" label="Blog ID" align="center" />
          <el-table-column prop="title" label="Title" align="center" />
          <el-table-column prop="summary" label="Summary" align="center" />
          <el-table-column prop="createTime" label="Create time" align="center" />

          <el-table-column
            label="operation"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
              >Modify</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
              >Delete</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-pagination
      :page-size.sync="queryParams.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      :page-sizes="[10, 20, 30, 40]"
      :current-page.sync="queryParams.pageNum"
      @current-change="getList"
      @size-change="getList"
    />

  </div>
</template>

<script>
// import { getToken } from '@/utils/auth'
import {
  listArticle,
  delArticle
}
from '@/api/content/article'

export default {
  name: 'Article',
  data() {
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined
      },
      title: '',
      // 是否显示弹出层
      open: false,
      // 总条数
      total: 0,
      articleList: [],
      showSearch: true
    }
  },
  watch: {},
  created() {
    this.getList()
  },
  methods: {
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true
      listArticle(this.queryParams).then((response) => {
        this.articleList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.$router.push('/write?id=' + row.id)
    },
    /** 新增用户 */
    handleAdd() {
      this.$router.push('/write')
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('Confirm to delete blog ' + ids + '?').then(function() {
        return delArticle(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('Delete success.')
      }).catch(() => {})
    }
  }
}
</script>
