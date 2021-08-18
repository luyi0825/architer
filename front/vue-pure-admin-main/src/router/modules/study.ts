import Layout from "/@/layout/index.vue";

const studyRouter = {
  path: "/study",
  component: Layout,
  redirect: "/study/codemap/index",
  name: "demo",
  meta: {
    title: "message.demo",
    icon: "el-icon-s-data",
    showLink: true,
    savedPosition: false,
    rank: 1
  },
  children: [
    {
      path: "/study/codemap/index",
      component: () => import("/@/views/study/codemap/index.vue"),
      name: "codemap",
      meta: {
        title: "message.codemap",
        showLink: true,
        savedPosition: false
      }
    }
  ]
};

export default studyRouter;
