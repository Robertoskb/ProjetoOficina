
use oficina_db;

delete from user where user_id >= 1;
insert into user (user_id, user_name, email, password, admin) values 
(1, "admin", "admin@gmail.com", "123", true),
(2, "Funcionario", "func@gmail.com", "123", false)
;

delete from client where client_id > 0;

INSERT INTO Client (client_id, client_name, address, cpf) VALUES
(1,'João Silva', 'Rua das Flores, 123', 11111111101),
(2,'Maria Oliveira', 'Av. Central, 456', 11111111102),
(3,'Pedro Santos', 'Rua São José, 789', 11111111103),
(4,'Ana Costa', 'Rua do Sol, 101', 11111111104),
(5,'Carlos Souza', 'Av. Brasil, 202', 11111111105),
(6,'Juliana Lima', 'Rua das Acácias, 303', 11111111106),
(7,'Fernando Alves', 'Rua da Paz, 404', 11111111107),
(8,'Patrícia Gomes', 'Av. Independência, 505', 11111111108),
(9,'Ricardo Martins', 'Rua Nova, 606', 11111111109),
(10,'Camila Rocha', 'Rua dos Ipês, 707', 11111111110),
(11,'Bruno Ferreira', 'Av. Recife, 808', 1111114112111),
(12,'Bruno José', 'Av. Recife, 808', 113451134611),
(13,'Larissa Barbosa', 'Rua Esperança, 909', 11111111112),
(14,'Gabriel Melo', 'Rua Horizonte, 111', 11111111113),
(15,'Beatriz Nunes', 'Av. Boa Vista, 222', 11111111114),
(16,'Lucas Carvalho', 'Rua do Comércio, 333', 11111111115),
(17,'Amanda Ribeiro', 'Rua Projetada, 444', 11111111116),
(18,'Rafael Araújo', 'Av. dos Estados, 555', 11111111117),
(19,'Fernanda Dias', 'Rua Principal, 666', 11111111118),
(20,'Thiago Batista', 'Rua das Palmeiras, 777', 11111111119);

delete from car where car_id > 0;

INSERT INTO Car (
	car_id,
    brand,
    model,
    color,
    plate,
    year,
    mileage,
    client_id
) VALUES
(1,'Toyota', 'Corolla', 'Prata', 'AAA1A01', 2020, 45000, 1),
(2,'Honda', 'Civic', 'Preto', 'BBB2B02', 2019, 52000, 2),
(3,'Volkswagen', 'Gol', 'Branco', 'CCC3C03', 2018, 78000, 3),
(4,'Chevrolet', 'Onix', 'Vermelho', 'DDD4D04', 2021, 32000, 4),
(5,'Fiat', 'Argo', 'Cinza', 'EEE5E05', 2022, 18000, 5),
(6,'Hyundai', 'HB20', 'Azul', 'FFF6F06', 2020, 41000, 6),
(7,'Renault', 'Kwid', 'Branco', 'GGG7G07', 2023, 9000, 7),
(8,'Nissan', 'Versa', 'Prata', 'HHH8H08', 2019, 63000, 8),
(9,'Jeep', 'Renegade', 'Preto', 'III9I09', 2021, 27000, 9),
(10,'Ford', 'Ka', 'Vermelho', 'JJJ1J10', 2018, 85000, 10),
(11,'Toyota', 'Yaris', 'Cinza', 'KKK2K11', 2022, 15000, 11),
(12,'Honda', 'Fit', 'Branco', 'LLL3L12', 2017, 92000, 12),
(13,'Chevrolet', 'Cruze', 'Azul', 'MMM4M13', 2020, 38000, 13),
(14,'Fiat', 'Cronos', 'Prata', 'NNN5N14', 2021, 29000, 14),
(15,'Volkswagen', 'Polo', 'Preto', 'OOO6O15', 2023, 7000, 15),
(16,'Hyundai', 'Creta', 'Branco', 'PPP7P16', 2022, 21000, 16),
(17,'Renault', 'Sandero', 'Cinza', 'QQQ8Q17', 2019, 61000, 17),
(18,'Nissan', 'Kicks', 'Vermelho', 'RRR9R18', 2021, 33000, 18),
(19,'Jeep', 'Compass', 'Prata', 'SSS1S19', 2020, 46000, 19),
(20,'Ford', 'EcoSport', 'Azul', 'TTT2T20', 2018, 79000, 20);

DELETE FROM Part
WHERE part_id > 0;

