package com.rsypj.ftuimobile.ui.fragment;

import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseFragment;
import com.rsypj.ftuimobile.ui.fragment.controller.PDFReaderController;

/**
 * Author By dhadotid
 * Date 13/07/2018.
 */
public class PDFReaderFragment extends SmartSipBaseFragment implements OnPageChangeListener, OnLoadCompleteListener {

    PDFView pdfView;
    private PDFReaderController controller;
    @Override
    public int getLayoutID() {
        return R.layout.fragment_pdf_reader;
    }

    @Override
    public void initItem(View v) {
        //Helper.idpdf = idnya;
        pdfView = v.findViewById(R.id.fragment_pdf_reader_pdfView);

        controller = new PDFReaderController(this);
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        controller.onPageChanged(page, pageCount);
    }

    @Override
    public void loadComplete(int nbPages) {
        controller.loadComplete(nbPages);
    }

    //set id of pdf R.id.fragment_pdf_reader_pdfView
    public PDFView getPdfView() {
        return pdfView;
    }
}
