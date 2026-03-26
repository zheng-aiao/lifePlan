<template>
  <el-card
    class="day-task-card"
    :class="{ 'is-active': isActive }"
    :style="{
      '--border-color': task.borderColor,
      backgroundColor: task.bgColor,
      opacity: task.opacity || 1,
    }"
    :body-style="{ padding: isActive ? '2rem' : '1.5rem' }"
  >
    <!-- 头部信息 -->
    <div class="card-header">
      <div class="header-left">
        <span class="task-tag" :style="{ color: task.tagColor }">{{ task.tag }}</span>
        <div class="time-display">
          <span v-if="task.delayedFrom" class="time-range old-time">{{ task.delayedFrom }}</span>
          <span class="time-range" :class="{ 'new-time': task.delayedFrom }">{{ task.timeRange }}</span>
        </div>
      </div>
      <div class="header-right">
        <span class="actual-duration">已用时 {{ task.actualDuration }}</span>
        <el-button circle class="action-btn delay-btn" @click="handleDelay" title="延时">
          <el-icon><Timer /></el-icon>
        </el-button>
        <el-button circle class="action-btn feedback-btn" @click="handleFeedback" title="反馈">
          <el-icon><ChatDotRound /></el-icon>
        </el-button>
        <el-button circle class="action-btn pause-btn" @click="handlePause" title="暂停">
          <el-icon><VideoPause /></el-icon>
        </el-button>
      </div>
    </div>

    <!-- 任务标题 -->
    <p class="task-title">{{ task.title }}</p>

    <!-- 子任务列表 -->
    <div v-if="task.subTasks && task.subTasks.length" class="sub-task-list">
      <div
        v-for="(subTask, idx) in task.subTasks"
        :key="idx"
        class="sub-task-item"
        @click="toggleSubTask(idx)"
      >
        <div class="checkbox" :class="{ checked: subTask.completed }">
          <el-icon v-if="subTask.completed"><Check /></el-icon>
        </div>
        <span class="sub-task-text" :class="{ completed: subTask.completed }">
          {{ subTask.text }}
        </span>
      </div>
    </div>

    <!-- 描述/引用 -->
    <p v-if="task.quote" class="task-quote">{{ task.quote }}</p>

    <!-- 延时弹窗 -->
    <el-dialog
      v-model="delayDialogVisible"
      title="延时任务"
      width="400px"
      :close-on-click-modal="false"
    >
      <div class="delay-form">
        <p class="current-time">当前时间：{{ task.timeRange }}</p>
        <el-form :model="delayForm" label-width="80px">
          <el-form-item label="新的开始">
            <el-time-picker
              v-model="delayForm.startTime"
              format="HH:mm"
              placeholder="选择开始时间"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item label="新的结束">
            <el-time-picker
              v-model="delayForm.endTime"
              format="HH:mm"
              placeholder="选择结束时间"
              style="width: 100%"
            />
          </el-form-item>
        </el-form>
      </div>
      <template #footer>
        <el-button @click="delayDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmDelay">确认延时</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ChatDotRound, VideoPause, Check, Timer } from '@element-plus/icons-vue';

