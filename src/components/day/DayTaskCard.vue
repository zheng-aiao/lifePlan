<template>
  <el-card
    class="day-task-card"
    :class="{ 'is-active': isActive, 'is-collapsed': !isActive && hasOverflow }"
    :style="{
      '--border-color': task.borderColor,
      backgroundColor: task.bgColor,
      opacity: task.opacity || 1,
    }"
    :body-style="{ padding: '0.75rem' }"
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
        <el-button circle class="action-btn delay-btn" @click.stop="handleDelay" title="延时">
          <el-icon><Timer /></el-icon>
        </el-button>
        <el-button circle class="action-btn feedback-btn" @click.stop="handleFeedback" title="反馈">
          <el-icon><ChatDotRound /></el-icon>
        </el-button>
        <el-button circle class="action-btn pause-btn" @click.stop="handlePause" title="暂停">
          <el-icon><VideoPause /></el-icon>
        </el-button>
      </div>
    </div>

    <!-- 任务标题 -->
    <p class="task-title">{{ task.title }}</p>

    <!-- 子任务列表 -->
    <div v-if="task.subTasks && task.subTasks.length" class="sub-task-list">
      <div
        v-for="(subTask, idx) in displayedSubTasks"
        :key="idx"
        class="sub-task-item"
        @click.stop="toggleSubTask(idx)"
      >
        <div class="checkbox" :class="{ checked: subTask.completed }">
          <el-icon v-if="subTask.completed"><Check /></el-icon>
        </div>
        <span class="sub-task-text" :class="{ completed: subTask.completed }">
          {{ subTask.text }}
        </span>
      </div>
      <!-- 省略号提示 - 当未选中且有溢出时显示 -->
      <div v-if="!isActive && hasOverflow" class="more-tasks-hint">
        <el-icon><MoreFilled /></el-icon>
        <span>还有 {{ task.subTasks.length - visibleSubTaskCount }} 项</span>
      </div>
    </div>

    <!-- 描述/引用 -->
    <p v-if="task.quote && (isActive || !hasOverflow)" class="task-quote">{{ task.quote }}</p>

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
import { ref, reactive, computed } from 'vue';
import { ChatDotRound, VideoPause, Check, Timer, MoreFilled } from '@element-plus/icons-vue';

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
  baseHeight: {
    type: Number,
    default: 60, // 默认基础高度（时长决定的高度）
  },
});

const emit = defineEmits(['toggleSubTask', 'feedback', 'pause', 'delay', 'heightChange']);

// 常量
const SUBTASK_ITEM_HEIGHT = 26; // 每个子任务项的高度
const HEADER_HEIGHT = 50; // 头部区域高度
const TITLE_HEIGHT = 28; // 标题高度
const QUOTE_HEIGHT = 24; // 引用文字高度
const PADDING = 24; // 上下padding总和

const delayDialogVisible = ref(false);
const delayForm = reactive({
  startTime: null,
  endTime: null,
});

// 计算内容完全展开所需的高度
const calculateContentHeight = () => {
  let height = PADDING + HEADER_HEIGHT + TITLE_HEIGHT;
  
  // 子任务列表高度
  if (props.task.subTasks && props.task.subTasks.length > 0) {
    height += props.task.subTasks.length * SUBTASK_ITEM_HEIGHT;
  }
  
  // 引用文字高度
  if (props.task.quote) {
    height += QUOTE_HEIGHT;
  }
  
  return height;
};

// 内容完全展开所需高度
const contentHeight = computed(() => calculateContentHeight());

// 是否有溢出（内容高度大于基础高度）
const hasOverflow = computed(() => {
  return contentHeight.value > props.baseHeight;
});

// 根据可用高度计算能显示多少个子任务
const visibleSubTaskCount = computed(() => {
  if (props.isActive) {
    return props.task.subTasks?.length || 0;
  }
  
  // 未选中时，计算在baseHeight内能显示多少个子任务
  const availableHeight = props.baseHeight - PADDING - HEADER_HEIGHT - TITLE_HEIGHT - 20; // 20是省略号提示高度
  if (availableHeight <= 0) return 0;
  
  const count = Math.floor(availableHeight / SUBTASK_ITEM_HEIGHT);
  return Math.max(0, Math.min(count, props.task.subTasks?.length || 0));
});

// 显示的子任务列表
const displayedSubTasks = computed(() => {
  if (!props.task.subTasks) return [];
  if (props.isActive) return props.task.subTasks;
  return props.task.subTasks.slice(0, visibleSubTaskCount.value);
});

