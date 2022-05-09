import FormService from "./form.service";

export default {
  URL_BACK: process.env.VUE_APP_BACK_URL,

  createQuote() {
    fetch(this.URL_BACK + "quotes", {
      method: "POST",
      body: JSON.stringify(FormService.registration),
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        FormService.registration.id = data.id;
      });
  },

  updateQuote() {
    fetch(this.URL_BACK + `quotes/${FormService.registration.id}`, {
      method: "PUT",
      body: JSON.stringify(FormService.registration),
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    });
  },

  validateQuote() {
    fetch(this.URL_BACK + `quotes/${FormService.registration.id}/validate`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    });
  },

  getAllQuotes() {
    return fetch(this.URL_BACK + "quotes")
      .then((response) => response.json())
      .then((data) => {
        return data;
      });
  },
};
