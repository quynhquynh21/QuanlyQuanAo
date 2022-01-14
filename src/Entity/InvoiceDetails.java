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
public class InvoiceDetails {

    private int codeinvoice, countbuy, codeclothes;
    private String nameclothes;
    private float unitprice,total;
    public float[] totalPrice;

    public InvoiceDetails(int codeinvoice, int codeclothes, int countbuy, float unitprice) {
        this.codeinvoice = codeinvoice;
        this.countbuy = countbuy;
        this.codeclothes = codeclothes;
        this.unitprice = unitprice;

    }

    public InvoiceDetails(int codeinvoice, int codeclothes,String nameclothes, int countbuy, float unitprice,float total) {
        this.codeinvoice = codeinvoice;
        this.codeclothes = codeclothes;
        this.nameclothes = nameclothes;
        this.countbuy = countbuy;
        this.unitprice = unitprice;
        this.total = total;
    }

    public int getCodeinvoice() {
        return codeinvoice;
    }

    public void setCodeinvoice(int codeinvoice) {
        this.codeinvoice = codeinvoice;
    }

    public int getCountbuy() {
        return countbuy;
    }

    public void setCountbuy(int countbuy) {
        this.countbuy = countbuy;
    }

    public float getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(float unitprice) {
        this.unitprice = unitprice;
    }

    public int getCodeclothes() {
        return codeclothes;
    }

    public void setCodeclothes(int codeclothes) {
        this.codeclothes = codeclothes;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getNameclothes() {
        return nameclothes;
    }

    public void setNameclothes(String nameclothes) {
        this.nameclothes = nameclothes;
    }
    public float getTongTien( int n) {
        float tong = 0;
        for (int i = 0; i < n; i++) {
            tong += totalPrice[i];
        }
        return tong;
    }
     @Override
    public String toString() {
        return "{" + "id=" + codeinvoice + ", hoTen=" + codeclothes + ",họ tên"+nameclothes+", diaChi=" + countbuy + ", tenLop=" + unitprice + ", namSinh=" + total + '}';
    }
}
