-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 12, 2017 at 01:54 AM
-- Server version: 5.7.19-0ubuntu0.16.04.1
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
-- Table structure for table `answers`
--

CREATE TABLE `answers` (
  `id` char(36) NOT NULL,
  `user_id` char(36) NOT NULL,
  `poll_id` char(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `answers`
--

INSERT INTO `answers` (`id`, `user_id`, `poll_id`) VALUES
('341f5b5f-b835-4453-86ee-0d9b9aa88984', '7fc03104-3875-45c1-8aaa-c3f62ffec0e3', 'f8d6f52b-8880-45b3-8db0-da425b20f4d9'),
('e11b5bc8-2a83-4fcb-8f40-d92435083cb3', '7fc03104-3875-45c1-8aaa-c3f62ffec0e3', '30880f19-cd85-43e2-aa40-fe29a304a0cb');

-- --------------------------------------------------------

--
-- Table structure for table `offered_answers`
--

CREATE TABLE `offered_answers` (
  `id` char(36) NOT NULL,
  `answer_text` varchar(256) NOT NULL,
  `count` int(11) DEFAULT '0',
  `question_id` char(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `offered_answers`
--

INSERT INTO `offered_answers` (`id`, `answer_text`, `count`, `question_id`) VALUES
('04795476-84a5-4b18-abfb-bebef1d91c22', 'CA', 0, '89b83179-7c85-445f-a835-3be529f114f1'),
('5c812729-cc55-45a2-ba1b-8c304c3e3baa', 'MT', 0, '89b83179-7c85-445f-a835-3be529f114f1'),
('aef75c0a-afce-4e52-a31e-d57b6a3a2b8e', 'RR', 0, '64b90327-dfbc-4c44-b3b9-5b08bac71faa'),
('d872ff3c-f2be-44b2-8857-05ad97c854f0', 'KN', 0, '64b90327-dfbc-4c44-b3b9-5b08bac71faa');

-- --------------------------------------------------------

--
-- Table structure for table `polls`
--

CREATE TABLE `polls` (
  `id` char(36) NOT NULL,
  `text` varchar(256) NOT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `is_open` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `polls`
--

INSERT INTO `polls` (`id`, `text`, `start_date`, `end_date`, `is_open`) VALUES
('30880f19-cd85-43e2-aa40-fe29a304a0cb', 'Elections CLLFST', '2017-10-11 22:12:00', '2017-10-11 22:12:00', 0);

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `id` char(36) NOT NULL,
  `question_text` varchar(256) NOT NULL,
  `poll_id` char(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`id`, `question_text`, `poll_id`) VALUES
('64b90327-dfbc-4c44-b3b9-5b08bac71faa', 'Mediatisation ? ', '30880f19-cd85-43e2-aa40-fe29a304a0cb'),
('89b83179-7c85-445f-a835-3be529f114f1', 'SG ? ', '30880f19-cd85-43e2-aa40-fe29a304a0cb');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` char(36) NOT NULL,
  `first_name` varchar(256) NOT NULL,
  `last_name` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `first_name`, `last_name`, `email`, `password`) VALUES
('7fc03104-3875-45c1-8aaa-c3f62ffec0e3', 'Chebbi', 'Aymen', 'aymen.chebi@gmail.com', 'root');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `answers`
--
ALTER TABLE `answers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `offered_answers`
--
ALTER TABLE `offered_answers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `polls`
--
ALTER TABLE `polls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
