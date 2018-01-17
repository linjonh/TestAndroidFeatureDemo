package com.jaysen.testfeaturedemo.RecycleViewDragAndDrop;

/**
 * Created by jaysen.lin@foxmail.com on 2017/11/1.
 */

public interface OnMoveAndSwipeListener {
    void onMove(int preposition, int toPosition);

    void onItemDismiss(int position);
}
