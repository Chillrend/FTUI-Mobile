package com.rsypj.ftuimobile.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.connection.LauncherRequest;
import com.rsypj.ftuimobile.connection.listener.NavigationManager;
import com.rsypj.ftuimobile.connection.listener.PDFDetailCallBack;
import com.rsypj.ftuimobile.helper.FragmentNavigationManager;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.model.BookMenuModel;
import com.rsypj.ftuimobile.model.DetailPDFModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcademicGuideBookActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    PDFView pdfView;
    //private AcademicBookController controller;
    public static final String KEY_PDF="pdf_content";

    FrameLayout frameLayout;
    Toolbar toolbar;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    private String mActivityTitle;
    private String[] items;

    ExpandableListView expandableListView;
    ExpandableListAdapter adapter;
    List<String> lstTitle;
    Map<String, List<String>> lstChild;
    NavigationManager navigationManager;

    List<BookMenuModel> headerList = new ArrayList<>();
    HashMap<BookMenuModel, List<BookMenuModel>> childList = new HashMap<>();

    ArrayList<DetailPDFModel> data = new ArrayList<>();

    String namepdf;

    ImageView imageView;

    private static boolean RUN_ONCE = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_academic_guide_book);

        imageView = findViewById(R.id.cov);

        imageView.setVisibility(View.VISIBLE);


        if (savedInstanceState == null) {

            //selectFirstItemAsDefault();
        }

        //runOnce();

        Helper.idpdf = getIntent().getStringExtra(Helper.DATA);

        drawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        expandableListView = findViewById(R.id.expandableListView);



        navigationManager = FragmentNavigationManager.getmInstance(this);

        prepareMenuData();

        View listHeaderview = getLayoutInflater().inflate(R.layout.nav_header_main, null,false);
        expandableListView.addHeaderView(listHeaderview);

        //genData();

        populateExpandableList();
        onRequestDataCategory();

        Log.d("onCreate: ", String.valueOf(data.size()));


        //addDrawersItem();
        setUpDrawer();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Academic Guide Book");


        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Academic Guide Book");
                invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(getmActivityTitle());
                invalidateOptionsMenu();
            }
        };

    }

    private void onRequestDataCategory() {
        new LauncherRequest(this).onRequestDetailPDF(new PDFDetailCallBack() {
            @Override
            public void onDataSetResult(ArrayList<DetailPDFModel> response) {
                data.addAll(response);
                navigationManager.ShowFragment(data.get(0), data.get(0).getPdf());
                imageView.setVisibility(View.GONE);
            }

            @Override
            public void onDataIsEmpty() {
                Toast.makeText(AcademicGuideBookActivity.this, "Data Not Found!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestError(String errorMessage) {
                Toast.makeText(AcademicGuideBookActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

            }
        });

        Log.d("onDataSetResult: ", String.valueOf(data.size()));

    }

    private void populateExpandableList() {
        adapter = new com.rsypj.ftuimobile.adapter.ExpandableListAdapter(this,headerList,childList);

        expandableListView.setAdapter(adapter);

        //imageView.setVisibility(View.GONE);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                imageView.setVisibility(View.GONE);

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        String selectedItem = headerList.get(groupPosition).getMenuName();
                        getSupportActionBar().setTitle(selectedItem);



                        if (headerList.get(groupPosition).getId()==1)
                            navigationManager.ShowFragment(data.get(0), selectedItem);

                        else if (headerList.get(groupPosition).getId()==2)
                            navigationManager.ShowFragment(data.get(1), selectedItem);

                        else if (headerList.get(groupPosition).getId()==3)
                            navigationManager.ShowFragment(data.get(2), selectedItem);

                        else if (headerList.get(groupPosition).getId()==4)
                            navigationManager.ShowFragment(data.get(3), selectedItem);

                        else if (headerList.get(groupPosition).getId()==5)
                            navigationManager.ShowFragment(data.get(4), selectedItem);

                        else if (headerList.get(groupPosition).getId()==6)
                            navigationManager.ShowFragment(data.get(5), selectedItem);

                        else if (headerList.get(groupPosition).getId()==7)
                            navigationManager.ShowFragment(data.get(27), selectedItem);

                        else
                            throw new IllegalArgumentException("Not supported fragment");

                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //getSupportActionBar().setTitle(lstTitle.get(groupPosition));
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("Academic Guide Book");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                imageView.setVisibility(View.GONE);

                if (childList.get(headerList.get(groupPosition)) != null) {
                    BookMenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);

                    String selectedItem = model.getMenuName();

                    Log.d("headerlist: ", selectedItem);


                    getSupportActionBar().setTitle(selectedItem);

                    ArrayList<DetailPDFModel> tempSubMenu = getDetailDataSubMenu(selectedItem);
                    if (tempSubMenu.size()>1 && headerList.get(groupPosition).getId() == 6){
                        navigationManager.ShowFragment(tempSubMenu.get(1), tempSubMenu.get(1).getPdf());
                    }else{
                        navigationManager.ShowFragment(tempSubMenu.get(0), tempSubMenu.get(0).getPdf());
                    }

                    /*
                    TODO:: Change this code to dynamic mapping from title becasue this data is static
                    if (selectedItem.equals("Civil Engineering"))
                        navigationManager.ShowFragment(data.get(3), data.get(3).getPdf());

                    else if (selectedItem.equals("Enviromental Engineering"))
                        navigationManager.ShowFragment(data.get(4), data.get(4).getPdf());

                    else if (selectedItem.equals("Mechanical Engineering"))
                        navigationManager.ShowFragment(data.get(5), data.get(5).getPdf());

                    else if (selectedItem.equals("Naval Architecture And Marine Engineering"))
                        navigationManager.ShowFragment(data.get(6), data.get(6).getPdf());

                    else if (selectedItem.equals("Electrical Engineering"))
                        navigationManager.ShowFragment(data.get(7), data.get(7).getPdf());

                    else if (selectedItem.equals("Computer Engineering"))
                        navigationManager.ShowFragment(data.get(8), data.get(8).getPdf());

                    else if (selectedItem.equals("Biomedical Engineering"))
                        navigationManager.ShowFragment(data.get(9), data.get(9).getPdf());

                    else if (selectedItem.equals("Metallurgy & Materials Engineering"))
                        navigationManager.ShowFragment(data.get(10), data.get(10).getPdf());

                    else if (selectedItem.equals("Architecture"))
                        navigationManager.ShowFragment(data.get(11), data.get(11).getPdf());

                    else if (selectedItem.equals("Interior Architecture"))
                        navigationManager.ShowFragment(data.get(12), data.get(12).getPdf());

                    else if (selectedItem.equals("Chemical Engineering"))
                        navigationManager.ShowFragment(data.get(13), data.get(13).getPdf());

                    else if (selectedItem.equals("Bioprocess Engineering"))
                        navigationManager.ShowFragment(data.get(14), data.get(14).getPdf());

                    else if (selectedItem.equals("Industrial Engineering"))
                        navigationManager.ShowFragment(data.get(15), data.get(15).getPdf());

                    else if (selectedItem.equals("Architect"))
                        navigationManager.ShowFragment(data.get(16), data.get(16).getPdf());

                    else if (selectedItem.equals("Engineers"))
                        navigationManager.ShowFragment(data.get(17), data.get(17).getPdf());

                    else if (selectedItem.equals("Energy System Engineering"))
                        navigationManager.ShowFragment(data.get(18), data.get(18).getPdf());

                    else if (selectedItem.equals("Civil Engineering"))
                        navigationManager.ShowFragment(data.get(19), data.get(19).getPdf());

                    else if (selectedItem.equals("Mechanical Engineering"))
                        navigationManager.ShowFragment(data.get(20), data.get(20).getPdf());

                    else if (selectedItem.equals("Electrical Engineering"))
                        navigationManager.ShowFragment(data.get(21), data.get(21).getPdf());

                    else if (selectedItem.equals("Biomedical Technology"))
                        navigationManager.ShowFragment(data.get(22), data.get(22).getPdf());

                    else if (selectedItem.equals("Metallurgy And Materials Engineering"))
                        navigationManager.ShowFragment(data.get(23), data.get(23).getPdf());

                    else if (selectedItem.equals("Architecture"))
                        navigationManager.ShowFragment(data.get(24), data.get(24).getPdf());

                    else if (selectedItem.equals("Chemical Engineering"))
                        navigationManager.ShowFragment(data.get(25), data.get(25).getPdf());

                    else if (selectedItem.equals("Industrial Engineering"))
                        navigationManager.ShowFragment(data.get(26), data.get(26).getPdf());

                    else
                        throw new IllegalArgumentException("Not supported fragment");

                     */

                    /*if (headerList.get(0).equals(headerList.get(groupPosition)))

                    {
                        //navigationManager.ShowFragment(selectedItem);
                        //navigationManager.ShowFragment(data.get(0), data.get(0).getPdf());

                        Log.d("headerlist: ", String.valueOf(headerList.get(0)));
                        Log.d("groupposition: ", String.valueOf(groupPosition));
                        Log.d("headerlistgroup: ", String.valueOf(headerList.get(groupPosition)));
                        Log.d("data: ", data.get(0).getPdf());

                    }


                    else if (headerList.get(1).equals(headerList.get(groupPosition)))
                        //navigationManager.ShowFragment(selectedItem);
                        navigationManager.ShowFragment(data.get(1),data.get(1).getPdf());
                    else if (headerList.get(2).equals(headerList.get(groupPosition)))
                        //navigationManager.ShowFragment(selectedItem);
                        navigationManager.ShowFragment(data.get(2),data.get(2).getPdf());
                    else if (headerList.get(3).equals(headerList.get(groupPosition)))
                        //navigationManager.ShowFragment(selectedItem);
                        navigationManager.ShowFragment(data.get(3),data.get(3).getPdf());
                    else if (headerList.get(4).equals(headerList.get(groupPosition)))
                        //navigationManager.ShowFragment(selectedItem);
                        navigationManager.ShowFragment(data.get(4),data.get(4).getPdf());
                    else if (headerList.get(5).equals(headerList.get(groupPosition)))
                        //navigationManager.ShowFragment(selectedItem);
                        navigationManager.ShowFragment(data.get(5), data.get(5).getPdf());
                    else if (headerList.get(6).equals(headerList.get(groupPosition)))
                        //navigationManager.ShowFragment(selectedItem);
                        navigationManager.ShowFragment(data.get(6), data.get(6).getPdf());
                    else
                        throw new IllegalArgumentException("Not supported fragment");
*/
                    drawerLayout.closeDrawer(GravityCompat.START);

                }

                return false;

            }
        });
    }

    private ArrayList<DetailPDFModel> getDetailDataSubMenu(String text){
        ArrayList<DetailPDFModel> temp = new ArrayList();
        for(DetailPDFModel d: data){
            if(d.getNama().toLowerCase().equals(text.toLowerCase())){
                temp.add(d);
            }
        }
        return temp;
    }

    private void runOnce() {
        if (RUN_ONCE) {
            RUN_ONCE = false;
        }
        imageView.setVisibility(View.GONE);
    }

    private void prepareMenuData() {
        BookMenuModel menuModel = new BookMenuModel("Profile Of FTUI And Departments", true, false, 1); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new BookMenuModel("Academic System And Regulation", true, false, 2); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new BookMenuModel("Facilities and Campus Life", true, false, 3); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        List<BookMenuModel> childModelsList = new ArrayList<>();
        menuModel = new BookMenuModel("Undergraduate Program", true, true, 4); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        BookMenuModel childModel = new BookMenuModel("Civil Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Enviromental Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Mechanical Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Naval Architecture And Marine Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Electrical Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Computer Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Biomedical Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Metallurgy & Materials Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Architecture", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Interior Architecture", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Chemical Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Bioprocess Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Industrial Engineering", false, false, 1);
        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new BookMenuModel("Professional Program", true, true, 5); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        childModel = new BookMenuModel("Architect", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Engineers", false, false, 1);
        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new BookMenuModel("Master Program", true, true, 6); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        childModel = new BookMenuModel("Energy System Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Civil Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Mechanical Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Electrical Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Biomedical Technology", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Metallurgy And Materials Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Architecture", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Chemical Engineering", false, false, 1);
        childModelsList.add(childModel);
        childModel = new BookMenuModel("Industrial Engineering", false, false, 1);
        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        menuModel = new BookMenuModel("Doctoral Program", true, false, 7); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }


    }

    private void selectFirstItemAsDefault() {
        if (navigationManager != null)
        {

           imageView.setVisibility(View.VISIBLE);

            /*onRequestDataCategory();
            String firstItem = headerList.get(0).getMenuName();
            //navigationManager.ShowFragment(data.get(0), data.get(0).getPdf());
            navigationManager.ShowFragment(data.get(0), firstItem);
            Log.d("selectFhirst", data.get(0).getPdf());*/




        }
    }

    private void setUpDrawer() {
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Academic Guide Book");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle("Academic Guide Book");
                invalidateOptionsMenu();
            }
        };

        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);
    }

    public String getmActivityTitle(){
        return mActivityTitle;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pfd_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (toggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        toggle.onConfigurationChanged(newConfig);
    }
}
