<template>
  <el-card
    class="day-task-card"
    :class="{ 'is-active': isActive }"
    :style="{
      '--border-color': taskData.borderColor,
      backgroundColor: taskData.bgColor,
      opacity: taskData.opacity || 1,
    }"
    :body-style="{ padding: 0, position: 'relative' }"
  >
    <div class="card-wrapper">
      <div class="card-header">
        <div class="header-left">
          <div class="title-row">
            <BaseButton class="action-btn" type="task" iconBtn />
            <span class="task-title">{{ taskData.title }}</span>
            <div class="meta-row">
              <BaseButton class="action-btn" type="clock" iconBtn />
              <span class="time-range">{{ taskData.timeRange }}</span>
              <span class="status-tag" :class="taskData.taskStatus">{{
                taskData.statusText || '进行中'
              }}</span>
            </div>
          </div>
        </div>
        <div class="header-right" v-if="isTaskActive">
          <BaseButton class="action-btn" type="add" label="新增" @click.stop="handleAddSubTask" />
          <BaseButton
            class="action-btn"
            :type="taskData.taskStatus === 1 ? 'pause' : 'start'"
            :label="startStopButtonInfo.text"
            @click.stop="handlePause"
          />
          <BaseButton class="action-btn" type="delay" label="延时" @click.stop="handleDelay" />
        </div>
      </div>

      <div class="card-content">
        <div class="left-section">
          <div class="section-header">
            <BaseButton class="action-btn" type="taskProcess" iconBtn />
            <span>任务清单</span>
          </div>
          <div class="sub-task-list">
            <div
              v-for="(subTask, idx) in taskData.subTasks"
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
            <div v-if="!taskData.subTasks || !taskData.subTasks.length" class="empty-list">
              暂无子任务
            </div>
          </div>
        </div>

        <div class="right-section">
          <div class="section-header">
            <BaseButton class="action-btn" type="active" iconBtn />
            <span>活动日志</span>
          </div>
          <div class="activity-list">
            <div v-for="(activity, idx) in taskData.activities" :key="idx" class="activity-item">
              <div class="activity-indicator" :class="activity.type"></div>
              <div class="activity-content">
                <div class="activity-time">{{ activity.time }} - {{ activity.typeText }}</div>
                <div class="activity-desc">{{ activity.description }}</div>
              </div>
            </div>
            <div v-if="!taskData.activities || !taskData.activities.length" class="empty-list">
              暂无活动日志
            </div>
          </div>
        </div>
      </div>

      <div class="card-footer">
        <div class="duration-info">
          <span class="duration-label">已用时:</span>
          <span class="duration-value">{{ taskData.actualDuration }}</span>
        </div>
        <div v-if="isTaskActive" class="footer-buttons">
          <BaseButton class="abandon-btn" type="cancel" label="放弃" @click.stop="handleAbandon" />
          <BaseButton
            class="complete-btn"
            type="confirm"
            label="完成"
            @click.stop="handleFeedback"
          />
        </div>
      </div>
    </div>

    <div class="resize-handle" @mousedown="startResize" title="拖动调整任务时长"></div>

    <delay-dialog
      v-model="delayDialogVisible"
      :current-time-range="taskData.timeRange"
      :task-title="taskData.title"
      @confirm="handleDelayConfirm"
      @cancel="handleDelayCancel"
    />

    <asset-dialog
      v-model="feedbackDialogVisible"
      :task-title="taskData.title"
      :time-range="taskData.timeRange"
      :actual-duration="taskData.actualDuration"
      @confirm="handleFeedbackConfirm"
      @cancel="handleFeedbackCancel"
    />

    <stop-dialog
      v-model="stopDialogVisible"
      :task-title="taskData.title"
      @confirm="handleStopConfirm"
      @cancel="handleStopCancel"
    />

    <stop-dialog
      v-model="abandonDialogVisible"
      :task-title="taskData.title"
      title="放弃任务"
      @confirm="handleAbandonConfirm"
      @cancel="handleAbandonCancel"
    />

    <add-task-item-dialog
      v-model="addTaskItemDialogVisible"
      :task-title="taskData.title"
      :sub-task-group="taskData.subTaskGroup"
      @confirm="handleAddTaskItemConfirm"
      @cancel="handleAddTaskItemCancel"
    />
  </el-card>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { ElMessage } from 'element-plus';
