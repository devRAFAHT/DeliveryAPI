-- DISH CATEGORIES
INSERT INTO tb_dish_category (name) VALUES ('Pizzas');
INSERT INTO tb_dish_category (name) VALUES ('Bebidas');
INSERT INTO tb_dish_category (name) VALUES ('Saladas');
INSERT INTO tb_dish_category (name) VALUES ('Massas');
INSERT INTO tb_dish_category (name) VALUES ('Sobremesas');
INSERT INTO tb_dish_category (name) VALUES ('Lanches');
INSERT INTO tb_dish_category (name) VALUES ('Cafés');
INSERT INTO tb_dish_category (name) VALUES ('Carnes');
INSERT INTO tb_dish_category (name) VALUES ('Sopas');
INSERT INTO tb_dish_category (name) VALUES ('Frutos do Mar');
INSERT INTO tb_dish_category (name) VALUES ('Vegetariano');
INSERT INTO tb_dish_category (name) VALUES ('Vegano');

-- DRINK CATEGORIES
INSERT INTO tb_drink_category (name) VALUES ('Refrigerantes');
INSERT INTO tb_drink_category (name) VALUES ('Sucos');
INSERT INTO tb_drink_category (name) VALUES ('Drinks');
INSERT INTO tb_drink_category (name) VALUES ('Coquetéis');
INSERT INTO tb_drink_category (name) VALUES ('Cervejas');
INSERT INTO tb_drink_category (name) VALUES ('Vinhos');
INSERT INTO tb_drink_category (name) VALUES ('Licores');
INSERT INTO tb_drink_category (name) VALUES ('Cafés');
INSERT INTO tb_drink_category (name) VALUES ('Chás');
INSERT INTO tb_drink_category (name) VALUES ('Águas');
INSERT INTO tb_drink_category (name) VALUES ('Smoothies');
INSERT INTO tb_drink_category (name) VALUES ('Milkshakes');

-- ADDITIONAL CATEGORIES
INSERT INTO tb_additional_category (name) VALUES ('Frutas');
INSERT INTO tb_additional_category (name) VALUES ('Coberturas');
INSERT INTO tb_additional_category (name) VALUES ('Grãos');
INSERT INTO tb_additional_category (name) VALUES ('Caldas');
INSERT INTO tb_additional_category (name) VALUES ('Oleaginosas');
INSERT INTO tb_additional_category (name) VALUES ('Granolas');
INSERT INTO tb_additional_category (name) VALUES ('Chocolates');
INSERT INTO tb_additional_category (name) VALUES ('Doces');
INSERT INTO tb_additional_category (name) VALUES ('Sementes');
INSERT INTO tb_additional_category (name) VALUES ('Cereais');
INSERT INTO tb_additional_category (name) VALUES ('Iogurtes');
INSERT INTO tb_additional_category (name) VALUES ('Mel');

-- RESTAURANT CATEGORIES
INSERT INTO tb_restaurant_category (name) VALUES ('Hamburgueria');
INSERT INTO tb_restaurant_category (name) VALUES ('Pizzaria');
INSERT INTO tb_restaurant_category (name) VALUES ('Churrascaria');
INSERT INTO tb_restaurant_category (name) VALUES ('Japonês');
INSERT INTO tb_restaurant_category (name) VALUES ('Italiana');
INSERT INTO tb_restaurant_category (name) VALUES ('Mexicana');
INSERT INTO tb_restaurant_category (name) VALUES ('Vegetariana');
INSERT INTO tb_restaurant_category (name) VALUES ('Vegana');
INSERT INTO tb_restaurant_category (name) VALUES ('Fast Food');
INSERT INTO tb_restaurant_category (name) VALUES ('Frutos do Mar');
INSERT INTO tb_restaurant_category (name) VALUES ('Árabe');
INSERT INTO tb_restaurant_category (name) VALUES ('Brasileira');
INSERT INTO tb_restaurant_category (name) VALUES ('Chinesa');
INSERT INTO tb_restaurant_category (name) VALUES ('Indiana');
INSERT INTO tb_restaurant_category (name) VALUES ('Francesa');
INSERT INTO tb_restaurant_category (name) VALUES ('Coreana');
INSERT INTO tb_restaurant_category (name) VALUES ('Espanhola');
INSERT INTO tb_restaurant_category (name) VALUES ('Peruana');
INSERT INTO tb_restaurant_category (name) VALUES ('Tailandesa');
INSERT INTO tb_restaurant_category (name) VALUES ('Outros');

