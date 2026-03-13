import { createApp } from 'vue'
import App from './App.vue'

// vue-router
import router from './router'   

// ElementPlus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'

// ElementPlus-icon
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// 全局css
import './assets/main.css'

const app = createApp(App)

app.use(router)
app.use(ElementPlus, {locale: zhCn})
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.mount('#app')
