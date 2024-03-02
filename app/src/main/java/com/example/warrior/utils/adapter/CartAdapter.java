package com.example.warrior.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warrior.R;
import com.example.warrior.utils.model.ProductCart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<ProductCart> productCartList;
    private CartClickedListeners cartClickedListeners;

    public CartAdapter(CartClickedListeners cartClickedListeners) {
        this.cartClickedListeners = cartClickedListeners;
    }

    public void setProductCartList(List<ProductCart> productCartList) {
        this.productCartList = productCartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_cart_item, parent, false);
        return new CartViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        ProductCart productCart = productCartList.get(position);
        holder.productImageView.setImageResource(productCart.getProductimage());
        holder.productNameTv.setText(productCart.getProductName());
        holder.productBrandNameTv.setText(productCart.getProductBrandName());
        holder.productQuantity.setText(productCart.getQuantity() + "");
        holder.productPriceTv.setText(productCart.getTotalItemPrice() + "");

        holder.deleteShoeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onDeleteClicked(productCart);
            }
        });

        holder.addQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onPlusClicked(productCart);
            }
        });

        holder.minusQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onMinusClicked(productCart);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (productCartList == null) {
            return 0;
        } else {
            return productCartList.size();
        }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView productNameTv, productBrandNameTv, productPriceTv, productQuantity;
        private ImageView deleteShoeBtn;
        private ImageView productImageView;
        private ImageButton addQuantityBtn, minusQuantityBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            productNameTv = itemView.findViewById(R.id.eachCartItemName);
            productBrandNameTv = itemView.findViewById(R.id.eachCartItemBrandNameTv);
            productPriceTv = itemView.findViewById(R.id.eachCartItemPriceTv);
            deleteShoeBtn = itemView.findViewById(R.id.eachCartItemDeleteBtn);
            productImageView = itemView.findViewById(R.id.eachCartItemIV);
            productQuantity = itemView.findViewById(R.id.eachCartItemQuantityTV);
            addQuantityBtn = itemView.findViewById(R.id.eachCartItemAddQuantityBtn);
            minusQuantityBtn = itemView.findViewById(R.id.eachCartItemMinusQuantityBtn);

        }
    }

    public interface CartClickedListeners{
        void onDeleteClicked(ProductCart productCart);
        void onPlusClicked(ProductCart productCart);
        void onMinusClicked(ProductCart productCart);

    }

}
