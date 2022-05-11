<template>
  <v-parallax
    :src="require('@/assets/cover.jpg')"
    id="cover"
    class="cover_image"
  >
    <v-layout column align-center justify-center class="white--text">
      <img src="../assets/logofast.png" alt="Logo Fast" height="175" />
      <h1 class="title_cover mulberry-dav mb-2 display-1 text-xs-center">
        {{ title }}
      </h1>
      <div class="subheading text-xs-center">
        <v-spacer></v-spacer>
        <v-fade-transition class="mt-5">
          <v-btn
            class="text-xs-center button_style_arrow"
            color="bg-mulberry-dav"
            dark
            fab
            @click="scrollTo('catalog-anchor')"
          >
            <v-icon>keyboard_arrow_down</v-icon>
          </v-btn>
        </v-fade-transition>
      </div>
    </v-layout>
  </v-parallax>
</template>

<script>
export default {
  name: "Cover",
  data: () => ({
    title: "Des experts à votre service",
    scrollPadding: 35,
  }),
  methods: {
    scrollTo(anchor = "") {
      let moveTo = document.getElementById(anchor);
      window.scroll({
        top: moveTo.offsetTop - this.scrollPadding,
        left: 0,
        behavior: "smooth",
      });
    },
  },
};
</script>

<style>
.title_cover {
  padding-top: 35px;
  font-size: 72px;
  font-weight: bold;
}

.cover_image .v-parallax__image {
  opacity: 0.35 !important;
  filter: grayscale(1);
}

.button_style_arrow {
  display: block;
  margin-top: 15%;
  width: 40px;
  height: 40px;
}
</style>
