import { createRouter, createWebHistory } from 'vue-router'
import DayPlan from '../views/DayPlan.vue'
import MonthPlan from '../views/MonthPlan.vue'
import YearPlan from '../views/YearPlan.vue'

const routes = [
  {
    path: '/',
    redirect: '/day'
  },
  {
    path: '/day',
    name: 'DayPlan',
    component: DayPlan
  },
  {
    path: '/month',
    name: 'MonthPlan',
    component: MonthPlan
  },
  {
    path: '/year',
    name: 'YearPlan',
    component: YearPlan
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router