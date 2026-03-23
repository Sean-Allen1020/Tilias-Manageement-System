import request from '@/utils/request.js'

// 查询
export const queryClazzApi = (name, begin, end, page, pageSize) => request.get(`/clazzs?name=${name}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`)

// 查询所有班级
export const getAllClazzApi = ()=> request.get('/clazzs/list')

// 新增
export const addClazzApi = (clazz) => request.post('/clazzs', clazz)

// 根据id查询班级
export const queryByIdApi = (id) => request.get(`/clazzs/${id}`)

// 更新
export const updateClazzApi = (clazz) => request.put('/clazzs', clazz)

// 删除
export const deleteClazzApi = (id) => request.delete(`/clazzs/${id}`)