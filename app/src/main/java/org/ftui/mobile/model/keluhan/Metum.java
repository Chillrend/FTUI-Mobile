
package org.ftui.mobile.model.keluhan;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metum implements Serializable
{

    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("page_total")
    @Expose
    private String pageTotal;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("limit")
    @Expose
    private String limit;
    @SerializedName("page")
    @Expose
    private String page;
    private final static long serialVersionUID = -3544413492866230503L;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(String pageTotal) {
        this.pageTotal = pageTotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

}
