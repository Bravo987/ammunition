package com.example.warrior.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warrior.R;
import com.example.warrior.utils.model.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder> {

    private List<ProductItem> productItemList;

    private List<ProductItem> productItemListFull;

    private ProductClickedListeners productClickedListeners;
    public ProductItemAdapter(ProductClickedListeners productClickedListeners){
        this.productClickedListeners=productClickedListeners;
    }


    public void setProductItemList(List<ProductItem> productItemList)
    {
        this.productItemList=productItemList;
        this.productItemListFull = new ArrayList<>(productItemList);

    }
    public Filter getFilter() {
        return shoeFilter;
    }

    private Filter shoeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProductItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(productItemListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ProductItem productItem : productItemListFull) {
                    if (productItem.getProductName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(productItem);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productItemList.clear();
            productItemList.addAll((List<ProductItem>) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_product,parent,false);
        return new ProductItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemViewHolder holder, int position) {

        ProductItem productItem = productItemList.get(position);
        holder.productNameTv.setText(productItem.getProductName());
        holder.productBrandNameTv.setText(productItem.getProductBrandName());
        holder.productPriceTv.setText(String.valueOf(productItem.getPrice()));
        holder.productImageView.setImageResource(productItem.getProductimage());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productClickedListeners.onCardClicked(productItem);

            }
        });

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productClickedListeners.onAddToCartBtnClicked(productItem);

            }
        });

    }

    @Override
    public int getItemCount() {

        if(productItemList == null){
            return 0;
        }
        else {
            return productItemList.size();
        }

    }

    public class ProductItemViewHolder extends RecyclerView.ViewHolder{


        private ImageView productImageView , addToCartBtn;
        private TextView productNameTv, productBrandNameTv, productPriceTv;
        private CardView cardView;

        public ProductItemViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.eachProductCardView);
            addToCartBtn = itemView.findViewById(R.id.eachProductAddToCartBtn);
            productNameTv = itemView.findViewById(R.id.eachProductName);
            productImageView = itemView.findViewById(R.id.eachProductIv);
            productBrandNameTv = itemView.findViewById(R.id.eachProductBrandNameTv);
            productPriceTv = itemView.findViewById(R.id.eachProductPriceTv);

        }
    }
    public interface ProductClickedListeners{
        void onCardClicked(ProductItem product);

        void onAddToCartBtnClicked(ProductItem productItem);

    }
}
