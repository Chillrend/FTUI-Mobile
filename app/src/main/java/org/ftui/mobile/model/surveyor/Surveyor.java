
package org.ftui.mobile.model.surveyor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Surveyor {

    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("details")
    @Expose
    private Details details;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

}
