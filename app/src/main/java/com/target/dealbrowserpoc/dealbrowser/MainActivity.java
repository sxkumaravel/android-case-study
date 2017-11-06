package com.target.dealbrowserpoc.dealbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.target.dealbrowserpoc.dealbrowser.data.DealItem;
import com.target.dealbrowserpoc.dealbrowser.fragments.DealListFragment;


public class MainActivity extends AppCompatActivity implements DealListFragment.FragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(DealListFragment.class.getSimpleName());
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_container, new DealListFragment(), DealListFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(DealItem dealItem) {
        Intent intent = new Intent(this, DealsDetailActivity.class);
        intent.putExtra(DealsDetailActivity.EXTRA_DEAL_ITEM, dealItem);
        startActivity(intent);
    }

}
