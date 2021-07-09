package com.yoki.im.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yoki.im.BuildConfig;
import java.lang.reflect.Field;

public class CustomLinearLayoutManager extends LinearLayoutManager {
    private static final int CHILD_HEIGHT = 1;
    private static final int CHILD_WIDTH = 0;
    private static final int DEFAULT_CHILD_SIZE = 100;
    private static boolean canMakeInsetsDirty = true;
    private static Field insetsDirtyField = null;
    private final int[] childDimensions;
    private int childSize;
    private int fixedCount;
    private boolean hasChildSize;
    private int overScrollMode;
    private final Rect tmpRect;
    private final RecyclerView view;

    public CustomLinearLayoutManager(Context context) {
        super(context);
        this.fixedCount = -1;
        this.childDimensions = new int[2];
        this.childSize = 100;
        this.overScrollMode = 0;
        this.tmpRect = new Rect();
        setAutoMeasureEnabled(false);
        this.view = null;
    }

    public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        this.fixedCount = -1;
        this.childDimensions = new int[2];
        this.childSize = 100;
        this.overScrollMode = 0;
        this.tmpRect = new Rect();
        setAutoMeasureEnabled(false);
        this.view = null;
    }

    public CustomLinearLayoutManager(RecyclerView view2) {
        super(view2.getContext());
        this.fixedCount = -1;
        this.childDimensions = new int[2];
        this.childSize = 100;
        this.overScrollMode = 0;
        this.tmpRect = new Rect();
        setAutoMeasureEnabled(false);
        this.view = view2;
        this.overScrollMode = ViewCompat.getOverScrollMode(view2);
    }

    public CustomLinearLayoutManager(RecyclerView view2, int orientation, boolean reverseLayout) {
        super(view2.getContext(), orientation, reverseLayout);
        this.fixedCount = -1;
        this.childDimensions = new int[2];
        this.childSize = 100;
        this.overScrollMode = 0;
        this.tmpRect = new Rect();
        setAutoMeasureEnabled(false);
        this.view = view2;
        this.overScrollMode = ViewCompat.getOverScrollMode(view2);
    }

    public void setOverScrollMode(int overScrollMode2) {
        if (overScrollMode2 < 0 || overScrollMode2 > 2) {
            throw new IllegalArgumentException("Unknown overscroll mode: " + overScrollMode2);
        } else if (this.view == null) {
            throw new IllegalStateException("view == null");
        } else {
            this.overScrollMode = overScrollMode2;
            ViewCompat.setOverScrollMode(this.view, overScrollMode2);
        }
    }

    public static int makeUnspecifiedSpec() {
        return View.MeasureSpec.makeMeasureSpec(0, 0);
    }

    public void setFixedCountItem(int count) {
        this.fixedCount = count;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        int width;
        int height;
        int i;
        int widthMode = View.MeasureSpec.getMode(widthSpec);
        int heightMode = View.MeasureSpec.getMode(heightSpec);
        int widthSize = View.MeasureSpec.getSize(widthSpec);
        int heightSize = View.MeasureSpec.getSize(heightSpec);
        boolean hasWidthSize = widthMode != 0;
        boolean hasHeightSize = heightMode != 0;
        boolean exactWidth = widthMode == 1073741824;
        boolean exactHeight = heightMode == 1073741824;
        int unspecified = makeUnspecifiedSpec();
        if (!exactWidth || !exactHeight) {
            boolean vertical = getOrientation() == 1;
            initChildDimensions(widthSize, heightSize, vertical);
            int width2 = 0;
            int height2 = 0;
            recycler.clear();
            int stateItemCount = state.getItemCount();
            int adapterItemCount = getItemCount();
            for (int i2 = 0; i2 < adapterItemCount; i2++) {
                if (vertical) {
                    if (!this.hasChildSize) {
                        if (i2 < stateItemCount) {
                            measureChild(recycler, i2, widthSize, unspecified, this.childDimensions);
                        } else {
                            logMeasureWarning(i2);
                        }
                    }
                    if (this.fixedCount != -1 && this.fixedCount >= i2) {
                        height2 += this.childDimensions[1];
                    }
                    if (i2 == 0) {
                        width2 = this.childDimensions[0];
                    }
                    if (hasHeightSize && height2 >= heightSize) {
                        break;
                    }
                } else {
                    if (!this.hasChildSize) {
                        if (i2 < stateItemCount) {
                            measureChild(recycler, i2, unspecified, heightSize, this.childDimensions);
                        } else {
                            logMeasureWarning(i2);
                        }
                    }
                    width2 += this.childDimensions[0];
                    if (i2 == 0) {
                        height2 = this.childDimensions[1];
                    }
                    if (hasWidthSize && width2 >= widthSize) {
                        break;
                    }
                }
            }
            if (exactWidth) {
                width = widthSize;
            } else {
                width = width2 + getPaddingLeft() + getPaddingRight();
                if (hasWidthSize) {
                    width = Math.min(width, widthSize);
                }
            }
            if (exactHeight) {
                height = heightSize;
            } else {
                height = height2 + getPaddingTop() + getPaddingBottom();
                if (hasHeightSize) {
                    height = Math.min(height, heightSize);
                }
            }
            setMeasuredDimension(width, height);
            if (this.view != null && this.overScrollMode == 1) {
                boolean fit = (vertical && (!hasHeightSize || height < heightSize)) || (!vertical && (!hasWidthSize || width < widthSize));
                RecyclerView recyclerView = this.view;
                if (fit) {
                    i = 2;
                } else {
                    i = 0;
                }
                ViewCompat.setOverScrollMode(recyclerView, i);
                return;
            }
            return;
        }
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }

    private void logMeasureWarning(int child) {
        if (BuildConfig.DEBUG) {
            Log.w("LinearLayoutManager", "Can't measure child #" + child + ", previously used dimensions will be reused.To remove this message either use #setChildSize() method or don't run RecyclerView animations");
        }
    }

    private void initChildDimensions(int width, int height, boolean vertical) {
        if (this.childDimensions[0] != 0 || this.childDimensions[1] != 0) {
            return;
        }
        if (vertical) {
            this.childDimensions[0] = width;
            this.childDimensions[1] = this.childSize;
            return;
        }
        this.childDimensions[0] = this.childSize;
        this.childDimensions[1] = height;
    }

    @Override // android.support.v7.widget.LinearLayoutManager
    public void setOrientation(int orientation) {
        if (!(this.childDimensions == null || getOrientation() == orientation)) {
            this.childDimensions[0] = 0;
            this.childDimensions[1] = 0;
        }
        super.setOrientation(orientation);
    }

    public void clearChildSize() {
        this.hasChildSize = false;
        setChildSize(100);
    }

    public void setChildSize(int childSize2) {
        this.hasChildSize = true;
        if (this.childSize != childSize2) {
            this.childSize = childSize2;
            requestLayout();
        }
    }

    private void measureChild(RecyclerView.Recycler recycler, int position, int widthSize, int heightSize, int[] dimensions) {
        try {
            View child = recycler.getViewForPosition(position);
            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) child.getLayoutParams();
            int hPadding = getPaddingLeft() + getPaddingRight();
            int vPadding = getPaddingTop() + getPaddingBottom();
            int hMargin = p.leftMargin + p.rightMargin;
            int vMargin = p.topMargin + p.bottomMargin;
            makeInsetsDirty(p);
            calculateItemDecorationsForChild(child, this.tmpRect);
            child.measure(getChildMeasureSpec(widthSize, hPadding + hMargin + getRightDecorationWidth(child) + getLeftDecorationWidth(child), p.width, canScrollHorizontally()), getChildMeasureSpec(heightSize, vPadding + vMargin + getTopDecorationHeight(child) + getBottomDecorationHeight(child), p.height, canScrollVertically()));
            dimensions[0] = getDecoratedMeasuredWidth(child) + p.leftMargin + p.rightMargin;
            dimensions[1] = getDecoratedMeasuredHeight(child) + p.bottomMargin + p.topMargin;
            makeInsetsDirty(p);
            recycler.recycleView(child);
        } catch (IndexOutOfBoundsException e) {
            if (BuildConfig.DEBUG) {
                Log.w("LinearLayoutManager", "LinearLayoutManager doesn't work well with animations. Consider switching them off", e);
            }
        }
    }

    private static void makeInsetsDirty(RecyclerView.LayoutParams p) {
        if (canMakeInsetsDirty) {
            try {
                if (insetsDirtyField == null) {
                    insetsDirtyField = RecyclerView.LayoutParams.class.getDeclaredField("mInsetsDirty");
                    insetsDirtyField.setAccessible(true);
                }
                insetsDirtyField.set(p, true);
            } catch (NoSuchFieldException e) {
                onMakeInsertDirtyFailed();
            } catch (IllegalAccessException e2) {
                onMakeInsertDirtyFailed();
            }
        }
    }

    private static void onMakeInsertDirtyFailed() {
        canMakeInsetsDirty = false;
        if (BuildConfig.DEBUG) {
            Log.w("LinearLayoutManager", "Can't make LayoutParams insets dirty, decorations measurements might be incorrect");
        }
    }
}
