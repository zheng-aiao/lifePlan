<template>
  <div class="app">
    <main class="app-main">
      <!-- 任务月份视图 -->
      <el-card class="task-months-card">
        <template #header>
          <div class="card-header">
            <span>年任务月份分布</span>
            <el-date-picker
              v-model="currentDate"
              type="year"
              placeholder="选择年份"
              @change="loadTasks"
              size="small"
            />
          </div>
        </template>
        <div class="months-container">
          <div
            v-for="month in 12"
            :key="month"
            class="month-card"
          >
            <div class="month-header">
              <h3>{{ month }}月</h3>
              <div class="add-task-btn">
                <el-button type="primary" size="small" circle @click="showTaskDialogWithMonth(month)">
                  <el-icon><Plus /></el-icon>
                </el-button>
              </div>
            </div>
            <div class="month-tasks">
              <div
                v-for="(task, index) in getTasksForMonth(month)"
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
        </div>
      </el-card>
    </main>

    <!-- 新建任务弹窗 -->
    <TaskDialog
      v-model:visible="taskDialogVisible"
      type="year"
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
  name: 'YearPlan',
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
      currentTask: null
    }
  },
  mounted() {
    this.loadTasks()
  },
  methods: {
    loadTasks() {
      const year = this.currentDate.getFullYear()
      const storedTasks = localStorage.getItem(`year_tasks_${year}`)
      if (storedTasks) {
        this.tasks = JSON.parse(storedTasks)
      } else {
        this.tasks = []
      }
    },
    saveTasks() {
      const year = this.currentDate.getFullYear()
      localStorage.setItem(`year_tasks_${year}`, JSON.stringify(this.tasks))
    },
    showTaskDialogWithMonth(month) {
      // 这里可以将月份传递给弹窗组件
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
    getTasksForMonth(month) {
      return this.tasks.filter(task => {
        return month >= task.startMonth && month <= task.endMonth
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

.task-months-card {
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

.months-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 15px;
  width: 100%;
  height: calc(100vh - 200px);
  overflow-y: auto;
}

.month-card {
  position: relative;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  min-height: 220px;
  background-color: white;
  transition: all 0.3s ease;
}

.month-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
  border-color: #409eff;
}

.month-card:hover .add-task-btn {
  opacity: 1;
  transform: scale(1);
}

.month-header {
  position: relative;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 2px solid #f0f0f0;
  text-align: center;
}

.month-header h3 {
  font-size: 18px;
  color: #333;
  margin: 0;
  font-weight: bold;
}

.add-task-btn {
  position: absolute;
  top: -10px;
  right: -10px;
  opacity: 0;
  transform: scale(0.8);
  transition: all 0.3s ease;
  z-index: 1;
}

.month-tasks {
  display: flex;
  flex-direction: column;
  gap: 12px;
  max-height: 180px;
  overflow-y: auto;
}

.task-card {
  background-color: #f0f9ff;
  border: 1px solid #d6ecff;
  border-radius: 6px;
  padding: 12px;
  font-size: 14px;
  transition: all 0.3s ease;
}

.task-card:hover {
  background-color: #e6f7ff;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.task-name {
  font-weight: bold;
  margin-bottom: 6px;
  color: #333;
  font-size: 15px;
}

.task-description {
  color: #666;
  margin-bottom: 8px;
  font-size: 13px;
  line-height: 1.4;
}

.task-feedback {
  margin-top: 10px;
  padding-top: 10px;
  border-top: 1px solid #e0e0e0;
  font-size: 13px;
}

.feedback-item {
  margin-bottom: 6px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

@media (max-width: 1200px) {
  .months-container {
    grid-template-columns: repeat(3, 1fr);
  }

  .month-card {
    min-height: 220px;
  }

  .month-tasks {
    max-height: 150px;
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

  .months-container {
    grid-template-columns: repeat(2, 1fr);
    gap: 10px;
  }

  .month-card {
    min-height: 180px;
    padding: 15px;
  }

  .month-header h3 {
    font-size: 16px;
  }

  .task-card {
    padding: 10px;
    font-size: 13px;
  }

  .month-tasks {
    max-height: 120px;
  }
}

@media (max-width: 480px) {
  .months-container {
    grid-template-columns: 1fr;
  }
}
</style>