<script setup>
import { ref, watch, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { queryAllApi as queryAllDeptApi } from '@/api/dept.js'
import { queryPageApi, addEmpApi, queryInfoApi, updateEmpApi, deleteEmpApi } from '@/api/emp.js'

// 钩子函数
onMounted(() => {
  search()
  queryAllDepts()
  getToken()
})

// 元数据，用于下拉列表等数据的统一维护
// 职位列表数据
const jobs = ref([{ name: '班主任', value: 1 }, { name: '讲师', value: 2 }, { name: '学工主管', value: 3 }, { name: '教研主管', value: 4 }, { name: '咨询师', value: 5 }, { name: '其他', value: 6 }])
// 性别列表数据
const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 2 }])
// 部门列表数据
const depts = ref([])
const queryAllDepts = async () => {
  const res = await queryAllDeptApi()
  if (res.code) {
    depts.value = res.data
  }
}

// 搜索对象
const searchEmp = ref({ name: '', gender: '', date: [], begin: '', end: '' })
// 侦听函数，侦听searchEmp中的date属性
watch(() => searchEmp.value.date, (newDate) => {
  if (newDate && newDate.length == 2) {   // Elplus的表单清空，会返回 null，直接用null做数式判断，会出现运行时异常
    searchEmp.value.begin = newDate[0]
    searchEmp.value.end = newDate[1]
  }
  else {
    searchEmp.value.begin = ''  // 这里如果不做else处理，上一次查询后赋的值会残留下来
    searchEmp.value.end = ''
  }
})

// 控制弹窗
const dialogVisible = ref(false)
const dialogTitle = ref('')

//员工对象
const empList = ref([])
// 分页参数
const currentPage = ref(1)  // 页码
const pageSize = ref(10)    // 每页展示记录数
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
//新增/修改表单参数
const employee = ref({
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: []
})

// 表单查询列表
const search = async () => {
  const res = await queryPageApi(searchEmp.value.name, searchEmp.value.gender, searchEmp.value.begin, searchEmp.value.end, currentPage.value, pageSize.value)
  if (res.code) {
    empList.value = res.data.rows
    total.value = res.data.total
  }

}
// 清空查询列表
const clear = () => {
  searchEmp.value = { name: '', gender: '', date: [], begin: '', end: '' }
  search()
}
// 新增员工
const addEmp = () => {
  dialogTitle.value = '新增员工'
  employee.value = { username: '', name: '', gender: '', hone: '', job: '', salary: '', deptId: '', entryDate: '', image: '', exprList: [] }
  if (empFormRef.value) empFormRef.value.resetFields()
  dialogVisible.value = true
}
// 添加工作经历栏
const addExprItem = () => {
  // .push 方法，用于为数组尾部添加一个元素
  employee.value.exprList.push({ company: '', job: '', begin: '', end: '', date: [] })
}
// 删除工作经历栏
const delExprItem = (index) => {
  employee.value.exprList.splice(index, 1)
}
// 通过 @change事件，检查date属性的变化，为begin，end赋值
const handleDateChange = (index) => {
  const expr = employee.value.exprList[index]
  const date = expr.date

  if (date && date.length == 2) {
    expr.begin = date[0]
    expr.end = date[1]
  } else {
    expr.begin = ''
    expr.end = ''
  }
}

//表单组件引用对象，用于校验表单
const empFormRef = ref()
// 根据id查询员工信息
const edit = async (id) => {
  const res = await queryInfoApi(id)
  if (res.code) {
    employee.value = res.data
    dialogTitle.value = '修改员工信息'
    if (empFormRef.value) empFormRef.value.resetFields()

    // 对日期进行处理
    let exprList = employee.value.exprList
    if (exprList && exprList.length > 0) {
      exprList.forEach((expr) => {
        // 在 expr 中动态追加 date 属性，用于回显
        expr.date = [expr.begin, expr.end]
      })
    }

    dialogVisible.value = true
  }
}
// 保存
const save = async () => {
  // 校验表单状态
  if (!empFormRef.value) return
  try {
    await empFormRef.value.validate()
  } catch (e) {
    ElMessage.error('输入信息非法')
    return
  }

  let res
  if (employee.value.id) {
    // 修改员工
    res = await updateEmpApi(employee.value)
  } else {
    // 新增员工
    res = await addEmpApi(employee.value)
  }

  if (res.code) {
    ElMessage.success('保存成功')
    dialogVisible.value = false
    search()
  }
  else {
    // 失败提示
    ElMessage.error(res.msg)
  }
}
//表单校验规则
const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度应在2到20个字符之间', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ]
});

// 根据id删除员工
const deleteById = (id) => {
  // 弹出确认框
  ElMessageBox.confirm(
    '是否确认删除员工?',
    '警告',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {   // 确认后的回调函数
      const res = await deleteEmpApi(id)
      if (res.code) {
        ElMessage.success('员工已删除')
        search()
      } else {
        ElMessage.message(res.msg)
      }
    })
    .catch(() => {  // 取消后的回调函数
      ElMessage.info('取消删除')
    })
}
// 批量删除员工
const ids = ref([])
const handleSelectionChange = (rows) => {
  // 监听 @selection-change 事件，获取行数组，再通过 .map()方法，获取数组对象中的指定属性
  ids.value = rows.map(row => row.id)
}

