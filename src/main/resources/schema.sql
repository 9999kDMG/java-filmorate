CREATE TABLE IF NOT EXISTS USERS
(
    USER_ID  INTEGER auto_increment
        primary key,
    EMAIL    CHARACTER VARYING     not null,
    LOGIN    CHARACTER VARYING(50) not null,
    NAME     CHARACTER VARYING(50) not null,
    BIRTHDAY DATE                  not null
);
CREATE TABLE IF NOT EXISTS FRIENDSHIP
(
    USER_ID   INTEGER not null,
    FRIEND_ID INTEGER not null,
    constraint FRIENDSHIP_USERS_USER_ID_FK
        foreign key (USER_ID) references USERS
            on delete cascade,
    constraint "FRIENDSHIP_USERS_USER_ID_fk"
        foreign key (FRIEND_ID) references USERS
            on delete cascade
);
CREATE TABLE IF NOT EXISTS MPA
(
    MPA_ID INTEGER AUTO_INCREMENT PRIMARY KEY,
    NAME   VARCHAR(10) UNIQUE NOT NULL
);
CREATE TABLE IF NOT EXISTS FILM
(
    FILM_ID      INTEGER auto_increment
        primary key,
    NAME         CHARACTER VARYING(50)  not null,
    DESCRIPTION  CHARACTER VARYING(200) not null,
    RELEASE_DATE DATE                   not null,
    DURATION     INTEGER                not null,
    MPA_ID       INTEGER                not null,
    constraint FILM_MPA_MPA_ID_FK
        foreign key (MPA_ID) references MPA
);
CREATE TABLE IF NOT EXISTS LIKES
(
    FILM_ID INTEGER not null,
    USER_ID INTEGER not null,
    constraint LIKES_FILM_FILM_ID_FK
        foreign key (FILM_ID) references FILM
            on delete cascade,
    constraint LIKES_USERS_USER_ID_FK
        foreign key (USER_ID) references USERS
            on delete cascade
);
CREATE TABLE IF NOT EXISTS GENRE
(
    GENRE_ID INTEGER auto_increment
        primary key,
    NAME     CHARACTER VARYING(30) not null
);
CREATE TABLE IF NOT EXISTS FILM_GENRES
(
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint FILM_GENRES_FILM_FILM_ID_FK
        foreign key (FILM_ID) references FILM
            on delete cascade,
    constraint FILM_GENRES_GENRE_GENRE_ID_FK
        foreign key (GENRE_ID) references GENRE
            on delete cascade
);
