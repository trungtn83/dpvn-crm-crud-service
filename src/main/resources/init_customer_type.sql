insert into customer_type (active, created_date, modified_date, type_code, type_name, type_references)
values
    (true, now(), now(), 'PHARMACY', 'Nhà thuốc', 'PHARMACY,DRUGSTORE'),
    (true, now(), now(), 'PHARMACIST', 'Dược sĩ', 'PHARMACIST,DENTISTRY'),
    (true, now(), now(), 'PATIENT', 'Khách lẻ', 'PATIENT,RETAIL'),
    (true, now(), now(), 'HOSPITAL', 'Bệnh viện', 'HOSPITAL'),
    (true, now(), now(), 'HEALTHCARE_CENTER', 'Salon làm đẹp', 'HEALTHCARE_CENTER,HEALTzH_CENTER,BEAUTY_SALON'),
    (true, now(), now(), 'CLINIC', 'Phòng khám', 'CLINIC'),
    (true, now(), now(), 'COMPANY', 'Công ty', 'COMPANY,PHARMA_COMPANY'),
    (true, now(), now(), 'OTHER', 'Khác', 'OTHER');