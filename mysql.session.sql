-- CREATE TABLE manager (Username VARCHAR(250) primary key,
-- Password VARCHAR(250) not NULL,
-- recoveryKey VARCHAR(250) not NULL);

-- INSERT INTO manager (Username,Password,recoveryKey) values ('manager@123',"123",'java');
drop TABLE Employee_details;

CREATE TABLE Employee_details (
            id int primary key,
            firstName VARCHAR(250) ,
            lastName VARCHAR(250),
            gender VARCHAR(10),
            phone int,
            venue VARCHAR(250),
            date VARCHAR(250));

INSERT INTO Employee_details (id,firstName,lastName,gender,phone,venue,date) values 
   (1,'Abebe',"Getahun",'male',0901010112,'Web Developer','1/2/2022');

-- SELECT * FROM admin;

-- CREATE TABLE payroll (
--     id INT primary key,
--     firstName VARCHAR(250),
--     lastName VARCHAR(250),
--     gross decimal,
--     overTime INT
-- );
-- INSERT INTO payroll (id,firstName,lastName,gross,overTime) values 
--    (1,'Abebe',"Getahun",50000,2);

-- CREATE TABLE admin(
--     firstName VARCHAR(250),
--     lastName VARCHAR(250) ,
--     username VARCHAR(250) primary key,
--     password VARCHAR(250),
--     recoveryKey VARCHAR(50)
-- );
-- -- drop TABLE admin;
-- INSERT INTO admin (
--         firstName,
--         lastName,
--         username,
--         password,
--         recoveryKey
--     )
-- values (
--         'Ermias',
--         'Getahun',
--         'employee@123',
--         '123',
--         'ermi'
--     );
