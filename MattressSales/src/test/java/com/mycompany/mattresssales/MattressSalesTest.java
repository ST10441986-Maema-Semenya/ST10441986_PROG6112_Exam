package com.mycompany.mattresssales;

import com.mycompany.mattresssales.Product.IProduct;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PrintProduct {

    PrintProduct(IProduct stub1) {
    }

    // Stub so the printer is tested on its own, without Product
    private final Product.IProduct stub = new Product.IProduct() {
        public int GetTotalProductsSold() { return 100; }
        public double GetAverageProductSold() { return 33.333; }
        public String GetMostPopularProduct() { return "Test Mattress"; }
    };

    @Test
    void reportContainsAllValues() {
        String report = (String) new PrintProduct(stub).buildReport();

        assertTrue(report.contains("PRODUCT SALES REPORT - 2026"));
        assertTrue(report.contains("Total Product sales : 100"));
        assertTrue(report.contains("Average Product sales: 33.33"));
        assertTrue(report.contains("Most Popular Product: Test Mattress"));
    }

    @Test
    void printWritesReportToStandardOutput() {
        var original = System.out;
        var buffer = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(buffer));
        try {
            new PrintProduct(stub).print();
        } finally {
            System.setOut(original);
        }
        assertEquals(new PrintProduct(stub).buildReport(), buffer.toString());
    }

    private void print() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private Object buildReport() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}