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
public class Invoice {
    private int codestaff,codecustomer ;
    private String purchasedate,namecustommer,namestaff,codeinvoice; 

    public Invoice() {
    }

    
    public Invoice(String codeinvoice, int codecustomer,String namecustommer, String purchasedate, int codestaff,String namestaff) {
        this.codeinvoice = codeinvoice;
        this.codecustomer = codecustomer;
        this.namecustommer = namecustommer;
        this.purchasedate = purchasedate;
        this.codestaff = codestaff;
        this.namestaff = namestaff;
    }
    
    public Invoice(int codecustomer,String namecustommer, String purchasedate, int codestaff,String namestaff) {
        this.codecustomer = codecustomer;
        this.namecustommer= namecustommer;
        this.purchasedate = purchasedate;
        this.codestaff = codestaff;
        this.namestaff = namestaff;
    }

    public String getCodeinvoice() {
        return codeinvoice;
    }

    public void setCodeinvoice(String codeinvoice) {
        this.codeinvoice = codeinvoice;
    }

    public int getCodestaff() {
        return codestaff;
    }

    public void setCodestaff(int codestaff) {
        this.codestaff = codestaff;
    }

    public int getCodecustomer() {
        return codecustomer;
    }

    public void setCodecustomer(int codecustomer) {
        this.codecustomer = codecustomer;
    }

    public String getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(String purchasedate) {
        this.purchasedate = purchasedate;
    }

    public String getNamecustommer() {
        return namecustommer;
    }

    public void setNamecustommer(String namecustommer) {
        this.namecustommer = namecustommer;
    }

    public String getNamestaff() {
        return namestaff;
    }

    public void setNamestaff(String namestaff) {
        this.namestaff = namestaff;
    }

    @Override
    public String toString() {
        return "{" + "id hoá đơn=" + codeinvoice + ", mã nhân viên=" + codestaff + ",Tên khách hàng"+namecustommer+", ngày nhập=" + purchasedate ;
    }
}
