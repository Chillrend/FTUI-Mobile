package org.ftui.mobile;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.fragment.EKeluhan;
import org.ftui.mobile.fragment.Home;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Home.OnFragmentInteractionListener,
        EKeluhan.OnFragmentInteractionListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new Home();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_frame, fragment, Home.HOME_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager fm = getSupportFragmentManager();
        Home home = (Home) getSupportFragmentManager().findFragmentByTag(Home.HOME_FRAGMENT_TAG);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(home != null && home.isVisible()){
            super.onBackPressed();
        }else if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fr = new Fragment();
        String fr_tag = "";

        if(id == R.id.nav_home){
            fr = new Home();
            fr_tag = Home.HOME_FRAGMENT_TAG;
            replaceFragment(fr, fr_tag);
        }else if(id == R.id.nav_complaint){
            fr = new EKeluhan();
            fr_tag = EKeluhan.EKELUHAN_FRAGMENT_TAG;
            replaceFragment(fr, fr_tag);
        }else if(id == R.id.nav_logout){
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }else if(id == R.id.nav_map){
            Intent i = new Intent(this, CampusMap.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment, String tag){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, fragment, tag)
                .addToBackStack(null)
                .commit();
    }


    //FrickOff
    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
