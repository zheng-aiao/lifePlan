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

const handlePrev = () => {
  const newDate = new Date(currentDate.value);
  newDate.setDate(newDate.getDate() - 1);
  currentDate.value = newDate;
  emit('updateTasks', newDate);
};

const handleNext = () => {
  const newDate = new Date(currentDate.value);
  newDate.setDate(newDate.getDate() + 1);
  currentDate.value = newDate;
  emit('updateTasks', newDate);
};
</script>

<style scoped lang="scss">
.timeline-header {
  width: 100%;
  height: 3.5rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding-top: 1rem;
  margin-bottom: 0.25rem;

  .header-content {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;

    .header-text {
      font-size: 0.875rem;
      font-family: 'Alibaba PuHuiTi-Regular';
      font-weight: 400;
      letter-spacing: 0.175rem;
      line-height: 1.25rem;
      text-transform: uppercase;
      color: rgba(74, 64, 224, 1);
      margin: 0;
    }
  }

  .header-nav {
    display: flex;
    flex-direction: row;
    gap: 0.5rem;
    .nav-btn {
      width: 2.5rem;
      height: 2.5rem;
      box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
      background-color: rgba(255, 255, 255, 1);
      border: none;

      &:hover {
        background-color: rgba(244, 246, 255, 1);
      }
    }
  }
}
</style>
