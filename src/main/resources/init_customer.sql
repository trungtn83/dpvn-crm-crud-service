INSERT INTO customer(
    customer_name,
    customer_code,
    gender,
    mobile_phone,
    email,
    address,
    level_point,
    source_id,
    source,
    source_note,
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
    kc.address as address,
    kc.reward_point as level_point,
    1 as source_id,
    'KIOTVIET' as source,
    null as source_note,
    1 as status,
    1 as customer_type_id,
    (select u.id from "user" u where u.username = (select ku.username from kv_user ku where ku.id = kc.created_by)) as created_by,
    kc.created_date,
    (select u.id from "user" u where u.username = (select ku.username from kv_user ku where ku.id = kc.modified_by)) as modified_by,
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
    tc.address as address,
    0 as level_point,
    2 as source_id,
    'CRAFTONLINE' as source,
    tc.hash_tag as source_note,
    case
        when tc.status = 'ACTIVE' then 1
        when tc.status = 'INACTIVE' then 0
        else -1
        end as status,
    case
        when tc.scope = 'PHARMACY' then 1
        when tc.scope = 'PHARMACIST' then 2
        when tc.scope = 'PATIENT' then 3
        when tc.scope = 'HOSPITAL' then 4
        when tc.scope = 'HEALTHCARE_CENTER' then 5
        when tc.scope = 'BEAUTY_SALON' then 5
        when tc.scope = 'DRUGSTORE' then 6
        when tc.scope = 'CLINIC' then 7
        when tc.scope = 'DENTISTRY' then 8
        when tc.scope = 'PHARMA_COMPANY' then 9
        else 99
        end as customer_type_id,
    (select u.id from "user" u where u.username = 'VJP0000000') as created_by,
    tc.created_date,
    (select u.id from "user" u where u.username = 'VJP0000000') as modified_by ,
    tc.modified_date
FROM
    ts_customer tc
where tc.phone is not null;
