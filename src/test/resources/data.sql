INSERT INTO user_role(role_id, role_name, description) VALUES ('2e72ed53-f5e2-4f7a-bd86-8aadcadeb4eb','admin', 'Base role for admin') ON CONFLICT DO NOTHING;
INSERT INTO user_permission(permission_id, uri, permission_key, permission_method) VALUES ('ccc7ff73-1989-413a-ab52-9bec7a049e33', '/users', 'create.user', 'POST') ON CONFLICT DO NOTHING;
INSERT INTO role_permission(role_id, permission_id) VALUES ('2e72ed53-f5e2-4f7a-bd86-8aadcadeb4eb', 'ccc7ff73-1989-413a-ab52-9bec7a049e33') ON CONFLICT DO NOTHING;
INSERT INTO user(user_id,email,phoneNumber,address,password,role) VALUES ('2e72ed53-f5e2-4f7a-bd86-8aadcadeb4eb','admin@localhost','123456789','admin address','admin', '2e72ed53-f5e2-4f7a-bd86-8aadcadeb4eb') ON CONFLICT DO NOTHING;
INSERT INTO order (order_id, total,status,user_id) VALUES ('5631cbd3-cf53-415f-bd06-4e995ee3c322',2, 'CREATED', '2e72ed53-f5e2-4f7a-bd86-8aadcadeb4eb') ON CONFLICT DO NOTHING;