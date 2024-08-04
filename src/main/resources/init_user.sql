insert into "user" (address_detail, email, full_name, mobile_phone,dob, description, password, status, username, department_id, role_id)
select
    address as address_detail,
    email as email,
    given_name as full_name,
    mobile_phone as mobile_phone,
    dob as dob,
    description as description,
    '12345' as password,
    CASE
        WHEN is_active = true THEN 1
        WHEN is_active = false THEN 0
        end as status,
    username as username,
    (select id from department where department_name = 'SALE') as department_id,
    (select id from role where role_name = 'USER') as role_id
from kv_user;

-- update role for VJP2003001 and VJP2003002 => 2
