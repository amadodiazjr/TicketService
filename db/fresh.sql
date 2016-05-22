create database if not exists `walmart`;

use walmart;

-- Turn this key off so we will not run into constraint issues when re-creating tables
SET foreign_key_checks = 0;

source create-tables.sql;

-- Turn this key on to leave it at default setting
SET foreign_key_checks = 1;

SHOW TABLES;