const props = defineProps({
  task: {
    type: Object,
    required: true,
    default: () => ({
      id: 0,
      tag: '',
      tagColor: '',
      timeRange: '',
      delayedFrom: '',
      actualDuration: '',
      title: '',
      subTasks: [],
      quote: '',
      borderColor: '',
      bgColor: '',
      opacity: 1,
    }),
  },
  isActive: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(['toggleSubTask', 'feedback', 'pause', 'delay']);

const delayDialogVisible = ref(false);
const delayForm = reactive({
  startTime: null,
  endTime: null,
});

const toggleSubTask = (idx) => {
  emit('toggleSubTask', idx);
};

const handleFeedback = () => {
  emit('feedback');
};

const handlePause = () => {
  emit('pause');
};

const handleDelay = () => {
  // 解析当前时间
  const [start, end] = props.task.timeRange.split('-');
  const [startHour, startMin] = start.split(':').map(Number);
  const [endHour, endMin] = end.split(':').map(Number);

  const now = new Date();
  delayForm.startTime = new Date(now.getFullYear(), now.getMonth(), now.getDate(), startHour, startMin);
  delayForm.endTime = new Date(now.getFullYear(), now.getMonth(), now.getDate(), endHour, endMin);

  delayDialogVisible.value = true;
};

const confirmDelay = () => {
  if (!delayForm.startTime || !delayForm.endTime) {
    return;
  }

  const formatTime = (date) => {
    return `${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
  };

  const newTimeRange = `${formatTime(delayForm.startTime)}-${formatTime(delayForm.endTime)}`;
  emit('delay', newTimeRange);
  delayDialogVisible.value = false;
};
</script>

<style scoped lang="scss">
.day-task-card {
  width: 100%;
  max-width: 42.75rem;
  border-radius: 1rem;
  border: none;
  border-left: 0.25rem solid var(--border-color);
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;

  &.is-active {
    transform: scale(1.02);
    box-shadow: 0 0.25rem 1rem rgba(74, 64, 224, 0.15);
  }
}

.card-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.75rem;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.task-tag {
  font-size: 0.625rem;
  font-family: 'Alibaba PuHuiTi-Regular';
  font-weight: 400;
  letter-spacing: 0.0625rem;
  line-height: 0.9375rem;
  text-transform: uppercase;
  padding: 0.25rem 0.5rem;
  background-color: rgba(74, 64, 224, 0.1);
  border-radius: 0.25rem;
}

.time-display {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.time-range {
  font-size: 0.75rem;
  font-family: 'Inter-Medium';
  font-weight: 500;
  line-height: 1rem;
  color: rgba(104, 120, 143, 1);

  &.old-time {
    text-decoration: line-through;
    color: rgba(158, 174, 199, 1);
  }

  &.new-time {
    color: rgba(74, 64, 224, 1);
    font-weight: 600;
  }
}

.header-right {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.actual-duration {
  font-size: 0.75rem;
  font-family: 'Inter-Medium';
  font-weight: 500;
  line-height: 1rem;
  color: rgba(74, 64, 224, 1);
  padding: 0.25rem 0.5rem;
  background-color: rgba(74, 64, 224, 0.1);
  border-radius: 0.25rem;
}

.action-btn {
  width: 2rem;
  height: 2rem;
  padding: 0;
  border: none;
  background-color: rgba(244, 246, 255, 1);

  &:hover {
    background-color: rgba(74, 64, 224, 0.1);
  }

  .el-icon {
    font-size: 0.875rem;
    color: rgba(74, 64, 224, 1);
  }

  &.delay-btn:hover {
    background-color: rgba(248, 160, 16, 0.1);
    .el-icon {
      color: rgba(248, 160, 16, 1);
    }
  }
}

.task-title {
  font-size: 1.25rem;
  font-family: 'Alibaba PuHuiTi-Regular';
  font-weight: 400;
  line-height: 1.75rem;
  color: rgba(32, 48, 68, 1);
  margin: 0 0 1rem 0;
}

.sub-task-list {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  margin-bottom: 1rem;
}

.sub-task-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  cursor: pointer;

  &:hover {
    .sub-task-text {
      color: rgba(74, 64, 224, 1);
    }
  }
}

.checkbox {
  width: 1.25rem;
  height: 1.25rem;
  border: 0.125rem solid rgba(158, 174, 199, 1);
  border-radius: 0.25rem;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s ease;

  &.checked {
    background-color: rgba(74, 64, 224, 1);
    border-color: rgba(74, 64, 224, 1);

    .el-icon {
      color: white;
      font-size: 0.75rem;
    }
  }
}

.sub-task-text {
  font-size: 0.875rem;
  font-family: 'Alibaba PuHuiTi-Regular';
  font-weight: 400;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  transition: all 0.2s ease;

  &.completed {
    text-decoration: line-through;
    color: rgba(158, 174, 199, 1);
  }
}

.task-quote {
  font-size: 0.75rem;
  font-family: 'Alibaba PuHuiTi-Regular';
  font-weight: 400;
  line-height: 1.25rem;
  color: rgba(158, 174, 199, 1);
  font-style: italic;
  margin: 0;
  padding-top: 0.5rem;
  border-top: 0.0625rem solid rgba(220, 233, 255, 1);
}

// 延时弹窗样式
.delay-form {
  .current-time {
    font-size: 0.875rem;
    color: rgba(104, 120, 143, 1);
    margin-bottom: 1rem;
    padding: 0.75rem;
    background-color: rgba(244, 246, 255, 1);
    border-radius: 0.5rem;
  }
}

:deep(.el-card__body) {
  display: flex;
  flex-direction: column;
}

:deep(.el-button.is-circle) {
  padding: 0.5rem;
}
</style>
