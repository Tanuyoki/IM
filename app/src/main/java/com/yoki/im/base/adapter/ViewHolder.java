package com.yoki.im.base.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.yoki.im.tools.imageloader.ImageLoader;

public class ViewHolder extends RecyclerView.ViewHolder {
    private View mConvertView;
    private SparseArray<RecyclerView> mRecyclerView = new SparseArray<>();
    private SparseArray<View> mViews = new SparseArray<>();

    private ViewHolder(View itemView) {
        super(itemView);
        this.mConvertView = itemView;
    }

    public static ViewHolder create(Context context, int layoutId, ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        itemView.setTag(Integer.valueOf(viewType));
        return new ViewHolder(itemView);
    }

    public static ViewHolder create(View itemView) {
        return new ViewHolder(itemView);
    }

    public int getTag() {
        return ((Integer) this.mConvertView.getTag()).intValue();
    }

    public <T extends View> T getView(int viewId) {
        T t = (T) this.mViews.get(viewId);
        if (t != null) {
            return t;
        }
        T t2 = (T) this.mConvertView.findViewById(viewId);
        this.mViews.put(viewId, t2);
        return t2;
    }

    public <T extends RecyclerView> T getRecyclerView(int viewId) {
        T t = (T) this.mRecyclerView.get(viewId);
        if (t != null) {
            return t;
        }
        T t2 = (T) ((RecyclerView) this.mConvertView.findViewById(viewId));
        this.mViews.put(viewId, t2);
        return t2;
    }

    public View getConvertView() {
        return this.mConvertView;
    }

    public View getSwipeView() {
        ViewGroup itemLayout = (ViewGroup) this.mConvertView;
        if (itemLayout.getChildCount() == 2) {
            return itemLayout.getChildAt(1);
        }
        return null;
    }

    public void setText(int viewId, String text) {
        ((TextView) getView(viewId)).setText(text);
    }

    public void setText(int viewId, Spanned text) {
        ((TextView) getView(viewId)).setText(text);
    }

    public void setText(int viewId, SpannableStringBuilder ssb) {
        ((TextView) getView(viewId)).setText(ssb);
    }

    public void setTextColor(int viewId, int colorId) {
        ((TextView) getView(viewId)).setTextColor(colorId);
    }

    public void setTextColor(int viewId, int id, Context context) {
        ((TextView) getView(viewId)).setTextColor(ContextCompat.getColor(context, id));
    }

    public void setVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
    }

    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        getView(viewId).setOnClickListener(clickListener);
    }

    public void setSelected(int viewId, boolean selected) {
        getView(viewId).setSelected(selected);
    }

    public void setEnabled(int viewId, boolean b) {
        getView(viewId).setEnabled(b);
    }

    public void setTag(int viewId, Object tag) {
        getView(viewId).setTag(tag);
    }

    public int getWidth(int viewId) {
        return getView(viewId).getWidth();
    }

    public int getHeight(int viewId) {
        return getView(viewId).getHeight();
    }

    public boolean isSelected(int viewId) {
        return getView(viewId).isSelected();
    }

    public void setBackgroundResource(int viewId, int resId) {
        getView(viewId).setBackgroundResource(resId);
    }

    public void setBackgroundDrawable(int viewId, Drawable drawable) {
        getView(viewId).setBackgroundDrawable(drawable);
    }

    public void setImageDrawable(int imgId, Drawable drawable) {
        ((ImageView) getView(imgId)).setImageDrawable(drawable);
    }

    public void loadImage(int viewId, int resId) {
        ImageLoader.getInstance().loadImage(Integer.valueOf(resId), (ImageView) getView(viewId));
    }

    public void loadImageCircle(int viewId, int resId) {
        ImageLoader.getInstance().loadImageCircle(Integer.valueOf(resId), (ImageView) getView(viewId));
    }

    public void loadImageCircleNetwork(int viewId, String url) {
        ImageLoader.getInstance().loadImageCircleNetwork(url, (ImageView) getView(viewId));
    }

    public void setAdapter(int viewId, RecyclerView.Adapter adapter) {
        getRecyclerView(viewId).setAdapter(adapter);
    }

    public void addItemDecoration(int viewId, RecyclerView.ItemDecoration decor) {
        getRecyclerView(viewId).addItemDecoration(decor);
    }
}
