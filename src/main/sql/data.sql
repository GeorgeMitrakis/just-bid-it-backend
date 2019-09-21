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

INSERT INTO just_bid_it.administrator (id, first_name, last_name) VALUES (1, 'Andreas', 'Papandreou');
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

INSERT INTO just_bid_it.bid (id, item_id, bidder_id, time, amount) VALUES (1, 5, 9, '2019-08-30 02:05:24', 17.3);
INSERT INTO just_bid_it.bid (id, item_id, bidder_id, time, amount) VALUES (2, 5, 9, '2019-08-30 02:10:06', 18);
INSERT INTO just_bid_it.bid (id, item_id, bidder_id, time, amount) VALUES (3, 1, 12, '2019-08-31 00:05:18', 18);
INSERT INTO just_bid_it.bid (id, item_id, bidder_id, time, amount) VALUES (4, 4, 12, '2019-08-31 18:27:23', 18);
INSERT INTO just_bid_it.bid (id, item_id, bidder_id, time, amount) VALUES (5, 5, 9, '2019-09-02 00:07:29', 20);
INSERT INTO just_bid_it.bid (id, item_id, bidder_id, time, amount) VALUES (6, 8, 9, '2019-09-02 00:20:38', 20);
INSERT INTO just_bid_it.bid (id, item_id, bidder_id, time, amount) VALUES (7, 12, 9, '2019-09-02 00:26:12', 17.02);
INSERT INTO just_bid_it.bid (id, item_id, bidder_id, time, amount) VALUES (8, 12, 9, '2019-09-02 00:28:05', 17.03);
INSERT INTO just_bid_it.bid (id, item_id, bidder_id, time, amount) VALUES (9, 12, 9, '2019-09-02 00:28:30', 20);
create table category
(
    id   int auto_increment
        primary key,
    name varchar(128) not null,
    constraint category_name_uindex
        unique (name)
);

