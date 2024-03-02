package com.example.warrior.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductItemAdapter.ProductClickedListeners {

    RecyclerView recyclerView;

    private SearchView search;
    private List<ProductItem> productItemList;
    private ProductItemAdapter adapter;

    private CartViewModel viewModel;
    private CoordinatorLayout coordinatorLayout;
    private List<ProductCart> productCartList;
    private ImageView cartImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
        setUpList();

        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
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
        productItemList.add(new ProductItem("Uzi","Xyz",R.drawable.walluzi,12000));
        productItemList.add(new ProductItem("Ak15","Xyz",R.drawable.wallak15,20000));
        productItemList.add(new ProductItem("Ak47","Xyz",R.drawable.wallak47,24000));
        productItemList.add(new ProductItem("Colt","Xyz",R.drawable.wallcolt,21500));
        productItemList.add(new ProductItem("Dan Wesson","Xyz",R.drawable.walldanwesson,23500));
        productItemList.add(new ProductItem("Glock","Xyz",R.drawable.wallglock,24500));
        productItemList.add(new ProductItem("Heckler","Xyz",R.drawable.wallheckler,16800));
        productItemList.add(new ProductItem("MAC","Xyz",R.drawable.wallmac,20200));
        productItemList.add(new ProductItem("Marlin","Xyz",R.drawable.wallmarlin,17800));


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
        Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
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
                        startActivity(new Intent(MainActivity.this, CartActivity.class));
                    }
                }).show();
    }
}
