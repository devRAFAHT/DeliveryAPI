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

-- RESTAURANTS
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, fixed_delivery_fee, address_id) VALUES ('McDonalds', 'Famosa cadeia de fast food', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 5.00, 1);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, fixed_delivery_fee, address_id) VALUES ('SushiMaki', 'Restaurante japonês especializado em sushi', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 5.00, 2);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, fixed_delivery_fee, address_id) VALUES ('Chin Bahia', 'Comida chinesa tradicional da Bahia', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 5.00, 3);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, fixed_delivery_fee, address_id) VALUES ('Outback Steakhouse', 'Restaurante especializado em carnes', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 5.00, 4);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, fixed_delivery_fee, address_id) VALUES ('Pizza Hut', 'Cadeia internacional de pizzarias', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 5.00, 5);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, fixed_delivery_fee, address_id) VALUES ('Burger King', 'Famosa rede de hambúrgueres', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 5.00, 6);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, fixed_delivery_fee, address_id) VALUES ('Starbucks', 'Cadeia de cafeterias', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 5.00, 7);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, fixed_delivery_fee, address_id) VALUES ('Subway', 'Rede de sanduíches submarinos', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 5.00, 8);
INSERT INTO tb_restaurant (name, description, created_at, phone_number, img_profile_url, img_background_url, average_price, estimated_delivery_time, is_open, fixed_delivery_fee, address_id) VALUES ('KFC', 'Famosa rede de frango frito', '2023-07-03 10:15:30', '+1234567890', 'https://example.com/profile.jpg', 'https://example.com/background.jpg', 50.0, 30, true, 5.00, 9);

-- MENUS
INSERT INTO tb_menu (category, sale_status, restaurant_id) VALUES ('Almoço', 0, 1);
INSERT INTO tb_menu (category, sale_status, restaurant_id) VALUES ('Jantar', 0, 2);
INSERT INTO tb_menu (category, sale_status, restaurant_id) VALUES ('Sobremesas', 0, 3);
INSERT INTO tb_menu (category, sale_status, restaurant_id) VALUES ('Menu Executivo', 0, 4);
INSERT INTO tb_menu (category, sale_status, restaurant_id) VALUES ('Menu Vegetariano', 0, 5);
INSERT INTO tb_menu (category, sale_status, restaurant_id) VALUES ('Menu Infantil', 0, 6);
INSERT INTO tb_menu (category, sale_status, restaurant_id) VALUES ('Menu Fitness', 0, 7);
INSERT INTO tb_menu (category, sale_status, restaurant_id) VALUES ('Menu Degustação', 0, 8);
INSERT INTO tb_menu (category, sale_status, restaurant_id) VALUES ('Menu Vegan', 0, 9);