INSERT INTO Part (
    part_id,
    part_name,
    part_price,
    manufacturer,
    model
) VALUES
(1, 'Filtro de Óleo', 35.90, 'Bosch', 'FO-101'),
(2, 'Pastilha de Freio', 89.90, 'Cobreq', 'PF-202'),
(3, 'Amortecedor Dianteiro', 249.90, 'Monroe', 'AD-303'),
(4, 'Bateria 60Ah', 429.90, 'Moura', 'B60-404'),
(5, 'Correia Dentada', 119.90, 'Gates', 'CD-505'),
(6, 'Vela de Ignição', 24.90, 'NGK', 'VI-606'),
(7, 'Filtro de Ar', 39.90, 'Mann', 'FA-707'),
(8, 'Disco de Freio', 159.90, 'Fremax', 'DF-808'),
(9, 'Radiador', 389.90, 'Valeo', 'RD-909'),
(10, 'Bomba de Combustível', 279.90, 'Delphi', 'BC-110'),
(11, 'Alternador', 799.90, 'Bosch', 'ALT-111'),
(12, 'Motor de Partida', 649.90, 'Valeo', 'MP-112'),
(13, 'Rolamento de Roda', 109.90, 'SKF', 'RR-113'),
(14, 'Pneu 185/65 R15', 429.90, 'Michelin', 'PR15-114'),
(15, 'Sensor de Oxigênio', 199.90, 'Bosch', 'SO-115'),
(16, 'Filtro de Combustível', 49.90, 'Tecfil', 'FC-116'),
(17, 'Kit Embreagem', 699.90, 'Luk', 'KE-117'),
(18, 'Coxim do Motor', 149.90, 'Axios', 'CM-118'),
(19, 'Mangueira do Radiador', 59.90, 'Goodyear', 'MR-119'),
(20, 'Junta do Cabeçote', 179.90, 'Sabó', 'JC-120');

DELETE FROM Service
WHERE service_id > 0;

INSERT INTO Service (
    service_id,
    service_name,
    service_price
) VALUES
(1, 'Troca de Óleo', 120.00),
(2, 'Alinhamento', 80.00),
(3, 'Balanceamento', 60.00),
(4, 'Troca de Pastilhas de Freio', 150.00),
(5, 'Revisão Completa', 450.00),
(6, 'Troca de Correia Dentada', 350.00),
(7, 'Limpeza de Bicos Injetores', 180.00),
(8, 'Troca de Bateria', 50.00),
(9, 'Diagnóstico Eletrônico', 100.00),
(10, 'Troca de Amortecedores', 300.00),
(11, 'Troca de Embreagem', 600.00),
(12, 'Troca de Radiador', 250.00),
(13, 'Troca de Velas', 90.00),
(14, 'Higienização do Ar Condicionado', 120.00),
(15, 'Recarga de Ar Condicionado', 200.00),
(16, 'Troca de Filtro de Ar', 40.00),
(17, 'Troca de Filtro de Combustível', 50.00),
(18, 'Troca de Rolamento', 180.00),
(19, 'Funilaria Simples', 350.00),
(20, 'Polimento e Cristalização', 220.00);

DELETE FROM Budget
WHERE budget_id > 0;

INSERT INTO Budget (
    budget_id,
    car_id,
    budget_price,
    budget_date_start,
    budget_date_finish
) VALUES
(1, 1, 350.00, '2026-01-02 08:00:00', null),
(2, 2, 1200.00, '2026-01-03 09:00:00', null),
(3, 3, 480.00, '2026-01-04 08:30:00', '2026-01-04 11:00:00'),
(4, 4, 950.00, '2026-01-05 10:00:00', '2026-01-05 16:00:00'),
(5, 5, 220.00, '2026-01-06 08:00:00', '2026-01-06 09:30:00'),
(6, 6, 780.00, '2026-01-07 09:00:00', '2026-01-07 13:00:00'),
(7, 7, 410.00, '2026-01-08 08:00:00', '2026-01-08 11:30:00'),
(8, 8, 1650.00, '2026-01-09 08:00:00', '2026-01-09 17:00:00'),
(9, 9, 290.00, '2026-01-10 09:00:00', null),
(10, 10, 670.00, '2026-01-11 08:00:00', '2026-01-11 12:00:00'),
(11, 11, 540.00, '2026-01-12 09:00:00', '2026-01-12 13:00:00'),
(12, 12, 890.00, '2026-01-13 08:00:00', null),
(13, 13, 310.00, '2026-01-14 09:00:00', '2026-01-14 11:00:00'),
(14, 14, 730.00, '2026-01-15 08:30:00', '2026-01-15 13:30:00'),
(15, 15, 460.00, '2026-01-16 08:00:00', null),
(16, 16, 1400.00, '2026-01-17 08:00:00', '2026-01-17 16:00:00'),
(17, 17, 520.00, '2026-01-18 09:00:00', '2026-01-18 12:30:00'),
(18, 18, 990.00, '2026-01-19 08:00:00', null),
(19, 19, 280.00, '2026-01-20 09:00:00', null),
(20, 20, 620.00, '2026-01-21 08:00:00', '2026-01-21 12:00:00');

