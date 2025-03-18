CREATE INDEX idx_sale_customer_sale_id_relationship_type_customer_id ON sale_customer (sale_id, relationship_type, customer_id);

CREATE INDEX idx_sale_customer_sale_id ON sale_customer (sale_id);
CREATE INDEX idx_sale_customer_sale_id_created_date ON sale_customer (sale_id, created_date);