-- Dishes
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id, menu_id) VALUES ('Macarrão Carbonara', 'Uma deliciosa massa carbonara com bacon e queijo parmesão.', 'https://example.com/macarrao_carbonara.jpg', 25.90, 25.90, 0.00, 1, 1800000000000, 0, 0, 1, 1);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id, menu_id) VALUES ('Frango Parmesão', 'Frango empanado com molho de tomate e queijo parmesão.', 'https://example.com/frango_parmesao.jpg', 29.90, 29.90, 0.00, 0, 1800000000000, 0, 0, 2, 1);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id, menu_id) VALUES ('Salmão Grelhado', 'Salmão grelhado com ervas finas.', 'https://example.com/salmao_grelhado.jpg', 35.90, 35.90, 0.00, 0, 1800000000000, 0, 0, 3, 1);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id, menu_id) VALUES ('Risoto de Cogumelos', 'Risoto cremoso com mix de cogumelos frescos.', 'https://example.com/risoto_cogumelos.jpg', 28.50, 28.50, 0.00, 1, 1800000000000, 0, 0, 4, 1);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id, menu_id) VALUES ('Bife à Cavalo', 'Bife grelhado com ovo frito.', 'https://example.com/bife_cavalo.jpg', 31.90, 31.90, 0.00, 0, 1800000000000, 0, 0, 5, 1);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id, menu_id) VALUES ('Tacos Mexicanos', 'Tacos recheados com carne, queijo e molho especial.', 'https://example.com/tacos_mexicanos.jpg', 22.50, 22.50, 0.00, 2, 1800000000000, 0, 0, 6, 2);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id, menu_id) VALUES ('Pizza Margherita', 'Pizza com molho de tomate, queijo mozzarella e manjericão.', 'https://example.com/pizza_margherita.jpg', 26.90, 26.90, 0.00, 0, 1800000000000, 0, 0, 7, 2);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id, menu_id) VALUES ('Hambúrguer Clássico', 'Hambúrguer clássico com queijo, alface, tomate e molho especial.', 'https://example.com/hamburguer_classico.jpg', 19.90, 19.90, 0.00, 0, 1800000000000, 0, 0, 8, 2);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id, menu_id) VALUES ('Sushi de Salmão', 'Sushi tradicional de salmão com arroz temperado.', 'https://example.com/sushi_salmao.jpg', 34.90, 34.90, 0.00, 1, 1800000000000, 0, 0, 9, 2);
INSERT INTO tb_dish (name, description, img_url, original_price, current_price, discount_in_percentage, portion_size, preparation_time, food_restriction, sale_status, dish_category_id, menu_id) VALUES ('Paella', 'Paella espanhola com frutos do mar.', 'https://example.com/paella.jpg', 39.90, 39.90, 0.00, 1, 1800000000000, 0, 0, 10, 2);

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

-- DRINKS
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id, menu_id) VALUES ('Coca-Cola', 'Refrigerante de cola.', 'https://example.com/coca_cola.jpg', 4.50, 4.50, 0.00, 0, 0, 0, 1, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id, menu_id) VALUES ('Suco de Laranja', 'Suco natural de laranja.', 'https://example.com/suco_laranja.jpg', 6.90, 6.90, 0.00, 0, 0, 0, 1, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id, menu_id) VALUES ('Mojito', 'Coquetel de rum, limão, hortelã e água com gás.', 'https://example.com/mojito.jpg', 15.90, 15.90, 0.00, 0, 0, 0, 1, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id, menu_id) VALUES ('Caipirinha', 'Coquetel de cachaça, limão e açúcar.', 'https://example.com/caipirinha.jpg', 12.90, 12.90, 0.00, 0, 0, 0, 1, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id, menu_id) VALUES ('Heineken', 'Cerveja pilsen holandesa.', 'https://example.com/heineken.jpg', 9.90, 9.90, 0.00, 0, 0, 0, 1, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id, menu_id) VALUES ('Vinho Tinto', 'Vinho tinto encorpado.', 'https://example.com/vinho_tinto.jpg', 29.90, 29.90, 0.00, 0, 0, 0, 1, 1);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id, menu_id) VALUES ('Licor de Chocolate', 'Licor cremoso de chocolate.', 'https://example.com/licor_chocolate.jpg', 18.90, 18.90, 0.00, 0, 0, 0, 1, 2);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id, menu_id) VALUES ('Café Expresso', 'Café curto e forte.', 'https://example.com/cafe_expresso.jpg', 5.50, 5.50, 0.00, 0, 0, 0, 1, 2);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id, menu_id) VALUES ('Chá Verde', 'Chá verde natural.', 'https://example.com/cha_verde.jpg', 4.90, 4.90, 0.00, 0, 0, 0, 1, 2);
INSERT INTO tb_drink (name, description, img_url, original_price, current_price, discount_in_percentage, size, unit_measurement, sale_status, drink_category_id, menu_id) VALUES ('Água Mineral', 'Água mineral natural.', 'https://example.com/agua_mineral.jpg', 3.00, 3.00, 0.00, 0, 0, 0, 1, 2);

