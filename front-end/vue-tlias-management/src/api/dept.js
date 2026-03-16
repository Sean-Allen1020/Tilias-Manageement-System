import request from '@/utils/request.js'    // 创建 request.js，也就是Result类的实例

// 导出列表查询结果
export const queryAllApi = () => {
    return request.get('/depts');   // request.js中的基础地址与此处的地址拼接,传给前端服务器处理后,发送给后端
}

// 新增
export const addApi = (dept) => {
    return request.post('/depts', dept) // Json参数, 后端用 @RequestBody
}

// 根据id查询部门
export const queryByIdApi = (id) => {
    return request.get(`depts/${id}`)   // 路径参数,注意是用反引号括的, 后端用@PathVariable
}

// 修改
export const updateApi = (dept) => {
    return request.put('/depts', dept)
}

// 删除
export const deleteByIdApi = (id) => {
    return request.delete(`/depts?id=${id}`)  // query参数, 后端直接用同名参数来接,或者用@RequestParam
}
