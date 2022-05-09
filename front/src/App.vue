<template>
  <div id="app">
    <component :is="layout"></component>
  </div>
</template>

<script>
const default_layout = "default";
export default {
  name: "App",
  computed: {
    layout() {
      return (this.$route.meta.layout || default_layout) + "-layout";
    },
  },
};
</script>
<style lang="scss">
@import "assets/davidson-graphic-charter";

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-color: white;
}
</style>
