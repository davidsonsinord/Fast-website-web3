<template>
  <div>
    <ModalAdaptive />
    <v-parallax :src="require('@/assets/cover2.jpg')" id="cover">
      <v-layout column align-center justify-center>
        <div
          class="headline white--text mb-3 text-xs-center"
          style="font-size: 30px"
        >
          Vous avez un besoin ?
        </div>
        <em style="font-size: 19px">N'hésitez pas à nous en faire part !</em>
        <v-list class="transparent">
          <v-list-tile>
            <v-list-tile-action>
              <v-icon class="near-white-dav">phone</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <v-list-tile-title class="near-white-dav">{{
                phone
              }}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-list-tile>
            <v-list-tile-action>
              <v-icon class="near-white-dav">place</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <v-list-tile-title class="near-white-dav">{{
                place
              }}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-list-tile>
            <v-list-tile-action>
              <v-icon class="near-white-dav">email</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <v-list-tile-title class="near-white-dav">{{
                email
              }}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
        </v-list>
      </v-layout>
    </v-parallax>
  </div>
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

<style scoped>
#cover {
  height: 280px !important;
}
</style>
