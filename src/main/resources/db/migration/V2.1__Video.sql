CREATE TABLE IF NOT EXISTS `videos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `video_type` int(1) NOT NULL,
  `url` VARCHAR(250) NOT NULL,
  `width` int(4),
  `height` int(4),
  `video_size` bigint,
  `duration` bigint,
  `video_codec` int(1),
  `audio_codec` int(1),
  `vbitrate` int,
  `abitrate` int,
  `profile_id` bigint,
  `status` int(1),
  PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `episode_videos` (
  `episode_id` bigint NOT NULL,
  `video_id` bigint NOT NULL,
  KEY `episode_id` (`episode_id`),
  KEY `video_id` (`video_id`),
  UNIQUE (`episode_id`, `video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;