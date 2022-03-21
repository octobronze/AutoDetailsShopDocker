CREATE TABLE details
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    detail_name character varying(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE offers
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    car_brand character varying(255) NOT NULL,
    car_model character varying(255) NOT NULL,
    detail_id bigint NOT NULL,
    price numeric(19,2) NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_details
        FOREIGN KEY(detail_id)
	    REFERENCES details(id)
);

CREATE TABLE users
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO details(detail_name) VALUES 
	('engine'),
	('wheels'),
	('transmission'),
	('radiators'),
	('mirrors'),
	('hoods'),
	('grilles'),
	('bumpers'),
	('tail lights'),
	('step bumpers'),
	('headlights');

INSERT INTO users (username, password, first_name, last_name, role) VALUES
('Max', '$2a$12$2h6GG2lkPydluZvZro2YYOigM5YskqkVbVkJ9sSkbGdwBsrN3d7MK', 'Maxim', 'Gromyko', 'ADMIN'),
('Den', '$2a$12$eD.0COMh0fr61nMY1CWmTOiIv9v8BnJ1E.xgBb4w0kjIeLGMe83HO', 'Denis', 'Vorozhtsov', 'USER'),
('Tom', '$2a$12$qLqVy9asyZWfpabj4otkQeA./H9TmcEeMfncUyKV8oEr0c9C799Ma', 'Artyom', 'Maiseikov', 'USER');


INSERT INTO offers(car_brand, car_model, detail_id, price) VALUES
('Lada', 'Vesta', 1, 999.47),
('Lada', 'Vesta', 2, 129.23),
('Lada', 'Granta', 2, 119.14),
('Lada', 'Granta', 6, 11.15),
('Lada', 'Xray', 3, 199.44),
('Lada', 'Xray', 4, 34.32),
('Lada', 'Vesta', 4, 29.76),
('Lada', 'Granta', 5, 19.88),
('Lada', 'Vesta', 7, 40.32),
('Lada', 'Xray', 8, 201.23),
('Zhiguli', 'VAZ-2101', 5, 15.23),
('Zhiguli', 'VAZ-2102', 5, 15.99),
('Zhiguli', 'VAZ-2101', 6, 10.23),
('Zhiguli', 'VAZ-2103', 7, 35.94),
('Zhiguli', 'VAZ-2105', 8, 150.23),
('Zhiguli', 'VAZ-2101', 8, 130.22),
('Zhiguli', 'VAZ-2101', 9, 170.99),
('Zhiguli', 'VAZ-2103', 9, 100.99),
('Zhiguli', 'VAZ-2104', 10, 40.99),
('Zhiguli', 'VAZ-2102', 10, 39.00),
('Zhiguli', 'VAZ-2101', 11, 35.22),
('Zhiguli', 'VAZ-2104', 11, 39.99);