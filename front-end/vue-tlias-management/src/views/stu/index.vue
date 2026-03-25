<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { queryStuApi, addStuApi, queryByIdApi, updateStuApi, deleteStuApi, deductStuScoreApi } from '../../api/student'
import { getAllClazzApi } from '../../api/clazz'

// 钩子函数
onMounted(() => {
  getAllClazz()
  search()
})
const handleSizeChange = () => search()
const handleCurrentChange = () => search()

// 元数据
const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 2 }])
const degreeList = ref([{ name: '初中', value: 1 }, { name: '高中', value: 2 }, { name: '大专', value: 3 }, { name: '本科', value: 4 }, { name: '硕士', value: 5 }, { name: '博士', value: 6 }])
const clazzList = ref([])
const getAllClazz = async () => {
  const res = await getAllClazzApi()
  if (res.code) {
    clazzList.value = res.data
  }
}

// 查询条件
const searchStu = ref({ name: '', degree: '', clazzId: '' })
// 学员对象
const stuList = ref([])
// 分页参数
const currentPage = ref(1)  // 页码
const pageSize = ref(10)    // 每页展示记录数
const background = ref(true)
const total = ref(0)
// 查询
const search = async () => {
  const res = await queryStuApi(searchStu.value.name, searchStu.value.degree, searchStu.value.clazzId, currentPage.value, pageSize.value)
  if (res.code) {
    stuList.value = res.data.rows
    total.value = res.data.total
  }
}
// 清空
const clear = () => {
  searchStu.value = { name: '', degree: '', clazzId: '' }
  search()
}

// 对话框
const dialogVisible = ref(false)
const dialogTitle = ref('')
// 对话框表单 -- 学员
const stu = ref({ name: '', no: '', gender: null, phone: '', idCard: '', isCollege: null, address: '', degree: null, graduationDate: '', clazzId: null })
// 对话框表单 -- 校验
const stuFormRef = ref()
const rules = ref({
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2到10个字之间', trigger: 'blur' }
  ],
  no: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    { min: 10, max: 10, message: '学号应为10位', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { min: 11, max: 11, message: '手机号应为11位', trigger: 'blur' }
  ],
  idCard: [
    { required: true, message: '请输入身份证号', trigger: 'blur' },
    { min: 18, max: 18, message: '身份证号应为18位', trigger: 'blur' }
  ],
  isCollege: [
    { required: true, message: '请选择是为院校学员', trigger: 'change' }
  ],
})
// 新增学员
const addStu = () => {
  if (stuFormRef.value) stuFormRef.value.resetFields()
  dialogTitle.value = '新增学员'
  stu.value = { name: '', no: '', gender: null, phone: '', idCard: '', isCollege: null, address: '', degree: null, graduationDate: '', clazzId: null }
  dialogVisible.value = true
}
// 更新学员
const edit = async (id) => {
  // 信息回显
  const res = await queryByIdApi(id)
  if (res.code) {
    stu.value = res.data
  }
  dialogTitle.value = '修改学员信息'
  dialogVisible.value = true
}

