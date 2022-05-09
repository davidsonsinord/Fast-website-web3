import Vue from "vue";
import App from "./App.vue";
import VueRouter from "vue-router";
import Axios from "axios";
import VueAnalytics from "vue-analytics";
import "./plugins/vuetify";
import "./plugins/vue-router";
import "./plugins/font-awesome";
import "./plugins/date-format";

import Home from "./components/Home";
import Blog from "./components/Blog";
import LegalNotices from "./components/LegalNotices";
import Article from "./components/Article";
import Default from "./layout/default";
import Admin from "./layout/admin";
import PageNotFound from "./components/PageNotFound";
import FormationPage from "./components/FormationPage";
import marked from "marked";

import VueGAPI from "vue-gapi";

Vue.component("default-layout", Default);
Vue.component("admin-layout", Admin);

Vue.config.productionTip = false;
Axios.defaults.baseURL = process.env.API_ENDPOINT;

const router = new VueRouter({
  mode: "history",
  routes: [
    { path: "/", redirect: "/home" },
    { path: "/home", component: Home },
    { path: "/blog", component: Blog },
    { path: "/mentions-legales", component: LegalNotices },
    { path: "/posts/:id", component: Article },
    { path: "/formation/:id", component: FormationPage },
    { path: "*", component: PageNotFound },
  ],
});

Vue.use(VueAnalytics, {
  id: "UA-145614641-1",
  router,
});

const apiConfig = {
  apiKey: "AIzaSyCzGj3MbDRm6nwAbv509WQkfVzPyvewucc",
  clientId:
    "95618499195-jpkkthe32ubiqrgcuqr28h1rru719d3n.apps.googleusercontent.com",
  discoveryDocs: ["https://sheets.googleapis.com/$discovery/rest?version=v4"],
  scope: "https://www.googleapis.com/auth/spreadsheets",
  // see all available scopes here: https://developers.google.com/identity/protocols/googlescopes'
};

Vue.use(VueGAPI, apiConfig);
Vue.use({
  install() {
    Vue.marked = marked;
    Vue.prototype.$marked = marked;
  },
});
new Vue({
  router,
  marked,
  render: (h) => h(App),
}).$mount("#app");