-- DISHES
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id) VALUES ('Macarrão Carbonara', 'Uma deliciosa massa carbonara com bacon e queijo parmesão.', 'https://example.com/macarrao_carbonara.jpg', 25.90, 25.90, 0.00, 1, 1800000000000, 0, 0, 1);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id) VALUES ('Frango Parmesão', 'Frango empanado com molho de tomate e queijo parmesão.', 'https://example.com/frango_parmesao.jpg', 29.90, 29.90, 0.00, 0, 1800000000000, 0, 0, 2);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id) VALUES ('Salmão Grelhado', 'Salmão grelhado com ervas finas.', 'https://example.com/salmao_grelhado.jpg', 35.90, 35.90, 0.00, 0, 1800000000000, 0, 0, 3);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id) VALUES ('Risoto de Cogumelos', 'Risoto cremoso com mix de cogumelos frescos.', 'https://example.com/risoto_cogumelos.jpg', 28.50, 28.50, 0.00, 1, 1800000000000, 0, 0, 4);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id) VALUES ('Bife à Cavalo', 'Bife grelhado com ovo frito.', 'https://example.com/bife_cavalo.jpg', 31.90, 31.90, 0.00, 0, 1800000000000, 0, 0, 5);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id) VALUES ('Tacos Mexicanos', 'Tacos recheados com carne, queijo e molho especial.', 'https://example.com/tacos_mexicanos.jpg', 22.50, 22.50, 0.00, 2, 1800000000000, 0, 0, 6);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id) VALUES ('Pizza Margherita', 'Pizza com molho de tomate, queijo mozzarella e manjericão.', 'https://example.com/pizza_margherita.jpg', 26.90, 26.90, 0.00, 0, 1800000000000, 0, 0, 7);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id) VALUES ('Hambúrguer Clássico', 'Hambúrguer clássico com queijo, alface, tomate e molho especial.', 'https://example.com/hamburguer_classico.jpg', 19.90, 19.90, 0.00, 0, 1800000000000, 0, 0, 8);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id) VALUES ('Sushi de Salmão', 'Sushi tradicional de salmão com arroz temperado.', 'https://example.com/sushi_salmao.jpg', 34.90, 34.90, 0.00, 1, 1800000000000, 0, 0, 9);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id) VALUES ('Paella', 'Paella espanhola com frutos do mar.', 'https://example.com/paella.jpg', 39.90, 39.90, 0.00, 1, 1800000000000, 0, 0, 10);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id) VALUES ('Curry de Frango', 'Curry de frango com arroz basmati.', 'https://example.com/curry_frango.jpg', 27.50, 27.50, 0.00, 0, 1800000000000, 0, 0, 11);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id) VALUES ('Quiche Lorraine', 'Quiche francesa de bacon e queijo.', 'https://example.com/quiche_lorraine.jpg', 18.90, 18.90, 0.00, 0, 1800000000000, 0, 0, 12);

-- DRINKS
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id) VALUES ('Coca-Cola', 'Refrigerante de cola.', 'https://example.com/coca_cola.jpg', 4.50, 4.50, 0.00, 0, 0, 0, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id) VALUES ('Suco de Laranja', 'Suco natural de laranja.', 'https://example.com/suco_laranja.jpg', 6.90, 6.90, 0.00, 0, 0, 0, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id) VALUES ('Mojito', 'Coquetel de rum, limão, hortelã e água com gás.', 'https://example.com/mojito.jpg', 15.90, 15.90, 0.00, 0, 0, 0, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id) VALUES ('Caipirinha', 'Coquetel de cachaça, limão e açúcar.', 'https://example.com/caipirinha.jpg', 12.90, 12.90, 0.00, 0, 0, 0, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id) VALUES ('Heineken', 'Cerveja pilsen holandesa.', 'https://example.com/heineken.jpg', 9.90, 9.90, 0.00, 0, 0, 0, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id) VALUES ('Vinho Tinto', 'Vinho tinto encorpado.', 'https://example.com/vinho_tinto.jpg', 29.90, 29.90, 0.00, 0, 0, 0, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id) VALUES ('Licor de Chocolate', 'Licor cremoso de chocolate.', 'https://example.com/licor_chocolate.jpg', 18.90, 18.90, 0.00, 0, 0, 0, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id) VALUES ('Café Expresso', 'Café curto e forte.', 'https://example.com/cafe_expresso.jpg', 5.50, 5.50, 0.00, 0, 0, 0, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id) VALUES ('Chá Verde', 'Chá verde natural.', 'https://example.com/cha_verde.jpg', 4.90, 4.90, 0.00, 0, 0, 0, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id) VALUES ('Água Mineral', 'Água mineral natural.', 'https://example.com/agua_mineral.jpg', 3.00, 3.00, 0.00, 0, 0, 0, 1);

