package com.target.dealbrowserpoc.dealbrowser;

import com.target.dealbrowserpoc.dealbrowser.data.DealItem;

/**
 * Interface to be implemented by the caller to get the deal selected even.
 *
 * @author kumars
 */
public interface DealSelectedListener {

    /**
     * Method to call when deal item is selected.
     *
     * @param dealItem {@link DealItem}
     */
    void onDealSelected(DealItem dealItem);
}
