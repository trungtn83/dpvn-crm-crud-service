SELECT COUNT(c.id)
FROM customer c
WHERE c.active = TRUE
  AND c.deleted = FALSE
  AND c.status = 'RUN_OF_MINE'
    {FILTER}