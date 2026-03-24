<template>
  <el-dialog
    v-model="dialogVisible"
    :title="title"
    width="500px"
    :before-close="handleClose"
  >
    <el-form :model="form" label-width="100px">
      <el-form-item label="任务名称" v-if="type !== 'day'">
        <el-input v-model="form.name" placeholder="请输入任务名称" />
      </el-form-item>
      <el-form-item label="任务内容" v-if="type === 'day'">
        <el-input v-model="form.content" placeholder="请输入任务内容" />
      </el-form-item>
      <el-form-item label="任务描述">
        <el-input
          v-model="form.description"
          type="textarea"
          placeholder="请输入任务描述"
          :rows="3"
        />
      </el-form-item>
      <el-form-item label="开始时间" v-if="type === 'day'">
        <el-time-picker
          v-model="form.startTime"
          format="HH:mm"
          placeholder="选择开始时间"
        />
      </el-form-item>
      <el-form-item label="结束时间" v-if="type === 'day'">
        <el-time-picker
          v-model="form.endTime"
          format="HH:mm"
          placeholder="选择结束时间"
        />
      </el-form-item>
      <el-form-item label="开始日期" v-if="type === 'month'">
        <el-date-picker
          v-model="form.startDate"
          type="date"
          placeholder="选择开始日期"
          :picker-options="dateOptions"
        />
      </el-form-item>
      <el-form-item label="结束日期" v-if="type === 'month'">
        <el-date-picker
          v-model="form.endDate"
          type="date"
          placeholder="选择结束日期"
          :picker-options="dateOptions"
        />
      </el-form-item>
      <el-form-item label="开始月份" v-if="type === 'year'">
        <el-select v-model="form.startMonth" placeholder="选择开始月份">
          <el-option v-for="month in 12" :key="month" :label="`${month}月`" :value="month" />
        </el-select>
      </el-form-item>
      <el-form-item label="结束月份" v-if="type === 'year'">
        <el-select v-model="form.endMonth" placeholder="选择结束月份">
          <el-option v-for="month in 12" :key="month" :label="`${month}月`" :value="month" />
        </el-select>
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
  name: 'TaskDialog',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    type: {
      type: String,
      default: 'day',
      validator: (value) => ['day', 'month', 'year'].includes(value)
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
      const titles = {
        day: '创建日任务',
        month: '创建月任务',
        year: '创建年任务'
      }
      return titles[this.type]
    },
    dateOptions() {
      if (this.type !== 'month') return {}
      const year = this.date.getFullYear()
      const month = this.date.getMonth()
      const firstDay = new Date(year, month, 1)
      const lastDay = new Date(year, month + 1, 0)
      return {
        disabledDate: (time) => {
          return time < firstDay || time > lastDay
        }
      }
    }
  },
  data() {
    return {
      form: {
        name: '',
        content: '',
        description: '',
        startTime: '',
        endTime: '',
        startDate: '',
        endDate: '',
        startMonth: '',
        endMonth: ''
      }
    }
  },
  methods: {
    handleClose() {
      this.resetForm()
      this.dialogVisible = false
    },
    handleSubmit() {
      let isValid = true
      if (this.type === 'day') {
        isValid = this.form.content && this.form.startTime && this.form.endTime
      } else {
        isValid = this.form.name && ((this.type === 'month' && this.form.startDate && this.form.endDate) || (this.type === 'year' && this.form.startMonth && this.form.endMonth))
      }
      
      if (!isValid) {
        this.$message.warning('请填写完整任务信息')
        return
      }
      
      this.$emit('submit', this.form)
      this.resetForm()
      this.dialogVisible = false
    },
    resetForm() {
      this.form = {
        name: '',
        content: '',
        description: '',
        startTime: '',
        endTime: '',
        startDate: '',
        endDate: '',
        startMonth: '',
        endMonth: ''
      }
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
</style>