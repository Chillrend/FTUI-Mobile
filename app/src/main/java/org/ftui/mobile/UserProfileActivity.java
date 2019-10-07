package org.ftui.mobile;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.fragment.Home;
import org.ftui.mobile.model.CompleteUser;

public class UserProfileActivity extends AppCompatActivity {

    private TextView userFullname, userRole, userNoIdentity, userFaculty, userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userFullname = findViewById(R.id.user_full_name);
        userRole = findViewById(R.id.user_role);
        userNoIdentity = findViewById(R.id.user_identity_number);
        userFaculty = findViewById(R.id.user_department);
        userEmail = findViewById(R.id.user_email);

        if(!LoginActivity.completeUserPrefExist(this)){
            finish();
            Toasty.error(this, "Not authorized").show();
        }
        Gson gson = new Gson();
        CompleteUser user = gson.fromJson(getSharedPreferences(Home.COMPLETE_USER_SHARED_PREFERENCES, MODE_PRIVATE).getString("complete_user", null), CompleteUser.class);

        userFullname.setText(user.getName());
        userRole.setText(user.getIdentitas());
        userEmail.setText(user.getEmail());

        if(user.getNoidentitas() == null || user.getNoidentitas().equals("")){
            userNoIdentity.setText("No identity number supplied");
        }else{
            userNoIdentity.setText(user.getNoidentitas());
        }

        if(user.getStudyprog() == null || user.getStudyprog().equals("")){
            userFaculty.setText("No study program supplied");
        }else{
            userFaculty.setText(user.getStudyprog());
        }

    }
}
