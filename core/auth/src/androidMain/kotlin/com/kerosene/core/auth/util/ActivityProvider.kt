package com.kerosene.core.auth.util

import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference

object ActivityProvider {
    private var activityRef: WeakReference<FragmentActivity>? = null

    var currentActivity: FragmentActivity?
        get() = activityRef?.get()
        set(value) {
            activityRef = value?.let { WeakReference(it) }
        }
}
