<template>
  <v-app light>
    <v-content>
      <section id="legal-anchor">
        <v-layout column wrap class="ma-5 layout_global">
          <v-flex xs12 class="mt-5">
            <div class="ma-1 div_style justify ATCArquette-Medium">
              <VueShowdown :markdown="legaltext" class="justify"></VueShowdown>
            </div>
          </v-flex>
        </v-layout>
      </section>
    </v-content>
  </v-app>
</template>

<script>
import LegalService from "../services/legal.service";
import VueShowdown from "vue-showdown";

export default {
  name: "LegalNotices",
  components: VueShowdown,

  data: function () {
    return {
      legaltext: "",
    };
  },

  created: function () {
    LegalService.getAll().then((response) => {
      this.legaltext = response.toString();
    });
  },
};
</script>

<style scoped>
.div_style {
  font-size: 1em;
}
</style>
