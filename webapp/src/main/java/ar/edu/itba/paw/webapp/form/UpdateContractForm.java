package ar.edu.itba.paw.webapp.form;


import ar.edu.itba.paw.models.JobContract;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UpdateContractForm {

    @NotNull
    @Size(min = 0, max = 8)
    int newState;

    @DateTimeFormat
    String newScheduledDate;

    String returnURL;

    public int getNewState() {
        return newState;
    }

    public void setNewState(int newState) {
        this.newState = newState;
    }

    public String getReturnURL() {
        return returnURL;
    }

    public void setReturnURL(String returnURL) {
        this.returnURL = returnURL;
    }

    public String getNewScheduledDate() {
        return newScheduledDate;
    }

    public void setNewScheduledDate(String newScheduledDate) {
        this.newScheduledDate = newScheduledDate;
    }
}