import BaseButton from '@/components/common/BaseButton.vue';
import DelayDialog from '../dialog/DelayDialog.vue';
import AssetDialog from '../dialog/AssetDialog.vue';
import StopDialog from '../dialog/StopDialog.vue';
import AddTaskItemDialog from '../dialog/AddTaskItemDialog.vue';
import { mapTaskStatusText } from '@/emun/constant';
import bizService from '@/utils/bizService';

const props = defineProps({
  task: {
    type: Object,
    required: true,
    default: () => ({
      id: 0,
      title: '',
      timeRange: '',
      taskStatus: 0,
      statusText: '进行中',
      actualDuration: '',
      subTasks: [],
      activities: [],
      borderColor: 'rgba(74, 64, 224, 1)',
      bgColor: 'rgba(255, 255, 255, 1)',
      opacity: 1,
    }),
  },
  isActive: {
    type: Boolean,
    default: false,
  },
  otherTasks: {
    type: Array,
    default: () => [],
  },
});

const emit = defineEmits(['toggleSubTask', 'feedback', 'pause', 'delay', 'resize', 'abandon']);

const MIN_TASK_DURATION = 30;
const PIXELS_PER_MINUTE = 2;

const delayDialogVisible = ref(false);
const feedbackDialogVisible = ref(false);
const stopDialogVisible = ref(false);
const addTaskItemDialogVisible = ref(false);
const abandonDialogVisible = ref(false);
const isResizing = ref(false);
const startY = ref(0);
const startHeight = ref(0);

// 使用ref包装任务数据，确保响应式更新
const taskData = ref({ ...props.task });

// 监听props.task的变化，更新taskData
watch(
  () => props.task,
  (newTask) => {
    taskData.value = { ...newTask };
  },
  { deep: true }
);

// 判断任务是否处于活动状态（待开始、进行中、暂停中）
const isTaskActive = computed(() => {
  const status = taskData.value.taskStatus;
  return status !== 3 && status !== 4;
});

// 根据任务状态计算启停按钮的显示文本
const startStopButtonInfo = computed(() => {
  const status = taskData.value.taskStatus;
  switch (status) {
    case 0: // 待开始
      return { text: '开启' };
    case 1: // 进行中
      return { text: '暂停' };
    case 2: // 暂停中
      return { text: '恢复' };
    default:
      return { text: '启停' };
  }
});

// 注意：子任务和活动日志数据由父组件提供，组件内不再主动加载

// 计算任务的基础高度（由时长决定）
const getTaskBaseHeight = () => {
  const TASK_MIN_HEIGHT = 60; // 半小时任务的最小高度（像素）
  const HOUR_HEIGHT = TASK_MIN_HEIGHT * 2; // 1 小时的高度（120px）

  // 从时间范围计算时长
  const [start, end] = taskData.value.timeRange.split('-');
  const [startHour, startMinute] = start.split(':').map(Number);
  const [endHour, endMinute] = end.split(':').map(Number);

  // 计算时长（小时）
  const duration = endHour + endMinute / 60 - (startHour + startMinute / 60);

  // 限制在 0.5 - 12 小时之间
  const clampedDuration = Math.max(0.5, Math.min(12, duration));

  return Math.max(TASK_MIN_HEIGHT, clampedDuration * HOUR_HEIGHT);
};

