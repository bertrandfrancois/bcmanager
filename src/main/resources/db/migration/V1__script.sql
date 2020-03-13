CREATE TABLE CLIENTS
(
    CLIENT_ID    SERIAL PRIMARY KEY,
    LAST_NAME    VARCHAR(60) NOT NULL,
    FIRST_NAME   VARCHAR(30),
    STREET       VARCHAR(50) NOT NULL,
    POST_CODE    VARCHAR(5)  NOT NULL,
    CITY         VARCHAR(30) NOT NULL,
    PHONE_NUMBER VARCHAR(30),
    MAIL         VARCHAR(60),
    TAX_NUMBER   VARCHAR(30)
);


CREATE TABLE USERS
(
    USERNAME VARCHAR(20) PRIMARY KEY NOT NULL,
    PASSWORD VARCHAR(60)             NOT NULL,
    ENABLED  BOOLEAN                 NOT NULL
);

create table AUTHORITIES
(
    USERNAME  VARCHAR(20) REFERENCES USERS (USERNAME) ON DELETE CASCADE,
    AUTHORITY VARCHAR(20) NOT NULL
);

insert into USERS
values ('admin', '$2a$10$lqa.0P8bsTPgbWLMYGc16O9C/q//yGbXuWKibgFBMzmY9s3ZrOaWq', true);
insert into AUTHORITIES
values ('admin', 'ADMIN');

CREATE TABLE PROJECTS
(
    PROJECT_ID  SERIAL PRIMARY KEY,
    DESCRIPTION VARCHAR(50),
    STREET      VARCHAR(30),
    POST_CODE   VARCHAR(5),
    CITY        VARCHAR(30),
    CLIENT_ID   INTEGER references CLIENTS (CLIENT_ID) ON DELETE CASCADE
);

CREATE TABLE DOCUMENTS
(
    DOCUMENT_ID        SERIAL PRIMARY KEY,
    DOCUMENT_CODE      VARCHAR(10) NOT NULL,
    TYPE               VARCHAR(15) NOT NULL,
    CREATION_DATE      DATE        NOT NULL,
    PAYMENT_DATE       DATE,
    STATUS             VARCHAR(15),
    TAX_RATE           VARCHAR(5)  NOT NULL,
    CLIENT_ID          INTEGER references CLIENTS (CLIENT_ID) ON DELETE CASCADE,
    PROJECT_ID         INTEGER references PROJECTS (PROJECT_ID) ON DELETE CASCADE,
    LINKED_DOCUMENT_ID INTEGER     references DOCUMENTS (DOCUMENT_ID) ON DELETE SET NULL
);

CREATE TABLE DOCUMENT_LINES
(
    DOCUMENT_LINE_ID SERIAL PRIMARY KEY,
    DESCRIPTION      VARCHAR(200)   NOT NULL,
    PRICE            DECIMAL(10, 2) NOT NULL,
    QUANTITY         DECIMAL(10, 3) NOT NULL,
    UNIT             VARCHAR(5)     NOT NULL,
    DOCUMENT_ID      INTEGER references DOCUMENTS (DOCUMENT_ID) ON DELETE CASCADE
);

CREATE TABLE ENTERPRISE
(
    ID            SERIAL PRIMARY KEY,
    NAME          VARCHAR(50) NOT NULL,
    STREET        VARCHAR(50) NOT NULL,
    POST_CODE     VARCHAR(5)  NOT NULL,
    CITY          VARCHAR(30) NOT NULL,
    PHONE_NUMBER  VARCHAR(30) NOT NULL,
    MAIL          VARCHAR(60) NOT NULL,
    TAX_NUMBER    VARCHAR(30) NOT NULL,
    BIC           VARCHAR(30) NOT NULL,
    IBAN          VARCHAR(30) NOT NULL,
    REGISTER_DATE DATE        NOT NULL
)