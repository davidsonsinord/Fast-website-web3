export default {

    URL_BACK: process.env.VUE_APP_BACK_URL,

    getAllTestimony() {

        return fetch(this.URL_BACK + 'testimonies')
            .then((response) => response.json())
            .then((data) => {
                return data;
            })
    },

    init() {
        this.testimony = this.getAllTestimony();
    },

    testimony: []

};