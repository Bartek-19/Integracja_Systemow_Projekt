-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Cze 05, 2025 at 02:53 PM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bazaprojekt`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `graduates`
--

CREATE TABLE `graduates` (
  `year` int(11) NOT NULL,
  `number` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `graduates`
--

INSERT INTO `graduates` (`year`, `number`) VALUES
(2024, 2000),
(2023, 2100),
(2022, 2300);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `inflation`
--

CREATE TABLE `inflation` (
  `year` int(11) NOT NULL,
  `rate` float NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inflation`
--

INSERT INTO `inflation` (`year`, `rate`) VALUES
(2024, 8.7),
(2023, 6.5),
(2022, 18.4);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'User'),
(2, 'Admin');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `login`, `password`) VALUES
(1, 'user', 'user'),
(2, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users_roles`
--

CREATE TABLE `users_roles` (
  `User_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (`User_id`, `roles_id`) VALUES
(1, 1),
(2, 2);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `graduates`
--
ALTER TABLE `graduates`
  ADD PRIMARY KEY (`year`),
  ADD KEY `GRADUATES_YEAR_INDEX` (`year`);

--
-- Indeksy dla tabeli `inflation`
--
ALTER TABLE `inflation`
  ADD PRIMARY KEY (`year`),
  ADD KEY `INFLATION_YEAR_INDEX` (`year`);

--
-- Indeksy dla tabeli `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`) USING HASH,
  ADD KEY `ROLE_NAME_INDEX` (`name`(250));

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ow0gan20590jrb00upg3va2fn` (`login`) USING HASH,
  ADD KEY `USER_LOGIN_INDEX` (`login`(250));

--
-- Indeksy dla tabeli `users_roles`
--
ALTER TABLE `users_roles`
  ADD UNIQUE KEY `UK_60loxav507l5mreo05v0im1lq` (`roles_id`),
  ADD KEY `FKe6k7h92pkxjim6t1176b7h95x` (`User_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
