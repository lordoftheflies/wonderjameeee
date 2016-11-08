--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.14
-- Dumped by pg_dump version 9.5.5

-- Started on 2016-11-08 13:09:14 CET

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

--
-- TOC entry 2033 (class 0 OID 25106)
-- Dependencies: 178
-- Data for Name: networknodeentity; Type: TABLE DATA; Schema: public; Owner: topflavon
--

INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1471, true, 1, NULL);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1485, true, 9, 1471);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1473, true, 2, 1471);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1483, true, 7, 1471);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1475, true, 3, 1483);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1484, true, 8, 1473);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1479, true, 5, 1473);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1481, true, 6, 1473);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1477, true, 4, 1483);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1486, true, 0, 1471);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1509, true, 0, 1471);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1533, true, 0, 1471);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1543, true, 0, 1471);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1545, true, 0, 1543);
INSERT INTO networknodeentity (id, active, codes, parent_id) VALUES (1547, true, 0, 1543);


--
-- TOC entry 2027 (class 0 OID 25064)
-- Dependencies: 172
-- Data for Name: accountentity; Type: TABLE DATA; Schema: public; Owner: topflavon
--

INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('a8f0127b-bec5-4aa3-b471-00a70eef6f2e', 'istvan.nagy4@gmail.com', true, NULL, 'Nagy  István ', 'sun13riz', '+36706796791', 'en', 'ADMIN', NULL, 1547);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('876fd3e9-bfc8-4c37-8afa-112c4d729f37', 'admin@topflavon.net', true, NULL, 'Teszt adminisztrátor', 'qwe123', NULL, 'en', 'ADMIN', NULL, 1471);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('7c9b2a4c-0df1-465f-97fb-acc1a6d372dc', 'admin@cherubits.hu', true, NULL, 'Cherubits (Felhasználó)', 'qwe123', NULL, 'hu', 'USER', 'ffLuhgZlQDs:APA91bEV8x-Zky3Lt8h-6HuDjQp-sCga7wsUucy_OUnezIdn3SaoqxQw5jf1ImfXzX2xlQbEFAsmlHeqWackLwDYEkeFVHlSfQMJW8He1uWFksxj7HeAq_RkvWy-G1cwZN4E_lJT--hl', 1477);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('71209012-abd7-4ca6-a400-37dd5e95a993', 'administrator@cherubits.hu', true, NULL, 'Cherubits (Adminisztrátor)', 'qwe123', NULL, 'en', 'ADMIN', 'ffLuhgZlQDs:APA91bEV8x-Zky3Lt8h-6HuDjQp-sCga7wsUucy_OUnezIdn3SaoqxQw5jf1ImfXzX2xlQbEFAsmlHeqWackLwDYEkeFVHlSfQMJW8He1uWFksxj7HeAq_RkvWy-G1cwZN4E_lJT--hl', 1475);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('58fa4b88-b1e9-4ef7-912d-77ee8f598005', 'blaise.peczely@gmail.com', true, NULL, 'Péczely Balázs', 'qwe123', NULL, 'en', 'ADMIN', 'dYwLQdSqXEU:APA91bHDFW3r6zm7AyPcRkzu7F8PqGgdyJxJDYktmX3UEmuJAwRzoqN3qyGnsJDG7LPBG0V4_N4AY4N559UiPNxIia1flPGUPgE4o7RgyRvs5raxthxUSOaHiyYyaA5Cyh8Mjq7tcwAx', 1533);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('3c025369-f8b0-47f4-8cb1-da609206fdfc', 'laszlo.hegedus.iii@gmail.com', true, NULL, 'Hegedűs László', 'qwe123qwe123', '+36706241010', 'en', 'USER', NULL, 1486);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('121fd37e-376b-4c08-a2d5-76984dc60cc3', 'laszlo.hegedus.iii@cherubits.hu', true, NULL, 'Csicsikawa', 'c432c552-ca9a-4375-a11e-6da8f2ee57ce', NULL, 'en', 'ADMIN', NULL, 1509);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('710fd1b7-e59d-4e49-b7ce-d8d76bc18d3e', 'jungkriszta@yahoo.com', true, NULL, 'Jungné Kovács Krisztina', 'NCC1701', '+36301234567', 'en', 'ADMIN', 'fCSGSXUs-P4:APA91bGpcYJ4jXrszcC5wPTO-_CR5rPzHtU6ys1CHCbqRBbSumuTwIwJ7uvYR-GuJJLXaB2HT3yElcNgoOX0V7KhZANj-8t1EJsrnE0iDMAP4FfXTtcvdSlwWWBc7nhapc_bFi2D8hbT', 1543);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('a1ea1fbf-0d6b-4090-8369-13c64224de1f', 'gmflavon@gmail.com', true, NULL, 'Molnár Gábor', 'e16f3b2d-5253-46bc-b93b-285f3893cd6d', NULL, 'hu', 'ADMIN', NULL, 1545);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('3a3fad75-fc35-4950-831e-826452731402', 'webmaster@topflavon.net', true, NULL, 'Teszt felhasználó', 'qwe123', NULL, 'hu', 'USER', NULL, 1473);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('94cf4ca7-bd0e-4188-9981-5ec1358c03bf', 'teszt.elek@gmail.com', true, NULL, 'Teszt Elek', 'qwe123', NULL, 'hu', 'USER', NULL, 1479);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('5a6566ce-e87a-44ad-9831-288f9a14b191', 'feriahegyrol@gmail.com', true, NULL, 'Ferdinand Highlander', 'qwe123', NULL, 'en', 'USER', NULL, 1481);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('2439dff0-2e34-48aa-8720-58831aeb626a', NULL, true, NULL, 'Digitális Védelem', NULL, NULL, NULL, 'GROUP', NULL, 1483);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('b4508151-3060-4021-b2f5-f615035687a3', NULL, true, NULL, 'Görögország', NULL, NULL, NULL, 'GROUP', NULL, 1484);
INSERT INTO accountentity (id, email, enabled, homeurl, name, password, phone, preferredlanguage, state, subscriptionid, node_id) VALUES ('6f000ca4-1af1-46e9-a0a9-90dc0edfe8ff', NULL, true, NULL, 'Magyarország', NULL, NULL, NULL, 'GROUP', NULL, 1485);


