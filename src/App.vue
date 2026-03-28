<template>
  <div class="app-container">
    <Header />
    <div class="content-container">
      <AsideLeft />
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup>
import { getCurrentInstance } from "vue";
import Header from "./components/layout/Header.vue";
import AsideLeft from "@/components/layout/AsideLeft.vue";

window.app = getCurrentInstance();
</script>

<style scoped lang="scss">
@import '@/assets/styles/_mixins.scss';

.app-container {
  min-height: 100vh;
  width: 100%;
  min-width: pxToRem(1280);
}

.content-container {
  display: flex;
  width: 100%;
  height: 100vh;
  padding-top: var(--header-height, 4rem);
  overflow: hidden;
}
</style>
