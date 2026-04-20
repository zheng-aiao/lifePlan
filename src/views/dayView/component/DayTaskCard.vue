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
    <div class="card-wrapper" :class="{ 'is-expanded': isExpanded }">
      <!-- 展开态模板：完整结构（头部 + 左右两栏 + 底部） -->
      <template v-if="isExpanded">
        <div class="card-header">
          <div class="header-left">
            <div class="title-row">
              <BaseButton class="action-btn" type="task" iconBtn @click.stop="startTitleEdit" />
              <div class="title-wrapper">
                <input
                  v-if="isEditingTitle"
                  ref="titleInputRef"
                  v-model="editTitle"
                  class="task-title-input"
                  @blur="saveTitle"
                  @keyup.enter="saveTitle"
                />
                <span v-else class="task-title">{{ taskData.title }}</span>
              </div>
              <div class="meta-row">
                <BaseButton
                  class="action-btn"
                  type="clock"
                  iconBtn
                  @click.stop="showTimePicker = true"
                />
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
                @click.stop="
                  !subTask.completed || isCompletedToday(subTask) ? toggleSubTask(idx) : null
                "
                :class="{ 'not-editable': subTask.completed && !isCompletedToday(subTask) }"
              >
                <div class="checkbox" :class="{ checked: subTask.completed }">
                  <el-icon v-if="subTask.completed"><Check /></el-icon>
                </div>
                <span class="sub-task-text" :class="{ completed: subTask.completed }">
                  {{ subTask.title }}
                </span>
                <span
                  v-if="subTask.completed && !isCompletedToday(subTask) && subTask.finishTime"
                  class="finish-time-tag"
                >
                  {{ formatDateMD(subTask.finishTime) }}
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
            <BaseButton
              class="abandon-btn"
              type="cancel"
              label="放弃"
              @click.stop="handleAbandon"
            />
            <BaseButton
              class="complete-btn"
              type="confirm"
              label="完成"
              @click.stop="handleFeedback"
            />
          </div>
        </div>
      </template>

      <!-- 折叠态模板：一行三列布局 -->
      <template v-else>
        <div class="collapsed-content">
          <!-- 第一列：标题概述信息 -->
          <div class="collapsed-title-section">
            <div class="title-info">
              <span class="task-title">{{ taskData.title }}</span>
              <span class="status-tag" :class="taskData.taskStatus">{{
                taskData.statusText || '进行中'
              }}</span>
            </div>
            <div class="meta-info">
              <span class="time-range">{{ taskData.timeRange }}</span>
            </div>
          </div>

          <!-- 第二列：子任务进度信息 -->
          <div class="collapsed-progress-section">
            <div class="progress-info">
              <div class="progress-header">
                <span class="progress-desc">
                  {{ completedSubTaskCount }}/{{
                    taskData.subTasks?.length || 0
                  }}
                  子任务已完成</span
                >
                <span class="progress-percentage">{{ subTaskProgressPercentage }}%</span>
              </div>
              <div class="progress-bar">
                <span
                  class="progress-fill"
                  :style="{ width: `${subTaskProgressPercentage}%` }"
                ></span>
              </div>
            </div>
            <div class="add-btn">
              <BaseButton type="add" iconBtn @click.stop="handleAddSubTask" />
            </div>
          </div>

          <!-- 第三列：操作按钮 -->
          <div class="collapsed-actions-section">
            <div v-if="isTaskActive" class="action-buttons">
              <BaseButton
                class="action-btn"
                :type="taskData.taskStatus === 1 ? 'pause' : 'start'"
                :label="startStopButtonInfo.text"
                @click.stop="handlePause"
              />
              <BaseButton class="action-btn" type="delay" label="延时" @click.stop="handleDelay" />
              <BaseButton
                class="action-btn"
                type="cancel"
                label="放弃"
                @click.stop="handleAbandon"
              />
              <BaseButton
                class="action-btn"
                type="confirm"
                label="完成"
                @click.stop="handleFeedback"
              />
            </div>
          </div>
        </div>
      </template>
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

    <!-- 时间选择器弹窗 -->
    <TimePickerDialog
      :visible="showTimePicker"
      @close="showTimePicker = false"
      @confirm="handleTimePickerConfirm"
    />
  </el-card>
