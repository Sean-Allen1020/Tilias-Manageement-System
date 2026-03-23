<script setup>
import { ref, watch, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { queryStuApi } from '../../api/student'
import { getAllClazzApi } from '../../api/clazz'

// 钩子函数
onMounted(() => {
  getAllClazz()
  search()
})
const handleSizeChange = () => search()
const handleCurrentChange = () => search()

// 元数据
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
const dialogTitle = ref('新增学员')
// 对话框表单 -- 学员
const stu = ref({ name: '', room: '', beginDate: '', endDate: '', masterId: null, subject: null })
// 对话框表单 -- 校验
const stuFormRef = ref()
// const rules = ref({
//   name: [
//     { required: true, message: '请输入班级', trigger: 'blur' },
//     { min: 4, max: 30, message: '班级名称非法', trigger: 'blur' }
//   ],
//   beginDate: [
//     { required: true, message: '请选择开课日期', trigger: 'blur' }
//   ],
//   endDate: [
//     { required: true, message: '请选择结课日期', trigger: 'blur' }
//   ],
//   subject: [
//     { required: true, message: '请选择学科', trigger: 'change' }
//   ]
// })
// 新增学员
const addStu = () => {
  dialogVisible.value = true
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
    <el-button type="danger" @click="batchDelete(ids)">- 批量删除</el-button>
  </div>

  <!-- 学员表格 -->
  <div class="container">
    <el-table :data="stuList" border :cell-style="{ textAlign: 'center' }"
      :header-cell-style="{ 'text-align': 'center' }" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" />
      <el-table-column prop="name" label="姓名" width="120" />
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
      <el-table-column prop="degree" label="最高学位" width="120">
        <template #default="scope">
          <span>{{degreeList.find(degree => degree.value === scope.row.degree)?.name || ''}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="violationCount" label="违纪次数" width="100" />
      <el-table-column prop="violationScore" label="违纪扣分" width="100" />
      <el-table-column prop="updateTime" label="最后操作时间" width="200" value-format="YYYY-MM-DD HH:mm:ss" />
      <el-table-column label="操作" width="300">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon>
              <EditPen />
            </el-icon>编辑</el-button>
          <el-button type="warning" size="small" @click="violate(scope.row.id)"><el-icon>
              <Scissor />
            </el-icon>违纪</el-button>
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)"><el-icon>
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
  <el-dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form :model="stu" label-width="80px" :rules="rules" ref="stuFormRef">
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="stu.username" placeholder="请输入员工用户名，2-20个字"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="stu.name" placeholder="请输入员工姓名，2-10个字"></el-input>
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
            <el-input v-model="stu.phone" placeholder="请输入员工手机号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="职位">
            <el-select v-model="stu.job" placeholder="请选择职位" style="width: 100%;">
              <el-option v-for="j in jobs" :key="j.value" :label="j.name" :value="j.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="薪资">
            <el-input v-model="stu.salary" placeholder="请输入员工薪资"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="所属部门">
            <el-select v-model="stu.deptId" placeholder="请选择部门" style="width: 100%;">
              <el-option v-for="d in depts" :key="d.id" :label="d.name" :value="d.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入职日期">
            <el-date-picker v-model="stu.entryDate" type="date" style="width: 100%;" placeholder="选择日期"
              format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第五行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="入职日期">
            <el-date-picker v-model="stu.entryDate" type="date" style="width: 100%;" placeholder="选择日期"
              format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="所属部门">
            <el-select v-model="stu.deptId" placeholder="请选择部门" style="width: 100%;">
              <el-option v-for="d in depts" :key="d.id" :label="d.name" :value="d.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 底部按钮 -->
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </span>
      </template>
    </el-form>
  </el-dialog>

</template>

<style scoped>
.container {
  margin: 10px 0
}
</style>