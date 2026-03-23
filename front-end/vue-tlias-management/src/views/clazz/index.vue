<script setup>
import { ref, watch, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { queryClazzApi, addClazzApi, queryByIdApi, updateClazzApi, deleteClazzApi } from '@/api/clazz.js'
import { getAllEmpsApi } from '../../api/emp'

// 钩子函数
onMounted(() => {
  search()
  getAllEmps()
})

// 元数据
const empList = ref([])
const subject = ref([{ name: 'java', value: 1 }, { name: '前端', value: 2 }, { name: '大数据', value: 3 }, { name: 'Python', value: 4 }, { name: 'Go', value: 5 }, { name: '嵌入式', value: 6 }])
// 查询所有员工
const getAllEmps = async () => {
  const res = await getAllEmpsApi()
  if (res.code) {
    empList.value = res.data
  }
}

// 查询条件
const searchClazz = ref({ name: '', date: [], begin: '', end: '' })
// 侦听date
watch(() => searchClazz.value.date, (newDate) => {
  if (newDate && newDate.length == 2) {
    searchClazz.value.begin = newDate[0]
    searchClazz.value.end = newDate[1]
  } else {
    searchClazz.value.begin = ''
    searchClazz.value.end = ''
  }
})
// 分页参数
const currentPage = ref(1)  // 页码
const pageSize = ref(5)    // 每页展示记录数
const background = ref(true)
const total = ref(0)
// 每页展示记录数变化时，查询列表
const handleSizeChange = async () => {
  search()
}
// 页码变化时，查询列表
const handleCurrentChange = async () => {
  search()
}

// 查询按钮
const clazzList = ref([])
const search = async () => {
  const res = await queryClazzApi(searchClazz.value.name, searchClazz.value.begin, searchClazz.value.end, currentPage.value, pageSize.value)
  if (res.code) {
    clazzList.value = res.data.rows
    total.value = res.data.total
  }
}
// 清空
const clear = () => {
  searchClazz.value = { name: '', date: [], begin: '', end: '' }
  search()
}

// 对话框
const dialogVisible = ref(false)
const dialogTitle = ref('新增班级')
// 对话框表单 -- 班级
const clazz = ref({ name: '', room: '', beginDate: '', endDate: '', masterId: null, subject: null })
// 对话框表单 -- 校验
const clazzFormRef = ref()
const rules = ref({
  name: [
    { required: true, message: '请输入班级', trigger: 'blur' },
    { min: 4, max: 30, message: '班级名称非法', trigger: 'blur' }
  ],
  beginDate: [
    { required: true, message: '请选择开课日期', trigger: 'blur' }
  ],
  endDate: [
    { required: true, message: '请选择结课日期', trigger: 'blur' }
  ],
  subject: [
    { required: true, message: '请选择学科', trigger: 'change' }
  ]
})

// 新增班级
const addClazz = () => {
  clazz.value = { name: '', room: '', beginDate: '', endDate: '', masterId: null, subject: null }
  if (clazzFormRef.value) clazzFormRef.value.resetFields()
  dialogVisible.value = true
}
// 更新班级
const edit = async (id) => {
  const res = await queryByIdApi(id)
  if (res.code) {
    clazz.value = res.data
  }

  if (clazzFormRef.value) clazzFormRef.value.resetFields()
  dialogTitle.value = '修改班级'
  dialogVisible.value = true
}

// 保存
const save = async () => {
  // 表单校验
  if (!clazzFormRef.value) return
  try {
    await clazzFormRef.value.validate()
  } catch (e) {
    ElMessage.error('输入信息非法')
    return
  }
  let res
  if (clazz.value.id) {
    res = await updateClazzApi(clazz.value)
  } else {
    res = await addClazzApi(clazz.value)
  }
  if (res.code) {
    ElMessage.success('保存成功')
    dialogVisible.value = false
    search()
  } else {
    ElMessage.error(res.msg)
  }
}

// 删除
const deleteClazz = async (id) => {
  ElMessageBox.confirm(
    '是否确认删除员工?',
    '警告',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {
      const res = await deleteClazzApi(id)
      if (res.code) {
        ElMessage({
          type: 'success',
          message: '班级已删除',
        })
        search()
      } else {
        ElMessage.message(res.msg)
      }
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
}

</script>

<template>
  <!-- 标题 -->
  <h1>班级管理</h1>

  <!-- 搜索表单 -->
  <div class="container">
    <el-form :inline="true" :model="searchClazz">
      <el-form-item label="班级名称">
        <el-input v-model="searchClazz.name" placeholder="请输入班级名称" clearable />
      </el-form-item>
      <el-form-item label="结课日期">
        <el-date-picker v-model="searchClazz.date" type="daterange" range-separator="到" start-placeholder="开始日期"
          end-placeholder="结束日期" , value-format="YYYY-MM-DD" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <!-- 功能按钮 -->
  <div class="container">
    <el-button type="primary" @click="addClazz">+ 新增班级</el-button>
  </div>

  <!-- 班级表格 -->
  <div class="container">
    <el-table :data="clazzList" border :cell-style="{ textAlign: 'center' }"
      :header-cell-style="{ 'text-align': 'center' }" style="width: 100%">
      <el-table-column type="index" label="序号" width="60" />
      <el-table-column prop="name" label="班级名称" width="160" />
      <el-table-column prop="room" label="班级教室" width="100" />
      <el-table-column prop="masterName" label="班主任" width="100" />
      <el-table-column prop="beginDate" label="开课日期" width="160" />
      <el-table-column prop="endDate" label="结课日期" width="160" />
      <el-table-column prop="status" label="状态" width="80" />
      <el-table-column prop="updateTime" label="最后操作时间" width="180" value-format="YYYY-MM-DD HH:mm:ss" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon>
              <EditPen />
            </el-icon>编辑</el-button>
          <el-button type="danger" size="small" @click="deleteClazz(scope.row.id)"><el-icon>
              <Delete />
            </el-icon>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 分页条 -->
  <div class="container">
    <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[5, 10, 20]"
      :background="background" layout="total, sizes, prev, pager, next, jumper" :total="total"
      @size-change="handleSizeChange" @current-change="handleCurrentChange" />
  </div>

  <!-- 新增/修改班级对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="30%">
    <el-form :model="clazz" label-width="80px" :rules="rules" ref="clazzFormRef">
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-form-item label="班级名称" prop="name" :label-width="formLabelWidth">
        <el-input v-model="clazz.name" placeholder="请输入班级名称，如：JavaEE就业170期"></el-input>
      </el-form-item>

      <!-- 第二行 -->
      <el-form-item label="班级教师" prop="room" :label-width="formLabelWidth">
        <el-input v-model="clazz.room" placeholder="请填写教室编号"></el-input>
      </el-form-item>

      <!-- 第三行 -->
      <el-form-item label="开课日期" :label-width="formLabelWidth" prop="beginDate">
        <el-date-picker v-model="clazz.beginDate" type="date" style="width: 100%;" placeholder="选择日期"
          format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
      </el-form-item>

      <!-- 第四行 -->
      <el-form-item label="结课日期" :label-width="formLabelWidth" prop="endDate">
        <el-date-picker v-model="clazz.endDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"></el-date-picker>
      </el-form-item>

      <!-- 第五行 -->
      <el-form-item label="班主任" :label-width="formLabelWidth">
        <el-select v-model="clazz.masterId" placeholder="请选择班主任" style="width: 100%;">
          <el-option v-for="e in empList" :key="e.id" :label="e.name" :value="e.id"></el-option>
        </el-select>
      </el-form-item>

      <!-- 第六行 -->
      <el-form-item label="学科" :label-width="formLabelWidth" prop="subject">
        <el-select v-model="clazz.subject" placeholder="请选择学科" style="width: 100%;">
          <el-option v-for="s in subject" :key="s.value" :label="s.name" :value="s.value"></el-option>
        </el-select>
      </el-form-item>
    </el-form>

    <!-- 底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.container {
  margin: 10px 0
}
</style>