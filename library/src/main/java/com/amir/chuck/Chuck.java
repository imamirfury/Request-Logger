
package com.amir.chuck;

import android.content.Context;
import android.content.Intent;

import com.amir.chuck.internal.ui.MainActivity;


public class Chuck {

    /**
     * Get an Intent to launch the Chuck UI directly.
     *
     * @param context A Context.
     * @return An Intent for the main Chuck Activity that can be started with {@link Context#startActivity(Intent)}.
     */
    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}