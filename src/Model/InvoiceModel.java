/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import Entity.Invoice;

/**
 *
 * @author BUIDUCQUYNH
 */
public class InvoiceModel extends AbstractTableModel {

    private static ArrayList<Invoice> invoices = null;
    private Object[][] data = null;
    private String[] ColumnName = {"<html><center>Mã Hoá<br>đơn", "<html><center>Tên khách<br>hàng", "Ngày mua",
        "<html><center>Tên nhân<br>viên", "<html><center>Tên quần<br>áo", "Đơn giá",
        "Số lượng", "Thành tiền"};

    public InvoiceModel(ArrayList<Invoice> _invoice) {
        invoices = _invoice;
        data = new Object[invoices.size()][];
        for (int i = 0; i < invoices.size(); i++) {
            Invoice st = invoices.get(i);
            Object[] row = {st.getCodeinvoice(), st.getNamecustommer(), st.getPurchasedate(), st.getNamestaff()};
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
