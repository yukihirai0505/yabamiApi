CREATE TABLE `cmc_data` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `symbol` varchar(50) NOT NULL,
  `rank` varchar(100) NOT NULL,
  `price_btc` varchar(500) DEFAULT NULL,
  `available_supply` varchar(500) DEFAULT NULL,
  `total_supply` varchar(500) DEFAULT NULL,
  `max_supply` varchar(500) DEFAULT NULL,
  `percent_change_1h` varchar(45) DEFAULT NULL,
  `percent_change_24h` varchar(45) DEFAULT NULL,
  `percent_change_7d` varchar(45) DEFAULT NULL,
  `last_updated` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='CoinMarketCapData';

ALTER TABLE `ubiqannbot`.`cmc_data`
ADD INDEX `symbol_idx` (`symbol` ASC);
