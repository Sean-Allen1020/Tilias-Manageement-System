import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },

  // 代理配置
  server: {
    port: 7070, // 前端端口号
    proxy: {
      '/tilias': {   // 代理路径(唯一)
        target: 'http://localhost:8081',  // 后端端口号
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/tilias/, '')   // 重写代理路径，防止后端接收到错误路径
      }
    }
  }
})
