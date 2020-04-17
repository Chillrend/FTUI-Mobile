package com.rsypj.ftuimobile.ui.fragment.controller;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.service.DownloadService;
import com.rsypj.ftuimobile.ui.fragment.PDFReaderFragment;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.List;

/**
 * Author By dhadotid
 * Date 13/07/2018.
 */
public class PDFReaderController {

    PDFReaderFragment fragment;
    private String idnya;
    private String namePDF;
    private int pageNumber = 0;
    ProgressDialog dialog;
    File pdfFile;

    public PDFReaderController(PDFReaderFragment fragment) {
        this.fragment = fragment;

        dialog = new ProgressDialog(fragment.getContext());

        idnya = fragment.getArguments().getString(Helper.KEY_DATA);
        namePDF = fragment.getArguments().getString(Helper.KEY_NAME_PDF);

        Log.d("controller2.idnya",idnya);
        Log.d("controller2.namepdf",namePDF);

        checkFile();
    }

    private void checkFile(){
        pdfFile = new File(Environment.getExternalStorageDirectory() + "/Smart SIP/" + namePDF);
        Log.d("checkFile.pdffile", pdfFile.toString());
        if (pdfFile.exists()){
            //open
            openFile();
        }else {
            //download
            //new DownloadFile().execute(URL.file + name, name);
            executedDownload(URL.file, namePDF);
            Log.d("checkFile.urlfile", URL.file + namePDF);
        }
    }

    private void executedDownload(String url, String name) {
        DownloadManager.Request downloadManager = new DownloadManager.Request(Uri.parse(url + name));
        downloadManager.allowScanningByMediaScanner();
        downloadManager.setTitle("Download progress");
        downloadManager.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        downloadManager.setVisibleInDownloadsUi(true);
        downloadManager.setDestinationInExternalPublicDir("/Smart SIP/", name);

        DownloadManager downloadManager1 = (DownloadManager) fragment.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager1.enqueue(downloadManager);

        Intent intent = new Intent(fragment.getContext(), DownloadService.class);
        fragment.getContext().startService(intent);

        IntentFilter intentFilter = new IntentFilter(Helper.DOWNLOAD_KEY);
        fragment.getContext().registerReceiver(broadcastReceiver, intentFilter);

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
                Toast.makeText(fragment.getContext(), "Download Success", Toast.LENGTH_LONG).show();
                context.unregisterReceiver(this);
            }
        }
    };

    private void openFile() {
        fragment.getPdfView().fromFile(pdfFile)
                .defaultPage(pageNumber)
                .enableSwipe(true)
//                .swipeHorizontal(true)
                .onPageChange(fragment)
                .enableAnnotationRendering(true)
                .onLoad(fragment)
                //.scrollHandle(null)
                .scrollHandle(new DefaultScrollHandle(fragment.getContext()))
                .swipeHorizontal(true)
                .load();
    }

    public void onPageChanged(int page, int pageCount){
        pageNumber = page;
        fragment.getActivity().setTitle(String.format("%s %s / %s", pdfFile, page + 1, pageCount));
    }

    //load pdf and set to id of pdf layout
    public void loadComplete(int nbPages){
        PdfDocument.Meta meta = fragment.getPdfView().getDocumentMeta();
        printBookmarksTree(fragment.getPdfView().getTableOfContents(), "-");
    }

    private void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {
            Log.e("ABC", String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }
}
