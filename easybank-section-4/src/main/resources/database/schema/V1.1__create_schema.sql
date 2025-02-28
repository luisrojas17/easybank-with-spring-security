-- This script is based on org/springframework/security/core/userdetails/jdbc/users.ddl
create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

create table customers (
    id int not null primary key auto_increment,
    email varchar(50) not null,
    pwd  varchar(200) not null,
    role varchar(50) not null
);