
package org.ftui.mobile.model.surveyor;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyorResponse {

    @SerializedName("surveyor")
    @Expose
    private List<Surveyor> surveyor = null;

    public List<Surveyor> getSurveyor() {
        return surveyor;
    }

    public void setSurveyor(List<Surveyor> surveyor) {
        this.surveyor = surveyor;
    }

}
