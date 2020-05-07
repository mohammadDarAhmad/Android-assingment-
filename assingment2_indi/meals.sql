-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 07, 2020 at 11:48 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `meals`
--

-- --------------------------------------------------------

--
-- Table structure for table `meales`
--

CREATE TABLE `meales` (
  `IDMeals` int(10) NOT NULL,
  `NameMeals` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `TypeMeals` varchar(20) COLLATE utf8_bin NOT NULL,
  `countryMeals` varchar(20) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `meales`
--

INSERT INTO `meales` (`IDMeals`, `NameMeals`, `TypeMeals`, `countryMeals`) VALUES
(1, 'Greek Pizza', 'Pizza', 'Greek'),
(2, 'spaghetti', 'macronia', 'italy');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `meales`
--
ALTER TABLE `meales`
  ADD PRIMARY KEY (`IDMeals`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `meales`
--
ALTER TABLE `meales`
  MODIFY `IDMeals` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
