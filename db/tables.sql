-- url="jdbc:oracle:thin:@demo.gnomontech.com:1521:orcl"
-- url="jdbc:oracle:thin:@ubertutor.cxbqqbwmdfap.us-west-2.rds.amazonaws.com:1521:orcl"
CREATE SEQUENCE SCHOOLS_SEQ;
CREATE SEQUENCE USERS_SEQ; 
CREATE SEQUENCE SUBJECT_CATEGORY_SEQ;
CREATE SEQUENCE SUBJECT_SEQ;
CREATE SEQUENCE USER_SUBJECT_SEQ;
CREATE SEQUENCE REQUEST_STATUS_SEQ;
CREATE SEQUENCE USER_REQUEST_SEQ;
CREATE SEQUENCE USER_REQUEST_STATS_LOG_SEQ;
CREATE SEQUENCE FEEDBACK_SEQ;

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
	DESCRIPTION VARCHAR2(255),
	CATEGORY_ID INT references SUBJECT_CATEGORY(ID),
	PRIMARY KEY (ID)	
);

-- What subjects a user has
CREATE TABLE USER_SUBJECT(
	ID INT NOT NULL,
	USER_ID INT references USERS(ID),
	SUBJECT_ID INT references SUBJECT(ID),	
	PRIMARY KEY (ID)
);

CREATE TABLE REQUEST_STATUS(
	ID INT NOT NULL,
	CODE VARCHAR2(255),
	DESCRIPTION VARCHAR2(255),
	PRIMARY KEY (ID)
);

CREATE TABLE USER_REQUEST(
	ID INT NOT NULL,
	USER_ID INT references USERS(ID) NOT NULL,
	TUTOR_ID INT references USERS(ID) NOT NULL,
	SUBJECT_ID INT references SUBJECT(ID) NOT NULL,
	TITLE NVARCHAR2(255),
	DESCRIPTION NVARCHAR2(510),
	FEE_OFFERED NUMBER(6,2),
	FEE_CLOSED NUMBER(6,2),
	CREATE_DATE TIMESTAMP,
	UPDATE_DATE TIMESTAMP,
	PRIMARY KEY (ID)
);

CREATE TABLE USER_REQUEST_STATS_LOG(
	ID INT NOT NULL,
	USER_REQUEST_ID INT references USER_REQUEST(ID) NOT NULL,
	TRANSACTION_STATUS VARCHAR2(255),
	TRANSACTION_DATE TIMESTAMP,
	PRIMARY KEY (ID)
);

CREATE TABLE FEEDBACK(
	ID INT NOT NULL,
	USER_ID INT references USERS(ID),
	TUTOR_ID INT references USERS(ID),
	REQUEST_ID INT references USER_REQUEST(ID),
	CREATE_DATE DATE,
	RATING NUMBER(2,1),
	COMMENTS NVARCHAR2(510),
	PRIMARY KEY (ID)
);

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
	INTO SUBJECT(ID, TITLE, DESCRIPTION, CATEGORY_ID) VALUES('2', 'Java', 'Java', '2')
SELECT * FROM DUAL;

INSERT ALL
	INTO USER_SUBJECT(ID, USER_ID, SUBJECT_ID) VALUES('1', '2', '1')
	INTO USER_SUBJECT(ID, USER_ID, SUBJECT_ID) VALUES('2', '2', '2')
SELECT * FROM DUAL;

INSERT ALL
	INTO REQUEST_STATUS(ID, CODE, DESCRIPTION) VALUES('1', 'OPEN', 'Request is open')
	INTO REQUEST_STATUS(ID, CODE, DESCRIPTION) VALUES('2', 'PENDING', 'Request has been looked at')
	INTO REQUEST_STATUS(ID, CODE, DESCRIPTION) VALUES('3', 'IN PROCESS', 'Request has been accepted')
	INTO REQUEST_STATUS(ID, CODE, DESCRIPTION) VALUES('4', 'COMPLETED', 'Request has been completed')
	INTO REQUEST_STATUS(ID, CODE, DESCRIPTION) VALUES('5', 'CANCELLED', 'Request has been cancelled')
SELECT * FROM DUAL;

INSERT ALL
	INTO USER_REQUEST (ID, USER_ID,TUTOR_ID, SUBJECT_ID, DESCRIPTION, CREATE_DATE, UPDATE_DATE) VALUES('1', '1', '2', '1', 'Request Description', SYSDATE, SYSDATE)
	INTO USER_REQUEST (ID, USER_ID,TUTOR_ID, SUBJECT_ID, DESCRIPTION, CREATE_DATE, UPDATE_DATE) VALUES('2', '1', '2', '2', 'Request Description', SYSDATE, SYSDATE)
SELECT * FROM DUAL;

INSERT ALL
	INTO USER_REQUEST_STATS_LOG (ID, USER_REQUEST_ID, TRANSACTION_STATUS, TRANSACTION_DATE) VALUES ('1', '1', 'COMPLETED', SYSDATE)
	INTO USER_REQUEST_STATS_LOG (ID, USER_REQUEST_ID, TRANSACTION_STATUS, TRANSACTION_DATE) VALUES ('2', '2', 'COMPLETED', SYSDATE)
SELECT * FROM DUAL;

INSERT ALL
	INTO FEEDBACK (ID, USER_ID, TUTOR_ID, REQUEST_ID, CREATE_DATE, RATING, COMMENTS) VALUES ('1', '1', '2', '1', SYSDATE, 5, 'This was a great tutor!')
	INTO FEEDBACK (ID, USER_ID, TUTOR_ID, REQUEST_ID, CREATE_DATE, RATING, COMMENTS) VALUES ('2', '1', '2', '2', SYSDATE, 4, 'This was a great tutor! Not!')
SELECT * FROM DUAL;

-- Comments
COMMENT ON COLUMN "USERS"."ID" IS 'ID';
COMMENT ON COLUMN "USERS"."UUID" IS 'UUID';
COMMENT ON COLUMN "USERS"."USERNAME" IS 'Users ID';
COMMENT ON COLUMN "USERS"."FULLNAME" IS 'Users Name';
COMMENT ON COLUMN "USERS"."PASSWORD" IS 'Password(Encrypted)';
COMMENT ON COLUMN "USERS"."EMAIL" IS 'Email';
COMMENT ON COLUMN "USERS"."MOBILE" IS 'Mobile No';
COMMENT ON COLUMN "USERS"."CREATE_BY" IS 'Created By';
COMMENT ON COLUMN "USERS"."CREATE_DATE" IS 'Date Created';
COMMENT ON COLUMN "USERS"."UPDATE_BY" IS 'Updated By';
COMMENT ON COLUMN "USERS"."UPDATE_DATE" IS 'Date Updated';
COMMENT ON COLUMN "USERS"."DELETE_BY" IS 'Deleted By';
COMMENT ON COLUMN "USERS"."DELETE_DATE" IS 'Date Deleted';
COMMENT ON COLUMN "USERS"."IS_DISABLED" IS 'Disabled';
COMMENT ON COLUMN "USERS"."IS_TUTOR" IS 'Tutor Status';
COMMENT ON COLUMN "USERS"."IS_VERIFIED" IS 'Verification Status';
COMMENT ON TABLE "USERS"  IS 'User';