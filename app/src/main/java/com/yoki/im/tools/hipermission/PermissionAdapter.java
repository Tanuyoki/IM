package com.yoki.im.tools.hipermission;

import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoki.im.R;

import java.util.List;

public class PermissionAdapter extends BaseAdapter {
    private List<PermissionItem> mData;
    private int mFilterColor;
    private int mTextColor;

    public PermissionAdapter(List<PermissionItem> data) {
        this.mData = data;
    }

    public int getCount() {
        return this.mData.size();
    }

    public Object getItem(int position) {
        return this.mData.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PermissionItem item = this.mData.get(position);
//        View view = View.inflate(parent.getContext(), R.layout.permission_info_item, null);
//        int blue = Color.blue(this.mFilterColor);
//        int green = Color.green(this.mFilterColor);
//        int red = Color.red(this.mFilterColor);
//        ImageView icon = (ImageView) view.findViewById(R.id.icon);
//        icon.setColorFilter(new ColorMatrixColorFilter(new float[]{1.0f, 0.0f, 0.0f, 0.0f, (float) red, 0.0f, 1.0f, 0.0f, 0.0f, (float) green, 0.0f, 0.0f, 1.0f, 0.0f, (float) blue, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f}));
//        TextView name = (TextView) view.findViewById(R.id.name);
//        if (this.mTextColor != 0) {
//            name.setTextColor(this.mTextColor);
//        }
//        icon.setImageResource(item.PermissionIconRes);
//        name.setText(item.PermissionName);
//        return view;
        return null;
    }

    public void setTextColor(int itemTextColor) {
        this.mTextColor = itemTextColor;
        notifyDataSetChanged();
    }

    public void setFilterColor(int filterColor) {
        this.mFilterColor = filterColor;
        notifyDataSetChanged();
    }
}
