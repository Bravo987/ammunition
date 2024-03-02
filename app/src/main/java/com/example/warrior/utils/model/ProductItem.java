package com.example.warrior.utils.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProductItem implements Parcelable {

    private String productName,productBrandName;
    private int productimage;
    private double price;

    public ProductItem(String productName, String productBrandName, int productimage, double price) {
        this.productName = productName;
        this.productBrandName = productBrandName;
        this.productimage = productimage;
        this.price = price;
    }

    protected ProductItem(Parcel in) {
        productName = in.readString();
        productBrandName = in.readString();
        productimage = in.readInt();
        price = in.readDouble();
    }

    public static final Creator<ProductItem> CREATOR = new Creator<ProductItem>() {
        @Override
        public ProductItem createFromParcel(Parcel in) {
            return new ProductItem(in);
        }

        @Override
        public ProductItem[] newArray(int size) {
            return new ProductItem[size];
        }
    };

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
    }

    public int getProductimage() {
        return productimage;
    }

    public void setProductimage(int productimage) {
        this.productimage = productimage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeString(productBrandName);
        dest.writeInt(productimage);
        dest.writeDouble(price);
    }
}
