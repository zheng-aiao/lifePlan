<template>
  <el-card
    class="day-task-card"
    :class="{ 'is-active': isActive }"
    :style="{
      '--border-color': task.borderColor,
      backgroundColor: task.bgColor,
      opacity: task.opacity || 1,
    }"
    :body-style="{ padding: '0.75rem', position: 'relative' }"
  >
    <!-- 头部信息 -->
    <div class="card-header">
      <div class="header-left">
        <span class="task-tag" :style="{ color: task.tagColor }">{{ task.tag }}</span>
        <div class="time-display">
          <span v-if="task.delayedFrom" class="time-range old-time">{{ task.delayedFrom }}</span>
          <span class="time-range" :class="{ 'new-time': task.delayedFrom }">{{
            task.timeRange
          }}</span>
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
    <div class="task-title-container">
      <p class="task-title">{{ task.title }}</p>
      <el-button circle class="add-subtask-btn" @click.stop="handleAddSubTask" title="添加子任务">
        <el-icon><Plus /></el-icon>
      </el-button>
    </div>

    <!-- 子任务列表 - 默认全部展开 -->
    <div v-if="task.subTasks && task.subTasks.length" class="sub-task-list">
      <div
        v-for="(subTask, idx) in task.subTasks"
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
    </div>

    <!-- 描述/引用 -->
    <p v-if="task.quote" class="task-quote">{{ task.quote }}</p>

    <!-- 拖动手柄 -->
    <div class="resize-handle" @mousedown="startResize" title="拖动调整任务时长"></div>

    <!-- 延时弹窗 -->
    <delay-dialog
      v-model="delayDialogVisible"
      :current-time-range="task.timeRange"
      :task-title="task.title"
      @confirm="handleDelayConfirm"
      @cancel="handleDelayCancel"
    />
  </el-card>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
import { ChatDotRound, VideoPause, Check, Timer, Plus } from '@element-plus/icons-vue';
import DelayDialog from '../dialog/DelayDialog.vue';

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
  otherTasks: {
    type: Array,
    default: () => [],
  },
});

const emit = defineEmits([
  'toggleSubTask',
  'feedback',
  'pause',
  'delay',
  'heightChange',
  'resize',
  'addSubTask',
]);

// 常量
const SUBTASK_ITEM_HEIGHT = 26; // 每个子任务项的高度
const HEADER_HEIGHT = 50; // 头部区域高度
const TITLE_HEIGHT = 28; // 标题高度
const QUOTE_HEIGHT = 24; // 引用文字高度
const PADDING = 24; // 上下padding总和
const MIN_TASK_DURATION = 30; // 最小任务时长（分钟）
const PIXELS_PER_MINUTE = 2; // 每分钟对应的像素高度

// 延时弹窗显示状态
const delayDialogVisible = ref(false);

// 拖动相关数据
const isResizing = ref(false);
const startY = ref(0);
const startHeight = ref(0);

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

// 当前实际高度（始终使用内容高度，由父组件根据内容高度决定是否覆盖时长高度）
const currentHeight = computed(() => {
  return contentHeight.value;
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
  delayDialogVisible.value = true;
};

const handleDelayConfirm = ({ timeRange, reason }) => {
  emit('delay', timeRange, reason);
  delayDialogVisible.value = false;
};

const handleDelayCancel = () => {
  delayDialogVisible.value = false;
};

// 开始拖动调整大小
const startResize = (e) => {
  e.preventDefault();
  console.log('开始拖动');
  isResizing.value = true;
  startY.value = e.clientY;
  startHeight.value = currentHeight.value;

  // 添加全局鼠标事件监听器
  document.addEventListener('mousemove', onMouseMove);
  document.addEventListener('mouseup', onMouseUp);
};

// 检查时间冲突
const checkTimeConflict = (newStart, newEnd) => {
  // 解析当前任务的开始时间
  const [startHour, startMin] = newStart.split(':').map(Number);
  const startTimestamp = startHour * 60 + startMin;

  // 解析当前任务的新结束时间
  const [endHour, endMin] = newEnd.split(':').map(Number);
  const endTimestamp = endHour * 60 + endMin;

  // 检查与其他任务的冲突
  for (const otherTask of props.otherTasks) {
    if (otherTask.id === props.task.id) continue; // 跳过自己

    const [otherStart, otherEnd] = otherTask.timeRange.split('-');
    const [otherStartHour, otherStartMin] = otherStart.split(':').map(Number);
    const otherStartTimestamp = otherStartHour * 60 + otherStartMin;

    const [otherEndHour, otherEndMin] = otherEnd.split(':').map(Number);
    const otherEndTimestamp = otherEndHour * 60 + otherEndMin;

    // 检查时间重叠
    if (startTimestamp < otherEndTimestamp && endTimestamp > otherStartTimestamp) {
      return true; // 冲突
    }
  }

  return false; // 无冲突
};

// 拖动过程
const onMouseMove = (e) => {
  if (!isResizing.value) return;

  console.log('拖动中');
  const deltaY = e.clientY - startY.value;
  const newHeight = Math.max(startHeight.value + deltaY, MIN_TASK_DURATION * PIXELS_PER_MINUTE);

  // 计算新的时长（分钟）
  const newDuration = Math.round(newHeight / PIXELS_PER_MINUTE);

  // 计算新的结束时间
  const [start, end] = props.task.timeRange.split('-');
  const [startHour, startMin] = start.split(':').map(Number);
  const startDate = new Date();
  startDate.setHours(startHour, startMin, 0, 0);

  const endDate = new Date(startDate.getTime() + newDuration * 60 * 1000);
  const endHour = endDate.getHours().toString().padStart(2, '0');
  const endMin = endDate.getMinutes().toString().padStart(2, '0');
  const newEndTime = `${endHour}:${endMin}`;

  // 检查时间冲突
  if (!checkTimeConflict(start, newEndTime)) {
    const newTimeRange = `${start}-${newEndTime}`;
    console.log('新的时间范围:', newTimeRange);
    emit('resize', newTimeRange, newHeight);
  }
};

// 结束拖动
const onMouseUp = () => {
  console.log('结束拖动');
  isResizing.value = false;
  // 移除全局鼠标事件监听器
  document.removeEventListener('mousemove', onMouseMove);
  document.removeEventListener('mouseup', onMouseUp);
};

// 处理添加子任务
const handleAddSubTask = () => {
  emit('addSubTask');
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
  position: relative;

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

.task-title-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 0.375rem;
  flex-shrink: 0;
}

.task-title {
  font-size: 0.9375rem;
  font-family: 'Alibaba PuHuiTi-Regular';
  font-weight: 400;
  line-height: 1.375rem;
  color: rgba(32, 48, 68, 1);
  margin: 0;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-right: 0.5rem;
}

.add-subtask-btn {
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

/* 拖动手柄样式 */
.resize-handle {
  width: 100%;
  height: 12px;
  position: absolute;
  bottom: 0;
  left: 0;
  cursor: ns-resize;
  background: rgba(74, 64, 224, 0.2);
  transition: all 0.2s ease;
  border-radius: 0 0 0.5rem 0.5rem;
  z-index: 10;

  &:hover {
    background: rgba(74, 64, 224, 0.3);
  }

  &:active {
    background: rgba(74, 64, 224, 0.4);
  }
}
</style>
