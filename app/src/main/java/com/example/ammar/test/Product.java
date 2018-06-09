package com.example.ammar.test;

 // Getter Setter Class for Products we get in a response.
public class Product {
    // Declare all the fields getting in a response
     // Since I don't know the actual data , creating dummy variables.

     int ProductId;
     String ProductName;
     int ProductQuantity;

     public Product(int ProductId, String ProductName,int ProductQuantity)
     {
         this.ProductId = ProductId;
         this.ProductName = ProductName;
         this.ProductQuantity = ProductQuantity;

     }

     public int getProductId() {
         return ProductId;
     }

     public void setProductId(int productId) {
         ProductId = productId;
     }

     public String getProductName() {
         return ProductName;
     }

     public void setProductName(String productName) {
         ProductName = productName;
     }

     public int getProductQuantity() {
         return ProductQuantity;
     }

     public void setProductQuantity(int productQuantity) {
         ProductQuantity = productQuantity;
     }
 }
