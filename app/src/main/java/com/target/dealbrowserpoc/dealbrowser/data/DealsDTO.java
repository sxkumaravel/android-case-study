package com.target.dealbrowserpoc.dealbrowser.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Deals list data transfer model.
 *
 * @author kumars
 */
public class DealsDTO {

    @SerializedName("_id")
    private String id;

    private List<DealItem> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DealItem> getData() {
        return data;
    }

    public void setData(List<DealItem> data) {
        this.data = data;
    }
}
