<template>
  <div class="tag-select" v-click-outside="handleClickOutside">
    <div class="selected-tags">
      <div
        v-for="tag in selectedTags"
        :key="tag.id"
        class="selected-tag"
        :style="{ backgroundColor: tag.bgColor }"
      >
        <span class="tag-dot" :style="{ backgroundColor: tag.color }"></span>
        <span class="tag-name">{{ tag.name }}</span>
        <el-icon class="tag-remove" @click.stop="removeTag(tag)">
          <Close />
        </el-icon>
      </div>
      <div
        v-if="selectedTags.length < maxTags"
        class="add-tag-btn"
        @click.stop.prevent="toggleDropdown"
      >
        <el-icon><Plus /></el-icon>
        <span>添加标签</span>
      </div>
    </div>

    <Transition name="dropdown">
      <div v-if="showDropdown && !showCreatePanel" class="tag-dropdown">
        <div class="dropdown-header">
          <span class="dropdown-title">选择标签</span>
          <span v-if="selectedTags.length > 0" class="clear-btn" @click="clearAllTags"> 清除 </span>
        </div>

        <div class="dropdown-search">
          <el-input
            v-model="searchKeyword"
            placeholder="搜索标签..."
            prefix-icon="Search"
            clearable
          />
        </div>

        <el-scrollbar class="dropdown-list">
          <div v-if="selectedTags.length > 0" class="tag-section">
            <div class="section-title">已选择标签</div>
            <div
              v-for="tag in selectedFilteredTags"
              :key="tag.id"
              class="tag-item selected"
              @click="unselectTag(tag)"
            >
              <el-icon class="check-icon"><Check /></el-icon>
              <span class="tag-dot" :style="{ backgroundColor: tag.color }"></span>
              <span class="tag-name">{{ tag.name }}</span>
            </div>
          </div>

          <div v-if="availableFilteredTags.length > 0" class="tag-section">
            <div class="section-title">可选标签</div>
            <div
              v-for="tag in availableFilteredTags"
              :key="tag.id"
              class="tag-item"
              @click="selectTag(tag)"
            >
              <span class="check-placeholder"></span>
              <span class="tag-dot" :style="{ backgroundColor: tag.color }"></span>
              <span class="tag-name">{{ tag.name }}</span>
            </div>
          </div>

          <div v-if="filteredTags.length === 0" class="empty-state">
            <span>暂无匹配的标签</span>
          </div>
        </el-scrollbar>

        <div class="dropdown-footer">
          <div class="create-btn" @click="showCreatePanel = true">
            <el-icon><Plus /></el-icon>
            <span>创建新标签</span>
          </div>
        </div>
      </div>
    </Transition>

    <Transition name="dropdown">
      <div v-if="showCreatePanel" class="create-tag-panel">
        <div class="create-panel-header">
          <span class="create-panel-title">创建新标签</span>
          <el-icon class="close-icon" @click="cancelCreate"><Close /></el-icon>
        </div>

        <div class="create-panel-content">
          <div class="create-form-item">
            <div class="create-form-label">标签名称</div>
            <el-input
              v-model="newTagName"
              placeholder="请输入标签名称"
              maxlength="10"
              show-word-limit
            />
          </div>

          <div class="create-form-item">
            <div class="create-form-label">标签颜色</div>
            <div class="color-picker">
              <div
                v-for="color in presetColors"
                :key="color"
                :class="['color-item', { active: newTagColor === color }]"
                :style="{ backgroundColor: color }"
                @click="newTagColor = color"
              >
                <el-icon v-if="newTagColor === color" class="check-icon"><Check /></el-icon>
              </div>
            </div>
          </div>
        </div>

        <div class="create-panel-footer">
          <el-button class="cancel-btn" @click="cancelCreate">取消</el-button>
          <el-button class="create-btn-primary" type="primary" @click="createTag">创建</el-button>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup>
import { ref, computed, watch, nextTick, onMounted } from 'vue';
import { Close, Plus, Check } from '@element-plus/icons-vue';

const props = defineProps({
  modelValue: {
    type: Array,
    default: () => [],
  },
  maxTags: {
    type: Number,
    default: 5,
  },
  taskType: {
    type: Number,
    default: 3,
  },
});

const emit = defineEmits(['update:modelValue', 'change']);

const showDropdown = ref(false);
const showCreatePanel = ref(false);
const searchKeyword = ref('');
const newTagName = ref('');
const newTagColor = ref('');

