drop table if exists products;
create table products
(
    id    int primary key,
    name  varchar(255),
    price decimal(10, 2)
);
insert into products (id, name, price)
values (1, 'iPhone', 700.00);
insert into products (id, name, price)
values (2, 'Samsung Z Fold', 500.00);
