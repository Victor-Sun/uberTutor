-- url="jdbc:oracle:thin:@demo.gnomontech.com:1521:orcl"
-- url="jdbc:oracle:thin:@ubertutor.cxbqqbwmdfap.us-west-2.rds.amazonaws.com:1521:orcl"
CREATE SEQUENCE SCHOOLS_SEQ;
CREATE SEQUENCE USERS_SEQ START WITH 3; 
CREATE SEQUENCE SUBJECT_CATEGORY_SEQ;
CREATE SEQUENCE SUBJECT_SEQ;
CREATE SEQUENCE USER_SUBJECT_SEQ START WITH 3;
CREATE SEQUENCE REQUEST_STATUS_SEQ;
CREATE SEQUENCE USER_REQUEST_SEQ START WITH 4;
CREATE SEQUENCE FEEDBACK_SEQ START WITH 2;

-- Schools
CREATE TABLE SCHOOLS(
	ID INT NOT NULL,
	NAME NVARCHAR2(510),
	CITY NVARCHAR2(510),
	STATE NVARCHAR2(510),
	PRIMARY KEY(ID)
);

-- User accounts
CREATE TABLE USERS(
	ID INT NOT NULL,
	UUID VARCHAR(36),
	USERNAME VARCHAR2(32),
	FULLNAME NVARCHAR2(255),
	PASSWORD VARCHAR2(255),
	SCHOOL_ID INT references SCHOOLS(ID),
	EMAIL VARCHAR2(255),
	MOBILE VARCHAR2(64),
	BIO NVARCHAR2(2000),
	CREATE_BY VARCHAR2(255),
	CREATE_DATE TIMESTAMP,
	UPDATE_BY VARCHAR2(255),
	UPDATE_DATE TIMESTAMP,
	DELETE_BY VARCHAR2(255),
	DELETE_DATE TIMESTAMP,
	IS_DISABLED CHAR(1) DEFAULT 'N',
	IS_TUTOR CHAR(1) DEFAULT 'N',
	IS_ADMIN CHAR(1) DEFAULT 'N',
	IS_VERIFIED CHAR(1) DEFAULT 'N',
	PRIMARY KEY (ID)
);

-- A subject's category
CREATE TABLE SUBJECT_CATEGORY(
	ID INT NOT NULL,
	TITLE VARCHAR2(255) NOT NULL,
	PRIMARY KEY (ID)
);

-- List of subjects
CREATE TABLE SUBJECT(
	ID INT NOT NULL,
	TITLE VARCHAR2(255),
	DESCRIPTION NVARCHAR2(2000),
	CATEGORY_ID INT references SUBJECT_CATEGORY(ID),
	PRIMARY KEY (ID)	
);

-- What subjects a user has
CREATE TABLE USER_SUBJECT(
	ID INT NOT NULL,
	USER_ID INT references USERS(ID),
	SUBJECT_ID INT references SUBJECT(ID),
	DESCRIPTION NVARCHAR2(2000),
	IS_ACTIVE CHAR(1) DEFAULT 'Y',
	CREATE_DATE TIMESTAMP,
	UPDATE_DATE TIMESTAMP,
	PRIMARY KEY (ID)
);

CREATE TABLE REQUEST_STATUS(
	ID INT,
	CODE VARCHAR2(255),
	DESCRIPTION VARCHAR2(255),
	PRIMARY KEY (CODE)
);

CREATE TABLE FEEDBACK(
	ID INT NOT NULL,
	CREATE_DATE DATE,
	RATING NUMBER(2,1),
	FEEDBACK NVARCHAR2(2000),
	PRIMARY KEY (ID)
);

CREATE TABLE USER_REQUEST(
	ID INT NOT NULL,
	USER_ID INT references USERS(ID) NOT NULL,
	TUTOR_ID INT references USERS(ID),
	SUBJECT_ID INT references SUBJECT(ID) NOT NULL,
	STATUS VARCHAR(255) DEFAULT 'OPEN' references REQUEST_STATUS(CODE),
	TITLE NVARCHAR2(255),
	DESCRIPTION NVARCHAR2(2000),
	FEEDBACK INT references FEEDBACK(ID),
	CREATE_DATE TIMESTAMP,
	PENDING_DATE TIMESTAMP,
	PROCESS_DATE TIMESTAMP,
	CLOSE_DATE TIMESTAMP,
	CANCEL_DATE TIMESTAMP,
	PRIMARY KEY (ID)
);

