-- Insert roles
INSERT INTO role (role_id, role_name) VALUES (1, 'Admin'), (2, 'Manager'), (3, 'Editor'), (4, 'Viewer')
ON DUPLICATE KEY UPDATE role_name = VALUES(role_name);

-- Insert permissions
INSERT INTO permission (permission_id, permission_name) VALUES 
(1, 'CREATE_DOCUMENT'), 
(2, 'EDIT_DOCUMENT'), 
(3, 'VIEW_DOCUMENT'),
(4, 'MANAGE_USERS')
ON DUPLICATE KEY UPDATE permission_name = VALUES(permission_name);
