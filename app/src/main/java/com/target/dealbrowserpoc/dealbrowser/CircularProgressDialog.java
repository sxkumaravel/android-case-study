package com.target.dealbrowserpoc.dealbrowser;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

/**
 * Simple dialog to show custom white circular progress bar with transparent background.
 * Create on 11/2/17.
 *
 * @author kumars
 */
public class CircularProgressDialog extends ProgressDialog {

    public CircularProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_spinner);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        setIndeterminate(true);
    }
}
