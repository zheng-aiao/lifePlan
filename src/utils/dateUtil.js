/**
 * 日期时间工具函数
 */

/**
 * 获取当前日期字符串 (YYYY-MM-DD 格式)
 * @returns {string} 当前日期字符串
 */
export const getCurrentDateString = () => {
  const now = new Date();
  // 调整为中国标准时间 (UTC+8)
  const utc8 = new Date(now.getTime() + 8 * 60 * 60 * 1000);
  return utc8.toISOString().slice(0, 10);
};

/**
 * 将日期和时间组合成完整的日期时间字符串
 * @param {Date} date - 日期对象
 * @param {string} time - 时间字符串 (HH:mm 格式)
 * @returns {string} YYYY-MM-DD HH:mm:ss 格式的日期时间字符串
 */
export const combineDateAndTime = (date, time) => {
  const [hours, minutes] = time.split(':');
  const combinedDate = new Date(date);
  combinedDate.setHours(parseInt(hours, 10), parseInt(minutes, 10), 0, 0);

  // 调整为中国标准时间 (UTC+8)
  const utc8 = new Date(combinedDate.getTime() + 8 * 60 * 60 * 1000);
  const dateStr = utc8.toISOString().slice(0, 10);
  const timeStr = `${String(combinedDate.getHours()).padStart(2, '0')}:${String(combinedDate.getMinutes()).padStart(2, '0')}:${String(combinedDate.getSeconds()).padStart(2, '0')}`;

  return `${dateStr} ${timeStr}`;
};

/**
 * 格式化日期为 YYYY-MM-DD 格式
 * @param {Date} date - 要格式化的日期对象
 * @returns {string} YYYY-MM-DD 格式的日期字符串
 */
export const formatDate = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};
