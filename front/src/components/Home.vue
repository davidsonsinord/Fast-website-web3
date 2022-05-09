<template>
  <v-app light>
    <v-content>
      <section>
        <Cover id="cover-anchor" />
      </section>
      <section id="agenda-anchor">
        <AgendaCarousel />
      </section>
      <section id="catalog-anchor" ref="test">
        <Catalog v-bind:showCheckbox="false"></Catalog>
      </section>
      <section id="form-anchor">
        <Relation></Relation>
      </section>
      <section id="blog-anchor">
        <PreviewBlog
          :preview="true"
          title="BLOG"
          subtitle="Consultez nos articles de blog"
          :posts="posts"
        ></PreviewBlog>
      </section>
      <section id="testimony-anchor">
        <Testimony />
      </section>
      <section id="contact-anchor">
        <Contact />
      </section>
    </v-content>
  </v-app>
</template>

<script>
import Cover from "./Cover";
import Catalog from "./Catalog";
import Contact from "./Contact";
import Testimony from "./Testimony";
import AgendaCarousel from "./AgendaCarousel";
import Relation from "./Relation";
import PreviewBlog from "./PreviewBlog";
import BlogService from "../services/blog.service";

export default {
  name: "Home",
  components: {
    Cover,
    Catalog,
    Relation,
    AgendaCarousel,
    Testimony,
    Contact,
    PreviewBlog
  },
  created() {
    window.scrollTo(0, 0);
    BlogService.articles.then(data => {
      this.posts = data;
      this.sortPosts();

    });
  },
  data: () => ({
    posts: []
  }),
  methods: {
    sortPosts(){
      this.posts.sort(function(a, b){
        var dateA=new Date(a.date), dateB=new Date(b.date);
        return dateB-dateA
      });
      return this.posts;
    },
  }
};
</script>

