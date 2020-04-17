package com.rsypj.ftuimobile.ui.activity;

import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.helper.Helper;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.List;

/**
 * Author By dhadotid
 * Date 11/07/2018.
 */
public class ReadEbookActivity extends SmartSipBaseActivity implements OnPageChangeListener, OnLoadCompleteListener{

    TextView tvTitle;
    PDFView pdfView;
    Integer pageNumber = 0;
    String fileName;
    String judul;
    File pdfFile;

    @Override
    public int getLayoutID() {
        return R.layout.activity_read_ebook;
    }

    @Override
    public void initItem() {
        tvTitle = findViewById(R.id.read_ebook_tvTitle);
        pdfView = findViewById(R.id.read_ebook);
        hideActionBar();
        fileName = getIntent().getStringExtra(Helper.DATA);
        judul = getIntent().getStringExtra(Helper.DATANAME);
        tvTitle.setText(judul);
        Log.d("ACCC", fileName);
        pdfFile = new File(Environment.getExternalStorageDirectory() + "/Smart SIP/" + fileName);  // -> filename = maven.pdf

//        try {
//            String parsedText="";
//            PdfReader reader = new PdfReader(pdfFile+"");
//            int n = reader.getNumberOfPages();
//            for (int i = 0; i <n ; i++) {
//                parsedText   = parsedText+ PdfTextExtractor.getTextFromPage(reader, i+1).trim()+"\n"; //Extracting the content from the different pages
//            }
//            Log.d("ININI", parsedText);
//            reader.close();
//        } catch (Exception e) {
//            Log.d("EX", e+"");
//        }

        pdfView.fromFile(pdfFile)
                .defaultPage(pageNumber)
                .enableSwipe(true)
//                .swipeHorizontal(true)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                //.scrollHandle(null)
                .scrollHandle(new DefaultScrollHandle(this))
                .swipeHorizontal(true)
                .load();

        //new ReadEbookController(this);
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
//        Log.d("AAA", pageNumber+" "+ pageCount);
//        Toast toast = Toast.makeText(this, (pageNumber+1) + " / " + pageCount, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 5, 5);
//        toast.show();
        setTitle(String.format("%s %s / %s", pdfFile, page + 1, pageCount));
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e("ABC", String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    public PDFView getPdfView() {
        return pdfView;
    }
}