-- OPERATING HOURS
INSERT INTO tb_restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 1), (1, '08:00', '18:00', 1);
INSERT INTO tb_restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 1), (1, '08:00', '18:00', 1), (2, '08:00', '18:00', 1), (3, '08:00', '18:00', 1), (4, '08:00', '18:00', 1), (5, '08:00', '18:00', 1), (6, '08:00', '18:00', 1);
INSERT INTO tb_restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 2), (1, '08:00', '18:00', 2), (2, '08:00', '18:00', 2), (3, '08:00', '18:00', 2), (4, '08:00', '18:00', 2), (5, '08:00', '18:00', 2), (6, '08:00', '18:00', 2);
INSERT INTO tb_restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 3), (1, '08:00', '18:00', 3), (2, '08:00', '18:00', 3), (3, '08:00', '18:00', 3), (4, '08:00', '18:00', 3), (5, '08:00', '18:00', 3), (6, '08:00', '18:00', 3);
INSERT INTO tb_restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 4), (1, '08:00', '18:00', 4), (2, '08:00', '18:00', 4), (3, '08:00', '18:00', 4), (4, '08:00', '18:00', 4), (5, '08:00', '18:00', 4), (6, '08:00', '18:00', 4);
INSERT INTO tb_restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 5), (1, '08:00', '18:00', 5), (2, '08:00', '18:00', 5), (3, '08:00', '18:00', 5), (4, '08:00', '18:00', 5), (5, '08:00', '18:00', 5), (6, '08:00', '18:00', 5);
INSERT INTO tb_restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 6), (1, '08:00', '18:00', 6), (2, '08:00', '18:00', 6), (3, '08:00', '18:00', 6), (4, '08:00', '18:00', 6), (5, '08:00', '18:00', 6), (6, '08:00', '18:00', 6);
INSERT INTO tb_restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 7), (1, '08:00', '18:00', 7), (2, '08:00', '18:00', 7), (3, '08:00', '18:00', 7), (4, '08:00', '18:00', 7), (5, '08:00', '18:00', 7), (6, '08:00', '18:00', 7);
INSERT INTO tb_restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 8), (1, '08:00', '18:00', 8), (2, '08:00', '18:00', 8), (3, '08:00', '18:00', 8), (4, '08:00', '18:00', 8), (5, '08:00', '18:00', 8), (6, '08:00', '18:00', 8);
INSERT INTO tb_restaurant_operating_hours (day_of_week, opening_time, closing_time, restaurant_id) VALUES (0, '08:00', '18:00', 9), (1, '08:00', '18:00', 9), (2, '08:00', '18:00', 9), (3, '08:00', '18:00', 9), (4, '08:00', '18:00', 9), (5, '08:00', '18:00', 9), (6, '08:00', '18:00', 9);

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

-- BAGS
INSERT INTO TB_BAG (DISCOUNT, QUANTITY_OF_ITEMS, TOTAL_PRICE) VALUES (0.10, 2, 20.00);
INSERT INTO TB_BAG (DISCOUNT, QUANTITY_OF_ITEMS, TOTAL_PRICE) VALUES (0.15, 3, 35.00);
INSERT INTO TB_BAG (DISCOUNT, QUANTITY_OF_ITEMS, TOTAL_PRICE) VALUES (0.20, 1, 15.00);
INSERT INTO TB_BAG (DISCOUNT, QUANTITY_OF_ITEMS, TOTAL_PRICE) VALUES (0.05, 5, 50.00);
INSERT INTO TB_BAG (DISCOUNT, QUANTITY_OF_ITEMS, TOTAL_PRICE) VALUES (0.10, 4, 40.00);
INSERT INTO TB_BAG (DISCOUNT, QUANTITY_OF_ITEMS, TOTAL_PRICE) VALUES (0.25, 2, 30.00);
INSERT INTO TB_BAG (DISCOUNT, QUANTITY_OF_ITEMS, TOTAL_PRICE) VALUES (0.30, 1, 10.00);
INSERT INTO TB_BAG (DISCOUNT, QUANTITY_OF_ITEMS, TOTAL_PRICE) VALUES (0.05, 6, 60.00);
INSERT INTO TB_BAG (DISCOUNT, QUANTITY_OF_ITEMS, TOTAL_PRICE) VALUES (0.15, 3, 45.00);
INSERT INTO TB_BAG (DISCOUNT, QUANTITY_OF_ITEMS, TOTAL_PRICE) VALUES (0.10, 4, 55.00);