const batchDelete = () => {
  if (ids.value.length == 0) {
    ElMessage.warning('请选择员工')
    return
  }
  // 弹出确认框
  ElMessageBox.confirm(
    '是否删除员工?',
    '警告',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {   // 确认后的回调函数
      const res = await deleteEmpApi(ids.value)
      if (res.code) {
        ElMessage.success('员工已删除')
        search()
      } else {
        ElMessage.message(res.msg)
      }
    })
    .catch(() => {  // 取消后的回调函数
      ElMessage.info('取消删除')
    })
}

//文件上传
const token = ref('')
const getToken = () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'))
  if (loginUser && loginUser.token) {
    token.value = loginUser.token
  }
}
// 图片上传成功后触发
const handleAvatarSuccess = (response) => {
  employee.value.image = response.data
}
// 文件上传之前触发
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上传图片')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('只能上传10M以内图片')
    return false
  }
  return true
}

</script>

<template>
  <!-- 标题 -->
  <h1>员工管理</h1>

  <!-- 搜索表单 -->
  <div class="container">
    <el-form :inline="true" :model="searchEmp">
      <el-form-item label="姓名">
        <el-input v-model="searchEmp.name" placeholder="请输入员工姓名" clearable />
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="searchEmp.gender" placeholder="请选择" clearable>
          <el-option label="男" value="1" />
          <el-option label="女" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="入职时间">
        <el-date-picker v-model="searchEmp.date" type="daterange" range-separator="到" start-placeholder="开始日期"
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
    <el-button type="primary" @click="addEmp">+ 新增员工</el-button>
    <el-button type="danger" @click="batchDelete">- 批量删除</el-button>
  </div>

  <!-- 员工表格 -->
  <div class="container">
    <el-table :data="empList" border :cell-style="{ textAlign: 'center' }"
      :header-cell-style="{ 'text-align': 'center' }" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" />
      <el-table-column prop="name" label="姓名" width="120" />
      <el-table-column label="性别" width="100">
        <template #default="scope">
          {{ scope.row.gender == 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column label="头像" width="120">
        <template #default="scope">
          <img :src="scope.row.image" alt="未知">
        </template>
      </el-table-column>
      <el-table-column prop="deptName" label="所属部门" width="120" />
      <el-table-column prop="job" label="职位">
        <template #default="scope">
          <span>{{jobs.find(job => job.value === scope.row.job)?.name || ''}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="entryDate" label="入职时间" width="180" />
      <el-table-column prop="updateTime" label="最后操作时间" width="200" value-format="YYYY-MM-DD HH:mm:ss" />
      <el-table-column label="操作">
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

  <!-- 分页条 -->
  <div class="container">
    <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize"
      :page-sizes="[5, 10, 20, 50, 75, 100]" :background="background" layout="total, sizes, prev, pager, next, jumper"
      :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
  </div>

  <!-- 新增/修改员工对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form :model="employee" label-width="80px" :rules="rules" ref="empFormRef">
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="employee.username" placeholder="请输入员工用户名，2-20个字"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="employee.name" placeholder="请输入员工姓名，2-10个字"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="employee.gender" placeholder="请选择性别" style="width: 100%;">
              <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="employee.phone" placeholder="请输入员工手机号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="职位">
            <el-select v-model="employee.job" placeholder="请选择职位" style="width: 100%;">
              <el-option v-for="j in jobs" :key="j.value" :label="j.name" :value="j.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="薪资">
            <el-input v-model="employee.salary" placeholder="请输入员工薪资"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="所属部门">
            <el-select v-model="employee.deptId" placeholder="请选择部门" style="width: 100%;">
              <el-option v-for="d in depts" :key="d.id" :label="d.name" :value="d.id"></el-option>

            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入职日期">
            <el-date-picker v-model="employee.entryDate" type="date" style="width: 100%;" placeholder="选择日期"
              format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第五行 -->
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="头像">
            <el-upload class="avatar-uploader" action="/api/upload" :headers="{ 'token': token }"
              :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
              <img v-if="employee.image" :src="employee.image" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon">
                <Plus />
              </el-icon>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>


      <!-- 工作经历 -->
      <!-- 第六行 -->
      <el-row :gutter="10">
        <el-col :span="24">
          <el-form-item label="工作经历">
            <el-button type="success" size="small" @click="addExprItem">+ 添加工作经历</el-button>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第七行 ...  工作经历 -->
      <el-row :gutter="3" v-for="(expr, index) in employee.exprList">
        <el-col :span="10">
          <el-form-item size="small" label="时间" label-width="80px">
            <el-date-picker v-model=expr.date type="daterange" @change="handleDateChange(index)" range-separator="至"
              start-placeholder="开始日期" end-placeholder="结束日期" format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item size="small" label="公司" :prop="'exprList.' + index + '.company'" :rules="[
            { required: true, message: '请输入公司', trigger: 'blur' }
          ]" label-width="60px">
            <el-input v-model="expr.company" placeholder="请输入公司名称"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item size="small" label="职位" label-width="60px">
            <el-input v-model="expr.job" placeholder="请输入职位"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="2">
          <el-form-item size="small" label-width="0px">
            <el-button type="danger" @click="delExprItem(index)">- 删除</el-button>
          </el-form-item>
        </el-col>
      </el-row>
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

.avatar {
  height: 40px;
}

.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  border-radius: 10px;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}
</style>