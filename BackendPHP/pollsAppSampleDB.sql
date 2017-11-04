-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 04, 2017 at 01:52 PM
-- Server version: 5.7.20-0ubuntu0.16.04.1
-- PHP Version: 7.0.22-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pollsapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` char(36) NOT NULL,
  `first_name` varchar(256) NOT NULL,
  `last_name` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  `admin` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `first_name`, `last_name`, `email`, `password`, `created`, `description`, `admin`) VALUES
('40f18202-86ef-4bd0-8895-c7730d7a9b1b', 'aymen', 'chebbi', 'aymen@gmail.com', '$2y$10$dWpUMQFPWtyGHINSTJGYuObq8NKE9LIrNdb6wWn.HBbp3/Akh4jui', '2017-10-29 12:04:42', NULL, 0),
('af6cf673-5d93-45d8-b2dc-8bc7e5842d7c', 'aymen', 'chebbi', 'aymen.chebi@gmail.com', '$2y$10$G9QNQltIaqwYyWQ7QUweUe4xOpH24nGB0S2BFg.8mKLlASuQKFrNq', '2017-10-29 11:02:18', '21 year old Tunisian, Tech enthusiast & OpenSource supporter. Computer Science Engineering Student at Faculty of Science of Tunis. General Secretary at CLLFST.', 1),
('dc71e974-db24-425e-95b8-a13e716cd5b4', 'Mahmoud', 'Turki', 'mahmoud.tuurki@gmail.com', '$2y$10$/IotZ4L6pqtLE62CIJTP0OvpXD8DtqpJSHAdUtmxsZVNdSVJIFHSq', '2017-11-04 12:40:34', NULL, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;