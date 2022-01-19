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
public class Custommer {

    private String codecustommer;
    private String namecustomer, numbercustomer, addresscustomer;

    public Custommer() {
    }

    public Custommer(String codecustommer, String namecustomer, String numbercustomer, String addresscustomer) {
        this.codecustommer = codecustommer;
        this.namecustomer = namecustomer;
        this.numbercustomer = numbercustomer;
        this.addresscustomer = addresscustomer;

    }

    public Custommer(String namecustomer, String numbercustomer, String addresscustomer) {
        this.namecustomer = namecustomer;
        this.numbercustomer = numbercustomer;
        this.addresscustomer = addresscustomer;

    }

    public String getNamecustomer() {
        return namecustomer;
    }

    public void setNamecustomer(String namecustomer) {
        this.namecustomer = namecustomer;
    }

    public String getNumbercustomer() {
        return numbercustomer;
    }

    public void setNumbercustomer(String numbercustomer) {
        this.numbercustomer = numbercustomer;
    }

    public String getAddresscustomer() {
        return addresscustomer;
    }

    public void setAddresscustomer(String addresscustomer) {
        this.addresscustomer = addresscustomer;
    }

    public String getCodecustommer() {
        return codecustommer;
    }

    public void setCodecustommer(String codecustommer) {
        this.codecustommer = codecustommer;
    }

    @Override
    public String toString() {
        return this.namecustomer;
    }
}
