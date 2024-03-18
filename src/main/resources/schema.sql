CREATE TABLE IF NOT EXISTS items(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name character varying(255) NOT NULL,
    price numeric NOT NULL
);



CREATE TABLE IF NOT EXISTS inventories(
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_id character varying(25) NOT NULL,
    qty int NOT NULL,
    type character varying(1) NOT NULL,

    foreign key (ITEM_ID) references items(id)
);

--INSERT INTO inventories(item_id, qty, type) values(1, 5, 'T');
--INSERT INTO inventories(item_id, qty, type) values(2, 10, 'T');
--INSERT INTO inventories(item_id, qty, type) values(3, 30, 'T');
--INSERT INTO inventories(item_id, qty, type) values(4, 3, 'T');
--INSERT INTO inventories(item_id, qty, type) values(5, 45, 'T');
--INSERT INTO inventories(item_id, qty, type) values(6, 5, 'T');
--INSERT INTO inventories(item_id, qty, type) values(7, 25, 'T');
--INSERT INTO inventories(item_id, qty, type) values(5, 10, 'W');


CREATE TABLE IF NOT EXISTS orders(
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_id character varying(25) NOT NULL,
    order_no character varying(50) NOT NULL,
    qty int NOT NULL,

    foreign key (ITEM_ID) references items(id)
);


-- insert items
INSERT INTO items(name, price) values('Pen', 5);
INSERT INTO items(name, price) values('Book', 10);
INSERT INTO items(name, price) values('Bag', 30);
INSERT INTO items(name, price) values('Pencil', 3);
INSERT INTO items(name, price) values('Shoe', 45);
INSERT INTO items(name, price) values('Box', 5);
INSERT INTO items(name, price) values('Cap', 25);


--insert inventory
INSERT INTO inventories(item_id, qty, type) values(1, 5, 'T');
INSERT INTO inventories(item_id, qty, type) values(2, 10, 'T');
INSERT INTO inventories(item_id, qty, type) values(3, 30, 'T');
INSERT INTO inventories(item_id, qty, type) values(4, 3, 'T');
INSERT INTO inventories(item_id, qty, type) values(5, 45, 'T');
INSERT INTO inventories(item_id, qty, type) values(6, 5, 'T');
INSERT INTO inventories(item_id, qty, type) values(7, 25, 'T');
INSERT INTO inventories(item_id, qty, type) values(5, 10, 'W');

-- insert orders
INSERT INTO orders(item_id, order_no, qty) values(1, 'O1', 2);
INSERT INTO orders(item_id, order_no, qty) values(2, 'O2', 3);
INSERT INTO orders(item_id, order_no, qty) values(5, 'O3', 4);
INSERT INTO orders(item_id, order_no, qty) values(4, 'O4', 1);
INSERT INTO orders(item_id, order_no, qty) values(5, 'O5', 2);
INSERT INTO orders(item_id, order_no, qty) values(6, 'O6', 3);
INSERT INTO orders(item_id, order_no, qty) values(1, 'O7', 5);
INSERT INTO orders(item_id, order_no, qty) values(2, 'O8', 4);
INSERT INTO orders(item_id, order_no, qty) values(3, 'O9', 2);
INSERT INTO orders(item_id, order_no, qty) values(4, 'O10',3);

