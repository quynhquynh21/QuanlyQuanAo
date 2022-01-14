/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InvoiceDetailsModel;
import View.InvoiceDetailsView;
import DAO.InvoiceDetailsDAO;
import Model.InvoiceModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author BUIDUCQUYNH
 */
public class InvoiceDetailsController implements DocumentListener {

    InvoiceDetailsDAO DAO;
    private InvoiceDetailsView invoiceDetailsView;
    private InvoiceDetailsModel invoiceDetailsModel;

    public InvoiceDetailsController(InvoiceDetailsView _invoiceDetailsView, InvoiceDetailsModel _invoiceDetailsModel) {
        invoiceDetailsModel = _invoiceDetailsModel;
        invoiceDetailsView = _invoiceDetailsView;
        DAO = new InvoiceDetailsDAO();
       
    }

    public void showInvoiceDetailsView() {
        
        invoiceDetailsView.showListInvoiceDetails(invoiceDetailsModel);
        invoiceDetailsView.showComboboxNameClothes(DAO.getCBboxNameclothes());
        invoiceDetailsView.totaLabel.setText(DAO.getToalPrice());
    }

    public void search() {
        String txtString = invoiceDetailsView.textFieldSearch.getText();
        invoiceDetailsModel = (InvoiceDetailsModel) invoiceDetailsView.tableqa.getModel();
        TableRowSorter<InvoiceDetailsModel> sorter = new TableRowSorter<>(invoiceDetailsModel);
        invoiceDetailsView.tableqa.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + txtString)));
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        search();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        search();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class myEventTable implements ListSelectionListener {

        public myEventTable() {

        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int row = invoiceDetailsView.tableqa.getSelectedRow();
            if (row >= 0) {
                invoiceDetailsView.codeinvoice.setText(invoiceDetailsView.tableqa.getModel().getValueAt(row, 0).toString());
                invoiceDetailsView.codeclothes.setText(invoiceDetailsView.tableqa.getModel().getValueAt(row, 1).toString());

                String nameclothes = invoiceDetailsView.tableqa.getValueAt(row, 2).toString();
                for (int i = 0; i < invoiceDetailsView.nameclothes.getItemCount(); i++) {
                    if (invoiceDetailsView.nameclothes.getItemAt(i).toString().equalsIgnoreCase(nameclothes)) {
                        invoiceDetailsView.nameclothes.setSelectedIndex(i);
                    }
                }
                invoiceDetailsView.countbuy.setText(invoiceDetailsView.tableqa.getModel().getValueAt(row, 3).toString());
                invoiceDetailsView.unitprice.setText(invoiceDetailsView.tableqa.getModel().getValueAt(row, 4).toString());
                invoiceDetailsView.total.setText(invoiceDetailsView.tableqa.getModel().getValueAt(row, 5).toString());
                
                // enable Edit and Delete buttons
                invoiceDetailsView.btnAdd.setEnabled(true);
                invoiceDetailsView.btnDelet.setEnabled(true);
                invoiceDetailsView.btnEdit.setEnabled(true);
            }
        }

    }
}
