
/* insert data into 'genre' table*/
INSERT INTO `genre`(`genre_name`) VALUES ('Horror');
INSERT INTO `genre`(`genre_name`) VALUES ('Fantasy');
INSERT INTO `genre`(`genre_name`) VALUES ('Science fiction');
INSERT INTO `genre`(`genre_name`) VALUES ('Western');
INSERT INTO `genre`(`genre_name`) VALUES ('Romance');
INSERT INTO `genre`(`genre_name`) VALUES ('Thriller');
INSERT INTO `genre`(`genre_name`) VALUES ('Mystery');
INSERT INTO `genre`(`genre_name`) VALUES ('Detective story');
INSERT INTO `genre`(`genre_name`) VALUES ('Dystopia');
INSERT INTO `genre`(`genre_name`) VALUES ('Memoir');
INSERT INTO `genre`(`genre_name`) VALUES ('Biography');
INSERT INTO `genre`(`genre_name`) VALUES ('Play');
INSERT INTO `genre`(`genre_name`) VALUES ('Musical');
INSERT INTO `genre`(`genre_name`) VALUES ('Satire');
INSERT INTO `genre`(`genre_name`) VALUES ('Haiku');
INSERT INTO `genre`(`genre_name`) VALUES ('Children\'s books');
INSERT INTO `genre`(`genre_name`) VALUES ('Adult literature');
INSERT INTO `genre`(`genre_name`) VALUES ('Drama');
INSERT INTO `genre`(`genre_name`) VALUES ('Adventure');
INSERT INTO `genre`(`genre_name`) VALUES ('Fiction');


/*insert data to 'author' table*/

INSERT INTO `author`(`author_name`) VALUES ('Stephen King');
INSERT INTO `author`(`author_name`) VALUES ('Ray Brudbary');
INSERT INTO `author`(`author_name`) VALUES ('Agatha Christie');
INSERT INTO `author`(`author_name`) VALUES ('Leo Tolstoy');
INSERT INTO `author`(`author_name`) VALUES ('Paulo Coelho');
INSERT INTO `author`(`author_name`) VALUES ('Jane Austen');
INSERT INTO `author`(`author_name`) VALUES ('Mark Twain');
INSERT INTO `author`(`author_name`) VALUES ('Ernest Hemingway');
INSERT INTO `author`(`author_name`) VALUES ('George Orwell');
INSERT INTO `author`(`author_name`) VALUES ('Alexandre Dumas');
INSERT INTO `author`(`author_name`) VALUES ('Oscar Wilde');
INSERT INTO `author`(`author_name`) VALUES ('Arthur Conan Doyle');
INSERT INTO `author`(`author_name`) VALUES ('Victor Hugo');
INSERT INTO `author`(`author_name`) VALUES ('Franz Kafka');


/* insert data into 'book' table*/

INSERT INTO `book`(`title`, `author_id`, `genre_id`, `quantity`) VALUES ('Fahrenheit 451',2,3,5);
INSERT INTO `book`(`title`, `author_id`, `genre_id`, `quantity`) VALUES ('Dandelion Wine',2,3,5);
INSERT INTO `book`(`title`, `author_id`, `genre_id`, `quantity`) VALUES ('It',1,6,5);
INSERT INTO `book`(`title`, `author_id`, `genre_id`, `quantity`) VALUES ('The Shining',1,1,5);
INSERT INTO `book`(`title`, `author_id`, `genre_id`, `quantity`) VALUES ('The Alchemist',5,18,5);
INSERT INTO `book`(`title`, `author_id`, `genre_id`, `quantity`) VALUES ('Adultery',5,20,5);
INSERT INTO `book`(`title`, `author_id`, `genre_id`, `quantity`) VALUES ('The Count of Monte Cristo',10,19,5);
INSERT INTO `book`(`title`, `author_id`, `genre_id`, `quantity`) VALUES ('The Picture of Dorian Gray',11,20,5);
INSERT INTO `book`(`title`, `author_id`, `genre_id`, `quantity`) VALUES ('The Metamorphosis',14,20,5);
INSERT INTO `book`(`title`, `author_id`, `genre_id`, `quantity`) VALUES ('The Castle',14,9,5);


