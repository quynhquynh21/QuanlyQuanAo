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
public class Staff {

    private  String namestaff,addressstaff,numberstaff,codestaff;

    public Staff() {
    }

    public Staff(String codestaff, String namestaff, String numberstaff, String addressstaff) {
        this.codestaff = codestaff;
        this.namestaff = namestaff;
        this.numberstaff = numberstaff;
        this.addressstaff = addressstaff;
        
    }
    
    public Staff( String namestaff, String numberstaff, String addressstaff) {
        this.namestaff = namestaff;
        this.numberstaff = numberstaff;
        this.addressstaff = addressstaff;
        
    }
    
    public String getCodestaff() {
        return codestaff;
    }

    public void setCodestaff(String codestaff) {
        this.codestaff = codestaff;
    }

    public String getNamestaff() {
        return namestaff;
    }

    public void setNamestaff(String namestaff) {
        this.namestaff = namestaff;
    }
    public String getNumberstaff() {
        return numberstaff;
    }

    public void setNumberstaff(String numberstaff) {
        this.numberstaff = numberstaff;
    }
    public String getAddressstaff() {
        return addressstaff;
    }

    public void setAddressstaff(String addressstaff) {
        this.addressstaff = addressstaff;
    }
    
    @Override
    public String toString() {
        return this.namestaff;
    }
}
