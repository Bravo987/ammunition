package com.example.warrior.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.warrior.utils.model.ProductCart;

import java.util.List;

@Dao
public interface CartDAO {

    @Insert
    void insertCartItem(ProductCart productCart);

    @Query("SELECT * FROM product_table")
    LiveData<List<ProductCart>>getAllCartItems();

    @Delete
    Void deleteCartItem(ProductCart productCart);
    @Query("UPDATE product_table SET quantity=:quantity WHERE id=:id")
    void updateQuantity(int id, int quantity);

    @Query("UPDATE product_table SET totalItemPrice=:totalItemPrice WHERE id=:id")
    void updatePrice(int id, double totalItemPrice);

    @Query("DELETE FROM product_table")
    void deleteAllItems();
}
