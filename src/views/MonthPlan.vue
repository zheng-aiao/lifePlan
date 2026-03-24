<template>
  <div class="app">
    <main class="app-main">
      <!-- 任务日历视图 -->
      <el-card class="task-calendar-card">
        <template #header>
          <div class="card-header">
            <span>月任务日历</span>
            <el-date-picker
              v-model="currentDate"
              type="month"
              placeholder="选择月份"
              @change="loadTasks"
              size="small"
            />
          </div>
        </template>
        <div class="calendar-container">
          <!-- 星期标题 -->
          <div class="week-header">
            <div v-for="day in weekDays" :key="day" class="week-day">
              {{ day }}
            </div>
          </div>

          <!-- 日期网格 -->
          <div class="calendar-grid">
            <!-- 空白天数 -->
            <div
              v-for="empty in emptyDaysBefore"
              :key="`empty-before-${empty}`"
              class="calendar-day empty"
            ></div>

            <!-- 当月天数 -->
            <div
              v-for="day in daysInMonth"
              :key="day"
              class="calendar-day"
            >
              <div class="day-number">{{ day }}</div>
              <div class="add-task-btn">
                <el-button type="primary" size="small" circle @click="showTaskDialogWithDay(day)">
                  <el-icon><Plus /></el-icon>
                </el-button>
              </div>
              <div class="day-tasks">
                <div
                  v-for="(task, index) in getTasksForDay(day)"
                  :key="index"
                  class="task-card"
                >
                  <div class="task-name">{{ task.name }}</div>
                  <div v-if="task.description" class="task-description">{{ task.description }}</div>
                  <div v-if="task.feedback" class="task-feedback">
                    <div class="feedback-item">
                      <strong>完成情况:</strong> {{ task.feedback.status }}
                    </div>
                  </div>
                  <el-button
                    v-if="!task.feedback"
                    type="primary"
                    size="small"
                    @click.stop="showFeedbackDialog(task)"
                  >
                    添加反馈
                  </el-button>
                </div>
              </div>
            </div>

            <!-- 空白天数 -->
            <div
              v-for="empty in emptyDaysAfter"
              :key="`empty-after-${empty}`"
              class="calendar-day empty"
            ></div>
          </div>
        </div>
      </el-card>
    </main>

    <!-- 新建任务弹窗 -->
    <TaskDialog
      v-model:visible="taskDialogVisible"
      type="month"
      :date="currentDate"
      @submit="addTask"
    />

    <!-- 反馈对话框 -->
    <el-dialog
      v-model="feedbackDialogVisible"
      title="任务反馈"
      width="500px"
    >
      <el-form :model="feedbackForm" label-width="120px">
        <el-form-item label="完成情况">
          <el-select v-model="feedbackForm.status" placeholder="请选择完成情况">
            <el-option label="已完成" value="已完成" />
            <el-option label="部分完成" value="部分完成" />
            <el-option label="未完成" value="未完成" />
          </el-select>
        </el-form-item>
        <el-form-item label="详细说明">
          <el-input
            v-model="feedbackForm.note"
            type="textarea"
            placeholder="请输入详细说明"
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
  name: 'MonthPlan',
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
        status: '',
        note: ''
      },
      currentTask: null,
      weekDays: ['日', '一', '二', '三', '四', '五', '六']
    }
  },
  computed: {
    dateOptions() {
      const year = this.currentDate.getFullYear()
      const month = this.currentDate.getMonth()
      const firstDay = new Date(year, month, 1)
      const lastDay = new Date(year, month + 1, 0)
      return {
        disabledDate: (time) => {
          return time < firstDay || time > lastDay
        }
      }
    },
    daysInMonth() {
      const year = this.currentDate.getFullYear()
      const month = this.currentDate.getMonth()
      return new Date(year, month + 1, 0).getDate()
    },
    emptyDaysBefore() {
      const year = this.currentDate.getFullYear()
      const month = this.currentDate.getMonth()
      return new Date(year, month, 1).getDay()
    },
    emptyDaysAfter() {
      const totalDays = this.emptyDaysBefore + this.daysInMonth
      return 7 - (totalDays % 7) || 0
    }
  },
  mounted() {
    this.loadTasks()
  },
  methods: {
    loadTasks() {
      const year = this.currentDate.getFullYear()
      const month = (this.currentDate.getMonth() + 1).toString().padStart(2, '0')
      const monthStr = `${year}-${month}`
      const storedTasks = localStorage.getItem(`month_tasks_${monthStr}`)
      if (storedTasks) {
        this.tasks = JSON.parse(storedTasks)
      } else {
        this.tasks = []
      }
    },
    saveTasks() {
      const year = this.currentDate.getFullYear()
      const month = (this.currentDate.getMonth() + 1).toString().padStart(2, '0')
      const monthStr = `${year}-${month}`
      localStorage.setItem(`month_tasks_${monthStr}`, JSON.stringify(this.tasks))
    },
    showTaskDialogWithDay(day) {
      // 这里可以将日期传递给弹窗组件
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
    getTasksForDay(day) {
      const year = this.currentDate.getFullYear()
      const month = this.currentDate.getMonth()
      const currentDay = new Date(year, month, day)

      return this.tasks.filter(task => {
        const startDate = new Date(task.startDate)
        const endDate = new Date(task.endDate)
        return currentDay >= startDate && currentDay <= endDate
      })
    },
    showFeedbackDialog(task) {
      this.currentTask = task
      this.feedbackForm = {
        status: '',
        note: ''
      }
      this.feedbackDialogVisible = true
    },
    submitFeedback() {
      if (!this.feedbackForm.status) {
        this.$message.warning('请选择完成情况')
        return
      }

      this.currentTask.feedback = {
        status: this.feedbackForm.status,
        note: this.feedbackForm.note
      }

      this.saveTasks()
      this.feedbackDialogVisible = false
      this.$message.success('反馈提交成功')
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

.task-calendar-card {
  margin-bottom: 20px;
  width: 100%;
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

.calendar-container {
  width: 100%;
  height: calc(100vh - 200px);
  display: flex;
  flex-direction: column;
}

.week-header {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 8px;
  margin-bottom: 8px;
  flex-shrink: 0;
}

.week-day {
  text-align: center;
  font-weight: bold;
  padding: 12px;
  background-color: #f0f0f0;
  border-radius: 4px;
}

.calendar-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 8px;
  flex: 1;
  overflow-y: auto;
}

.calendar-day {
  position: relative;
  min-height: 150px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 15px;
  background-color: white;
  transition: all 0.3s ease;
  cursor: pointer;
}

.calendar-day:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-color: #409eff;
}

.calendar-day:hover .add-task-btn {
  opacity: 1;
  transform: translateY(0);
}

.calendar-day.empty {
  background-color: #f9f9f9;
  cursor: default;
}

.day-number {
  font-weight: bold;
  margin-bottom: 10px;
  font-size: 16px;
  color: #333;
}

.add-task-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  opacity: 0;
  transform: translateY(-10px);
  transition: all 0.3s ease;
  z-index: 1;
}

.day-tasks {
  display: flex;
  flex-direction: column;
  gap: 8px;
  max-height: 120px;
  overflow-y: auto;
  margin-top: 10px;
}

.task-card {
  background-color: #f0f9ff;
  border: 1px solid #d6ecff;
  border-radius: 4px;
  padding: 10px;
  font-size: 13px;
  transition: all 0.3s ease;
}

.task-card:hover {
  background-color: #e6f7ff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.task-name {
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
}

.task-description {
  color: #666;
  margin-bottom: 5px;
  font-size: 12px;
}

.task-feedback {
  margin-top: 8px;
  padding-top: 8px;
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
  .calendar-day {
    min-height: 120px;
  }

  .day-tasks {
    max-height: 90px;
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

  .week-header {
    gap: 5px;
  }

  .week-day {
    padding: 10px;
    font-size: 14px;
  }

  .calendar-grid {
    gap: 5px;
  }

  .calendar-day {
    min-height: 100px;
    padding: 10px;
  }

  .day-number {
    font-size: 14px;
  }

  .task-card {
    padding: 6px;
    font-size: 12px;
  }

  .day-tasks {
    max-height: 70px;
  }
}
</style>