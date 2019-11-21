package org.ftui.mobile.model;

import org.ftui.mobile.R;

import java.util.ArrayList;

public class Notification {

    private String title, content;
    private Integer drawable;

    public Notification(String title, String content, Integer drawable) {
        this.title = title;
        this.content = content;
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDrawable() {
        return drawable;
    }

    public void setDrawable(Integer drawable) {
        this.drawable = drawable;
    }

    public static ArrayList<Notification> mockApiData (){
        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification("Keluhan telah ditangani", "Keluhan Anda tentang \"Broken Glaz\" telah ditangani. Anda dapat membuka keluhan kembali jika anda tidak puas dengan hasil penanganan.", R.drawable.ic_check_circle_success_24dp));
        notifications.add(new Notification("Keluhan telah ditangani", "Keluhan Anda tentang \"Broken Glaz\" telah ditangani. Anda dapat membuka keluhan kembali jika anda tidak puas dengan hasil penanganan.", R.drawable.ic_check_circle_success_24dp));
        notifications.add(new Notification("Keluhan telah ditangani", "Keluhan Anda tentang \"Broken Glaz\" telah ditangani. Anda dapat membuka keluhan kembali jika anda tidak puas dengan hasil penanganan.", R.drawable.ic_check_circle_success_24dp));

        return notifications;
    }
}
