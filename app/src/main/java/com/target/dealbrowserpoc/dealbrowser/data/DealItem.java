package com.target.dealbrowserpoc.dealbrowser.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DealItem implements Parcelable {

    @SerializedName("_id")
    private String id;
    private String aisle;
    private String description;
    private String image;
    private String price;
    private String salePrice;
    private String title;
    private int index;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.aisle);
        dest.writeString(this.description);
        dest.writeString(this.image);
        dest.writeString(this.price);
        dest.writeString(this.salePrice);
        dest.writeString(this.title);
        dest.writeInt(this.index);
    }

    public DealItem() {
        // NOP
    }

    private DealItem(Parcel in) {
        this.id = in.readString();
        this.aisle = in.readString();
        this.description = in.readString();
        this.image = in.readString();
        this.price = in.readString();
        this.salePrice = in.readString();
        this.title = in.readString();
        this.index = in.readInt();
    }

    public static final Parcelable.Creator<DealItem> CREATOR = new Parcelable.Creator<DealItem>() {

        @Override
        public DealItem createFromParcel(Parcel source) {
            return new DealItem(source);
        }

        @Override
        public DealItem[] newArray(int size) {
            return new DealItem[size];
        }
    };
}