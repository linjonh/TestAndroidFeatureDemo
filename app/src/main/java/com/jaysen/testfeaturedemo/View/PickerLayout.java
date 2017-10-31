package com.jaysen.testfeaturedemo.View;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jaysen.testfeaturedemo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jaysen.lin@foxmail.com on 2017/7/13.
 */

class PickerLayout extends FrameLayout {

    public static final int OFFSET = 1;
    TextView cancelBtn;
    TextView  okBtn;
    WheelView yearWheelView;
    WheelView monthWheelView;

    private int year  = 2014;
    private int month = 1;
    private TextView mSelectedYearMonthTitle;

    public PickerLayout(@NonNull Context context) {
        this(context, null);
    }

    public PickerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PickerLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        View child = View.inflate(context, R.layout.spinner_pick_layout, null);
        child.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickDialog();
            }
        });
        addView(child);
        mSelectedYearMonthTitle = (TextView) findViewById(R.id.selectedYearMonthTitle);
    }

    private void getReference(View view) {
        cancelBtn = (TextView) view.findViewById(R.id.cancelBtn);
        okBtn = (TextView) view.findViewById(R.id.okBtn);
        yearWheelView = (WheelView) view.findViewById(R.id.yearWheelView);
        monthWheelView = (WheelView) view.findViewById(R.id.monthWheelView);
    }

    private void showPickDialog() {
        View child = View.inflate(getContext(), R.layout.whell_picker_layout, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.msg).create();
        alertDialog.setView(child);
        getReference(child);
        Window window = alertDialog.getWindow();

        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(attributes);
//            window.setWindowAnimations(R.style.bottom_dialog_anim_style);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        okBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
//                onConfirmSelect(year, month);
            }
        });
        yearWheelView.setOffset(OFFSET);
        yearWheelView.setItems(getYears());
        monthWheelView.setItems(getMonth());
        yearWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.i("onSelected", "selectedIndex:" + selectedIndex + "item:" + item);
                if (selectedIndex == OFFSET) {
                    year = 2014;
                } else {
                    String y = item.replace("年", "");
                    year = Integer.parseInt(y);
                }
            }
        });
        monthWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                if (selectedIndex == OFFSET) {
                    month = 1;
                } else {
                    String y = item.replace("月", "");
                    month = Integer.parseInt(y);
                }
            }
        });
        yearWheelView.setSeletion(2);
        monthWheelView.setSeletion(2);
        alertDialog.show();
    }

    private ArrayList<String> getYears() {
        ArrayList<String> strings = new ArrayList<>();
        Date              date    = new Date();
        date.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        strings.add("全部");

        int currentYear = calendar.get(Calendar.YEAR);
        for (int i = 2014; i <= currentYear; i++) {
            strings.add(i + "年");
        }
        return strings;
    }

    private ArrayList<String> getMonth() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("全部");

        for (int i = 1; i <= 12; i++) {
            strings.add(i + "月");
        }
        return strings;
    }

    public void setmSelectedYearMonthTitle(String mSelectedYearMonthTitle) {
        if (mSelectedYearMonthTitle != null)
            this.mSelectedYearMonthTitle.setText(mSelectedYearMonthTitle);
    }

//    private void onConfirmSelect(int year, int month) {
//        WritableMap event = Arguments.createMap();
//        event.putInt("year", year);
//        event.putInt("month", month);
//        ReactContext reactContext = (ReactContext) getContext();
//        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "topChange", event);
//    }
}
