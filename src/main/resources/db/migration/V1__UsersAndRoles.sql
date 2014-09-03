DROP TABLE IF EXISTS kstream.roles;
DROP TABLE IF EXISTS kstream.users;
DROP TABLE IF EXISTS kstream.user_roles;

CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` char(60) NOT NULL,
  `email` varchar(40) NOT NULL,
  `disabled` BIT NOT NULL,
  `last_login` DATETIME,
  PRIMARY KEY (`id`),
  UNIQUE (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `user` (`user_id`),
  KEY `role` (`role_id`),
  UNIQUE (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

