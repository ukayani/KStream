
CREATE TABLE IF NOT EXISTS `series` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  `tvdb_id` varchar(20) NOT NULL,
  `banner` varchar(250),
  `overview` TEXT,
  `imdb_id` VARCHAR(20),
  `content_rating` VARCHAR(50),
  `network` VARCHAR(50),
  `imdb_rating` DECIMAL(10,2),
  `rating` DECIMAL(10,2),
  `status` VARCHAR(20),
  `priority` int(1) NOT NULL,
  `profile_id` bigint,
  `first_aired` DATE,
  `air_day` VARCHAR(15),
  `air_time` VARCHAR(10),
  `runtime` VARCHAR(20),
  PRIMARY KEY (`id`),
  UNIQUE (`imdb_id`),
  UNIQUE (`tvdb_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `genre` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `series_genres` (
  `series_id` bigint NOT NULL,
  `genre_id` bigint NOT NULL,
  KEY `series_id` (`series_id`),
  KEY `genre_id` (`genre_id`),
  UNIQUE (`series_id`, `genre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `episodes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `episode_number` int(4) NOT NULL,
  `season_number` int(4) NOT NULL,
  `first_aired` DATE,
  `last_updated` DATE,
  `overview` TEXT,
  `rating` DECIMAL(10,2),
  `total_video_count` int(2) NOT NULL,
  `visible_video_count` int(2) NOT NULL,
  `status` int(1),
  `series_tvdb_id` VARCHAR(20),
  `series_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_SeriesEp FOREIGN KEY (`series_id`)
  REFERENCES series(`id`)
  ON UPDATE CASCADE
  ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;