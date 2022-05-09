<template v-show="snackbar">
    <v-card>
        <v-card-text class="pa-0">
            <v-container fluid>
                <v-layout >
                    <v-flex class="ma-0">
                        <v-snackbar id="snack" class="ma-0 turquoise-dav cookie_bar"
                                v-model="snackbar"
                                :color="color"
                                :multi-line="mode === 'multi-line'"
                                :timeout="timeout"
                                :vertical="mode === 'vertical'"
                                :auto-height="true"
                        >
                            {{ text }}
                            <v-btn class="bg-turquoise-dav button"
                                    text
                                    @click=cookieFalse
                            >
                                Accepter
                            </v-btn>
                        </v-snackbar>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-card-text>
    </v-card>
</template>

<script>
    import cookie from 'js-cookie';

    export default {
        name: "CookieBanner",
        data () {
            return {
                snackbar: false,
                color: 'white',
                mode: '',
                timeout: 0,
                text: 'Ce site Web utilise des cookies pour s\'assurer que vous obtenez la meilleure expérience',
                bottom: true,
            }
        },
        created() {
            if(cookie.get('snackbar') === undefined) {
                cookie.set('snackbar', true);
                this.snackbar = true;
            }
        },
        methods: {
            cookieFalse() {
                let cookieName = 'snackbar';
                cookie.set(cookieName, false);
                this.snackbar = false;
            }
        }
    }
</script>

<style scoped>
    .cookie_bar div{
        padding-right:0.6% !important;
    }

    .button {
        margin-right:0.6%;
    }
</style>