-- Views
CREATE OR REPLACE VIEW USERS_SUBJECT_CATEGORY AS
	SELECT USER_SUBJECT.ID AS ID, SUBJECT.ID AS SUBJECT_ID, SUBJECT.TITLE AS SUBJECT_TITLE, SUBJECT_CATEGORY.ID AS CATEGORY_ID, SUBJECT_CATEGORY.TITLE AS CATEGORY_TITLE, USER_SUBJECT.USER_ID, USER_SUBJECT.DESCRIPTION, USER_SUBJECT.CREATE_DATE, USER_SUBJECT.UPDATE_DATE
	FROM USER_SUBJECT, SUBJECT, SUBJECT_CATEGORY
	WHERE USER_SUBJECT.SUBJECT_ID = SUBJECT.ID AND SUBJECT.CATEGORY_ID = SUBJECT_CATEGORY.ID;

CREATE OR REPLACE VIEW USER_FEEDBACK AS
	SELECT USERS.FULLNAME, USERS.ID AS USER_ID, FEEDBACK.CREATE_DATE, FEEDBACK.RATING, FEEDBACK.FEEDBACK, REQUEST.FEEDBACK AS FEEDBACK_ID, REQUEST.ID AS REQUEST_ID
	FROM USERS
	INNER JOIN USER_REQUEST REQUEST
	ON REQUEST.USER_ID = USERS.ID
	INNER JOIN FEEDBACK
	ON USERS.ID = FEEDBACK.USER_ID AND REQUEST.FEEDBACK = FEEDBACK.ID;

CREATE OR REPLACE VIEW USER_SESSIONS AS 
	SELECT REQUEST.ID AS REQUEST_ID, REQUEST.CREATE_DATE, REQUEST.USER_ID AS STUDENT_ID, STUDENT.FULLNAME AS STUDENT_NAME, REQUEST.TUTOR_ID, TUTOR.FULLNAME AS TUTOR_NAME, CATEGORY.TITLE AS CATEGORY, SUBJECT.TITLE AS SUBJECT, REQUEST.STATUS AS STATUS, REQUEST.DESCRIPTION AS SUBJECT_DESCRIPTION, REQUEST.TITLE AS REQUEST_TITLE
	FROM USER_REQUEST REQUEST
	INNER JOIN USERS STUDENT
	ON REQUEST.USER_ID = STUDENT.ID
	INNER JOIN SUBJECT
	ON REQUEST.SUBJECT_ID = SUBJECT.ID
	INNER JOIN SUBJECT_CATEGORY CATEGORY
	ON SUBJECT.CATEGORY_ID = CATEGORY.ID
	LEFT OUTER JOIN USERS TUTOR
	ON REQUEST.TUTOR_ID = TUTOR.ID AND TUTOR.IS_TUTOR = 'Y';

CREATE OR REPLACE VIEW SESSION_INFO AS
	SELECT REQUEST.ID AS REQUEST_ID, CATEGORY.TITLE AS CATEGORY, SUBJECT.TITLE AS SUBJECT, STUDENT.ID AS STUDENT_ID, STUDENT.FULLNAME AS STUDENT_NAME, TUTOR.ID AS TUTOR_ID, TUTOR.FULLNAME AS TUTOR_NAME, REQUEST.TITLE, REQUEST.DESCRIPTION, REQUEST.CREATE_DATE, REQUEST.PENDING_DATE, REQUEST.PROCESS_DATE, REQUEST.CLOSE_DATE, REQUEST.CANCEL_DATE, REQUEST.DESCRIPTION AS SUBJECT_DESCRIPTION, REQUEST.STATUS, REQUEST.TITLE AS REQUEST_TITLE	
	FROM USER_REQUEST REQUEST
	INNER JOIN USERS STUDENT
	ON REQUEST.USER_ID = STUDENT.ID
	INNER JOIN SUBJECT
	ON REQUEST.SUBJECT_ID = SUBJECT.ID
	INNER JOIN SUBJECT_CATEGORY CATEGORY
	ON SUBJECT.CATEGORY_ID = CATEGORY.ID
	LEFT OUTER JOIN USERS TUTOR
	ON REQUEST.TUTOR_ID = TUTOR.ID AND TUTOR.IS_TUTOR = 'Y';

-- Table initialization
INSERT ALL
	INTO SCHOOLS (ID, NAME, CITY, STATE) VALUES ('1', 'GRAND VALLEY STATE UNIVERSITY', 'ALLENDALE', 'MI')
	INTO SCHOOLS (ID, NAME, CITY, STATE) VALUES ('2', 'UNIVERSITY OF MICHIGAN', 'ANN ARBOR', 'MI')
SELECT * FROM DUAL;