--
-- TOC entry 2028 (class 0 OID 25072)
-- Dependencies: 173
-- Data for Name: containercontententity; Type: TABLE DATA; Schema: public; Owner: topflavon
--

INSERT INTO containercontententity (id, title, contenttype, draft, embeddedfile, publicindicator, parent_id, node_id) VALUES ('aebdfaf1-9e9f-47aa-8010-4b9b2366f6d5', 'Products', 'linked', true, NULL, true, NULL, NULL);
INSERT INTO containercontententity (id, title, contenttype, draft, embeddedfile, publicindicator, parent_id, node_id) VALUES ('08ed5424-ea1d-42b8-9c27-3afec7a33f4a', 'FLAVON ACTIVE', 'assembled', false, NULL, true, 'aebdfaf1-9e9f-47aa-8010-4b9b2366f6d5', NULL);
INSERT INTO containercontententity (id, title, contenttype, draft, embeddedfile, publicindicator, parent_id, node_id) VALUES ('08df872a-ba84-42ba-8419-5158b0c6b8f1', 'FLAVON GREEN', 'assembled', false, NULL, true, 'aebdfaf1-9e9f-47aa-8010-4b9b2366f6d5', NULL);
INSERT INTO containercontententity (id, title, contenttype, draft, embeddedfile, publicindicator, parent_id, node_id) VALUES ('e7d015ed-a5a7-4592-b75d-a495083b370a', 'FLAVON MAX', 'assembled', false, NULL, true, 'aebdfaf1-9e9f-47aa-8010-4b9b2366f6d5', NULL);
INSERT INTO containercontententity (id, title, contenttype, draft, embeddedfile, publicindicator, parent_id, node_id) VALUES ('4df58466-0994-43da-a646-67559a413da5', 'FLAVON JOY', 'assembled', false, NULL, true, 'aebdfaf1-9e9f-47aa-8010-4b9b2366f6d5', NULL);
INSERT INTO containercontententity (id, title, contenttype, draft, embeddedfile, publicindicator, parent_id, node_id) VALUES ('b9a52fa9-534e-49cb-84bc-138738a8980c', 'Medical presentations', 'linked', true, NULL, true, NULL, NULL);
INSERT INTO containercontententity (id, title, contenttype, draft, embeddedfile, publicindicator, parent_id, node_id) VALUES ('7e85895e-1188-41fa-bbc2-817c587701c7', 'Lisa Ann Robinson', 'linked', true, NULL, true, 'b9a52fa9-534e-49cb-84bc-138738a8980c', NULL);
INSERT INTO containercontententity (id, title, contenttype, draft, embeddedfile, publicindicator, parent_id, node_id) VALUES ('5cc2d33b-e1e9-46a2-953a-78e60abad74b', 'Lisa Ann Robinson (Youtube)', 'linked', true, NULL, true, 'b9a52fa9-534e-49cb-84bc-138738a8980c', NULL);
INSERT INTO containercontententity (id, title, contenttype, draft, embeddedfile, publicindicator, parent_id, node_id) VALUES ('4f021261-3c6c-4bfb-93ea-cd8e010b956f', 'Dr. Leonard Ariel Lado', 'linked', true, NULL, true, 'b9a52fa9-534e-49cb-84bc-138738a8980c', NULL);
INSERT INTO containercontententity (id, title, contenttype, draft, embeddedfile, publicindicator, parent_id, node_id) VALUES ('04e99293-abb9-4fdf-9d68-c51d0f02a729', 'Dr. Leonard Ariel Lado (Youtube)', 'linked', true, NULL, true, 'b9a52fa9-534e-49cb-84bc-138738a8980c', NULL);
INSERT INTO containercontententity (id, title, contenttype, draft, embeddedfile, publicindicator, parent_id, node_id) VALUES ('9252c923-6f43-4528-89db-e9e444d09434', 'Dr. Brian Thornburg', 'linked', true, NULL, true, 'b9a52fa9-534e-49cb-84bc-138738a8980c', NULL);
INSERT INTO containercontententity (id, title, contenttype, draft, embeddedfile, publicindicator, parent_id, node_id) VALUES ('738d3469-9c68-418e-83b3-b629b0f4c0d4', 'Dr. Brian Thornburg (Youtube)', 'linked', true, NULL, true, 'b9a52fa9-534e-49cb-84bc-138738a8980c', NULL);
INSERT INTO containercontententity (id, title, contenttype, draft, embeddedfile, publicindicator, parent_id, node_id) VALUES ('2c6b4216-a49f-4ab7-8707-adb71b1d9103', 'Flavon', 'linked', true, NULL, true, 'aebdfaf1-9e9f-47aa-8010-4b9b2366f6d5', NULL);


