package com.example.warrior.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.warrior.dao.CartDAO;
import com.example.warrior.database.CartDatabase;
import com.example.warrior.utils.model.ProductCart;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CartRepo {

    private CartDAO cartDAO;
    private LiveData<List<ProductCart>> allCartItemsLiveData;
    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List<ProductCart>> getAllCartItemsLiveData() {
        return allCartItemsLiveData;
    }

    public CartRepo(Application application)
    {
        cartDAO = CartDatabase.getInstance(application).cartDAO();
        allCartItemsLiveData = cartDAO.getAllCartItems();

    }

    public void insertCartItem(ProductCart productCart)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.insertCartItem(productCart);
            }
        });
    }
    public void deleteCartItem(ProductCart productCart)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.deleteCartItem(productCart);
            }
        });
    }

    public void updateQuantity(int id, int quantity)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.updateQuantity(id, quantity);
            }
        });
    }

    public void updatePrice(int id, double price)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.updatePrice(id, price);
            }
        });
    }
    public void deleteAllCartItem(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.deleteAllItems();
            }
        });
    }
}


