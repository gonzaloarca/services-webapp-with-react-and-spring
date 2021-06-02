package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.JobContract;

public class ChangeContractStateForm {
    long id;
    int newState;
    String returnURL;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
