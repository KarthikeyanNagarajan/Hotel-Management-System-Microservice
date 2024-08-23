Insert into auth_table (id, user_name, user_pwd, enabled) VALUES (1, 'Karthik', '$2a$10$9Tl4WG1U4gib5SIf4jiyxOX4EfkgN7wf7iQhzmfpbR9FVP1yNUOvm', true);
Insert into auth_table (id, user_name, user_pwd, enabled) VALUES (2, 'Karthik123', '$2a$10$9Tl4WG1U4gib5SIf4jiyxOX4EfkgN7wf7iQhzmfpbR9FVP1yNUOvm', true);

Insert into role_table (id, role) VALUES (1, 'USER');
Insert into role_table (id, role) VALUES (2, 'ADMIN');

Insert into user_roles_table (user_id, role_id) VALUES (1, 2);
Insert into user_roles_table (user_id, role_id) VALUES (2, 1);