INSERT INTO just_bid_it.category (id, name) VALUES (1, 'Apple');
INSERT INTO just_bid_it.category (id, name) VALUES (2, 'Banana');
INSERT INTO just_bid_it.category (id, name) VALUES (11, 'Books');
INSERT INTO just_bid_it.category (id, name) VALUES (3, 'Carrots');
INSERT INTO just_bid_it.category (id, name) VALUES (6, 'Cars');
INSERT INTO just_bid_it.category (id, name) VALUES (7, 'Clothing');
INSERT INTO just_bid_it.category (id, name) VALUES (13, 'Computers');
INSERT INTO just_bid_it.category (id, name) VALUES (14, 'Energy Drinks');
INSERT INTO just_bid_it.category (id, name) VALUES (4, 'Food');
INSERT INTO just_bid_it.category (id, name) VALUES (5, 'Grocery');
INSERT INTO just_bid_it.category (id, name) VALUES (8, 'Hardware');
INSERT INTO just_bid_it.category (id, name) VALUES (10, 'Music');
INSERT INTO just_bid_it.category (id, name) VALUES (20, 'Orange');
INSERT INTO just_bid_it.category (id, name) VALUES (17, 'Shampoo');
INSERT INTO just_bid_it.category (id, name) VALUES (16, 'Shoes');
INSERT INTO just_bid_it.category (id, name) VALUES (18, 'Socks');
INSERT INTO just_bid_it.category (id, name) VALUES (9, 'Tools');
INSERT INTO just_bid_it.category (id, name) VALUES (15, 'Video Equipment');
INSERT INTO just_bid_it.category (id, name) VALUES (12, 'Videogames');
INSERT INTO just_bid_it.category (id, name) VALUES (19, 'Weird Toys');
create table common_user
(
    id                      int           not null
        primary key,
    first_name              varchar(64)   not null,
    last_name               varchar(64)   not null,
    email                   varchar(128)  not null,
    phone_number            varchar(16)   not null,
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

INSERT INTO just_bid_it.common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (9, 'George', 'Mitrakis', 'dunno', '654', 'Syria', 'bikkini bottom', '111', 0, 0);
INSERT INTO just_bid_it.common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (11, 'George', 'Mitrakis', 'dunno2', '6541', 'Syria', 'bikkini bottom', '1112', 0, 0);
INSERT INTO just_bid_it.common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (12, 'George2', 'Mitrakis2', 'dunno22', '65412', 'Syria2', 'bikkini bottom2', '11122', 0, 0);
INSERT INTO just_bid_it.common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (13, 'George21', 'Mitrakis21', 'dunno221', '654121', 'Syria21', 'bikkini bottom21', '111221', 0, 0);
INSERT INTO just_bid_it.common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (14, 'whatever', 'whatever', 'whatever', 'whatever', 'whatever', 'whatever', 'whatever', 0, 0);
INSERT INTO just_bid_it.common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (15, 'whatever', 'whatever', 'whateverrfewr', 'whateverqecv', 'whatever', 'whatever', 'whateverqvewf', 0, 0);
INSERT INTO just_bid_it.common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (16, 'whatever', 'whatever', 'whateverq', 'whatever111', 'whatever', 'whatever', 'whatever2314', 0, 0);
INSERT INTO just_bid_it.common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (17, 'whatever', 'whatever', 'wha@fsv.m', 'whatever1114', 'whatever', 'whatever', 'whateverqvewf4', 0, 0);
INSERT INTO just_bid_it.common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (18, 'whatever', 'whatever', 'whateverq@aev.as', 'whateverqecvads', 'whatever', 'whatever', 'whatever2314esa', 0, 0);
INSERT INTO just_bid_it.common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (19, 'whatever', 'whatever', 'gs_mail@otenet.gr', 'whateverqecv1221', 'whatever', 'whatever', 'whateverqvewf12212121', 0, 0);
INSERT INTO just_bid_it.common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (20, 'whatever', 'whatever', 'whateverrfewr@vdsasdv.n', 'whatever1111', 'whatever', 'whatever', 'whateverqvewf1', 0, 0);
INSERT INTO just_bid_it.common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (21, 'maria', 'xan', 'ma@hotmail.com', '849297373', 'greece', 'athens', '828292', 0, 0);
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

create fulltext index name_and_description
    on item (name, description);

INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (1, 9, 'christopher radko | fritz n_ frosty sledding', 18, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'brand new beautiful handmade european blown glass ornament from christopher radko. this particular ornament features a snowman paired with a little girl bundled up in here pale blue coat sledding along on a silver and blue sled filled with packages. the ornament is approximately 5_ tall and 4_ wide. brand new and never displayed, it is in its clear plastic packaging and comes in the signature blac');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (2, 9, 'PM BOY AND GIRL EXCLUSIVE EVENT PIECE LE', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'YOU ARE ALWAYS IN MY HEART IS THE NAME OF IT ,HAS A BOY AND A GIRL EACH SITTING IN A HEART SHAPED SHELL ON A BENCH THESE PIECES ARE RARE AND EXCLUSIVE YOU GET BOTH PIECES IN YOUR BID THEY ARE MINT NEVER BEEN DISPLAYED BRAND NEW IN BOXES .... ALSO TAKE PAYPALBUYER PAYS 8.00 SHIPPING');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (3, 9, 'Precious Moments Girl Stove Mini Tea Set', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'PRECIOUS MOMENTS GIRL/STOVE MINI TEA SET: This really cute little mini decorative teaset is mint in box. It was only removed for photo. It is about 6 inches across. Original price was $20.00. Buyer to pay postage and handling of $4.50 for delivery in USA.');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (4, 9, 'Beatrix Potter Tailor of Gloucester Shakers', 18, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'BEATRIX POTTER TAILOR OF GLOUSCESTER SHAKERS: Really cute set of figural salt and pepper shakers of the mouse tailor and two spools of thread. It is mint in box. Original price was $15.00. Buyer to pay postage and handling of $4.50 for delivery in USA.');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (5, 9, 'Precious Moments Fig-ANGEL OF MERCY- NURSE', 20, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'Precious Moments Fig-ANGEL OF MERCY- NURSE Click picture to enlarge Makes a great gift for Collectors Description Up for bids is this great figurine from Precious Moments. It is #102482, ANGEL OF MERCY. It is a 5 1/2 inch figurine of an angel nurse. She is carrying a plant. It is really cute, just look at the pictures. It was released in 1986. This figurine bears the FIRST production mark of the O');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (6, 9, 'Town Crier CD102', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'Dreamsicle Club Members Only Figurine 1995 4 1/2 Issued:1995 * Retired:1995 This piece is in mint condition and in the original box. Free Honesty Counters powered by Andale! Payment Details See item description and Payment Instructions, or contact seller for more information. Payment Instructions See item description or contact seller for more information.');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (7, 9, 'Hertz Corporation Bond Certificate 1970''s', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'Scripophily Makes a Great Gift for Everyone Our Stocks are worth the paper they''re printed on (sm) Our Company is a TRUSTED Buyer and Seller of Old Stock Certificates. Check out our About Me Page , where you can find out about us and read the recent articles about our company in Time Magazine, The Wall Street Journal, The Globe and Mail, Washington Post and our appearence on CNN. Beautifully engra');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (8, 9, 'Hallmark TIN Mail Truck Series', 20, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'This ornie is mint in mint box. It has never been displayed. It comes from a smoke free home. I charge $5.00 to ship Priority insured mail by USPS. I can usually ship as many as 3 ornies for the price of one. I accept Paypal, checks. and money orders. I ship the next day on Money orders and Paypal. If you have at least 10 Positive feedbacks with no negatives I do not hold your check for clearnace.');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (9, 9, 'Authentic Avon Quartz Silver/Gold Watch NIB', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'This is new in the box bought for Avon representitives to wear. Made with high quality quartz movement which provides accuracy within seconds per month. Silver & gold adjustable band, gold face with Let''s Talk Avon, surrounded with diamonds around the face. 2000 Item, save & keep as a collectible for years to come! Ship $4, paypal/money orders ship same day, personal checks held 10 days. Buy it ');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (10, 9, 'HANDBLOWN, ETCHED GLASS BRANDY SNIFTER', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'HANDBLOWN, ETCHED GLASS BRANDY SNIFTER. The sides are impressed in for easy holding. Bottom is white and pedestal is applied to glass. Glass is 6-1/2_ tall and base is 2_ tall and 3_ in diameter at bottom. The color of the glass is a smoky gray. This is a unique snifter with no damage at all in any way _ no chips, cracks, breaks, or hairlines. The etched glass design is wheat stalk. PLEASE SEE PHO');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (11, 9, 'CGC 9.0 80 PAGE GIANT #8 SECRET ORIGINS VF/NM', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'According to the CGC census their are only 3 higher graded copies of this book in the world. KILLER BOOK! One owner (CAPTWHIZ)! COMBINE SHIPPING! This comic book is graded VFN/NM 9.0 by CGC! Any waves, bends or flashes in the photos is just BAD photography. This is the BLUE LABEL which means NO RESTORATION!! NUFF SAID! The NM price is $450.00. SUPERFAST shipping only $3.95, international extra! Pa');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (12, 9, 'NEVADA ghost town bottle digging book, 1961', 20, 15.5, 20, 3, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'GREAT BOOK from the early heyday of bottle digging. This 55 page book by Adele Reed describes visits to 12 Nevada ghost towns and 4 California ghost towns, a description of what remained of the town and the bottles they dug in each town. It is autographed by the author. The Nevada towns included Goldfield, Virginia city, Sutro, Aurora, Belleville, Teel''s Marsh, Candelaria, Belmont, Manhattan, Eure');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (13, 9, 'PRECIOUS MOMENTS 15 HAPPY YEARS A TWEET BIRDS', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', '15 HAPPY YEARS TOGETHER WHAT A TWEET! COMMEMORATIVE ANNUAL 1993 FIGURINE 1993 BUTTERFLY SYMBOL Figurine is 5.25_ tall by 8_ long. Bunnies listen as Angel leads BLUEBIRD Choir. A beauty of a figurine. This is the Commemorative Annual 1993 figurine _ Butterfly Symbol. Figurine is brand new in PRECIOUS MOMENTS purple box _ never used _ and comes with original hang tag, tent card and paperwork. ****AL');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (14, 9, '~~~50~~~8.5x14.5 BUBBLE MAILERS #3~~BUY NOW!!', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', '***SELF-SEALING*** *** BUBBLE MAILERS *** All text and photos in this ad are protected under Copyright 2001 BargainsGaloreStore All Rights Reserved This auction is for 8.5x14.5 #3 mailers ONLY!!! ***This size is not pictured above but is the SAME brand as those pictured Other sizes ARE available!!! For additional shipping supplies, simply visit my other Ebay Auctions!!!! ***** You may combine mult');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (15, 9, 'ENESCO MARY''S MOO MOOS COW COUPLE W BABY', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'ENESCO MARY''S MOO MOO COUPLE W BABY FIGURINE ..THIS PIECE IS A MUST FOR THE COLLECTOR OF M/M/M/ ..THIS PIECE IS FROM THE 1996 COLLECTION ..RETAIL IN 96 WAS $48 GET THIS CHERISHED PIECE FOR A LOT LESS ...5.95 FOR S & H GOOD LUCK!!!!!! Free Honesty Counters powered by Andale! Payment Details See item description and Payment Instructions, or contact seller for more information. Payment Instructions S');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (16, 9, 'Fantastic Four #79 NR paypal VG', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'Here is a great comic. It is Fantastic Four #79. Comic is in VG condition or better (I always try to undergrade my comics). Comic has little wear along the spine and on the top of the comic. Comic has all its pages, pages are in great shape, staples are firm and rust free. Please check my other auctions for Silver Age X-Men and other Fantastic Four. Check in the future for Avnegers #1, Amazing Spi');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (17, 9, '1955 Churchill Queen Elizabath Nehru photo', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', '.bgprimary { background-color:#FFCC00; } .bgsecondary { background-color:#FF6600; } .bgaccent { background-color:#00CC00; } .bgaccentlight { background-color:#CCFFCC; } HR { color:#00CC00; } .textColor { color:#FF6600; font-family: Verdana, helvetica, sans-serif;} .titledata{ color: #FFFFFF; font-family: Verdana, helvetica, sans-serif;} .description {font-family: verdana, sans-serif; color:#000000');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (18, 9, 'Set of 6 glass coasters w/characters', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'I am selling a set of 6 coasters with different characters on them. One is a ballerina with a pink and red dress. Another is a bartender with a big mustache mixing a drink. There is a man in his green pajamas (I think) holding his head with stars swimming around his head (possibly a headache). Next is a couple of drunks sitting by a lamppost. And the last two are the same of a dancer with a top ha');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (19, 9, 'BATMAN no 58 from 1950. Rare', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', '.titledata { FONT-WEIGHT: bold; FONT-SIZE: 18px; COLOR: #000000; FONT-FAMILY: verdana, sans-serif } .description { COLOR: #000000; FONT-FAMILY: verdana, sans-serif } .link { FONT-FAMILY: verdana, sans-serif; underline: } HR { color: #000000; } BATMAN no 58 from 1950. Rare Click image to view larger version Here is a rarely seen copy of BATMAN NO 58 from 1950. the comic is only in Fair condition, c');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (20, 9, 'Classics Illustrated, No.162, 1961, (O), F/F-', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'Classics Illustrated, No.162, ROBUR THE CONQUEROR, by Jules Verne, HRN 162, The Original, published by Gilberton Company Inc., in 1961, is in Fine/Fine- condition. the front cover looks Fine, very light edgewear around page showing. The Spine is in Fine- condition, light edgewear around bottom staple barely noticeable. The back cover is fine condition, very light age yellowing and the lightest of ');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (21, 9, 'Vintage Anchor Hocking Salt & Pepper Shakers', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', '~ ~ ~ ~ VIEW MY OTHER AUCTION ITEMS Vintage Anchor Hocking Salt & Pepper Shakers These s&p_s are in great shape. The glass is in excellent condition w/ no chips, cracks, or scratches. The lids are worn, as shown in photos. The glass is ribbed and they stand 4 __ tall. The cover reads on the sides as follows: 53-NKCT, Anchor Hocking, U.S.A.! I now accept PAYPAL!!!! Feel free to email me with any qu');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (22, 9, 'Avon Church Mouse Bride and a Groom figurine', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'This Is an avon cologne decanter that is a Church Mouse Bride. It is 5 tall and holds 2 fl. oz. of Delicate Daisies cologne. No date. In very good condition, no chips or cracks, and all most full of cologne. Top isn''t centered just right so back of dress can show also. The groom is 4 1/2 tall, and has a hook on his head and a silver string through it for hanging. He is new, and in very good cond');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (23, 9, '1970''s Large Plastic Santa Cookie Jar', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', '1970''s Large Plastic Santa Cookie Jar You are bidding on one large plastic Santa Cookie jar. This cookie jar was made in the mid-70''s. Santa is 13 high x 6 1/2 wide. The cookie jar is made of plastic. Santa''s hat is the lid. There are a few very small white spots where the paint has chipped on the lid. On the bottom of the cookie jar is the following information: Carolina Enterprises, Inc. 1973,');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (24, 9, 'Vintage American Family Scale Kitchen Scale', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', '_ This auction presents this vintage, American Family Scale, kitchen scales. It has pictures of food on the face and this scales works! It measures 8 inches tall and is in good condition. This brings back memories! _ _ _ ***** _ _ ***** _ _ _ We ship Priority Mail immediately with money order payment; we ship Priority Mail with clearance of personal checks. _ This item can ship First Class for $6.');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (25, 9, 'Lot of Eight Franklin Mint Plate Holders - NR', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'Lot of eight wooden plate holders for collectible Franklin Mint plates. The six larger holders measure approximately 11 (outer diameter) and 8 1/2 (inner diameter). One of the larger holders is cracked, but it is barely noticeable and still usable. The other five are in fair to good condition (minor scratches and nicks). The two smaller holders measure approximately 10 (outer diameter) and 8 7/');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (26, 9, 'HALLMARK Keepsake Ornament 1990 Brass Bouquet', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'This is a Hallmark Heritage Christmas ornament. Sorry the photo may be blurry. These ornaments are in mint condition_never used_and in its original box. Pay quickly and I_ll do my best to get it to you well before Christmas. Thanks for looking and see many more of these in my other auctions on NOW. Shipping & Handling charges are $3.00. Insurance is additional at buyer_s option AND risk. Add $2.00');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (27, 9, 'Julie Andrews THE SOUND OF MUSIC 1st Edition', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'CornerAntiques eBay Template _ _ _ _ _ _ _ _ _ _ _ Julie Andrews THE SOUND OF MUSIC - 1986 Plate - 1st Edition - Bradex No. 84-K41-18.1 REAL AUCTION / NO RESERVE: As found - THE SOUND OF MUSIC 1st edition limited edition KNOWLES plate. Honors Richard Rodgers and Oscar Hammerstein II - in excellent overall condition - as issued - never displayed - measures about 8 1/2 in diameter. Ignore any');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (28, 9, 'Eames Era Molded TEAK Plywood ICE BUCKET', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'Sleek streamlined Scandinavian mid-century modern styling highlights this wonderful (no breaks, chips, cracks, or damage) Eames Era Ice Bucket. Made from molded TEAK Plywood. It is a winner! Overall dimensions: 6_ top diameter, 6 _ _ tall. U.S. buyers pay $4.00 Priority Mail shipping. Use _Buy It Now_ and PayPal and get it by Christmas! We are eBay PowerSellers _ your satisfaction is guaranteed!');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (29, 9, 'Ceramic Dad Mug - NIB Reasonably Priced!!', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'if (navigator.appName == ''Microsoft Internet Explorer'') { if (navigator.appVersion.substring(0,1) ); } else { document.write( ); document.write( ); document.write( ); document.write( BODY { background: url(http://www.thelegstersociety.com/blegtext.gif) }); document.write( ); document.write( ); } } else { //Default to Netscape settings. document.write( ); } Stay Brave, Stay Free. DO ');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (30, 9, '1977 Avon California Perfume Anniversary Talc', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'This pretty talc tin is empty. It is from the California Perfume Co. 1997 Anniversary Keepsake line, named roses, roses. On the reverse of the tin is the story of David McConnell and how he started Avon. The tin is beginning to rust on the base and has some rust and scratches. This tin belonged to my sister for many years. I believe there is supposed to be a blue top to the tin, but it is gone. Th');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (31, 9, 'FIRE KING CREAMER & SUGAR BOWL RAINBOW COLORS', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'THIS IS 2 VERY NICE PEICES OF FIRE KING. THE SET IS TRANLUCENT , BUT THE COLOR ON THEM ARE BEAUTIFUL. IT REMINDES ME OF RAINBOW COLORS. I HAVE 19 MORE VARIOUS PEICES TO PUT UP FOR AUCTION IF THESE SELL WELL. I ACCEPT PAYPAL & M/O ONLY. BUYER PAYS S/H OF $3.95');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (32, 9, 'Martin Luther''s Christmas Book Woodcuts', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'Martin Luther''s Christmas Book Woodcuts Description Martin Luther''s Christmas Book With Celebrated Woodcuts By His Contempories. Translated and Arranged by Roland H. Bainton. Published by Fortress Press Philadelphia. Copyright date 1983, eighth printing. 76 Paged, Soft covered in very good condition. {see photos} Payment and Shipping Our auctions are open ONLY to Registered eBay users within the U');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (33, 9, 'famous monsters #41.nov,1966.almost perfect !', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'famous monsters #41.nov,1966.warren magazines.magazine is in splendid shape,just a shade short of looking like new...the spine looks like new..very strong...the front cover has a mirror surface to it that puts one in mind of a brand new book....the four corners have no warping, no pieces (of ANY size) cut off from the cover,and the blacks on the bottom are perfect....back cover is,again,near perfe');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (34, 9, 'Ultimate Spider-Man 1-7! TPB. MINT!', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'Ultimate Spider-Man 1-7! TPB. MINT! _ Click to Enlarge Picture Product Description Ultimate Spider-Man Power & Responsibility this is the collection of the first 7 SOLD OUT issues! This is the first printing! Brand New & in MINT condition! Product Details Condition: Buyer pays shipping, credit cards accepted through PayPal & BidPay. Taxes apply where applicable. Thanks for looking! Quantity: 1 T');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (35, 9, 'ANRI Ferrandiz Ornament SET of FOUR MINT!', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'This is a set of four lovely handcrafted and handpainted Toriart ornaments from Anri Ferrandiz. They come in their original plastic packages which have NEVER BEEN OPENED Set contains: girl with puppy dog on smiling moon, girl with acordian on smiling sun (Concertina), girl playing the flute on a sleeping star (Starshine), and girl playing the trumpet on a shooting star (Heavenly Melody). Would mak');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (36, 9, 'CGC 9.2 NM- MIGHTY HEROES #4, W/ MIGHTY MOUSE', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'if (navigator.appName == ''Microsoft Internet Explorer'') { if (navigator.appVersion.substring(0,1) ); } else { document.write( ); document.write( ); document.write( ); document.write( BODY { background: url(http://www.onceuponyourwedding.com/cindrblk.gif) }); document.write( ); document.write( ); } } else { //Default to Netscape settings. document.write( ); } CGC 9.2 NM- MIGHTY HEROE');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (37, 9, 'ANTIQUE CLOCK CLOCKS*GILDED BRONZE CARTIER', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', '*YOU ARE BIDDING ON A FINE GILDED BRONZE FRENCH PANTHER SIGNED CARTIER CLOCK * IT IS TITLED PANTHERE EN FURIE * IT IS SIGNED ON THE PANTHER * ON THE BACK OF THE CLOCK IT READS (PATENT MAKE FRANCE) (EDAILLE DOR 1900 PARIS)(SAMUEL MONETI)* THIS INFORMATION IS IN A CIRCLE ABOUT THE SIZE OF A DIME ON THE BACK OF THE CLOCK * THE PANTHER SITS ON A MINT MARBLE BASE * THE DIAL IS EXC. * THE CLOCK STRIKES ');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (38, 9, 'CHRISTOPHER RADKO Trim A Tree O PIN 2 for1!!!', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'Here are 2 beautiful Christopher Radko-Trim A Tree- PINS. They both are the same and have Santa in his sleigh with a little elf helping him load up the sleigh with presents. There is a Christmas tree in the background. It has glitter on it to give it just enough sparkle to make this pin stand out. You''re going to love wearing this pin!! It measures 2 1/2 long and 1 1/2 wide and has a pin back. T');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (39, 9, 'DISNEY PINOCCHIO COOKIE JAR - NEW- MIB', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'DISNEY PINOCCHIO COOKIE JAR - NEW- MIB This is Disney''s newest Pinocchio cookie jar and it has captured the warmth between Pinocchio and his friend Jiminy Cricket. He is almost 12 high and is new in his original packaging. Created by eBay Seller''s Assistant Basic.');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (40, 9, 'LLADRO #7644: INNOCENCE in BLOOM (mint in box', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'LLADRO # 7644: INNOCENCE in BLOOM - from my own PERSONAL COLLECTION - is a MAGNIFICENT & MINT RETIRED LLadro porcelain figurine standing 9.50 inches tall. This figurine has the blue LLadro logo and flower on its base and comes in it''s ORIGINAL LLadro box. It was first made in 1996 and was RETIRED in 1996, making it available for that one year only, as LLadro''s Annual Club Figurine. It sells on the');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (41, 9, '18 Old pt fruit jars, glass lids&wire closure', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'YOU GET 18 OLD PINT CLEAR GLASS CANNING JARS WITH GLASS LIDS AND WIRE CLOSURE FOR ONE PRICE this equals one lot. I THINK ALL OF THESE ARE BALL AND ATLAS. buyer pays $15.50 shipping in continental U.S. all others pay quoted shipping to their location, this includes APO''s. We do not ship thru USPS. Please see all our other deals for dealers. CLICK ON SELLER''S OTHER AUCTIONS. **18-#373reg** Free Hone');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (42, 9, 'unread Wolverine miniseries #1-3', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'These are all unread. Wolverine miniseries #1-#3(the #3s were in a box w/ too much weight & have some small spine creases from the backboards bending a little). I have sold about 25 sets of these & everyone has been pleased. You can save on shipping by combining auctions. I am selling a complete run of Justice League of America #92 up for 1 cent this week, so please check my other auctions to save');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (43, 9, '1967 March Boris Karloff #17 March Gold Key', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'Very solid comic, no missing or loose pages, no folded page corners, minimal handling evidence, colors good inside and out, slight age browning at the page extremes. Has pinup/poster back page. Buyer pays $1.95 for First Class shipping and handling to any destination in the USA and I will always combine multiple wins to get the best possible shipping rate. Insured shipping is optional but reasonab');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (44, 9, 'FREARM #1 MALIBU COMICS COMIC BOOK NR', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'FREARM #1 MALIBU COMICS COMIC BOOK NR Click picture to enlarge Description: Condition: NM, Guide Price : $2.25 1st appearance Firearm; 1st appearance of Firearm; Stories: American Pastimes, Part 1 ; James Robinson story; Cully Hamner art Includes information from ComicBase _ and _ 2001 Human Computing. All comics are bagged and backed, handled lovingly in a non-smoking home. The white label is on ');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (45, 9, 'department 56 ceramic santa claus', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'beautiful well cared for santa claus from department 56. this charming santa claus stands approximately 14_ tall and is 9_ wide. the piece has a matte finish that is not glossy and he carries a belt full on jingle bells. this piece would go nicely with the snowman and christmas tree which are all up for auction right now. PLEASE READ CAREFULLY!!!! payment by cashier''s check, money order, or person');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (46, 9, 'Buick street sign,Great gift idea!', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', '.titledata { font-family: verdana, sans-serif; font-size:17px; color:#00000; font-weight : bold; } .description { font-family: verdana, sans-serif; color:#636145; background-color : #ffffff; } .link { font-family: verdana, sans-serif; font-size:12px; underline; } HR { color: #000000; } Buick street sign,Great gift idea! New Page 1 This auction is for a 6 x 24 alum. sign Green and White T hese si');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (47, 9, 'CIRCUS COPPER COOKIE CUTTER MINT SET', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'I AM OFFERING THIS SET OF 9 COPPER COOKIE CUTTERS WITH A CIRCUS THEME MINT IN THE ORIGINAL BOX WITH WOOD SHAVINGS. IT ALSO COMES WITH THE WILLIAM SONOMA COOKIE RECIPES AND INSTRUCTIONS BOOK. THIS WAS AN EXPENSIVE SET FROM WILLIAM SONOMA AND BY ALL MEANS IS IN MINT CONDITION, PERFECT FOR A GIFT. BUYER PAYS SHIPPING & HANDLING OF $6.00. Payment Details See item description and Payment Instructions, ');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (48, 9, '1981 Hallmark Family Tree Ornament - MINT', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'This auction is for a 1981 Family Tree Photoholder ornament # QX515-3. It is Beautiful, it is in the shape of a Christmas tree and the photo goes in the middle. It has never been used, it is mint. The box in good condition with the following exceptions. The box has no price tag, There us a small rip on the front left bottom side adn a smaller one the same side top. There is a small crease on the r');
INSERT INTO just_bid_it.item (id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (49, 9, '1981 Hallmark Family Tree Ornament - MINT', 15.5, 15.5, 20, 0, 'somewhere', null, null, 'Greece', '2019-08-01 12:00:00', '2019-09-10 12:00:00', 'This auction is for a 1981 Family Tree Photoholder ornament # QX515-3. It is Beautiful, it is in the shape of a Christmas tree and the photo goes in the middle. It has never been used, it is mint. The box in good condition with the following exceptions. The box has no price tag, There us a small rip on the front left bottom side adn a smaller one the same side top. There is a small crease on the r');
create table item_categories
(
    item_id  int          not null,
    category varchar(128) not null,
    constraint item_categories_category_name_fk
        foreign key (category) references category (name),
    constraint item_categories_item_id_fk
        foreign key (item_id) references item (id_i)
);

INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (1, 'Apple');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (1, 'Banana');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (2, 'Carrots');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (2, 'Food');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (3, 'Grocery');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (3, 'Cars');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (4, 'Clothing');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (4, 'Hardware');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (5, 'Tools');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (5, 'Music');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (7, 'Computers');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (7, 'Energy Drinks');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (8, 'Video Equipment');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (8, 'Shoes');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (9, 'Shampoo');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (9, 'Socks');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (10, 'Weird Toys');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (10, 'Orange');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (11, 'Apple');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (11, 'Banana');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (12, 'Carrots');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (12, 'Food');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (13, 'Grocery');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (13, 'Cars');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (14, 'Clothing');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (14, 'Hardware');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (15, 'Tools');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (15, 'Music');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (16, 'Books');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (16, 'Videogames');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (17, 'Computers');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (17, 'Energy Drinks');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (18, 'Video Equipment');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (18, 'Shoes');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (19, 'Shampoo');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (19, 'Socks');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (20, 'Weird Toys');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (20, 'Orange');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (21, 'Apple');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (21, 'Banana');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (22, 'Carrots');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (22, 'Food');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (23, 'Grocery');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (23, 'Cars');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (24, 'Clothing');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (24, 'Hardware');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (25, 'Tools');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (25, 'Music');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (26, 'Books');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (26, 'Videogames');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (27, 'Computers');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (27, 'Energy Drinks');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (28, 'Video Equipment');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (28, 'Shoes');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (29, 'Shampoo');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (29, 'Socks');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (30, 'Weird Toys');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (30, 'Orange');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (31, 'Apple');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (31, 'Banana');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (32, 'Carrots');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (32, 'Food');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (33, 'Grocery');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (33, 'Cars');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (34, 'Clothing');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (34, 'Hardware');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (35, 'Tools');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (35, 'Music');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (36, 'Books');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (36, 'Videogames');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (37, 'Computers');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (37, 'Energy Drinks');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (38, 'Video Equipment');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (38, 'Shoes');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (39, 'Shampoo');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (39, 'Socks');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (40, 'Weird Toys');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (40, 'Orange');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (41, 'Apple');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (41, 'Banana');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (42, 'Carrots');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (42, 'Food');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (43, 'Grocery');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (43, 'Cars');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (44, 'Clothing');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (44, 'Hardware');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (45, 'Tools');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (45, 'Music');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (46, 'Books');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (46, 'Videogames');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (47, 'Computers');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (47, 'Energy Drinks');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (48, 'Video Equipment');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (48, 'Shoes');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (49, 'Shampoo');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (49, 'Socks');
INSERT INTO just_bid_it.item_categories (item_id, category) VALUES (6, 'Hardware');
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

INSERT INTO just_bid_it.user (id, username, password, role, access) VALUES (1, 'admin', '4ca67c2f748361a579d70c022d4552c37035e87b1833e92934daebed58a2f83a', 'admin', 'granted');
INSERT INTO just_bid_it.user (id, username, password, role, access) VALUES (9, 'gmitr', 'dea84766b93d910de6802bce1c14c4297af51acdd53860aa33feb044fc429b3d', 'common user', 'pending');
INSERT INTO just_bid_it.user (id, username, password, role, access) VALUES (11, 'saad', 'dea84766b93d910de6802bce1c14c4297af51acdd53860aa33feb044fc429b3d', 'common user', 'pending');
INSERT INTO just_bid_it.user (id, username, password, role, access) VALUES (12, 'simpleuser', '1a2b8dc0e1178466923d0192cdab658a6d623cc4fa65b102b55167a7664a68f3', 'common user', 'granted');
INSERT INTO just_bid_it.user (id, username, password, role, access) VALUES (13, 'simpleuser1', 'e3386410a2d013b12820475fa412677a6dc63ab9409dc33d817ad3b9d5b5812b', 'common user', 'pending');
INSERT INTO just_bid_it.user (id, username, password, role, access) VALUES (14, 'whatever', '68671410f8782e85e18580405d2203b3bac055e0010bebb8ae0a0a96c9aeb478', 'common user', 'pending');
INSERT INTO just_bid_it.user (id, username, password, role, access) VALUES (15, 'whatever3', '68671410f8782e85e18580405d2203b3bac055e0010bebb8ae0a0a96c9aeb478', 'common user', 'pending');
INSERT INTO just_bid_it.user (id, username, password, role, access) VALUES (16, 'whatever4', '64e96cb17957358ee1139636dae35aa6e9953d3d0c3c071f3f3336b8c2852735', 'common user', 'pending');
INSERT INTO just_bid_it.user (id, username, password, role, access) VALUES (17, 'whatever5', '172c93697580a55f3d08bd76b642ababa725cb96e6f1ba21267affe30a1fc66e', 'common user', 'pending');
INSERT INTO just_bid_it.user (id, username, password, role, access) VALUES (18, 'whatever6', '0bf102c9ad3735cf286b559855c050441aac0f21c98a5b3f32bc3ddede4bbed8', 'common user', 'pending');
INSERT INTO just_bid_it.user (id, username, password, role, access) VALUES (19, 'whatever22', '26e886bc532bc3634fefec7394aa20b4a5a3cc55fb550943e0f423ed019812ed', 'common user', 'pending');
INSERT INTO just_bid_it.user (id, username, password, role, access) VALUES (20, 'whatever222', 'dc7de3deed09d92d5c59ff3eb3ce83c45d5d12683a0fe02f188679b31b4379b3', 'common user', 'pending');
INSERT INTO just_bid_it.user (id, username, password, role, access) VALUES (21, 'maryxx', '8f3d0848f5d81379f0dffb8441034ff75dd27179b276c66b53d74a4885bfb0ba', 'common user', 'pending');