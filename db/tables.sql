-- url="jdbc:oracle:thin:@demo.gnomontech.com:1521:orcl"
-- url="jdbc:oracle:thin:@ubertutor.cxbqqbwmdfap.us-west-2.rds.amazonaws.com:1521:orcl"
CREATE SEQUENCE USERS_SEQ; 
CREATE SEQUENCE USER_SUBJECT_SEQ;

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
	SCHOOLS INT references SCHOOLS(ID),
	EMAIL VARCHAR2(255),
	MOBILE VARCHAR2(64),
	CREATE_BY VARCHAR2(255),
	CREATE_DATE TIMESTAMP,
	UPDATE_BY VARCHAR2(255),
	UPDATE_DATE TIMESTAMP,
	DELETE_BY VARCHAR2(255),
	DELETE_DATE TIMESTAMP,
	RATING INT,
	IS_DISABLED CHAR(1) DEFAULT 'N',
	IS_TUTOR CHAR(1) DEFAULT 'N',
	IS_ADMIN CHAR(1) DEFAULT 'N',
	IS_VERIFIED CHAR(1) DEFAULT 'N',
  PRIMARY KEY(ID)	
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
	DESCRIPTION NVARCHAR2(255),
	STATUS VARCHAR2(255),
	FEE_OFFERED NUMBER(6,2),
	FEE_CLOSED NUMBER(6,2),
	CREATE_DATE TIMESTAMP,
	UPDATE_DATE TIMESTAMP,
	PRIMARY KEY (ID)
);

CREATE TABLE USER_REQUEST_STATS_LOG(
	ID INT NOT NULL,
	USER_REQUEST_ID INT references USER_REQUEST(ID) NOT NULL,
	TRANSACTION_TYPE VARCHAR2(255),
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
	COMMENTS VARCHAR2(510),
	PRIMARY KEY (ID)
);

-- Table initialization
INSERT INTO USERS(ID, USERNAME, FULLNAME, PASSWORD, EMAIL, IS_ADMIN) 
VALUES('1', 'ADMIN', 'FULLNAME', 'oqGiG3w2C/s4l945xI++My4Wpv2cCyLi', 'email@domain.com', 'Y');


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