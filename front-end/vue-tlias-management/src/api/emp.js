import request from '@/utils/request.js'    // 创建 request.js，也就是Result类的实例

// 查询员工列表数据
export const queryPageApi = (name, gender, begin, end, page, pageSize) => {
    return request.get(`/emps?name=${name}&gender=${gender}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`)
}

// 新增
export const addEmpApi = (emp) => {
    return request.post('/emps', emp)   // Json参数, 后端用 @RequestBody
}

// 根据id查询
export const queryInfoApi = (id) => {
    return request.get(`/emps/${id}`)   // 路径参数,注意是用反引号括的, 后端用@PathVariable
}

// 修改
export const updateEmpApi = (emp) => {
    return request.put('/emps', emp)   // Json参数, 后端用 @RequestBody
}

// 删除
export const deleteEmpApi = (ids) => {
    return request.delete(`/emps?ids=${ids}`)
}