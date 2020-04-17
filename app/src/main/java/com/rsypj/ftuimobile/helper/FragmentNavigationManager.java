package com.rsypj.ftuimobile.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.rsypj.ftuimobile.BuildConfig;
import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.connection.listener.NavigationManager;
import com.rsypj.ftuimobile.model.DetailPDFModel;
import com.rsypj.ftuimobile.ui.RevisiPdfReaderActivityFragment;
import com.rsypj.ftuimobile.ui.activity.AcademicGuideBookActivity;

/**
 * Created by Nur Hildayanti Utami on 24/02/2019.
 * feel free to contact me on nur.hildayanti.u@gmail.com
 */
public class FragmentNavigationManager implements NavigationManager {
    private static FragmentNavigationManager mInstance;

    private FragmentManager fragmentManager;
    private AcademicGuideBookActivity academicGuideBookActivity;

    private RevisiPdfReaderActivityFragment revisiPdfReaderActivityFragment;

    public static FragmentNavigationManager getmInstance(AcademicGuideBookActivity academicGuideBookActivity){
        if (mInstance == null)
            mInstance = new FragmentNavigationManager();
        mInstance.configure(academicGuideBookActivity);
        return mInstance;
    }

    private void configure(AcademicGuideBookActivity academicGuideBookActivity) {
        academicGuideBookActivity = academicGuideBookActivity;

        fragmentManager = academicGuideBookActivity.getSupportFragmentManager();

    }


    private void showFragment(Fragment fragment, boolean allowstate) {
        FragmentManager manager = fragmentManager;
        FragmentTransaction fragmentTransaction = manager.beginTransaction().replace(R.id.container_pdf, fragment);
        fragmentTransaction.addToBackStack(null);
        if (allowstate || !BuildConfig.DEBUG)
            fragmentTransaction.commitAllowingStateLoss();
        else
            fragmentTransaction.commit();

        fragmentManager.executePendingTransactions();

    }

    @Override
    public void ShowFragment(DetailPDFModel data, String title) {
        showFragment(RevisiPdfReaderActivityFragment.newInstance(data,title),false);
    }

    @Override
    public void ShowFragment(DetailPDFModel data) {
       // showFragment(RevisiPdfReaderActivityFragment.newInstance(data),false);

    }
}