</template>

<script setup>
import { ref, computed, watch, nextTick } from 'vue';
import { ElMessage } from 'element-plus';
import BaseButton from '@/components/common/BaseButton.vue';
import DelayDialog from '../dialog/DelayDialog.vue';
import AssetDialog from '../dialog/AssetDialog.vue';
import StopDialog from '../dialog/StopDialog.vue';
import AddTaskItemDialog from '../dialog/AddTaskItemDialog.vue';
import TimePickerDialog from '../dialog/TimePickerDialog.vue';
import { mapTaskStatusText } from '@/emun/constant';
import bizService from '@/utils/bizService';
import { formatDateMD } from '@/utils/dateUtil';

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

// 标题编辑相关
const isEditingTitle = ref(false);
const editTitle = ref('');
const titleInputRef = ref(null);

// 时间选择器相关
const showTimePicker = ref(false);

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

// 卡片的展开状态
const isExpanded = computed(() => {
  // 只有手动选中（isActive）的卡片才展开，其他均为折叠状态
  return props.isActive;
});

// 计算已完成的子任务数量
const completedSubTaskCount = computed(() => {
  if (!taskData.value.subTasks || taskData.value.subTasks.length === 0) return 0;
  return taskData.value.subTasks.filter((subTask) => subTask.completed).length;
});

