<template>
  <v-layout column justify-center align-center>
    <v-flex xs12 sm4 class="my-3">
      <div class="text-xs-center">
        <h2 class="headline title ATCArquette-Medium turquoise-dav">Tous les posts</h2>
        <span class="subheading ATCArquette-Light">{{subtitle}}</span>
      </div>
    </v-flex>
    <v-layout class="card row wrap" v-for="(item, i) in posts" :key="i">
      <v-flex class="info-blog">
        <v-card-title>
          <div>
            <span class="headline near-black-dav">{{item.title}}</span>
            <v-spacer></v-spacer>
            <span class="grey--text">
              par
              <span class="font-weight-bold">{{item.author}}</span>
              <div>le {{item.date | formatDate}}</div>
            </span>
          </div>
        </v-card-title>
        <v-btn flat class="turquoise-dav" :to="'/posts/' + item.title">Voir plus</v-btn>
      </v-flex>
      <v-flex>
        <v-hover>
          <v-img slot-scope="{ hover }" class="white--text mx-auto img_articles" :src="item.cover">
            <router-link :to="'/posts/' + item.title" class="indication">
              <v-expand-transition>
                <v-layout
                  justify-center
                  align-center
                  v-if="hover"
                  class="d-flex transition-fast-in-fast-out darken-2 title v-card--reveal white--text"
                  style="height: 100%;"
                >Lire l'article</v-layout>
              </v-expand-transition>
            </router-link>
          </v-img>
        </v-hover>
      </v-flex>
    </v-layout>
  </v-layout>
</template>

<script>
import BlogService from "../services/blog.service";

export default {
  name: "CardBlog",
  props: {
    posts: []
  },
  data: () => ({})
};
</script>

<style scoped>
.card {
  width: 50%;
  margin-bottom: 10px;
}
.indication {
  text-decoration: none;
}
.info-blog {
  max-width: 300px;
}
@media only screen and (max-width: 959px) {
  .img_articles {
    width: 350px !important;
    height: 200px !important;
  }
}
.img_articles {
  border: 1px solid grey;
  width: 200px;
  height: 200px;
}
</style>