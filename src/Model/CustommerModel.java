/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entity.Custommer;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author BUIDUCQUYNH
 */
public class CustommerModel extends AbstractTableModel{
     private static ArrayList<Custommer> custommers = null;
    private Object[][] data = null;
    private String [] ColumnName = {"<html><center>Mã khách<br>hàng","<html><center>Tên khách<br>hàng","<html><center>Số điện<br>thoại", "Địa chỉ"};
    
    public CustommerModel(ArrayList<Custommer> _custommers) {
        custommers = _custommers;
        data = new Object[custommers.size()][];
        for (int i = 0; i < custommers.size(); i++) {
            Custommer st = custommers.get(i);
            Object[] row = {st.getCodecustommer(), st.getNamecustomer(), st.getNumbercustomer(), st.getAddresscustomer()};
            data[i] = row;

        }
    }

    @Override
    public int getRowCount() {
        return custommers.size();
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
