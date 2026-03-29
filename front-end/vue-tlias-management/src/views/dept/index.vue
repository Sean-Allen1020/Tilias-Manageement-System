<script setup>
import { EditPen } from '@element-plus/icons-vue';
import { ref, onMounted } from 'vue'  // 引入响应式函数，和钩子式函数

// 导入api包
import { queryAllApi, addApi, queryByIdApi, updateApi, deleteByIdApi } from '@/api/dept.js'
import { ElMessage, ElMessageBox } from 'element-plus'

// 钩子函数
onMounted(() => {  // 调用响应式函数onMounted，声明钩子函数
  search()
})

// 对话框状态
const formTitle = ref('')   // 对话框标题
const dialogFormVisible = ref(false)  // 对话框是否显示
const addDept = () => {
  formTitle.value = '新增部门'
  dept.value = { name: '' }
  // 重置表单校验状态
  if (deptFormRef.value) deptFormRef.value.resetFields()

  dialogFormVisible.value = true
}

// 响应数据
const deptList = ref([])
const dept = ref({ name: '' })

// 查询
const search = async () => {
  const res = await queryAllApi()
  if (res.code) {  // Js隐式类型转换,如果返回值是数字 0,则转换为boolen的false,其它为true
    deptList.value = res.data
  }
}

// 表单校验规则
const rules = ref({
  name: [
    { required: true, message: '请输入部门名称', trigger: 'blur' },
    { min: 2, max: 10, message: '名称字数不合法', trigger: 'blur' },
  ]
})

//表单组件引用对象，用于校验表单
const deptFormRef = ref()
// 新增与修改
const save = async () => {
  // 校验表单状态
  if (!deptFormRef.value) return
  try {
    await deptFormRef.value.validate()
  } catch (e) {
    ElMessage.error('输入信息非法')
    return
  }

  let res
  if (dept.value.id) {
    // 修改
    res = await updateApi(dept.value)
  } else {
    // 新增
    res = await addApi(dept.value)
  }

  if (res.code) {
    // 成功提示
    ElMessage.success("保存成功")
    // 关闭对话框
    dialogFormVisible.value = false
    // 查询
    search()
  }
  else {
    // 失败提示
    ElMessage.error(res.msg)
  }
}

// 根据id查询部门(编辑按钮)
const edit = async (id) => {
  const res = await queryByIdApi(id)
  if (res.code) {
    formTitle.value = '编辑部门'
    dept.value = res.data
    // 重置表单校验状态
    if (deptFormRef.value) deptFormRef.value.resetFields()

    dialogFormVisible.value = true
  }
}
// 删除
const deleteById = async (id) => {
  // 弹出确认框
  ElMessageBox.confirm(
    '是否确认删除部门?',
    '警告',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {   // 确认后的回调函数
      const res = await deleteByIdApi(id)
      if (res.code) {
        ElMessage({
          type: 'success',
          message: '部门已删除',
        })
        search()
      } else {
        ElMessage.warning(res.msg)
      }

    })
    .catch(() => {  // 取消后的回调函数
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
}


</script>

<template>
  <!-- 标题 -->
  <h1>部门管理</h1>

  <!-- 操作按钮 -->
  <div class="container">
    <el-button type="primary" @click="addDept">+ 新增部门</el-button>
  </div>

  <!-- 数据表格 -->
  <div class="container">
    <el-table :data="deptList" border style="width: 100%">
      <el-table-column type="index" label="序号" width="100" align="center" />
      <el-table-column prop="name" label="部门名称" width="260" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" width="300" align="center" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon>
              <EditPen />
            </el-icon>编辑</el-button>
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)"><el-icon>
              <Delete />
            </el-icon>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 对话框: 新增按钮, 编辑按钮共用 -->
  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="500">
    <el-form :model="dept" :rules="rules" ref="deptFormRef">
      <el-form-item label="部门名称" :label-width="formLabelWidth" prop="name">
        <el-input v-model="dept.name" autocomplete="off" placeholder="请输入部门名称, 长度2-10位" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
.container {
  margin: 20px 0
}
</style>
