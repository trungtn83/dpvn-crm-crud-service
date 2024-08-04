insert into address (ward_code, ward_name, district_code, district_name, province_code, province_name, region_code, region_name, fee_ratio, "level", status)
select
    code as ward_code,
    name as ward_name,
    district_code,
    district_name,
    province_code,
    province_name,
    region_code,
    region_name,
    fee_province_value as fee_ratio,
    level,
    1 as status
from ts_address ;
