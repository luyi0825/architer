import Layout from "/@/layout/index.vue";

const studyRouter = {
    path: "/study",
    component: Layout,
    redirect: "/study/codemap",
    name: "study",
    meta: {
      title: "message.demo",
      icon: "el-icon-s-data",
      showLink: true,
      savedPosition: true,
      rank: 1
    },
    children: [
      {
        path: "/study/codemap",
        name: "codemap",
        component: () => import("/@/views/study/codemap/index.vue"),
        meta: {
          title: "message.codemap",
          showLink: true,
          savedPosition: true
        }
      },
      {
        path: "/study/area",
        name: "area",
        component: () => import("/@/views/study/area/index.vue"),
        meta: {
          title: "message.area",
          showLink: true,
          savedPosition: true
        }
      }
    ]
  }
;

export default studyRouter;
