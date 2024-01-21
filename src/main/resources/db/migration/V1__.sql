CREATE TABLE IF NOT EXISTS power_production
(
    id                 BIGINT                      NOT NULL,
    power_station_ipv6 VARCHAR(255)                NOT NULL,
    produced_power     FLOAT4                      NOT NULL,
    state              VARCHAR(255)                NOT NULL,
    timestamp          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT power_production_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS power_station
(
    id            BIGINT                      NOT NULL,
    creation_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    ipv6address   VARCHAR(39)                 NOT NULL,
    is_connected  BOOLEAN                     NOT NULL,
    max_power     FLOAT8                      NOT NULL,
    state         VARCHAR(255)                NOT NULL,
    CONSTRAINT power_station_pkey PRIMARY KEY (id)
);

ALTER TABLE power_station
    ADD CONSTRAINT uk_kilksu1gdyegoypq5xpv31f26 UNIQUE (ipv6address);

CREATE SEQUENCE IF NOT EXISTS minutes_aggregation_seq AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE SEQUENCE IF NOT EXISTS power_production_aggregation_seq AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE SEQUENCE IF NOT EXISTS power_production_seq AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE SEQUENCE IF NOT EXISTS power_station_seq AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE IF NOT EXISTS days_aggregation
(
    id                        BIGINT                      NOT NULL,
    aggregated_power_stations BIGINT                      NOT NULL,
    aggregation_value         BIGINT                      NOT NULL,
    timestamp                 TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT days_aggregation_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS hours_aggregation
(
    id                        BIGINT                      NOT NULL,
    aggregated_power_stations BIGINT                      NOT NULL,
    aggregation_value         BIGINT                      NOT NULL,
    timestamp                 TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT hours_aggregation_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS minutes_aggregation
(
    id                        BIGINT                      NOT NULL,
    aggregated_power_stations BIGINT                      NOT NULL,
    aggregation_value         BIGINT                      NOT NULL,
    timestamp                 TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT minutes_aggregation_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS solar_panel
(
    optimal_temperature FLOAT8 NOT NULL,
    id                  BIGINT NOT NULL,
    CONSTRAINT solar_panel_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS wind_turbine
(
    blade_length BIGINT NOT NULL,
    id           BIGINT NOT NULL,
    CONSTRAINT wind_turbine_pkey PRIMARY KEY (id)
);

ALTER TABLE solar_panel
    ADD CONSTRAINT fkig1s8pd4qn5fv9uluoicijlps FOREIGN KEY (id) REFERENCES power_station (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE wind_turbine
    ADD CONSTRAINT fkpj0kkmyva4n3yd3targwvhg5v FOREIGN KEY (id) REFERENCES power_station (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

DROP TRIGGER IF EXISTS trigger_aggregation_insert on power_production;

CREATE OR REPLACE FUNCTION count_aggregated_values()
    RETURNS TRIGGER AS
$$
DECLARE

BEGIN
    IF EXISTS(SELECT * FROM minutes_aggregation WHERE timestamp = date_trunc('minute', new.timestamp)) THEN
        UPDATE minutes_aggregation
        SET aggregated_power_stations = aggregated_power_stations + 1,
            aggregation_value         = aggregation_value + new.produced_power
        WHERE timestamp = date_trunc('minute', new.timestamp);
    else
        INSERT INTO minutes_aggregation
        VALUES (nextval('power_production_seq'), 1, new.produced_power, date_trunc('minute', new.timestamp));
    end if;

    IF EXISTS(SELECT * FROM hours_aggregation WHERE timestamp = date_trunc('hour', new.timestamp)) THEN
        UPDATE hours_aggregation
        SET aggregated_power_stations = aggregated_power_stations + 1,
            aggregation_value         = aggregation_value + new.produced_power
        WHERE timestamp = date_trunc('hour', new.timestamp);
    else
        INSERT INTO hours_aggregation
        VALUES (nextval('power_production_seq'), 1, new.produced_power, date_trunc('hour', new.timestamp));
    end if;

    IF EXISTS(SELECT * FROM days_aggregation WHERE timestamp = date_trunc('day', new.timestamp)) THEN
        UPDATE days_aggregation
        SET aggregated_power_stations = aggregated_power_stations + 1,
            aggregation_value         = aggregation_value + new.produced_power
        WHERE timestamp = date_trunc('day', new.timestamp);
    else
        INSERT INTO days_aggregation
        VALUES (nextval('power_production_seq'), 1, new.produced_power, date_trunc('day', new.timestamp));
    end if;

    RETURN null;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_aggregation_insert
    AFTER INSERT
    ON power_production
    FOR EACH ROW
EXECUTE FUNCTION count_aggregated_values();