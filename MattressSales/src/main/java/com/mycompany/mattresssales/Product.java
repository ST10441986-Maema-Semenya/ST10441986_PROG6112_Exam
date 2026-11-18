package com.mycompany.mattresssales;

import java.util.*;

public class Product {

    private final List<Sale> sales;


    public interface IProduct {
        int GetTotalProductsSold();
        double GetAverageProductSold();
        String GetMostPopularProduct();
    }

    public record Sale(String company, int productNumberMonth1, int productNumberMonth2, int productNumberMonth3) {
        int total() { return productNumberMonth1 + productNumberMonth2 + productNumberMonth3; }
    }

    public Product(List<Sale> sales) {
        this.sales = sales;
    }

    public int GetTotalProductsSold() {
        return sales.stream().mapToInt(Sale::total).sum();
    }

    public double GetAverageProductSold() {
        return (double) GetTotalProductsSold() / 9;
    }

    public String GetMostPopularProduct() {
        return sales.stream().max(Comparator.comparingInt(Sale::total)).get().company();
    }
}