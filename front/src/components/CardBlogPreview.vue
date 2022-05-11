<template>
  <v-layout row wrap align-center justify-center align-content-space-around>
    <v-flex v-for="(item, i) in most" :key="i" class="card mobile">
      <router-link :to="'/posts/' + item.title" class="indication">
        <v-img
          class="white--text mx-auto img_articles"
          height="200px"
          width="350px"
          :src="getCover(item)"
        >
        </v-img>
      </router-link>

      <v-card-title class="card-article">
        <div>
          <span class="headline near-black-dav title_article">{{
            item.title
          }}</span>
          <v-spacer></v-spacer>
          <div class="w-350">
            <span class="left">{{ item.author }}</span>
            <span class="right">{{ item.date | formatDate }}</span>
          </div>
          <div>
            <v-btn
              flat
              class="turquoise-dav pa-0 ma-0 see_more"
              :to="'/posts/' + item.title"
              >Voir plus <v-icon class="icon">trending_flat</v-icon></v-btn
            >
          </div>
        </div>
      </v-card-title>
    </v-flex>

    <v-flex v-for="(item, i) in most" :key="i" class="card desktop">
      <v-hover>
        <v-img
          slot-scope="{ hover }"
          class="white--text mx-auto img_articles"
          height="200px"
          width="350px"
          :src="getCover(item)"
        >
          <router-link :to="'/posts/' + item.title" class="indication">
            <v-expand-transition>
              <v-layout
                justify-center
                align-center
                v-if="hover"
                class="d-flex transition-fast-in-fast-out title darken-2 v-card--reveal white--text"
                style="height: 100%"
                >Lire l'article</v-layout
              >
            </v-expand-transition>
          </router-link>
        </v-img>
      </v-hover>
      <v-card-title class="card-article">
        <div>
          <span class="headline near-black-dav title_article">{{
            item.title
          }}</span>
          <v-spacer></v-spacer>
          <div class="w-350">
            <span class="left">{{ item.author }}</span>
            <span class="right">{{ item.date | formatDate }}</span>
          </div>
          <div>
            <v-btn
              flat
              class="turquoise-dav pa-0 ma-0 see_more"
              :to="'/posts/' + item.title"
              >Voir plus<v-icon class="icon">trending_flat</v-icon></v-btn
            >
          </div>
        </div>
      </v-card-title>
    </v-flex>
  </v-layout>
</template>
<script>
export default {
  name: "CardBlogPreview",
  props: {
    most: [],
  },
  data: () => ({}),
  methods: {
    getCover(article) {
      return process.env.VUE_APP_BACK_URL + "img" + article.cover;
    },
  },
};
</script>

<style scoped>
.w-350 {
  width: 350px;
}

.right {
  display: block;
  float: right;
  text-align: right;
  width: 100px;
}

.left {
  display: block;
  float: left;
  width: 250px;
}

.see_more {
  width: 120px;
}

.icon {
  padding-left: 8%;
}

.card-article {
  padding-top: 1% !important;
  padding-left: 0;
  margin: auto;
  text-align: left;
  width: 350px;
}

.title_article {
  font-family: "Roboto Condensed", sans-serif !important;
  text-transform: uppercase;
  font-weight: bolder;
  margin-bottom: 0;
  font-size: 30px !important;
}

.mobile {
  display: none;
}

@media screen and (max-width: 780px) {
  .mobile {
    display: contents;
  }

  .desktop {
    display: none;
  }
}

.card {
  margin: 10px 20px 0px 20px;
  max-width: 380px;
}
@media only screen and (max-width: 959px) {
  .card {
    margin: 0%;
  }
}
.indication {
  text-decoration: none;
}
.img_articles {
  border: 1px solid grey;
}
</style>
