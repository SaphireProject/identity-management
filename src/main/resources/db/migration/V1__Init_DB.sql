create sequence hibernate_sequence start 1 increment 1;

create table client (
    client_id int8 not null,
    secret_id varchar(255),
    user_id int8,
    primary key (client_id)
    );

create table user_role (
    user_id int8 not null,
    roles varchar(255)
    );

create table usertable (
    id int8 not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
    );

alter table if exists client
    add constraint client_user_id_fk
    foreign key (user_id) references usertable;

alter table if exists user_role
    add constraint user_role_user_id_fk
    foreign key (user_id) references usertable;