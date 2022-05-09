export default {

    URL_BACK: process.env.VUE_APP_BACK_URL,


    getSkill(id) {
        return fetch(this.URL_BACK + 'skill/' + id)
            .then(response => {
                if(response.status === 200){
                    return response.json();
                }
                return null;
            })
            .then(data => {
                return data;
            });
    }
};