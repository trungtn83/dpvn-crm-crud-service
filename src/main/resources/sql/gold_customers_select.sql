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
     c.id AS customer_id,
     g.sale_id,
     g.available_to,
     g.reason_code,
     scs.customer_category_id
 FROM customer c
          JOIN gold_sc g ON g.customer_id = c.id
          LEFT JOIN sale_customer_state scs
                    ON scs.sale_id = g.sale_id
                        AND scs.customer_id = g.customer_id
 WHERE c.active = true and c.deleted = false
     {BASE_FILTER}
),
grouped AS (
    SELECT
        customer_id,
        STRING_AGG(cast(sale_id as text), ',' ORDER BY available_to DESC, sale_id) AS sale_ids,
        STRING_AGG(COALESCE(cast(reason_code as text), ''), ',' ORDER BY available_to ASC, sale_id) as reason_codes,
        STRING_AGG(COALESCE(cast(customer_category_id as text), ''), ',' ORDER BY available_to ASC, sale_id) as category_ids,
        STRING_AGG(COALESCE(to_char(available_to AT TIME ZONE 'UTC', 'YYYY-MM-DD"T"HH24:MI:SS.MSZ'), ''), ',' ORDER BY available_to ASC, sale_id) as available_tos,
        MAX(available_to) AS sort_available_to
    FROM base
    GROUP BY customer_id
)
SELECT *
FROM grouped
ORDER BY sort_available_to asc nulls last, customer_id
