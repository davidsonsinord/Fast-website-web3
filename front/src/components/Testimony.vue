<template>
  <v-carousel id="testimony-carousel" class="caroussel" dark hide-delimiters>
    <div class="testimony_headline white--text mb-3 text-xs-center">
      Ils nous ont fait confiance
    </div>
    <v-carousel-item v-for="(testimony, index) in testimonies" :key="index">
      <v-layout align-center justify-center column class="carousel_item">
        <div id="avatar">
          <v-img :src="getImage(testimony)"></v-img>
        </div>
        <blockquote class="blockquote near-white-dav" id="id_testimony_name">
          {{ testimony.nom }}
        </blockquote>
        <blockquote class="blockquote near-white-dav" id="id_citation">
          {{ testimony.description }}
        </blockquote>
      </v-layout>
    </v-carousel-item>
  </v-carousel>
</template>

<script>
import TestimonyService from "../services/testimony.service";
export default {
  name: "Testimony",
  created() {
    TestimonyService.testimony.then(data => (this.testimonies = data));
  },

  data: () => ({
    testimonies: []
  }),

  methods: {
    getImage(testimony) {
      return process.env.VUE_APP_BACK_URL + "img/" + testimony.image;
    }
  }
};
</script>

<style scoped>
#avatar {
  width: 150px;
  height: 100%;
}

#id_testimony_name {
  margin-top: 1%;
  font-size: 2em;
  padding-left: 0;
}

@media only screen and (max-width: 780px) {
  .testimony_headline {
    top: 1% !important;
    font-size: 24px;
  }
  .carousel_item {
    padding-top: 55px;
  }

  #id_citation {
    font-size: 16px;
  }
}

#id_citation {
  margin-left: 15%;
  margin-right: 15%;
}

#id_citation:before {
  content: open-quote;
  right: 10px;
  position: relative;
  top: 30px;
  font-size: 4em;
  line-height: 0;
}

#id_citation:after {
  content: close-quote;
  left: 10px;
  position: relative;
  top: 30px;
  font-size: 4em;
  line-height: 0;
}

.testimony_headline {
  width: 100%;
  text-align: center;
  top: 3%;
  margin: auto;
  position: absolute;
  font-size: 30px;
}

.carousel_item {
  padding-top: 80px;
}

#testimony-carousel {
  background-image: url("../assets/testimony_background.jpg");
}
</style>
