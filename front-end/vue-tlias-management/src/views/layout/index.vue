<script setup>
import { HomeFilled } from '@element-plus/icons-vue';
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'// 引入 router实例
import { useRouter } from 'vue-router'
const router = useRouter()

const name = ref('')
// 从localStorage中取出登录信息
const getName = () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'))
  if (loginUser && loginUser.name) {
    name.value = loginUser.name
  }
}

// 退出登录
const logout = () => {
  // 弹出确认框
  ElMessageBox.confirm(
    '是否退出登录?',
    '警告',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {   // 确认后的回调函数
      ElMessage.success('退出成功')
      localStorage.removeItem('loginUser')  // 移除登录信息
      router.push('/login') // 跳转页面
    })
}

// 钩子函数
onMounted(() => {
  getName()
})

</script>

<template>
  <div class="common-layout">
    <el-container>
      <!-- Header 区域 -->
      <el-header class="header">
        <span class="title">Tlias智能学习辅助系统</span>
        <span class="right_tool">
          <a href="">
            <el-icon>
              <EditPen />
            </el-icon> 修改密码 &nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;
          </a>
          <a href="javascript:void(0)" @click="logout">
            <el-icon>
              <SwitchButton />
            </el-icon> 退出登录 【{{ name }}】
          </a>
        </span>
      </el-header>

      <el-container>
        <!-- 左侧菜单 -->
        <el-aside width="200px" class="aside">
          <el-scrollbar>
            <el-menu router>
              <!-- 首页跳转 -->
              <el-menu-item index="/index">
                <el-icon>
                  <Promotion />
                </el-icon> 首页
              </el-menu-item>

              <!-- 班级学员管理 -->
              <el-sub-menu index="/manage">
                <template #title>
                  <el-icon>
                    <Menu />
                  </el-icon><b>班级学员管理</b>
                </template>
                <el-menu-item index="/clazz"><el-icon>
                    <HomeFilled />
                  </el-icon>班级管理
                </el-menu-item>
                <el-menu-item index="/stu"><el-icon>
                    <UserFilled />
                  </el-icon>学员管理
                </el-menu-item>
              </el-sub-menu>

              <!-- 系统信息管理 -->
              <el-sub-menu index="/system">
                <template #title>
                  <el-icon>
                    <Tools />
                  </el-icon><b>系统信息管理</b>
                </template>
                <el-menu-item index="/dept"><el-icon>
                    <HelpFilled />
                  </el-icon>部门管理
                </el-menu-item>
                <el-menu-item index="/emp"><el-icon>
                    <Avatar />
                  </el-icon>员工管理
                </el-menu-item>
              </el-sub-menu>

              <!-- 数据统计管理 -->
              <el-sub-menu index="/report">
                <template #title>
                  <el-icon>
                    <Histogram />
                  </el-icon><b>数据统计管理</b>
                </template>
                <el-menu-item index="/empReport"><el-icon>
                    <Avatar />
                  </el-icon>员工信息统计
                </el-menu-item>
                <el-menu-item index="/stuReport"><el-icon>
                    <UserFilled />
                  </el-icon>学员信息统计
                </el-menu-item>
                <el-menu-item index="/log">
                  <el-icon>
                    <Document />
                  </el-icon>日志信息统计
                </el-menu-item>
              </el-sub-menu>
            </el-menu>
          </el-scrollbar>
        </el-aside>

        <el-main>
          <!-- 路由视图组件 -->
          <router-view />
        </el-main>
      </el-container>

    </el-container>
  </div>
</template>

<style scoped>
.header {
  background-image: linear-gradient(to right, #00547d, #007fa4, #00aaa0, #00d072, #a8eb12);
}

.title {
  color: white;
  font-size: 40px;
  font-family: 楷体;
  line-height: 60px;
  font-weight: bolder;
}

.right_tool {
  float: right;
  line-height: 60px;
}

a {
  color: white;
  text-decoration: none;
}

.aside {
  width: 220px;
  border-right: 1px solid #ccc;
  height: 730px;
}
</style>
