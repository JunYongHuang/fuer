-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               5.6.23-log - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2015-03-24 01:58:17
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for fuer
DROP DATABASE IF EXISTS `fuer`;
CREATE DATABASE IF NOT EXISTS `fuer` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fuer`;


-- Dumping structure for table fuer.article
DROP TABLE IF EXISTS `article`;
CREATE TABLE IF NOT EXISTS `article` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `source` varchar(50) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `keywords` varchar(50) DEFAULT NULL,
  `summary` varchar(50) DEFAULT NULL,
  `content` text,
  `click` int(11) DEFAULT '0',
  `top` bit(1) DEFAULT b'0',
  `createDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table fuer.article: ~0 rows (approximately)
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` (`id`, `title`, `type`, `source`, `author`, `keywords`, `summary`, `content`, `click`, `top`, `createDate`) VALUES
	(1, '新网站开始啦', 4, '本站', '管理员', '新网站', '哈哈', '<p>欢迎光临，新网站开发中。。。</p>', 2, b'00000000', '2015-03-22'),
	(2, '公告111', 4, '本站', '管理员', 'hell', '轻轻巧巧', '<p style="text-align:center;">游戏公告</p><p style="text-align:center;"><br /></p><p style="text-align:left;"> &nbsp; &nbsp;游戏公告按时发生的发生的发生的份上<img src="http://img.baidu.com/hi/jx2/j_0007.gif" /></p>', 2, b'00000000', '2015-03-23');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;


-- Dumping structure for table fuer.charge
DROP TABLE IF EXISTS `charge`;
CREATE TABLE IF NOT EXISTS `charge` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `orderId` bigint(10) DEFAULT NULL,
  `dealId` varchar(50) DEFAULT NULL,
  `chargeAmount` bigint(10) DEFAULT NULL,
  `payAmount` bigint(10) DEFAULT NULL,
  `goldMoney` bigint(10) DEFAULT NULL,
  `presentGold` bigint(10) DEFAULT NULL,
  `presentName` varchar(50) DEFAULT NULL,
  `otherChargeAmount` bigint(10) DEFAULT NULL,
  `payType` varchar(50) DEFAULT NULL,
  `bankId` varchar(50) DEFAULT NULL,
  `bankDealId` varchar(50) DEFAULT NULL,
  `errCode` varchar(50) DEFAULT NULL,
  `dealTime` varchar(50) DEFAULT NULL,
  `user` varchar(50) DEFAULT NULL,
  `confirmUser` varchar(50) DEFAULT NULL,
  `managerName` varchar(50) DEFAULT NULL,
  `gameServer` varchar(50) DEFAULT NULL,
  `payUser` varchar(50) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `payDate` date DEFAULT NULL,
  `fee` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `chargeDate` date DEFAULT NULL,
  `UserIP` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table fuer.charge: ~0 rows (approximately)
/*!40000 ALTER TABLE `charge` DISABLE KEYS */;
/*!40000 ALTER TABLE `charge` ENABLE KEYS */;


-- Dumping structure for table fuer.gamerole
DROP TABLE IF EXISTS `gamerole`;
CREATE TABLE IF NOT EXISTS `gamerole` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `serverCode` varchar(50) DEFAULT NULL,
  `managerName` varchar(50) DEFAULT NULL,
  `creationDate` date DEFAULT NULL,
  `loginDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table fuer.gamerole: ~0 rows (approximately)
/*!40000 ALTER TABLE `gamerole` DISABLE KEYS */;
/*!40000 ALTER TABLE `gamerole` ENABLE KEYS */;


-- Dumping structure for table fuer.gameserver
DROP TABLE IF EXISTS `gameserver`;
CREATE TABLE IF NOT EXISTS `gameserver` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `port` int(11) DEFAULT '0',
  `maxCount` int(11) DEFAULT '0',
  `currentCount` int(11) DEFAULT '0',
  `createDate` date DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table fuer.gameserver: ~0 rows (approximately)
/*!40000 ALTER TABLE `gameserver` DISABLE KEYS */;
/*!40000 ALTER TABLE `gameserver` ENABLE KEYS */;


