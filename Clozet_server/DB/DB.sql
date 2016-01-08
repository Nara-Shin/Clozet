-- phpMyAdmin SQL Dump
-- version 2.11.11
-- http://www.phpmyadmin.net
--
-- 호스트: h6.woobi.co.kr
-- 처리한 시간: 16-01-06 20:09 
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
  `PrdBarcode` varchar(100) NOT NULL,
  `PrdName` varchar(300) DEFAULT NULL COMMENT 'PrdName',
  `PrdPrice` int(10) DEFAULT NULL COMMENT 'PrdPrice',
  `PrdDetail` varchar(1000) NOT NULL,
  `ShopCode` int(6) DEFAULT NULL COMMENT 'ShopCode',
  `PrdImage` varchar(200) NOT NULL,
  `RegDate` datetime DEFAULT NULL COMMENT 'RegDate',
  `ModDate` datetime DEFAULT NULL COMMENT 'ModDate',
  PRIMARY KEY (`PrdCode`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='ProductInfo';

--
-- 테이블의 덤프 데이터 `ProductInfo`
--

INSERT INTO `ProductInfo` (`PrdCode`, `PrdBarcode`, `PrdName`, `PrdPrice`, `PrdDetail`, `ShopCode`, `PrdImage`, `RegDate`, `ModDate`) VALUES
(100000, '', NULL, NULL, '', NULL, '', NULL, NULL),
(100001, '1234', '블루 애니다운 하프점퍼', 699000, '', 100001, '100001.jpg', '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100002, '', '더블버튼 울코트', 129900, '', 100001, '100002.jpg', '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100003, '', '베이지 애니다운 하프 점퍼', 699000, '', 100001, '100003.jpg', '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100004, '', '아이보리 코튼 기모 팬츠', 167300, '', 100001, '100004.jpg', '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100005, '', '스위트 멀티 프린트 스웨트 셔츠', 39900, '', 100001, '100005.jpg', '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100006, '', '인조스웨이드 풀지퍼 재킷', 209000, '', 100002, '100006.jpg', '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100007, '', '턴락 울 혼방 카디건', 289000, '', 100002, '100007.jpg', '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100008, '', '인조 레더 트리밍 트렌치 코트', 269000, '', 100002, '100008.jpg', '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100009, '', '브룩 스키니 치노 팬츠', 209000, '', 100002, '100009.jpg', '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100010, '', '윈도페인 울 플란넬 스커트', 189000, '', 100002, '100010.jpg', '2016-01-05 17:46:00', '2016-01-05 17:46:00');

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

INSERT INTO `ProductOption` (`OptionCode`, `PrdCode`, `PrdSize`, `PrdColor`, `PrdStock`, `RegDate`, `ModDate`) VALUES
(100001, 100001, '90', 'ebceb1', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100002, 100001, '95', 'ebceb1', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100003, 100001, '100', 'ebceb1', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100004, 100001, '90', '000000', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100005, 100001, '95', '000000', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100006, 100001, '100', '000000', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100007, 100001, '90', '089acc', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100008, 100001, '95', '089acc', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100009, 100001, '100', '089acc', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100010, 100002, 'S', 'd3a125', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100011, 100002, 'M', 'd3a125', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100012, 100002, 'S', 'a92078', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100013, 100002, 'M', 'a92078', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100014, 100003, '90', 'ebceb1', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100015, 100003, '95', 'ebceb1', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100016, 100003, '100', 'ebceb1', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100017, 100003, '90', '000000', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100018, 100003, '95', '000000', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100019, 100003, '100', '000000', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100020, 100003, '90', '089acc', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100021, 100003, '95', '089acc', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100022, 100003, '100', '089acc', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100023, 100004, '67', 'fff4c7', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100024, 100004, '70', 'fff4c7', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100025, 100004, '73', 'fff4c7', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100026, 100004, '76', 'fff4c7', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100027, 100004, '67', 'aa704b', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100028, 100004, '70', 'aa704b', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100029, 100004, '73', 'aa704b', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100030, 100004, '76', 'aa704b', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100031, 100004, '67', '7b885c', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100032, 100004, '70', '7b885c', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100033, 100004, '73', '7b885c', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100034, 100004, '76', '7b885c', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100035, 100004, '67', '0a1f89', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100036, 100004, '70', '0a1f89', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100037, 100004, '73', '0a1f89', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100038, 100004, '76', '0a1f89', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100039, 100005, 'S', 'ffffff', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100040, 100005, 'M', '808080', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100041, 100005, 'S', 'ffffff', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100042, 100005, 'M', '808080', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100043, 100006, 'XS', 'cc996c', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100044, 100006, 'M', 'cc996c', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100045, 100007, 'XXS', '615C58', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100046, 100007, 'XS', '615C58', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100047, 100007, 'S', '615C58', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100048, 100007, 'M', '615C58', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100049, 100007, 'L', '615C58', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100050, 100008, 'XXS', 'EDD3BC', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100051, 100008, 'XS', 'EDD3BC', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100052, 100008, 'S', 'EDD3BC', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100053, 100008, 'M', 'EDD3BC', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100054, 100008, 'L', 'EDD3BC', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100055, 100009, '0', 'BFB7B5', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100056, 100009, '2', 'BFB7B5', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100057, 100009, '4', 'BFB7B5', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100058, 100009, '6', 'BFB7B5', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100059, 100009, '8', 'BFB7B5', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100060, 100009, '10', 'BFB7B5', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100061, 100010, '0', 'DFD8C8', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100062, 100010, '2', 'DFD8C8', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100063, 100010, '4', 'DFD8C8', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100064, 100010, '6', 'DFD8C8', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00'),
(100065, 100010, '8', 'DFD8C8', 10, '2016-01-05 17:46:00', '2016-01-05 17:46:00');

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
(100000, NULL, NULL, NULL, NULL),
(100001, 100001, 100001, '2016-01-06 19:28:20', '2016-01-06 19:28:20');

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
(100001, 100001, 1, '2016-01-06 14:41:21', '2016-01-06 14:41:21'),
(100002, 100001, 2, '2016-01-06 14:41:21', '2016-01-06 14:41:21'),
(100003, 100001, 3, '2016-01-06 14:41:21', '2016-01-06 14:41:21'),
(100004, 100002, 1, '2016-01-06 14:41:21', '2016-01-06 14:41:21'),
(100005, 100002, 2, '2016-01-06 14:41:21', '2016-01-06 14:41:21'),
(100006, 100002, 3, '2016-01-06 14:41:21', '2016-01-06 14:41:21');

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
