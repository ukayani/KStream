INSERT INTO kstream.roles (name) VALUES ('admin');

SET @adminRoleId = LAST_INSERT_ID();

INSERT INTO kstream.roles (name) VALUES ('user');

SET @userRoleId = LAST_INSERT_ID();

INSERT INTO kstream.users (username, password, email, disabled, last_login) VALUES ('kayani', '$2a$10$QVLzEvKS1AGoTmM4sl.wbe0hki9MOah6FEU5/T8WqUy6CLVk3qG7S', 'kayani.u@gmail.com', 0, NOW());

SET @userId = LAST_INSERT_ID();

INSERT INTO kstream.users (username, password, email, disabled, last_login) VALUES ('admin', '$2a$10$uPTCvDJ/9IXV2QT01/hgNuQaW3n3L9qJtaCLFYV4EivJPpyHRT.vm', 'saltan23@gmail.com', 0, NOW());

SET @adminId = LAST_INSERT_ID();

INSERT INTO kstream.user_roles (user_id, role_id) VALUES (@userId, @userRoleId), (@adminId, @adminRoleId);
