package ar.edu.itba.paw.webapp.form;


public class ChangeContractStateForm {
    int newState;
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
}
