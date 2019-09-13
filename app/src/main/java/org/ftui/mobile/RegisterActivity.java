package org.ftui.mobile;

import android.content.Intent;
import android.os.Build;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    TextView helloMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Intent received = getIntent();

        JSONObject receivedHtmlResponse;
        String name = "";
        try {
            receivedHtmlResponse = new JSONObject(received.getStringExtra("response"));
            name = receivedHtmlResponse.getString("name");
        }catch (JSONException e){
            e.printStackTrace();
        }

        helloMessage = findViewById(R.id.success_verif_text);
        name = getResources().getString(R.string.hello_text, name);
        helloMessage.setText(name);


    }
}
