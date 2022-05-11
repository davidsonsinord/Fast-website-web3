<template>
  <v-app light>
    <v-content class="content">
      <div class="pt-5 background-grey pb-5">
        <div class="bandeau">
          <v-layout row wrap>
            <v-flex md8>
              <span class="title">{{ skill.title }}</span>
              <v-avatar size="48px" class="mobile logo-mobile" tile>
                <v-img contain :src="skill.logo" />
              </v-avatar>

              <v-divider></v-divider>

              <div class="description">{{ skill.description }}</div>

              <div class="puces">
                <div v-for="(item, i) in skill.puces" :key="i" class="mt-2">
                  <v-layout>
                    <v-icon class="icon">keyboard_arrow_right</v-icon>
                    <v-flex>
                      <div class="item_list">{{ item }}</div>
                    </v-flex>
                  </v-layout>
                </div>
              </div>
            </v-flex>
            <v-flex md4 class="desktop">
              <v-img contain class="logo ml-5" :src="skill.logo" />
            </v-flex>
          </v-layout>
        </div>
      </div>
      <div class="formateur pt-5" v-if="skill.trainers">
        <v-layout row wrap justify-center>
          <v-flex
            v-for="trainer in skill.trainers"
            v-bind:key="trainer.id"
            class="trainer text-center"
          >
            <v-avatar size="200">
              <img :src="trainer.image" alt="trainer.nom" class="trainer_img" />
            </v-avatar>
            <div class="trainer_name">{{ trainer.nom }}</div>
            <div class="trainer_description">{{ trainer.description }}</div>
          </v-flex>
        </v-layout>
      </div>
    </v-content>
  </v-app>
</template>

<script>
import SkillService from "../services/skill.service";

export default {
  name: "FormationPage",
  created: function () {
    window.scrollTo(0, 0);
    SkillService.getSkill(this.$route.params.id).then((article) => {
      if (article != null && article.description.length >= 15) {
        this.skill = article;
      } else {
        this.$router.push("/404");
      }
    });
  },
  data: () => ({
    skill: [],
  }),
};
</script>

<style scoped>
.icon {
  min-width: 40px !important;
  align-items: normal;
}
.trainer_description {
  padding-left: 15%;
  padding-right: 15%;
}

.mobile {
  display: none;
}

.logo-mobile {
  bottom: -1.5%;
}

.trainer {
  text-align: center;
  margin-bottom: 5%;
}

.formateur {
  margin-left: 10%;
  margin-right: 10%;
}

.puces {
  margin-top: 5%;
  background: none;
  margin-left: 5%;
}

.item_list {
  font-family: "Roboto Condensed", sans-serif !important;
  font-size: 16px !important;
  text-align: justify;
}

.bandeau {
  margin-top: 5%;
  margin-left: 15%;
  margin-right: 15%;
}

.background-grey {
  background: rgba(238, 240, 239, 0.5);
  border-bottom: 1px solid lightgrey;
}

.trainer_name {
  font-family: "Roboto Condensed", sans-serif !important;
  text-transform: uppercase;
  font-weight: bolder;
  margin-bottom: 0;
  font-size: 30px !important;
}

.title {
  padding-left: 5%;
  font-family: "Roboto Condensed", sans-serif !important;
  text-transform: uppercase;
  font-weight: bolder;
  margin-bottom: 0;
  font-size: 50px !important;
}

.description {
  margin-top: 4%;
  text-align: justify;
  margin-bottom: 4%;
}

.logo {
  width: 80%;
}

@media screen and (max-width: 960px) {
  .mobile {
    display: contents;
  }
  .desktop {
    display: none;
  }
  .title {
    font-size: 30px !important;
  }
}
</style>
