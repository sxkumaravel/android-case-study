package com.target.dealbrowserpoc.dealbrowser.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.target.dealbrowserpoc.dealbrowser.R;
import com.target.dealbrowserpoc.dealbrowser.data.DealItem;

/**
 * Fragment to show the detail info about the given deal.
 *
 * @author kumars
 */
public class DetailFragment extends Fragment {

    private static final String ARG_EXTRA = "detail_deals";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViews();
    }

    private void setUpViews() {
        final DealItem dealItem = getArguments().getParcelable(ARG_EXTRA);
        if (dealItem == null) {
            return;
        }

        handleShowingTitle(dealItem.getTitle());
        handleShowingPrice(dealItem);
        handleShowingImage(dealItem.getImage());

        ((TextView) getView().findViewById(R.id.deals_details_tv_desc)).setText(dealItem.getDescription());

        getView().findViewById(R.id.deals_detail_button_add_to_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Add to List Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        getView().findViewById(R.id.deals_detail_button_add_to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Add to Cart Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleShowingTitle(String titleString) {
        TextView title = getView().findViewById(R.id.deals_details_tv_title);
        if (!TextUtils.isEmpty(titleString) && titleString.length() > 1) {
            title.setText(titleString.substring(0, 1).toUpperCase() + titleString.substring(1)); // capitalize first letter.
        } else {
            title.setText(titleString);
        }
    }

    private void handleShowingPrice(DealItem dealItem) {
        TextView salePrice = getView().findViewById(R.id.deals_details_tv_sale_price);
        TextView price = getView().findViewById(R.id.deals_details_tv_price);
        if (TextUtils.isEmpty(dealItem.getSalePrice())) {
            // if no sale price then assign the price to sale and hide the price view
            salePrice.setText(dealItem.getPrice());
            price.setVisibility(View.GONE);
        } else {
            salePrice.setText(dealItem.getSalePrice());

            price.setText(getString(R.string.reg_amount, dealItem.getPrice()), TextView.BufferType.SPANNABLE);
            Spannable spannable = (Spannable) price.getText();
            spannable.setSpan(new StrikethroughSpan(), 5, price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void handleShowingImage(final String uri) {
        if (TextUtils.isEmpty(uri)) {
            return;
        }

        final ImageView imageView = getView().findViewById(R.id.deals_details_image);
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Picasso.with(getContext())
                        .load(uri)
                        .resize(imageView.getWidth(), 0) // 0 - varies the height depending on the image aspect ratio with the fixed width.
                        .into(imageView);
            }
        });
    }

    public static DetailFragment newInstance(@NonNull DealItem dealItem) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_EXTRA, dealItem);
        fragment.setArguments(bundle);
        return fragment;
    }
}
