package com.target.dealbrowserpoc.dealbrowser.data;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

/**
 * Loader client that provides the caller with the option to do some work on background thread and to return the result on UI Thread.
 *
 * @author kumars
 */
public class LoaderClient<D> {

    /**
     * Start loader to load data on Worker thread.
     * This forces an asynchronous load to ignore previously loaded data and load new one.
     *
     * @param fragment the fragment requires to run loader using {@link LoaderManager}.
     * @param callback the {@link LoaderClientCallback} to use.
     */
    public void load(Fragment fragment, LoaderClientCallback<D> callback) {
        load(fragment.getContext(), fragment.getLoaderManager(), callback);
    }

    private void load(final Context context, final LoaderManager loaderManager, final LoaderClientCallback<D> callback) {
        loaderManager.initLoader(0, null, new LoaderManager.LoaderCallbacks<D>() {
            @Override
            public Loader<D> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<D>(context) {
                    @Override
                    public D loadInBackground() {
                        return callback.onLoadInBackground();
                    }

                    @Override
                    protected void onStartLoading() {
                        // load new data
                        forceLoad();
                    }

                    @Override
                    protected void onStopLoading() {
                        cancelLoad();
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<D> loader, final D data) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResult(data);
                        loaderManager.destroyLoader(0);
                    }
                });
            }

            @Override
            public void onLoaderReset(Loader<D> loader) {
                // NOP
            }
        });
    }

    /**
     * Callback Interface for the application to load data.
     */
    public interface LoaderClientCallback<D> {
        /**
         * Called on a worker thread to perform the actual load.
         *
         * @return The result of the loaded operation.
         */
        D onLoadInBackground();

        /**
         * Called to deliver the result on UI Thread
         *
         * @param result The result of the load operation
         */
        void onResult(D result);
    }
}
