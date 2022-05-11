<template>
  <v-app light>
    <v-content>
      <v-container grid-list-md class="content" v-scroll="onScroll">
        <v-layout row wrap>
          <v-flex xs12>
            <v-img
              style="height: 500px"
              :src="getCover(article)"
              alt="image de couverture"
            ></v-img>
          </v-flex>
          <v-flex class="social_sharing">
            <vue-goodshare-facebook
              :page_url="getUrl()"
              title_social="Partager"
              has_icon
            ></vue-goodshare-facebook>
            <vue-goodshare-twitter
              :page_url="getUrl()"
              title_social="Twitter"
              has_icon
            ></vue-goodshare-twitter>
            <vue-goodshare-linked-in
              :page_url="getUrl()"
              title_social="Partager"
              has_icon
            ></vue-goodshare-linked-in>
          </v-flex>
          <v-flex xs12 class="article">
            <p class="display-3 title_article">{{ article.title }}</p>
            <p class="subheading">
              Auteur : {{ article.author }} |
              <span>{{ article.date | formatDate }}</span>
            </p>
            <v-divider></v-divider>
            <div id="markdown" v-html="$marked(article.markdown)"></div>
          </v-flex>
        </v-layout>
      </v-container>
      <v-fade-transition>
        <v-btn
          color="pink"
          v-if="scrolldown"
          dark
          fixed
          bottom
          right
          fab
          @click="$vuetify.goTo('#top-anchor')"
        >
          <v-icon>keyboard_arrow_up</v-icon>
        </v-btn>
      </v-fade-transition>
    </v-content>
  </v-app>
</template>

<script>
import BlogService from "../services/blog.service";
import VueGoodshareFacebook from "vue-goodshare/src/providers/Facebook";
import VueGoodshareLinkedIn from "vue-goodshare/src/providers/LinkedIn";
import VueGoodshareTwitter from "./Twitter";

export default {
  name: "Article",
  components: {
    VueGoodshareTwitter,
    VueGoodshareLinkedIn,
    VueGoodshareFacebook,
  },
  created() {
    window.scrollTo(0, 0);
    BlogService.getByTitle(this.$route.params.id).then((post) => {
      if (post != null) {
        this.article = post;
        this.cover = process.env.VUE_APP_BACK_URL + "img" + post.cover;
      } else {
        this.$router.push("/404");
      }
    });
  },
  data: () => ({
    article: {},
    offsetTop: 0,
    scrolldown: false,
  }),
  mounted() {
    this.url =
      "http://localhost/posts/" + this.$route.params.id.replace(/\s+/g, "%20");
    this.cover = process.env.VUE_APP_BACK_URL + "img" + this.article.cover;
  },
  methods: {
    onScroll() {
      let mark = document.getElementById("markdown");
      if (window.scrollY > mark.offsetTop) {
        this.scrolldown = true;
      }
      if (window.scrollY < mark.offsetTop) {
        this.scrolldown = false;
      }
    },
    getUrl() {
      return this.url;
    },
    getCover() {
      return this.cover;
    },
  },
};
</script>
<style scoped>
.subheading {
  padding-left: 5.3%;
}

.title_article {
  padding-left: 5%;
  font-family: "Roboto Condensed", sans-serif !important;
  text-transform: uppercase;
  font-weight: bolder;
  margin-bottom: 0;
  font-size: 50px !important;
}

.social_sharing {
  text-align: right;
}

.article {
  margin-right: 10%;
  margin-left: 10%;
}

#markdown {
  margin-top: 3%;
  text-align: justify;
}

@media only screen and (max-width: 959px) {
  #markdown /deep/ img {
    width: 350px !important;
    height: 200px !important;
  }

  #markdown /deep/ code {
    width: 350px !important;
  }

  .article {
    margin-right: 0%;
    margin-left: 0%;
  }
}
</style>
