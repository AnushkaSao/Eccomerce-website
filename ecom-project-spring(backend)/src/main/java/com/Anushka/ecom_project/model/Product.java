package com.Anushka.ecom_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;//create getter setter for data :)
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.Date;
@Entity//makes a table -it is part of the hibernate
@Data//coz i dont wanna create getter setters
@AllArgsConstructor
@NoArgsConstructor


public class Product {
    @Id
    //to make it primary key
    private int id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern ="dd-MM-yyyy")
    //but can be done in react as well hehe;
    //this is a annotation from jackson--converts data into jason and vice versa
    private Date releaseDate;
    private boolean productAvailable;
    private int stockQuantity;

    private String imageName;
    private String imageType;
    @Lob
    //to keep the image persisitent as it is a large object
    private byte[] imageDate;


}