/*insert data into 'user' table*/
INSERT INTO `user`(`first_name`, `last_name`, `user_name`,`email`,`password`,`role`) VALUES ('Nick','Jonson','johny','tonoyan.sergey1@mail.ru','0000','USER');
INSERT INTO `user`(`first_name`, `last_name`, `user_name`,`email`,`password`,`role`) VALUES ('Tom','Smith','tommy','tommy@mail.com','1111','USER');

/*insert data into 'admin' table*/
INSERT INTO `admin`(`user_name`,`password`,`role`) VALUES ('admin','0000','ADMIN');

INSERT INTO `loan_table` (`created_on`, `updated_on`, `expire_date`, `loan_date`, `book_id`, `user_id`) VALUES ('2019-09-10 11:21:16', '2019-09-10 11:21:16', '2019-09-15 11:21:16.161000', '2019-09-10 11:21:12.826000', '5', '1');
INSERT INTO `loan_table` (`created_on`, `updated_on`, `expire_date`, `loan_date`, `book_id`, `user_id`) VALUES ('2019-09-01 11:21:16', '2019-09-01 11:21:16', '2019-09-16 11:21:16.161000', '2019-09-01 11:21:12.826000', '9', '2');
INSERT INTO `loan_table` (`created_on`, `updated_on`, `expire_date`, `loan_date`, `book_id`, `user_id`) VALUES ('2019-08-20 11:21:16', '2019-08-20 11:21:16', '2019-08-25 11:21:16.161000', '2019-08-20 11:21:12.826000', '4', '1');
INSERT INTO `loan_table` (`created_on`, `updated_on`, `expire_date`, `loan_date`, `book_id`, `user_id`) VALUES ('2019-09-16 11:21:16', '2019-09-16 11:21:16', '2019-09-26 11:21:16.161000', '2019-09-16 11:21:12.826000', '5', '1');
INSERT INTO `loan_table` (`created_on`, `updated_on`, `expire_date`, `loan_date`, `book_id`, `user_id`) VALUES ('2019-09-12 11:21:16', '2019-09-12 11:21:16', '2019-09-17 11:21:16.161000', '2019-09-12 11:21:12.826000', '1', '1');
INSERT INTO `loan_table` (`created_on`, `updated_on`, `expire_date`, `loan_date`, `book_id`, `user_id`) VALUES ('2019-08-01 11:21:16', '2019-08-01 11:21:16', '2019-08-16 11:21:16.161000', '2019-08-01 11:21:12.826000', '3', '2');
INSERT INTO `loan_table` (`created_on`, `updated_on`, `expire_date`, `loan_date`, `book_id`, `user_id`) VALUES ('2019-08-10 11:21:16', '2019-08-10 11:21:16', '2019-08-20 11:21:16.161000', '2019-08-10 11:21:12.826000', '5', '1');
INSERT INTO `loan_table` (`created_on`, `updated_on`, `expire_date`, `loan_date`, `book_id`, `user_id`) VALUES ('2019-08-23 11:21:16', '2019-08-23 11:21:16', '2019-08-28 11:21:16.161000', '2019-08-23 11:21:12.826000', '7', '2');
INSERT INTO `loan_table` (`created_on`, `updated_on`, `expire_date`, `loan_date`, `book_id`, `user_id`) VALUES ('2019-09-05 11:21:16', '2019-09-05 11:21:16', '2019-09-15 11:21:16.161000', '2019-09-05 11:21:12.826000', '6', '2');
INSERT INTO `loan_table` (`created_on`, `updated_on`, `expire_date`, `loan_date`, `book_id`, `user_id`) VALUES ('2019-09-10 10:15:10', '2019-09-10 10:15:10', '2019-09-15 10:15:101000', '2019-09-10 10:15:106000', '10', '1');



