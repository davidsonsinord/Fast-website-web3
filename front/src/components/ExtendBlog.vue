<template>
    <v-layout column justify-center align-center>
        <v-layout class="card row wrap mt-1" v-for="(item, i) in posts" :key="i" :class="{'background': i % 2 === 0}">

            <v-container grid-list-md class="content" v-scroll="onScroll">
                <v-layout row wrap>
                    <v-flex xs12>
                        <v-img style="height:500px" :src="getCover(item)"></v-img>
                    </v-flex>
                    <v-flex class="social_sharing">
                        <vue-goodshare-facebook
                                :page_url="getUrl(item)"
                                title_social="Partager"
                                has_icon
                        ></vue-goodshare-facebook>
                        <vue-goodshare-twitter
                                :page_url="getUrl(item)"
                                title_social="Twitter"
                                has_icon
                        ></vue-goodshare-twitter>
                        <vue-goodshare-linked-in
                                :page_url="getUrl(item)"
                                title_social="Partager"
                                has_icon
                        ></vue-goodshare-linked-in>
                    </v-flex>
                    <v-flex xs12 class="article">
                        <p class="display-3 title_article">{{ item.title }}</p>
                        <p class="subheading">
                            Auteur : {{ item.author }} |
                            <span>{{ item.date | formatDate }}</span>
                        </p>
                        <v-divider></v-divider>
                        <div id="markdown" v-html="$marked(item.markdown)"></div>
                    </v-flex>
                </v-layout>
            </v-container>

        </v-layout>
        <v-fade-transition>
            <v-btn
                    color="pink"
                    v-if="scrolldown"
                    dark
                    fixed
                    bottom
                    right
                    fab
                    @click="$vuetify.goTo('#top-anchor')"
            >
                <v-icon>keyboard_arrow_up</v-icon>
            </v-btn>
        </v-fade-transition>
    </v-layout>
</template>

<script>
    import VueGoodshareFacebook from "vue-goodshare/src/providers/Facebook";
    import VueGoodshareLinkedIn from "vue-goodshare/src/providers/LinkedIn";
    import VueGoodshareTwitter from "./Twitter";

    export default {

        name: "ExtendBlog",
        components: {
            VueGoodshareTwitter,
            VueGoodshareLinkedIn,
            VueGoodshareFacebook
        },
        created() {
            window.scrollTo(0, 0);
        },
        props: {
            posts : []
        },
        data: () => ({
            offsetTop: 0,
            scrolldown: false,
        }),
        methods: {
            onScroll() {
                let mark = document.getElementById("markdown");
                if (window.scrollY > mark.offsetTop) {
                    this.scrolldown = true;
                }
                if (window.scrollY < mark.offsetTop) {
                    this.scrolldown = false;
                }
            },
            getUrl(item){
                return 'https://fast.projet-davidson.fr/posts/' + item.title.replace(/\s+/g, '%20');
            },
            getCover(article){
                return process.env.VUE_APP_BACK_URL + "img" + article.cover;
            }
        }
    };
</script>

<style scoped>
    .background{
        border-top:1px solid lightgrey;
        border-bottom:1px solid lightgrey;
        background-color:rgba(238,240,239,.5) !important;
        width:100%;
    }

    .subheading{
        padding-left:5.3%;
    }

    .title_article {
        padding-left:5%;
        font-family:'Roboto Condensed', sans-serif !important;
        text-transform: uppercase;
        font-weight: bolder;
        margin-bottom:0;
        font-size:50px !important;
    }

    .social_sharing {
        text-align:right;
    }
    #markdown{
        margin-top:3%;
        text-align: justify;
    }

    .article {
        margin-right: 10%;
        margin-left: 10%;
    }
    @media only screen and (max-width: 959px) {
        #markdown /deep/ img {
            width: 350px !important;
            height: 200px !important;
        }
        #markdown /deep/ code {
            width: 350px !important;
        }
        .article {
            margin-right: 0%;
            margin-left: 0%;
        }
    }
</style>