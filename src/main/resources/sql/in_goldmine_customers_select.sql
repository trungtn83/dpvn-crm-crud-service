SELECT
    c.id,
    (scr_1.customer_id IS NOT NULL) AS favorite_status,
    (scr_2.customer_id IS NOT NULL) AS boom_status
FROM customer c
         LEFT JOIN sale_customer_reference scr_1
                   ON scr_1.customer_id = c.id
                       AND scr_1.sale_id = :saleId
                       AND scr_1.status = 'FAVORITE'
         LEFT JOIN sale_customer_reference scr_2
                   ON scr_2.customer_id = c.id
                       AND scr_2.status = 'BOOM'
WHERE c.active = TRUE
  AND c.deleted = FALSE
  AND c.status = 'ORE'
  AND NOT EXISTS (
    SELECT 1
    FROM sale_customer sc
    WHERE sc.customer_id = c.id
      AND sc.active = TRUE
      AND sc.deleted = FALSE
      AND ((sc.status IN ('TREASURE', 'GOLD') AND sc.available_from <= NOW() AND sc.available_to > NOW()) OR (sc.status = 'GOLD' AND sc.sale_id = :saleId))
)
    {FILTER}
ORDER BY scr_1.status NULLS LAST, c.id