<script setup>
import { ref, watch, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { queryStuApi } from '../../api/student'
import { getAllClazzApi } from '../../api/clazz'

// 钩子函数
onMounted(() => {
  search()
  getAllClazz()
})

// 元数据
const degree = ref([{ name: '初中', value: 1 }, { name: '高中', value: 2 }, { name: '大专', value: 3 }, { name: '本科', value: 4 }, { name: '硕士', value: 5 }, { name: '博士', value: 6 }])
const clazzList = ref([])
const getAllClazz = async () => {
  const res = await getAllClazzApi()
  if (res.code) {
    clazzList.value = res.data
  }
}

// 查询条件
const searchStu = ref({ name: '', degree: null, clazzId: null })
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
    stuList.value = res.data
  }
}
// 清空
const clear = () => {
  searchStu.value = { name: '', degree: null, clazzId: null }
}

</script>

<template>
  <!-- 标题 -->
  <h1>学员管理</h1>

  <!-- 搜索栏 -->
  <div class="container">
    <el-form :inline="true" :model="searchStu">
      <el-form-item label="姓名">
        <el-input v-model="searchStu.user" placeholder="请输入姓名" clearable />
      </el-form-item>
      <el-form-item label="最高学历">
        <el-select v-model="searchStu.degree" placeholder="请选择学历" clearable>
          <el-option v-for="d in degree" :key="d.value" :label="d.name" :value="d.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="所属班级">
        <el-select v-model="searchStu.clazzId" placeholder="请选择班级" clearable>
          <el-option v-for="c in clazzList" :key="c.id" :label="c.name" :value="c.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saerch">查询</el-button>
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
      <el-table-column prop="no" label="学号" width="100" />
      <el-table-column prop="clazzName" label="班级" width="120" />
      <el-table-column label="性别" width="100">
        <template #default="scope">
          {{ scope.row.gender == 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column prop="deptName" label="所属部门" width="120" />
      <el-table-column prop="job" label="职位">
        <template #default="scope">
          <span v-if="scope.row.job == 1">班主任</span>
          <span v-else-if="scope.row.job == 2">讲师</span>
          <span v-else-if="scope.row.job == 3">学工主管</span>
          <span v-else-if="scope.row.job == 4">教研主管</span>
          <span v-else-if="scope.row.job == 5">咨询师</span>
          <span v-else>其它</span>
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


</template>

<style scoped>
.container {
  margin: 10px 0
}
</style>