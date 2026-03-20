import request from '@/utils/request.js'    // 创建 request.js，也就是Result类的实例

// 获取员工职位数据
export const getEmpJobDataApi = () => {
    return request.get('/report/empJobData')
}

// 获取员工性别数据
export const getEmpGenderDataApi = () => {
    return request.get('/report/empGenderData')
}

// 获取班级人数数据
export const getClazzDataApi = () => {
    return request.get('/report/studentCountData')
}

// 获取学位人数数据
export const getDegreeDataApi = () => {
    return request.get('/report/studentDegreeData')
}