-- -- ASSOCIATION BAGS DISHES
INSERT INTO tb_bag_dish (bag_id, dish_id) VALUES (1, 1);
INSERT INTO tb_bag_dish (bag_id, dish_id) VALUES (1, 2);
INSERT INTO tb_bag_dish (bag_id, dish_id) VALUES (2, 3);
INSERT INTO tb_bag_dish (bag_id, dish_id) VALUES (2, 4);
INSERT INTO tb_bag_dish (bag_id, dish_id) VALUES (3, 5);
INSERT INTO tb_bag_dish (bag_id, dish_id) VALUES (3, 6);
INSERT INTO tb_bag_dish (bag_id, dish_id) VALUES (4, 7);
INSERT INTO tb_bag_dish (bag_id, dish_id) VALUES (4, 8);
INSERT INTO tb_bag_dish (bag_id, dish_id) VALUES (5, 9);
INSERT INTO tb_bag_dish (bag_id, dish_id) VALUES (5, 10);

-- -- ASSOCIATION BAGS DRINKS
INSERT INTO tb_bag_drink (bag_id, drink_id) VALUES (6, 1);
INSERT INTO tb_bag_drink (bag_id, drink_id) VALUES (6, 2);
INSERT INTO tb_bag_drink (bag_id, drink_id) VALUES (7, 3);
INSERT INTO tb_bag_drink (bag_id, drink_id) VALUES (7, 4);
INSERT INTO tb_bag_drink (bag_id, drink_id) VALUES (8, 5);
INSERT INTO tb_bag_drink (bag_id, drink_id) VALUES (8, 6);
INSERT INTO tb_bag_drink (bag_id, drink_id) VALUES (9, 7);
INSERT INTO tb_bag_drink (bag_id, drink_id) VALUES (9, 8);
INSERT INTO tb_bag_drink (bag_id, drink_id) VALUES (10, 9);
INSERT INTO tb_bag_drink (bag_id, drink_id) VALUES (10, 10);

