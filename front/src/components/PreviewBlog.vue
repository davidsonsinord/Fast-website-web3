<template>
    <v-layout column align-center justify-center wrap class="my-1">
        <v-flex xs12 sm4 class="my-3">
            <div class="text-xs-center">
                <h2 class="headline title ATCArquette-Medium turquoise-dav">{{title}}</h2>
                <span class="subheading ATCArquette-Light">
                        {{subtitle}}
                    </span>
            </div>
        </v-flex>
        <v-layout row wrap align-center justify-center v-if="preview">
            <card-blog-preview :most="posts.slice(0,3)"></card-blog-preview>
        </v-layout>
        <v-layout row wrap align-center justify-center v-else>
            <card-blog-preview :most="posts"></card-blog-preview>
        </v-layout>
        <div v-if="preview">
            <v-btn to="/blog" id="button_blog_home" class="mb-4 btn" v-on:click.native="track_blog" >Accéder au blog</v-btn>
        </div>
    </v-layout>
</template>

<script>

    import CardBlogPreview from './CardBlogPreview';

    export default {
        name: "PreviewBlog",
        props: {
            title: String,
            subtitle: String,
            preview: Boolean,
            posts: []
        },
        components: {
            CardBlogPreview
        },
        created() {
            this.$ga.page(this.$router)
        },
        methods: {
            track_blog: function (event) {
                this.$ga.event('Blog', "accès au blog depuis la page d'accueil", 'Blog accueil');
            }
        }
    }
</script>

<style scoped>

</style>