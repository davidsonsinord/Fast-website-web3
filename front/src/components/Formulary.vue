<template>
  <v-form ref="form" v-model="valid" lazy-validation>
    <v-container>
      <v-layout row wrap>
        <v-flex xs12 md4>
          <v-text-field v-model="registration.name" :rules="rules.nameRules" label="Prénom Nom" required></v-text-field>
        </v-flex>
        <v-flex xs12 md4>
          <v-text-field
            v-model="registration.tel"
            :rules="rules.phoneRules"
            label="Téléphone"
            required
          ></v-text-field>
        </v-flex>
        <v-flex xs12 md4>
          <v-text-field
            v-model="registration.mail"
            :rules="rules.emailRules"
            label="E-mail"
            required
          ></v-text-field>
        </v-flex>
      </v-layout>
      <v-layout>
        <v-flex xs12 md8>
          <v-textarea v-model="registration.description" label="Commentaire"></v-textarea>
        </v-flex>
      </v-layout>
    </v-container>
    <v-checkbox v-model="checkbox" :rules="[v => !!v || 'Cochez pour continuer!']" required>
      <template slot="label">
        <span>
          En cochant cette case, vous acceptez nos
          <router-link @click.stop to="/mentions-legales" target="_blank">conditions d'utilisation</router-link>
        </span>
      </template>
    </v-checkbox>
  </v-form>
</template>

<script>
import FormService from "../services/form.service";
import ApiService from "../services/api.service";
import LegalNotices from "./LegalNotices";

export default {
  name: "Formulary",

  created() {
    this.rules = FormService.rules;
    this.registration = FormService.registration;
  },

  components: {
    LegalNotices
  },

  data: () => ({
    valid: false,
    rules: {},
    registration: {}
  }),
  methods: {
    validate() {
      if (this.$refs.form.validate()) {
        this.$emit("next");
        ApiService.updateQuote();
      }
    }
  }
};
</script>