// 当前实际高度
const currentHeight = computed(() => {
  if (props.isActive && hasOverflow.value) {
    // 选中且有溢出时，展开到内容高度
    return contentHeight.value;
  }
  // 否则使用基础高度（时长决定的高度）
  return props.baseHeight;
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
  height: 100%;
  border-radius: 0.5rem;
  border: none;
  border-left: 0.25rem solid var(--border-color);
  box-shadow: 0 0.0625rem 0.25rem 0 rgba(0, 0, 0, 0.08);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  overflow: hidden;

  &.is-active {
    transform: scale(1.01);
    box-shadow: 0 0.5rem 1.5rem rgba(74, 64, 224, 0.2);
    z-index: 10;
  }

  &.is-collapsed {
    .sub-task-list {
      mask-image: linear-gradient(to bottom, black 60%, transparent 100%);
      -webkit-mask-image: linear-gradient(to bottom, black 60%, transparent 100%);
    }
  }
}

.card-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.375rem;
  flex-shrink: 0;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.task-tag {
  font-size: 0.625rem;
  font-family: 'Alibaba PuHuiTi-Regular';
  font-weight: 400;
  letter-spacing: 0.0625rem;
  line-height: 0.875rem;
  text-transform: uppercase;
  padding: 0.125rem 0.375rem;
  background-color: rgba(74, 64, 224, 0.1);
  border-radius: 0.25rem;
  white-space: nowrap;
}

.time-display {
  display: flex;
  align-items: center;
  gap: 0.375rem;
}

.time-range {
  font-size: 0.6875rem;
  font-family: 'Inter-Medium';
  font-weight: 500;
  line-height: 1rem;
  color: rgba(104, 120, 143, 1);
  white-space: nowrap;

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
  gap: 0.25rem;
}

.actual-duration {
  font-size: 0.6875rem;
  font-family: 'Inter-Medium';
  font-weight: 500;
  line-height: 1rem;
  color: rgba(74, 64, 224, 1);
  padding: 0.125rem 0.375rem;
  background-color: rgba(74, 64, 224, 0.1);
  border-radius: 0.25rem;
  white-space: nowrap;
}

.action-btn {
  width: 1.5rem;
  height: 1.5rem;
  padding: 0;
  border: none;
  background-color: rgba(244, 246, 255, 1);
  flex-shrink: 0;

  &:hover {
    background-color: rgba(74, 64, 224, 0.1);
  }

  .el-icon {
    font-size: 0.75rem;
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
  font-size: 0.9375rem;
  font-family: 'Alibaba PuHuiTi-Regular';
  font-weight: 400;
  line-height: 1.375rem;
  color: rgba(32, 48, 68, 1);
  margin: 0 0 0.375rem 0;
  flex-shrink: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.sub-task-list {
  display: flex;
  flex-direction: column;
  gap: 0.125rem;
  margin-bottom: 0.375rem;
  overflow: hidden;
}

.sub-task-item {
  display: flex;
  align-items: center;
  gap: 0.375rem;
  cursor: pointer;
  height: 24px;
  flex-shrink: 0;

  &:hover {
    .sub-task-text {
      color: rgba(74, 64, 224, 1);
    }
  }
}

.checkbox {
  width: 0.875rem;
  height: 0.875rem;
  border: 0.125rem solid rgba(158, 174, 199, 1);
  border-radius: 0.25rem;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s ease;
  flex-shrink: 0;

  &.checked {
    background-color: rgba(74, 64, 224, 1);
    border-color: rgba(74, 64, 224, 1);

    .el-icon {
      color: white;
      font-size: 0.625rem;
    }
  }
}

.sub-task-text {
  font-size: 0.8125rem;
  font-family: 'Alibaba PuHuiTi-Regular';
  font-weight: 400;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  transition: all 0.2s ease;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;

  &.completed {
    text-decoration: line-through;
    color: rgba(158, 174, 199, 1);
  }
}

.more-tasks-hint {
  display: flex;
  align-items: center;
  gap: 0.25rem;
  height: 18px;
  color: rgba(158, 174, 199, 1);
  font-size: 0.6875rem;
  margin-top: 0.125rem;

  .el-icon {
    font-size: 0.75rem;
  }
}

.task-quote {
  font-size: 0.6875rem;
  font-family: 'Alibaba PuHuiTi-Regular';
  font-weight: 400;
  line-height: 1rem;
  color: rgba(158, 174, 199, 1);
  font-style: italic;
  margin: 0;
  padding-top: 0.25rem;
  border-top: 0.0625rem solid rgba(220, 233, 255, 1);
  flex-shrink: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
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
  height: 100%;
  box-sizing: border-box;
  overflow: hidden;
}

:deep(.el-button.is-circle) {
  padding: 0.25rem;
}
</style>
