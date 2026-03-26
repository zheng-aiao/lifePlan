// 年度任务
export const yearlyTasks = [
  {
    id: 1,
    title: '完成品牌设计规范指南',
    category: '工作',
    progress: 75,
    color: 'rgba(74, 64, 224, 1)',
  },
  {
    id: 2,
    title: '阅读 4 本深度学习书籍',
    category: '学习',
    progress: 25,
    color: 'rgba(248, 160, 16, 1)',
  },
  {
    id: 3,
    title: '半程马拉松训练营',
    category: '健身',
    progress: 60,
    color: 'rgba(0, 105, 71, 1)',
  },
];

export const monthlyTasks = [
  {
    id: 1,
    title: '阅读 4 本深度学习书籍',
    category: '学习',
    progress: 25,
    color: 'rgba(248, 160, 16, 1)',
  },
  {
    id: 2,
    title: '半程马拉松训练营',
    category: '健身',
    progress: 60,
    color: 'rgba(0, 105, 71, 1)',
  },
];

export const temporaryTasks = [
  {
    id: 1,
    title: '阅读 4 本深度学习书籍',
    category: '学习',
    progress: 25,
    color: 'rgba(248, 160, 16, 1)',
  },
  {
    id: 2,
    title: '半程马拉松训练营',
    category: '健身',
    progress: 60,
    color: 'rgba(0, 105, 71, 1)',
  },
];


// 每日任务列表（时间轴任务）
export const dailyTasks = [
  {
    id: 1,
    tag: 'WORK',
    tagColor: 'rgba(74, 64, 224, 1)',
    timeRange: '07:00-09:00',
    actualDuration: '1h 30min',
    title: '早间深度工作：界面排版优化',
    subTasks: [
      { text: '梳理 Chronicle 核心视觉层次', completed: true },
      { text: '更新排版网格规范文档', completed: false },
      { text: '提交初稿至内部评审小组', completed: false },
    ],
    quote: '专注力是创造力的核心：保持沉浸式设计状态。',
    borderColor: 'rgba(74, 64, 224, 1)',
    bgColor: 'rgba(255, 255, 255, 1)',
  },
  {
    id: 2,
    tag: 'MEETING',
    tagColor: 'rgba(248, 160, 16, 1)',
    timeRange: '09:00-10:30',
    actualDuration: '30min',
    title: '产品策略同步会议',
    subTasks: [
      { text: '确认 Q3 产品路线图', completed: false },
      { text: '讨论用户反馈优先级', completed: false },
    ],
    borderColor: 'rgba(248, 160, 16, 1)',
    bgColor: 'rgba(255, 250, 240, 1)',
  },
  {
    id: 3,
    tag: 'WORK',
    tagColor: 'rgba(74, 64, 224, 1)',
    timeRange: '10:30-12:00',
    actualDuration: '1h 15min',
    title: '组件库开发',
    subTasks: [
      { text: '完成 Button 组件', completed: true },
      { text: '编写单元测试', completed: true },
      { text: '更新文档', completed: false },
    ],
    borderColor: 'rgba(74, 64, 224, 1)',
    bgColor: 'rgba(255, 255, 255, 1)',
  },
  {
    id: 4,
    tag: 'REST',
    tagColor: 'rgba(0, 105, 71, 1)',
    timeRange: '12:00-13:30',
    actualDuration: '1h',
    title: '午休与恢复',
    subTasks: [{ text: '冥想 15 分钟', completed: true }],
    quote: '休息是为了更好的出发。',
    borderColor: 'rgba(0, 105, 71, 1)',
    bgColor: 'rgba(240, 255, 250, 1)',
  },
  {
    id: 5,
    tag: 'WORK',
    tagColor: 'rgba(74, 64, 224, 1)',
    timeRange: '13:30-15:30',
    actualDuration: '1h 45min',
    title: '代码审查与优化',
    subTasks: [
      { text: '审查 PR #234', completed: true },
      { text: '优化性能瓶颈', completed: false },
    ],
    borderColor: 'rgba(74, 64, 224, 1)',
    bgColor: 'rgba(255, 255, 255, 1)',
  },
  {
    id: 6,
    tag: 'REVIEW',
    tagColor: 'rgba(104, 120, 143, 1)',
    timeRange: '15:30-17:00',
    actualDuration: '45min',
    title: '设计稿评审',
    subTasks: [
      { text: '评审首页设计', completed: false },
      { text: '整理反馈意见', completed: false },
    ],
    borderColor: 'rgba(158, 174, 199, 1)',
    bgColor: 'rgba(234, 241, 255, 1)',
    opacity: 0.6,
  },
  {
    id: 7,
    tag: 'WORK',
    tagColor: 'rgba(74, 64, 224, 1)',
    timeRange: '17:00-19:00',
    actualDuration: '0min',
    title: '文档整理',
    subTasks: [
      { text: '更新 API 文档', completed: false },
      { text: '整理会议纪要', completed: false },
    ],
    borderColor: 'rgba(74, 64, 224, 1)',
    bgColor: 'rgba(255, 255, 255, 1)',
  },
  {
    id: 8,
    tag: 'REST',
    tagColor: 'rgba(0, 105, 71, 1)',
    timeRange: '19:00-20:00',
    actualDuration: '0min',
    title: '运动健身',
    subTasks: [{ text: '跑步 30 分钟', completed: false }],
    borderColor: 'rgba(0, 105, 71, 1)',
    bgColor: 'rgba(240, 255, 250, 1)',
  },
  {
    id: 9,
    tag: 'WORK',
    tagColor: 'rgba(74, 64, 224, 1)',
    timeRange: '20:00-22:00',
    actualDuration: '0min',
    title: '个人项目开发',
    subTasks: [
      { text: '学习新技术', completed: false },
      { text: '写技术博客', completed: false },
    ],
    borderColor: 'rgba(74, 64, 224, 1)',
    bgColor: 'rgba(255, 255, 255, 1)',
  },
  {
    id: 10,
    tag: 'REVIEW',
    tagColor: 'rgba(104, 120, 143, 1)',
    timeRange: '22:00-23:00',
    actualDuration: '0min',
    title: '明日计划',
    subTasks: [{ text: '整理待办事项', completed: false }],
    borderColor: 'rgba(158, 174, 199, 1)',
    bgColor: 'rgba(234, 241, 255, 1)',
    opacity: 0.6,
  },
];

// 获取指定日期的任务列表（预留接口，后续可接入后端）
export const getDailyTasksByDate = (date) => {
  // 这里可以根据日期返回不同的任务数据
  // 目前返回默认的每日任务
  return dailyTasks;
};