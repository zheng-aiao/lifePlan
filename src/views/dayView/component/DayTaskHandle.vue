<template>
  <div class="timeline-header">
    <div class="header-content">
      <p class="header-text">{{ headerText }}</p>
    </div>
    <div class="header-nav">
      <el-button circle class="nav-btn" @click="handlePrev">
        <el-icon><ArrowLeft /></el-icon>
      </el-button>
      <el-button circle class="nav-btn" @click="handleNext">
        <el-icon><ArrowRight /></el-icon>
      </el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { ArrowLeft, ArrowRight } from '@element-plus/icons-vue';

const emit = defineEmits(['updateTasks']);

const currentDate = ref(new Date());

const headerText = computed(() => {
  const year = currentDate.value.getFullYear();
  const month = currentDate.value.getMonth() + 1;
  const day = currentDate.value.getDate();
  const weekdays = ['日', '一', '二', '三', '四', '五', '六'];
  const weekday = weekdays[currentDate.value.getDay()];
  return `${year}年${month}月${day}日 · 星期${weekday} · 今日重点`;
});

const formatDate = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

const handlePrev = () => {
  const newDate = new Date(currentDate.value);
  newDate.setDate(newDate.getDate() - 1);
  currentDate.value = newDate;
  emit('updateTasks', formatDate(newDate));
};

const handleNext = () => {
  const newDate = new Date(currentDate.value);
  newDate.setDate(newDate.getDate() + 1);
  currentDate.value = newDate;
  emit('updateTasks', formatDate(newDate));
};
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;
.timeline-header {
  width: 100%;
  height: pxToRem(56);
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: pxToRem(16) 0;
  margin-bottom: pxToRem(4);

  .header-content {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;

    .header-text {
      font-size: pxToRem(14);
      font-family: 'Alibaba PuHuiTi-Regular';
      font-weight: 400;
      letter-spacing: pxToRem(2.8);
      line-height: pxToRem(20);
      text-transform: uppercase;
      color: rgba(74, 64, 224, 1);
      margin: 0;
    }
  }

  .header-nav {
    display: flex;
    flex-direction: row;
    gap: pxToRem(8);
    .nav-btn {
      width: pxToRem(40);
      height: pxToRem(40);
      box-shadow: 0 pxToRem(1) pxToRem(2) 0 rgba(0, 0, 0, 0.05);
      background-color: rgba(255, 255, 255, 1);
      border: none;

      &:hover {
        background-color: rgba(244, 246, 255, 1);
      }
    }
  }
}
</style>