-- ADDITIONAL
INSERT INTO tb_additional (name, img_url, price, description, sale_status, additional_category_id) VALUES ('Morango', 'https://example.com/morango.jpg', 5.50, 'Morango fresco', 0, 1);
INSERT INTO tb_additional (name, img_url, price, description, sale_status, additional_category_id) VALUES ('Granola', 'https://example.com/granola.jpg', 2.00, 'Granola crocante', 0, 2);
INSERT INTO tb_additional (name, img_url, price, description, sale_status, additional_category_id) VALUES ('Mel', 'https://example.com/mel.jpg', 1.50, 'Mel puro', 0, 3);
INSERT INTO tb_additional (name, img_url, price, description, sale_status, additional_category_id) VALUES ('Banana', 'https://example.com/banana.jpg', 3.00, 'Banana madura', 0, 1);
INSERT INTO tb_additional (name, img_url, price, description, sale_status, additional_category_id) VALUES ('Leite Condensado', 'https://example.com/leite_condensado.jpg', 2.50, 'Leite condensado', 1, 4);
INSERT INTO tb_additional (name, img_url, price, description, sale_status, additional_category_id) VALUES ('Paçoca', 'https://example.com/pacoca.jpg', 2.00, 'Paçoca tradicional', 0, 2);
INSERT INTO tb_additional (name, img_url, price, description, sale_status, additional_category_id) VALUES ('Amendoim', 'https://example.com/amendoim.jpg', 1.50, 'Amendoim torrado', 0, 2);
INSERT INTO tb_additional (name, img_url, price, description, sale_status, additional_category_id) VALUES ('Coco Ralado', 'https://example.com/coco_ralado.jpg', 2.50, 'Coco ralado fresco', 0, 1);
INSERT INTO tb_additional (name, img_url, price, description, sale_status, additional_category_id) VALUES ('Calda de Chocolate', 'https://example.com/calda_chocolate.jpg', 2.00, 'Calda de chocolate', 1, 4);
INSERT INTO tb_additional (name, img_url, price, description, sale_status, additional_category_id) VALUES ('Castanha de Caju', 'https://example.com/castanha_caju.jpg', 3.00, 'Castanha de caju', 0, 2);

INSERT INTO tb_dish_additional (dish_id, additional_id) VALUES (1, 1);
INSERT INTO tb_dish_additional (dish_id, additional_id) VALUES (1, 4);
INSERT INTO tb_dish_additional (dish_id, additional_id) VALUES (2, 4);
INSERT INTO tb_dish_additional (dish_id, additional_id) VALUES (3, 7);
INSERT INTO tb_dish_additional (dish_id, additional_id) VALUES (4, 9);
INSERT INTO tb_dish_additional (dish_id, additional_id) VALUES (5, 3);
INSERT INTO tb_dish_additional (dish_id, additional_id) VALUES (6, 2);
INSERT INTO tb_dish_additional (dish_id, additional_id) VALUES (7, 6);
INSERT INTO tb_dish_additional (dish_id, additional_id) VALUES (8, 8);
INSERT INTO tb_dish_additional (dish_id, additional_id) VALUES (9, 5);
INSERT INTO tb_dish_additional (dish_id, additional_id) VALUES (10, 10);