// 计算任务内容完全展开所需的高度
const calculateTaskContentHeight = () => {
  const HEADER_HEIGHT = 60; // 头部高度（包含内边距）
  const FOOTER_HEIGHT = 72; // 底部高度（包含内边距）
  const SECTION_HEADER_HEIGHT = 36; // 区域头部高度
  const SUBTASK_ITEM_HEIGHT = 32; // 子任务项高度
  const ACTIVITY_ITEM_HEIGHT = 36; // 活动项高度
  const CONTENT_PADDING = 24; // 内容区域上下内边距总和
  const SECTION_PADDING = 40; // 区域内边距总和（上下各16px + 底部24px）
  const SECTION_GAP = 12; // 子任务/活动项之间的间距
  const EMPTY_LIST_HEIGHT = 48; // 空列表高度（包含内边距）

  let height = HEADER_HEIGHT + FOOTER_HEIGHT;

  const subTaskCount = taskData.value.subTasks?.length || 0;
  const activityCount = taskData.value.activities?.length || 0;

  if (subTaskCount > 0 || activityCount > 0) {
    const leftSectionHeight =
      SECTION_HEADER_HEIGHT +
      SECTION_PADDING +
      subTaskCount * SUBTASK_ITEM_HEIGHT +
      (subTaskCount - 1) * SECTION_GAP;
    const rightSectionHeight =
      SECTION_HEADER_HEIGHT +
      SECTION_PADDING +
      activityCount * ACTIVITY_ITEM_HEIGHT +
      (activityCount - 1) * SECTION_GAP;

    const contentHeight = Math.max(leftSectionHeight, rightSectionHeight);
    height += contentHeight + CONTENT_PADDING;
  } else {
    // 当内容为空时，计算空内容区域的高度
    const emptyContentHeight =
      SECTION_HEADER_HEIGHT + // 区域头部高度
      SECTION_PADDING + // 区域内边距
      EMPTY_LIST_HEIGHT; // 空列表高度

    // 两个区域（左侧和右侧）
    const contentHeight = emptyContentHeight * 2;
    height += contentHeight;
  }

  return height + 40; // 添加安全边距
};

// 获取任务的实际高度（根据时间高度和内容高度的关系决定）
const currentHeight = computed(() => {
  const baseHeight = getTaskBaseHeight();
  const contentHeight = calculateTaskContentHeight();
  const TASK_MIN_HEIGHT = 60; // 半小时任务的最小高度（像素）

  // 按照文档要求：
  // 时间高度 > 内容高度：由内容高度决定，内容高度最小为刻度间隔的一半（半小时）
  // 内容高度 > 时间高度：由时间高度决定，时间刻度最小为刻度间隔的一半（半小时）
  if (baseHeight > contentHeight) {
    return Math.max(contentHeight, TASK_MIN_HEIGHT); // 内容高度至少为半小时
  }
  return Math.max(baseHeight, TASK_MIN_HEIGHT); // 时间高度至少为半小时
});

const hasContent = computed(() => {
  return (
    (taskData.value.subTasks && taskData.value.subTasks.length > 0) ||
    (taskData.value.activities && taskData.value.activities.length > 0)
  );
});

const toggleSubTask = async (idx) => {
  try {
    const subTask = taskData.value.subTasks[idx];
    if (subTask.completed) {
      await bizService.subTask.uncompleteSubTask(subTask.id);
    } else {
      await bizService.subTask.completeSubTask(subTask.id);
    }
    ElMessage.success('操作成功');
    // 刷新任务数据
    await loadTaskDetails();
  } catch (error) {
    console.error('切换子任务状态失败:', error);
  }
};

const handleFeedback = () => {
  feedbackDialogVisible.value = true;
};

const handleFeedbackConfirm = async (feedbackData) => {
  try {
    await bizService.task.completeTask(taskData.value.id, feedbackData.feedback);
    ElMessage.success('反馈提交成功');
    feedbackDialogVisible.value = false;
    // 刷新任务数据
    await loadTaskDetails();
  } catch (error) {
    console.error('提交反馈失败:', error);
  }
};

