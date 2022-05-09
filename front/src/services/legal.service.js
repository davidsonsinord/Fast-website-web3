export default {
    getAll: function () {
        return fetch('LegalNotices.md')
            .then((response) => response.text())
            .then((data) => {
                return data;
            })
    },
};