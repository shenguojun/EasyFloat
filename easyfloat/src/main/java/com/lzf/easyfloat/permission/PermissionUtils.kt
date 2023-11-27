package com.lzf.easyfloat.permission

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.lzf.easyfloat.interfaces.OnPermissionResult
import com.lzf.easyfloat.permission.rom.MeizuUtils
import com.lzf.easyfloat.permission.rom.RomUtils

/**
 * @author: liuzhenfeng
 * @function: 悬浮窗权限工具类
 * @date: 2019-07-15  10:22
 */
object PermissionUtils {

    internal const val requestCode = 199
    private const val TAG = "PermissionUtils--->"

    /**
     * 检测是否有悬浮窗权限
     * 6.0 版本之后由于 google 增加了对悬浮窗权限的管理，所以方式就统一了
     */
    @JvmStatic
    fun checkPermission(context: Context): Boolean =
        commonROMPermissionCheck(context)

    /**
     * 申请悬浮窗权限
     */
    @JvmStatic
    fun requestPermission(activity: Activity, onPermissionResult: OnPermissionResult) =
        PermissionFragment.requestPermission(activity, onPermissionResult)

    internal fun requestPermission(fragment: Fragment) =
        commonROMPermissionApply(fragment)


    private fun meizuPermissionCheck(context: Context) =
        MeizuUtils.checkFloatWindowPermission(context)


    /**
     * 6.0以后，通用悬浮窗权限检测
     * 但是魅族6.0的系统这种方式不好用，需要单独适配一下
     */
    private fun commonROMPermissionCheck(context: Context): Boolean =
        if (RomUtils.checkIsMeizuRom()) meizuPermissionCheck(context) else
            Settings.canDrawOverlays(context)

    /**
     * 通用 rom 权限申请
     */
    private fun commonROMPermissionApply(fragment: Fragment) = when {
        // 这里也一样，魅族系统需要单独适配
        RomUtils.checkIsMeizuRom() -> MeizuUtils.applyPermission(fragment)
        // 需要做统计效果
        else -> commonROMPermissionApplyInternal(fragment)
    }

    @JvmStatic
    fun commonROMPermissionApplyInternal(fragment: Fragment) {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        intent.data = Uri.parse("package:${fragment.context.packageName}")
        fragment.startActivityForResult(intent, requestCode)
    }

}

