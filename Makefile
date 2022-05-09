.DEFAULT_GOAL := help

help: ## Print this message
	@echo  "$$(grep -hE '^\S+:.*##' $(MAKEFILE_LIST) | sort | sed -e 's/:.*##\s*/:/' -e 's/^\(.\+\):\(.*\)/\x1b[36m\1\x1b[m:\2/' | column -c2 -t -s :)"
.PHONY: help

install: install-back install-front ## Build the whole solution
.PHONY: install

install-front: ## build the front application
	@cd front && npm i && npm run build && cd -
.PHONY: install-front

install-back: ## build the back application
	@cd back && ./mvnw clean install && cd -
.PHONY: install-back

run-dev: ## Run local dev environment
	@docker compose up -d
.PHONY: run-dev

run-preprod: ## Run preproduction environment
	@cd Docker/preprod && docker-compose up -d && cd -
.PHONY: run-dev

run-prod: ## Run production environment
	@cd Docker/prod && docker-compose up -d && cd -
.PHONY: run-dev

publish-docker-image: ## publish all docker images
	@docker push slavazais/davidson-fast-back:latest
	@docker push slavazais/davidson-fast-nginx:latest
	@docker push slavazais/davidson-fast-front:latest
.PHONY: publish-docker-image

build-web3-docker: ## build all docker images for web3 purpose
	@make build-web3-front-docker
	@make build-web3-nginx-docker
	@make build-web3-back-docker
.PHONY: build-web3-docker

build-web3-front-docker: ## build front hardened docker image for web3
	@docker build front -t slavazais/davidson-fast-front:latest
.PHONY: build-web3-front-docker

build-web3-nginx-docker: ## build nginx hardened docker image for web3
	@docker build nginx -t slavazais/davidson-fast-nginx:latest
.PHONY: build-web3-nginx-docker

build-web3-back-docker: install-back ## build back hardened docker image for web3
	@docker build back -t slavazais/davidson-fast-back:latest
.PHONY: build-web3-back-docker

build-push-web3-docker: build-web3-docker publish-docker-image ## build and push images to docker hub for web3
.PHONY: build-push-web3-docker





