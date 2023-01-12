-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 04, 2023 at 10:26 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projekat`
--

-- --------------------------------------------------------

--
-- Table structure for table `historija`
--

CREATE TABLE `historija` (
  `IDKor` int(11) NOT NULL,
  `IDNar` int(11) NOT NULL,
  `KorPro` varchar(255) NOT NULL,
  `ImePro` varchar(255) NOT NULL,
  `CijenaPro` int(11) NOT NULL,
  `KolPro` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

--
-- Dumping data for table `historija`
--

INSERT INTO `historija` (`IDKor`, `IDNar`, `KorPro`, `ImePro`, `CijenaPro`, `KolPro`) VALUES
(2, 11, 'don', 'Banana', 8, 14),
(2, 14, 'don', 'Limun', 2, 36),
(3, 89, 'amra', 'Jabuka', 4, 44),
(3, 156, 'amra', 'Narandza', 6, 69);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `historija`
--
ALTER TABLE `historija`
  ADD PRIMARY KEY (`IDNar`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
