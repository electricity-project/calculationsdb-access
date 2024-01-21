CREATE TABLE IF NOT EXISTS weather_api_key
(
    id          INTEGER      NOT NULL,
    api_key     VARCHAR(255) NOT NULL,
    change_date TIMESTAMP(6) NOT NULL,
    CONSTRAINT weather_api_key_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS weather_api_key_seq AS INTEGER START WITH 1 INCREMENT BY 50 MINVALUE 1 CACHE 1;

