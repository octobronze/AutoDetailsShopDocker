CREATE TABLE details
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    detail_name character varying(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE car_brands
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    car_brand_name character varying(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE car_models
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    car_model_name character varying(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE offers
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    car_brand_id bigint NOT NULL,
    car_model_id bigint NOT NULL,
    detail_id bigint NOT NULL,
    price numeric(19,2) NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_details
        FOREIGN KEY(detail_id)
	    REFERENCES details(id),
    CONSTRAINT fk_car_brands
        FOREIGN KEY(car_brand_id)
	    REFERENCES car_brands(id),
    CONSTRAINT fk_car_models
        FOREIGN KEY(car_model_id)
	    REFERENCES car_models(id)
);

CREATE TABLE users
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    sex character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
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

INSERT INTO car_brands(car_brand_name) VALUES
	('Lada'),
	('Zhiguli');

INSERT INTO car_models(car_model_name) VALUES
	('Vesta'),
	('Granta'),
	('Xray'),
	('VAZ-2101'),
	('VAZ-2102'),
	('VAZ=2103'),
	('VAZ-2104'),
	('VAZ-2105');

INSERT INTO users (username, password, email, first_name, last_name, sex, role, status) VALUES
('Max', '$2a$12$2h6GG2lkPydluZvZro2YYOigM5YskqkVbVkJ9sSkbGdwBsrN3d7MK', 'max@gmail.com', 'Maxim', 'Gromyko', 'male', 'ADMIN', 'Suspended'),
('Den', '$2a$12$eD.0COMh0fr61nMY1CWmTOiIv9v8BnJ1E.xgBb4w0kjIeLGMe83HO', 'den@gmail.com', 'Denis', 'Vorozhtsov', 'male', 'USER', 'Suspended'),
('Tom', '$2a$12$qLqVy9asyZWfpabj4otkQeA./H9TmcEeMfncUyKV8oEr0c9C799Ma', 'tema@gmail.com', 'Artyom', 'Maiseikov', 'male', 'USER', 'Suspended');


INSERT INTO offers(car_brand_id, car_model_id, detail_id, price) VALUES
(1, 1, 1, 999.47),
(1, 1, 2, 129.23),
(1, 2, 2, 119.14),
(1, 2, 6, 11.15),
(1, 3, 3, 199.44),
(1, 3, 4, 34.32),
(1, 1, 4, 29.76),
(1, 2, 5, 19.88),
(1, 1, 7, 40.32),
(1, 3, 8, 201.23),
(2, 4, 5, 15.23),
(2, 5, 5, 15.99),
(2, 4, 6, 10.23),
(2, 6, 7, 35.94),
(2, 8, 8, 150.23),
(2, 4, 8, 130.22),
(2, 4, 9, 170.99),
(2, 6, 9, 100.99),
(2, 7, 10, 40.99),
(2, 5, 10, 39.00),
(2, 4, 11, 35.22),
(2, 7, 11, 39.99);