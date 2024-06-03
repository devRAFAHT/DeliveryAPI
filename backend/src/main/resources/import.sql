-- CATEGORIES
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

-- DISHES
INSERT INTO tb_dish (name, description, img_url, price, portion_size, preparation_time, dish_category_id) VALUES ('Macarrão Carbonara', 'Uma deliciosa massa carbonara com bacon e queijo parmesão.', 'https://example.com/macarrao_carbonara.jpg', 25.9, 2, 1800000000000, 1);
INSERT INTO tb_dish (name, description, img_url, price, portion_size, preparation_time, dish_category_id) VALUES ('Frango Parmesão', 'Frango empanado com molho de tomate e queijo parmesão.', 'https://example.com/frango_parmesao.jpg', 29.9, 1, 1800000000000, 2);
INSERT INTO tb_dish (name, description, img_url, price, portion_size, preparation_time, dish_category_id) VALUES ('Salmão Grelhado', 'Salmão grelhado com ervas finas.', 'https://example.com/salmao_grelhado.jpg', 35.9, 1, 1800000000000, 3);
INSERT INTO tb_dish (name, description, img_url, price, portion_size, preparation_time, dish_category_id) VALUES ('Risoto de Cogumelos', 'Risoto cremoso com mix de cogumelos frescos.', 'https://example.com/risoto_cogumelos.jpg', 28.5, 2, 1800000000000, 4);
INSERT INTO tb_dish (name, description, img_url, price, portion_size, preparation_time, dish_category_id) VALUES ('Bife à Cavalo', 'Bife grelhado com ovo frito.', 'https://example.com/bife_cavalo.jpg', 31.9, 1, 1800000000000, 5);
INSERT INTO tb_dish (name, description, img_url, price, portion_size, preparation_time, dish_category_id) VALUES ('Tacos Mexicanos', 'Tacos recheados com carne, queijo e molho especial.', 'https://example.com/tacos_mexicanos.jpg', 22.5, 3, 1800000000000, 6);
INSERT INTO tb_dish (name, description, img_url, price, portion_size, preparation_time, dish_category_id) VALUES ('Pizza Margherita', 'Pizza com molho de tomate, queijo mozzarella e manjericão.', 'https://example.com/pizza_margherita.jpg', 26.9, 1, 1800000000000, 7);
INSERT INTO tb_dish (name, description, img_url, price, portion_size, preparation_time, dish_category_id) VALUES ('Hambúrguer Clássico', 'Hambúrguer clássico com queijo, alface, tomate e molho especial.', 'https://example.com/hamburguer_classico.jpg', 19.9, 1, 1800000000000, 8);
INSERT INTO tb_dish (name, description, img_url, price, portion_size, preparation_time, dish_category_id) VALUES ('Sushi de Salmão', 'Sushi tradicional de salmão com arroz temperado.', 'https://example.com/sushi_salmao.jpg', 34.9, 2, 1800000000000, 9);
INSERT INTO tb_dish (name, description, img_url, price, portion_size, preparation_time, dish_category_id) VALUES ('Paella', 'Paella espanhola com frutos do mar.', 'https://example.com/paella.jpg', 39.9, 2, 1800000000000, 10);
INSERT INTO tb_dish (name, description, img_url, price, portion_size, preparation_time, dish_category_id) VALUES ('Curry de Frango', 'Curry de frango com arroz basmati.', 'https://example.com/curry_frango.jpg', 27.5, 1, 1800000000000, 11);
INSERT INTO tb_dish (name, description, img_url, price, portion_size, preparation_time, dish_category_id) VALUES ('Quiche Lorraine', 'Quiche francesa de bacon e queijo.', 'https://example.com/quiche_lorraine.jpg', 18.9, 1, 1800000000000, 12);