-- ADDRESS
insert into tb_address (country, postal_code, state, city, neighborhood, street, residence_type, residence_number, floor, apartment_number, complement) values ('Brazil', '01000-000', 'São Paulo', 'São Paulo', 'Centro', 'Rua da Consolação', 0, 123, NULL, NULL, 'Casa amarela com portão branco');
insert into tb_address (country, postal_code, state, city, neighborhood, street, residence_type, residence_number, floor, apartment_number, complement) values ('Brazil', '22000-000', 'Rio de Janeiro', 'Rio de Janeiro', 'Copacabana', 'Avenida Atlântica', 1, 456, 10, 1002, 'Vista para o mar');
insert into tb_address (country, postal_code, state, city, neighborhood, street, residence_type, residence_number, floor, apartment_number, complement) values ('Brazil', '30100-000', 'Minas Gerais', 'Belo Horizonte', 'Savassi', 'Rua Pernambuco', 0, 789, NULL, NULL, 'Casa com jardim');
insert into tb_address (country, postal_code, state, city, neighborhood, street, residence_type, residence_number, floor, apartment_number, complement) values ('Brazil', '40000-000', 'Bahia', 'Salvador', 'Barra', 'Avenida Sete de Setembro', 1, 321, 8, 802, 'Perto do Farol da Barra');
insert into tb_address (country, postal_code, state, city, neighborhood, street, residence_type, residence_number, floor, apartment_number, complement) values ('Brazil', '50000-000', 'Pernambuco', 'Recife', 'Boa Viagem', 'Avenida Boa Viagem', 0, 654, NULL, NULL, 'Casa com piscina');
insert into tb_address (country, postal_code, state, city, neighborhood, street, residence_type, residence_number, floor, apartment_number, complement) values ('Brazil', '60000-000', 'Ceará', 'Fortaleza', 'Meireles', 'Rua Ana Bilhar', 1, 987, 12, 1205, 'Próximo à praia');
insert into tb_address (country, postal_code, state, city, neighborhood, street, residence_type, residence_number, floor, apartment_number, complement) values ('Brazil', '70000-000', 'Distrito Federal', 'Brasília', 'Asa Sul', 'SGAS 910', 0, 741, NULL, NULL, 'Casa moderna');
insert into tb_address (country, postal_code, state, city, neighborhood, street, residence_type, residence_number, floor, apartment_number, complement) values ('Brazil', '80000-000', 'Paraná', 'Curitiba', 'Batel', 'Avenida Batel', 1, 852, 15, 1503, 'Cobertura com vista panorâmica');
insert into tb_address (country, postal_code, state, city, neighborhood, street, residence_type, residence_number, floor, apartment_number, complement) values ('Brazil', '90000-000', 'Rio Grande do Sul', 'Porto Alegre', 'Moinhos de Vento', 'Rua Padre Chagas', 0, 963, NULL, NULL, 'Casa de esquina');
insert into tb_address (country, postal_code, state, city, neighborhood, street, residence_type, residence_number, floor, apartment_number, complement) values ('Brazil', '10000-000', 'Santa Catarina', 'Florianópolis', 'Lagoa da Conceição', 'Avenida das Rendeiras', 1, 258, 6, 604, 'Próximo à lagoa');

-- MENUS
INSERT INTO tb_menu (id, category, sale_status) VALUES (1, 'Almoço', 0);
INSERT INTO tb_menu (id, category, sale_status) VALUES (2, 'Jantar', 0);
INSERT INTO tb_menu (id, category, sale_status) VALUES (3, 'Sobremesas', 0);
INSERT INTO tb_menu (id, category, sale_status) VALUES (4, 'Bebidas', 0);
INSERT INTO tb_menu (id, category, sale_status) VALUES (5, 'Menu Executivo', 0);
INSERT INTO tb_menu (id, category, sale_status) VALUES (6, 'Menu Vegetariano', 0);
INSERT INTO tb_menu (id, category, sale_status) VALUES (7, 'Menu Infantil', 0);
INSERT INTO tb_menu (id, category, sale_status) VALUES (8, 'Menu Fitness', 0);
INSERT INTO tb_menu (id, category, sale_status) VALUES (9, 'Menu Degustação', 0);
INSERT INTO tb_menu (id, category, sale_status) VALUES (10, 'Menu Vegan', 0);

