INSERT INTO customer_level (
    customer_level_name,
    description,
    start_date,
    end_date,
    status
) VALUES
    ('Phân cấp khách hàng quý III năm 2024', '', '2024-07-01T00:00:00Z', '2024-09-30T23:59:59Z', 1);

INSERT INTO customer_level_detail (
    customer_level_id,
    level_name,
    description,
    base_point
) VALUES
      ((select id from customer_level where customer_level_name = 'Phân cấp khách hàng quý III năm 2024'), 'Bronze', 'Basic level for new customers', 100),
      ((select id from customer_level where customer_level_name = 'Phân cấp khách hàng quý III năm 2024'), 'Silver', 'Intermediate level for regular customers', 200),
      ((select id from customer_level where customer_level_name = 'Phân cấp khách hàng quý III năm 2024'), 'Gold', 'Advanced level for loyal customers', 300),
      ((select id from customer_level where customer_level_name = 'Phân cấp khách hàng quý III năm 2024'), 'Diamond', 'Premium level for top customers', 400),
      ((select id from customer_level where customer_level_name = 'Phân cấp khách hàng quý III năm 2024'), 'GOD', 'Ultimate level for elite customers', 500);
