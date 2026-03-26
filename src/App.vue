<template>
  <div class="app-container">
    <Header />
    <div class="content-container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup>
import { getCurrentInstance } from "vue";
import Header from "./components/Header.vue";

window.app = getCurrentInstance();
</script>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  width: 100%;
  min-width: 80rem;
}

.content-container {
  flex: 1;
  padding-top: var(--header-height, 4rem);
  width: 100%;
  height: calc(100vh - var(--header-height, 4rem));
  overflow: hidden;
}
</style>
