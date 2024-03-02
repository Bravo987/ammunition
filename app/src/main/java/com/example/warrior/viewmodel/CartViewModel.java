package com.example.warrior.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.warrior.repository.CartRepo;
import com.example.warrior.utils.model.ProductCart;
import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private CartRepo cartRepo;
    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepo = new CartRepo(application);
    }

    public LiveData<List<ProductCart>> getAllCartItems(){
        return cartRepo.getAllCartItemsLiveData();
    }

    public void insertCartItem(ProductCart productCart)
    {
        cartRepo.insertCartItem(productCart);
    }

    public void updateQuantity(int id, int quantity)
    {
        cartRepo.updateQuantity(id, quantity);
    }

    public void updatePrice(int id, double price)
    {
        cartRepo.updatePrice(id, price);
    }
    public void deleteCartItem(ProductCart productCart)
    {
        cartRepo.deleteCartItem(productCart);
    }

    public void deleteAllCartItems() {
        cartRepo.deleteAllCartItem();
    }
}
