package com.coconino.coconinoapp.model.object;

import java.util.List;
import java.util.Objects;

public class Product {
    public final int id;
    public final String name;
    public final String description;
    public final float listPrice;
    public final int stockCount;
    public final int brandID;
    public final String brandName;

    public Product(int id,
                   String name,
                   String description,
                   float listPrice,
                   int stockCount,
                   String brandName,
                   int brandID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.listPrice = listPrice;
        this.stockCount = stockCount;
        this.brandID = brandID;
        this.brandName = brandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Product(List<Object> l) {
        this((int) l.get(0),
                (String) l.get(1),
                (String) l.get(2),
                // In cases where the list_price is a whole number, Cursor will return an Integer.
                // This is despite it being declared in the .sql file as a decimal. !!!!
                // In those cases, the value will need to be converted into a float.
                (l.get(3) instanceof Integer) ? ((Integer) l.get(3)).floatValue() : (float) l.get(3),
                (int) l.get(4),
                (String) l.get(5),
                (int) l.get(6));


    }
}
