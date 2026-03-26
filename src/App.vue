
<template>
  <router-view></router-view>
</template>
<script setup>
import { getCurrentInstance } from 'vue';

window.app = getCurrentInstance();
</script>