const handleFeedbackCancel = () => {
  feedbackDialogVisible.value = false;
};

const handlePause = async () => {
  const status = taskData.value.taskStatus;
  if (status === 1) {
    // 进行中，点击暂停
    stopDialogVisible.value = true;
  } else if (status === 0) {
    // 待开始，点击开启
    try {
      await bizService.task.startTask(taskData.value.id);
      ElMessage.success('任务已开启');
      // 刷新任务数据
      await loadTaskDetails();
    } catch (error) {
      console.error('开启任务失败:', error);
    }
  } else if (status === 2) {
    // 暂停中，点击恢复
    try {
      await bizService.task.resumeTask(taskData.value.id);
      ElMessage.success('任务已恢复');
      // 刷新任务数据
      await loadTaskDetails();
    } catch (error) {
      console.error('恢复任务失败:', error);
    }
  }
};

const handleStopConfirm = async (stopData) => {
  try {
    await bizService.task.pauseTask(taskData.value.id, stopData.reason);
    ElMessage.success('任务已暂停');
    stopDialogVisible.value = false;
    // 刷新任务数据
    await loadTaskDetails();
  } catch (error) {
    console.error('暂停任务失败:', error);
  }
};

const handleStopCancel = () => {
  stopDialogVisible.value = false;
};

const handleAbandon = () => {
  abandonDialogVisible.value = true;
};

const handleAbandonConfirm = async (abandonData) => {
  try {
    await bizService.task.abandonTask(taskData.value.id, abandonData.reason);
    ElMessage.success('任务已放弃');
    abandonDialogVisible.value = false;
    // 刷新任务数据
    await loadTaskDetails();
  } catch (error) {
    console.error('放弃任务失败:', error);
  }
};

const handleAbandonCancel = () => {
  abandonDialogVisible.value = false;
};

const handleDelay = () => {
  delayDialogVisible.value = true;
};

const handleDelayConfirm = async ({ timeRange, reason }) => {
  try {
    await bizService.task.delayTask(taskData.value.id, reason);
    ElMessage.success('任务已延时');
    delayDialogVisible.value = false;
    // 刷新任务数据
    await loadTaskDetails();
  } catch (error) {
    console.error('延时任务失败:', error);
  }
};

const handleDelayCancel = () => {
  delayDialogVisible.value = false;
};

const startResize = (e) => {
  e.preventDefault();
  isResizing.value = true;
  startY.value = e.clientY;
  startHeight.value = currentHeight.value;
  document.addEventListener('mousemove', onMouseMove);
  document.addEventListener('mouseup', onMouseUp);
};

const checkTimeConflict = (newStart, newEnd) => {
  const [startHour, startMin] = newStart.split(':').map(Number);
  const startTimestamp = startHour * 60 + startMin;
  const [endHour, endMin] = newEnd.split(':').map(Number);
  const endTimestamp = endHour * 60 + endMin;

  for (const otherTask of props.otherTasks) {
    if (otherTask.id === taskData.value.id) continue;
    const [otherStart, otherEnd] = otherTask.timeRange.split('-');
    const [otherStartHour, otherStartMin] = otherStart.split(':').map(Number);
    const otherStartTimestamp = otherStartHour * 60 + otherStartMin;
    const [otherEndHour, otherEndMin] = otherEnd.split(':').map(Number);
    const otherEndTimestamp = otherEndHour * 60 + otherEndMin;
    if (startTimestamp < otherEndTimestamp && endTimestamp > otherStartTimestamp) {
      return true;
    }
  }
  return false;
};

