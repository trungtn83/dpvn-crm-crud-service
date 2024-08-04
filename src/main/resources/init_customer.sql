INSERT INTO customer(
    customer_name,
    customer_code,
    gender,
    mobile_phone,
    email,
    address_detail,
    address_id,
    description,
    tax_code,
    level_point,
    source,
    source_id,
    status,
    customer_type_id,
    created_by,
    created_date,
    modified_by,
    modified_date
)
SELECT
    kc."name" as customer_name,
    kc.code as customer_code,
    CASE
        WHEN kc.gender THEN 1
        ELSE 0
        END as gender,
    kc.contact_number as mobile_phone,
    null as email,
    kc.address as address_detail,
    CAST(NULL AS bigint) as address_id,
    null as description,
    null as tax_code,
    null as level_point,
    'KIOTVIET' as source,
    kc.id as source_id,
    1 as status,
    1 as customer_type_id,
    kc.created_by,
    kc.created_date,
    kc.modified_by,
    kc.modified_date
FROM
    kv_customer kc
where kc.contact_number is not null

UNION ALL

SELECT
    tc."name" as customer_name,
    tc.code as customer_code,
    null as gender,
    tc.phone as mobile_phone,
    tc.email as email,
    tc.address as address_detail,
    CAST(NULL AS bigint) as address_id,
    null as description,
    null as tax_code,
    tc.level_point as level_point,
    'THUOCSI' as source,
    tc.id as source_id,
    1 as status,
    454282 as created_by,
    1 as customer_type_id,
    tc.created_date,
    tc.modified_by,
    tc.modified_date
FROM
    ts_customer tc
where tc.phone is not null;
