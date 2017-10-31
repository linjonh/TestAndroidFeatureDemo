package com.jaysen.testfeaturedemo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by liuj on 2016/9/1.
 * 圆角tablayout
 */
public class SimplePagerTabLayout extends LinearLayout implements PagerTabLayout {

    private static final float DEF_DP_RADIUS = 15;

    private View                           currentTabView;
    private ViewPager                      mViewPager;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int                            mCurrentPos;

    private int textSize;
    private int txtDefColor;
    private int txtSelectedColor;
    private int dividerColor;
    private int dividerWidth;
    private int tabViewBackgroundResId;
    private float radius;

    private Paint maskPaint;
    private Paint borderPaint;


    private ColorStateList tabTxtColorStateList;

    private OnClickListener tabViewClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = indexOfChild(v);
            if (mCurrentPos == pos) {
                return;
            }
            setCurrentItem(pos);
        }
    };

    public SimplePagerTabLayout(Context context) {
        this(context, null);
    }

    public SimplePagerTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimplePagerTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleAttrs(context, attrs);
        initLayout();
    }

    private void handleAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SimplePagerTabLayout);
        textSize = typedArray.getDimensionPixelSize(R.styleable.SimplePagerTabLayout_SimplePager_text_size, getResources().getDimensionPixelOffset(R.dimen.default_tab_txt_size));
        txtDefColor = typedArray.getColor(R.styleable.SimplePagerTabLayout_SimplePager_text_default_color, getResources().getColor(R.color.default_tab_txt_color));
        txtSelectedColor = typedArray.getColor(R.styleable.SimplePagerTabLayout_SimplePager_text_selected_color, getResources().getColor(R.color.default_tab_txt_selected_color));
        tabTxtColorStateList = createColorStateList(txtDefColor, txtSelectedColor);
        dividerColor = typedArray.getColor(R.styleable.SimplePagerTabLayout_SimplePager_divider_color, getResources().getColor(R.color.default_divider_color));
        dividerWidth = typedArray.getDimensionPixelOffset(R.styleable.SimplePagerTabLayout_SimplePager_divider_width, getResources().getDimensionPixelOffset(R.dimen.default_divider_size));
        tabViewBackgroundResId = typedArray.getResourceId(R.styleable.SimplePagerTabLayout_SimplePager_tab_background, R.drawable.def_tab_selector);
        int defRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_DP_RADIUS, getResources().getDisplayMetrics());
        radius = typedArray.getDimensionPixelSize(R.styleable.SimplePagerTabLayout_SimplePager_radius, defRadius);
        typedArray.recycle();
    }

    private void initLayout() {
        setOrientation(HORIZONTAL);
        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_HARDWARE, null);//部分机型需要开启硬件加速 否则绘制会出现异常
    }

    @Override
    public void setOnPageChangedListener(ViewPager.OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    @Override
    public void setCurrentItem(int pos) {
        mCurrentPos = pos;
        setSelectTab(pos);
        mViewPager.setCurrentItem(pos);
    }

    @Override
    public void notifyDataSetChanged() {
        removeAllViews();
        addTabs();
        invalidate();
    }

    private void setSelectTab(int currentPos) {
        if (currentTabView != null) {
            currentTabView.setSelected(false);
        }
        currentTabView = getChildAt(currentPos);
        currentTabView.setSelected(true);
    }

    private static ColorStateList createColorStateList(int defaultColor, int selectedColor) {
        final int[][] states = new int[3][];
        final int[] colors = new int[3];
        int i = 0;

        states[i] = SELECTED_STATE_SET;
        colors[i] = selectedColor;
        i++;

        // Default enabled state
        states[i] = EMPTY_STATE_SET;
        colors[i] = defaultColor;
        i++;

        states[i] = PRESSED_ENABLED_SELECTED_STATE_SET;
        colors[i] = selectedColor;

        return new ColorStateList(states, colors);
    }

    /**
     * 添加tab
     */
    private void addTabs() {
        if (mViewPager != null) {
            PagerAdapter pagerAdapter = mViewPager.getAdapter();
            if (pagerAdapter != null) {
                for (int i = 0; i < pagerAdapter.getCount(); i++) {
                    Tab  tab     = TabBuilder.createTab(i, pagerAdapter, this);
                    View tabView = createTabView(tab);
                    addView(tabView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                }
                setSelectTab(0);
            }
        }
    }

    /**
     * 创建tabview
     *
     * @param tab
     * @return
     */
    private View createTabView(Tab tab) {
        View tabView = null;
        if (tab.customView != null) {
            tabView = tab.customView;
        } else {
            TextView tv = new TextView(getContext());
            tv.setText(tab.title);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(tabTxtColorStateList);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            tv.setBackgroundResource(tabViewBackgroundResId);
            tabView = tv;
        }
        tabView.setOnClickListener(tabViewClickListener);
        return tabView;
    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {

        int childCount = getChildCount();
        if (childCount == 0) {
            super.dispatchDraw(canvas);
            return;
        }

        int count = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);

        super.dispatchDraw(canvas);

        drawDivider(canvas, childCount);
        drawBoundMask(canvas);
        drawBorder(canvas);

        canvas.restoreToCount(count);

    }


    /**
     * 绘制分割线
     *
     * @param canvas
     * @param childCount
     */
    private void drawDivider(Canvas canvas, int childCount) {
        int width = getWidth() / childCount;
        int height = getHeight();
        int drawXPos = width;
        //绘制分割线
        for (int i = 0; i < childCount - 1; i++) {
            Paint paint = getBorderPaint();
            //与当前位置不相邻的位置需要画分割线
            if (i != mCurrentPos - 1 && i != mCurrentPos) {
                canvas.drawLine(drawXPos, dividerWidth, drawXPos, height - dividerWidth, paint);
            }
            drawXPos += width;
        }
    }

    /**
     * 绘制边框
     *
     * @param canvas
     */
    private void drawBorder(Canvas canvas) {
        int  inset = dividerWidth / 2;
        Path path  = getBoundPath(inset);
        canvas.drawPath(path, getBorderPaint());
    }

    private void drawBoundMask(Canvas canvas) {
        Path path = getBoundPath(0);
        canvas.drawPath(path, getMaskPaint());
    }


    private Path getBoundPath(int inset) {
        int   width  = getWidth();
        int   height = getHeight();
        RectF bound  = new RectF(0, 0, width, height);
        bound.inset(inset, inset);
        Path path = new Path();
        path.addRoundRect(bound, radius, radius, Path.Direction.CCW);
        return path;
    }

    private Paint getBorderPaint() {
        if (borderPaint == null) {
            borderPaint = new Paint();
            borderPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            borderPaint.setStyle(Paint.Style.STROKE);
        }
        borderPaint.setColor(dividerColor);
        borderPaint.setStrokeWidth(dividerWidth);
        return borderPaint;
    }

    private Paint getMaskPaint() {
        if (maskPaint == null) {
            maskPaint = new Paint();
            maskPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN)); //实现裁剪效果
        }
        return maskPaint;
    }

    public void changeBoderColor(int color) {
        dividerColor = color;
        invalidate();
    }
}
