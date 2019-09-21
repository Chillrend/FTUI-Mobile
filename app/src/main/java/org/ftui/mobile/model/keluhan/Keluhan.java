
package org.ftui.mobile.model.keluhan;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Keluhan implements Serializable
{

    @SerializedName("urlimg")
    @Expose
    private String urlimg;
    @SerializedName("meta")
    @Expose
    private List<Metum> meta = null;
    @SerializedName("results")
    @Expose
    private Results results;
    private final static long serialVersionUID = -3045259399660800589L;

    public String getUrlimg() {
        return urlimg;
    }

    public void setUrlimg(String urlimg) {
        this.urlimg = urlimg;
    }

    public List<Metum> getMeta() {
        return meta;
    }

    public void setMeta(List<Metum> meta) {
        this.meta = meta;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

}
