create table products (
  id            bigserial primary key,
  title         varchar(255),
  price         int,
  created_at    timestamp default current_timestamp,
  updated_at    timestamp default current_timestamp
);

insert into products (title, price)
values
('Bread', 100),
('Apple', 100),
('Milk', 70),
('Eggs', 100),
('Fish', 600),
('Coffee', 500),
('Tea', 300),
('Butter', 200),
('Butter Maxi', 450),
('Banana', 70),
('Orange juice', 100),
('Cheese', 1200),
('Tomato', 200),
('Cucumber', 150),
('Potato', 50),
('Pasta', 100),
('Dumplings', 700),
('Corn', 70),
('Cookie', 100),
('Salmon', 1200);

create table users (
  id                    bigserial   primary key,
  username              varchar(36) not null unique,
  password              varchar(80) not null,
  email                 varchar(50) unique,
  created_at            timestamp default current_timestamp,
  updated_at            timestamp default current_timestamp
);

create table roles (
  id                    bigserial primary key,
  name                  varchar(50) not null,
  created_at            timestamp default current_timestamp,
  updated_at            timestamp default current_timestamp
);

create table users_roles (
  user_id               bigint not null,
  role_id               bigint not null,
  primary key (user_id, role_id),
  foreign key (user_id) references users (id),
  foreign key (role_id) references roles (id),
  created_at            timestamp default current_timestamp,
  updated_at            timestamp default current_timestamp
);

insert into roles (name)
values
('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_SUPERADMIN');


insert into users (username, password, email)
values
('max', '$2a$12$yGAFikKAKK/RKnPzQJvJ1uhWkmz/V6mbovQiYWzIsX0nTXz4miwni', 'max@gmail.com'), ---100
('bob', '$2a$12$.k8QQw0D5cF6LeKSVIzYhOSV9qwMjsIqlpS.KWzgjZAafEyhJvAxq', 'bob@gmail.com'), ---200
('admin', '$2a$12$U7YGhR3hmlqKPoDCM3C1Jux5f0VQwtLPMDrXxl6d3RyFEAtqXf3ze', 'admin@gmail.com'), ---111
('superadmin', '$2a$12$k.J0kmjVS6KluXe2VuWYxeMdnKK5JpEpaXppHctSkOpDJxF5kfPau', 'superadmin@gmail.com'); ---777

insert into users_roles (user_id, role_id)
values
(1, 1), (2, 1), (3, 2), (4, 3);


create table orders (
    id          bigserial primary key,
    user_id     bigint not null references users (id),
    total_price int not null,
    address     varchar(255),
    phone       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);


create table order_items (
    id                  bigserial primary key,
    product_id          bigint not null references products (id),
    order_id            bigint references orders (id),
    quantity            int not null,
    price_per_product   int not null,
    price               int not null,
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);

insert into orders(user_id, total_price, address, phone) values
(1, 200, 'address', '12345');

insert into order_items (product_id, order_id, quantity, price_per_product, price) values
(1, 1, 2, 100, 200);