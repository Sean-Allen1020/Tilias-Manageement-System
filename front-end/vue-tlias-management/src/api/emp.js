import request from '@/utils/request.js'    // 创建 request.js，也就是Result类的实例

// 查询员工列表数据
export const queryPageApi = (name, gender, begin, end, page, pageSize) => {
    return request.get(`/emps?name=${name}&gender=${gender}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`)
}

// 新增
export const addEmpApi = (emp) => {
    return request.post('/emps', emp) // Json参数, 后端用 @RequestBody
}
