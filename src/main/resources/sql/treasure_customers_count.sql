SELECT COUNT(DISTINCT c.id)
FROM sale_customer sc
         JOIN customer c
              ON sc.customer_id = c.id
WHERE c.active = TRUE
  AND c.deleted = FALSE
  AND sc.active = TRUE
  AND sc.deleted = FALSE
  AND sc.status = 'TREASURE'
  AND sc.available_from <= NOW()
  AND sc.available_to > NOW()
  {FILTER}