FROM node:14.15-alpine
MAINTAINER http://davidson.fr

# Creating user app (first user)
RUN addgroup -g 1001 app && \
    adduser --system --home /app --shell /bin/sh --ingroup app -u 1001 app

RUN apk --no-cache --virtual build-dependencies add \
    python \
    make \
    g++

WORKDIR /app

COPY --chown=app . .

RUN rm -Rf node_modules && rm -Rf dist && \
    npm install && \
    npm run build && \
    apk del build-dependencies

USER 1001
EXPOSE 5000

CMD echo "VUE_APP_BACK_URL=$URL_BACK/api/v1/" > /app/.env && npm start
