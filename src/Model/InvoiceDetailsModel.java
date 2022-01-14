/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entity.InvoiceDetails;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author BUIDUCQUYNH
 */
    public class InvoiceDetailsModel extends AbstractTableModel {

    private static ArrayList<InvoiceDetails> invoices = null;
    private Object[][] data = null;
    private String[] ColumnName = {"<html><center>Mã Hoá<br>đơn", "<html><center>Mã sản<br>phẩm", "<html><center>Tên quần<br> áo",
                                   "Số lượng", "Đơn giá", "Thành tiền"};

    public InvoiceDetailsModel(ArrayList<InvoiceDetails> _invoice) {
        invoices = _invoice;
        data = new Object[invoices.size()][];
        for (int i = 0; i < invoices.size(); i++) {
            InvoiceDetails st = invoices.get(i);
            Object[] row = {st.getCodeinvoice(), st.getCodeclothes(),st.getNameclothes(),st.getCountbuy(),st.getUnitprice(),st.getTotal()};
            data[i] = row;

        }
    }

    @Override
    public int getRowCount() {
        return invoices.size();
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