const onMouseMove = (e) => {
  if (!isResizing.value) return;
  const deltaY = e.clientY - startY.value;
  const newHeight = Math.max(startHeight.value + deltaY, MIN_TASK_DURATION * PIXELS_PER_MINUTE);
  const newDuration = Math.round(newHeight / PIXELS_PER_MINUTE);
  const [start] = taskData.value.timeRange.split('-');
  const [startHour, startMin] = start.split(':').map(Number);
  const startDate = new Date();
  startDate.setHours(startHour, startMin, 0, 0);
  const endDate = new Date(startDate.getTime() + newDuration * 60 * 1000);
  const endHour = endDate.getHours().toString().padStart(2, '0');
  const endMin = endDate.getMinutes().toString().padStart(2, '0');
  const newEndTime = `${endHour}:${endMin}`;
  if (!checkTimeConflict(start, newEndTime)) {
    const newTimeRange = `${start}-${newEndTime}`;
    emit('resize', newTimeRange, newHeight);
  }
};

const onMouseUp = () => {
  isResizing.value = false;
  document.removeEventListener('mousemove', onMouseMove);
  document.removeEventListener('mouseup', onMouseUp);
};

const handleAddSubTask = () => {
  addTaskItemDialogVisible.value = true;
};

// 获取任务详情
const loadTaskDetails = async () => {
  try {
    const response = await bizService.task.getTaskDetailsById(taskData.value.id);
    if (response.data) {
      const taskResponse = response.data;
      // 更新任务的子任务列表
      const subTasks = taskResponse.subTasks
        ? taskResponse.subTasks.map((subTask) => ({
            id: subTask.id,
            text: subTask.title,
            completed: subTask.status === 1,
            taskId: subTask.taskId,
            subTaskGroup: subTask.subTaskGroup,
            sortOrder: subTask.sortOrder,
            createdAt: subTask.createdAt,
            updatedAt: subTask.updatedAt,
          }))
        : [];

      // 更新任务的活动日志列表
      const typeMap = {
        1: { type: 'start', typeText: '开始任务' },
        2: { type: 'pause', typeText: '暂停任务' },
        3: { type: 'resume', typeText: '恢复任务' },
        4: { type: 'complete', typeText: '完成任务' },
        5: { type: 'abandon', typeText: '放弃任务' },
        6: { type: 'delay', typeText: '延时任务' },
      };

      const activities = taskResponse.activities
        ? taskResponse.activities.map((activity) => {
            const typeInfo = typeMap[activity.changeType] || { type: 'info', typeText: '信息' };
            return {
              id: activity.id,
              taskId: activity.taskId,
              changeType: activity.changeType,
              type: typeInfo.type,
              typeText: typeInfo.typeText,
              time: formatTime(activity.createdAt),
              description: activity.feedbackContent || typeInfo.typeText,
              userId: activity.userId,
              createdAt: activity.createdAt,
            };
          })
        : [];

      // 更新taskData，触发组件重新渲染
      taskData.value = {
        ...taskData.value,
        ...taskResponse,
        statusText: mapTaskStatusText(taskResponse.taskStatus),
        subTasks,
        activities,
      };
    }
  } catch (error) {
    console.error('加载任务详情失败:', error);
  }
};

// 格式化时间
const formatTime = (dateTimeStr) => {
  if (!dateTimeStr) return '';
  const date = new Date(dateTimeStr);
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  return `${hours}:${minutes}`;
};

const handleAddTaskItemConfirm = async () => {
  try {
    // 刷新当前任务的数据
    await loadTaskDetails();
  } catch (error) {
    console.error('刷新任务数据失败:', error);
  } finally {
    addTaskItemDialogVisible.value = false;
  }
};

const handleAddTaskItemCancel = () => {
  addTaskItemDialogVisible.value = false;
};
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;
.day-task-card {
  width: 100%;
  height: 100%;
  border-radius: pxToRem(16);
  border: none;
  border-left: pxToRem(8) solid var(--border-color);
  box-shadow:
    0 pxToRem(20) pxToRem(25) - pxToRem(5) rgba(224, 231, 255, 0.4),
    0 pxToRem(8) pxToRem(10) - pxToRem(6) rgba(224, 231, 255, 0.4);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  overflow: visible;
  position: relative;

  &.is-active {
    box-shadow:
      0 pxToRem(20) pxToRem(25) - pxToRem(5) rgba(224, 231, 255, 0.6),
      0 pxToRem(8) pxToRem(10) - pxToRem(6) rgba(224, 231, 255, 0.6);
    z-index: 10;
  }
}

