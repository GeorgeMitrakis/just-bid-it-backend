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
    start          datetime      not null,
    end            datetime      not null,
    description    varchar(2048) not null
);
