CREATE TABLE customers (
    id NUMBER GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR2(255) NOT NULL,
    email VARCHAR2(255) NOT NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id),
    CONSTRAINT unique_email UNIQUE (email)
);