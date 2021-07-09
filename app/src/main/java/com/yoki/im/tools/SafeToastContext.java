package com.yoki.im.tools;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* access modifiers changed from: package-private */
public final class SafeToastContext extends ContextWrapper {
    @Nullable
    private BadTokenListener badTokenListener;
    @NonNull
    private Toast toast;

    SafeToastContext(@NonNull Context base, @NonNull Toast toast2) {
        super(base);
        this.toast = toast2;
    }

    public Context getApplicationContext() {
        return new ApplicationContextWrapper(getBaseContext().getApplicationContext());
    }

    public void setBadTokenListener(@NonNull BadTokenListener badTokenListener2) {
        this.badTokenListener = badTokenListener2;
    }

    private final class ApplicationContextWrapper extends ContextWrapper {
        private ApplicationContextWrapper(@NonNull Context base) {
            super(base);
        }

        @Override // android.content.Context, android.content.ContextWrapper
        public Object getSystemService(@NonNull String name) {
            if ("window".equals(name)) {
                return new WindowManagerWrapper((WindowManager) getBaseContext().getSystemService(name));
            }
            return super.getSystemService(name);
        }
    }

    private final class WindowManagerWrapper implements WindowManager {
        private static final String TAG = "WindowManagerWrapper";
        @NonNull
        private final WindowManager base;

        private WindowManagerWrapper(@NonNull WindowManager base2) {
            this.base = base2;
        }

        public Display getDefaultDisplay() {
            return this.base.getDefaultDisplay();
        }

        public void removeViewImmediate(View view) {
            this.base.removeViewImmediate(view);
        }

        public void addView(View view, ViewGroup.LayoutParams params) {
            try {
                Log.d(TAG, "WindowManager's addView(view, params) has been hooked.");
                this.base.addView(view, params);
            } catch (BadTokenException e) {
                Log.i(TAG, e.getMessage());
                if (SafeToastContext.this.badTokenListener != null) {
                    SafeToastContext.this.badTokenListener.onBadTokenCaught(SafeToastContext.this.toast);
                }
            } catch (Throwable throwable) {
                Log.e(TAG, "[addView]", throwable);
            }
        }

        public void updateViewLayout(View view, ViewGroup.LayoutParams params) {
            this.base.updateViewLayout(view, params);
        }

        public void removeView(View view) {
            this.base.removeView(view);
        }
    }
}
