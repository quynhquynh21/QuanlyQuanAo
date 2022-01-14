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
public class Suppllier {
     
    private int codesupplier;
    private String namesupplier, numbersupplier, addresssupplier;
    
    public Suppllier(int codesupplier, String namesupplier, String numbersupplier, String addresssupplier) {
        this.codesupplier = codesupplier;
        this.namesupplier = namesupplier;
        this.numbersupplier = numbersupplier;
        this.addresssupplier = addresssupplier;
    }

    public Suppllier() {
    }
    
     public Suppllier( String namesupplier, String numbersupplier, String addresssupplier) {
        this.namesupplier = namesupplier;
        this.numbersupplier = numbersupplier;
        this.addresssupplier = addresssupplier;
    }
    public int getCodesupplier() {
        return codesupplier;
    }

    public void setCodesupplier(int codesupplier) {
        this.codesupplier = codesupplier;
    }

    public String getNamesupplier() {
        return namesupplier;
    }

    public void setNamesupplier(String namesupplier) {
        this.namesupplier = namesupplier;
    }

    public String getNumbersupplier() {
        return numbersupplier;
    }

    public void setNumbersupplier(String numbersupplier) {
        this.numbersupplier = numbersupplier;
    }

    public String getAddresssupplier() {
        return addresssupplier;
    }

    public void setAddresssupplier(String addresssupplier) {
        this.addresssupplier = addresssupplier;
    }
    
    @Override
    public String toString() {
        return this.namesupplier;
    }
}
