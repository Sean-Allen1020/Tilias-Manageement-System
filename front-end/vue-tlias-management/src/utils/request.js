import axios from 'axios'
import router from '@/router'
import { ElMessage } from 'element-plus'

// 创建axios实例对象
const request = axios.create({
  baseURL: '/api',   // 前端响应基础地址
  timeout: 600000   // 响应时间
})

// axios的请求request拦截器 - 获取localStorage中的token，在请求头中添加token
request.interceptors.request.use(
  (request) => { // 成功回调
    const loginUser = JSON.parse(localStorage.getItem('loginUser'))
    if (loginUser && loginUser.token) {
      request.headers.token = loginUser.token
    }
    return request
  }
)

// axios的响应 response 拦截器
request.interceptors.response.use(
  (response) => { // 成功回调
    return response.data    // 将 data数据 封装
  },
  (error) => { // 失败回调
    if (error.response && error.response.status === 401) {
      ElMessage.error('登录过期')
      router.replace('/login')
    } else {
      ElMessage.error('接口访问异常')
    }
    return Promise.reject(error)
  }
)

// 默认导出 request 实例
export default request