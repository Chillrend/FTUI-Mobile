package org.ftui.mobile.model;

import android.graphics.Bitmap;

public class Photos {
    private Bitmap photo;
    private long timestamp;

    public Photos(){

    }

    public Photos(Bitmap photo, long timestamp) {
        this.photo = photo;
        this.timestamp = timestamp;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
