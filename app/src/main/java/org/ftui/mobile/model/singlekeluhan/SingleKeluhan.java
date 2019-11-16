
package org.ftui.mobile.model.singlekeluhan;

import java.util.List;
import com.google.gson.annotations.Expose;
import org.ftui.mobile.model.keluhan.Metum;

@SuppressWarnings("unused")
public class SingleKeluhan {

    @Expose
    private List<Metum> meta;
    @Expose
    private Results results;
    @Expose
    private List<String> surveyor;
    @Expose
    private String urlimg;

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

    public List<String> getSurveyor() {
        return surveyor;
    }

    public void setSurveyor(List<String> surveyor) {
        this.surveyor = surveyor;
    }

    public String getUrlimg() {
        return urlimg;
    }

    public void setUrlimg(String urlimg) {
        this.urlimg = urlimg;
    }

}
