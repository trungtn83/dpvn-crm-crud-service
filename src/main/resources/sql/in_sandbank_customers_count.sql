SELECT COUNT(c.id)
FROM customer c
WHERE c.active = TRUE
  AND c.deleted = FALSE
  AND c.status = 'RUN_OF_MINE'
  AND NOT EXISTS (
    SELECT 1
    FROM sale_customer sc
    WHERE sc.customer_id = c.id
      AND sc.active = TRUE
      AND sc.deleted = FALSE
      AND ((sc.status = 'TREASURE' AND sc.available_from <= NOW() AND sc.available_to > NOW()) OR sc.status = 'GOLD')
)
    {FILTER}