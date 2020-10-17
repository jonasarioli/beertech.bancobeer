INSERT INTO conta(id, hash, saldo, nome, email, cnpj, senha) 
VALUES (1, '1', 0.00, 'jonas', 'jonas@email.com','cnpj', '$2y$12$/hEKlemmbuSRl9frI447h.71w/A.vC/cAhMssDItGy9sS5KGOPh7C');

INSERT INTO profile(id, name) VALUES (1, 'COMUM');
INSERT INTO profile(id, name) VALUES (2, 'ADMIN');

INSERT INTO conta_profiles(my_sql_conta_id, profiles_id) VALUES (1, 2);
