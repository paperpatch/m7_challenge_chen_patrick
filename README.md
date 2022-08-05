# M7 Challenge Chen Patrick

Cognizant/SMU Java Bootcamp Course Submission - Module 7 Project 2.

To create and deploy microservices for an online music store

Please note that this is a backend activity.

## Table of Contents

* [Setup](#setup)
* [Content](#content)
* [Server API Routes](#server-api-routes)
* [Contributing](#contributing)
* [License](#license)

## Setup
:floppy_disk:

These repositories uses the following technology:
- [Java 1.8](https://docs.oracle.com/javase/8/docs/api/)
    - Java Platform Standard Ed. 8

These repositories uses the following dependency:
- Spring Boot Web
- Spring Boot Starter Validation
- Spring Boot Starter Data JPA
- MySQL Connector Java
- Spring Boot Starter Test
- Junit v4.13
- Spring Doc Open API

## Content
:computer:

Given the following `music-store-catalog` MySQL database using the following schema, accomplish the below goals:

```
drop database if exists music_store_catalog;
create database music_store_catalog;
use music_store_catalog;

create table label (
    label_id int primary key auto_increment,
    `name` varchar(50) not null,
    website varchar(255) null
);

create table artist (
    artist_id int primary key auto_increment,
    `name` varchar(50) not null,
    instagram varchar(255) null,
    twitter varchar(255) null
);

create table album (
    album_id int primary key auto_increment,
    title varchar(50) not null,
    artist_id int not null,
    release_date date not null,
    label_id int not null,
    list_price decimal(5, 2) not null,
    index fk_artist_id (album_id),
    foreign key (artist_id)
        references artist(artist_id),
    index fk_label_id (label_id),
    foreign key (label_id)
        references label(label_id)
);

create table track (
    track_id int primary key auto_increment,
    album_id int not null,
    title varchar(50) not null,
    run_time int not null,
    index fk_album_id (album_id),
    foreign key (album_id)
        references album(album_id)
);
```

Given the following `music-store-recommendations` MySQL database using the following schema, accomplish the below goals:

```
drop database if exists music_store_recommendations;
create database music_store_recommendations;
use music_store_recommendations;

create table label_recommendation (
    label_recommendation_id int primary key auto_increment,
    label_id int not null,
    user_id int not null,
    liked bool not null
);

create table artist_recommendation (
    artist_recommendation_id int primary key auto_increment,
    artist_id int not null,
    user_id int not null,
    liked bool not null
);

create table album_recommendation (
    album_recommendation_id int primary key auto_increment,
    album_id int not null,
    user_id int not null,
    liked bool not null
);

create table track_recommendation (
    track_recommendation_id int primary key auto_increment,
    track_id int not null,
    user_id int not null,
    liked bool not null
);
```

- Goals:
  - Create and deploy a Catalog microservice.
  - Create and deploy a Recommendations microservice.


## Server API Routes

This repository contains the following Heroku servers:

- [Music Store Catalog Heroku App Link](https://music-store-catalog-app.herokuapp.com/)
  - Detailed API routes and instructions can be found in [./music-store-catalog/openapi.yaml](./music-store-catalog/openapi.yaml) file. 

- [Music Store Recommendations Heroku App Link](https://music-store-recommendations.herokuapp.com/)
  - Detailed API routes and instructions can be found in [./music-store-recommendations/openapi.yaml](./music-store-recommendations/openapi.yaml) file. 


*Recommended to use API Routes through Postman or Insomnia. <br/>
*Note that you may first encounter a Whielabel Error Page as a fallback. This is normal. Please refer to the API routes to test the sites.

### MUSIC STORE CATALOG ROUTES:

| Method | Routes            |
|--------|-------------------|
| ALBUM ROUTES               |
| GET    | /albums           |
| GET    | /albums/{id}      |
| POST   | /albums           |
| PUT    | /albums/{id}      |
| DELETE    | /albums/{id}   |
| ARTIST ROUTES              |
| GET    | /artists          |
| GET    | /artists/{id}     |
| POST   | /artists          |
| PUT    | /artists/{id}     |
| DELETE | /artists/{id}     |
| LABEL ROUTES               |
| GET    | /labels           |
| GET    | /labels/{id}      |
| POST   | /labels           |
| PUT    | /labels/{id}      |
| DELETE | /labels/{id}      |
| TRACK ROUTES               |
| GET    | /tracks           |
| GET    | /tracks/{id}      |
| POST   | /tracks           |
| PUT    | /tracks/{id}      |
| DELETE | /tracks/{id}      |

### MUSIC STORE RECOMMENDATIONS ROUTES:

| Method | Routes                     |
|--------|----------------------------|
| ALBUM RECOMMENDATIONS ROUTES        |
| GET    | /albumRecommendation       |
| GET    | /albumRecommendation/{id}  |
| POST   | /albumRecommendation       |
| PUT    | /albumRecommendation/{id}  |
| DELETE | /albumRecommendation/{id}  |
| ARTIST RECOMMENDATIONS ROUTES       |
| GET    | /artistRecommendation      |
| GET    | /artistRecommendation/{id} |
| POST   | /artistRecommendation      |
| PUT    | /artistRecommendation/{id} |
| DELETE | /artistRecommendation/{id} |
| LABEL RECOMMENDATIONS ROUTES        |
| GET    | /labelRecommendation       |
| GET    | /labelRecommendation       |
| POST   | /labelRecommendation       |
| PUT    | /labelRecommendation       |
| DELETE | /labelRecommendation       |
| TRACK RECOMMENDATIONS ROUTES        |
| GET    | /trackRecommendation       |
| GET    | /trackRecommendation/{id}  |
| POST   | /trackRecommendation       |
| PUT    | /trackRecommendation/{id}  |
| DELETE | /trackRecommendation/{id}  |

## Contributing

:octocat:

[paperpatch](https://github.com/paperpatch) </br>

## License

:receipt:

No registered license.