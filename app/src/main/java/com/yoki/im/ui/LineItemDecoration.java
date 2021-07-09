package com.yoki.im.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.yoki.im.tools.CommonData;

public class LineItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL_LIST = 0;
    public static final int VERTICAL_LIST = 1;
    private Context mContext;
    private Drawable mDivider;
    private Drawable mDividerSpacing;
    private int mHorizontalSpacing = 0;
    private int mOrientation;

    public LineItemDecoration(Context context, int orientation, int resId) {
        this.mContext = context;
        this.mDivider = this.mContext.getResources().getDrawable(resId);
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation == 0 || orientation == 1) {
            this.mOrientation = orientation;
            return;
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    public LineItemDecoration setHorizontalSpacing(int resId, int spacing) {
        this.mDividerSpacing = this.mContext.getResources().getDrawable(resId);
        this.mHorizontalSpacing = spacing;
        return this;
    }

    public LineItemDecoration setHorizontalSpacing(int resId) {
        this.mDividerSpacing = this.mContext.getResources().getDrawable(resId);
        this.mHorizontalSpacing = (int) (((double) CommonData.ScreenWidth) * 0.02d);
        return this;
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            drawVertical(c, parent, state);
        } else {
            drawHorizontal(c, parent, state);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int left = child.getRight() + ((RecyclerView.LayoutParams) child.getLayoutParams()).rightMargin;
            this.mDivider.setBounds(left, top, left + this.mDivider.getIntrinsicWidth(), bottom);
            this.mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + this.mHorizontalSpacing;
        int right = (parent.getWidth() - parent.getPaddingRight()) - this.mHorizontalSpacing;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            int top = child.getBottom() + ((RecyclerView.LayoutParams) child.getLayoutParams()).bottomMargin;
            int bottom = top + this.mDivider.getIntrinsicHeight();
            this.mDivider.setBounds(left, top, right, bottom);
            this.mDivider.draw(c);
            drawSpacing(c, left, top, right, bottom);
        }
    }

    public void drawSpacing(Canvas c, int left, int top, int right, int bottom) {
        if (this.mDividerSpacing != null) {
            this.mDividerSpacing.setBounds(left - this.mHorizontalSpacing, top, left, bottom);
            this.mDividerSpacing.draw(c);
            this.mDividerSpacing.setBounds(right, top, this.mHorizontalSpacing + right, bottom);
            this.mDividerSpacing.draw(c);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            outRect.set(0, 0, this.mDivider.getIntrinsicWidth(), 0);
        } else {
            outRect.set(0, 0, 0, this.mDivider.getIntrinsicHeight());
        }
    }
}
