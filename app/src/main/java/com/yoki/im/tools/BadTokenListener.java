package com.yoki.im.tools;

import android.widget.Toast;

import androidx.annotation.NonNull;

public interface BadTokenListener {
    void onBadTokenCaught(@NonNull Toast toast);
}
