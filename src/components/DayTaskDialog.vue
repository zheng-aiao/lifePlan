<template>
  <el-dialog
    v-model="dialogVisible"
    :title="title"
    width="500px"
    :before-close="handleClose"
  >
    <el-form :model="form" label-width="100px">
      <el-form-item label="任务标题">
        <el-input v-model="form.title" placeholder="请输入任务标题" />
      </el-form-item>
      <el-form-item label="任务项">
        <div v-for="(item, index) in form.items" :key="index" class="task-item">
          <el-input 
            v-model="form.items[index]" 
            placeholder="请输入任务项"
            class="task-input"
            :disabled="editingItems[index] !== true"
          />
          <div class="task-buttons">
            <el-button 
              type="primary" 
              size="small" 
              @click="toggleEdit(index)"
            >
              {{ editingItems[index] ? '保存' : '编辑' }}
            </el-button>
            <el-button 
              type="danger" 
              size="small" 
              @click="removeItem(index)"
            >
              删除
            </el-button>
          </div>
        </div>
        <el-button 
          type="primary" 
          size="small" 
          @click="addItem"
          v-if="form.items.length < 10"
          class="add-item-btn"
        >
          添加任务项
        </el-button>
        <div v-if="form.items.length >= 10" class="item-limit">最多添加10条任务项</div>
      </el-form-item>
      <el-form-item label="时长（小时）">
        <el-input-number 
          v-model="form.duration" 
          :min="0.5" 
          :max="6" 
          :step="0.5"
          :precision="1"
          placeholder="请输入时长"
          @change="handleDurationChange"
        />
      </el-form-item>
      <el-form-item label="开始时间">
        <el-time-picker
          v-model="form.startTime"
          format="HH:mm"
          value-format="HH:mm"
          placeholder="选择开始时间"
          @change="handleStartTimeChange"
        />
      </el-form-item>
      <el-form-item label="结束时间">
        <el-time-picker
          v-model="form.endTime"
          format="HH:mm"
          value-format="HH:mm"
          placeholder="选择结束时间"
          @change="handleEndTimeChange"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
export default {
  name: 'DayTaskDialog',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    date: {
      type: Date,
      default: () => new Date()
    }
  },
  computed: {
    dialogVisible: {
      get() {
        return this.visible
      },
      set(value) {
        this.$emit('update:visible', value)
      }
    },
    title() {
      return '创建日任务'
    }
  },
  data() {
    // 设置默认开始时间为当前时间
    const now = new Date()
    const hours = now.getHours().toString().padStart(2, '0')
    const minutes = now.getMinutes().toString().padStart(2, '0')
    const defaultStartTime = `${hours}:${minutes}`
    
    // 计算默认结束时间（开始时间 + 1小时）
    const endTime = new Date(now.getTime() + 60 * 60 * 1000)
    const endHours = endTime.getHours().toString().padStart(2, '0')
    const endMinutes = endTime.getMinutes().toString().padStart(2, '0')
    const defaultEndTime = `${endHours}:${endMinutes}`
    
    return {
      form: {
        title: '',
        items: [''],
        duration: 1,
        startTime: defaultStartTime,
        endTime: defaultEndTime
      },
      editingItems: [true] // 默认为编辑状态
    }
  },
  methods: {
    handleClose() {
      this.resetForm()
      this.dialogVisible = false
    },
    handleSubmit() {
      let isValid = true
      isValid = this.form.title && this.form.items.some(item => item.trim()) && this.form.startTime && this.form.endTime
      
      if (!isValid) {
        this.$message.warning('请填写完整任务信息')
        return
      }
      
      this.$emit('submit', this.form)
      this.resetForm()
      this.dialogVisible = false
    },
    resetForm() {
      // 重置表单时重新设置默认时间
      const now = new Date()
      const hours = now.getHours().toString().padStart(2, '0')
      const minutes = now.getMinutes().toString().padStart(2, '0')
      const defaultStartTime = `${hours}:${minutes}`
      
      const endTime = new Date(now.getTime() + 60 * 60 * 1000)
      const endHours = endTime.getHours().toString().padStart(2, '0')
      const endMinutes = endTime.getMinutes().toString().padStart(2, '0')
      const defaultEndTime = `${endHours}:${endMinutes}`
      
      this.form = {
        title: '',
        items: [''],
        duration: 1,
        startTime: defaultStartTime,
        endTime: defaultEndTime
      }
      
      this.editingItems = [true] // 重置编辑状态
    },
    addItem() {
      if (this.form.items.length < 10) {
        this.form.items.push('')
        this.editingItems.push(true) // 新添加的任务项默认为编辑状态
      }
    },
    removeItem(index) {
      if (this.form.items.length > 1) {
        this.form.items.splice(index, 1)
        this.editingItems.splice(index, 1) // 同步删除编辑状态
      }
    },
    toggleEdit(index) {
      this.editingItems[index] = !this.editingItems[index]
    },
    handleStartTimeChange() {
      // 当开始时间改变时，根据时长计算结束时间
      this.calculateEndTime()
    },
    handleEndTimeChange() {
      // 当结束时间改变时，计算时长
      this.calculateDuration()
    },
    handleDurationChange() {
      // 当时长改变时，根据开始时间计算结束时间
      this.calculateEndTime()
    },
    calculateEndTime() {
      if (!this.form.startTime) return
      
      const [startHours, startMinutes] = this.form.startTime.split(':').map(Number)
      const startDate = new Date()
      startDate.setHours(startHours, startMinutes, 0, 0)
      
      // 计算结束时间
      const endDate = new Date(startDate.getTime() + this.form.duration * 60 * 60 * 1000)
      
      // 确保结束时间不超过23:59
      if (endDate.getHours() >= 24 || (endDate.getHours() === 23 && endDate.getMinutes() > 59)) {
        this.form.endTime = '23:59'
      } else {
        const endHours = endDate.getHours().toString().padStart(2, '0')
        const endMinutes = endDate.getMinutes().toString().padStart(2, '0')
        this.form.endTime = `${endHours}:${endMinutes}`
      }
    },
    calculateDuration() {
      if (!this.form.startTime || !this.form.endTime) return
      
      const [startHours, startMinutes] = this.form.startTime.split(':').map(Number)
      const [endHours, endMinutes] = this.form.endTime.split(':').map(Number)
      
      const startDate = new Date()
      startDate.setHours(startHours, startMinutes, 0, 0)
      
      const endDate = new Date()
      endDate.setHours(endHours, endMinutes, 0, 0)
      
      // 计算时长（小时）
      const duration = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60)
      this.form.duration = Math.max(0.5, Math.min(6, Math.round(duration * 10) / 10))
    }
  }
}
</script>

<style scoped>
.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.task-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  gap: 10px;
}

.task-input {
  flex: 1;
}

.task-buttons {
  display: flex;
  gap: 8px;
  align-items: center;
}

.add-item-btn {
  margin-top: 10px;
  display: block;
  width: 100%;
}

.item-limit {
  color: #909399;
  font-size: 12px;
  margin-top: 5px;
}
</style>