.card-wrapper {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.card-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: pxToRem(10) pxToRem(24);
  border-bottom: pxToRem(1) solid rgb(229, 234, 238);
  flex-shrink: 0;

  .header-left {
    display: flex;
    flex-direction: column;
    gap: pxToRem(4);

    .title-row {
      display: flex;
      align-items: center;
      gap: pxToRem(12);

      .title-icon {
        font-size: pxToRem(16);
        color: rgba(74, 64, 224, 1);
      }

      .task-title {
        font-size: pxToRem(20);
        font-family: 'Alibaba PuHuiTi-Medium';
        font-weight: 500;
        line-height: pxToRem(28);
        color: rgba(32, 48, 68, 1);
      }
      .meta-row {
        display: flex;
        align-items: center;
        gap: pxToRem(8);

        .time-icon {
          font-size: pxToRem(13);
          color: rgba(100, 116, 139, 1);
        }

        .time-range {
          font-size: pxToRem(14);
          font-family: 'Inter-Medium';
          font-weight: 500;
          line-height: pxToRem(20);
          color: rgba(100, 116, 139, 1);
        }

        .status-tag {
          font-size: pxToRem(10);
          font-family: 'Inter-SemiBold';
          font-weight: 600;
          line-height: pxToRem(15);
          color: rgba(74, 64, 224, 1);
          padding: pxToRem(2) pxToRem(8);
          border-radius: pxToRem(9999);
          background-color: rgba(210, 228, 255, 1);

          &.completed {
            color: rgba(34, 197, 94, 1);
            background-color: rgba(220, 252, 231, 1);
          }

          &.delayed {
            color: rgba(249, 115, 22, 1);
            background-color: rgba(255, 237, 213, 1);
          }
        }
      }
    }
  }

  .header-right {
    display: flex;
    align-items: center;
    gap: pxToRem(8);

    .action-btn {
      display: flex;
      align-items: center;
      gap: pxToRem(8);
      padding: pxToRem(8) pxToRem(12);
      border-radius: pxToRem(4);
      background-color: rgba(248, 250, 252, 1);
      cursor: pointer;
      transition: all 0.2s ease;

      &:hover {
        background-color: rgba(74, 64, 224, 0.1);
      }

      .el-icon {
        font-size: pxToRem(14);
        color: rgba(71, 85, 105, 1);
      }

      span {
        font-size: pxToRem(12);
        font-family: 'Alibaba PuHuiTi-Medium';
        font-weight: 500;
        line-height: pxToRem(16);
        color: rgba(71, 85, 105, 1);
      }
    }
  }
}

