--liquibase formatted sql
--changeset init:1
CREATE TABLE user (
    id VARCHAR(36) NOT NULL,
    login VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

--changeset init:2
CREATE TABLE item (
    id VARCHAR(36) NOT NULL,
    owner VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_item_owner FOREIGN KEY (owner) references user(id)
);