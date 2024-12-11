package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
  List<Customer> findByIdIn(List<Long> ids);

  String FIND_CUSTOMER_BY_MOBILE_PHONE =
      """
       SELECT DISTINCT
           c.id,
           c.customer_id,
           c.created_by,
           c.created_date,
           c.modified_by,
           c.modified_date,
           c.address,
           c.address_id,
           c.customer_category_id,
           c.customer_code,
           c.customer_name,
           c.customer_type_id,
           c.email,
           c.gender,
           c.level_point,
           c.mobile_phone,
           c.notes,
           c.pin_code,
           c.relationships,
           c.source_id,
           c.source_note,
           c.special_events,
           c.status,
           c.tax_code,
           c.customer_id AS customer_unique_id,
           c.birthday
       FROM public.customer c
       LEFT JOIN public.customer_reference cr
           ON c.id = cr.customer_id
       WHERE c.mobile_phone = :mobilePhone
          OR ((cr.code = 'MOBILE_PHONE' AND cr.value = :mobilePhone) OR (cr.code = 'ZALO' AND cr.value = :mobilePhone));
      """;

  @Query(value = FIND_CUSTOMER_BY_MOBILE_PHONE, nativeQuery = true)
  List<Customer> findCustomersByMobilePhone(String mobilePhone);
}