--
-- TOC entry 2029 (class 0 OID 25080)
-- Dependencies: 174
-- Data for Name: contententity; Type: TABLE DATA; Schema: public; Owner: topflavon
--

INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', 'be03b708-a6f9-4b85-946e-a604695be57f', NULL, 'If we want to stay on top, to meet the expectations and face the challenges of the 21st century, to keep up with the accelerated pace of the world, we need to live a conscious and active life. This challenge affects all of us.', 12, 0, NULL, 0, 0, '08ed5424-ea1d-42b8-9c27-3afec7a33f4a');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', 'a8640fd1-0830-416b-b852-1b8ece6c28aa', NULL, 'Regular vegetable consumption is a significant and inevitable part of healthy nutrition. Vegetables supply our body with essential nutrients, fibre and vitamins and have a beneficial effect on our general well-being. Flavon Green is a revolutionary product, a true innovation for vegetable consumption that reshapes previous habits.', 12, 0, NULL, 0, 0, '08df872a-ba84-42ba-8419-5158b0c6b8f1');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', 'c5059509-e0de-42bb-be66-4534250f836f', NULL, 'The lifestyle of the 21st century including increasing daily stress, polluted environment, and nutrition deficiencies has extremely bad effects on our body and organism. Our body needs help to be able to win over these negative effects.', 12, 0, NULL, 0, 0, 'e7d015ed-a5a7-4592-b75d-a495083b370a');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', '68158468-2bfe-4397-91ec-88f0878a054f', NULL, 'Premium category Flavon Joy includes one of the ancient natural treasures, the fruit of cocoa tree due to which it has become a curio on the market of dietary supplements. Keeping the gel-consistency that is beneficial regarding bioavailability, we combined such fruit and vegetable ingredients and spices that have strong synergetic interactions, thus by enhancing each other’s effects. Therefore, it protects and pampers our body in all age group.', 12, 0, NULL, 0, 0, '4df58466-0994-43da-a646-67559a413da5');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('video', 'c12c44a0-6e5e-43b0-b77e-7dcd82015c26', NULL, '/backend/video/LisaAnnRobinson.mp4', 12, 600, NULL, 0, 1000, '7e85895e-1188-41fa-bbc2-817c587701c7');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('video', '2a8cda3f-ed99-41e3-8959-7661d938a81b', NULL, 'https://www.youtube.com/watch?v=MArvZyBm_bU', 12, 600, NULL, 3, 1000, '2c6b4216-a49f-4ab7-8707-adb71b1d9103');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('image', 'bf200e49-6af9-4d32-8fa5-cb96b2632fb4', NULL, '/data/flavon_active.png', 0, 300, NULL, 1, 300, '08ed5424-ea1d-42b8-9c27-3afec7a33f4a');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('image', '583b991f-8a02-4f68-b3a8-b9438f82ef11', NULL, '/data/flavon_green.png', 0, 300, NULL, 1, 300, '08df872a-ba84-42ba-8419-5158b0c6b8f1');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('image', 'cb29753f-f2ec-4baa-8a17-7b69ccb3726b', NULL, '/data/flavon_max.png', 0, 300, NULL, 1, 300, 'e7d015ed-a5a7-4592-b75d-a495083b370a');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('image', '7b22bafe-2465-4f6e-866d-5b40071f8159', NULL, '/data/flavon_green.png', 0, 300, NULL, 1, 300, '4df58466-0994-43da-a646-67559a413da5');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('video', 'afd22af0-d77e-459a-b668-1ee01928e55c', NULL, 'https://youtu.be/v7Osy8OpoOk', 12, 600, NULL, 0, 1000, '5cc2d33b-e1e9-46a2-953a-78e60abad74b');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', 'de7aa550-5fef-4e3a-aee7-fd65c4713c8b', 'OÉTI notification number: 10026/2011', '<ul><li><span>1</span> x <iron-icon icon="wonderjam-icons:spoon"></iron-icon>=<span>38094</span><label>T-ORAC</label></li><li><span>1</span> x <iron-icon icon="wonderjam-icons:can"></iron-icon>=<span>1523760</span><label>T-ORAC</label></li></ul>', 12, 0, NULL, 2, 0, '08ed5424-ea1d-42b8-9c27-3afec7a33f4a');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', '8e99840c-17a6-4126-8871-4bcb20168c78', 'OÉTI notification number: 10026/2011', '<ul><li><span>1</span> x <iron-icon icon="wonderjam-icons:spoon"></iron-icon>=<span>12072</span><label>T-ORAC</label></li><li><span>1</span> x <iron-icon icon="wonderjam-icons:can"></iron-icon>=<span>482880</span><label>T-ORAC</label></li></ul>', 12, 0, NULL, 2, 0, '08df872a-ba84-42ba-8419-5158b0c6b8f1');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', '1057001b-c4f5-46cf-ae2f-d2faa960606e', 'OÉTI notification number: 10026/2011', '<ul><li><span>1</span> x <iron-icon icon="wonderjam-icons:spoon"></iron-icon>=<span>10896</span><label>T-ORAC</label></li><li><span>1</span> x <iron-icon icon="wonderjam-icons:can"></iron-icon>=<span>435840</span><label>T-ORAC</label></li></ul>', 12, 0, NULL, 2, 0, 'e7d015ed-a5a7-4592-b75d-a495083b370a');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', '227f882b-c9ad-4134-b07d-078e998c5714', 'OÉTI notification number: 10026/2011', '<ul><li><span>1</span> x <iron-icon icon="wonderjam-icons:spoon"></iron-icon>=<span>60606</span><label>T-ORAC</label></li><li><span>1</span> x <iron-icon icon="wonderjam-icons:can"></iron-icon>=<span>2424240</span><label>T-ORAC</label></li></ul>', 12, 0, NULL, 2, 0, '4df58466-0994-43da-a646-67559a413da5');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('video', '9858036b-c3b2-4065-b883-4f2b51dfc2ee', NULL, '/backend/video/DrLeonardArielLado.mp4', 12, 600, NULL, 1, 1000, '4f021261-3c6c-4bfb-93ea-cd8e010b956f');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', '5c836770-d2c7-48a5-ab17-5d99e2a4d593', NULL, 'We often hear that this accelerated rhythm calls for some response. What can we do? We should not react to challenges by ruining our body! Both our body and soul need to stay healthy because they are indispensable for an active life.', 12, 0, NULL, 3, 0, '08ed5424-ea1d-42b8-9c27-3afec7a33f4a');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', 'dd1c74dd-feae-4b67-a881-b0f34186ee89', NULL, 'Flavon Green provides the positive physiological effects of vegetables in a complex way. The included vegetables help us maintain a balanced diet rich in vitamins, minerals, antioxidants and fibres.', 12, 0, NULL, 3, 0, '08df872a-ba84-42ba-8419-5158b0c6b8f1');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', '72b1e942-ac52-4c53-838b-4a98687cf577', NULL, 'High quality dietary supplements play a major role in one’s nutrition today. By constantly consuming them we might prevent the occurrence of deficiency symptoms caused by inadequate nutrition. Flavon broke with previous methods! Instead of pills and powder, it created a delicious and easily consumable gel-consistency form which makes it possible for its product to supply the human body with the necessary active substances.', 12, 0, NULL, 3, 0, 'e7d015ed-a5a7-4592-b75d-a495083b370a');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', '81806543-dbe9-4322-ae73-8a60da33271a', NULL, 'From children, through pregnant women and adults exposed to stronger oxidative stress, up to the older generation, anyone can consume it who craves for a tasty, sweet dietary supplement that has unique physiological effects at the same time.', 12, 0, NULL, 3, 0, '4df58466-0994-43da-a646-67559a413da5');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('video', '7189cf88-bc63-4b5f-b5a0-78ccdfed5ed8', NULL, 'https://youtu.be/8Fua1F_RYWM', 12, 600, NULL, 1, 1000, '04e99293-abb9-4fdf-9d68-c51d0f02a729');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', '03a37c4e-74ab-449d-914f-22b6e63a7f20', NULL, 'The fifth member of our product line is again the result of serious innovation. The product is a possible solution to the challenges of present times. It helps us every day to do our best even when we are under high pressure.', 12, 0, NULL, 4, 0, '08ed5424-ea1d-42b8-9c27-3afec7a33f4a');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', 'd9ad52bb-9f72-4e68-9aee-0b5aff8aa99d', NULL, 'Although numerous researches prove that regular vegetable consumption protects our health, only a few people consume the required amount day by day. Flavon Green can be the solution, because we can cover the significant part of the daily vegetable intake with a product constituted of only well-selected ingredients of high quality.', 12, 0, NULL, 4, 0, '08df872a-ba84-42ba-8419-5158b0c6b8f1');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', '4ecaf203-a1c9-4bda-bf89-6256087af94b', NULL, 'Flavon max was created for the people of the 21st century. It is a health-conscious product that supports life quality and its plant ingredients help the proper function of the antioxidant defence system and suitably support the function of the immune system.', 12, 0, NULL, 4, 0, 'e7d015ed-a5a7-4592-b75d-a495083b370a');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', '437d6edb-109c-4b05-832f-03559954de69', NULL, 'Flavon Joy delivers polyphenols to our body in a complex way to keep it healthy, and in case of health conscious consuming, not only does it protect the health of our organism, it may also boost our mental/psychical characteristics, learning skills and shock absorbing capacity.', 12, 0, NULL, 4, 0, '4df58466-0994-43da-a646-67559a413da5');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('video', '38fd287a-9ddd-4822-b559-27f7243c4288', NULL, '/backend/video/DrBrianThornburg.mp4', 12, 600, NULL, 2, 1000, '9252c923-6f43-4528-89db-e9e444d09434');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', 'd02b8a5c-b952-4054-9abd-a02aa4658092', 'The consumption of Flavon Active is recommended for everyone', '<ul><li>who has an active, dynamic, sporty lifestyle</li><li>who craves for healthy stimulation</li><li>who wants to successfully meet the challenges of the 21st century</li><li>who would like to enjoy the benefits of today’s super fruits</li></ul>', 12, 0, NULL, 5, 0, '08ed5424-ea1d-42b8-9c27-3afec7a33f4a');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', '3600651f-1437-4961-8be8-3b357c16dabb', 'The consumption of Flavon Green is recommended for adults', '<ul><li>who cannot ensure the intake of sufficient vegetables</li><li>who considers it important to continuously take in vitamins and minerals from a pure source</li><li>who would like to consume vegetables in a new form they have not tried before</li><li>who is a conscientious consumer and would like to complete a modern diet</li></ul>', 12, 0, NULL, 5, 0, '08df872a-ba84-42ba-8419-5158b0c6b8f1');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', '58d00c23-54b3-442c-a8d4-5d3bf7f6bba9', 'The consumption of Flavon Max is recommended for everybody', '<ul><li>who does not consume enough fruits</li><li>who wants to complement their current one-sided nutrition</li><li>who takes good care of the their own and their family’s health</li><li>who wants to enjoy and take advantage of an innovative product</li></ul>', 12, 0, NULL, 5, 0, 'e7d015ed-a5a7-4592-b75d-a495083b370a');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('text', 'ad254110-ff66-49e1-ade2-03de81f4aa5f', 'The consumption of Flavon Joy is recommended for everyone', '<ul><li>who is exposed to constant stress, does sport regularly</li><li>who needs more mental energy</li><li>who would like to satisfy their desire for sweets in a healthy way</li><li>who wants to make a conscious choice of cocoa bean, ancient spices and the synergy of super fruits and vegetables</li></ul>', 12, 0, NULL, 5, 0, '4df58466-0994-43da-a646-67559a413da5');
INSERT INTO contententity (resource, id, title, content, fontsize, height, justification, orderindex, width, parent_id) VALUES ('video', 'b61b2276-b2a0-4f99-84f5-319e576bed6d', NULL, 'https://youtu.be/DyS2jC0DLZc', 12, 600, NULL, 2, 1000, '738d3469-9c68-418e-83b3-b629b0f4c0d4');


