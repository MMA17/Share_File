-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 10, 2021 lúc 12:39 PM
-- Phiên bản máy phục vụ: 10.4.18-MariaDB
-- Phiên bản PHP: 8.0.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `sharefile`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblfile`
--

CREATE TABLE `tblfile` (
  `file_id` int(11) NOT NULL,
  `file_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `size` int(11) NOT NULL,
  `file_extension` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `owner` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tblfile`
--

INSERT INTO `tblfile` (`file_id`, `file_name`, `size`, `file_extension`, `owner`) VALUES
(1, 'test file', 1024, '.pdf', 1),
(2, 'test file 2', 1024, '.pdf', 2),
(5, 'Bao cao bai tap lon QLATTT nhom 15.docx', 1024, '.pdf', 1),
(6, 'Bao cao bai tap lon QLATTT nhom 15.docx', 1024, '.pdf', 1),
(7, 'Bao cao bai tap lon QLATTT nhom 15.docx', 1024, '.pdf', 1),
(8, 'Untitled.png', 1024, '.pdf', 1),
(9, 'Untitled.png', 1024, '.pdf', 1),
(10, 'dashboard.jsp', 1024, '.pdf', 1),
(11, 'Redis 101.pptx', 1024, '.pdf', 1),
(12, 'Redis 101.pptx', 1024, '.pdf', 1),
(13, 'Watercolor College Project by Slidesgo.pptx', 1024, '.pdf', 1),
(14, 'known_hosts', 1024, '.pdf', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblfriend`
--

CREATE TABLE `tblfriend` (
  `sender_id` int(11) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  `status` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tblfriend`
--

INSERT INTO `tblfriend` (`sender_id`, `receiver_id`, `status`) VALUES
(1, 8, 'done'),
(1, 6, 'done');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblgroup`
--

CREATE TABLE `tblgroup` (
  `group_id` int(11) NOT NULL,
  `group_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `group_type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbljoingroup`
--

CREATE TABLE `tbljoingroup` (
  `join_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `note` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblpermission`
--

CREATE TABLE `tblpermission` (
  `readPermission` int(11) DEFAULT NULL,
  `deletePermission` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `file_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tblpermission`
--

INSERT INTO `tblpermission` (`readPermission`, `deletePermission`, `user_id`, `file_id`) VALUES
(1, 1, 1, 1),
(1, 1, 1, 2),
(1, 1, 1, 5),
(1, 1, 1, 6),
(1, 1, 1, 7),
(1, 1, 1, 8),
(1, 1, 1, 9),
(1, 1, 1, 10),
(1, 1, 1, 11),
(1, 1, 1, 12),
(1, 1, 1, 13),
(1, 1, 1, 14);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbluser`
--

CREATE TABLE `tbluser` (
  `user_id` int(10) NOT NULL,
  `user_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `pass` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `usaged` int(10) NOT NULL DEFAULT 0,
  `tel` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tbluser`
--

INSERT INTO `tbluser` (`user_id`, `user_name`, `pass`, `name`, `usaged`, `tel`, `note`) VALUES
(1, 'hoangviet', '123', 'Nguyen Hoang Viet', 0, '01111111111', ''),
(2, 'hoangviet1', '123', 'Nguyen Hoang Viet', 0, '01111111111', ''),
(6, 'hviet', '123', 'hoang viet 222', 0, '122201021', NULL),
(8, 'viet', '1', 'hoang viet', 0, '0111111111', NULL);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `tblfile`
--
ALTER TABLE `tblfile`
  ADD PRIMARY KEY (`file_id`),
  ADD KEY `owner` (`owner`);

--
-- Chỉ mục cho bảng `tblfriend`
--
ALTER TABLE `tblfriend`
  ADD KEY `sender_id` (`sender_id`),
  ADD KEY `receiver_id` (`receiver_id`);

--
-- Chỉ mục cho bảng `tblgroup`
--
ALTER TABLE `tblgroup`
  ADD PRIMARY KEY (`group_id`);

--
-- Chỉ mục cho bảng `tbljoingroup`
--
ALTER TABLE `tbljoingroup`
  ADD KEY `user_id` (`user_id`),
  ADD KEY `group_id` (`group_id`);

--
-- Chỉ mục cho bảng `tblpermission`
--
ALTER TABLE `tblpermission`
  ADD KEY `user_id` (`user_id`),
  ADD KEY `file_id` (`file_id`);

--
-- Chỉ mục cho bảng `tbluser`
--
ALTER TABLE `tbluser`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_name` (`user_name`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `tblfile`
--
ALTER TABLE `tblfile`
  MODIFY `file_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `tblgroup`
--
ALTER TABLE `tblgroup`
  MODIFY `group_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `tbluser`
--
ALTER TABLE `tbluser`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `tblfile`
--
ALTER TABLE `tblfile`
  ADD CONSTRAINT `tblfile_ibfk_1` FOREIGN KEY (`owner`) REFERENCES `tbluser` (`user_id`);

--
-- Các ràng buộc cho bảng `tblfriend`
--
ALTER TABLE `tblfriend`
  ADD CONSTRAINT `tblfriend_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `tbluser` (`user_id`),
  ADD CONSTRAINT `tblfriend_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `tbluser` (`user_id`);

--
-- Các ràng buộc cho bảng `tbljoingroup`
--
ALTER TABLE `tbljoingroup`
  ADD CONSTRAINT `tbljoingroup_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbluser` (`user_id`),
  ADD CONSTRAINT `tbljoingroup_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `tblgroup` (`group_id`);

--
-- Các ràng buộc cho bảng `tblpermission`
--
ALTER TABLE `tblpermission`
  ADD CONSTRAINT `tblpermission_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbluser` (`user_id`),
  ADD CONSTRAINT `tblpermission_ibfk_2` FOREIGN KEY (`file_id`) REFERENCES `tblfile` (`file_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
