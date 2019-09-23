drop table if exists administrator;

drop table if exists bid;

drop table if exists item_categories;

drop table if exists category;

drop table if exists item;

drop table if exists common_user;

drop table if exists user;

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
    first_name              varchar(64)   not null,
    last_name               varchar(64)   not null,
    email                   varchar(128)  not null,
    phone_number            varchar(32)   not null,
    country                 varchar(32)   not null,
    location                varchar(64)   not null,
    tax_registration_number varchar(32)   not null,
    seller_rating           int default 0 not null,
    bidder_rating           int default 0 not null,
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
    buy_price      float         not null,
    number_of_bids int default 0 not null,
    location       varchar(32)   not null,
    latitude       double        null,
    longitude      double        null,
    country        varchar(32)   not null,
    start          datetime      not null,
    end            datetime      not null,
    description    varchar(512)  not null,
    constraint item_common_user_id_fk
        foreign key (seller_id) references common_user (id)
);

create table bid
(
    id        int auto_increment
        primary key,
    item_id   int      not null,
    bidder_id int      not null,
    time      datetime not null,
    amount    float    not null,
    constraint bid_casual_user_id_fk
        foreign key (bidder_id) references common_user (id),
    constraint bid_item_id_fk
        foreign key (item_id) references item (id_i)
);

create fulltext index name_and_description
    on item (name, description);

create table item_categories
(
    item_id  int          not null,
    category varchar(128) not null,
    constraint item_categories_category_name_fk
        foreign key (category) references category (name),
    constraint item_categories_item_id_fk
        foreign key (item_id) references item (id_i)
);

