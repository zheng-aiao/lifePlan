
import { createRouter, createWebHistory  } from "vue-router";

const routes = [{path: "/", name: "Frame12", component: () => import("@/views/Frame12.vue"), meta: { guid: "1:2" }},];

const routePathMap = new Map();

export const getRoutePathByGuid = (guid) => {
  if (!guid) return;
  if (routePathMap.has(guid)) return routePathMap.get(guid);

  const route = routes.find((item) => item.meta?.guid === guid);
  if (!route) return;
  routePathMap.set(guid, route.path);

  return route.path;
}

export const router = createRouter({
  history: createWebHistory(),
  routes,
});
