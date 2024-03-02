package com.example.warrior.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.warrior.R;
import com.example.warrior.utils.model.ProductCart;
import com.example.warrior.utils.model.ProductItem;
import com.example.warrior.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productNameTV, productBrandNameTV, productPriceTV;
    private AppCompatButton addToCartBtn;
    private ProductItem product;



    private CartViewModel viewModel;

    private List<ProductCart> productCartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        product = getIntent().getParcelableExtra("productItem");
        initializeVaraibles();

        viewModel.getAllCartItems().observe(this, new Observer<List<ProductCart>>() {
            @Override
            public void onChanged(List<ProductCart> productCarts) {
                productCartList.addAll(productCarts);
            }
        });

        if(product !=null)
        {
            setDataToWidgets();
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertToRoom();
            }
        });

    }

    private void insertToRoom(){
        ProductCart productCart = new ProductCart();
        productCart.setProductName(product.getProductName());
        productCart.setProductBrandName(product.getProductBrandName());
        productCart.setPrice(product.getPrice());
        productCart.setProductimage(product.getProductimage());

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

        startActivity(new Intent(DetailedActivity.this, CartActivity.class));
    }
    private void setDataToWidgets(){
        productNameTV.setText(product.getProductName());
        productBrandNameTV.setText(product.getProductBrandName());
        productPriceTV.setText(String.valueOf(product.getPrice()));
        productImageView.setImageResource(product.getProductimage());

    }

    private void initializeVaraibles()
    {
        productCartList = new ArrayList<>();
        productImageView = findViewById(R.id.detailActivityProductIV);
        productNameTV = findViewById(R.id.detailActivityProductNameTv);
        productBrandNameTV = findViewById(R.id.detailActivityProductBrandNameTv);
        productPriceTV = findViewById(R.id.detailActivityProductPriceTv);
        addToCartBtn = findViewById(R.id.detailActivityAddToCartBtn);

        viewModel = new ViewModelProvider(this).get(CartViewModel.class);


    }
}