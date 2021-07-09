package com.yoki.im.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.yoki.im.R;
import com.yoki.im.base.BaseActivity;
import com.yoki.im.base.BaseFragment;
import com.yoki.im.tools.SystemIntentUtils;

public class ImInputModeFragment extends BaseFragment implements View.OnClickListener {
    private ChatInputModeFragmentListener mListener;
    SystemIntentUtils.onIntentResultListener resultListener = new SystemIntentUtils.onIntentResultListener() {
        /* class com.acarbang.android.fragment.ImInputModeFragment.AnonymousClass1 */

        @Override // com.acarbang.android.tools.SystemIntentUtils.onIntentResultListener
        public void getPicture(String path) {
            if (ImInputModeFragment.this.mListener != null) {
                ImInputModeFragment.this.mListener.getPhoto(path);
            }
        }
    };

    public interface ChatInputModeFragmentListener {
        void getPhoto(String str);
    }

    public void setChatInputModeListener(ChatInputModeFragmentListener listener) {
        this.mListener = listener;
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_im_mode, (ViewGroup) null);
        view.findViewById(R.id.im_mode_container_picture).setOnClickListener(this);
        view.findViewById(R.id.im_mode_container_camera).setOnClickListener(this);
        view.findViewById(R.id.im_mode_container_order).setOnClickListener(this);
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_mode_container_camera /*{ENCODED_INT: 2131296866}*/:
                SystemIntentUtils.getCameraPicture((BaseActivity) getActivity(), null, this.resultListener);
                return;
            case R.id.im_mode_container_order /*{ENCODED_INT: 2131296867}*/:
            default:
                return;
            case R.id.im_mode_container_picture /*{ENCODED_INT: 2131296868}*/:
                SystemIntentUtils.getAlbumPicture((BaseActivity) getActivity(), this.resultListener);
                return;
        }
    }
}
