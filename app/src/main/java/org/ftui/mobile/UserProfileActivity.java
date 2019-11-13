package org.ftui.mobile;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.fragment.Home;
import org.ftui.mobile.model.CompleteUser;
import org.ftui.mobile.utils.SPService;

public class UserProfileActivity extends AppCompatActivity {

    private TextView userFullname, userRole, userNoIdentity, userFaculty, userEmail;
    private SPService sharedPreferenceService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        sharedPreferenceService = new SPService(this);

        userFullname = findViewById(R.id.user_full_name);
        userRole = findViewById(R.id.user_role);
        userNoIdentity = findViewById(R.id.user_identity_number);
        userFaculty = findViewById(R.id.user_department);
        userEmail = findViewById(R.id.user_email);

        if(!sharedPreferenceService.isCompleteSpExist()){
            finish();
            Toasty.error(this, "Not authorized").show();
        }

        CompleteUser user = sharedPreferenceService.getCompleteUserFromSp();
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
