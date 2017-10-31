package com.jaysen.testfeaturedemo.span;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.jaysen.testfeaturedemo.R;

public class SpanTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span_test);
        TextView textView = (TextView) findViewById(R.id.textView);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_tuijian);
        bitmapDrawable.setBounds(0, 0, bitmapDrawable.getIntrinsicWidth(), bitmapDrawable.getIntrinsicHeight());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String                 string                 = getString(R.string.suning_pay);
        spannableStringBuilder.append(string);
        spannableStringBuilder.append("\n");
        String imagePlaceHolder = "imagePlaceHolder";
        spannableStringBuilder.append(imagePlaceHolder);
        String text = " 苏宁支付活动";
        spannableStringBuilder.append(text);
        spannableStringBuilder.setSpan(new ImageSpan(this,bitmapDrawable.getBitmap(),ImageSpan.ALIGN_BOTTOM),
                                       spannableStringBuilder.length() - text.length() - imagePlaceHolder.length(),
                                       spannableStringBuilder.length() - text.length(),
                                       Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.YELLOW),
                                       spannableStringBuilder.length() - text.length(), spannableStringBuilder.length(),
                                       Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(12, true),
                                       spannableStringBuilder.length() - text.length(), spannableStringBuilder.length(),
                                       Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableStringBuilder);
    }
}
