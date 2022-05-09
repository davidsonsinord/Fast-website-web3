<template>
  <v-content id="agenda">
    <div class="agenda_headline ATCArquette-Medium white--text mb-3 text-xs-center">
      NOTRE AGENDA
    </div>
    <carousel
        class="mt-5 ml-5 mr-5"
        id="agenda-carousel"
        :perPage="getSlideNumber()"
        :navigationEnabled="true"
        :paginationEnabled="false"
        :scrollPerPage="false"
        :mouseDrag="false"
        :navigateTo="[slideToStart, true]"
        :navigation-next-label="nextLabel"
        :navigation-prev-label="prevLabel"
        @pageChange="handlePageChange"
    >

      <slide v-for="entry in entries" v-if="entry[0]==='o'">
        <div class="white--text">
          <div height="50px">
            <span class="type left">{{ entry[1] }}</span>
            <span class="date right">{{ moment(entry[3], "DD MM YYYY").format("MMMM YYYY") }}</span>
          </div>
          <div class="titre">{{ entry[2] }}</div>
          <div class="descriptif">
            <div v-if="entry[4]!==''">• {{ entry[4] }}</div>
            <div v-if="entry[5]!==''">• {{ entry[5] }}</div>
            <div v-if="entry[6]!==''">• {{ entry[6] }}</div>
            <div v-if="entry[7]!==''">• {{ entry[7] }}</div>
          </div>
        </div>
      </slide>

    </carousel>
  </v-content>
</template>

<script>
import {Carousel, Slide} from "vue-carousel";
import moment from "moment";

moment.locale('fr');

export default {
  name: "AgendaCarousel",
  components: {
    Carousel,
    Slide,
  },
  data() {
    return {
      nextLabel: "<i aria-hidden='true' class='v-icon material-icons theme--dark'>keyboard_arrow_right</i>",
      prevLabel: "<i aria-hidden='true' class='v-icon material-icons theme--dark'>keyboard_arrow_left</i>",
      entries: [],
      moment: moment,
      actualSlide: 0,
      slideToStart: 0,
    }
  },
  created() {

    this.$getGapiClient()
        .then(gapi => {
          gapi.client.sheets.spreadsheets.values.get({
            spreadsheetId: '1HnhwLaiIn7T3H_Mm0QJ9BVale0qLX2j0nEQeK4O_dSI',
            range: 'Feuille 1',
          }).then(data => {
            this.entries = data.result.values;
            this.slideToStart = this.slideToNavigate();
          });
        })
  },

  methods: {
    handlePageChange(page) {
      this.slideToStart = page;
    },
    getSlideNumber() {
      switch (this.$vuetify.breakpoint.name) {
        case 'xs':
          return 1;
        case 'sm':
          return 2;
        case 'md':
        case 'lg':
          return 3;
        case 'xl':
          return 5
      }
    },

    getRowToStart() {
      let length = 0;
      let numberOfNone = 0;
      const currentDate = moment();
      let closeDate = currentDate;
      this.entries.forEach(row => {
        if (row[0] === 'o' && moment(closeDate).isSameOrBefore(currentDate)) {
          closeDate = moment(row[3], "DD MM YYYY");
          length++;
        } else if (row[0] === 'n') {
          numberOfNone++;
          length++
        }
      });
      return {"length": length, "numberOfNone": numberOfNone};
    },

    slideToNavigate() {
      const tuple = this.getRowToStart();
      let start = tuple.length - tuple.numberOfNone;
      const length = this.entries.length - tuple.numberOfNone - 1;
      let slide = start - this.getSlideNumber();

      switch (this.$vuetify.breakpoint.name) {
        case 'xs':
        case 'sm':
          return slide;
        case 'md':
        case 'lg':
          if (start === length)
            return slide;
          if (start === (length - 1))
            return slide + 1;
          return slide + 1;
        case 'xl':
          if (start === length)
            return slide;
          if (start === (length - 1))
            return slide + 1;
          return slide + 2
      }
    },

  }
}
</script>

<style scoped>
#agenda-carousel {
  min-height: 310px;
  width: 95%;
  margin-left: 0;
  margin-right: 0;
}

#agenda {
  background-image: url("../assets/testimony_background.jpg");
}

.agenda_headline {
  width: 100%;
  text-align: center;
  top: 3%;
  margin: auto;
  position: absolute;
  font-size: 30px;
}

.type {
  text-align: left;
  font-size: 1.3em;
  border-radius: 5px;
  border: 1px solid white;
  display: inline-block;
  padding: 1px 3px 0 2px;
  font-style: italic;
}

.titre {
  clear: both;
  bottom: 0;
  font-weight: bold;
  font-size: 1.7em;
  color: #33CCCC;
  padding-top: 5%;
  min-height: 75px;
}

.date {
  font-size: 1.3em;
  font-style: italic;
  text-align: right;
  margin-right: 0;
  float: right;
  text-transform: capitalize;
}

.descriptif {
  font-size: 1.3em;
}

.VueCarousel-slide {
  position: relative;
  margin-top: 1.5%;
  box-sizing: border-box;
  border-left: solid white 1px;
  padding: 0 2%;
}

@media screen and (max-width: 600px) {
  .VueCarousel-slide {
    border-left: none;
  }

  .date {
    width: 50%
  }

  #agenda-carousel {
    margin-top: 20% !important;
    margin-left: 35px !important;
    width: 80%;
  }
}

@media screen and (min-width: 600px) and (max-width: 1400px) {
  .date {
    width: 50%
  }

  #agenda-carousel {
    margin-left: 35px !important;
    width: 90%;
  }
}


</style>