-- USERS
INSERT INTO tb_user (user_name, first_name, last_name, email, personal_document, date_of_birth, gender, phone_number, profile_picture_url, biography, created_at, updated_at, active, bag_id) VALUES ('john_doe', 'John', 'Doe', 'john.doe@example.com', '12345678900', '1990-01-01', 'male', '555-1234', 'http://example.com/profile.jpg', 'Software developer from California.', '2023-07-11T14:48:00Z', '2023-07-11T14:48:00Z', true, 1);
INSERT INTO tb_user (user_name, first_name, last_name, email, personal_document, date_of_birth, gender, phone_number, profile_picture_url, biography, created_at, updated_at, active, bag_id) VALUES ('jane_smith', 'Jane', 'Smith', 'jane.smith@example.com', '98765432100', '1985-05-15', 'female', '555-5678', 'http://example.com/profile2.jpg', 'Graphic designer from New York.', '2023-07-11T14:50:00Z', '2023-07-11T14:50:00Z', true, 2);
INSERT INTO tb_user (user_name, first_name, last_name, email, personal_document, date_of_birth, gender, phone_number, profile_picture_url, biography, created_at, updated_at, active, bag_id) VALUES ('michael_jones', 'Michael', 'Jones', 'michael.jones@example.com', '11223344550', '1988-03-22', 'male', '555-8765', 'http://example.com/profile3.jpg', 'Marketing specialist from Los Angeles.', '2023-07-11T14:52:00Z', '2023-07-11T14:52:00Z', true, 3);
INSERT INTO tb_user (user_name, first_name, last_name, email, personal_document, date_of_birth, gender, phone_number, profile_picture_url, biography, created_at, updated_at, active, bag_id) VALUES ('alice_brown', 'Alice', 'Brown', 'alice.brown@example.com', '55667788900', '1992-08-10', 'female', '555-2345', 'http://example.com/profile4.jpg', 'HR manager from Chicago.', '2023-07-11T14:54:00Z', '2023-07-11T14:54:00Z', true, 4);
INSERT INTO tb_user (user_name, first_name, last_name, email, personal_document, date_of_birth, gender, phone_number, profile_picture_url, biography, created_at, updated_at, active, bag_id) VALUES ('charles_davis', 'Charles', 'Davis', 'charles.davis@example.com', '99887766500', '1995-11-05', 'male', '555-3456', 'http://example.com/profile5.jpg', 'Software engineer from Seattle.', '2023-07-11T14:56:00Z', '2023-07-11T14:56:00Z', true, 5);
INSERT INTO tb_user (user_name, first_name, last_name, email, personal_document, date_of_birth, gender, phone_number, profile_picture_url, biography, created_at, updated_at, active, bag_id) VALUES ('emma_white', 'Emma', 'White', 'emma.white@example.com', '33445566700', '1991-02-28', 'female', '555-4567', 'http://example.com/profile6.jpg', 'Product manager from San Francisco.', '2023-07-11T14:58:00Z', '2023-07-11T14:58:00Z', true, 6);
INSERT INTO tb_user (user_name, first_name, last_name, email, personal_document, date_of_birth, gender, phone_number, profile_picture_url, biography, created_at, updated_at, active, bag_id) VALUES ('george_harris', 'George', 'Harris', 'george.harris@example.com', '22334455600', '1987-07-12', 'male', '555-5678', 'http://example.com/profile7.jpg', 'Data scientist from Boston.', '2023-07-11T15:00:00Z', '2023-07-11T15:00:00Z', true, 7);
INSERT INTO tb_user (user_name, first_name, last_name, email, personal_document, date_of_birth, gender, phone_number, profile_picture_url, biography, created_at, updated_at, active, bag_id) VALUES ('isabella_clark', 'Isabella', 'Clark', 'isabella.clark@example.com', '77665544300', '1993-04-18', 'female', '555-6789', 'http://example.com/profile8.jpg', 'UX designer from Austin.', '2023-07-11T15:02:00Z', '2023-07-11T15:02:00Z', true, 8);
INSERT INTO tb_user (user_name, first_name, last_name, email, personal_document, date_of_birth, gender, phone_number, profile_picture_url, biography, created_at, updated_at, active, bag_id) VALUES ('jack_martin', 'Jack', 'Martin', 'jack.martin@example.com', '88990011200', '1989-09-09', 'male', '555-7890', 'http://example.com/profile9.jpg', 'Backend developer from Denver.', '2023-07-11T15:04:00Z', '2023-07-11T15:04:00Z', true, 9);
INSERT INTO tb_user (user_name, first_name, last_name, email, personal_document, date_of_birth, gender, phone_number, profile_picture_url, biography, created_at, updated_at, active, bag_id) VALUES ('olivia_walker', 'Olivia', 'Walker', 'olivia.walker@example.com', '55443322100', '1994-06-25', 'female', '555-8901', 'http://example.com/profile10.jpg', 'Frontend developer from Miami.', '2023-07-11T15:06:00Z', '2023-07-11T15:06:00Z', true, 10);

