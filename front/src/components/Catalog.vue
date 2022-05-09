<template>
  <v-layout column wrap class="my-1 layout_global elevation-5 box">
    <v-flex xs12>
      <v-container id="id_container" grid-list-xl>
        <v-layout v-if="!showCheckbox" align-center justify-center column fill-height>
          <v-flex xs12 sm4>
            <div class="text-xs-center">
              <h2 class="headline ATCArquette-Medium turquoise-dav">FORMATIONS & AUDITS & SPEAKER</h2>
              <span class="subheading ATCArquette-Light near-black-dav">Un savoir-faire diversifié</span>
            </div>
          </v-flex>
        </v-layout>


        <v-expansion-panel class="mobile">
          <v-expansion-panel-content :class="{'color-mobile-formation': index % 2 === 0}" v-for="(domain, index) in domains" :key="index">
            <template v-slot:header >
              <div class="headline turquoise-dav ATCArquette-Medium">{{domain.title.substring(2, domain.title.length)}}</div>
            </template>
            <v-card>
              <v-card-text class="text-column-catalog" :class="{'color-mobile-formation': index % 2 === 0}">
                <form>
                  <ul>
                    <li v-for="(subdomain, index) in domain.subdomains" :key="index">
                      <div class="ATCArquette-Bold pt-4 subdomain">{{subdomain.title}}</div>
                      <ul>
                        <li
                                class="ATCArquette-Medium near-black-dav bef-turquoise-dav list_skills"
                                v-for="(skill, index) in subdomain.skills"
                                :key="index"
                        >
                          <input
                                  type="checkbox"
                                  class="ma-0"
                                  v-if="showCheckbox"
                                  v-model="registration.skills"
                                  v-bind:value="skill"
                          />
                          {{ skill.title }}
                        </li>
                      </ul>
                    </li>
                  </ul>
                </form>
              </v-card-text>
            </v-card>
          </v-expansion-panel-content>
        </v-expansion-panel>



        <v-layout row wrap justify-center  pa_card align-top desktop>
          <v-flex id="column" xs12 md3 v-for="(domain, index) in domains" :key="index">
            <v-card class="elevation-0 transparent">
              <v-card-title v-if="!showCheckbox" primary-title class="layout justify-center">
                <div
                  class="headline turquoise-dav ATCArquette-Medium"
                >{{domain.title.substring(2, domain.title.length)}}</div>
              </v-card-title>
              <v-card-title v-else primary-title class="layout justify-center ma-0 pa-0">
                <div
                  class="headline turquoise-dav ATCArquette-Medium domain ma-0 pa-0"
                >{{domain.title.substring(2, domain.title.length)}}</div>
              </v-card-title>
              <hr
                v-if="!showCheckbox"
                width="30%"
                height="3px"
                class="near-black-dav ml-auto mr-auto"
              />
              <v-card-text class="text-column-catalog">
                <form>
                  <ul>
                    <li v-for="(subdomain, index) in domain.subdomains" :key="index">
                      <div class="ATCArquette-Bold pt-4 subdomain">{{subdomain.title}}</div>
                      <ul>
                        <li
                          class="ATCArquette-Medium near-black-dav bef-turquoise-dav list_skills"
                          v-for="(skill, index) in subdomain.skills"
                          :key="index"
                        >
                          <input
                            type="checkbox"
                            class="ma-0"
                            v-if="showCheckbox"
                            v-model="registration.skills"
                            v-bind:value="skill"
                          />
                          {{ skill.title }} <router-link flat v-if="skill.description !== 'description'" :to="'/formation/' + skill.id" class="black--text plus_btn">[+]</router-link>
                        </li>
                      </ul>
                    </li>
                  </ul>
                </form>
              </v-card-text>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-flex>
  </v-layout>
</template>
<script>


import FormService from "../services/form.service";
import DomainService from "../services/domain.service";

export default {
  name: "Catalog",
  props: {
    showCheckbox: Boolean
  },
  created() {
    DomainService.domains.then(response => {
      this.domains = response;
      this.domains.sort(this.compare);
    });
    this.registration = FormService.registration;
  },
  data: () => ({
    domains: [],
    registration: {},
  }),
  methods: {
    compare(a, b) {
      const nameA = a.title;
      const nameB = b.title;

      if (nameA < nameB) {
        return -1;
      } else if (nameA > nameB) {
        return 1;
      }
      return 0;
    }
  }
};
</script>

<style>
  .v-expansion-panel__header{
    padding: 8px 8px !important;
  }
</style>
<style scoped lang="scss">

  .plus_btn{
    text-decoration: none !important;
  }


  .box{
    -webkit-box-shadow: 0 0 0 0 rgba(0,0,0,0.1), 0 0 0 0 rgba(0,0,0,0.1), 0 0 15px 2px rgba(0,0,0,0.1) !important;

    box-shadow: 0 0 0 0 rgba(0,0,0,0.1), 0 0 0 0 rgba(0,0,0,0.1), 0 0 15px 2px rgba(0,0,0,0.1) !important;
  }

  .mobile {
    display:none
  }

  @media screen and (max-width: 640px) {
    .mobile {
      display:contents;
    }
    .desktop {
      display:none;
    }
    .color-mobile-formation {
      background-color : rgba(238, 240, 239, 0.5) !important;
    }
  }

#column:nth-child(odd) {
  background-color: rgba(238, 240, 239, 0.5);
}

.layout_global {
  background-size: cover;
  overflow: hidden;
}

#id_container {
  padding: 0px !important;
  max-width: none !important;
}

.list_skills::before {
  content: "•";
  display: inline-block;
  width: 1em;
}

.list_skills {
  list-style-position: outside;
}

.text-column-catalog {
  padding-top: 0%;
}

hr {
  display: block;
  border-style: solid;
  border-width: 1.75px;
}

ul {
  list-style: none;
  padding-inline-start: 0px;
}

.btn_continue {
  right: 0px;
  position: absolute;
}

.domain {
  font-size: 1.2em !important;
}

.subdomain::first-letter {
  text-transform: uppercase !important;
}

#footer-catalog {
  position: fixed;
  bottom: 0%;
}
</style>