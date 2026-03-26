<template>
  <div class="left-content">
    <div id="1_63" class="Pixso-frame-1_63">
      <TaskCard :title="'年度任务'" :year="'2024'" :tasks="yearlyTasks" />
      <TaskCard :title="'月度任务'" :year="'2024'" :tasks="monthlyTasks" />
      <TaskCard :title="'临时任务'" :year="'2024'" :tasks="temporaryTasks" />
    </div>
  </div>
</template>

<script setup>
import TaskCard from "@/components/common/TaskCard.vue";

const yearlyTasks = [
  {
    id: 1,
    title: "完成品牌设计规范指南",
    category: "工作",
    progress: 75,
    color: "rgba(74, 64, 224, 1)",
  },
  {
    id: 2,
    title: "阅读 4 本深度学习书籍",
    category: "学习",
    progress: 25,
    color: "rgba(248, 160, 16, 1)",
  },
  {
    id: 3,
    title: "半程马拉松训练营",
    category: "健身",
    progress: 60,
    color: "rgba(0, 105, 71, 1)",
  },
];

const monthlyTasks = [
  {
    id: 1,
    title: "阅读 4 本深度学习书籍",
    category: "学习",
    progress: 25,
    color: "rgba(248, 160, 16, 1)",
  },
  {
    id: 2,
    title: "半程马拉松训练营",
    category: "健身",
    progress: 60,
    color: "rgba(0, 105, 71, 1)",
  },
];

const temporaryTasks = [
  {
    id: 1,
    title: "阅读 4 本深度学习书籍",
    category: "学习",
    progress: 25,
    color: "rgba(248, 160, 16, 1)",
  },
  {
    id: 2,
    title: "半程马拉松训练营",
    category: "健身",
    progress: 60,
    color: "rgba(0, 105, 71, 1)",
  },
];
</script>

<style scoped>
.left-content {
  height: 100%;
  overflow: hidden;
}

.Pixso-frame-1_63 {
  width: 100%;
  height: 100%;
  position: relative;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 1.25rem 0 1rem 0;
}