const presetColors = [
  'rgba(151, 149, 255, 1)',
  'rgba(248, 160, 16, 1)',
  'rgba(105, 246, 184, 1)',
  'rgba(247, 75, 109, 1)',
  'rgba(64, 158, 255, 1)',
  'rgba(103, 194, 58, 1)',
  'rgba(230, 162, 60, 1)',
  'rgba(144, 147, 153, 1)',
];

const allTags = ref([
  {
    id: 1,
    name: '年任务',
    color: 'rgba(151, 149, 255, 1)',
    bgColor: 'rgba(151, 149, 255, 0.1)',
    type: 1,
  },
  {
    id: 2,
    name: '月任务',
    color: 'rgba(248, 160, 16, 1)',
    bgColor: 'rgba(248, 160, 16, 0.1)',
    type: 2,
  },
  {
    id: 3,
    name: '日任务',
    color: 'rgba(105, 246, 184, 1)',
    bgColor: 'rgba(105, 246, 184, 0.1)',
    type: 3,
  },
  {
    id: 4,
    name: '工作',
    color: 'rgba(151, 149, 255, 1)',
    bgColor: 'rgba(151, 149, 255, 0.1)',
    type: null,
  },
  {
    id: 5,
    name: '学习',
    color: 'rgba(248, 160, 16, 1)',
    bgColor: 'rgba(248, 160, 16, 0.1)',
    type: null,
  },
  {
    id: 6,
    name: '生活',
    color: 'rgba(105, 246, 184, 1)',
    bgColor: 'rgba(105, 246, 184, 0.1)',
    type: null,
  },
  {
    id: 7,
    name: '健身',
    color: 'rgba(0, 105, 71, 1)',
    bgColor: 'rgba(0, 105, 71, 0.1)',
    type: null,
  },
  {
    id: 8,
    name: '重要',
    color: 'rgba(247, 75, 109, 1)',
    bgColor: 'rgba(247, 75, 109, 0.1)',
    type: null,
  },
]);

const selectedTags = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val),
});

const filteredTags = computed(() => {
  const keyword = searchKeyword.value.toLowerCase().trim();
  if (!keyword) return allTags.value;
  return allTags.value.filter((tag) => tag.name.toLowerCase().includes(keyword));
});

const selectedFilteredTags = computed(() => {
  return filteredTags.value.filter((tag) => selectedTags.value.some((t) => t.id === tag.id));
});

const availableFilteredTags = computed(() => {
  return filteredTags.value.filter((tag) => !selectedTags.value.some((t) => t.id === tag.id));
});

const toggleDropdown = () => {
  if (showDropdown.value) {
    showDropdown.value = false;
    showCreatePanel.value = false;
  } else {
    showDropdown.value = true;
    showCreatePanel.value = false;
  }
};

const handleClickOutside = () => {
  showDropdown.value = false;
  showCreatePanel.value = false;
};

const selectTag = (tag) => {
  if (selectedTags.value.length >= props.maxTags) return;
  selectedTags.value = [...selectedTags.value, tag];
  emit('change', selectedTags.value);
};

const unselectTag = (tag) => {
  selectedTags.value = selectedTags.value.filter((t) => t.id !== tag.id);
  emit('change', selectedTags.value);
};

const removeTag = (tag) => {
  selectedTags.value = selectedTags.value.filter((t) => t.id !== tag.id);
  emit('change', selectedTags.value);
};

const clearAllTags = () => {
  selectedTags.value = [];
  emit('change', selectedTags.value);
  showDropdown.value = false;
};

const cancelCreate = () => {
  showCreatePanel.value = false;
  showDropdown.value = true;
  newTagName.value = '';
  newTagColor.value = presetColors[0];
};

const createTag = () => {
  if (!newTagName.value.trim()) return;

  const newTag = {
    id: Date.now(),
    name: newTagName.value.trim(),
    color: newTagColor.value,
    bgColor: newTagColor.value.replace('1)', '0.1)'),
    type: null,
  };

  allTags.value.push(newTag);
  selectTag(newTag);

  showCreatePanel.value = false;
  showDropdown.value = false;
  newTagName.value = '';
  newTagColor.value = presetColors[0];
};

// 处理任务类型变化，自动选择对应标签
const handleTaskTypeChange = (taskType) => {
  // 查找与当前任务类型匹配的标签
  const typeTag = allTags.value.find((tag) => tag.type === taskType);

  // 如果找到匹配的标签
  if (typeTag) {
    // 确保selectedTags是数组
    const currentTags = Array.isArray(selectedTags.value) ? [...selectedTags.value] : [];

    // 移除所有其他类型标签（只保留通用标签）
    const filteredTags = currentTags.filter((t) => t.type === null);

    // 确保当前类型标签在数组中
    if (!filteredTags.some((t) => t.id === typeTag.id)) {
      filteredTags.unshift(typeTag);
    }

    // 更新选中的标签
    selectedTags.value = filteredTags;
    emit('change', selectedTags.value);
  }
};

