package com.spring.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Customer {
    @Id
    private String modelId;
    private String firstName;
    private String lastName;

    @TextIndexed
    private String content;

    @TextIndexed
    private String tags;

    private Long age;

    private Address address;

    public Customer(String firstName,String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}


