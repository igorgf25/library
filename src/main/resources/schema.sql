drop table if exists tb_books;
drop table if exists tb_authors;
drop table if exists tb_categories;

CREATE TABLE tb_authors (
    author_id bigint primary key auto_increment ,
    name varchar(100) not null ,
    nationality varchar(30) not null,
    birth_date date not null
);

CREATE TABLE tb_categories (
    category_id bigint primary key auto_increment ,
    name varchar(100) not null ,
    description varchar(255) not null
);

CREATE TABLE tb_books (
    book_id bigint primary key auto_increment ,
    title varchar(100) not null ,
    description varchar(500) not null ,
    number_of_pages int not null ,
    language varchar(50) not null ,
    publisher varchar(100) not null ,
    publication_date date not null ,
    author_id bigint not null ,
    category_id bigint not null,
    foreign key (author_id) references tb_authors(author_id),
    foreign key (category_id) references tb_categories(category_id)
);

CREATE TABLE tb_users (
    user_id bigint primary key auto_increment ,
    username varchar(100) not null,
    password varchar(100) not null
);

CREATE TABLE tb_roles (
    role_id bigint primary key auto_increment ,
    name varchar(100) not null
);

CREATE TABLE tb_roles_users (
    role_id bigint not null ,
    user_id bigint not null ,
    foreign key (role_id) references tb_roles(role_id),
    foreign key (user_id) references tb_users(user_id)

);