// 计算子任务进度百分比
const subTaskProgressPercentage = computed(() => {
  const total = taskData.value.subTasks?.length || 0;
  if (total === 0) return 0;
  return Math.round((completedSubTaskCount.value / total) * 100);
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

// 开始编辑标题
const startTitleEdit = () => {
  editTitle.value = taskData.value.title;
  isEditingTitle.value = true;
  nextTick(() => {
    if (titleInputRef.value) {
      titleInputRef.value.focus();
      titleInputRef.value.select();
    }
  });
};

// 保存标题
const saveTitle = async () => {
  const newTitle = editTitle.value.trim();
  if (!newTitle) {
    ElMessage.warning('标题不能为空');
    editTitle.value = taskData.value.title;
    return;
  }
  if (newTitle === taskData.value.title) {
    isEditingTitle.value = false;
    return;
  }
  try {
    await bizService.task.updateTask(taskData.value.id, { title: newTitle });
    taskData.value.title = newTitle;
    ElMessage.success('标题修改成功');
  } catch (error) {
    console.error('修改标题失败:', error);
    ElMessage.error('修改标题失败');
    editTitle.value = taskData.value.title;
  }
  isEditingTitle.value = false;
};

// 时间选择器确认处理
const handleTimePickerConfirm = async (timeRange) => {
  const [startTime, endTime] = timeRange;
  try {
    await bizService.task.updateTask(taskData.value.id, {
      plannedStartTime: `${new Date().toISOString().split('T')[0]}T${startTime}:00`,
      plannedEndTime: `${new Date().toISOString().split('T')[0]}T${endTime}:00`,
    });
    taskData.value.timeRange = `${startTime}-${endTime}`;
    ElMessage.success('时间修改成功');
  } catch (error) {
    console.error('修改时间失败:', error);
    ElMessage.error('修改时间失败');
  }
  showTimePicker.value = false;
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
            ...subTask,
            completed: subTask.status === 1,
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

// 检查子任务是否在当天完成
const isCompletedToday = (subTask) => {
  if (!subTask.finishTime) return false;
  const finishDate = new Date(subTask.finishTime);
  if (isNaN(finishDate.getTime())) return false; // 无效日期
  const today = new Date();
  // 重置时间部分，只比较日期
  finishDate.setHours(0, 0, 0, 0);
  today.setHours(0, 0, 0, 0);
  return finishDate.getTime() === today.getTime();
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
  height: pxToRem(120);
  transition: height 0.3s cubic-bezier(0.4, 0, 0.2, 1);

  &.is-expanded {
    height: pxToRem(300);
  }
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

      .title-wrapper {
        display: flex;
        align-items: center;

        .task-title {
          font-size: pxToRem(20);
          font-family: 'Alibaba PuHuiTi-Medium';
          font-weight: 500;
          line-height: pxToRem(28);
          color: rgba(32, 48, 68, 1);
          cursor: text;
        }

        .task-title-input {
          font-size: pxToRem(20);
          font-family: 'Alibaba PuHuiTi-Medium';
          font-weight: 500;
          line-height: pxToRem(28);
          color: rgba(32, 48, 68, 1);
          border: pxToRem(2) solid rgba(74, 64, 224, 1);
          border-radius: pxToRem(6);
          padding: pxToRem(4) pxToRem(12);
          outline: none;
          background: rgba(240, 242, 255, 1);
          min-width: pxToRem(120);
          max-width: pxToRem(300);
        }
      }
      .meta-row {
        display: flex;
        align-items: center;
        gap: pxToRem(8);

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

        &.not-editable {
          cursor: not-allowed;

          &:hover {
            .sub-task-text {
              color: rgba(148, 163, 184, 1);
            }
          }
        }

        &:hover:not(.not-editable) {
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

        .finish-time-tag {
          font-size: pxToRem(12);
          font-family: 'Inter-Medium';
          font-weight: 500;
          line-height: pxToRem(16);
          color: rgba(100, 116, 139, 1);
          padding: pxToRem(2) pxToRem(8);
          border-radius: pxToRem(9999);
          background-color: rgba(241, 245, 249, 1);
          margin-left: auto;
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

          &.start {
            background-color: rgba(34, 197, 94, 1);
          }

          &.pause {
            background-color: rgba(234, 179, 8, 1);
          }

          &.resume {
            background-color: rgba(34, 197, 94, 1);
          }

          &.complete {
            background-color: rgba(34, 197, 94, 1);
          }

          &.abandon {
            background-color: rgba(239, 68, 68, 1);
          }

          &.delay {
            background-color: rgba(234, 179, 8, 1);
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

.collapsed-content {
  display: flex;
  flex-direction: row;
  height: 100%;
  padding: pxToRem(12) pxToRem(24);
  gap: pxToRem(24);
  align-items: center;

  // 第一列：标题概述信息
  .collapsed-title-section {
    flex: 2;
    min-width: 0;
    display: flex;
    flex-direction: column;

    .title-info {
      display: flex;
      align-items: center;
      gap: pxToRem(12);
      margin-bottom: pxToRem(4);

      .task-title {
        font-size: var(--fontSize-1);
        font-weight: var(--fontWeight-1);
        line-height: var(--fontLineHeight-1);
        color: var(--textColor-1);
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

    .meta-info {
      display: flex;
      align-items: center;
      gap: pxToRem(8);

      .time-range {
        font-size: pxToRem(12);
        font-family: 'Inter-Medium';
        font-weight: 500;
        line-height: pxToRem(16);
        color: rgba(100, 116, 139, 1);
      }
    }
  }

  // 第二列：子任务进度信息
  .collapsed-progress-section {
    flex: 2;
    min-width: 0;
    display: flex;
    align-items: center;
    gap: pxToRem(20);
    border-right: pxToRem(2) solid rgba(100, 116, 139, 0.3);

    .progress-info {
      flex: 1;
      display: flex;
      flex-direction: column;
      gap: pxToRem(12);

      .progress-header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        width: 100%;

        .progress-desc {
          font-size: pxToRem(12);
          font-weight: 500;
          line-height: pxToRem(16);
        }

        .progress-percentage {
          font-size: pxToRem(12);
          font-weight: 500;
          line-height: pxToRem(16);
          color: rgba(74, 64, 224, 1);
        }
      }

      .progress-bar {
        width: 100%;
        height: pxToRem(8);
        background-color: rgba(220, 233, 255, 1);
        border-radius: pxToRem(9999);
        overflow: hidden;

        .progress-fill {
          height: 100%;
          background: linear-gradient(90deg, rgba(74, 64, 224, 1) 0%, rgba(151, 149, 255, 1) 100%);
          border-radius: pxToRem(9999);
          transition: width 0.3s ease;
        }
      }
    }
    .add-btn {
      padding-right: pxToRem(20);
    }
  }

  // 第三列：操作按钮
  .collapsed-actions-section {
    flex: 3;
    min-width: 0;
    align-items: flex-end;

    .action-buttons {
      display: flex;
      align-items: center;
      justify-content: space-around;
      gap: pxToRem(4);
    }
  }
}
</style>
