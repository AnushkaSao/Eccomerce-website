package com.Anushka.ecom_project.controller;

import com.Anushka.ecom_project.model.Product;
import com.Anushka.ecom_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
//spring is working on the servelet api
//servlet is a server side program that handles the client side requests
//spring is build upon the servelet api for better developing enviroment
@RestController
//Combination of @Controller and @ResponseBody:
@CrossOrigin
//to connect different port numbers of react and spring

@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService service;
    @RequestMapping("/")
    public String greet(){
        return "this is Anushka's world";
    }
    @GetMapping("/products")
    //make ur own status codes and other methods as well using response entity class
    //hence changing List<Products> to ResponseEntity<> its object
    //response entity<> wraps the return object with a status code as it is a response
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){//use of @pathVariable here coz {id} correspond to a dyanamic uri
        Product product=service.getProductById(id);
        if(product!=null)
         return new ResponseEntity<>(service.getProductById(id),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        //? kyuki we dont care or dont know the incoming request type
        //@RequestPart not RequestBody here coz request coming in parts
        //multipart is a type we use to send or recieve attachments,files etc
        try {
            Product product1=service.addProduct(product,imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);

        }
        catch (Exception e){
            //e.getMessage will print the description of the error msg
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //image not saved with the product react get it from the fetch method
    //u need to give that link to the fetch method
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[] >getImageByProductId(@PathVariable int productId){
        //product ghe ani tyacha link jodun de
        Product product=service.getProductById(productId);
        byte[] imageFile = product.getImageDate();

        //now return 3 things 1.statuscode 2.contentType 3.body
        return ResponseEntity.ok()//1
                .body(imageFile);//3
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product,
                                                @RequestPart MultipartFile imagefile){
        Product product1= null;
        try {
            product1 = service.updateProduct(id,product,imagefile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
        }
        if(product1!=null)
            return new ResponseEntity<>("updated",HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String>  deleteProduct(@PathVariable int id){
        Product product1 =service.getProductById(id);
        if(product1!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        //using Requestparam to sort out using parameters i.e to know whhich criteria to search
        //we'll be searching in all
        List<Product> products= service.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);

    }



}
