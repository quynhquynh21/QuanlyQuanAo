/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entity.Import;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;
import sun.security.rsa.RSACore;

/**
 *
 * @author BUIDUCQUYNH
 */
public class ImporModel extends AbstractTableModel{
     private static ArrayList<Import> imports = null;
    private Object[][] data = null;
    private String [] ColumnName = {"<html><center>Mã quần<br>áo","<html><center>Tên quần<br>áo","Giá ", "Số lượng",
    "<html><center>Đơn vị <br>tính","Ngày nhập","<html><center>Tên nhân<br>viên","<html><center>Tên nhà<br>cung cấp"};
    
    public ImporModel(ArrayList<Import> _imports) {
        imports = _imports;
        data = new Object[imports.size()][];
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        for (int i = 0; i < imports.size(); i++) {
            Import st = imports.get(i);
            
            String priceclothes = currencyVN.format(st.getPriceclothes());
            Object[] row = {st.getCodeclothes(), st.getNameclothes(), priceclothes, st.getCountclothes(),
                st.getUnit(),st.getDateimport(),st.getNameimporter(),st.getNamesupplier()};
            data[i] = row;

        }
    }

    @Override
    public int getRowCount() {
        return imports.size();
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
