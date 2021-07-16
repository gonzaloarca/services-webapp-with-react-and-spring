package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class EditJobContractDto {

    @Min(0)
    @Max(8)
    int newState;

    @DateTimeFormat(pattern = HirenetUtils.ISO_DATE_TIME_FORMAT)
    String newScheduledDate;

    public int getNewState() {
        return newState;
    }

    public void setNewState(int newState) {
        this.newState = newState;
    }

    public String getNewScheduledDate() {
        return newScheduledDate;
    }

    public void setNewScheduledDate(String newScheduledDate) {
        this.newScheduledDate = newScheduledDate;
    }
}