.card-content {
  display: flex;
  flex-direction: row;
  flex: 1;
  padding: 0 pxToRem(24);
  overflow: hidden;

  .left-section {
    flex: 1;
    display: flex;
    flex-direction: column;
    padding: pxToRem(16) pxToRem(24) pxToRem(24);
    border-right: pxToRem(1) solid rgba(248, 250, 252, 1);
    overflow: hidden;

    .section-header {
      display: flex;
      align-items: center;
      gap: pxToRem(8);
      margin-bottom: pxToRem(16);
      flex-shrink: 0;

      .el-icon {
        font-size: pxToRem(16);
        color: rgba(74, 64, 224, 1);
      }

      span {
        font-size: pxToRem(14);
        font-family: 'Alibaba PuHuiTi-Medium';
        font-weight: 500;
        line-height: pxToRem(20);
        color: rgba(32, 48, 68, 1);
      }
    }

    .sub-task-list {
      display: flex;
      flex-direction: column;
      gap: pxToRem(12);
      flex: 1;
      overflow-y: auto;
      padding-right: pxToRem(8); /* 为滚动条预留空间 */

      /* 自定义滚动条样式 */
      &::-webkit-scrollbar {
        width: pxToRem(6);
      }

      &::-webkit-scrollbar-track {
        background: rgba(241, 245, 249, 1);
        border-radius: pxToRem(3);
      }

      &::-webkit-scrollbar-thumb {
        background: rgba(148, 163, 184, 1);
        border-radius: pxToRem(3);
      }

      &::-webkit-scrollbar-thumb:hover {
        background: rgba(100, 116, 139, 1);
      }

      .sub-task-item {
        display: flex;
        align-items: center;
        gap: pxToRem(12);
        cursor: pointer;

        &:hover {
          .sub-task-text {
            color: rgba(74, 64, 224, 1);
          }
        }

        .checkbox {
          width: pxToRem(16);
          height: pxToRem(16);
          border: pxToRem(1) solid rgba(148, 163, 184, 1);
          border-radius: pxToRem(4);
          display: flex;
          align-items: center;
          justify-content: center;
          flex-shrink: 0;
          transition: all 0.2s ease;

          &.checked {
            background-color: rgba(74, 64, 224, 1);
            border-color: rgba(74, 64, 224, 1);

            .el-icon {
              color: white;
              font-size: pxToRem(10);
            }
          }
        }

        .sub-task-text {
          font-size: pxToRem(14);
          font-family: 'Alibaba PuHuiTi-Regular';
          font-weight: 400;
          line-height: pxToRem(20);
          color: rgba(32, 48, 68, 1);
          transition: all 0.2s ease;

          &.completed {
            text-decoration: line-through;
            color: rgba(148, 163, 184, 1);
          }
        }
      }

      .empty-list {
        font-size: pxToRem(12);
        color: rgba(148, 163, 184, 1);
        text-align: center;
        padding: pxToRem(16);
      }
    }
  }

  .right-section {
    flex: 1;
    display: flex;
    flex-direction: column;
    padding: pxToRem(16) pxToRem(24) pxToRem(24);
    background-color: rgba(248, 250, 252, 0.5);
    overflow: hidden;

    .section-header {
      display: flex;
      align-items: center;
      gap: pxToRem(8);
      margin-bottom: pxToRem(16);
      flex-shrink: 0;

      .el-icon {
        font-size: pxToRem(16);
        color: rgba(74, 64, 224, 1);
      }

      span {
        font-size: pxToRem(14);
        font-family: 'Alibaba PuHuiTi-Medium';
        font-weight: 500;
        line-height: pxToRem(20);
        color: rgba(32, 48, 68, 1);
      }
    }

    .activity-list {
      display: flex;
      flex-direction: column;
      gap: pxToRem(16);
      flex: 1;
      overflow-y: auto;
      padding-right: pxToRem(8); /* 为滚动条预留空间 */

      /* 自定义滚动条样式 */
      &::-webkit-scrollbar {
        width: pxToRem(6);
      }

      &::-webkit-scrollbar-track {
        background: rgba(241, 245, 249, 1);
        border-radius: pxToRem(3);
      }

      &::-webkit-scrollbar-thumb {
        background: rgba(148, 163, 184, 1);
        border-radius: pxToRem(3);
      }

      &::-webkit-scrollbar-thumb:hover {
        background: rgba(100, 116, 139, 1);
      }

      .activity-item {
        display: flex;
        gap: pxToRem(12);

        .activity-indicator {
          width: pxToRem(8);
          height: pxToRem(8);
          border-radius: 50%;
          margin-top: pxToRem(6);
          flex-shrink: 0;

          &.interruption {
            background-color: rgba(249, 115, 22, 1);
          }

          &.delay {
            background-color: rgba(234, 179, 8, 1);
          }

          &.feedback {
            background-color: rgba(74, 64, 224, 1);
          }

          &.info {
            background-color: rgba(100, 116, 139, 1);
          }
        }

        .activity-content {
          flex: 1;

          .activity-time {
            font-size: pxToRem(12);
            font-family: 'Inter-Medium';
            font-weight: 500;
            line-height: pxToRem(16);
            color: rgba(100, 116, 139, 1);
            margin-bottom: pxToRem(2);
          }

          .activity-desc {
            font-size: pxToRem(14);
            font-family: 'Alibaba PuHuiTi-Regular';
            font-weight: 400;
            line-height: pxToRem(20);
            color: rgba(32, 48, 68, 1);
          }
        }
      }

      .empty-list {
        font-size: pxToRem(12);
        color: rgba(148, 163, 184, 1);
        text-align: center;
        padding: pxToRem(16);
      }
    }
  }
}

