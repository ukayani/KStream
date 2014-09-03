CREATE TABLE IF NOT EXISTS `settings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `profile_id` bigint NOT NULL,
  `conversion_mode` int(1) NOT NULL,
  PRIMARY KEY(`id`),
  CONSTRAINT fk_convProfile FOREIGN KEY (`profile_id`)
  REFERENCES conversion_profiles(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;