--
-- TOC entry 2039 (class 0 OID 0)
-- Dependencies: 171
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: topflavon
--

SELECT pg_catalog.setval('hibernate_sequence', 1548, true);


--
-- TOC entry 2030 (class 0 OID 25088)
-- Dependencies: 175
-- Data for Name: localeentity; Type: TABLE DATA; Schema: public; Owner: topflavon
--

INSERT INTO localeentity (languagecode, displayname) VALUES ('en', 'English');
INSERT INTO localeentity (languagecode, displayname) VALUES ('po', 'Polskie');
INSERT INTO localeentity (languagecode, displayname) VALUES ('hu', 'Magyar');


--
-- TOC entry 2031 (class 0 OID 25093)
-- Dependencies: 176
-- Data for Name: mailboxentity; Type: TABLE DATA; Schema: public; Owner: topflavon
--

INSERT INTO mailboxentity (id, owner_id) VALUES (1472, 1471);
INSERT INTO mailboxentity (id, owner_id) VALUES (1474, 1473);
INSERT INTO mailboxentity (id, owner_id) VALUES (1476, 1475);
INSERT INTO mailboxentity (id, owner_id) VALUES (1478, 1477);
INSERT INTO mailboxentity (id, owner_id) VALUES (1480, 1479);
INSERT INTO mailboxentity (id, owner_id) VALUES (1482, 1481);
INSERT INTO mailboxentity (id, owner_id) VALUES (1487, 1486);
INSERT INTO mailboxentity (id, owner_id) VALUES (1510, 1509);
INSERT INTO mailboxentity (id, owner_id) VALUES (1534, 1533);
INSERT INTO mailboxentity (id, owner_id) VALUES (1544, 1543);
INSERT INTO mailboxentity (id, owner_id) VALUES (1546, 1545);
INSERT INTO mailboxentity (id, owner_id) VALUES (1548, 1547);


