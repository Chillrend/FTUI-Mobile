package com.rsypj.ftuimobile.connection.listener;

import com.rsypj.ftuimobile.model.DetailPDFModel;

import java.util.List;

/**
 * Created by Nur Hildayanti Utami on 24/02/2019.
 * feel free to contact me on nur.hildayanti.u@gmail.com
 */
public interface NavigationManager {
    //void ShowFragment(String tittle);
    void ShowFragment(DetailPDFModel data, String namePdf);

    void ShowFragment(DetailPDFModel detailPDFModel);
}
