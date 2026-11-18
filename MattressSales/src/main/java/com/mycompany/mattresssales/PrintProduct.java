package com.mycompany.mattresssales;

import java.util.*;

public class PrintProduct {

    private final Product.IProduct product;

    public PrintProduct(Product.IProduct product) {
        this.product = product;
    }

    public String buildReport() {
        String line = "=".repeat(49);
        return String.format(Locale.US,
                "%s%n PRODUCT SALES REPORT - 2026%n%s%n"
                + "Total Product sales : %d%n"
                + "Average Product sales: %.2f%n"
                + "Most Popular Product: %s%n",
                line, line,
                product.GetTotalProductsSold(),
                product.GetAverageProductSold(),
                product.GetMostPopularProduct());
    }

    public void print() {
        System.out.print(buildReport());
    }
}