/* 保持其他样式不变 */
.Pixso-frame-1_64 {
  width: 100%;
  height: 19.375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.Pixso-frame-1_65 {
  width: 100%;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 19.8019rem;
  justify-content: flex-start;
  align-items: center;
}

.Pixso-frame-1_66 {
  width: 3.8506rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_67 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0875rem;
  line-height: 1.25rem;
  text-transform: uppercase;
  color: rgba(32, 48, 68, 1);
  width: 3.8506rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_68 {
  width: 2.5975rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(210, 228, 255, 1);
}

.Pixso-paragraph-1_69 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(74, 64, 224, 1);
  width: 1.5975rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-5_18 {
  width: 100%;
  height: 17.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.625rem;
  justify-content: flex-start;
  align-items: center;
}

.Pixso-frame-1_70 {
  width: 100%;
  height: 3.75rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-1_71 {
  width: calc(100% - 2rem);
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-1_72 {
  width: 100%;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 13.25rem;
  justify-content: flex-start;
}

.Pixso-paragraph-1_73 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 8.75rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_74 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(151, 149, 255, 1);
}

.Pixso-paragraph-1_75 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(20, 0, 126, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_76 {
  width: 100%;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-1_77 {
  flex: 1;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-1_78 {
  width: 75%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 25%;
  top: 0;
  bottom: 0;
  background-color: rgba(74, 64, 224, 1);
}

.Pixso-frame-1_79 {
  width: 1.365rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_80 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.365rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-1_81 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(74, 64, 224, 1);
}

.Pixso-frame-5_19 {
  width: 100%;
  height: 3.75rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-5_20 {
  width: calc(100% - 2rem);
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-5_21 {
  width: 100%;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 13.1006rem;
  justify-content: flex-start;
}

.Pixso-paragraph-5_22 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 8.8994rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-5_23 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-paragraph-5_24 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(74, 44, 0, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-5_25 {
  width: 100%;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-5_26 {
  flex: 1;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-5_27 {
  width: 25%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 75%;
  top: 0;
  bottom: 0;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-frame-5_28 {
  width: 1.4006rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-5_29 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.4006rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-5_30 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-frame-5_31 {
  width: 100%;
  height: 3.75rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-5_32 {
  width: calc(100% - 2rem);
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-5_33 {
  width: 100%;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 15rem;
  justify-content: flex-start;
}

.Pixso-paragraph-5_34 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 7rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-5_35 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(105, 246, 184, 1);
}

.Pixso-paragraph-5_36 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(0, 90, 60, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-5_37 {
  width: 100%;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-5_38 {
  flex: 1;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-5_39 {
  width: 60%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 40%;
  top: 0;
  bottom: 0;
  background-color: rgba(0, 105, 71, 1);
}

.Pixso-frame-5_40 {
  width: 1.4406rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-5_41 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.4406rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-5_42 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(0, 105, 71, 1);
}

.Pixso-frame-1_82 {
  width: 100%;
  height: 19.375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.Pixso-frame-7_76 {
  width: 100%;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 19.8019rem;
  justify-content: flex-start;
  align-items: center;
}

.Pixso-frame-7_77 {
  width: 3.8125rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-7_85 {
  font-size: 0.875rem;
  font-family: "Inter-Semi Bold";
  font-weight: 600;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0875rem;
  line-height: 1.25rem;
  text-transform: uppercase;
  color: rgba(0, 0, 0, 1);
  width: auto;
  height: auto;
  position: relative;
  flex-shrink: 0;
  white-space: pre;
  flex-grow: 0;
}

.Pixso-frame-7_79 {
  width: 2.5975rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(210, 228, 255, 1);
}

.Pixso-paragraph-7_80 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(74, 64, 224, 1);
  width: 1.5975rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_85 {
  width: 100%;
  height: 17.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.625rem;
}

.Pixso-frame-1_86 {
  width: 100%;
  height: 4.6875rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-1_87 {
  width: calc(100% - 2rem);
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-1_88 {
  width: 100%;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 13.1006rem;
  justify-content: flex-start;
}

.Pixso-paragraph-1_89 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 8.8994rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_90 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-paragraph-1_91 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(74, 44, 0, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_92 {
  width: 100%;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-1_93 {
  flex: 1;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-1_94 {
  width: 25%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 75%;
  top: 0;
  bottom: 0;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-frame-1_95 {
  width: 1.4006rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_96 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.4006rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-1_97 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-frame-1_98 {
  width: 100%;
  height: 4.6875rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-1_99 {
  width: calc(100% - 2rem);
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-1_100 {
  width: 100%;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 15.15rem;
  justify-content: flex-start;
}

.Pixso-paragraph-1_101 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 7rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_102 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(105, 246, 184, 1);
}

.Pixso-paragraph-1_103 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(0, 90, 60, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_104 {
  width: 100%;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-1_105 {
  flex: 1;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-1_106 {
  width: 60%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 40%;
  top: 0;
  bottom: 0;
  background-color: rgba(0, 105, 71, 1);
}

.Pixso-frame-1_107 {
  width: 1.4406rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_108 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.4406rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-1_109 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(0, 105, 71, 1);
}

.Pixso-frame-1_110 {
  width: 100%;
  height: 19.375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.Pixso-frame-7_87 {
  width: 100%;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 19.8019rem;
  justify-content: flex-start;
  align-items: center;
}

.Pixso-frame-7_88 {
  width: 3.8125rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_112 {
  font-size: 0.875rem;
  font-family: "Inter-Semi Bold";
  font-weight: 600;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0875rem;
  line-height: 1.25rem;
  text-transform: uppercase;
  color: rgba(0, 0, 0, 1);
  width: auto;
  height: auto;
  position: relative;
  flex-shrink: 0;
  white-space: pre;
  flex-grow: 0;
}

.Pixso-frame-7_90 {
  width: 2.5975rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(210, 228, 255, 1);
}

.Pixso-paragraph-7_91 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(74, 64, 224, 1);
  width: 1.5975rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-6_44 {
  width: 100%;
  height: 17.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.625rem;
}

.Pixso-frame-6_45 {
  width: 100%;
  height: 4.6875rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-6_46 {
  width: calc(100% - 2rem);
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-6_47 {
  width: 100%;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 13.1006rem;
  justify-content: flex-start;
}

.Pixso-paragraph-6_48 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 8.8994rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-6_49 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-paragraph-6_50 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(74, 44, 0, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-6_51 {
  width: 100%;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-6_52 {
  flex: 1;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-6_53 {
  width: 25%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 75%;
  top: 0;
  bottom: 0;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-frame-6_54 {
  width: 1.4006rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-6_55 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.4006rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-6_56 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-frame-6_57 {
  width: 100%;
  height: 4.6875rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-6_58 {
  width: calc(100% - 2rem);
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-6_59 {
  width: 100%;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 15.15rem;
  justify-content: flex-start;
}

.Pixso-paragraph-6_60 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 7rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-6_61 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(105, 246, 184, 1);
}

.Pixso-paragraph-6_62 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(0, 90, 60, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-6_63 {
  width: 100%;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-6_64 {
  flex: 1;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-6_65 {
  width: 60%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 40%;
  top: 0;
  bottom: 0;
  background-color: rgba(0, 105, 71, 1);
}

.Pixso-frame-6_66 {
  width: 1.4406rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-6_67 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.4406rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-6_68 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(0, 105, 71, 1);
}
</style>
