/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.InvoiceDAO;
import Model.InvoiceModel;
import View.InvoiceView;
import View.InvoiceDetailsView;
import Entity.Invoice;
import Entity.*;
import Model.StaffModel;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.toedter.calendar.JDateChooser;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author BUIDUCQUYNH
 */
public class InvoiceController implements ActionListener, DocumentListener {

    InvoiceDAO DAO;
    private InvoiceView invoiceView;
    private InvoiceModel invoiceModel;

    public InvoiceController(InvoiceView _inInvoiceView) {
        invoiceView = _inInvoiceView;
        DAO = new InvoiceDAO();
    }

    public InvoiceController() {
    }

    public void showInvoice() {
        invoiceView.showListInvoice(new InvoiceModel(DAO.getAllInvoice()));
        invoiceView.showComboboxNameCustommer(DAO.getCBboxNameCustommer());
        invoiceView.showComboboxNameStaff(DAO.getCBboxNameStaff());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == invoiceView.btnDetails) {
            InvoiceDetailsView invoiceDetailsView = new InvoiceDetailsView();
            invoiceDetailsView.setVisible(true);
            invoiceDetailsView.setSize(800, 700);
            invoiceDetailsView.setResizable(false);
            invoiceDetailsView.setLocationRelativeTo(null);
        } else if (e.getSource() == invoiceView.btnAdd) {
            Invoice invoice = getInvoiceInfo();
            if (invoice != null) {
                DAO.insert(invoice);
                clearStaffInfo();
                invoiceView.showListInvoice(new InvoiceModel(DAO.getAllInvoice()));
                showMessage("Thêm thành công!");

            }
        } else if (e.getSource() == invoiceView.btnEdit) {
            Invoice invoice = getInvoiceInfo();
            if (invoice != null) {
                DAO.update(invoice);
                invoiceView.showListInvoice(new InvoiceModel(DAO.getAllInvoice()));
                showMessage("Thay đổi thành công");
            }

        } else if (e.getSource() == invoiceView.btnDelet) {
            Invoice invoice = getInvoiceInfo();

            if (invoice != null) {
                DAO.delete(invoice);
                clearStaffInfo();
                ArrayList<Invoice> ds = DAO.getAllInvoice();
                if (ds != null) {
                    invoiceView.showListInvoice(new InvoiceModel(ds));
                } else {
                    showMessage("Dữ liệu rỗng");
                }
                showMessage("Xóa thành công!");
            }
        }
    }

    public Invoice getInvoiceInfo() {

        // validate student        
        try {
            Invoice invoice = new Invoice();
            if (invoiceView.codeinvoice.getText() != null && !"".equals(invoiceView.codeinvoice.getText())) {
                invoice.setCodeinvoice(Integer.parseInt(invoiceView.codeinvoice.getText()));
            }

            Custommer custommer = (Custommer) invoiceView.namecustomer.getSelectedItem();
            int codeCustomer = custommer.getCodecustommer();
            invoice.setCodecustomer(codeCustomer);

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            invoice.setPurchasedate(df.format(invoiceView.purchasedate.getDate()));

            Staff staff = (Staff) invoiceView.namestaff.getSelectedItem();
            int codeStaff = staff.getCodestaff();
            invoice.setCodestaff(codeStaff);

            return invoice;
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
        return null;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(invoiceView, message);
    }

    public void clearStaffInfo() {
        invoiceView.codeinvoice.setText("");
        invoiceView.namecustomer.setSelectedItem(0);
//        invoiceView.purchasedate.setDate();
        invoiceView.namestaff.setSelectedItem(0);
        // disable Edit and Delete buttons
//        staffView.btnDelet.setEnabled(false);
//        staffView.btnEdit.setEnabled(false);
        // enable Add button
//        staffView.btnAdd.setEnabled(true);
    }

    public void search() {
        String txtString = invoiceView.textFieldSearch.getText();
        invoiceModel = (InvoiceModel) invoiceView.tableqa.getModel();
        TableRowSorter<InvoiceModel> sorter = new TableRowSorter<>(invoiceModel);
        invoiceView.tableqa.setRowSorter(sorter);
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
            int row = invoiceView.tableqa.getSelectedRow();
            if (row >= 0) {
                invoiceView.codeinvoice.setText(invoiceView.tableqa.getModel().getValueAt(row, 0).toString());
                String namecustommer = invoiceView.tableqa.getValueAt(row, 1).toString();
                for (int i = 0; i < invoiceView.namecustomer.getItemCount(); i++) {
                    if (invoiceView.namecustomer.getItemAt(i).toString().equalsIgnoreCase(namecustommer)) {
                        invoiceView.namecustomer.setSelectedIndex(i);
                    }
                }

                Date date;
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy").parse((String) invoiceView.tableqa.getModel().getValueAt(row, 2).toString());
                    invoiceView.purchasedate.setDate(date);
                } catch (ParseException ex) {
                    Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }

                String namestaff = invoiceView.tableqa.getValueAt(row, 3).toString();
                for (int i = 0; i < invoiceView.namestaff.getItemCount(); i++) {
                    if (invoiceView.namestaff.getItemAt(i).toString().equalsIgnoreCase(namestaff)) {
                        invoiceView.namestaff.setSelectedIndex(i);
                    }
                }
                // enable Edit and Delete buttons
                invoiceView.btnAdd.setEnabled(true);
                invoiceView.btnDelet.setEnabled(true);
                invoiceView.btnEdit.setEnabled(true);
            }
        }

    }
}
