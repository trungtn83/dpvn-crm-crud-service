
INSERT INTO sale_customer_category (active, deleted, sale_id, name, code, description)
SELECT
    true as active,
    false as deleted,
    u.id AS sale_id,
    name,
    code,
    name AS description
FROM "user" u
         CROSS JOIN (
    VALUES
        ('Đã chặn tin nhắn', 'da_chan_tin_nhan'),
        ('Đã chốt', 'da_chot'),
        ('Đã đồng ý', 'da_dong_y'),
        ('Đã gửi kết bạn', 'da_gui_ket_ban'),
        ('Đã liên hệ', 'da_lien_he'),
        ('Đang chăm', 'dang_cham'),
        ('Đang chốt', 'dang_chot'),
        ('Kết bạn sau', 'ket_ban_sau'),
        ('Liên hệ lại', 'lien_he_lai'),
        ('Quan tâm', 'quan_tam'),
        ('Thất bại', 'that_bai'),
        ('Từ chối', 'tu_choi')
) AS categories(name, code)
WHERE u.active = TRUE;