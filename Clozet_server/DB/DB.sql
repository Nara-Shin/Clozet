-- phpMyAdmin SQL Dump
-- version 2.11.11
-- http://www.phpmyadmin.net
--
-- 호스트: h6.woobi.co.kr
-- 처리한 시간: 16-01-06 17:40 
-- 서버 버전: 5.1.73
-- PHP 버전: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 데이터베이스: `godeung`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `ChangeRequest`
--

CREATE TABLE IF NOT EXISTS `ChangeRequest` (
  `ReqCode` int(6) NOT NULL DEFAULT '0' COMMENT 'ReqCode',
  `ReqMember` int(6) DEFAULT NULL COMMENT 'ReqMember',
  `RequestPrdOption` int(6) DEFAULT NULL COMMENT 'RequestPrdOption',
  `ReqResult` int(3) DEFAULT NULL COMMENT 'ReqResult',
  `ReqClerkCode` int(6) DEFAULT NULL COMMENT 'ReqClerkCode',
  `LimitTime` datetime DEFAULT NULL COMMENT 'LimitTime',
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`ReqCode`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='ChangeRequest';

--
-- 테이블의 덤프 데이터 `ChangeRequest`
--


-- --------------------------------------------------------

--
-- 테이블 구조 `ClerkInfo`
--

CREATE TABLE IF NOT EXISTS `ClerkInfo` (
  `ClerkCode` int(6) NOT NULL DEFAULT '0' COMMENT 'ClerkCode',
  `ClerkName` int(6) DEFAULT NULL COMMENT 'ClerkName',
  `WorkingShop` int(6) DEFAULT NULL COMMENT 'WorkingShop',
  `GcmRegId` varchar(200) DEFAULT NULL COMMENT 'GcmRegId',
  `AndroidId` varchar(200) DEFAULT NULL COMMENT 'AndroidId',
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`ClerkCode`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='ClerkInfo';

--
-- 테이블의 덤프 데이터 `ClerkInfo`
--

INSERT INTO `ClerkInfo` (`ClerkCode`, `ClerkName`, `WorkingShop`, `GcmRegId`, `AndroidId`, `RegDate`, `ModDate`) VALUES
(10000, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 테이블 구조 `CommonCode`
--

CREATE TABLE IF NOT EXISTS `CommonCode` (
  `Code` int(3) NOT NULL DEFAULT '0' COMMENT 'Code',
  `CodeCategory` varchar(50) DEFAULT NULL COMMENT 'CodeCategory',
  `CodeName` varchar(200) DEFAULT NULL COMMENT 'CodeName',
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`Code`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='CommonCode';

--
-- 테이블의 덤프 데이터 `CommonCode`
--

INSERT INTO `CommonCode` (`Code`, `CodeCategory`, `CodeName`, `RegDate`, `ModDate`) VALUES
(201, '교환', '요청', '2016-01-06 12:56:28', '2016-01-06 12:56:28'),
(202, '교환', '시간초과', '2016-01-06 12:56:28', '2016-01-06 12:56:28'),
(203, '교환', '취소', '2016-01-06 12:56:28', '2016-01-06 12:56:28'),
(204, '교환', '거절', '2016-01-06 12:56:28', '2016-01-06 12:56:28'),
(205, '교환', '수락', '2016-01-06 12:56:28', '2016-01-06 12:56:28');

-- --------------------------------------------------------

--
-- 테이블 구조 `MemberFitting`
--

CREATE TABLE IF NOT EXISTS `MemberFitting` (
  `MemberCode` int(6) NOT NULL DEFAULT '0' COMMENT 'MemberCode',
  `FittingType` int(3) DEFAULT NULL COMMENT 'FittingType',
  `FittingPhoto` varchar(200) DEFAULT NULL COMMENT 'FittingPhoto',
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`MemberCode`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='MemberFitting';

--
-- 테이블의 덤프 데이터 `MemberFitting`
--


-- --------------------------------------------------------

--
-- 테이블 구조 `MemberInfo`
--

CREATE TABLE IF NOT EXISTS `MemberInfo` (
  `MemberCode` int(6) NOT NULL DEFAULT '0' COMMENT 'MemberCode',
  `MemberName` varchar(50) DEFAULT NULL COMMENT 'MemberName',
  `MemberEmail` varchar(200) DEFAULT NULL COMMENT 'MemberEmail',
  `GcmRegId` varchar(200) DEFAULT NULL COMMENT 'GcmRegId',
  `AndroidId` varchar(200) DEFAULT NULL COMMENT 'AndroidId',
  `Sex` varchar(1) DEFAULT NULL COMMENT 'Sex',
  `Height` int(3) DEFAULT NULL COMMENT 'Height',
  `Age` int(3) DEFAULT NULL COMMENT 'Age',
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`MemberCode`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='MemberInfo';

--
-- 테이블의 덤프 데이터 `MemberInfo`
--

INSERT INTO `MemberInfo` (`MemberCode`, `MemberName`, `MemberEmail`, `GcmRegId`, `AndroidId`, `Sex`, `Height`, `Age`, `RegDate`, `ModDate`) VALUES
(100000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(100001, '', '', '1234', '1234', '', 0, 0, '2016-01-06 15:03:10', '2016-01-06 15:03:10'),
(100002, '', '', '', '', '', 0, 0, '2016-01-06 15:10:02', '2016-01-06 15:10:02');

-- --------------------------------------------------------

--
-- 테이블 구조 `MemberReservation`
--

CREATE TABLE IF NOT EXISTS `MemberReservation` (
  `ResvCode` int(6) NOT NULL DEFAULT '0' COMMENT 'ResvCode',
  `PrdCode` int(6) DEFAULT NULL COMMENT 'PrdCode',
  `ResvDate` datetime DEFAULT NULL COMMENT 'ResvDate',
  `ResvResult` int(3) DEFAULT NULL COMMENT 'ResvResult',
  `ResvMember` int(6) DEFAULT NULL COMMENT 'ResvMember',
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`ResvCode`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='MemberReservation';

--
-- 테이블의 덤프 데이터 `MemberReservation`
--


-- --------------------------------------------------------

--
-- 테이블 구조 `ProductInfo`
--

CREATE TABLE IF NOT EXISTS `ProductInfo` (
  `PrdCode` int(6) NOT NULL DEFAULT '0' COMMENT 'PrdCode',
  `PrdName` varchar(300) DEFAULT NULL COMMENT 'PrdName',
  `PrdPrice` int(10) DEFAULT NULL COMMENT 'PrdPrice',
  `ShopCode` int(6) DEFAULT NULL COMMENT 'ShopCode',
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`PrdCode`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='ProductInfo';

--
-- 테이블의 덤프 데이터 `ProductInfo`
--


-- --------------------------------------------------------

--
-- 테이블 구조 `ProductOption`
--

CREATE TABLE IF NOT EXISTS `ProductOption` (
  `OptionCode` int(6) NOT NULL DEFAULT '0' COMMENT 'OptionCode',
  `PrdCode` int(6) DEFAULT NULL COMMENT 'PrdCode',
  `PrdSize` varchar(3) DEFAULT NULL COMMENT 'PrdSize',
  `PrdColor` varchar(6) DEFAULT NULL COMMENT 'PrdColor',
  `PrdStock` int(10) DEFAULT NULL COMMENT 'PrdStock',
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`OptionCode`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='ProductOption';

--
-- 테이블의 덤프 데이터 `ProductOption`
--


-- --------------------------------------------------------

--
-- 테이블 구조 `ProductPromotion`
--

CREATE TABLE IF NOT EXISTS `ProductPromotion` (
  `PrdCode` int(6) NOT NULL DEFAULT '0' COMMENT 'PrdCode',
  `PromotionType` int(3) DEFAULT NULL COMMENT 'PromotionType',
  `StartDate` datetime DEFAULT NULL COMMENT 'StartDate',
  `EndDate` datetime DEFAULT NULL COMMENT 'EndDate',
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`PrdCode`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='ProductPromotion';

--
-- 테이블의 덤프 데이터 `ProductPromotion`
--


-- --------------------------------------------------------

--
-- 테이블 구조 `ProductSearchLog`
--

CREATE TABLE IF NOT EXISTS `ProductSearchLog` (
  `LogCode` int(6) NOT NULL DEFAULT '0' COMMENT 'LogCode',
  `SearchMember` int(6) DEFAULT NULL COMMENT 'SearchMember',
  `SearchProduct` int(6) DEFAULT NULL COMMENT 'SearchProduct',
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`LogCode`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='ProductSearchLog';

--
-- 테이블의 덤프 데이터 `ProductSearchLog`
--

INSERT INTO `ProductSearchLog` (`LogCode`, `SearchMember`, `SearchProduct`, `RegDate`, `ModDate`) VALUES
(100000, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 테이블 구조 `ShopBrand`
--

CREATE TABLE IF NOT EXISTS `ShopBrand` (
  `BrandCode` int(6) NOT NULL DEFAULT '0' COMMENT 'BrandCode',
  `BrandName` varchar(200) DEFAULT NULL COMMENT 'BrandName',
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`BrandCode`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='ShopBrand';

--
-- 테이블의 덤프 데이터 `ShopBrand`
--

INSERT INTO `ShopBrand` (`BrandCode`, `BrandName`, `RegDate`, `ModDate`) VALUES
(100001, '빈폴', '2016-01-06 09:48:43', '2016-01-06 09:48:43'),
(100002, '랄프로렌', '2016-01-06 09:48:43', '2016-01-06 09:48:43');

-- --------------------------------------------------------

--
-- 테이블 구조 `ShopFittingRoom`
--

CREATE TABLE IF NOT EXISTS `ShopFittingRoom` (
  `FitRoomCode` int(6) NOT NULL DEFAULT '0' COMMENT 'FitRoomCode',
  `ShopCode` int(6) DEFAULT NULL COMMENT 'ShopCode',
  `FitRoomNum` int(6) DEFAULT NULL COMMENT 'FitRoomNum',
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`FitRoomCode`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='ShopFittingRoom';

--
-- 테이블의 덤프 데이터 `ShopFittingRoom`
--

INSERT INTO `ShopFittingRoom` (`FitRoomCode`, `ShopCode`, `FitRoomNum`, `RegDate`, `ModDate`) VALUES
(10001, 100001, 1, '2016-01-06 14:41:21', '2016-01-06 14:41:21'),
(10002, 100001, 2, '2016-01-06 14:41:21', '2016-01-06 14:41:21'),
(10003, 100001, 3, '2016-01-06 14:41:21', '2016-01-06 14:41:21'),
(10004, 100002, 1, '2016-01-06 14:41:21', '2016-01-06 14:41:21'),
(10005, 100002, 2, '2016-01-06 14:41:21', '2016-01-06 14:41:21'),
(10006, 100002, 3, '2016-01-06 14:41:21', '2016-01-06 14:41:21');

-- --------------------------------------------------------

--
-- 테이블 구조 `ShopInfo`
--

CREATE TABLE IF NOT EXISTS `ShopInfo` (
  `ShopCode` int(6) NOT NULL DEFAULT '0' COMMENT 'ShopCode',
  `ShopName` varchar(200) DEFAULT NULL COMMENT 'ShopName',
  `BrandCode` int(6) DEFAULT NULL COMMENT 'BrandCode',
  `ShopImage` varchar(200) DEFAULT NULL COMMENT 'ShopImage',
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`ShopCode`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='ShopInfo';

--
-- 테이블의 덤프 데이터 `ShopInfo`
--

INSERT INTO `ShopInfo` (`ShopCode`, `ShopName`, `BrandCode`, `ShopImage`, `RegDate`, `ModDate`) VALUES
(100001, '서울 청량리점', 100001, '100001.png', '2016-01-06 14:40:02', '2016-01-06 14:40:02'),
(100002, '서울 잠실점', 100002, '100002.png', '2016-01-06 14:40:02', '2016-01-06 14:40:02');
