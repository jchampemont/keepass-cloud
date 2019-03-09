CREATE TABLE password_database (
  id       UUID         NOT NULL,
  name     VARCHAR(64)  NOT NULL,
  created  TIMESTAMP    NOT NULL,
  modified TIMESTAMP        NULL,

  PRIMARY KEY (id)
);