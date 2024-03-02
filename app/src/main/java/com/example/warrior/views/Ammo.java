package com.example.warrior.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.warrior.R;
import com.example.warrior.utils.adapter.ProductItemAdapter;
import com.example.warrior.utils.model.ProductCart;
import com.example.warrior.utils.model.ProductItem;
import com.example.warrior.viewmodel.CartViewModel;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.widget.SearchView; // search

import java.util.ArrayList;
import java.util.List;

public class Ammo extends AppCompatActivity implements ProductItemAdapter.ProductClickedListeners {

    private SearchView search;
    private RecyclerView recyclerView;
    private List<ProductItem> productItemList;
    private ProductItemAdapter adapter;
    private CartViewModel viewModel;
    private List<ProductCart> productCartList;
    private CoordinatorLayout coordinatorLayout;
    private ImageView cartImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ammo);

        initializeVariables();
        setUpList();

        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Ammo.this, CartActivity.class));
            }
        });

        // Search Logic
        search = findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        adapter.setProductItemList(productItemList);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getAllCartItems().observe(this, new Observer<List<ProductCart>>() {
            @Override
            public void onChanged(List<ProductCart> productCarts) {
                productCartList.addAll(productCarts);
            }
        });
    }

    // Product Data
    private void setUpList() {
        productItemList.add(new ProductItem("BirdShot","xyz",R.drawable.wallbirdshot,250));
        productItemList.add(new ProductItem("Bore","xyz",R.drawable.wallbore,200));
        productItemList.add(new ProductItem("BuckShot","xyz",R.drawable.wallbuckshot,300));
        productItemList.add(new ProductItem("Gauge","xyz",R.drawable.wallgauge,150));
        productItemList.add(new ProductItem("Gauge 20","xyz",R.drawable.wallgaugetwenty,250));
        productItemList.add(new ProductItem("9 MM Lungar","xyz",R.drawable.wallmmlungar,450));
        productItemList.add(new ProductItem("Remington","xyz",R.drawable.wallremington,150));
        productItemList.add(new ProductItem("Slug","xyz",R.drawable.wallslug,100));
        productItemList.add(new ProductItem("Special","xyz",R.drawable.wallspecial,300));
        productItemList.add(new ProductItem("Springfiled","xyz",R.drawable.wallspringfiled,450));
        productItemList.add(new ProductItem("Winchester","xyz",R.drawable.wallwinchester,400));
        productItemList.add(new ProductItem("Auto","xyz",R.drawable.wallauto,350));

    }

    private void initializeVariables() {

        productCartList = new ArrayList<>();
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        productItemList = new ArrayList<>();
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ProductItemAdapter(this);
        cartImageView = findViewById(R.id.cartIv);

    }

    @Override
    public void onCardClicked(ProductItem product) {
        Intent intent = new Intent(Ammo.this, DetailedActivity.class);
        intent.putExtra("productItem",product);
        startActivity(intent);

    }

    @Override
    public void onAddToCartBtnClicked(ProductItem productItem) {

        ProductCart productCart = new ProductCart();
        productCart.setProductName(productItem.getProductName());
        productCart.setProductBrandName(productItem.getProductBrandName());
        productCart.setPrice(productItem.getPrice());
        productCart.setProductimage(productItem.getProductimage());

        final int[] quantity ={1};
        final int[] id =new int[1];

        if (!productCartList.isEmpty()){
            for(int i=0; i<productCartList.size();i++)
            {
                if(productCart.getProductName().equals(productCartList.get(i).getProductName())){
                    quantity[0] = productCartList.get(i).getQuantity();
                    id[0]=productCartList.get(i).getId();
                }
            }
        }
        if (quantity[0]==1)
        {
            productCart.setQuantity(quantity[0]);
            productCart.setTotalItemPrice(quantity[0]*productCart.getPrice());
            viewModel.insertCartItem(productCart);
        }
        else {
            viewModel.updateQuantity(id[0],quantity[0]);
            viewModel.updatePrice(id[0],quantity[0]*productCart.getPrice());
        }

        makeSnackBar("Item Added to Cart");

    }
    private void makeSnackBar(String msg)
    {
        Snackbar.make(coordinatorLayout,msg,Snackbar.LENGTH_SHORT)
                .setAction("Go to Cart ", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Ammo.this, CartActivity.class));
                    }
                }).show();
    }


}