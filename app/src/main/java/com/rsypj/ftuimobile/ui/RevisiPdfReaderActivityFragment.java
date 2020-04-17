package com.rsypj.ftuimobile.ui;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.DetailPDFModel;
import com.rsypj.ftuimobile.service.DownloadService;

import java.io.File;

/**
 * A placeholder fragment containing a simple view.
 */
public class RevisiPdfReaderActivityFragment extends Fragment implements OnPageChangeListener, OnLoadCompleteListener {

    public static final String KEY_TITTLE="Content";
    public static final String KEY_PDF="pdf_content";

    File pdfFile;
    PDFView pdfView;
    private int pageNumber = 0;
    ProgressDialog dialog;

    String namePdf;

    RevisiPdfReaderActivityFragment mContext;

    public RevisiPdfReaderActivityFragment() {
    }

    public static RevisiPdfReaderActivityFragment newInstance(String param1){
        RevisiPdfReaderActivityFragment fragment = new RevisiPdfReaderActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITTLE,param1);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static RevisiPdfReaderActivityFragment newInstance(DetailPDFModel data, String namePDF){
        RevisiPdfReaderActivityFragment fragment = new RevisiPdfReaderActivityFragment();
        Bundle bundle = new Bundle();
        //bundle.putString(KEY_PDF, data.getIdpdfdet());
        bundle.putString(KEY_TITTLE, namePDF);
        bundle.putString(KEY_PDF, data.getPdf());
        fragment.setArguments(bundle);
        return fragment;
    }

    public static RevisiPdfReaderActivityFragment newInstance(DetailPDFModel data){
        RevisiPdfReaderActivityFragment fragment = new RevisiPdfReaderActivityFragment();
        Bundle bundle = new Bundle();
        //bundle.putString(KEY_PDF, data.getIdpdfdet());
        bundle.putString(KEY_PDF, data.getPdf());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // idnya = fragment.getArguments().getString(Helper.KEY_DATA);

        return inflater.inflate(R.layout.fragment_revisi_pdf_reader, container, false);
    }

    private void checkFile() {
        pdfFile = new File(Environment.getExternalStorageDirectory() + "/Smart SIP/" + namePdf);
        Log.d("checkFile.pdffile", pdfFile.toString());
        if (pdfFile.exists()){
            //open
            openFile();
        }else {
            //download
            //new DownloadFile().execute(URL.file + name, name);
            executedDownload(URL.file, namePdf);
            Log.d("checkFile.urlfile", URL.file + namePdf);
        }
    }

    private void executedDownload(String url, String name) {
        DownloadManager.Request downloadManager = new DownloadManager.Request(Uri.parse(url + name));
        downloadManager.allowScanningByMediaScanner();
        downloadManager.setTitle("Download progress");
        downloadManager.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        downloadManager.setVisibleInDownloadsUi(true);
        downloadManager.setDestinationInExternalPublicDir("/Smart SIP/", name);

        DownloadManager downloadManager1 = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager1.enqueue(downloadManager);

        Intent intent = new Intent(getContext(), DownloadService.class);
        getContext().startService(intent);

        IntentFilter intentFilter = new IntentFilter(Helper.DOWNLOAD_KEY);
        getContext().registerReceiver(broadcastReceiver, intentFilter);

        dialog.setMessage("Please wait while loading the data ..");
        dialog.setCancelable(false);
        dialog.show();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("SuccessCode", 0) == 200) {
                // rubah button kalo di boardicle
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                openFile();
                Toast.makeText(getContext(), "Download Success", Toast.LENGTH_LONG).show();
                context.unregisterReceiver(this);
            }
        }
    };

    private void openFile() {
/*        pdfView.fromFile(pdfFile)
                .defaultPage(pageNumber)
                .enableSwipe(true)
//                .swipeHorizontal(true)
                .onPageChange(mContext)
                .enableAnnotationRendering(true)
                .onLoad(mContext)
                //.scrollHandle(null)
                .scrollHandle(new DefaultScrollHandle(getContext()))
                .swipeHorizontal(true)
                .load();*/

       pdfView.fromFile(pdfFile)
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .onPageChange(mContext)
                .enableAnnotationRendering(true)
                .onLoad(mContext)
                .scrollHandle(new DefaultScrollHandle(getContext()))
                .load();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

/*
        String tittle = getArguments().getString(KEY_TITTLE);
        ((TextView)view.findViewById(R.id.coba)).setText(tittle);*/

        dialog = new ProgressDialog(getContext());

        pdfView = view.findViewById(R.id.pdf_reader_pdfView);

        namePdf = getArguments().getString(KEY_PDF);
        Log.d("onCreateView: ",namePdf);

        checkFile();

        //checkFile();

       // Helper.name = getIntent().getStringExtra(Helper.DATA);

    }

    @Override
    public void loadComplete(int nbPages) {
        mContext.loadComplete(nbPages);
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        mContext.onPageChanged(page, pageCount);
    }
}
