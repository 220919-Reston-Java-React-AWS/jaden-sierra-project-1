drop table if exists users CASCADE;
drop table if exists reimbursements;


create table users (
	id SERIAL primary key, -- autoincrementing INTEGER
	username VARCHAR(50) not null unique,
	password VARCHAR(50) not null,
	role VARCHAR(50)not NULL
);


create table reimbursements (
id SERIAL PRIMARY KEY,
amount NUMERIC(9,2),
description TEXT,
status VARCHAR(50),
userID INTEGER REFERENCES users(id) NOT NULL
);

INSERT INTO users (username, PASSWORD, role)
VALUES 
('Jaden', 'Jaden111','Employee'),
('Kyle', 'Kyle111','Employee'),
('Manager', 'Manager111','Manager');

INSERT INTO reimbursements (amount, description, status, userID)
values 
(1000.00,'Im poor', 'Denied', 1),
(1500.00,'Please Sir', 'Pending', 1),
(500.00,'Dinner Reimbursement', 'Approved', 2);

UPDATE reimbursements SET status = 'Pending' WHERE id = 5;
SELECT * FROM reimbursements;
SELECT * FROM users;