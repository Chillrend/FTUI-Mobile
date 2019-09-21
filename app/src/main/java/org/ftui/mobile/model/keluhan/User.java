
package org.ftui.mobile.model.keluhan;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("orgcode")
    @Expose
    private Object orgcode;
    @SerializedName("faculty")
    @Expose
    private Object faculty;
    @SerializedName("studyprog")
    @Expose
    private Object studyprog;
    @SerializedName("eduprog")
    @Expose
    private Object eduprog;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;
    @SerializedName("identitas")
    @Expose
    private String identitas;
    @SerializedName("noidentitas")
    @Expose
    private String noidentitas;
    @SerializedName("imgurl")
    @Expose
    private String imgurl;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("ticketit_admin")
    @Expose
    private Integer ticketitAdmin;
    @SerializedName("ticketit_agent")
    @Expose
    private Integer ticketitAgent;
    private final static long serialVersionUID = 4126523992127430213L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(Object orgcode) {
        this.orgcode = orgcode;
    }

    public Object getFaculty() {
        return faculty;
    }

    public void setFaculty(Object faculty) {
        this.faculty = faculty;
    }

    public Object getStudyprog() {
        return studyprog;
    }

    public void setStudyprog(Object studyprog) {
        this.studyprog = studyprog;
    }

    public Object getEduprog() {
        return eduprog;
    }

    public void setEduprog(Object eduprog) {
        this.eduprog = eduprog;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getTicketitAdmin() {
        return ticketitAdmin;
    }

    public void setTicketitAdmin(Integer ticketitAdmin) {
        this.ticketitAdmin = ticketitAdmin;
    }

    public Integer getTicketitAgent() {
        return ticketitAgent;
    }

    public void setTicketitAgent(Integer ticketitAgent) {
        this.ticketitAgent = ticketitAgent;
    }

}
