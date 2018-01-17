package com.jaysen.testfeaturedemo.RecycleViewDragAndDrop;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by jaysen.lin@foxmail.com on 2017/11/1.
 */

public class MyItemTouchCallBack extends ItemTouchHelper.Callback {
    private boolean mIsCanDrag             = false;
    private boolean mIsCanSwipeLeftOrRight = false;
    private OnMoveAndSwipeListener onMoveAndSwipeListener;

    public void setOnMoveAndSwipeListener(OnMoveAndSwipeListener onMoveAndSwipeListener) {
        this.onMoveAndSwipeListener = onMoveAndSwipeListener;
    }

    public void setmIsCanDrag(boolean mIsCanDrag) {
        this.mIsCanDrag = mIsCanDrag;
    }

    public void setmIsCanSwipeLeftOrRight(boolean mIsCanSwipeLeftOrRight) {
        this.mIsCanSwipeLeftOrRight = mIsCanSwipeLeftOrRight;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL)
                return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0);
        }
        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
            RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() == target.getItemViewType()) {
            if (onMoveAndSwipeListener != null) {
                onMoveAndSwipeListener.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            }
            return true;
        }
        return false ;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (onMoveAndSwipeListener != null) {
            onMoveAndSwipeListener.onItemDismiss(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return mIsCanDrag;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return mIsCanSwipeLeftOrRight;
    }
}
