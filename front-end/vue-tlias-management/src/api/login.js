import request from '@/utils/request.js'    // 创建 request.js，也就是Result类的实例

// 登录
export const loginApi = (loginForm) => {
    return request.post('/login', loginForm)
}