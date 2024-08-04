INSERT INTO campaign_type (description, campaign_type_name, status, steps)
VALUES
    ('Các bước để thu hút khách hàng thông qua email', 'Chiến dịch Email', 1, 3),
    ('Các bước để tăng nhận diện thương hiệu trên mạng xã hội', 'Chiến dịch Truyền thông Xã hội', 1, 3),
    ('Các bước để thu hút và giữ chân khách hàng bằng nội dung', 'Chiến dịch Tiếp thị Nội dung', 1, 3),
    ('Các bước để tăng lưu lượng truy cập vào trang web của bạn thông qua quảng cáo trả phí', 'PPC Campaign', 1, 3),
    ('Các bước để cải thiện sự hiển thị của trang web của bạn trong kết quả tìm kiếm', 'SEO Campaign', 1, 3),
    ('Các bước để khuyến khích khách hàng hiện tại giới thiệu khá ch hàng mới', 'Referral Campaign', 1, 3),
    ('Các bước để tạo sự phấn khích và nhận thức về sản phẩm mới', 'Product Launch Campaign', 1, 3),
    ('Các bước để kết nối lại với khách hàng không hoạt động hoặc bị mất', 'Re-engagement Campaign', 1, 3),
    ('Các bước để tăng sự nhận diện và ghi nhớ thương hiệu của bạn', 'Brand Awareness Campaign', 1, 3),
    ('Các bước để tận dụng các xu hướng và ngày lễ theo mùa', 'Seasonal Campaign', 1, 3),
    ('Các bước để quảng bá sự kiện để tăng sự tham gia và tương tác', 'Event Marketing Campaign', 1, 3),
    ('Các bước để tận dụng những người có ảnh hưởng để tiếp cận đối tượng rộng hơn', 'Influencer Marketing Campaign', 1, 3),
    ('Các bước để giữ khách hàng hiện tại tham gia và giảm thiểu việc mất khách hàng', 'Customer Retention Campaign', 1, 3),
    ('Các bước để tăng doanh số ngắn hạn thông qua các khuyến mại đặc biệt', 'Sales Promotion Campaign', 1, 3),
    ('Các bước để quản lý danh tiếng công ty của bạn và xây dựng mối quan hệ tích cực với công chúng', 'Public Relations Campaign', 1, 3),
    ('Các bước để tạo tiếng vang thông qua các chiến thuật tiếp thị sáng tạo và không thông thường', 'Guerilla Marketing Campaign', 1, 3);
