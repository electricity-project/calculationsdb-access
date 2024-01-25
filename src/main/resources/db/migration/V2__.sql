DROP TRIGGER IF EXISTS trigger_is_power_station_connected on power_production;

CREATE OR REPLACE FUNCTION check_if_power_station_is_connected()
    RETURNS TRIGGER AS
$$
DECLARE

BEGIN
    IF NOT EXISTS(SELECT FROM power_station WHERE ipv6address = new.power_station_ipv6 AND is_connected = true) THEN
        RETURN NULL;
    end if;

    UPDATE power_station SET state = new.state WHERE ipv6address = new.power_station_ipv6;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trigger_is_power_station_connected
    BEFORE INSERT
    ON power_production
    FOR EACH ROW
EXECUTE FUNCTION check_if_power_station_is_connected();