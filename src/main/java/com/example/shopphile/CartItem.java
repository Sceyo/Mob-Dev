package com.example.shopphile;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {
    private String name;
    private String description;
    private String price;
    private int imageResId;
    private int quantity;

    public CartItem(String name, String description, String price, int imageResId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
        this.quantity = 1; // Default quantity
    }

    protected CartItem(Parcel in) {
        name = in.readString();
        description = in.readString();
        price = in.readString();
        imageResId = in.readInt();
        quantity = in.readInt();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPriceAsInt() {
        return Integer.parseInt(price.replace("$", ""));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeInt(imageResId);
        dest.writeInt(quantity);
    }
}
