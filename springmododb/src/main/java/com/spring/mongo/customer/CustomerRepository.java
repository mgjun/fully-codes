package com.spring.mongo.customer;

import com.spring.mongo.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,String> {

//    List<Customer> findByLastName(String lastName, Sort sort);
//
//    GeoResults<Customer> findByAddressLocationNear(Point point, Distance distance);
//
//    Customer save(Customer customer);

    @Query("{'firstName':?#{[0]},'lastName':?#{[1]}}")
    List<Customer> findByQuery(String firstName,String lastName);

    @Query("{$or:[{'firstName':?#{[0]}},{'lastName':?#{[1]}}]}")
    List<Customer> findByOr(String firstName,String lastName);


    /**
     * $lt <
     * $lte <=
     * $gt >
     * $gte >=
     * @param age
     * @return
     */
    @Query("{'age':{$lt:?#{[0]}}}")
    List<Customer> findByLt(Long age);

    /**
     * 关联查询
     * @param zipCode
     * @return
     */
    @Query("{'address.zipCode':?#{[0]}}")
    List<Customer> findByAddressE(String zipCode);


    @Query(value = "{'firstName':?0}",fields = "{'firstName':1,'lastName':1}")
    List<Customer> findByFields(String firstName);

    Page<Customer> findAllBy(TextCriteria criteria, Pageable pageable);

    List<Customer> findAllBy(TextCriteria criteria);

    List<Customer> findAllBy(Criteria criteria);

    Page<Customer> findAllBy(Criteria criteria,Pageable pageable);


}
