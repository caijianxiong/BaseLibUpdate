package cjx.liyueyun.baselib.base.mvp.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PermissionHelper {

private static final String TAG="Permission";
private static PermissionListener mPermissionListener;


    public static boolean hasPermissions(@NonNull Context context,
                                         @Size(min = 1) @NonNull String... perms) {
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.w(TAG, "hasPermissions: API version < M, returning true by default");

            // DANGER ZONE!!! Changing this will break the library.
            return true;
        }

        // Null context may be passed if we have detected Low API (less than M) so getting
        // to this point with a null context should not be possible.
        if (context == null) {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }

        for (String perm : perms) {
            if (ActivityCompat.checkSelfPermission(context, perm)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }


    public static void requestPermissions(Activity activity,String[] permissions,PermissionListener listener){
        mPermissionListener=listener;
        if (Build.VERSION.SDK_INT < 23) {
            mPermissionListener.onGranted();
            return;
        }
        //用于存放未授权的权限
        List<String> permissionList = new ArrayList<>();
        //遍历传递过来的权限集合
        for (String permission : permissions) {
            //判断是否已经授权
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                //未授权，则加入待授权的权限集合中
                permissionList.add(permission);
            }
        }

        //判断集合
        if (!permissionList.isEmpty()) {  //如果集合不为空，则需要去授权
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {  //为空，则已经全部授权
            mPermissionListener.onGranted();
        }

    }


    /**
     * 在activity 或者fragment中重写onRequestPermissionsResult方法，并调用下面方法，，将回调交给Helper处理
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    //被用户拒绝的权限集合
                    List<String> deniedPermissions = new ArrayList<>();
                    //用户通过的权限集合
                    List<String> grantedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        //获取授权结果，这是一个int类型的值
                        int grantResult = grantResults[i];

                        if (grantResult != PackageManager.PERMISSION_GRANTED) { //用户拒绝授权的权限
                            String permission = permissions[i];
                            deniedPermissions.add(permission);
                        } else {  //用户同意的权限
                            String permission = permissions[i];
                            grantedPermissions.add(permission);
                        }
                    }

                    if (deniedPermissions.isEmpty()) {  //用户拒绝权限为空
                        if (mPermissionListener != null)
                            mPermissionListener.onGranted();
                    } else {  //不为空
                        //回调授权失败的接口
                        if (mPermissionListener != null)
                            mPermissionListener.onDenied(deniedPermissions);
                        //回调授权成功的接口
                        if (mPermissionListener != null)
                            mPermissionListener.onGranted(grantedPermissions);
                    }
                }
                break;
            default:
                break;
        }

    }

    /**
     * 权限回调接口
     */
    public interface PermissionListener {
        //全部授权
        void onGranted();

        //部分授权成功
        void onGranted(List<String> grantedPermission);

        //部分拒绝授权
        void onDenied(List<String> deniedPermission);
    }

}
