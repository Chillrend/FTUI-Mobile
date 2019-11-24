package org.ftui.mobile.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import org.ftui.mobile.R;

import java.util.ArrayList;

public class Notification extends RealmObject {

    @PrimaryKey
    private String id;
    private String title;
    private String content;
    private String keluhan_id;
    private long updated_at;

    public Notification(String title, String content, String keluhan_id) {
        this.title = title;
        this.content = content;
        this.keluhan_id = keluhan_id;
    }

    //Required empty constructor
    public Notification(){

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeluhan_id() {
        return keluhan_id;
    }

    public void setKeluhan_id(String keluhan_id) {
        this.keluhan_id = keluhan_id;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public static ArrayList<Notification> mockApiData (){
        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification("Keluhan telah ditangani", "Keluhan Anda tentang \"Broken Glaz\" telah ditangani. Anda dapat membuka keluhan kembali jika anda tidak puas dengan hasil penanganan.", "1"));
        notifications.add(new Notification("Keluhan telah ditangani", "Keluhan Anda tentang \"Broken Glaz\" telah ditangani. Anda dapat membuka keluhan kembali jika anda tidak puas dengan hasil penanganan.", "1"));
        notifications.add(new Notification("Keluhan telah ditangani", "Keluhan Anda tentang \"Broken Glaz\" telah ditangani. Anda dapat membuka keluhan kembali jika anda tidak puas dengan hasil penanganan.", "1"));

        return notifications;
    }
}