-- Dumping structure for table fuer.platform
DROP TABLE IF EXISTS `platform`;
CREATE TABLE IF NOT EXISTS `platform` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `loginUrl` varchar(50) DEFAULT NULL,
  `chargeurl` varchar(50) DEFAULT NULL,
  `loginKey` varchar(50) DEFAULT NULL,
  `chargeKey` varchar(50) DEFAULT NULL,
  `whiteIP` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table fuer.platform: ~0 rows (approximately)
/*!40000 ALTER TABLE `platform` DISABLE KEYS */;
/*!40000 ALTER TABLE `platform` ENABLE KEYS */;


-- Dumping structure for table fuer.spreaduser
DROP TABLE IF EXISTS `spreaduser`;
CREATE TABLE IF NOT EXISTS `spreaduser` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `userId` bigint(10) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `addressCode` varchar(50) DEFAULT NULL,
  `clickCount` int(11) DEFAULT '0',
  `date` date DEFAULT NULL,
  `userCount` int(11) DEFAULT '0',
  `chargeAmount` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table fuer.spreaduser: ~0 rows (approximately)
/*!40000 ALTER TABLE `spreaduser` DISABLE KEYS */;
/*!40000 ALTER TABLE `spreaduser` ENABLE KEYS */;


-- Dumping structure for table fuer.thirduser
DROP TABLE IF EXISTS `thirduser`;
CREATE TABLE IF NOT EXISTS `thirduser` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `loginType` int(11) DEFAULT '0',
  `openId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table fuer.thirduser: ~0 rows (approximately)
/*!40000 ALTER TABLE `thirduser` DISABLE KEYS */;
/*!40000 ALTER TABLE `thirduser` ENABLE KEYS */;


-- Dumping structure for table fuer.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `salt` varchar(50) DEFAULT NULL,
  `role` int(11) DEFAULT '0',
  `name` varchar(50) DEFAULT NULL,
  `pic` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phoneNumber` varchar(50) DEFAULT NULL,
  `idcard` varchar(50) DEFAULT NULL,
  `sex` int(11) DEFAULT '0',
  `enabled` bit(1) DEFAULT b'1',
  `accountExpired` bit(1) DEFAULT b'0',
  `accountLocked` bit(1) DEFAULT b'0',
  `credentialsExpired` bit(1) DEFAULT b'0',
  `regdate` bigint(10) DEFAULT NULL,
  `lastLoginTime` bigint(10) DEFAULT NULL,
  `loginIp` varchar(50) DEFAULT NULL,
  `ufrom` bigint(10) DEFAULT NULL,
  `sourceFrom` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table fuer.user: ~0 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `username`, `password`, `salt`, `role`, `name`, `pic`, `email`, `phoneNumber`, `idcard`, `sex`, `enabled`, `accountExpired`, `accountLocked`, `credentialsExpired`, `regdate`, `lastLoginTime`, `loginIp`, `ufrom`, `sourceFrom`) VALUES
	(1, 'admin', '14515ddcd02de2ed210fa291bee159cc', 'UI6UmM', 1, NULL, NULL, 'qq@11.com', NULL, NULL, 0, b'10000000', b'00000000', b'00000000', b'00000000', 1427007010, 1427131301, '0:0:0:0:0:0:0:1', 0, '');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table fuer.voteitem
DROP TABLE IF EXISTS `voteitem`;
CREATE TABLE IF NOT EXISTS `voteitem` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `img` varchar(50) DEFAULT NULL,
  `voteTotal` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table fuer.voteitem: ~0 rows (approximately)
/*!40000 ALTER TABLE `voteitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `voteitem` ENABLE KEYS */;


-- Dumping structure for table fuer.votereg
DROP TABLE IF EXISTS `votereg`;
CREATE TABLE IF NOT EXISTS `votereg` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `uname` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `addr` varchar(50) DEFAULT NULL,
  `regdate` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table fuer.votereg: ~0 rows (approximately)
/*!40000 ALTER TABLE `votereg` DISABLE KEYS */;
/*!40000 ALTER TABLE `votereg` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
