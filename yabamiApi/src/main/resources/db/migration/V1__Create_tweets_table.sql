CREATE TABLE IF NOT EXISTS `ubiqannbot`.`tweets` (
  `id` bigint(20) NOT NULL,
  `text` varchar(500) CHARACTER SET utf8mb4 NOT NULL COMMENT 'ツイッターテキスト',
  `create_at` datetime NOT NULL COMMENT '投稿日時',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='ツイート';