INSERT INTO campaign_type_step (campaign_type_id, completion_percentage, description, step_name, step_order)
VALUES
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Chiến dịch Email'), 33, 'Gửi email đầu tiên giới thiệu về chiến dịch', 'Email ban đầu', 1),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Chiến dịch Email'), 66, 'Gửi email tiếp theo với thêm chi tiết hoặc lời nhắc', 'Email tiếp theo', 2),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Chiến dịch Email'), 100, 'Gửi email cuối cùng để thúc đẩy hành động ngay lập tức', 'Email cuối cùng', 3);
INSERT INTO campaign_type_step (campaign_type_id, completion_percentage, description, step_name, step_order)
VALUES
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Chiến dịch Truyền thông Xã hội'), 33, 'Đăng bài thông báo ban đầu về chiến dịch', 'Đăng thông báo', 1),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Chiến dịch Truyền thông Xã hội'), 66, 'Phản hồi bình luận và tương tác với người theo dõi', 'Tương tác với người theo dõi', 2),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Chiến dịch Truyền thông Xã hội'), 100, 'Sử dụng quảng cáo trả phí để tăng phạm vi tiếp cận của bài đăng', 'Quảng cáo bài đăng', 3);
INSERT INTO campaign_type_step (campaign_type_id, completion_percentage, description, step_name, step_order)
VALUES
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Chiến dịch Tiếp thị Nội dung'), 33, 'Phát triển và xuất bản nội dung ban đầu', 'Tạo nội dung', 1),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Chiến dịch Tiếp thị Nội dung'), 66, 'Chia sẻ nội dung trên các kênh khác nhau', 'Quảng bá nội dung', 2),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Chiến dịch Tiếp thị Nội dung'), 100, 'Theo dõi và phân tích hiệu suất của nội dung', 'Phân tích hiệu suất', 3);
INSERT INTO campaign_type_step (campaign_type_id, completion_percentage, description, step_name, step_order)
VALUES
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'PPC Campaign'), 33, 'Tạo và cấu hình quảng cáo PPC', 'Thiết lập quảng cáo', 1),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'PPC Campaign'), 66, 'Khởi chạy chiến dịch PPC', 'Khởi chạy chiến dịch', 2),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'PPC Campaign'), 100, 'Theo dõi và tối ưu hóa hiệu suất của quảng cáo', 'Theo dõi hiệu suất', 3);
INSERT INTO campaign_type_step (campaign_type_id, completion_percentage, description, step_name, step_order)
VALUES
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'SEO Campaign'), 33, 'Xác định các từ khóa phù hợp để nhắm mục tiêu', 'Nghiên cứu từ khóa', 1),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'SEO Campaign'), 66, 'Tối ưu hóa nội dung và cấu trúc trang web', 'Tối ưu hóa On-Page', 2),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'SEO Campaign'), 100, 'Xây dựng liên kết để cải thiện thẩm quyền', 'Xây dựng liên kết', 3);
INSERT INTO campaign_type_step (campaign_type_id, completion_percentage, description, step_name, step_order)
VALUES
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Referral Campaign'), 33, 'Thiết lập các ưu đãi và phần thưởng cho chương trình giới thiệu', 'Tạo chương trình giới thiệu', 1),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Referral Campaign'), 66, 'Thông báo cho khách hàng hiện tại về chương trình giới thiệu', 'Khuyến khích khách hàng hiện tại', 2),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Referral Campaign'), 100, 'Theo dõi các lượt giới thiệu và trao thưởng', 'Theo dõi giới thiệu', 3);
INSERT INTO campaign_type_step (campaign_type_id, completion_percentage, description, step_name, step_order)
VALUES
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Product Launch Campaign'), 33, 'Chuẩn bị tài liệu và chiến lược ra mắt sản phẩm', 'Chuẩn bị ra mắt sản phẩm', 1),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Product Launch Campaign'), 66, 'Thông báo ra mắt sản phẩm mới đến khách hàng và công chúng', 'Thông báo sản phẩm', 2),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Product Launch Campaign'), 100, 'Theo dõi hiệu suất của sản phẩm mới sau khi ra mắt', 'Phân tích hiệu suất', 3);
INSERT INTO campaign_type_step (campaign_type_id, completion_percentage, description, step_name, step_order)
VALUES
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Re-engagement Campaign'), 33, 'Phân tích lý do khách hàng không còn hoạt động', 'Phân tích lý do', 1),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Re-engagement Campaign'), 66, 'Thiết kế các chiến dịch để tái kích hoạt khách hàng', 'Thiết kế chiến dịch', 2),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Re-engagement Campaign'), 100, 'Triển khai và theo dõi kết quả', 'Triển khai và theo dõi', 3);
INSERT INTO campaign_type_step (campaign_type_id, completion_percentage, description, step_name, step_order)
VALUES
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Brand Awareness Campaign'), 33, 'Xác định các kênh truyền thông phù hợp', 'Xác định kênh truyền thông', 1),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Brand Awareness Campaign'), 66, 'Tạo nội dung và thông điệp thương hiệu', 'Tạo nội dung và thông điệp', 2),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Brand Awareness Campaign'), 100, 'Phân phối nội dung và theo dõi hiệu suất', 'Phân phối nội dung và theo dõi', 3);
INSERT INTO campaign_type_step (campaign_type_id, completion_percentage, description, step_name, step_order)
VALUES
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Seasonal Campaign'), 33, 'Phân tích xu hướng mùa và ngày lễ', 'Phân tích xu hướng', 1),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Seasonal Campaign'), 66, 'Thiết kế các chương trình khuyến mại và sự kiện', 'Thiết kế chương trình', 2),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Seasonal Campaign'), 100, 'Thực hiện và đánh giá kết quả', 'Thực hiện và đánh giá', 3);
INSERT INTO campaign_type_step (campaign_type_id, completion_percentage, description, step_name, step_order)
VALUES
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Event Marketing Campaign'), 33, 'Lập kế hoạch và chuẩn bị cho sự kiện', 'Lập kế hoạch', 1),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Event Marketing Campaign'), 66, 'Quảng bá sự kiện đến khán giả mục tiêu', 'Quảng bá sự kiện', 2),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Event Marketing Campaign'), 100, 'Tổ chức sự kiện và theo dõi hiệu quả', 'Tổ chức sự kiện', 3);
INSERT INTO campaign_type_step (campaign_type_id, completion_percentage, description, step_name, step_order)
VALUES
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Influencer Marketing Campaign'), 33, 'Xác định và liên hệ với những người có ảnh hưởng phù hợp', 'Xác định và liên hệ', 1),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Influencer Marketing Campaign'), 66, 'Thỏa thuận và lên kế hoạch hợp tác', 'Thỏa thuận và lên kế hoạch', 2),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Influencer Marketing Campaign'), 100, 'Triển khai chiến dịch và đánh giá kết quả', 'Triển khai chiến dịch', 3);
INSERT INTO campaign_type_step (campaign_type_id, completion_percentage, description, step_name, step_order)
VALUES
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Customer Retention Campaign'), 33, 'Xác định và phân tích lý do mất khách hàng', 'Xác định và phân tích', 1),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Customer Retention Campaign'), 66, 'Thiết kế các chiến dịch giữ chân khách hàng', 'Thiết kế chiến dịch', 2),
    ((SELECT id FROM campaign_type WHERE campaign_type_name = 'Customer Retention Campaign'), 100, 'Thực hiện và theo dõi hiệu quả', 'Thực hiện và theo dõi hiệu quả của chiến dịch',3);

