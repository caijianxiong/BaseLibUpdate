package cjx.baselib.router;

import android.app.Activity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Router {
    Class<Activity> path();
}
