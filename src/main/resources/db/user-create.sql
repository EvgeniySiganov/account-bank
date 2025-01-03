create schema ad;

create sequence ad.users_id_seq;
create sequence ad.address_id_seq;
create sequence ad.bank_book_id_seq;

create table ad.address (
    id integer unique not null default nextval('ad.address_id_seq'),
    country varchar,
    city varchar,
    street varchar,
    home varchar,
    primary key (id)
);

create table ad.users (
    id integer unique not null default nextval('ad.users_id_seq'),
    name varchar not null,
    email varchar,
    address_id integer,
    primary key (id),
    foreign key (address_id) references ad.address(id)

);

create table ad.bank_books
(
    id       integer unique not null default nextval('ad.bank_book_id_seq'),
    user_id  integer,
    number   varchar        not null,
    amount   numeric(8, 2) check ( amount >= 0 ),
    currency_id integer,
    primary key (id),
    foreign key (user_id) references ad.users(id),
    foreign key (currency_id) references dict.currency(id)
);

create schema dict;

create sequence dict.currency_seq;

create table dict.currency
(
    id integer unique not null default nextval('dict.currency_seq'),
    name varchar,
    primary key (id)
);

create sequence ad.groups_seq;

create table ad.groups
(
    id integer unique not null default nextval('ad.groups_seq'),
    name varchar,
    primary key (id)
);

create table ad.users_groups
(
    user_id integer,
    group_id integer,
    primary key (user_id, group_id),
    foreign key (user_id) references ad.users(id),
    foreign key (group_id) references ad.groups(id)
);