package com.target.dealbrowserpoc.dealbrowser.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.target.dealbrowserpoc.dealbrowser.DealSelectedListener;
import com.target.dealbrowserpoc.dealbrowser.R;
import com.target.dealbrowserpoc.dealbrowser.data.DealItem;

import java.util.List;

/**
 * Linear recycler view adapter.
 *
 * @author kumars
 */
public class DealListAdapter extends RecyclerView.Adapter<DealListAdapter.ViewHolder> {

    private final Context mContext;
    private List<DealItem> mDealItems;
    private DealSelectedListener mListener;

    public DealListAdapter(Context context, List<DealItem> dealItems, DealSelectedListener listener) {
        mContext = context;
        mDealItems = dealItems;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_deal_item_linear, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DealItem dealItem = mDealItems.get(position);

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDealSelected(dealItem);
            }
        });

        if (!TextUtils.isEmpty(dealItem.getImage())) {
            Picasso.with(mContext)
                    .load(dealItem.getImage())
                    .into(holder.mImageView);
        }

        String title = dealItem.getTitle();
        if (!TextUtils.isEmpty(title) && title.length() > 1) {
            holder.mTitle.setText(title.substring(0, 1).toUpperCase() + title.substring(1)); // capitalize first letter.
        } else {
            holder.mTitle.setText(title);
        }

        if (!TextUtils.isEmpty(dealItem.getAisle())) {
            holder.mAisle.setText(dealItem.getAisle().toUpperCase());
        }

        if (TextUtils.isEmpty(dealItem.getSalePrice())) {
            holder.mAmount.setText(dealItem.getPrice());
        } else {
            holder.mAmount.setText(dealItem.getSalePrice());
        }
    }

    @Override
    public int getItemCount() {
        return mDealItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final LinearLayout mLinearLayout;
        final ImageView mImageView;
        final TextView mTitle;
        final TextView mAmount;
        final TextView mAisle;

        ViewHolder(View view) {
            super(view);
            mLinearLayout = view.findViewById(R.id.deal_item_ll_main);
            mImageView = view.findViewById(R.id.deal_item_image);
            mTitle = view.findViewById(R.id.deal_item_tv_title);
            mAmount = view.findViewById(R.id.deal_item_tv_amount);
            mAisle = view.findViewById(R.id.deal_item_tv_id);
        }
    }
}
