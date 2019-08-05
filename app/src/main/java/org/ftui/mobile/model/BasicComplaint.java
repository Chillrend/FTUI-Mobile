package org.ftui.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class BasicComplaint {
    private String userProfilePictureUrl;
    private String userFullname;
    private Long timestamp;
    private String objectLocation;
    private String complaintTitle;
    private String complaintDescription;
    private String coverImageUrl;
    private String complaintType;
    private String complaintStatus;
    private int commentCount;

    public BasicComplaint(){

    }


    public BasicComplaint(String userProfilePictureUrl, String userFullname, Long timestamp, String objectLocation, String complaintTitle, String complaintDescription, String coverImageUrl, String complaintType, String complaintStatus, int commentCount) {
        this.userProfilePictureUrl = userProfilePictureUrl;
        this.userFullname = userFullname;
        this.timestamp = timestamp;
        this.objectLocation = objectLocation;
        this.complaintTitle = complaintTitle;
        this.complaintDescription = complaintDescription;
        this.coverImageUrl = coverImageUrl;
        this.complaintType = complaintType;
        this.complaintStatus = complaintStatus;
        this.commentCount = commentCount;
    }

    public String getUserProfilePictureUrl() {
        return userProfilePictureUrl;
    }

    public void setUserProfilePictureUrl(String userProfilePictureUrl) {
        this.userProfilePictureUrl = userProfilePictureUrl;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getObjectLocation() {
        return objectLocation;
    }

    public void setObjectLocation(String objectLocation) {
        this.objectLocation = objectLocation;
    }

    public String getComplaintTitle() {
        return complaintTitle;
    }

    public void setComplaintTitle(String complaintTitle) {
        this.complaintTitle = complaintTitle;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }


    //Emulates API Data
    //Remove this after acquiring real API Data
    public static List<BasicComplaint> mockComplainData(){
        List<BasicComplaint> al = new ArrayList<>();
        al.add(new BasicComplaint("https://picsum.photos/25", "Muhammad Fadil Ardiansyah", Long.parseLong("1563580800"), "AA-304", "Monitor Rusak", "Monitor milik PC-34 di Ruangan AA-304 Tidak bisa dinyalakan", "https://picsum.photos/300/200", "FACILITIES_AND_INFRASTRUCTURE", "AWAITING_FOLLOWUP", 4));
        al.add(new BasicComplaint("https://picsum.photos/25", "Tjokorda Raka Wisnu", Long.parseLong("1563580800"), "Hall Engineering Center", "CCTV Tidak Berfungsi", "CCTV di pintu masuk utama Engineering Center tidak berfungsi", "https://picsum.photos/300/200", "FACILITIES_AND_INFRASTRUCTURE", "IS_BEING_FOLLOWED_UP", 0));
        al.add(new BasicComplaint("https://picsum.photos/25", "Andre Juliansyah", Long.parseLong("1563580800"), "Kantin Teknik", "Pelanggaran Aturan", "Mahasiswa merokok di sekitaran kantek, bukan di smoking area", "https://picsum.photos/300/200", "INCIDENT_AND_RULE_VIOLATION", "IS_BEING_FOLLOWED_UP", 5));
        return al;
    }
}