// 保存
const save = async () => {
  // 表单校验
  if (!stuFormRef.value) return
  try {
    await stuFormRef.value.validate()
  } catch (e) {
    ElMessage.error('输入信息非法')
    return
  }
  let res
  if (stu.value.id) {
    res = await updateStuApi(stu.value)
    console.log('修改返回 res = ', res)
  } else {
    res = await addStuApi(stu.value)
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
const deleteStu = (id) => {
  ElMessageBox.confirm(
    '是否确认删除学员?',
    '警告',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {
      const res = await deleteStuApi(id)
      if (res.code) {
        ElMessage.success('学员已删除')
        search()
      } else {
        ElMessage.warning(res.msg)
      }
    })
    .catch(() => {
      ElMessage.info('取消删除')
    })
}
// 获取id数组
const ids = ref([])
const handleSelectionChange = (rows) => {
  // 监听 @selection-change 事件，获取行数组，再通过 .map()方法，将数组对象转换为指定属性的数组
  ids.value = rows.map(row => row.id)
}
// 批量删除
const batchDelete = () => {
  if (ids.value.length == 0) {
    ElMessage.warning('请选择学员')
    return
  }
  ElMessageBox.confirm(
    '是否确认删除学员?',
    '警告',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {
      const res = await deleteStuApi(ids.value)
      if (res.code) {
        ElMessage.success('学员已删除')
        search()
      } else {
        ElMessage.warning(res.msg)
      }
    })
    .catch(() => {
      ElMessage.info('取消删除')
    })
}

// 违纪扣分
const violate = async (id) => {
  ElMessageBox.prompt('违纪扣分', '学生违纪处理', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    inputPattern:
      /^[0-9]{1,2}$/,
    inputErrorMessage: '非法输入',
  })
    .then(async ({ value }) => {
      const res = await deductStuScoreApi(id, value)
      if (res.code) {
        ElMessage.warning(`违纪扣分：${value}`)
        search()
      } else {
        ElMessage.error(res.msg)
      }
    })
    .catch(() => {
      ElMessage.info('取消扣分')
    })
}

</script>

<template>
  <!-- 标题 -->
  <h1>学员管理</h1>

  <!-- 搜索栏 -->
  <div class="container">
    <el-form :inline="true" :model="searchStu">
      <el-form-item label="姓名">
        <el-input v-model="searchStu.name" placeholder="请输入姓名" clearable />
      </el-form-item>
      <el-form-item label="最高学历">
        <el-select v-model="searchStu.degree" placeholder="请选择学历" clearable>
          <el-option v-for="d in degreeList" :key="d.value" :label="d.name" :value="d.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="所属班级">
        <el-select v-model="searchStu.clazzId" placeholder="请选择班级" clearable>
          <el-option v-for="c in clazzList" :key="c.id" :label="c.name" :value="c.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <!-- 功能按钮 -->
  <div class="container">
    <el-button type="primary" @click="addStu">+ 新增学员</el-button>
    <el-button type="danger" @click="batchDelete">- 批量删除</el-button>
  </div>

  <!-- 学员表格 -->
  <div class="container">
    <el-table :data="stuList" border :cell-style="{ textAlign: 'center' }"
      :header-cell-style="{ 'text-align': 'center' }" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" />
      <el-table-column prop="name" label="姓名" width="100" />
      <el-table-column prop="no" label="学号" width="130" />
      <el-table-column prop="clazzId" label="班级" width="160">
        <template #default="scope">
          <span>{{clazzList.find(clazz => clazz.id === scope.row.clazzId)?.name || ''}}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" width="100">
        <template #default="scope">
          {{ scope.row.gender == 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机号" width="120" />
      <el-table-column prop="degree" label="最高学位" width="110">
        <template #default="scope">
          <span>{{degreeList.find(degree => degree.value === scope.row.degree)?.name || ''}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="violationCount" label="违纪次数" width="100" />
      <el-table-column prop="violationScore" label="违纪扣分" width="100" />
      <el-table-column prop="updateTime" label="最后操作时间" width="190" value-format="YYYY-MM-DD HH:mm:ss" />
      <el-table-column label="操作" width="300">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon>
              <EditPen />
            </el-icon>编辑</el-button>
          <el-button type="warning" size="small" @click="violate(scope.row.id)"><el-icon>
              <Scissor />
            </el-icon>违纪</el-button>
          <el-button type="danger" size="small" @click="deleteStu(scope.row.id)"><el-icon>
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

  <!-- 新增/修改学员对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="50%">
    <el-form :model="stu" label-width="125px" :rules="rules" ref="stuFormRef">
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="stu.name" placeholder="请输入学员姓名，2-20个字"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="学号" prop="no">
            <el-input v-model="stu.no" placeholder="请输入10位学号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="stu.gender" placeholder="请选择性别" style="width: 100%;">
              <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="stu.phone" placeholder="请输入学员手机号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="身份证号" prop="idCard">
            <el-input v-model="stu.idCard" placeholder="请输入身份证号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否为院校学员" prop="isCollege">
            <el-select v-model="stu.isCollege" placeholder="请选择" style="width: 100%;">
              <el-option label="是" :value="1"></el-option>
              <el-option label="否" :value="0"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="联系地址" prop="address">
            <el-input v-model="stu.address" placeholder="请输入联系地址"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最高学历" prop="degree">
            <el-select v-model="stu.degree" placeholder="请选择" style="width: 100%;">
              <el-option v-for="d in degreeList" :key="d.value" :label="d.name" :value="d.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第五行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="毕业日期" prop="graduationDate">
            <el-date-picker v-model="stu.graduationDate" type="date" style="width: 100%;" placeholder="请选择日期"
              format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="所属班级" prop="clazzId">
            <el-select v-model="stu.clazzId" placeholder="请选择班级" style="width: 100%;">
              <el-option v-for="c in clazzList" :key="c.id" :label="c.name" :value="c.id"></el-option>
            </el-select>
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

  <!-- 违纪对话框 -->

</template>

<style scoped>
.container {
  margin: 10px 0
}
</style>