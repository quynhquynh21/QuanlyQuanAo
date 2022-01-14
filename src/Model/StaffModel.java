/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import Entity.Staff;

/**
 *
 * @author BUIDUCQUYNH
 */
public class StaffModel extends AbstractTableModel {

    private static ArrayList<Staff> staffs = null;
    private Object[][] data = null;
    private String [] ColumnName = {"<html><center>Mã nhân<br>viên","<html><center>Tên nhân<br>viên",
        "<html><center>Số điện<br>thoại", "Địa chỉ"};
    
    public StaffModel(ArrayList<Staff> _staffs) {
        staffs = _staffs;
        data = new Object[staffs.size()][];
        for (int i = 0; i < staffs.size(); i++) {
            Staff st = staffs.get(i);
            Object[] row = {st.getCodestaff(), st.getNamestaff(), st.getNumberstaff(), st.getAddressstaff()};
            data[i] = row;

        }
    }

    @Override
    public int getRowCount() {
        return staffs.size();
    }

    @Override
    public int getColumnCount() {
        return data[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
        @Override
    public String getColumnName(int colunnIdex) {
        return ColumnName[colunnIdex];
    }
}
