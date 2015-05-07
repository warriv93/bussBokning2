package com.example.simon.dbprojectv3.customs;

/**
 * Created by simon on 24-Feb-15.
 */
public class Resor {
    private int TurID;
    private String fran, till;

    public Resor() {

    }
    public Resor(int turID, String fran, String till) {
        super();
        this.TurID = turID;
        this.fran = fran;
        this.till = till;
    }

    public int getTurID() {
        return TurID;
    }

    public void setTurID(int turID) {
        TurID = turID;
    }

    public String getFran() {
        return fran;
    }

    public void setFran(String fran) {
        this.fran = fran;
    }

    public String getTill() {
        return till;
    }

    public void setTill(String till) {
        this.till = till;
    }
}
