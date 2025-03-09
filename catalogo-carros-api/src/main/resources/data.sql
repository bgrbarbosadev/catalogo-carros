-- Criação das tabelas (se necessário)
CREATE TABLE IF NOT EXISTS tb_manufacturer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    manufacturer VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_model (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(255) NOT NULL,
    manufacturer_id BIGINT,
    type_id BIGINT,
    FOREIGN KEY (manufacturer_id) REFERENCES tb_manufacturer(id),
    FOREIGN KEY (type_id) REFERENCES tb_type(id)
);

CREATE TABLE IF NOT EXISTS tb_vehicle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plate VARCHAR(255) NOT NULL,
    yearOfManufacture INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    mileage INT NOT NULL,
    items VARCHAR(255) NOT NULL,
    transmission VARCHAR(255) NOT NULL,
    fuel VARCHAR(255) NOT NULL,
    cor VARCHAR(255) NOT NULL,
    model_id BIGINT,
    FOREIGN KEY (model_id) REFERENCES tb_model(id)
);

-- Inserções na tabela tb_manufacturer
INSERT INTO tb_manufacturer (manufacturer) VALUES ('FORD');
INSERT INTO tb_manufacturer (manufacturer) VALUES ('FIAT');
INSERT INTO tb_manufacturer (manufacturer) VALUES ('CHEVROLET');
INSERT INTO tb_manufacturer (manufacturer) VALUES ('TOYOTA');

-- Inserções na tabela tb_type
INSERT INTO tb_type (type) VALUES ('SUV');
INSERT INTO tb_type (type) VALUES ('Popular');
INSERT INTO tb_type (type) VALUES ('Moto');
INSERT INTO tb_type (type) VALUES ('Picape');

-- Inserções na tabela tb_model
INSERT INTO tb_model (model, manufacturer_id, type_id) VALUES ('FOCUS', 1, 2);
INSERT INTO tb_model (model, manufacturer_id, type_id) VALUES ('ETIOS', 4, 2);
INSERT INTO tb_model (model, manufacturer_id, type_id) VALUES ('UNO', 2, 2);

-- Inserções na tabela tb_vehicle
INSERT INTO tb_vehicle (plate, year_Of_Manufacture, price, mileage, items, transmission, fuel, cor, model_id) VALUES ('LJM1890', '2025', 50000.00, 10000, 'COMPLETO', 'AUTOMATIC', 'FLEX', 'PRETO', 1);
INSERT INTO tb_vehicle (plate, year_of_manufacture, price, mileage, items, transmission, fuel, cor, model_id) VALUES ('KVC6525', '2020', 40000.00, 200000, 'COMPLETO', 'AUTOMATIC', 'FLEX', 'VERMELHO', 2);
INSERT INTO tb_vehicle (plate, year_Of_Manufacture, price, mileage, items, transmission, fuel, cor, model_id) VALUES ('LSK7777', '2022', 35000.00, 5000, 'COMPLETO', 'AUTOMATIC', 'FLEX', 'BRANCO', 3);
