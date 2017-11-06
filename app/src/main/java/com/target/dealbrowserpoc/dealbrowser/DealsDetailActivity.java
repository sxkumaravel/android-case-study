package com.target.dealbrowserpoc.dealbrowser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.target.dealbrowserpoc.dealbrowser.data.DealItem;
import com.target.dealbrowserpoc.dealbrowser.fragments.DetailFragment;

/**
 * @author kumars
 */
public class DealsDetailActivity extends AppCompatActivity {

    public static final String EXTRA_DEAL_ITEM = "deal_item_extra";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_detail);

        DealItem dealItem = getIntent().getParcelableExtra(EXTRA_DEAL_ITEM);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(DetailFragment.class.getSimpleName());
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.detail_fl_container, DetailFragment.newInstance(dealItem), DetailFragment.class.getSimpleName())
                    .commit();
        }
    }
}