// 监听任务类型变化
watch(
  () => props.taskType,
  (newType) => {
    handleTaskTypeChange(newType);
  },
  { immediate: true }
);

// 监听modelValue变化，确保与taskType保持同步
watch(
  () => props.modelValue,
  (newTags) => {
    // 只有当modelValue为空数组时，才自动选择对应标签
    if (newTags.length === 0) {
      handleTaskTypeChange(props.taskType);
    }
  },
  { immediate: true }
);

const vClickOutside = {
  mounted(el, binding) {
    el._clickOutside = (event) => {
      const addBtn = el.querySelector('.add-tag-btn');
      const dropdown = el.querySelector('.tag-dropdown');
      const createPanel = el.querySelector('.create-tag-panel');
      const isClickOnAddBtn = addBtn && (addBtn === event.target || addBtn.contains(event.target));
      const isClickInsideDropdown = dropdown && dropdown.contains(event.target);
      const isClickInsideCreatePanel = createPanel && createPanel.contains(event.target);
      if (!isClickOnAddBtn && !isClickInsideDropdown && !isClickInsideCreatePanel) {
        binding.value();
      }
    };
    document.addEventListener('click', el._clickOutside);
  },
  unmounted(el) {
    document.removeEventListener('click', el._clickOutside);
  },
};
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;

.tag-select {
  position: relative;
  width: 100%;
}

.selected-tags {
  display: flex;
  flex-wrap: wrap;
  gap: pxToRem(8);
  min-height: pxToRem(36);
}

.selected-tag {
  display: flex;
  align-items: center;
  gap: pxToRem(6);
  padding: pxToRem(6) pxToRem(10);
  border-radius: pxToRem(6);
  font-size: pxToRem(13);
  font-weight: 500;

  .tag-dot {
    width: pxToRem(6);
    height: pxToRem(6);
    border-radius: 50%;
  }

  .tag-name {
    color: rgba(77, 93, 115, 1);
  }

  .tag-remove {
    font-size: pxToRem(12);
    color: rgba(158, 174, 199, 1);
    cursor: pointer;
    margin-left: pxToRem(2);

    &:hover {
      color: rgba(247, 75, 109, 1);
    }
  }
}

.add-tag-btn {
  display: flex;
  align-items: center;
  gap: pxToRem(4);
  padding: pxToRem(6) pxToRem(12);
  border: 1px dashed rgba(158, 174, 199, 0.5);
  border-radius: pxToRem(6);
  font-size: pxToRem(13);
  color: rgba(139, 154, 181, 1);
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    border-color: rgba(74, 64, 224, 1);
    color: rgba(74, 64, 224, 1);
  }
}

.tag-dropdown {
  position: absolute;
  top: calc(100% + pxToRem(8));
  left: 0;
  right: 0;
  background: rgba(255, 255, 255, 1);
  border-radius: pxToRem(8);
  box-shadow: 0 pxToRem(4) pxToRem(16) rgba(32, 48, 68, 0.12);
  border: 1px solid rgba(220, 233, 255, 1);
  z-index: 100;
  overflow: hidden;
}

.dropdown-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: pxToRem(12) pxToRem(16);
  border-bottom: 1px solid rgba(220, 233, 255, 1);

  .dropdown-title {
    font-size: pxToRem(14);
    font-weight: 600;
    color: rgba(32, 48, 68, 1);
  }

  .clear-btn {
    font-size: pxToRem(13);
    color: rgba(74, 64, 224, 1);
    cursor: pointer;

    &:hover {
      opacity: 0.8;
    }
  }
}

.dropdown-search {
  padding: pxToRem(12) pxToRem(16);

  :deep(.el-input__wrapper) {
    background: rgba(234, 241, 255, 1);
    border-radius: pxToRem(6);
    box-shadow: none;
  }
}

.dropdown-list {
  height: pxToRem(240);
  overflow: hidden;
}

.tag-section {
  padding: pxToRem(8) pxToRem(16);

  .section-title {
    font-size: pxToRem(12);
    font-weight: 500;
    color: rgba(139, 154, 181, 1);
    margin-bottom: pxToRem(8);
  }
}