-- ORDERS
INSERT INTO tb_order (delivery_fee, estimated_delivery_time, sub_total, total_price, canceled_at, created_at, order_status, restaurant_id, user_id, special_request) VALUES (5.00, 1800000000000, 50.00, 55.00, NULL, '2023-07-17T12:30:00', 8, 1, 1, 'Extra ketchup');
INSERT INTO tb_order (delivery_fee, estimated_delivery_time, sub_total, total_price, canceled_at, created_at, order_status, restaurant_id, user_id, special_request) VALUES (3.00, 1800000000000, 30.00, 33.00, NULL, '2023-07-17T12:35:00', 0, 2, 2, 'No onions');
INSERT INTO tb_order (delivery_fee, estimated_delivery_time, sub_total, total_price, canceled_at, created_at, order_status, restaurant_id, user_id, special_request) VALUES (4.00, 1800000000000, 40.00, 44.00, NULL, '2023-07-17T12:40:00', 0, 3, 3, 'Spicy');
INSERT INTO tb_order (delivery_fee, estimated_delivery_time, sub_total, total_price, canceled_at, created_at, order_status, restaurant_id, user_id, special_request) VALUES (2.50, 1800000000000, 25.00, 27.50, NULL, '2023-07-17T12:45:00', 0, 4, 4, 'No cheese');
INSERT INTO tb_order (delivery_fee, estimated_delivery_time, sub_total, total_price, canceled_at, created_at, order_status, restaurant_id, user_id, special_request) VALUES (3.50, 1800000000000, 35.00, 38.50, NULL, '2023-07-17T12:50:00', 0, 5, 5, 'Extra sauce');
INSERT INTO tb_order (delivery_fee, estimated_delivery_time, sub_total, total_price, canceled_at, created_at, order_status, restaurant_id, user_id, special_request) VALUES (4.50, 1800000000000, 50.00, 54.50, NULL, '2023-07-17T12:55:00', 0, 6, 6, 'Well done');
INSERT INTO tb_order (delivery_fee, estimated_delivery_time, sub_total, total_price, canceled_at, created_at, order_status, restaurant_id, user_id, special_request) VALUES (3.75, 1800000000000, 37.00, 40.75, NULL, '2023-07-17T13:00:00', 0, 7, 7, 'Gluten-free');
INSERT INTO tb_order (delivery_fee, estimated_delivery_time, sub_total, total_price, canceled_at, created_at, order_status, restaurant_id, user_id, special_request) VALUES (5.25, 1800000000000, 52.00, 57.25, NULL, '2023-07-17T13:05:00', 0, 8, 8, 'Extra napkins');
INSERT INTO tb_order (delivery_fee, estimated_delivery_time, sub_total, total_price, canceled_at, created_at, order_status, restaurant_id, user_id, special_request) VALUES (4.00, 1800000000000, 40.00, 44.00, NULL, '2023-07-17T13:10:00', 0, 9, 9, 'Low salt');

-- ASSOCIATION ORDER DISH
INSERT INTO TB_ORDER_DISH (DISH_ID, ORDER_ID) VALUES (1, 1);
INSERT INTO TB_ORDER_DISH (DISH_ID, ORDER_ID) VALUES (2, 2);
INSERT INTO TB_ORDER_DISH (DISH_ID, ORDER_ID) VALUES (3, 3);
INSERT INTO TB_ORDER_DISH (DISH_ID, ORDER_ID) VALUES (4, 4);
INSERT INTO TB_ORDER_DISH (DISH_ID, ORDER_ID) VALUES (5, 5);
INSERT INTO TB_ORDER_DISH (DISH_ID, ORDER_ID) VALUES (6, 6);
INSERT INTO TB_ORDER_DISH (DISH_ID, ORDER_ID) VALUES (7, 7);
INSERT INTO TB_ORDER_DISH (DISH_ID, ORDER_ID) VALUES (8, 8);
INSERT INTO TB_ORDER_DISH (DISH_ID, ORDER_ID) VALUES (9, 9);

-- ASSOCIATION ORDER DRINK
INSERT INTO TB_ORDER_DRINK (DRINK_ID, ORDER_ID) VALUES (1, 1);
INSERT INTO TB_ORDER_DRINK (DRINK_ID, ORDER_ID) VALUES (2, 2);
INSERT INTO TB_ORDER_DRINK (DRINK_ID, ORDER_ID) VALUES (3, 3);
INSERT INTO TB_ORDER_DRINK (DRINK_ID, ORDER_ID) VALUES (4, 4);
INSERT INTO TB_ORDER_DRINK (DRINK_ID, ORDER_ID) VALUES (5, 5);
INSERT INTO TB_ORDER_DRINK (DRINK_ID, ORDER_ID) VALUES (6, 6);
INSERT INTO TB_ORDER_DRINK (DRINK_ID, ORDER_ID) VALUES (7, 7);
INSERT INTO TB_ORDER_DRINK (DRINK_ID, ORDER_ID) VALUES (8, 8);
INSERT INTO TB_ORDER_DRINK (DRINK_ID, ORDER_ID) VALUES (9, 9);

