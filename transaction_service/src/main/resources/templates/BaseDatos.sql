CREATE TABLE Person
(
    person_id      NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    name           VARCHAR2(100)       NOT NULL,
    gender         CHAR(10),
    age            NUMBER,
    identification VARCHAR2(50) UNIQUE NOT NULL,
    address        VARCHAR2(200),
    phone          VARCHAR2(20)
);

CREATE TABLE Client
(
    client_id VARCHAR(19) PRIMARY KEY,
    password  VARCHAR2(100) NOT NULL,
    status    NUMBER(1),
    person_id NUMBER        NOT NULL,
    CONSTRAINT fk_person FOREIGN KEY (person_id) REFERENCES Person (person_id)
);

CREATE TABLE Account
(
    account_id      NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    account_number  VARCHAR2(20) UNIQUE NOT NULL,
    account_type    VARCHAR2(20)        NOT NULL,
    initial_balance NUMBER DEFAULT 0    NOT NULL,
    status          NUMBER(1),
    client_id       VARCHAR(19)         NOT NULL,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES Client (client_id)
);

CREATE TABLE Transaction
(
    transaction_id   NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    transaction_date DATE         NOT NULL,
    transaction_type VARCHAR2(20) NOT NULL,
    amount           NUMBER       NOT NULL,
    balance          NUMBER       NOT NULL,
    account_id       NUMBER       NOT NULL,
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES Account (account_id)
);