INSERT INTO USERS(ID, USERNAME, FULLNAME, PASSWORD, SCHOOL_ID, EMAIL, IS_ADMIN) 
VALUES('1', 'admin', 'admin', 'oqGiG3w2C/s4l945xI++My4Wpv2cCyLi', '1', 'email@domain.com', 'Y');
INSERT INTO USERS(ID, USERNAME, FULLNAME, PASSWORD, SCHOOL_ID, EMAIL, BIO, IS_TUTOR,IS_ADMIN) 
VALUES('2', 'tutor', 'tutor', 'oqGiG3w2C/s4l945xI++My4Wpv2cCyLi', '2', 'email@domain.com', 'This is a tutors bio. Read about all the stuff this guy can do!' , 'Y','N');

INSERT ALL
	INTO SUBJECT_CATEGORY(ID, TITLE) VALUES('1', 'Math')
	INTO SUBJECT_CATEGORY(ID, TITLE) VALUES('2', 'CS')
SELECT * FROM DUAL;

INSERT ALL
	INTO SUBJECT(ID, TITLE, DESCRIPTION, CATEGORY_ID) VALUES('1', 'Algebra', 'Algebra 1', '1')
	INTO SUBJECT(ID, TITLE, DESCRIPTION, CATEGORY_ID) VALUES('2', 'Calculus', 'Calculus', '1')
	INTO SUBJECT(ID, TITLE, DESCRIPTION, CATEGORY_ID) VALUES('3', 'Geometry', 'Geometry', '1')
	INTO SUBJECT(ID, TITLE, DESCRIPTION, CATEGORY_ID) VALUES('4', 'Java', 'Java', '2')
	INTO SUBJECT(ID, TITLE, DESCRIPTION, CATEGORY_ID) VALUES('5', 'C++', 'C++', '2')
	INTO SUBJECT(ID, TITLE, DESCRIPTION, CATEGORY_ID) VALUES('6', 'C', 'C', '2')
	INTO SUBJECT(ID, TITLE, DESCRIPTION, CATEGORY_ID) VALUES('7', 'C#', 'C#', '2')
SELECT * FROM DUAL;

INSERT ALL
	INTO USER_SUBJECT(ID, USER_ID, SUBJECT_ID, DESCRIPTION, IS_ACTIVE) VALUES('1', '2', '1', 'THIS IS A SUBJECT DESCRIPTION. IT SHOULD BE VERY LONG TO TEST OUT THE TRUNCATION ISSUE. IS THIS LONG ENOUGH? SHOULD THIS BE LONGER?', 'Y')
	INTO USER_SUBJECT(ID, USER_ID, SUBJECT_ID, DESCRIPTION, IS_ACTIVE) VALUES('2', '2', '2', 'THIS IS A SUBJECT DESCRIPTION. IT SHOULD BE VERY LONG TO TEST OUT THE TRUNCATION ISSUE. IS THIS LONG ENOUGH? SHOULD THIS BE LONGER?', 'Y')
SELECT * FROM DUAL;

INSERT ALL
	INTO REQUEST_STATUS(ID, CODE, DESCRIPTION) VALUES('1', 'OPEN', 'Request is open')
	INTO REQUEST_STATUS(ID, CODE, DESCRIPTION) VALUES('2', 'PENDING', 'Request has been looked at')
	INTO REQUEST_STATUS(ID, CODE, DESCRIPTION) VALUES('3', 'IN PROCESS', 'Request has been accepted')
	INTO REQUEST_STATUS(ID, CODE, DESCRIPTION) VALUES('4', 'CLOSED', 'Request has been completed')
	INTO REQUEST_STATUS(ID, CODE, DESCRIPTION) VALUES('5', 'CANCELED', 'Request has been canceled')
SELECT * FROM DUAL;

INSERT ALL
	INTO FEEDBACK (ID, USER_ID, TUTOR_ID, CREATE_DATE, RATING, FEEDBACK) VALUES ('1', '1', '2', SYSDATE, 5, 'This was a great tutor!')
SELECT * FROM DUAL;

INSERT ALL
	INTO USER_REQUEST (ID, USER_ID, TUTOR_ID, SUBJECT_ID, STATUS, DESCRIPTION, FEEDBACK, CREATE_DATE, CLOSE_DATE) VALUES('1', '1', '2', '1', 'CLOSED', 'Request Description', '1', SYSDATE, SYSDATE)
	INTO USER_REQUEST (ID, USER_ID, TUTOR_ID, SUBJECT_ID, STATUS, DESCRIPTION, CREATE_DATE, CLOSE_DATE) VALUES('2', '1', '2', '2', 'CLOSED', 'Request Description', SYSDATE, SYSDATE)
	INTO USER_REQUEST (ID, USER_ID, SUBJECT_ID, STATUS, DESCRIPTION, CREATE_DATE) VALUES('3', '1', '2', 'OPEN', 'Request Description', SYSDATE)
SELECT * FROM DUAL;