--
-- TOC entry 2032 (class 0 OID 25098)
-- Dependencies: 177
-- Data for Name: messageentity; Type: TABLE DATA; Schema: public; Owner: topflavon
--

INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1488, false, false, 'asdasdsadasd', '2016-10-15', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1489, false, false, 'Szia', '2016-10-15', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1490, false, false, 'Ezt vetted?', '2016-10-15', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1491, false, false, 'Ezt vetted? hh', '2016-10-15', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1492, false, false, 'Hallo', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1493, false, false, 'Hallosss', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1494, false, false, 'Hallossskkkkk', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1495, false, false, 'Hallossskkkkkjhjhjhjhjhjh', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1496, false, false, 'Hall', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1497, false, false, 'Hall', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1498, false, false, 'Hall', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1499, false, false, 'asdadada
aldasd
asdaskdléakdlé', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1500, false, false, 'Nézd meg a https://www.topflavon.net/article-view/ROOT cikket.', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1501, false, false, 'Nézd meg a https://www.topflavon.net/article-view/ROOT cikket.', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1502, false, false, 'asdasdasd', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1504, false, false, 'csoki', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1506, false, false, 'asdasdasd
asdasda
asdsasdasd', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1507, false, false, 'asdsfsdf', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1508, false, false, 'új özenet', '2016-10-16', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1503, true, false, 'Szevasz', '2016-10-16', NULL, 1472, 1486);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1505, true, false, 'Vettem', '2016-10-16', NULL, 1472, 1486);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1517, true, false, 'Hhf', '2016-10-17', NULL, 1472, 1486);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1518, true, false, 'Csak annyit alartam mondani', '2016-10-17', NULL, 1472, 1486);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1519, false, false, 'hghghg', '2016-10-17', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1520, true, false, 'Itt vag?', '2016-10-17', NULL, 1472, 1486);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1521, false, false, 'igen', '2016-10-17', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1522, false, false, 'igen', '2016-10-17', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1523, false, false, 'igenssss', '2016-10-17', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1524, false, false, 'igenssss vagy nem', '2016-10-17', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1525, true, false, 'Csoki', '2016-10-17', NULL, 1472, 1486);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1526, true, false, 'Csoki hfhfjf', '2016-10-17', NULL, 1472, 1486);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1527, false, false, 'igenssss vagy nem', '2016-10-17', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1528, true, false, 'Csooooo', '2016-10-17', NULL, 1472, 1486);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1529, false, false, 'csoki', '2016-10-17', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1530, false, false, 'csokiasd', '2016-10-17', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1531, false, false, 'csokiasd jj', '2016-10-17', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1532, false, false, 'csokiasd jj kjkjkjkj', '2016-10-17', NULL, 1487, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1536, false, false, 'Csoki', '2016-10-17', NULL, 1534, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1535, true, false, 'Buco', '2016-10-17', NULL, 1472, 1533);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1537, true, false, 'Buco', '2016-10-17', NULL, 1472, 1533);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1538, true, false, 'Buco2', '2016-10-17', NULL, 1472, 1533);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1539, true, false, 'Buco3', '2016-10-17', NULL, 1472, 1533);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1540, true, false, 'Buco4', '2016-10-17', NULL, 1472, 1533);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1541, true, false, 'Buco5', '2016-10-17', NULL, 1472, 1533);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1511, true, false, 'ok', '2016-10-16', NULL, 1478, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1512, true, false, 'ok', '2016-10-16', NULL, 1476, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1513, true, false, 'ok222', '2016-10-16', NULL, 1478, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1514, true, false, 'ok222', '2016-10-16', NULL, 1476, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1515, true, false, 'ok222sss', '2016-10-16', NULL, 1478, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1516, true, false, 'ok222sss', '2016-10-16', NULL, 1476, 1471);
INSERT INTO messageentity (id, notified, read, text, ts, content_id, mailbox_id, sender_id) VALUES (1542, true, false, 'Cucli', '2016-10-19', NULL, 1472, 1533);


--
-- TOC entry 2034 (class 0 OID 25111)
-- Dependencies: 179
-- Data for Name: resourceentity; Type: TABLE DATA; Schema: public; Owner: topflavon
--



-- Completed on 2016-11-08 13:09:20 CET

--
-- PostgreSQL database dump complete
--

