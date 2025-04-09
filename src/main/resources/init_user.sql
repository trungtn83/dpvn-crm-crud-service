update "user" u
set department_id = (select id from department d where d.department_name = 'BOM')
where u.username = 'VJP2003001' or u.username = 'VJP2003002';

update "user" u
set role_id = (select id from "role" r where r.role_name = 'ADMIN')
where u.username = 'VJP2003001' or u.username = 'VJP2003002';

update "user" u
set role_id = (select id from "role" r where r.role_name = 'GOD')
where u.username = 'VJP2003001';


ALTER TABLE public.user_property ADD from_date timestamptz NULL;
ALTER TABLE public.user_property ADD to_date timestamptz NULL;
