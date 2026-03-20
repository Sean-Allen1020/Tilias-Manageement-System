<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { getClazzDataApi, getDegreeDataApi } from '@/api/report.js'
// 引入Echarts组件
import * as echarts from 'echarts'

const studentChartRef = ref(null)
const degreeChartRef = ref(null)
let studentChart = null
let degreeChart = null

// 班级人数数据
const clazzList = ref([])
const dataList = ref([])
// 员工性别数据
const degreeData = ref([{ name: '', value: '' }])

// 获取员工数据
const getStudentData = async () => {
  // 获取职位数据
  const clazzDataRes = await getClazzDataApi()
  if (clazzDataRes.code) {
    clazzList.value = clazzDataRes.data.clazzList
    dataList.value = clazzDataRes.data.dataList
  }
  // 获取性别数据
  const degreeDataRes = await getDegreeDataApi()
  if(degreeDataRes.code){
    degreeData.value = degreeDataRes.data
  }
}

onMounted(async () => {
  studentChart = echarts.init(studentChartRef.value)
  degreeChart = echarts.init(degreeChartRef.value)

  await getStudentData()

  // 班级人数统计
  const studentOption = {
    title: {
      text: '班级人数统计',
      left: 'center',
      top: 10
    },
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: clazzList.value
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: dataList.value,
        type: 'bar',
        barWidth: 60,
        itemStyle: {
          color: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              { offset: 0, color: '#e9bc67' },
              { offset: 1, color: '#ea7ccc' }
            ],
            global: false
          }
        }
      }
    ]
  }
  studentChart.setOption(studentOption)

  // 学位人数统计
  const degreeOption = {
    title: {
      text: '员工职位统计',
      left: 'center',
      top: 10
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '5%',
      left: 'center',
      top: 50
    },
    series: [
      {
        name: '学历',
        type: 'pie',
        radius: ['40%', '70%'],
        center: ['50%', '58%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 20,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: degreeData.value
      }
    ]
  }
  degreeChart.setOption(degreeOption)

  // 监听窗口大小变化事件，调用重置尺寸方法
  window.addEventListener('resize', handleResize)
})
// 重置尺寸方法
function handleResize() {
  if (studentChart) {
    studentChart.resize()
  }
  if (degreeChart) {
    degreeChart.resize()
  }
}
// 组件卸载前执行清理操作
// 1. 移除 resize 事件监听，防止无效回调
// 2. 销毁 ECharts 实例，释放内存资源
onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)

  if (studentChart) {
    studentChart.dispose()
    studentChart = null
  }

  if (degreeChart) {
    degreeChart.dispose()
    degreeChart = null
  }
})
</script>

<template>
  <div class="chart-container">
    <!-- 班级人数统计 -->
    <div ref="studentChartRef" class="chart-box"></div>

    <!-- 学位人数统计 -->
    <div ref="degreeChartRef" class="chart-box"></div>
  </div>
</template>

<style scoped>
.chart-container {
  display: flex;
  gap: 20px;
}

.chart-box {
  flex: 1;
  height: 400px;
}
</style>