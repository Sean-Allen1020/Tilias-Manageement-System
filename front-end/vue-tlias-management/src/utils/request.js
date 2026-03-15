import axios from 'axios'

// 创建axios实例对象
const request = axios.create({
  baseURL: '/api',   // 前端响应基础地址
  timeout: 600000   // 响应时间
})

// axios的响应 response 拦截器
request.interceptors.response.use(
  (response) => { // 成功回调
    return response.data    // 将 data数据 封装
  },
  (error) => { // 失败回调
    return Promise.reject(error)
  }
)

// 默认导出 request 实例
export default request