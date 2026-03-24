<template>
  <div class="app">
    <main class="app-main">
      <!-- 任务时间线 -->
      <el-card class="task-timeline-card">
        <template #header>
          <div class="card-header">
            <span>任务时间线</span>
            <el-date-picker
              v-model="currentDate"
              type="date"
              placeholder="选择日期"
              @change="loadTasks"
              size="small"
            />
          </div>
        </template>
        <div class="timeline-container">
          <!-- 上午 -->
          <div class="time-section">
            <div class="section-header">上午 (6:00 - 12:00)</div>
            <div class="section-content">
              <div class="time-scale">
                <div
                  v-for="hour in 6"
                  :key="hour"
                  class="time-mark"
                >
                  <span class="hour-label">{{ 5 + hour }}:00</span>
                  <div class="add-task-btn">
                    <el-button type="primary" size="small" circle @click="showTaskDialogWithTime(5 + hour, 0)">
                      <el-icon><Plus /></el-icon>
                    </el-button>
                  </div>
                </div>
              </div>
              <div class="tasks-area">
                <div
                  v-for="(task, index) in morningTasks"
                  :key="index"
                  class="task-card"
                  :style="{
                    top: `${getTaskTop(task)}px`,
                    height: `${getTaskHeight(task)}px`
                  }"
                >
                  <div class="task-header">
                    <span class="task-time">{{ formatTime(task.startTime) }} - {{ formatTime(task.endTime) }}</span>
                    <el-button
                      v-if="!task.feedback"
                      type="primary"
                      size="small"
                      @click="showFeedbackDialog(task)"
                    >
                      添加反馈
                    </el-button>
                  </div>
                  <div class="task-content">{{ task.content }}</div>
                  <div v-if="task.description" class="task-description">{{ task.description }}</div>
                  <div v-if="task.feedback" class="task-feedback">
                    <div class="feedback-item">
                      <strong>实际完成情况:</strong> {{ task.feedback.actual }}
                    </div>
                    <div class="feedback-item">
                      <strong>不足分析:</strong> {{ task.feedback.analysis }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 下午 -->
          <div class="time-section">
            <div class="section-header">下午 (12:00 - 18:00)</div>
            <div class="section-content">
              <div class="time-scale">
                <div
                  v-for="hour in 6"
                  :key="hour"
                  class="time-mark"
                >
                  <span class="hour-label">{{ 11 + hour }}:00</span>
                  <div class="add-task-btn">
                    <el-button type="primary" size="small" circle @click="showTaskDialogWithTime(11 + hour, 0)">
                      <el-icon><Plus /></el-icon>
                    </el-button>
                  </div>
                </div>
              </div>
              <div class="tasks-area">
                <div
                  v-for="(task, index) in afternoonTasks"
                  :key="index"
                  class="task-card"
                  :style="{
                    top: `${getTaskTop(task)}px`,
                    height: `${getTaskHeight(task)}px`
                  }"
                >
                  <div class="task-header">
                    <span class="task-time">{{ formatTime(task.startTime) }} - {{ formatTime(task.endTime) }}</span>
                    <el-button
                      v-if="!task.feedback"
                      type="primary"
                      size="small"
                      @click="showFeedbackDialog(task)"
                    >
                      添加反馈
                    </el-button>
                  </div>
                  <div class="task-content">{{ task.content }}</div>
                  <div v-if="task.description" class="task-description">{{ task.description }}</div>
                  <div v-if="task.feedback" class="task-feedback">
                    <div class="feedback-item">
                      <strong>实际完成情况:</strong> {{ task.feedback.actual }}
                    </div>
                    <div class="feedback-item">
                      <strong>不足分析:</strong> {{ task.feedback.analysis }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 晚上 -->
          <div class="time-section">
            <div class="section-header">晚上 (18:00 - 24:00)</div>
            <div class="section-content">
              <div class="time-scale">
                <div
                  v-for="hour in 6"
                  :key="hour"
                  class="time-mark"
                >
                  <span class="hour-label">{{ 17 + hour }}:00</span>
                  <div class="add-task-btn">
                    <el-button type="primary" size="small" circle @click="showTaskDialogWithTime(17 + hour, 0)">
                      <el-icon><Plus /></el-icon>
                    </el-button>
                  </div>
                </div>
              </div>
              <div class="tasks-area">
                <div
                  v-for="(task, index) in eveningTasks"
                  :key="index"
                  class="task-card"
                  :style="{
                    top: `${getTaskTop(task)}px`,
                    height: `${getTaskHeight(task)}px`
                  }"
                >
                  <div class="task-header">
                    <span class="task-time">{{ formatTime(task.startTime) }} - {{ formatTime(task.endTime) }}</span>
                    <el-button
                      v-if="!task.feedback"
                      type="primary"
                      size="small"
                      @click="showFeedbackDialog(task)"
                    >
                      添加反馈
                    </el-button>
                  </div>
                  <div class="task-content">{{ task.content }}</div>
                  <div v-if="task.description" class="task-description">{{ task.description }}</div>
                  <div v-if="task.feedback" class="task-feedback">
                    <div class="feedback-item">
                      <strong>实际完成情况:</strong> {{ task.feedback.actual }}
                    </div>
                    <div class="feedback-item">
                      <strong>不足分析:</strong> {{ task.feedback.analysis }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-card>
    </main>

    <!-- 新建任务弹窗 -->
    <TaskDialog
      v-model:visible="taskDialogVisible"
      type="day"
      @submit="addTask"
    />

    <!-- 反馈对话框 -->
    <el-dialog
      v-model="feedbackDialogVisible"
      title="任务反馈"
      width="500px"
    >
      <el-form :model="feedbackForm" label-width="120px">
        <el-form-item label="实际完成情况">
          <el-input
            v-model="feedbackForm.actual"
            type="textarea"
            placeholder="请输入实际完成情况"
            :rows="3"
          />
        </el-form-item>
        <el-form-item label="不足分析">
          <el-input
            v-model="feedbackForm.analysis"
            type="textarea"
            placeholder="请输入不足分析"
            :rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="feedbackDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitFeedback">提交</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { Plus } from '@element-plus/icons-vue'
import TaskDialog from '../components/TaskDialog.vue'

export default {
  name: 'DayPlan',
  components: {
    TaskDialog,
    Plus
  },
  data() {
    return {
      currentDate: new Date(),
      tasks: [],
      taskDialogVisible: false,
      feedbackDialogVisible: false,
      feedbackForm: {
        actual: '',
        analysis: ''
      },
      currentTask: null
    }
  },
  computed: {
    morningTasks() {
      return this.tasks.filter(task => {
        const hour = task.startTime.getHours()
        return hour >= 6 && hour < 12
      })
    },
    afternoonTasks() {
      return this.tasks.filter(task => {
        const hour = task.startTime.getHours()
        return hour >= 12 && hour < 18
      })
    },
    eveningTasks() {
      return this.tasks.filter(task => {
        const hour = task.startTime.getHours()
        return hour >= 18 && hour < 24
      })
    }
  },
  mounted() {
    this.loadTasks()
  },
  methods: {
    loadTasks() {
      const dateStr = this.formatDate(this.currentDate)
      const storedTasks = localStorage.getItem(`day_tasks_${dateStr}`)
      if (storedTasks) {
        this.tasks = JSON.parse(storedTasks)
      } else {
        this.tasks = []
      }
    },
    saveTasks() {
      const dateStr = this.formatDate(this.currentDate)
      localStorage.setItem(`day_tasks_${dateStr}`, JSON.stringify(this.tasks))
    },
    showTaskDialogWithTime(hour, minute) {
      const startTime = new Date()
      startTime.setHours(hour, minute, 0, 0)
      const endTime = new Date()
      endTime.setHours(hour + 1, minute, 0, 0)
      
      // 这里可以将时间传递给弹窗组件
      this.taskDialogVisible = true
    },
    addTask(form) {
      const newTask = {
        ...form,
        feedback: null
      }

      this.tasks.push(newTask)
      this.saveTasks()
      this.$message.success('任务添加成功')
    },
    showFeedbackDialog(task) {
      this.currentTask = task
      this.feedbackForm = {
        actual: '',
        analysis: ''
      }
      this.feedbackDialogVisible = true
    },
    submitFeedback() {
      if (!this.feedbackForm.actual || !this.feedbackForm.analysis) {
        this.$message.warning('请填写完整反馈信息')
        return
      }

      this.currentTask.feedback = {
        actual: this.feedbackForm.actual,
        analysis: this.feedbackForm.analysis
      }

      this.saveTasks()
      this.feedbackDialogVisible = false
      this.$message.success('反馈提交成功')
    },
    getTaskTop(task) {
      const startHour = task.startTime.getHours()
      const startMinute = task.startTime.getMinutes()
      const baseHour = startHour < 12 ? 6 : (startHour < 18 ? 12 : 18)
      const offsetHour = startHour - baseHour
      return (offsetHour * 60 + startMinute) * 2 // 每分钟2像素
    },
    getTaskHeight(task) {
      const startHour = task.startTime.getHours()
      const startMinute = task.startTime.getMinutes()
      const endHour = task.endTime.getHours()
      const endMinute = task.endTime.getMinutes()
      const duration = (endHour * 60 + endMinute) - (startHour * 60 + startMinute)
      return duration * 2 // 每分钟2像素
    },
    formatTime(time) {
      if (!time) return ''
      const hour = time.getHours().toString().padStart(2, '0')
      const minute = time.getMinutes().toString().padStart(2, '0')
      return `${hour}:${minute}`
    },
    formatDate(date) {
      const year = date.getFullYear()
      const month = (date.getMonth() + 1).toString().padStart(2, '0')
      const day = date.getDate().toString().padStart(2, '0')
      return `${year}-${month}-${day}`
    }
  }
}
</script>

<style scoped>
.app {
  width: 100%;
  min-height: 100vh;
  padding: 0;
  font-family: Arial, sans-serif;
  background-color: #f5f7fa;
}

.app-main {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 0;
}

.task-timeline-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 15px 20px;
}

