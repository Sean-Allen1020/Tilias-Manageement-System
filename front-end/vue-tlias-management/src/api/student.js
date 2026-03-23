import request from '@/utils/request'

// 学员分页查询
export const queryStuApi = (name, degree, clazzId, page, pageSize)=> request.get(`/students?name=${name}&degree=${degree}&clazzId=${clazzId}&page=${page}&pageSize=${pageSize}`)