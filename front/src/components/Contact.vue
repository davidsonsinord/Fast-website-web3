<template>
  <v-container grid-list-xl>
    <v-layout row wrap justify-center>
      <v-flex xs12 sm4>
        <v-card class="elevation-0 transparent">
          <v-card-title primary-title class="layout justify-center">
            <div class="headline">
              Davidson
              <img class="pl-2" height="30" src="../assets/davidson_logo.png" />
            </div>
          </v-card-title>
          <v-card-text>
            Créée en 2005, Davidson est une société de conseil en management et
            expertise technologique.
            <br />
            <br />Nous sommes présents dans 8 pays repartis en Europe, aux
            Emirats Arabes Unis et en Amérique du Nord.
          </v-card-text>
        </v-card>
      </v-flex>
      <v-flex xs12 sm4 offset-sm1>
        <v-card class="elevation-0 transparent">
          <v-card-title primary-title class="layout justify-center">
            <div class="headline">Nous contacter</div>
          </v-card-title>
          <v-list class="transparent">
            <v-list-tile>
              <v-list-tile-action>
                <v-icon class="turquoise-dav">phone</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <v-list-tile-title>{{ phone }}</v-list-tile-title>
              </v-list-tile-content>
            </v-list-tile>
            <v-list-tile>
              <v-list-tile-action>
                <v-icon class="turquoise-dav">place</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <v-list-tile-title>{{ place }}</v-list-tile-title>
              </v-list-tile-content>
            </v-list-tile>
            <v-list-tile>
              <v-list-tile-action>
                <v-icon class="turquoise-dav">email</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <v-list-tile-title>{{ email }}</v-list-tile-title>
              </v-list-tile-content>
            </v-list-tile>
          </v-list>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "Contact",
  data: () => ({
    phone: "Nicolas Lecaplain",
    place: "12, place Saint Hubert, 59000 Lille",
    email: "fast@davidson.fr",
  }),
};
</script>

<style scoped></style>
