# Fast-website

Website [FAST](http://fast-web.demo.projet-davidson.fr/#/home) by [Davidson SI Nord](https://www.davidson.fr/filiale/davidson-nord-2/davidson-si-nord/)

## Install and run the project

### pre-requisite

 - `nodeJs` version `14.15.0`
 - `npm` version `6.14.8`
 - `java` version `1.8` (openjdk)

NOTE: you can use `nvm` to install node version and `sdkman` to install java version

### install

* Clone the project `git clone https://github.com/davidsonsinord/Fast-website.git`
* Go in the project `cd Fast-website`
* Install and build the front `cd front && npm install && npm run build && cd ..`
* Install and build the back `cd back && mvn clean install && cd ..`
* Run the project with the profile dev `cd Docker/dev && docker-compose build && docker-compose up`

or

 - use the makefile
```shell
make install && make run-dev
```

then

 - Open a browser and go to "http://localhost:5000/"

## Run front for development

* Go to front `cd front`
* Launch `npm run serve`

## Add a new event in the agenda

* Google Sheet is accessible in the Google Drive of davidson.is.so.fast@gmail.com. 
"En production - Site FAST - Agenda"

## Write an post in the blog 📝

* clone the project that stores the trainings `git clone https://github.com/davidsonsinord/S_for_Speakers.git`
* to **add or remove an article** create a JSON file in the Articles folder :

* Then, copy this content in this file :

```markdown

---
title: my first post
date: "2018-03-20T15:14:32.169Z"
author: your name
image: @/assets/<cover_image>
---

Your content

```
* Feel free to add some images, medias (see our articles already present)...
* And make your Pull Request !

## Write a testimony on main page

* clone the project that stores the trainings `git clone https://github.com/davidsonsinord/S_for_Speakers.git`
* to **add or remove an article** create a JSON file in the Testimonies folder :

* Then, copy this content in this file :

```json
{
  "nom":"Nom Prénom",
  "image":"nom de l'image",
  "description":"description"
}
```
* Add the icon relative to your testimony into the image directory...
* And make your Pull Request !

## Add, delete or modify a skill, sub-domains and domains


* clone the project that stores the trainings `git clone https://github.com/davidsonsinord/F_for_Formations.git`
* to **add or delete a domain** create a folder with the domain name or delete the folder with the desired domain name
* to **add or delete a subdomain** create a folder with the subdomain name in a domain folder or delete the folder with the desired subdomain name
* to **add or remove a formation** create a JSON file in a subdomain folder with the following content :

```json
{
  "title" : "Title of formation",
  "description" : "Description of formation"
}
```
* And make your Pull Request !

## Annexes

* If the project is launched with docker go to "http://localhost:8080/swagger-ui.html" to get the complete documentation of the API
