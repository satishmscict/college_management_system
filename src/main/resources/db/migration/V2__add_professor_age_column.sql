ALTER TABLE professor ADD COLUMN age INTEGER;



ALTER TABLE professor RENAME TO professor_new

ALTER TABLE professor_new RENAME age to professor_age

ALTER TABLE professor_new RENAME professor_age String;

ALTER TABLE professor_new MODIFY COLUMN professor_age INTEGER

CREATE TABLE DEPARTMENT(
id INT PRIMARY KEY,
name VARCHAR(10),
age INT,
gender INT
)

DELETE TABLE DEPARTMENT;


ALTER TABLE table_name
RENAME TO new_table_name;
ALTER TABLE DEPARTMENT RENAME to dept;

ALTER TABLE table_name
RENAME COLUMN old_column_name TO new_column_name;
ALTER TABLE DEPARTMENT RENAME COLUMN dept_code to deptCode;

ALTER TABLE table_name
ADD column_name datatype;
ALTER TABLE DEPARTMENT ADD dept_code INT;

ALTER TABLE table_name
MODIFY COLUMN column_name new_datatype;
ALTER TABLE DEPARTMENT MODIFY COLUMN dept_code VARCHAR(10);





