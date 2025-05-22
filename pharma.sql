-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 22, 2025 at 03:46 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pharma`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `cart_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `total_quantity` int(11) NOT NULL,
  `total_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`cart_id`, `user_id`, `total_quantity`, `total_price`) VALUES
(1, 8, 1, 3),
(2, 7, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `cart_prod`
--

CREATE TABLE `cart_prod` (
  `cart_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cart_prod`
--

INSERT INTO `cart_prod` (`cart_id`, `product_id`) VALUES
(1, 19);

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL,
  `order_date` int(11) NOT NULL,
  `total_price` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`order_id`, `order_date`, `total_price`, `user_id`, `status`) VALUES
(1, 1747840558, 175, 8, 'PROCESSING'),
(2, 1747840906, 175, 7, 'SHIPPED'),
(3, 1747841022, 175, 7, 'COMPLETED'),
(4, 1747841885, 175, 8, 'PROCESSING'),
(5, 1747846804, 274, 8, 'COMPLETED'),
(6, 1747863858, 99, 8, 'PROCESSING'),
(7, 1747872452, 80, 8, 'PROCESSING');

-- --------------------------------------------------------

--
-- Table structure for table `order_prod`
--

CREATE TABLE `order_prod` (
  `product_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_prod`
--

INSERT INTO `order_prod` (`product_id`, `order_id`) VALUES
(3, 1),
(3, 2),
(2, 3),
(3, 4),
(3, 5),
(4, 5),
(4, 6),
(18, 7);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(200) NOT NULL,
  `product_category` varchar(200) NOT NULL,
  `product_description` varchar(200) NOT NULL,
  `product_price` int(11) NOT NULL,
  `product_quantity` int(11) NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `product_name`, `product_category`, `product_description`, `product_price`, `product_quantity`, `image`) VALUES
(2, 'Baby Oil', 'SkinCare', 'erfw', 35, 5, '1747840427687_COSRX Snail Mucin.webp'),
(3, 'Baby Oil', 'BabyProducts', 'Baby Oil', 35, 5, '1747840540604_baby-lotion.jpg'),
(4, 'Theromometer', 'Equipments', 'Thermometer\r\n', 33, 3, '1747841935235_Digital Thermometer.jpg'),
(7, 'New Product', 'Equipments', 'New', 32, 4, '1747867005105_vitamin-c.jpg'),
(8, 'Wheel Chair', 'Equipments', 'Wheel Chair for Adults', 200, 3, '1747867522764_1000_F_84368969_j77KHymgsz2l8Pzw3HCzLQ0MmE3a0GQZ.jpg'),
(9, 'Sthetoscope', 'Equipments', 'sthetoscope for doctors', 10, 3, '1747867882562_istockphoto-1179610553-2048x2048.jpg'),
(10, 'Blood pressure checker', 'Equipments', 'Blood pressure checker is for patient ', 25, 2, '1747868001633_top-view-tensiometer-checking-blood-pressure.jpg'),
(11, 'Baby-lotion', 'BabyProducts', 'gentle touch Baby lotion for soft, happy skin nature your baby\'s delicate skin with ultra-gentle and fast absorbing baby lotion.', 15, 5, '1747868916718_baby-lotion.jpg'),
(12, 'Paracetamol ', 'Medicine', 'paracetamol widely used in pain reliver and fever.', 1, 3, '1747869130306_paracetamol.jpg'),
(14, 'New Product', 'Medicine', 'New', 22, 3, '1747869325840_Thyronorm 25mcg.jpg'),
(15, 'New Product', 'Equipments', 'New', 230, 2, '1747869455693_1000_F_189577923_ANoSMIVLctEun9cy9WUafBnJ2YJvds9e.jpg'),
(16, 'Baby Wet Tissue', 'BabyProducts', 'New Product', 12, 7, '1747869571292_Baby Wet Tissue.jpeg'),
(17, 'Aclov-Cream ', 'Medicine', 'Aclov Cream contains alclometasone dipropionate 0.05%, a moderately potent topical corticosterold designed to alleviate inflammation and itching associated with various skin conditions.', 16, 6, '1747870164015_Aclov cream.png'),
(18, 'Metro-Gel', 'Medicine', 'Metro gel is a topical antibiotic gel containing 0.75%.', 8, 10, '1747870306807_Metro_Gel-20.png'),
(19, 'Flexi-joint Tablet', 'Medicine', 'New', 3, 18, '1747870378602_Flexi_Joint_tablet.png'),
(20, 'Levera-500', 'Medicine', 'New', 10, 4, '1747870459674_Levera-500.png');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(255) NOT NULL,
  `user_name` varchar(225) NOT NULL,
  `user_password` varchar(256) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `user_role` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone_number` varchar(100) NOT NULL,
  `profile_picture` varchar(255) DEFAULT 'default.png'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `user_password`, `user_email`, `gender`, `user_role`, `first_name`, `last_name`, `phone_number`, `profile_picture`) VALUES
(7, 'Aaron', 'naU8sP0rMsZuVBCd9bwyVjg8z8KyoqspT4TIBb73ih6yGsp8bB3Lqt+sSufTHU3K', 'aaron@gmail.com', 'male', 'admin', 'Aaron', 'Fauzadr', '9845556917', 'default.png'),
(8, 'rabina12', 'L0HfyYV0g3S+JqXk9R1Y27Nq3Sdi3o5mFdYEOZtE5oiNUNsrH/PvvkKWj0dZPIS5sCG778ac', 'rabina@gmail.com', 'female', 'user', 'rabina', 'lama', '9876543210', 'default.png'),
(9, 'sweta', '0E0409IDy/oNWs/w7BkgO395ynkzsBKmZw7sOZ3Qxzvfdv8QWooSf315BQ2yPQ/DCWx64Nvf', 'sweata@gmail.com', 'female', 'user', 'sweta', 'koirala', '9876543210', 'default.png'),
(10, 'rabina', 'Rabina@123', 'rabina12@gmail.com', 'female', 'user', 'rabina', 'lama', '9876543210', 'default.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`cart_id`),
  ADD KEY `userfk1` (`user_id`);

--
-- Indexes for table `cart_prod`
--
ALTER TABLE `cart_prod`
  ADD KEY `productfk2` (`product_id`),
  ADD KEY `cart_fk2` (`cart_id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `userfk2` (`user_id`);

--
-- Indexes for table `order_prod`
--
ALTER TABLE `order_prod`
  ADD KEY `orderfk_1` (`order_id`),
  ADD KEY `productfk1` (`product_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `cart_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `userfk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `cart_prod`
--
ALTER TABLE `cart_prod`
  ADD CONSTRAINT `cart_fk1` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`),
  ADD CONSTRAINT `cart_fk2` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`),
  ADD CONSTRAINT `productfk2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `userfk2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `order_prod`
--
ALTER TABLE `order_prod`
  ADD CONSTRAINT `orderfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`),
  ADD CONSTRAINT `productfk1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
