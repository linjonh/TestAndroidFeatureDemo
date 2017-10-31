package com.jaysen.testfeaturedemo;


import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Boolean mIsPortrait = true;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public BlankDialogFragment() {
        // Required empty public constructor
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.ThemeChallengeDlg);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankDialogFragment newInstance(String param1, String param2) {
        BlankDialogFragment fragment = new BlankDialogFragment();
        Bundle              args     = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        int orientation = getResources().getConfiguration().orientation;
//        if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
        Dialog dialog = getDialog();
        if (dialog!=null) {
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                window.setAttributes(attributes);
            }
        }
        Log.e("BlankDialogFragment", "onActivityCreated");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putBoolean("");/
        Log.e("BlankDialogFragment", "onSaveInstanceState");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("BlankDialogFragment", "onDestroy");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

}
