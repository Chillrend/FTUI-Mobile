package org.ftui.mobile.model;

public class CompleteUser {
    private int id;
    private String name;
    private int orgcode;
    private String faculty;
    private String studyprog;
    private String eduprog;
    private String email;
    private String email_verified_at;
    private String identitas;
    private String noidentitas;
    private String imgurl;
    private String created_at;
    private String updated_at;
    private int ticketit_admin;
    private int ticketit_agent;

    public CompleteUser(int id, String name, int orgcode, String faculty, String studyprog, String eduprog, String email, String email_verified_at, String identitas, String noidentitas, String imgurl, String created_at, String updated_at, int ticketit_admin, int ticketit_agent) {
        this.id = id;
        this.name = name;
        this.orgcode = orgcode;
        this.faculty = faculty;
        this.studyprog = studyprog;
        this.eduprog = eduprog;
        this.email = email;
        this.email_verified_at = email_verified_at;
        this.identitas = identitas;
        this.noidentitas = noidentitas;
        this.imgurl = imgurl;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.ticketit_admin = ticketit_admin;
        this.ticketit_agent = ticketit_agent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(int orgcode) {
        this.orgcode = orgcode;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getStudyprog() {
        return studyprog;
    }

    public void setStudyprog(String studyprog) {
        this.studyprog = studyprog;
    }

    public String getEduprog() {
        return eduprog;
    }

    public void setEduprog(String eduprog) {
        this.eduprog = eduprog;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getIdentitas() {
        return identitas;
    }

    public void setIdentitas(String identitas) {
        this.identitas = identitas;
    }

    public String getNoidentitas() {
        return noidentitas;
    }

    public void setNoidentitas(String noidentitas) {
        this.noidentitas = noidentitas;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getTicketit_admin() {
        return ticketit_admin;
    }

    public void setTicketit_admin(int ticketit_admin) {
        this.ticketit_admin = ticketit_admin;
    }

    public int getTicketit_agent() {
        return ticketit_agent;
    }

    public void setTicketit_agent(int ticketit_agent) {
        this.ticketit_agent = ticketit_agent;
    }
}
