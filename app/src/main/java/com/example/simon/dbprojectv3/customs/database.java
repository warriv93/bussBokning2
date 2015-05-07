package com.example.simon.dbprojectv3.customs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by simon on 23-Feb-15.
 */
public class database extends SQLiteOpenHelper {
    private static final String DB_NAME = "projektdb";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;

//    def __init__(self):
//            import mysql.connector
//    self.db = mysql.connector.connect(host="195.178.234.34", user="simon",
//    passwd="dudesson", db="simon", charset="latin1")

    public database(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    //gör det möjligt att skriva o läsa från tabellerna i databasen
    public void open() {
        db = getWritableDatabase();
    }

    //stänger anslutnignen till databasen
    public void close() {
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Resa ( TurID INTEGER PRIMARY KEY AUTOINCREMENT, Fran VARCHAR(255), Till VARCHAR(255));");

        db.execSQL("CREATE TABLE Busstur ( TurID INTEGER PRIMARY KEY AUTOINCREMENT, BussID INTEGER, Pris INTEGER,"+
                "Dag VARCHAR(200), Avgang INTEGER, Ankomst INTEGER);");

        db.execSQL("CREATE TABLE Resenar ( PersonID INTEGER PRIMARY KEY AUTOINCREMENT, Namn VARCHAR(255),"+
                "Adress VARCHAR(255), PNR INTEGER, Land VARCHAR(255), Telefon INTEGER, Mail VARCHAR(255));");

        db.execSQL("CREATE TABLE Paket ( PaketID INTEGER PRIMARY KEY AUTOINCREMENT, Pris INTEGER);");

        db.execSQL("CREATE TABLE ReserverarB (PersonID INTEGER , Buss INTEGER);");
        db.execSQL("CREATE TABLE ReserverarP (PaketID INTEGER , PersonID INTEGER);");

    }

    public void InsertItems(){

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
    }

    public Cursor getTravel() {
        Cursor c = db.rawQuery("SELECT TurID, Fran, Till FROM Resa", new String[]{});
        return c;
    }

    public Cursor getBussturer(Integer TurID){
        Cursor c = db.rawQuery("SELECT BussID, Pris, Dag, Avgang, Ankomst FROM Busstur WHERE TurID = "+TurID, new String[]{});
        return c;
    }

    public Cursor getResenar(){
        Cursor c = db.rawQuery("SELECT Namn, PersonID FROM Resenar", new String[]{});
        return c;
    }

    public Cursor getPaketLista(){
        Cursor c = db.rawQuery("SELECT PaketID, Pris FROM Paket", new String[]{});
        return c;
    }
    public Cursor getPaketResor(Integer PaketID){
        Cursor c = db.rawQuery("SELECT Resa.Till, Resa.Fran, Busstur.Avgang, Busstur.Ankomst, Busstur.Dag "+
                "FROM Paket "+
                "INNER JOIN Paketresor ON Paket.PaketID=Paketresor.PaketID "+
                "INNER JOIN Busstur ON "+
                "Paketresor.BussID=Busstur.BussID "+
                "INNER JOIN Resa ON "+
                "Busstur.TurID=Resa.TurID "+
                "WHERE Paket.PaketID = %s ", new String[]{});
        return c;
    }

    public Cursor getBokningarEnkel(Integer PersonID){

        Cursor c = db.rawQuery("SELECT Resa.Till, Resa.Fran, Busstur.Avgang, Busstur.Ankomst, Busstur.Dag "+
                "FROM ReserverarB "+
                "INNER JOIN Busstur ON ReserverarB.Buss=Busstur.BussID "+
                "INNER JOIN Resa ON "+
                "Busstur.TurID=Resa.TurID "+
                "WHERE ReserverarB.PersonID = "+PersonID, new String[]{});
        return c;
    }

    public Cursor getBokadePaket(Integer PersonID){
        Cursor c = db.rawQuery("SELECT Resa.Till, Resa.Fran, Busstur.Avgang, Busstur.Ankomst, Busstur.Dag "+
                "FROM ReserverarP "+
                "INNER JOIN Paket ON ReserverarP.PaketID=Paket.PaketID "+
                "INNER JOIN Paketresor ON Paketresor.PaketID=Paket.PaketID "+
                "INNER JOIN Busstur ON "+
                "Paketresor.BussID=Busstur.BussID "+
                "INNER JOIN Resa ON "+
                "Busstur.TurID=Resa.TurID "+
                "WHERE ReserverarP.PersonID= %s", new String[]{});
        return c;
    }

    public void addTravel(Integer TurID, String Fran, String Till){
        ContentValues values = new ContentValues();
        values.put("TurID", TurID);
        values.put("Fran", Fran);
        values.put("Till", Till);
        db.insert("Resa", null, values);
    }

    public void addBusstur(Integer TurID,Integer BussID, Integer Pris,String Dag,Integer Avgang,Integer Ankomst){
        ContentValues values = new ContentValues();
        values.put("TurID", TurID);
        values.put("BussID", BussID);
        values.put("Pris", Pris);
        values.put("Dag", Dag);
        values.put("Avgang", Avgang);
        values.put("Ankomst", Ankomst);
        db.insert("Busstur", null, values);
    }

    public void addResenar(String Namn,String Adress,Integer PNR,String Land,Integer Telefon,String Mail){
        ContentValues values = new ContentValues();
        values.put("Namn", Namn);
        values.put("Adress", Adress);
        values.put("PNR", PNR);
        values.put("Land", Land);
        values.put("Telefon", Telefon);
        values.put("Mail", Mail);
        db.insert("Resenar", null, values);
    }

    public void addReserverarB(Integer PersonID, Integer BussID){
        ContentValues values = new ContentValues();
        values.put("PersonID", PersonID);
        values.put("Buss", BussID);
        db.insert("ReserverarB", null, values);
    }

    public void addReserverarP(Integer PersonID, Integer PaketID){
        ContentValues values = new ContentValues();
        values.put("PaketID", PaketID);
        values.put("PersonID", PersonID);
        db.insert("ReserverarP", null, values);
    }
}
