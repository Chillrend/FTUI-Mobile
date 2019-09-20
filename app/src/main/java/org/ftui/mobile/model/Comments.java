package org.ftui.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class Comments {
    private String email;
    private String userFullname;
    private String userPictureURL;
    private Long dateSubmitted;
    private String comments;
    private String role;
    private String commentImgUrl;

    public Comments(){

    }

    public Comments(String email, String userFullname, String userPictureURL, Long dateSubmitted, String comments, String role, String commentImgUrl) {
        this.email = email;
        this.userFullname = userFullname;
        this.userPictureURL = userPictureURL;
        this.dateSubmitted = dateSubmitted;
        this.comments = comments;
        this.role = role;
        this.commentImgUrl = commentImgUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public String getUserPictureURL() {
        return userPictureURL;
    }

    public void setUserPictureURL(String userPictureURL) {
        this.userPictureURL = userPictureURL;
    }

    public Long getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Long dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCommentImgUrl() {
        return commentImgUrl;
    }

    public void setCommentImgUrl(String commentImgUrl) {
        this.commentImgUrl = commentImgUrl;
    }

    //Mock API Data
    public static List<Comments> mockCommentsData(){
        List<Comments> returnThis = new ArrayList<>();
        returnThis.add(new Comments("tjokorda@test.com", "Tjokorda Raka Wisnu", "https://picsum.photos/25/25", Long.parseLong("1565752875"), "Tolong ditindak Lanjuti", "USER", "https://picsum.photos/150/250"));
        returnThis.add(new Comments("surveyor@test.com", "Geraldi M Yushaar", "https://picsum.photos/25/25", Long.parseLong("1565752875"), "Keluhan Anda telah ditindak lanjuti, silahkan lihat update keluhan", "SURVEYOR", "https://picsum.photos/400/200"));
        returnThis.add(new Comments("surveyor@test.com", "Geraldi M Yushaar", "https://picsum.photos/25/25", Long.parseLong("1565752875"), "Keluhan Anda telah ditindak lanjuti, silahkan lihat update keluhan", "SURVEYOR", null));

        return returnThis;
    }
}
