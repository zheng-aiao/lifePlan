// 测试用例：验证 DayTaskCard 相关功能

// 测试数据
const testTasks = [
  {
    id: 1,
    title: '测试任务1',
    timeRange: '07:00-09:00', // 整点开始和结束
    status: 'in-progress',
    statusText: '进行中',
    actualDuration: '2小时',
    subTasks: [
      { text: '子任务1', completed: false },
      { text: '子任务2', completed: false },
      { text: '子任务3', completed: false }
    ],
    activities: [
      { time: '07:15', type: 'feedback', typeText: '反馈', description: '任务开始' },
      { time: '08:00', type: 'delay', typeText: '延时', description: '遇到问题' }
    ],
    borderColor: 'rgba(74, 64, 224, 1)',
    bgColor: 'rgba(255, 255, 255, 1)'
  },
  {
    id: 2,
    title: '测试任务2',
    timeRange: '09:30-11:45', // 非整点开始和结束
    status: 'in-progress',
    statusText: '进行中',
    actualDuration: '2小时15分钟',
    subTasks: [],
    activities: [],
    borderColor: 'rgba(34, 197, 94, 1)',
    bgColor: 'rgba(255, 255, 255, 1)'
  },
  {
    id: 3,
    title: '测试任务3',
    timeRange: '14:00-16:30', // 整点开始，非整点结束
    status: 'in-progress',
    statusText: '进行中',
    actualDuration: '2小时30分钟',
    subTasks: [
      { text: '子任务1', completed: true },
      { text: '子任务2', completed: false }
    ],
    activities: [
      { time: '14:30', type: 'interruption', typeText: '中断', description: '会议' }
    ],
    borderColor: 'rgba(249, 115, 22, 1)',
    bgColor: 'rgba(255, 255, 255, 1)'
  }
];

// 测试函数
function testHeightCalculation() {
  console.log('=== 测试高度计算逻辑 ===');
  
  // 模拟 getTaskBaseHeight 函数
  function getTaskBaseHeight(task) {
    const TASK_MIN_HEIGHT = 60;
    const HOUR_HEIGHT = 120;
    
    function getTaskDuration(timeRange) {
      const [start, end] = timeRange.split('-');
      const [startHour, startMin] = start.split(':').map(Number);
      const [endHour, endMin] = end.split(':').map(Number);
      const startDecimal = startHour + startMin / 60;
      const endDecimal = endHour + endMin / 60;
      let duration = endDecimal - startDecimal;
      return Math.max(0.5, Math.min(12, duration));
    }
    
    const duration = getTaskDuration(task.timeRange);
    return Math.max(TASK_MIN_HEIGHT, duration * HOUR_HEIGHT);
  }
  
  // 模拟 calculateTaskContentHeight 函数（使用修改后的逻辑）
  function calculateTaskContentHeight(task) {
    // 实际测量的高度值
    const HEADER_HEIGHT = 72; // 头部高度（包含内边距）
    const FOOTER_HEIGHT = 72; // 底部高度（包含内边距）
    const SECTION_HEADER_HEIGHT = 36; // 区域头部高度
    const SUBTASK_ITEM_HEIGHT = 32; // 子任务项高度
    const ACTIVITY_ITEM_HEIGHT = 36; // 活动项高度
    const SECTION_PADDING = 40; // 区域内边距总和（上下各16px + 底部24px）
    const SECTION_GAP = 12; // 子任务/活动项之间的间距
    const EMPTY_LIST_HEIGHT = 48; // 空列表高度（包含内边距）

    let height = HEADER_HEIGHT + FOOTER_HEIGHT;

    const subTaskCount = task.subTasks?.length || 0;
    const activityCount = task.activities?.length || 0;

    if (subTaskCount > 0 || activityCount > 0) {
      // 计算左侧子任务区域高度
      const leftSectionHeight =
        SECTION_HEADER_HEIGHT + 
        SECTION_PADDING + 
        subTaskCount * SUBTASK_ITEM_HEIGHT + 
        (subTaskCount - 1) * SECTION_GAP;
      
      // 计算右侧活动区域高度
      const rightSectionHeight =
        SECTION_HEADER_HEIGHT + 
        SECTION_PADDING + 
        activityCount * ACTIVITY_ITEM_HEIGHT + 
        (activityCount - 1) * SECTION_GAP;

      const contentHeight = Math.max(leftSectionHeight, rightSectionHeight);
      height += contentHeight;
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

    // 添加额外的安全边距，确保所有内容都能显示
    return height + 40;
  }
  
  // 模拟 getTaskActualHeight 函数
  function getTaskActualHeight(task) {
    const baseHeight = getTaskBaseHeight(task);
    const contentHeight = calculateTaskContentHeight(task);
    return contentHeight > baseHeight ? contentHeight : baseHeight;
  }
  
  // 测试每个任务的高度计算
  testTasks.forEach((task, index) => {
    const baseHeight = getTaskBaseHeight(task);
    const contentHeight = calculateTaskContentHeight(task);
    const actualHeight = getTaskActualHeight(task);
    
    console.log(`任务 ${index + 1}:`);
    console.log(`  任务标题: ${task.title}`);
    console.log(`  时间范围: ${task.timeRange}`);
    console.log(`  基础高度: ${baseHeight}px`);
    console.log(`  内容高度: ${contentHeight}px`);
    console.log(`  实际高度: ${actualHeight}px`);
    console.log(`  是否展开: ${contentHeight > baseHeight}`);
    console.log('');
  });
}

function testTimeLineDisplay() {
  console.log('=== 测试时间线显示逻辑 ===');
  
  // 模拟 parseTime 函数
  function parseTime(timeStr) {
    const [hour, min] = timeStr.split(':').map(Number);
    return { hour, min };
  }
  
  // 测试任务边界时间提取
  console.log('任务边界时间:');
  testTasks.forEach((task, index) => {
    const [start, end] = task.timeRange.split('-');
    const startParsed = parseTime(start);
    const endParsed = parseTime(end);
    
    console.log(`任务 ${index + 1} (${task.title}):`);
    console.log(`  开始时间: ${start} (${startParsed.hour}:${startParsed.min})`);
    console.log(`  结束时间: ${end} (${endParsed.hour}:${endParsed.min})`);
    console.log(`  开始时间是否为整点: ${startParsed.min === 0}`);
    console.log(`  结束时间是否为整点: ${endParsed.min === 0}`);
    console.log('');
  });
}

// 运行测试
console.log('开始测试 DayTaskCard 功能\n');
testHeightCalculation();
testTimeLineDisplay();
console.log('测试完成！');
