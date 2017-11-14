DROP DATABASE IF EXISTS hotelDB;
CREATE DATABASE hotelDB;
USE hotelDB;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
	firstName VARCHAR(20) NOT NULL,
	lastName VARCHAR(20) NOT NULL,
	username VARCHAR(20) NOT NULL,
	password VARCHAR(20) NOT NULL,
	age INT NOT NULL,
	gender ENUM('M', 'F'),
	userRole ENUM('Customer', 'Manager', 'Room Attendant'),
	PRIMARY KEY(username)
);

DROP TABLE IF EXISTS room;
CREATE TABLE room (
	roomID INT(10) NOT NULL AUTO_INCREMENT,
	costPerNight DOUBLE(10,2) NOT NULL, 
	roomType VARCHAR(20) NOT NULL,
	PRIMARY KEY(roomID)
);

DROP TABLE IF EXISTS reservation;
CREATE TABLE reservation (
	reservationID INT(10) NOT NULL AUTO_INCREMENT,
	roomID INT(10) NOT NULL,
	customerName VARCHAR(20) NOT NULL,
	startDate DATE NOT NULL,
	endDate DATE NOT NULL,
	totalNumOfDays INT(10),
	totalCost DOUBLE(10,2),
	cancelled BOOLEAN NOT NULL DEFAULT FALSE,
	updateOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
	PRIMARY KEY(reservationID),
	FOREIGN KEY(roomID) references room(roomID),
	FOREIGN KEY(customerName) references user(username)
);

DROP TABLE IF EXISTS roomService;
CREATE TABLE roomService (
	taskID INT(10) NOT NULL AUTO_INCREMENT,
	username VARCHAR(20) NOT NULL,
	task VARCHAR(20) NOT NULL,
	completedBy VARCHAR(20),
	reservationID  INT(10),
	updateOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
	PRIMARY KEY(taskID),
	FOREIGN KEY(completedBy) references user(username),
	FOREIGN KEY(username) references user(username),
	FOREIGN KEY(reservationID) references reservation(reservationID) 
);

DROP TABLE IF EXISTS complaint;
CREATE TABLE complaint(
	complaintID INT(20) NOT NULL AUTO_INCREMENT,
	customer VARCHAR(20) NOT NULL,
	complaint VARCHAR(150) NOT NULL,
	time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	resolvedBy VARCHAR(20),
	solution VARCHAR(150),
	updateOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
	PRIMARY KEY(complaintID),
	FOREIGN KEY(customer) references user(username),
	FOREIGN KEY(resolvedBy) references user(username)
);

DROP TABLE IF EXISTS ratingFeedback;
CREATE TABLE ratingFeedback (
	ratingID INT(20) NOT NULL AUTO_INCREMENT,
	customer VARCHAR(20) NOT NULL,
	rating INT NOT NULL,
	PRIMARY KEY(ratingID),
	FOREIGN KEY(customer) references user(username)
);

DROP TABLE IF EXISTS reservationArchive;
CREATE TABLE reservationArchive (
	reservationID INT(10) NOT NULL AUTO_INCREMENT,
	roomID INT(10) NOT NULL,
	customerName VARCHAR(20) NOT NULL,
	startDate DATE NOT NULL,
	endDate DATE NOT NULL,
	totalNumOfDays INT(10),
	totalCost DOUBLE(10,2),
	cancelled BOOLEAN NOT NULL DEFAULT FALSE,
	updateOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
	PRIMARY KEY(reservationID),
	FOREIGN KEY(roomID) references room(roomID),
	FOREIGN KEY(customerName) references user(username)
);
	
DROP TABLE IF EXISTS roomServiceArchive;
CREATE TABLE roomServiceArchive (
	taskID INT(10) NOT NULL AUTO_INCREMENT,
	username VARCHAR(20) NOT NULL,
	task VARCHAR(20) NOT NULL,
	completedBy VARCHAR(20),
	updateOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
	PRIMARY KEY(taskID),
	FOREIGN KEY(completedBy) references user(username),
	FOREIGN KEY(username) references user(username)
);

DROP TABLE IF EXISTS complaintArchive;
CREATE TABLE complaintArchive(
	complaintID INT(20) NOT NULL AUTO_INCREMENT,
	customer VARCHAR(20) NOT NULL,
	complaint VARCHAR(150) NOT NULL,
	time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	resolvedBy VARCHAR(20),
	solution VARCHAR(150),
	updateOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
	PRIMARY KEY(complaintID),
	FOREIGN KEY(customer) references user(username),
	FOREIGN KEY(resolvedBy) references user(username)
);



