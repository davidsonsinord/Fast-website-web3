export default {
  URL: process.env.VUE_APP_BACK_URL,

  getAll() {
    return fetch(this.URL + 'posts')
      .then(response => response.json())
      .then(data => {
        return data;
      });
  },

  getByTitle(titleArticle){
    var url = new URL(this.URL + 'posts'),
        params = {title:titleArticle};
    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
    return fetch(url)
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
    this.articles = this.getAll();
  },

  articles: []
};
