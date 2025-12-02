WITH gold_sc AS (
    SELECT
        sc.customer_id,
        sc.sale_id,
        sc.available_to,
        sc.reason_code
    FROM sale_customer sc
    WHERE sc.active = true and sc.deleted = false and sc.status = 'GOLD'
      AND NOT EXISTS (
        SELECT 1
        FROM sale_customer sc2
        WHERE sc2.customer_id = sc.customer_id
          AND sc2.active = true
          AND sc2.deleted = false
          AND sc2.status = 'TREASURE'
          AND (sc2.available_from IS NULL OR sc2.available_from <= NOW())
          AND (sc2.available_to IS NULL OR sc2.available_to > NOW())
    )
      and (sc.available_from is null or sc.available_from <= NOW())
        and (sc.available_to is null or sc.available_to > NOW())
        {GOLD_FILTER}
),
base AS (
 SELECT
     c.id
 FROM customer c
          JOIN gold_sc g ON g.customer_id = c.id
          LEFT JOIN sale_customer_state scs
                    ON scs.sale_id = g.sale_id
                        AND scs.customer_id = g.customer_id
 WHERE c.active = true and c.deleted = false
     {BASE_FILTER}
)
SELECT COUNT(DISTINCT id) AS total
FROM base
