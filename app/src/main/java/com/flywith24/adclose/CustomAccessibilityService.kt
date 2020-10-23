package com.flywith24.adclose

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo


/**
 * @author yyz (杨云召)
 * @date   2020/10/23
 * time   16:07
 * description
 */
class CustomAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {

        Log.i("yyz11", "onAccessibilityEvent: ${event.packageName}")
        performClick(findViewById())
    }

    override fun onInterrupt() {

    }

    /**
     * 根据getRootInActiveWindow查找包含当前text的控件
     */
    private fun findViewById(): List<AccessibilityNodeInfo>? {
        val info = rootInActiveWindow ?: return null
        val list: MutableList<AccessibilityNodeInfo> = ArrayList()

        list.addAll(info.findAccessibilityNodeInfosByViewId(("com.peakx.stickfight.mi:id/mimo_reward_close_img")))
        Log.i("yyz11", "findViewById:  ${list.size}")

        info.recycle()
        return list
    }

    private fun performClick(nodeInfos: List<AccessibilityNodeInfo>?) {
        if (nodeInfos != null && nodeInfos.isNotEmpty()) {
            var node: AccessibilityNodeInfo
            for (i in nodeInfos.indices) {
                node = nodeInfos[i]
                // 获得点击View的类型
                // 进行模拟点击
                if (node.isEnabled) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    return
                }
            }
        }
    }
}