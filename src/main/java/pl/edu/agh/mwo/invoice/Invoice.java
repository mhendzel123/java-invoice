package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.DairyProduct;
import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<>();
    private static int nextNumber = 0;
    private final int number = ++nextNumber;

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    
											    public static void main(String[] args) {
													Invoice inv = new Invoice();
													Product p1 = new DairyProduct("Zsiadle mleko", new BigDecimal("1.00"));
													Product p2 = new DairyProduct("tool", new BigDecimal("1.00"));
													inv.addProduct(p1, 1);
													inv.addProduct(p1, 1);
													inv.addProduct(p2, 1);
													inv.printProducts();
											    }
    
											    
 
    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        if (products.containsKey(product)) {
        	products.replace(product, products.get(product) + quantity);
        } else {
        products.put(product, quantity);
        }
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public int getNumber() {
        return number;
    }

    public void printProducts() {
        System.out.println("Numer faktury: " + number);
        for (Product product : products.keySet()) {
            System.out.println("[" + product.getName() + "] [Ilosc: " + products.get(product) + "] [Cena: " + product.getPrice() + "]");
        }
        System.out.println("Liczba pozycji: " + products.size());
    }
    
    public Map<Product, Integer> showItems(){
        return products;
    }
}