export default {

    URL_BACK: process.env.VUE_APP_BACK_URL,

    getAllDomains() {
        return fetch(this.URL_BACK + 'domains')
            .then(response => {
                if(response.status === 200){
                    return response.json();
                }
                return null;
            })
            .then(data => {
                return data;
            });
    },

    init() {
        this.domains = this.getAllDomains();
    },

    domains: []

};