.card-header .el-date-picker {
  height: 36px;
}

.timeline-container {
  display: flex;
  gap: 15px;
  width: 100%;
  height: calc(100vh - 200px);
}

.time-section {
  flex: 1;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.section-header {
  background-color: #f0f9ff;
  padding: 12px;
  font-weight: bold;
  text-align: center;
  border-bottom: 1px solid #e0e0e0;
  flex-shrink: 0;
}

.section-content {
  position: relative;
  flex: 1;
  min-height: 0;
}

.time-scale {
  position: absolute;
  left: 0;
  top: 0;
  height: 100%;
  width: 80px;
  border-right: 1px solid #e0e0e0;
}

.time-mark {
  position: relative;
  height: 120px; /* 1小时 */
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 0 10px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: all 0.3s ease;
  overflow: visible;
}

.time-mark:hover {
  background-color: #f5f7fa;
}

.time-mark:hover .add-task-btn {
  opacity: 1;
  transform: translateY(-50%);
}

.hour-label {
  font-size: 12px;
  color: #666;
  z-index: 1;
  margin-left: 10px;
}

.add-task-btn {
  position: absolute;
  left: 8px;
  top: 49%;
  transform: translateY(-50%);
  opacity: 0;
  transition: opacity 0.3s ease, transform 0.3s ease;
  z-index: 2;
}

.tasks-area {
  position: absolute;
  top: 0;
  left: 80px;
  right: 0;
  height: 100%;
  padding: 0 10px;
}

.task-card {
  position: absolute;
  left: 0;
  right: 0;
  background-color: #f9f9f9;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 10px;
  margin: 0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: all 0.3s ease;
}

.task-card:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.task-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
}

.task-time {
  font-size: 12px;
  color: #666;
}

.task-content {
  font-weight: bold;
  margin-bottom: 5px;
}

.task-description {
  font-size: 12px;
  color: #666;
  margin-bottom: 5px;
}

.task-feedback {
  margin-top: 10px;
  padding-top: 10px;
  border-top: 1px solid #e0e0e0;
  font-size: 12px;
}

.feedback-item {
  margin-bottom: 5px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

@media (max-width: 1200px) {
  .timeline-container {
    flex-direction: column;
  }

  .time-section {
    width: 100%;
  }
}

@media (max-width: 768px) {
  .app {
    padding: 10px;
  }

  .date-picker-container {
    top: 70px;
    right: 10px;
  }

  .section-content {
    height: 360px; /* 减半 */
  }

  .time-scale {
    width: 60px;
  }

  .tasks-area {
    left: 60px;
  }

  .time-mark {
    height: 60px; /* 减半 */
  }

  .task-card {
    font-size: 12px;
    padding: 5px;
  }
}
</style>