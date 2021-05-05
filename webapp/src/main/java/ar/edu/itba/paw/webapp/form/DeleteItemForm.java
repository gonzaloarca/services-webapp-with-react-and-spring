package ar.edu.itba.paw.webapp.form;

public class DeleteItemForm {
    long id;
    String returnURL;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReturnURL() {
        return returnURL;
    }

    public void setReturnURL(String returnURL) {
        this.returnURL = returnURL;
    }
}