-- ASSESSMENTS
INSERT INTO tb_assessment (points, created_at, dish_id, drink_id, restaurant_id, updated_at, user_id, comment) VALUES (5, '2024-08-01 10:00:00', 1, NULL, 1, '2024-08-01 10:00:00', 1, 'Ótimo prato, muito saboroso!');
INSERT INTO tb_assessment (points, created_at, dish_id, drink_id, restaurant_id, updated_at, user_id, comment) VALUES (4, '2024-08-01 10:10:00', NULL, 2, 1, '2024-08-01 10:10:00', 2, 'Bebida excelente, mas poderia ser mais gelada.');
INSERT INTO tb_assessment (points, created_at, dish_id, drink_id, restaurant_id, updated_at, user_id, comment) VALUES (3, '2024-08-01 10:20:00', 2, NULL, 2, '2024-08-01 10:20:00', 3, 'Prato estava bom, mas a porção era pequena.');
INSERT INTO tb_assessment (points, created_at, dish_id, drink_id, restaurant_id, updated_at, user_id, comment) VALUES (5, '2024-08-01 10:30:00', 3, NULL, 2, '2024-08-01 10:30:00', 4, 'Excelente qualidade, recomendo!');
INSERT INTO tb_assessment (points, created_at, dish_id, drink_id, restaurant_id, updated_at, user_id, comment) VALUES (2, '2024-08-01 10:40:00', NULL, 3, 3, '2024-08-01 10:40:00', 5, 'A bebida estava boa, mas o atendimento foi ruim.');
INSERT INTO tb_assessment (points, created_at, dish_id, drink_id, restaurant_id, updated_at, user_id, comment) VALUES (4, '2024-08-01 10:50:00', 4, NULL, 3, '2024-08-01 10:50:00', 6, 'Prato estava muito saboroso, mas demorou para chegar.');
INSERT INTO tb_assessment (points, created_at, dish_id, drink_id, restaurant_id, updated_at, user_id, comment) VALUES (5, '2024-08-01 11:00:00', 5, NULL, 4, '2024-08-01 11:00:00', 7, 'A comida estava maravilhosa, como sempre!');
INSERT INTO tb_assessment (points, created_at, dish_id, drink_id, restaurant_id, updated_at, user_id, comment) VALUES (1, '2024-08-01 11:10:00', NULL, 4, 4, '2024-08-01 11:10:00', 8, 'O prato estava frio e sem sabor.');
INSERT INTO tb_assessment (points, created_at, dish_id, drink_id, restaurant_id, updated_at, user_id, comment) VALUES (4, '2024-08-01 11:20:00', 6, NULL, 5, '2024-08-01 11:20:00', 9, 'Boa refeição, mas a apresentação poderia ser melhor.');
INSERT INTO tb_assessment (points, created_at, dish_id, drink_id, restaurant_id, updated_at, user_id, comment) VALUES (3, '2024-08-01 11:30:00', NULL, 5, 5, '2024-08-01 11:30:00', 10, 'A bebida estava boa, mas o prato não atendeu minhas expectativas.');

INSERT INTO TB_ASSESSMENT_UPDATE_HISTORY (ASSESSMENT_ID, UPDATE_DATE, COMMENT) VALUES (1, '2024-08-01 12:00:00', 'N/A');
INSERT INTO TB_ASSESSMENT_UPDATE_HISTORY (ASSESSMENT_ID, UPDATE_DATE, COMMENT) VALUES (2, '2024-08-01 12:05:00', 'N/A');
INSERT INTO TB_ASSESSMENT_UPDATE_HISTORY (ASSESSMENT_ID, UPDATE_DATE, COMMENT) VALUES (3, '2024-08-01 12:10:00', 'N/A');
INSERT INTO TB_ASSESSMENT_UPDATE_HISTORY (ASSESSMENT_ID, UPDATE_DATE, COMMENT) VALUES (4, '2024-08-01 12:15:00', 'N/A');
INSERT INTO TB_ASSESSMENT_UPDATE_HISTORY (ASSESSMENT_ID, UPDATE_DATE, COMMENT) VALUES (5, '2024-08-01 12:20:00', 'N/A');
INSERT INTO TB_ASSESSMENT_UPDATE_HISTORY (ASSESSMENT_ID, UPDATE_DATE, COMMENT) VALUES (6, '2024-08-01 12:25:00', 'N/A');
INSERT INTO TB_ASSESSMENT_UPDATE_HISTORY (ASSESSMENT_ID, UPDATE_DATE, COMMENT) VALUES (7, '2024-08-01 12:30:00', 'N/A');
INSERT INTO TB_ASSESSMENT_UPDATE_HISTORY (ASSESSMENT_ID, UPDATE_DATE, COMMENT) VALUES (8, '2024-08-01 12:35:00', 'N/A');
INSERT INTO TB_ASSESSMENT_UPDATE_HISTORY (ASSESSMENT_ID, UPDATE_DATE, COMMENT) VALUES (9, '2024-08-01 12:40:00', 'N/A');
INSERT INTO TB_ASSESSMENT_UPDATE_HISTORY (ASSESSMENT_ID, UPDATE_DATE, COMMENT) VALUES (10, '2024-08-01 12:45:00', 'N/A');

