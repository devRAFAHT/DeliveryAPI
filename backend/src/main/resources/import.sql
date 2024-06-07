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