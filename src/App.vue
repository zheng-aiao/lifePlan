<template>
  <div class="app-container">
    <HeaderLayout />
    <div class="content-container">
      <LeftLayout />
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup>
import { getCurrentInstance } from 'vue';
import HeaderLayout from './layout/HeaderLayout.vue';
import LeftLayout from '@/layout/LeftLayout.vue';

window.app = getCurrentInstance();
</script>

<style lang="scss">
@use '@/assets/scss/index' as *;

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
