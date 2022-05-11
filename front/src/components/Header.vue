<template>
  <v-toolbar
    height="65px"
    class="white pa-0 menu_toolbar"
    fixed
    id="top-anchor"
  >
    <router-link class="routeur_deco near-black-dav" to="/home">
      <v-toolbar-title class="headline">
        <v-avatar :size="60" id="logo-fast">
          <img src="../assets/logofast_without_text.png" alt="avatar" />
        </v-avatar>
        <span id="title" class="text-uppercase">{{ title }}</span>
        <div class="company_name_logo">
          <span id="company-name-text" class="Dancing_Script">{{
            company_name
          }}</span>
          <img
            class="pl-2"
            height="25"
            src="../assets/davidson_logo.png"
            alt="davidson_logo"
          />
        </div>
      </v-toolbar-title>
    </router-link>
    <v-spacer></v-spacer>
    <v-toolbar-items class="hidden-sm-and-down" height="inherit">
      <v-btn
        to="/blog"
        id="button_blog"
        class="button_style black--text"
        @click="track"
        flat
        >BLOG</v-btn
      >
    </v-toolbar-items>
    <v-menu class="hidden-md-and-up">
      <v-toolbar-side-icon slot="activator"></v-toolbar-side-icon>
      <v-list style="width: 100%" class="button_header">
        <v-list-tile class="button_header"> </v-list-tile>
        <v-list-tile class="button_header" to="/blog">
          <v-list-tile-content>
            <v-btn
              to="/blog"
              class="button_menu black--text"
              @click="track"
              flat
              >BLOG</v-btn
            >
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-menu>
  </v-toolbar>
</template>

<script>
import FormService from "../services/form.service";

export default {
  name: "Header",
  created() {
    this.dialog = FormService.dialog;
  },
  data: () => ({
    title: "FAST",
    company_name: "by Davidson",
    dialog: null,
  }),
  methods: {
    track() {
      this.$ga.event("Blog", "accès au blog depuis le menu", "Blog menu");
    },
  },
};
</script>

<style>
.button_menu {
  min-width: 160px;
  justify-content: left !important;
}

.button_menu div {
  justify-content: left !important;
}

.company_name_logo {
  position: absolute;
  margin-top: -30px;
  margin-left: 120px;
}
.pl-2 {
  vertical-align: middle;
}
.menu_toolbar .v-icon {
  height: 50px;
  width: 100%;
  font-size: 30px;
}
.menu_toolbar .v-menu {
  padding-right: 2%;
}
.menu_toolbar .v-btn--icon {
  width: 50px;
  height: 50px;
}
#title {
  font-weight: 800;
  margin-left: 3%;
  color: #008aa2;
  font-size: 30px;
  letter-spacing: 3px;
  white-space: nowrap;
}
@media only screen and (max-width: 959px) {
  .v-toolbar__content,
  .v-toolbar__extension {
    padding: 0px 0px !important;
  }
}
#top-anchor {
  height: 65px !important;
  padding-left: 0;
  padding-right: 0;
}

.routeur_deco {
  text-decoration: none;
}

.v-menu__content {
  margin-top: 14.5%;
}

.button-header {
  width: 100%;
}

.pa-0 div {
  padding-right: 0.1%;
}
</style>

<style scoped>
#title {
  vertical-align: middle;
}
#button_blog {
  margin-left: 10px;
  border-bottom-color: #479dd3;
}
#logo-fast {
  vertical-align: middle;
  padding: auto;
  width: 30px;
  height: 30px;
}

#company-name-text {
  font-weight: 300;
}

#company-name-text:link {
  text-decoration: none !important;
}

.headline {
  width: 200px;
}
</style>