-- assessments responses
INSERT INTO tb_assessment_response (assessment_id, created_at, id, restaurant_id, comment) VALUES (1, '2024-08-02 09:00:00', 1, 1, 'Agradecemos pelo feedback positivo! Ficamos felizes que você tenha gostado do prato. Esperamos vê-lo novamente em breve.');
INSERT INTO tb_assessment_response (assessment_id, created_at, id, restaurant_id, comment) VALUES (2, '2024-08-02 09:05:00', 2, 1, 'Obrigado pela avaliação! Vamos trabalhar para que a próxima bebida esteja ainda mais gelada. Agradecemos sua compreensão.');
INSERT INTO tb_assessment_response (assessment_id, created_at, id, restaurant_id, comment) VALUES (3, '2024-08-02 09:10:00', 3, 2, 'Lamentamos que a porção não tenha sido do seu agrado. Estamos revisando o tamanho das porções para melhorar a experiência.');
INSERT INTO tb_assessment_response (assessment_id, created_at, id, restaurant_id, comment) VALUES (4, '2024-08-02 09:15:00', 4, 2, 'Muito obrigado pela avaliação! Fico feliz que tenha gostado da qualidade do prato. Sua recomendação é muito apreciada.');
INSERT INTO tb_assessment_response (assessment_id, created_at, id, restaurant_id, comment) VALUES (5, '2024-08-02 09:20:00', 5, 3, 'Pedimos desculpas pelo atendimento ruim. Estamos trabalhando para melhorar o serviço e garantir uma melhor experiência.');
INSERT INTO tb_assessment_response (assessment_id, created_at, id, restaurant_id, comment) VALUES (6, '2024-08-02 09:25:00', 6, 3, 'Obrigado pelo seu feedback! Vamos tentar melhorar o tempo de entrega para garantir que a próxima refeição chegue mais rápido.');
INSERT INTO tb_assessment_response (assessment_id, created_at, id, restaurant_id, comment) VALUES (7, '2024-08-02 09:30:00', 7, 4, 'Agradecemos por sua avaliação positiva! É sempre um prazer saber que nossos clientes estão satisfeitos com a comida.');
INSERT INTO tb_assessment_response (assessment_id, created_at, id, restaurant_id, comment) VALUES (8, '2024-08-02 09:35:00', 8, 4, 'Lamentamos que o prato não tenha atendido suas expectativas. Estamos avaliando o feedback para evitar que isso aconteça novamente.');
INSERT INTO tb_assessment_response (assessment_id, created_at, id, restaurant_id, comment) VALUES (9, '2024-08-02 09:40:00', 9, 5, 'Obrigado pelo feedback! Vamos trabalhar na apresentação dos pratos para que fiquem ainda mais atraentes.');
INSERT INTO tb_assessment_response (assessment_id, created_at, id, restaurant_id, comment) VALUES (10, '2024-08-02 09:45:00', 10, 5, 'Sentimos muito que o prato não tenha atendido suas expectativas. Seu feedback é valioso e será considerado para futuras melhorias.');