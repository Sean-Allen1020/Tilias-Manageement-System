<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { getEmpJobDataApi, getEmpGenderDataApi } from '@/api/report.js'
// 引入Echarts组件
import * as echarts from 'echarts'

const jobChartRef = ref(null)
const genderChartRef = ref(null)
let jobChart = null
let genderChart = null

// 员工职位数据
const jobList = ref([])
const dataList = ref([])
// 员工性别数据
const genderData = ref([{ name: '', value: '' }])

// 获取员工数据
const getEmpData = async () => {
  // 获取职位数据
  const jobDataRes = await getEmpJobDataApi()
  if (jobDataRes.code) {
    jobList.value = jobDataRes.data.jobList
    dataList.value = jobDataRes.data.dataList
  }
  // 获取性别数据
  const genderDataRes = await getEmpGenderDataApi()
  if(genderDataRes.code){
    genderData.value = genderDataRes.data
  }
}

onMounted(async () => {
  jobChart = echarts.init(jobChartRef.value)
  genderChart = echarts.init(genderChartRef.value)

  await getEmpData()

  // 员工职位统计图
  const jobOption = {
    title: {
      text: '员工职位统计',
      left: 'center',
      top: 10
    },
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: jobList.value
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
  jobChart.setOption(jobOption)

  // 员工性别统计图
  const genderOption = {
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
        name: '性别',
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
        data: genderData.value
      }
    ]
  }
  genderChart.setOption(genderOption)

  // 监听窗口大小变化事件，调用重置尺寸方法
  window.addEventListener('resize', handleResize)
})
// 重置尺寸方法
function handleResize() {
  if (jobChart) {
    jobChart.resize()
  }
  if (genderChart) {
    genderChart.resize()
  }
}
// 组件卸载前执行清理操作
// 1. 移除 resize 事件监听，防止无效回调
// 2. 销毁 ECharts 实例，释放内存资源
onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)

  if (jobChart) {
    jobChart.dispose()
    jobChart = null
  }

  if (genderChart) {
    genderChart.dispose()
    genderChart = null
  }
})
</script>

<template>
  <div class="chart-container">
    <!-- 员工职位信息统计 -->
    <div ref="jobChartRef" class="chart-box"></div>

    <!-- 员工性别信息统计 -->
    <div ref="genderChartRef" class="chart-box"></div>
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