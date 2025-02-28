INSERT IGNORE INTO users VALUES('user', '{noop}userPassword', '1');
INSERT IGNORE INTO authorities VALUES('user', 'read');

-- The password for adminUser is adminUserPassword
INSERT IGNORE INTO users VALUES('adminUser', '{bcrypt}$2a$12$Wl76XDmqT/z7227sQhim5eDuE8p150gyLQuzuztj4vdtPcrtFQGUq', '1');
INSERT IGNORE INTO authorities VALUES('adminUser', 'admin');

--id column is auto_increment and it is not necessary to be specified
INSERT INTO customers (email, pwd, role) VALUES('user@gmail.com', '{noop}userPassword', 'read');
INSERT INTO customers (email, pwd, role) VALUES('adminUser@gmail.com', '{bcrypt}$2a$12$Wl76XDmqT/z7227sQhim5eDuE8p150gyLQuzuztj4vdtPcrtFQGUq', 'admin');