.tag-item {
  display: flex;
  align-items: center;
  gap: pxToRem(8);
  padding: pxToRem(8) pxToRem(12);
  border-radius: pxToRem(6);
  cursor: pointer;
  transition: background 0.2s ease;

  &:hover {
    background: rgba(234, 241, 255, 1);
  }

  .check-icon {
    font-size: pxToRem(14);
    color: rgba(74, 64, 224, 1);
  }

  .check-placeholder {
    width: pxToRem(14);
    height: pxToRem(14);
  }

  .tag-dot {
    width: pxToRem(8);
    height: pxToRem(8);
    border-radius: 50%;
  }

  .tag-name {
    font-size: pxToRem(14);
    color: rgba(77, 93, 115, 1);
  }
}

.empty-state {
  padding: pxToRem(24) pxToRem(16);
  text-align: center;
  font-size: pxToRem(14);
  color: rgba(139, 154, 181, 1);
}

.dropdown-footer {
  padding: pxToRem(12) pxToRem(16);
  border-top: 1px solid rgba(220, 233, 255, 1);

  .create-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: pxToRem(6);
    padding: pxToRem(10);
    border-radius: pxToRem(6);
    background: rgba(234, 241, 255, 1);
    font-size: pxToRem(14);
    color: rgba(74, 64, 224, 1);
    cursor: pointer;
    transition: all 0.3s ease;

    &:hover {
      background: rgba(220, 233, 255, 1);
    }
  }
}

.dropdown-enter-active,
.dropdown-leave-active {
  transition: all 0.2s ease;
}

.dropdown-enter-from,
.dropdown-leave-to {
  opacity: 0;
  transform: translateY(-pxToRem(8));
}

.create-tag-panel {
  position: absolute;
  top: calc(100% + pxToRem(8));
  left: 0;
  right: 0;
  background: rgba(255, 255, 255, 1);
  border-radius: pxToRem(8);
  box-shadow: 0 pxToRem(4) pxToRem(16) rgba(32, 48, 68, 0.12);
  border: 1px solid rgba(220, 233, 255, 1);
  z-index: 100;
  overflow: hidden;
}

.create-panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: pxToRem(12) pxToRem(16);
  border-bottom: 1px solid rgba(220, 233, 255, 1);

  .create-panel-title {
    font-size: pxToRem(14);
    font-weight: 600;
    color: rgba(32, 48, 68, 1);
  }

  .close-icon {
    font-size: pxToRem(16);
    color: rgba(139, 154, 181, 1);
    cursor: pointer;

    &:hover {
      color: rgba(77, 93, 115, 1);
    }
  }
}

.create-panel-content {
  padding: pxToRem(16);
}

.create-form-item {
  margin-bottom: pxToRem(16);

  &:last-child {
    margin-bottom: 0;
  }

  .create-form-label {
    font-size: pxToRem(14);
    font-weight: 500;
    color: rgba(77, 93, 115, 1);
    margin-bottom: pxToRem(8);
  }

  :deep(.el-input__wrapper) {
    background: rgba(234, 241, 255, 1);
    border-radius: pxToRem(6);
    box-shadow: none;
  }
}

.color-picker {
  display: flex;
  flex-wrap: wrap;
  gap: pxToRem(12);

  .color-item {
    width: pxToRem(32);
    height: pxToRem(32);
    border-radius: 50%;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: transform 0.2s ease;

    &:hover {
      transform: scale(1.1);
    }

    &.active {
      box-shadow:
        0 0 0 2px rgba(255, 255, 255, 1),
        0 0 0 4px currentColor;
    }

    .check-icon {
      font-size: pxToRem(16);
      color: rgba(255, 255, 255, 1);
    }
  }
}

.create-panel-footer {
  display: flex;
  justify-content: flex-end;
  gap: pxToRem(12);
  padding: pxToRem(12) pxToRem(16);
  border-top: 1px solid rgba(220, 233, 255, 1);

  .cancel-btn {
    padding: pxToRem(8) pxToRem(16);
    font-size: pxToRem(14);
    border-radius: pxToRem(6);
    background: rgba(234, 241, 255, 1);
    border: none;
    color: rgba(77, 93, 115, 1);
    cursor: pointer;

    &:hover {
      background: rgba(220, 233, 255, 1);
    }
  }

  .create-btn-primary {
    padding: pxToRem(8) pxToRem(16);
    font-size: pxToRem(14);
    border-radius: pxToRem(6);
    background: rgba(74, 64, 224, 1);
    border: none;
    color: rgba(255, 255, 255, 1);
    cursor: pointer;

    &:hover {
      background: rgba(64, 54, 204, 1);
    }
  }
}
</style>
