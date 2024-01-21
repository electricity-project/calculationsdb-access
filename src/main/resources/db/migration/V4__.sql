alter table power_production
    alter column timestamp type timestamp(6) with time zone using timestamp::timestamp(6) with time zone;

alter table days_aggregation
    alter column timestamp type timestamp(6) with time zone using timestamp::timestamp(6) with time zone;

alter table hours_aggregation
    alter column timestamp type timestamp(6) with time zone using timestamp::timestamp(6) with time zone;

alter table minutes_aggregation
    alter column timestamp type timestamp(6) with time zone using timestamp::timestamp(6) with time zone;

alter table power_station
    alter column creation_time type timestamp(6) with time zone using creation_time::timestamp(6) with time zone;