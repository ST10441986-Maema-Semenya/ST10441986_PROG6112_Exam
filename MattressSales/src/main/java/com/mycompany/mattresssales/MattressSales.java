package com.mycompany.mattresssales;

import java.util.*;

public class MattressSales {

    public static void main(String[] args) {
        List<Product.Sale> sales = List.of(
                new Product.Sale("Cozy Nights Monarch", 20, 18, 25),
                new Product.Sale("Restonic Barbados", 15, 10, 12),
                new Product.Sale("Sealy phiucket", 18, 15, 20));

        Product report = new Product(sales);

    }
}