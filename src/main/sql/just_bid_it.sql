drop table if exists administrator;

drop table if exists bid;

drop table if exists item_categories;

drop table if exists category;

drop table if exists item;

drop table if exists common_user;

drop table if exists `user`;

create table category
(
    id   int auto_increment
        primary key,
    name varchar(128) not null,
    constraint category_name_uindex
        unique (name)
);

create table user
(
    id       int auto_increment
        primary key,
    username varchar(128) not null,
    password varchar(256) not null,
    role     varchar(16)  not null,
    access   varchar(16)  not null,
    constraint user_username_uindex
        unique (username)
);

create table administrator
(
    id         int         not null
        primary key,
    first_name varchar(64) not null,
    last_name  varchar(64) not null,
    constraint administrator_user_id_fk
        foreign key (id) references user (id)
            on delete cascade
);

create table common_user
(
    id                      int           not null
        primary key,
    first_name              varchar(64)   null,
    last_name               varchar(64)   null,
    email                   varchar(128)  null,
    phone_number            varchar(32)   null,
    country                 varchar(32)   null,
    location                varchar(64)   null,
    tax_registration_number varchar(32)   null,
    seller_rating           int default 0 null,
    bidder_rating           int default 0 null,
    constraint casual_user_email_uindex
        unique (email),
    constraint casual_user_phone_number_uindex
        unique (phone_number),
    constraint casual_user_user_id_fk
        foreign key (id) references user (id)
            on delete cascade
);

create table item
(
    id             int auto_increment
        primary key,
    seller_id      int           not null,
    name           varchar(256)  not null,
    current_bid    float         not null,
    first_bid      float         not null,
    buy_price      float         null,
    number_of_bids int default 0 not null,
    location       varchar(32)   not null,
    latitude       double        null,
    longitude      double        null,
    country        varchar(32)   not null,
    start          varchar(32)   not null,
    end            varchar(32)   not null,
    description    varchar(10000) not null,
    constraint item_common_user_id_fk
        foreign key (seller_id) references common_user (id)
);

create table bid
(
    id        int auto_increment
        primary key,
    item_id   int         not null,
    bidder_id int         not null,
    time      varchar(32) not null,
    amount    float       not null,
    constraint bid_casual_user_id_fk
        foreign key (bidder_id) references common_user (id),
    constraint bid_item_id_fk
        foreign key (item_id) references item (id)
);

create fulltext index name_and_description
    on item (name, description);

create table item_categories
(
    item_id  int          not null,
    category varchar(128) not null,
    primary key (item_id, category),
    constraint item_categories_category_name_fk
        foreign key (category) references category (name),
    constraint item_categories_item_id_fk
        foreign key (item_id) references item (id)
            on delete cascade
);

create table message
(
    id          int auto_increment
        primary key,
    sender_id   int                  not null,
    receiver_id int                  not null,
    text        varchar(2048)        not null,
    time        varchar(32)          null,
    `read`      tinyint(1) default 0 not null,
    constraint message_common_user_id_fk
        foreign key (sender_id) references common_user (id),
    constraint message_common_user_id_fk_2
        foreign key (receiver_id) references common_user (id)
);

