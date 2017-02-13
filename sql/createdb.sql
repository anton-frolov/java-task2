CREATE USER rttest
  WITH
    PASSWORD 'rttest'
    LOGIN NOSUPERUSER
    INHERIT NOCREATEDB
    NOCREATEROLE;

CREATE DATABASE rttest
  WITH
    OWNER = rttest
    ENCODING = 'UTF8'
    LC_COLLATE ='Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

