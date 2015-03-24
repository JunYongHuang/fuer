-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               5.6.23-log - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2015-03-21 21:22:39
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for go
DROP DATABASE IF EXISTS `go`;
CREATE DATABASE IF NOT EXISTS `go` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `go`;


-- Dumping structure for table go.article
DROP TABLE IF EXISTS `article`;
CREATE TABLE IF NOT EXISTS `article` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT "0",
  `source` varchar(50) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `keywords` varchar(50) DEFAULT NULL,
  `summary` varchar(50) DEFAULT NULL,
  `content` text,
  `click` int(11) DEFAULT "0",
  `top` bit(1) DEFAULT false,
  `createDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Dumping structure for table go.article
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
  `fee` int(11) DEFAULT "0",
  `status` int(11) DEFAULT "0",
  `chargeDate` date DEFAULT NULL,
  `UserIP` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Dumping structure for table go.article
DROP TABLE IF EXISTS `gameRole`;
CREATE TABLE IF NOT EXISTS `gameRole` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `serverCode` varchar(50) DEFAULT NULL,
  `managerName` varchar(50) DEFAULT NULL,
  `creationDate` date DEFAULT NULL,
  `loginDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Dumping structure for table go.article
DROP TABLE IF EXISTS `gameServer`;
CREATE TABLE IF NOT EXISTS `gameServer` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `port` int(11) DEFAULT "0",
  `maxCount` int(11) DEFAULT "0",
  `currentCount` int(11) DEFAULT "0",
  `createDate` date DEFAULT NULL,
  `type` int(11) DEFAULT "0",
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Dumping structure for table go.article
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


-- Dumping structure for table go.article
DROP TABLE IF EXISTS `spreadUser`;
CREATE TABLE IF NOT EXISTS `spreadUser` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `userId` bigint(10) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `addressCode` varchar(50) DEFAULT NULL,
  `clickCount` int(11) DEFAULT "0",
  `date` date DEFAULT NULL,
  `userCount` int(11) DEFAULT "0",
  `chargeAmount` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Dumping structure for table go.article
DROP TABLE IF EXISTS `thirdUser`;
CREATE TABLE IF NOT EXISTS `thirdUser` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `loginType` int(11) DEFAULT "0",
  `openId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Dumping structure for table go.article
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `salt` varchar(50) DEFAULT NULL,
  `role` int(11) DEFAULT "0",
  `name` varchar(50) DEFAULT NULL,
  `pic` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phoneNumber` varchar(50) DEFAULT NULL,
  `idcard` varchar(50) DEFAULT NULL,
  `sex` int(11) DEFAULT '0',
  `enabled` bit(1) DEFAULT true,
  `accountExpired` bit(1) DEFAULT false,
  `accountLocked` bit(1) DEFAULT false,
  `credentialsExpired` bit(1) DEFAULT false,
  `regdate` bigint(10) DEFAULT NULL,
  `lastLoginTime` bigint(10) DEFAULT NULL,
  `loginIp` varchar(50) DEFAULT NULL,
  `ufrom` bigint(10) DEFAULT NULL,
  `sourceFrom` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Dumping structure for table go.article
DROP TABLE IF EXISTS `voteItem`;
CREATE TABLE IF NOT EXISTS `voteItem` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `img` varchar(50) DEFAULT NULL,
  `voteTotal` int(11) DEFAULT "0",
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Dumping structure for table go.article
DROP TABLE IF EXISTS `voteReg`;
CREATE TABLE IF NOT EXISTS `voteReg` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `uname` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `addr` varchar(50) DEFAULT NULL,
  `regdate` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Data exporting was unselected.
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
