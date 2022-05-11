<template>
  <v-app light>
    <v-content>
      <h1
        id="blog-anchor-view"
        class="ATCArquette-Medium turquoise-dav titre_blog mt-5 text-xs-center"
      >
        Le blog de Davidson
      </h1>
      <div class="text-xs-center">
        <v-btn v-on:click="extend = !extend" class="button-blog"
          >{{ getVerb(extend) }} les articles</v-btn
        >
      </div>
      <section v-if="!extend">
        <PreviewBlog
          title="Tous les posts"
          :preview="false"
          :posts="posts"
        ></PreviewBlog>
      </section>
      <section v-if="extend">
        <ExtendBlog :posts="posts"></ExtendBlog>
      </section>
    </v-content>
  </v-app>
</template>

<script>
import PreviewBlog from "./PreviewBlog";
import CardBlog from "./CardBlog";
import BlogService from "../services/blog.service";
import ExtendBlog from "./ExtendBlog";

export default {
  name: "Home",
  components: {
    CardBlog,
    PreviewBlog,
    ExtendBlog,
  },
  created() {
    window.scrollTo(0, 0);
    BlogService.articles.then((data) => {
      this.posts = data;
      this.sortPosts();
    });
  },
  data: () => ({
    posts: [],
    extend: false,
  }),

  methods: {
    sortPosts() {
      this.posts.sort(function (a, b) {
        var dateA = new Date(a.date),
          dateB = new Date(b.date);
        return dateB - dateA;
      });
      return this.posts;
    },

    getVerb(extend) {
      if (extend) {
        return "Condenser";
      } else {
        return "Déplier";
      }
    },
  },
};
</script>

<style>
.button-blog {
  margin-left: 10px;
  border-bottom-color: #479dd3;
  box-shadow: none !important;
  text-transform: none;
}

.titre_blog {
  font-weight: 400;
  font-size: 4em !important;
  line-height: 1em !important;
  margin-bottom: 2%;
}
@media only screen and (max-width: 959px) {
  .card {
    width: 100% !important;
  }
}
</style>
