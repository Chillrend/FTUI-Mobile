package org.ftui.mobile.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.RegisterActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class JSInterceptor {

    private Context ctx;
    public JSInterceptor(Context ctx){
        this.ctx = ctx;
    }

    @JavascriptInterface
    public void showHTML(String html){
        Log.d("RESPONSE : ", html);
        try{
            JSONObject response = new JSONObject(html);
            //for now we allow guest login, use ifs below on implementation and finish activity
            //if(response.getBoolean("eligible"));
            Intent i = new Intent(ctx, RegisterActivity.class);
            i.putExtra("response", html);
            ctx.startActivity(i);
            ((Activity) ctx).finish();
        }catch (JSONException e){
            //debug info only, remove this and leave the catch blank on implementatiion
            Toasty.error(ctx, "Not a valid json response", Toasty.LENGTH_LONG).show();
        }
    }

}
