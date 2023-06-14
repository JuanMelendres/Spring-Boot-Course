/* Populate Client Table */

INSERT INTO regions (id, region_name) VALUES (1, 'South America');
INSERT INTO regions (id, region_name) VALUES (2, 'Central America');
INSERT INTO regions (id, region_name) VALUES (3, 'Noth America');
INSERT INTO regions (id, region_name) VALUES (4, 'Europe');
INSERT INTO regions (id, region_name) VALUES (5, 'Asia');
INSERT INTO regions (id, region_name) VALUES (6, 'Africa');
INSERT INTO regions (id, region_name) VALUES (7, 'Oceania');
INSERT INTO regions (id, region_name) VALUES (8, 'Antarctica');

INSERT INTO clients (region_id, first_name, last_name, email, create_at) VALUES (3, 'Avee','Haseldine','ahaseldine0@multiply.com','1992-04-03');
INSERT INTO clients (region_id, first_name, last_name, email, create_at) VALUES (4, 'Joni','Azemar','azemar1@scientificamerican.com','1998-08-20');
INSERT INTO clients (region_id, first_name, last_name, email, create_at) VALUES (5, 'Emelen','Geard','egeard2@reddit.com','1964-04-03');
INSERT INTO clients (region_id, first_name, last_name, email, create_at) VALUES (3, 'Annie','McAughtrie','amcaughtrie3@noaa.gov','1989-10-31');
INSERT INTO clients (region_id, first_name, last_name, email, create_at) VALUES (2, 'Michaela','Samet','msamet4@qq.com','1942-06-17');

/* Create user and roles */

INSERT INTO `users` (user_name, password, enabled, first_name, last_name, email) VALUES ('juan', '$2a$10$sojS1HArWlASUEgGuwTL9.KVfJ1s7zBr/rL.YAIliAV5GZdq.d6Le', 1, "Juan", "Melendres", "test@gmail.com");
INSERT INTO `users` (user_name, password, enabled, first_name, last_name, email) VALUES ('admin', '$2a$10$9eNA0suO35F1yjdHlkrgT.dqdMq5Wl/nQ.S.oekd4FZcQbOdUWdAa', 1, "Admin", "Admin", "Admin@gmail.com");

INSERT INTO `roles` (role_name) VALUES ('ROLE_USER');
INSERT INTO `roles` (role_name) VALUES ('ROLE_ADMIN');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1, 1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 1);

/* Create products */

INSERT INTO products (product_name, price, create_at) VALUES ('Apple Iphone 512 GB', 599, NOW());
INSERT INTO products (product_name, price, create_at) VALUES ('Apple Ipad 512 GB', 899, NOW());
INSERT INTO products (product_name, price, create_at) VALUES ('Apple MacBoock Pro 512 GB', 1599, NOW());
INSERT INTO products (product_name, price, create_at) VALUES ('Apple MacBoock Pro 250 GB', 1299, NOW());
INSERT INTO products (product_name, price, create_at) VALUES ('Apple Mac Mini 512 GB', 1399, NOW());
INSERT INTO products (product_name, price, create_at) VALUES ('Bianchi Bicycle', 299, NOW());
INSERT INTO products (product_name, price, create_at) VALUES ('Wallet', 50, NOW());
INSERT INTO products (product_name, price, create_at) VALUES ('Smart TV 34', 150, NOW());

/* Create Bills */

INSERT INTO bills (description, observation, client_id, create_at) VALUES ('Apple bill', null, 1, NOW());
INSERT INTO bill_items (amount, bill_id, product_id) VALUES (1, 1, 1);
INSERT INTO bill_items (amount, bill_id, product_id) VALUES (4, 1, 2);
INSERT INTO bill_items (amount, bill_id, product_id) VALUES (1, 1, 5);

INSERT INTO bills (description, observation, client_id, create_at) VALUES ('Bicycle Bill', 'No refund ', 1, NOW());
INSERT INTO bill_items (amount, bill_id, product_id) VALUES (2, 2, 6);
