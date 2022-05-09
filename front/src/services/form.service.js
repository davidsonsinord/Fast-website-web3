export default {
    registration:{
        id: -1,
        name: "",
        tel: "",
        mail: "",
        description: "",
        skills: [],
    },

    rules : {
        nameRules: [
            v => !!v || 'Le prénom et le nom sont requis'
        ],
        emailRules: [
            v => !!v || 'Le mail est requis',
            v => /.+@.+/.test(v) || 'Le format de données est invalide'
        ],
        phoneRules: [
            v => !!v || 'Le numéro de téléphone est requis',
            v => /\d{10}$/.test(v) || 'Le format de données est invalide'
        ]
    },

    reset() {
        this.registration.id = -1;
        this.registration.name = "";
        this.registration.tel = "";
        this.registration.mail = "";
        this.registration.description = "";
        this.registration.skills = [];
    },

    dialog : false
};