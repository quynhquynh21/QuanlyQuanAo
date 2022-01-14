/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author BUIDUCQUYNH
 */
public class Import {
    private int codeclothes,countclothes,codestaff,codesupplier;
    private String nameclothes ,unit,dateimport,nameimporter,namesupplier;
    private float priceclothes;

    public  Import(){
        
    }
    public Import(int codeclothes, String nameclothes, float priceclothes, int countclothes, String unit, String dateimport,int codestaff,int codesupplier, String nameimporter, String namesupplier ) {
        
        this.codeclothes = codeclothes;
        this.nameclothes = nameclothes;
        this.priceclothes = priceclothes;
        this.countclothes = countclothes;
        this.unit = unit;
        this.dateimport = dateimport;
        this.codestaff = codestaff;
        this.codesupplier = codesupplier;
        this.nameimporter = nameimporter;
        this.namesupplier = namesupplier;
    }
     public Import(int codeclothes, String nameclothes, float priceclothes, int countclothes, String unit, String dateimport,int codestaff,int codesupplier) {
        
        this.codeclothes = codeclothes;
        this.nameclothes = nameclothes;
        this.priceclothes = priceclothes;
        this.countclothes = countclothes;
        this.unit = unit;
        this.dateimport = dateimport;
        this.codestaff = codestaff;
        this.codesupplier = codesupplier;
        
    }
    public Import( String nameclothes, float priceclothes, int countclothes, String unit, String dateimport,int codestaff,int codesupplier, String nameimporter, String namesupplier ) {
        
        this.nameclothes = nameclothes;
        this.priceclothes = priceclothes;
        this.countclothes = countclothes;
        this.unit = unit;
        this.dateimport = dateimport;
        this.codestaff = codestaff;
        this.codesupplier = codesupplier;
        this.nameimporter = nameimporter;
        this.namesupplier = namesupplier;
    }
     
    public int getCodeclothes() {
        return codeclothes;
    }

    public void setCodeclothes(int codeclothes) {
        this.codeclothes = codeclothes;
    }

    public int getCountclothes() {
        return countclothes;
    }

    public void setCountclothes(int countclothes) {
        this.countclothes = countclothes;
    }

    public float getPriceclothes() {
        return priceclothes;
    }

    public void setPriceclothes(float priceclothes) {
        this.priceclothes = priceclothes;
    }
    
    public String getNameclothes() {
        return nameclothes;
    }

    public void setNameclothes(String nameclothes) {
        this.nameclothes = nameclothes;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDateimport() {
        return dateimport;
    }

    public void setDateimport(String dateimport) {
        this.dateimport = dateimport;
    }

    public int getCodestaff() {
        return codestaff;
    }

    public void setCodestaff(int codestaff) {
        this.codestaff = codestaff;
    }

    public int getCodesupplier() {
        return codesupplier;
    }

    public void setCodesupplier(int codesupplier) {
        this.codesupplier = codesupplier;
    }

    public String getNameimporter() {
        return nameimporter;
    }

    public void setNameimporter(String nameimporter) {
        this.nameimporter = nameimporter;
    }

    public String getNamesupplier() {
        return namesupplier;
    }

    public void setNamesupplier(String namesupplier) {
        this.namesupplier = namesupplier;
    }
    
     @Override
    public String toString() {
        return this.nameclothes ;
    }
}
