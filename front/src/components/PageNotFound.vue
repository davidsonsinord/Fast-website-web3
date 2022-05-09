<template>
    <v-app light>
        <v-content class="mt-5 h-50">
            <v-layout justify-center>
                <v-flex class="text-xs-center">
                    <v-img :src="gif[Math.floor(Math.random() * Math.floor(4))]" aspect-ratio="5" contain></v-img>
                    <div>
                        <h2 class=" oops ATCArquette-Medium turquoise-dav mt-3 mb-3">Oops ... </h2>
                        {{ text }}
                    </div>
                    <v-btn to="/home" id="button_blog_home" class="mb-4 btn mt-3">{{ button}}</v-btn>
                </v-flex>
            </v-layout>

        </v-content>
    </v-app>
</template>

<script>
    export default {


        name: "PageNotFound",
        data: () => ({
            gif: ['https://media.giphy.com/media/3o7TKrau6cMz9BhKRa/giphy.gif', 'https://media.giphy.com/media/3o7aCTPPm4OHfRLSH6/giphy.gif','https://media.giphy.com/media/9JgjpeyN48rdtzIvOZ/giphy.gif','https://media.giphy.com/media/l4Epf0KwYUQY5DcGc/giphy.gif' ],
            text: "Il me semble que vous vous êtes perdu en route ! Cette page n'existe pas ou a peut-être été déplacée.",
            button: "Retourner à l'accueil"
        })
    };
</script>

<style scoped>

    .oops{
        font-size:42px;
    }
</style>