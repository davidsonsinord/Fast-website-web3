<template>
    <div id="default">
    <CookieBanner/>
    <Header :key="$route.fullPath"/>
        <router-view ></router-view>
    <Footer/>
    </div>
</template>

<script>
    import Header from "./../components/Header";
    import Footer from "./../components/Footer";
    import CookieBanner from "./../components/CookieBanner";
    import BlogService from "./../services/blog.service";
    import DomainService from "./../services/domain.service";
    import TestimonyService from "./../services/testimony.service";

    export default {
        name: 'default',
        components: {
            CookieBanner,
            Header,
            Footer
        },
        created() {
            BlogService.init();
            DomainService.init();
            TestimonyService.init();
        },
        data() {
            return {}
        }
    }
</script>

<style scoped>

</style>