-- RESTAURANTS
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, address_id) VALUES ('McDonalds', 'Famosa cadeia de fast food', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 1);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, address_id) VALUES ('SushiMaki', 'Restaurante japonês especializado em sushi', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 2);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, address_id) VALUES ('Chin Bahia', 'Comida chinesa tradicional da Bahia', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 3);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, address_id) VALUES ('Outback Steakhouse', 'Restaurante especializado em carnes', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 4);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, address_id) VALUES ('Pizza Hut', 'Cadeia internacional de pizzarias', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 5);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, address_id) VALUES ('Burger King', 'Famosa rede de hambúrgueres', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 6);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, address_id) VALUES ('Starbucks', 'Cadeia de cafeterias', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 7);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, address_id) VALUES ('Subway', 'Rede de sanduíches submarinos', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 8);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, address_id) VALUES ('KFC', 'Famosa rede de frango frito', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 9);

-- OPERATING HOURS
INSERT INTO restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 1), (1, '08:00', '18:00', 1);
INSERT INTO restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 1), (1, '08:00', '18:00', 1), (2, '08:00', '18:00', 1), (3, '08:00', '18:00', 1), (4, '08:00', '18:00', 1), (5, '08:00', '18:00', 1), (6, '08:00', '18:00', 1);
INSERT INTO restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 2), (1, '08:00', '18:00', 2), (2, '08:00', '18:00', 2), (3, '08:00', '18:00', 2), (4, '08:00', '18:00', 2), (5, '08:00', '18:00', 2), (6, '08:00', '18:00', 2);
INSERT INTO restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 3), (1, '08:00', '18:00', 3), (2, '08:00', '18:00', 3), (3, '08:00', '18:00', 3), (4, '08:00', '18:00', 3), (5, '08:00', '18:00', 3), (6, '08:00', '18:00', 3);
INSERT INTO restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 4), (1, '08:00', '18:00', 4), (2, '08:00', '18:00', 4), (3, '08:00', '18:00', 4), (4, '08:00', '18:00', 4), (5, '08:00', '18:00', 4), (6, '08:00', '18:00', 4);
INSERT INTO restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 5), (1, '08:00', '18:00', 5), (2, '08:00', '18:00', 5), (3, '08:00', '18:00', 5), (4, '08:00', '18:00', 5), (5, '08:00', '18:00', 5), (6, '08:00', '18:00', 5);
INSERT INTO restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 6), (1, '08:00', '18:00', 6), (2, '08:00', '18:00', 6), (3, '08:00', '18:00', 6), (4, '08:00', '18:00', 6), (5, '08:00', '18:00', 6), (6, '08:00', '18:00', 6);
INSERT INTO restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 7), (1, '08:00', '18:00', 7), (2, '08:00', '18:00', 7), (3, '08:00', '18:00', 7), (4, '08:00', '18:00', 7), (5, '08:00', '18:00', 7), (6, '08:00', '18:00', 7);
INSERT INTO restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 8), (1, '08:00', '18:00', 8), (2, '08:00', '18:00', 8), (3, '08:00', '18:00', 8), (4, '08:00', '18:00', 8), (5, '08:00', '18:00', 8), (6, '08:00', '18:00', 8);
INSERT INTO restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 9), (1, '08:00', '18:00', 9), (2, '08:00', '18:00', 9), (3, '08:00', '18:00', 9), (4, '08:00', '18:00', 9), (5, '08:00', '18:00', 9), (6, '08:00', '18:00', 9);

-- RESTAURANTS WITH RESTAURANT CATEGORIES
INSERT INTO tb_association_restaurant_category (restaurant_id, category_id) VALUES (1, 9);
INSERT INTO tb_association_restaurant_category (restaurant_id, category_id) VALUES (2, 4);
INSERT INTO tb_association_restaurant_category (restaurant_id, category_id) VALUES (3, 12);
INSERT INTO tb_association_restaurant_category (restaurant_id, category_id) VALUES (4, 3);
INSERT INTO tb_association_restaurant_category (restaurant_id, category_id) VALUES (5, 2);
INSERT INTO tb_association_restaurant_category (restaurant_id, category_id) VALUES (6, 9);
INSERT INTO tb_association_restaurant_category (restaurant_id, category_id) VALUES (7, 17);
INSERT INTO tb_association_restaurant_category (restaurant_id, category_id) VALUES (8, 9);
INSERT INTO tb_association_restaurant_category (restaurant_id, category_id) VALUES (9, 12);

