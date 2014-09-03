CREATE TABLE IF NOT EXISTS `conversion_profiles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `label` VARCHAR(50) NOT NULL,
  `file_suffix` VARCHAR(50),
  `format` int(1) NOT NULL,
  `video_codec` int(1) NOT NULL,
  `audio_codec` int(1) NOT NULL,
  `video_bitrate` int,
  `audio_bitrate` int,
  `video_options` VARCHAR(250),
  `audio_options` VARCHAR(250),
  PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `conversion_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `profile_id` bigint NOT NULL,
  `video_id` bigint NOT NULL,
  `priority` int(1) NOT NULL,
  `media_type` int(1) NOT NULL,
  `source` VARCHAR(250),
  `destination` VARCHAR(250),
  `date_created` DATETIME,
  `status` int(1),
  PRIMARY KEY(`id`),
  CONSTRAINT fk_conversionProfile FOREIGN KEY (`profile_id`)
  REFERENCES conversion_profiles(`id`),
  CONSTRAINT fk_video FOREIGN KEY (`video_id`)
  REFERENCES videos(`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;