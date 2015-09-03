package com.pavel.dolbik.retainfragment;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Pavel on 29.08.2015.
 */
public class RetainFragment extends Fragment {

    private SignModel signModel;

    public RetainFragment() {
        signModel = new SignModel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public SignModel getSignModel() {
        return signModel;
    }
}
