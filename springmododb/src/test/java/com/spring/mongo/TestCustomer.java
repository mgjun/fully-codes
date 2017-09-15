package com.spring.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.spring.mongo.model.Address;
import com.spring.mongo.model.Customer;
import com.spring.mongo.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCustomer {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MongoOperations mongoOperations;

    private Customer dave;
    private Customer oliver;
    private Customer carter;

    @Before
    public void setUp() {
        customerRepository.deleteAll();
        for (int i=0;i<10;i++) {
            Customer customer = new Customer();
            customer.setModelId("000"+i);
            customer.setFirstName("name"+i);
            customer.setLastName("lName"+i);
            customer.setAge(Long.valueOf(1+i));
            Address address = new Address();
            address.setStreet("街道"+i);
            address.setZipCode("45672"+i);
            address.setLocation(new Point(10.22,4.33));
            customer.setAddress(address);
            customerRepository.save(customer);
        }
    }
    @Test
    public void testSave() {
        Customer customer = new Customer("customer1","lastName");
        customer.setModelId("1234");
        Address address = new Address();
        address.setStreet("成都市");
        customer.setAddress(address);
        Customer dave = customerRepository.save(customer);
        assertThat(dave.getModelId(), is(notNullValue()));
    }

    @Test
    public void testQuery() {
        List<Customer> customerList = customerRepository.findByQuery("name1","lName2");
        assertThat(customerList,is(notNullValue()));
    }

    @Test
    public void testOr() {
        List<Customer> byOr = customerRepository.findByOr("name1", "lName3");
        System.out.println(byOr);
    }

    @Test
    public void testLt() {
        List<Customer> customerList = customerRepository.findByLt(5L);
        System.out.println(customerList);
    }

    @Test
    public void testByAddress() {
        List<Customer> customerList = customerRepository.findByAddressE("456720");
        System.out.println(customerList);
    }

    @Test
    public void testFields() {
        List<Customer> customerList = customerRepository.findByFields("name1");
        assertThat(customerList,is(notNullValue()));
    }

    @Test
    public void testPage() {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching("name");
        Page<Customer> page = customerRepository.findAllBy(criteria, new PageRequest(1, 1, null));
        System.out.println(page);
    }

    @Test
    public void testCriteria() {
//        DBObject obj = new BasicDBObject();
//        obj.put("firstName","name1");
//        obj.put("lastName","lName1");
//        Query query = new BasicQuery(obj);
//        customerRepository.findOne()
        Criteria criteria = new Criteria()
                .orOperator(Criteria.where("firstName").is("name2").and("lastName").is("lName2"),
                Criteria.where("lastName").is("lName1"));
        Page<Customer> customerPage = customerRepository.findAllBy(criteria,new PageRequest(0,2));
        System.out.println(customerPage.getContent());
    }
}