.card-footer {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: pxToRem(16) pxToRem(24);
  background-color: rgba(248, 250, 252, 0.8);
  border-top: pxToRem(1) solid rgb(229, 234, 238);
  flex-shrink: 0;

  .duration-info {
    display: flex;
    align-items: center;
    gap: pxToRem(8);

    .duration-label {
      font-size: pxToRem(12);
      font-family: 'Alibaba PuHuiTi-Medium';
      font-weight: 500;
      line-height: pxToRem(16);
      color: rgba(148, 163, 184, 1);
    }

    .duration-value {
      font-size: pxToRem(14);
      font-family: 'Alibaba PuHuiTi-Regular';
      font-weight: 400;
      line-height: pxToRem(20);
      color: rgba(74, 64, 224, 1);
    }
  }

  .footer-buttons {
    display: flex;
    gap: pxToRem(12);
    align-items: center;
  }

  .abandon-btn {
    display: flex;
    align-items: center;
    gap: pxToRem(6);
    padding: pxToRem(8) pxToRem(16);
    border-radius: pxToRem(8);
    background-color: rgba(248, 250, 252, 1);
    border: pxToRem(1) solid rgba(229, 234, 238, 1);
    cursor: pointer;
    transition: all 0.2s ease;

    &:hover {
      background-color: rgba(239, 68, 68, 0.1);
      border-color: rgba(239, 68, 68, 0.3);
    }

    .el-icon {
      font-size: pxToRem(14);
      color: rgba(239, 68, 68, 1);
    }

    span {
      font-size: pxToRem(12);
      font-family: 'Alibaba PuHuiTi-Medium';
      font-weight: 500;
      line-height: pxToRem(16);
      color: rgba(239, 68, 68, 1);
    }
  }

  .complete-btn {
    display: flex;
    align-items: center;
    gap: pxToRem(6);
    padding: pxToRem(8) pxToRem(16);
    border-radius: pxToRem(8);
    background: linear-gradient(135deg, rgba(74, 64, 224, 1) 0%, rgba(151, 149, 255, 1) 100%);
    cursor: pointer;
    box-shadow:
      0 pxToRem(4) pxToRem(6) - pxToRem(4) rgba(199, 210, 254, 1),
      0 pxToRem(10) pxToRem(15) - pxToRem(3) rgba(199, 210, 254, 1);
    transition: all 0.2s ease;

    &:hover {
      transform: scale(1.05);
    }

    .el-icon {
      font-size: pxToRem(14);
      color: white;
    }

    span {
      font-size: pxToRem(12);
      font-family: 'Alibaba PuHuiTi-Medium';
      font-weight: 500;
      line-height: pxToRem(16);
      color: white;
    }
  }
}

:deep(.el-card__body) {
  display: flex;
  flex-direction: column;
  height: 100%;
  box-sizing: border-box;
  overflow: visible;
  padding: 0;
}

.resize-handle {
  width: 100%;
  height: pxToRem(12);
  position: absolute;
  bottom: 0;
  left: 0;
  cursor: ns-resize;
  background: transparent;
  z-index: 10;

  &:hover {
    background: rgba(74, 64, 224, 0.1);
  }

  &:active {
    background: rgba(74, 64, 224, 0.2);
  }
}
</style>