DELETE FROM `Order`
WHERE order_id > 0;

INSERT INTO `Order` (
    order_id,
    car_id,
    order_price,
    order_date_start,
    order_date_finish,
    completed
) VALUES
(1, 1, 350.00, '2026-02-02 08:00:00', '2026-02-02 10:00:00', TRUE),
(2, 2, 1200.00, '2026-02-03 09:00:00', '2026-02-03 15:00:00', TRUE),
(3, 3, 480.00, '2026-02-04 08:30:00', '2026-02-04 11:00:00', TRUE),
(4, 4, 950.00, '2026-02-05 10:00:00', '2026-02-05 16:00:00', TRUE),
(5, 5, 220.00, '2026-02-06 08:00:00', '2026-02-06 09:30:00', TRUE),
(6, 6, 780.00, '2026-02-07 09:00:00', '2026-02-07 13:00:00', TRUE),
(7, 7, 410.00, '2026-02-08 08:00:00', '2026-02-08 11:30:00', TRUE),
(8, 8, 1650.00, '2026-02-09 08:00:00', '2026-02-09 17:00:00', TRUE),
(9, 9, 290.00, '2026-02-10 09:00:00', '2026-02-10 10:30:00', TRUE),
(10, 10, 670.00, '2026-02-11 08:00:00', '2026-02-11 12:00:00', TRUE),

(11, 11, 540.00, '2026-02-12 09:00:00', NULL, FALSE),
(12, 12, 890.00, '2026-02-13 08:00:00', NULL, FALSE),
(13, 13, 310.00, '2026-02-14 09:00:00', NULL, FALSE),
(14, 14, 730.00, '2026-02-15 08:30:00', NULL, FALSE),
(15, 15, 460.00, '2026-02-16 08:00:00', NULL, FALSE),
(16, 16, 1400.00, '2026-02-17 08:00:00', NULL, FALSE),
(17, 17, 520.00, '2026-02-18 09:00:00', NULL, FALSE),
(18, 18, 990.00, '2026-02-19 08:00:00', NULL, FALSE),
(19, 19, 280.00, '2026-02-20 09:00:00', NULL, FALSE),
(20, 20, 620.00, '2026-02-21 08:00:00', NULL, FALSE);

DELETE FROM budget_parts where budget_id >= 0;
DELETE FROM budget_services where budget_id >= 0;
DELETE FROM order_parts where order_id >= 0;
DELETE FROM order_services where order_id >= 0;

INSERT INTO budget_parts (budget_id, part_id) VALUES
(1,1),(1,2),
(2,3),(2,4),
(3,5),(3,6),
(4,7),(4,8),
(5,9),(5,10),
(6,11),(6,12),
(7,13),(7,14),
(8,15),(8,16),
(9,17),(9,18),
(10,19),(10,20),
(11,1),(11,3),
(12,2),(12,4),
(13,5),(13,7),
(14,6),(14,8),
(15,9),(15,11),
(16,10),(16,12),
(17,13),(17,15),
(18,14),(18,16),
(19,17),(19,19),
(20,18),(20,20);

INSERT INTO budget_services (budget_id, service_id) VALUES
(1,1),
(2,2),
(3,3),
(4,4),
(5,5),
(6,6),
(7,7),
(8,8),
(9,9),
(10,10),
(11,11),
(12,12),
(13,13),
(14,14),
(15,15),
(16,16),
(17,17),
(18,18),
(19,19),
(20,20);

INSERT INTO order_parts (order_id, part_id) VALUES
(1,2),(1,3),
(2,4),(2,5),
(3,6),(3,7),
(4,8),(4,9),
(5,10),(5,11),
(6,12),(6,13),
(7,14),(7,15),
(8,16),(8,17),
(9,18),(9,19),
(10,20),(10,1),
(11,2),(11,4),
(12,3),(12,5),
(13,6),(13,8),
(14,7),(14,9),
(15,10),(15,12),
(16,11),(16,13),
(17,14),(17,16),
(18,15),(18,17),
(19,18),(19,20),
(20,1),(20,19);

INSERT INTO order_services (order_id, service_id) VALUES
(1,2),
(2,3),
(3,4),
(4,5),
(5,6),
(6,7),
(7,8),
(8,9),
(9,10),
(10,11),
(11,12),
(12,13),
(13,14),
(14,15),
(15,16),
(16,17),
(17,18),
(18,19),
(19,20),
(20,1);