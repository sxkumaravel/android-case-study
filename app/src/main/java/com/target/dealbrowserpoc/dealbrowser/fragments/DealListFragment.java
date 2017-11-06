package com.target.dealbrowserpoc.dealbrowser.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.target.dealbrowserpoc.dealbrowser.CircularProgressDialog;
import com.target.dealbrowserpoc.dealbrowser.DealSelectedListener;
import com.target.dealbrowserpoc.dealbrowser.R;
import com.target.dealbrowserpoc.dealbrowser.adapters.DealListAdapter;
import com.target.dealbrowserpoc.dealbrowser.adapters.GridDealListAdapter;
import com.target.dealbrowserpoc.dealbrowser.data.DealItem;
import com.target.dealbrowserpoc.dealbrowser.data.ApiDataSource;
import com.target.dealbrowserpoc.dealbrowser.data.LoaderClient;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Fragment to show deals list.
 *
 * @author kumars
 */
public class DealListFragment extends Fragment implements DealSelectedListener {

    private static final int GRID_SPAN_COUNT = 2;

    private FragmentInteractionListener mListener;

    private List<DealItem> mDealItemList;
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private boolean mIsLinearView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deal_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = getView().findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        if (savedInstanceState == null) {
            loadDealsFromApi();
        } else {
            if (mIsLinearView) {
                showLinearLayout();
            } else {
                showGridView();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_grid_view) {
            showGridView();
        } else {
            showLinearLayout();
        }
        return true;
    }

    @Override
    public void onDealSelected(DealItem dealItem) {
        if (mListener != null)
            mListener.onFragmentInteraction(dealItem);
    }

    private void showGridView() {
        if (mDealItemList == null) {
            Toast.makeText(getContext(), "Nothing to show", Toast.LENGTH_SHORT).show();
        }
        mIsLinearView = false;

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), GRID_SPAN_COUNT));
        mRecyclerView.setAdapter(new GridDealListAdapter(getContext(), mDealItemList, this));
    }

    private void showLinearLayout() {
        if (mDealItemList == null) {
            Toast.makeText(getContext(), "Nothing to show", Toast.LENGTH_SHORT).show();
        }
        mIsLinearView = true;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new DealListAdapter(getContext(), mDealItemList, this));
    }

    private void loadDealsFromApi() {
        showLoading();
        new LoaderClient<List<DealItem>>().load(this, new LoaderClient.LoaderClientCallback<List<DealItem>>() {
            @Override
            public List<DealItem> onLoadInBackground() {
                return new ApiDataSource().getDeals();
            }

            @Override
            public void onResult(List<DealItem> result) {
                hideLoading();
                if (CollectionUtils.isEmpty(result)) {
                    Toast.makeText(getContext(), "Oops Something went wrong", Toast.LENGTH_LONG).show();
                    return;
                }

                mDealItemList = result;
                showLinearLayout();
            }
        });
    }

    private void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new CircularProgressDialog(getContext());
        }
        mProgressDialog.show();
    }

    private void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Interface to be implemented by the caller.
     */
    public interface FragmentInteractionListener {

        /**
         * Method to call when a list idem is selected.
         *
         * @param dealItem - selected {@link DealItem}.
         */
        void onFragmentInteraction(DealItem dealItem);
    }
}