<script setup>
//引入views/layout/index.vue命名为Layout
import Layout from "@/views/layout/index.vue";  // @ 代表 src目录
</script>

<template>
  <!-- <Layout></Layout> -->
  <!-- 引入 Layout组件，并将其写死，会导致进入网页时必然是Layout相关组件和子组件。
   在有登录等功能的网页下，会影响页面组件展示的灵活性，所以这里直接用路由视图标签 -->
  <router-view />
</template>

<style scoped></style>
