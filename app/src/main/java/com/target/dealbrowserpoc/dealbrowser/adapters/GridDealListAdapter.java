package com.target.dealbrowserpoc.dealbrowser.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.target.dealbrowserpoc.dealbrowser.DealSelectedListener;
import com.target.dealbrowserpoc.dealbrowser.R;
import com.target.dealbrowserpoc.dealbrowser.data.DealItem;

import java.util.List;

/**
 * Grid recycler view adapter.
 *
 * @author kumars
 */
public class GridDealListAdapter extends RecyclerView.Adapter<GridDealListAdapter.ViewHolder> {

    private final Context mContext;
    private List<DealItem> mDealItems;
    private DealSelectedListener mListener;

    public GridDealListAdapter(Context context, List<DealItem> dealItems, DealSelectedListener listener) {
        mContext = context;
        mDealItems = dealItems;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_deal_item_grid, parent, false));
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

        if (TextUtils.isEmpty(dealItem.getSalePrice())) {
            holder.mAmount.setText(dealItem.getPrice());
        } else {
            holder.mAmount.setText(dealItem.getSalePrice());
        }

        handleImage(holder.mImageView, dealItem.getImage());
    }

    private void handleImage(final ImageView imageView, final String uri) {
        if (TextUtils.isEmpty(uri)) {
            return;
        }

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Picasso.with(mContext)
                        .load(uri)
                        .resize(imageView.getWidth(), 0) // 0 - varies the height depending on the image aspect ratio with the fixed width.
                        .into(imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDealItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final LinearLayout mLinearLayout;
        final ImageView mImageView;
        final TextView mAmount;

        ViewHolder(View view) {
            super(view);
            mLinearLayout = view.findViewById(R.id.deal_item_ll_main);
            mImageView = view.findViewById(R.id.deal_item_image);
            mAmount = view.findViewById(R.id.deal_list_tv_amount);
        }
    }
}
