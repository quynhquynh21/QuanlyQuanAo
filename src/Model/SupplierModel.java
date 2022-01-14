/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import  Entity.Suppllier;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author BUIDUCQUYNH
 */
public class SupplierModel extends AbstractTableModel{
    private static ArrayList<Suppllier> supplliers = null;
    private Object[][] data = null;
    private String [] ColumnName = {"<html><center>Mã nhà <br>cung cấp","<html><center>Tên nhà<br>cung cấp","<html><center>Số điện<br>thoại", "Địa chỉ"};
    
    public SupplierModel(ArrayList<Suppllier> _supplliers) {
        supplliers = _supplliers;
        data = new Object[supplliers.size()][];
        for (int i = 0; i < supplliers.size(); i++) {
            Suppllier st = supplliers.get(i);
            Object[] row = {st.getCodesupplier(), st.getNamesupplier(), st.getNumbersupplier(), st.getAddresssupplier()};
            data[i] = row;

        }
    }

    @Override
    public int getRowCount() {
        return supplliers.size();
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
