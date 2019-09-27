delete
from administrator;

delete
from bid;

delete
from item_categories;

delete
from category;

delete
from item;

delete
from message;

delete
from common_user;

delete
from user;



alter table user auto_increment=1;
alter table common_user auto_increment=1;
alter table bid auto_increment=1;
alter table item auto_increment=1;
alter table category auto_increment=1;
alter table message auto_increment=1;
alter table administrator auto_increment=1;


insert into just_bid_it.user SET username = 'admin', password = '4ca67c2f748361a579d70c022d4552c37035e87b1833e92934daebed58a2f83a', role = 'admin', access = 'granted';
insert into just_bid_it.administrator SET first_name = 'Andreas', last_name = 'Papandreou', id=1;
