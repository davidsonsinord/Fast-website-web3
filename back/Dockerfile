FROM eclipse-temurin:8-jre
MAINTAINER http://davidson.fr

# Creating user app (first user)
RUN groupadd -g 1000 app && \
    useradd --system --create-home --home-dir /app -s /bin/bash -g app -u 1000 app

RUN apt-get update && \
    apt-get install git -y

# Installing app runner
ARG RUN_JAVA_VERSION=1.3.8
RUN curl https://repo1.maven.org/maven2/io/fabric8/run-java-sh/${RUN_JAVA_VERSION}/run-java-sh-${RUN_JAVA_VERSION}-sh.sh -o /app/run-java.sh &&\
    chown 1000 /app/run-java.sh && \
    chmod 540 /app/run-java.sh

USER 1000
WORKDIR /app
COPY --chown=app target/*.jar .

EXPOSE 8080

ENTRYPOINT ["/app/run-java.sh"]
