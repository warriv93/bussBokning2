package com.example.simon.dbprojectv3.customs;

/**
 * Created by Iceman on 2015-03-19.
 */
public class Bokningar {
    String dag, ankomst, avgang, fran, till;

    public Bokningar() {

    }
    public Bokningar(String dag, String ankomst, String avgang, String fran, String till) {
        super();
        this.dag = dag;
        this.fran = fran;
        this.till = till;
        this.ankomst = ankomst;
        this.avgang = avgang;
    }

    public String getDag() {
        return dag;
    }


    public String getFran() {
        return fran;
    }


    public String getTill() {
        return till;
    }


    public String getAnkomst() {
        return ankomst;
    }


    public String getAvgang() {
        return avgang;
    }

}
