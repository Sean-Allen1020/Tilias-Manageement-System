import request from '@/utils/request'

// 学员分页查询
export const queryStuApi = (name, degree, clazzId, page, pageSize) => request.get(`/students?name=${name}&degree=${degree}&clazzId=${clazzId}&page=${page}&pageSize=${pageSize}`)

// 新增
export const addStuApi = (stu) => request.post('/students', stu)

// 根据id查询学生
export const queryByIdApi = (id) => request.get(`/students/${id}`)

// 修改
export const updateStuApi = (stu) => request.put('/students', stu)

// 删除
export const deleteStuApi = (ids) => request.delete(`/students/${ids}`)

// 违纪扣分
export const deductStuScoreApi = (id, score) => request.put(`/students/violation/${id}/${score}`)