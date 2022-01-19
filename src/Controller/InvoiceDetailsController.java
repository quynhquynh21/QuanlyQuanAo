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
import Entity.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
public class InvoiceDetailsController implements DocumentListener, ActionListener {

    InvoiceDetailsDAO DAO;
    private InvoiceDetailsView invoiceDetailsView;
    private InvoiceDetailsModel invoiceDetailsModel;

    public InvoiceDetailsController(InvoiceDetailsView _invoiceDetailsView) {
        invoiceDetailsView = _invoiceDetailsView;
        DAO = new InvoiceDetailsDAO();

    }

    public void showInvoiceDetailsView() {
        String codeinvoice = invoiceDetailsView.codeinvoice.getText().replaceAll("[^\\d.]", "");
        if (!DAO.getAllInvoiceDetail(codeinvoice).isEmpty()) {
            invoiceDetailsView.showListInvoiceDetails(new InvoiceDetailsModel(DAO.getAllInvoiceDetail(codeinvoice)));
        }
        if (DAO.getCBboxNameclothes().getSize() > 0) {
            invoiceDetailsView.showComboboxNameClothes(DAO.getCBboxNameclothes());
        }
//        Locale localeVN = new Locale("vi", "VN");
//        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
//        String str1 = currencyVN.format(invoiceDetailsView.totaLabel.gett]);
        invoiceDetailsView.totaLabel.setText(DAO.getToalPrice(codeinvoice));
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == invoiceDetailsView.btnAdd) {
            InvoiceDetails invoiceDetails = getInvoiceDetailsInfo();
            if (invoiceDetails != null) {
                if (DAO.CheckAdd(invoiceDetails)) {
                    int count = DAO.getcountclothes(invoiceDetails.getCodeclothes());
                    if (count < invoiceDetails.getCountbuy()) {
                        showMessage("Số lượng trong kho không đủ");
                    } else {
                        DAO.insert(invoiceDetails);
                        clearStaffInfo();
                        invoiceDetailsView.showListInvoiceDetails(new InvoiceDetailsModel(DAO.getAllInvoiceDetail(invoiceDetails.getCodeinvoice())));
                        showMessage("Thêm thành công!");
                        invoiceDetailsView.totaLabel.setText(DAO.getToalPrice(invoiceDetails.getCodeinvoice()));
                        DAO.updatecoutnclothes(invoiceDetails.getCodeclothes(), invoiceDetails.getCountbuy());
                    }
                } else {
                    showMessage("Sản phẩm đã tồn tại trong giỏ hàng");
                }
            }
        } else if (e.getSource() == invoiceDetailsView.btnEdit) {
            InvoiceDetails invoiceDetails = getInvoiceDetailsInfo();
            if (invoiceDetails != null) {
                int countclothesinvoice = DAO.getCountclothes(invoiceDetails.getCodeinvoice(), invoiceDetails.getCodeclothes());
                int count = DAO.getsumCountclothes(invoiceDetails.getCodeclothes(),countclothesinvoice);
                if (count < invoiceDetails.getCountbuy()) {
                    showMessage("Số lượng trong kho không đủ");
                } else {
                    DAO.updatecoutnclothes(invoiceDetails.getCodeclothes(), invoiceDetails.getCountbuy() - countclothesinvoice);
                    DAO.update(invoiceDetails);
                    invoiceDetailsView.showListInvoiceDetails(new InvoiceDetailsModel(DAO.getAllInvoiceDetail(invoiceDetails.getCodeinvoice())));
                    showMessage("Thay đổi thành công");
                    invoiceDetailsView.totaLabel.setText(DAO.getToalPrice(invoiceDetails.getCodeinvoice()));
                }

            }
        } else if (e.getSource() == invoiceDetailsView.btnDelet) {
            InvoiceDetails invoiceDetails = getInvoiceDetailsInfo();
            if (invoiceDetails != null) {
                DAO.updatecoutnclothes(invoiceDetails.getCodeclothes(), -invoiceDetails.getCountbuy());
                DAO.delete(invoiceDetails);
                clearStaffInfo();
                if (!DAO.getAllInvoiceDetail(invoiceDetails.getCodeinvoice()).isEmpty()) {
                    invoiceDetailsView.showListInvoiceDetails(new InvoiceDetailsModel(DAO.getAllInvoiceDetail(invoiceDetails.getCodeinvoice())));
                }
                invoiceDetailsView.totaLabel.setText(DAO.getToalPrice(invoiceDetails.getCodeinvoice()));
                showMessage("Xóa thành công!");
            }
        }
    }

    public InvoiceDetails getInvoiceDetailsInfo() {
        try {
            InvoiceDetails invoice = new InvoiceDetails();
            String codeInvoice = invoiceDetailsView.codeinvoice.getText().trim().replaceAll("[^\\d.]", "");
            invoice.setCodeinvoice(codeInvoice);
            Import ip = (Import) invoiceDetailsView.nameclothes.getSelectedItem();
            int codeClothes = Integer.parseInt(ip.getCodeclothes().replaceAll("[^\\d.]", ""));
            invoice.setCodeclothes(codeClothes);
            invoice.setCountbuy(Integer.parseInt(invoiceDetailsView.countbuy.getText()));
            return invoice;
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
        return null;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(invoiceDetailsView, message);
    }

    public void clearStaffInfo() {
//        invoiceDetailsView.codeinvoice.setText("");
//        invoiceDetailsView.nameclothes.setSelectedItem(0);
//        invoiceDetailsView.purchasedate.setDate();
        invoiceDetailsView.countbuy.setText("");
        invoiceDetailsView.unitprice.setText("");
        invoiceDetailsView.total.setText("");
        invoiceDetailsView.codeclothes.setText("");
        // disable Edit and Delete buttons
//        staffView.btnDelet.setEnabled(false);
//        staffView.btnEdit.setEnabled(false);
        // enable Add button
//        staffView.btnAdd.setEnabled(true);
    }

    public class myEventTable implements ListSelectionListener {

        public myEventTable() {

        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int row = invoiceDetailsView.tableqa.getSelectedRow();
            if (row >= 0) {
                invoiceDetailsView.codeclothes.setText(invoiceDetailsView.tableqa.getModel().getValueAt(row, 0).toString());

                String nameclothes = invoiceDetailsView.tableqa.getValueAt(row, 1).toString();
                for (int i = 0; i < invoiceDetailsView.nameclothes.getItemCount(); i++) {
                    if (invoiceDetailsView.nameclothes.getItemAt(i).toString().equalsIgnoreCase(nameclothes)) {
                        invoiceDetailsView.nameclothes.setSelectedIndex(i);
                    }
                }
                invoiceDetailsView.countbuy.setText(invoiceDetailsView.tableqa.getModel().getValueAt(row, 2).toString());
                invoiceDetailsView.unitprice.setText(invoiceDetailsView.tableqa.getModel().getValueAt(row, 3).toString());
                invoiceDetailsView.total.setText(invoiceDetailsView.tableqa.getModel().getValueAt(row, 4).toString());

                // enable Edit and Delete buttons
                invoiceDetailsView.btnAdd.setEnabled(true);
                invoiceDetailsView.btnDelet.setEnabled(true);
                invoiceDetailsView.btnEdit.setEnabled(true);
            }
        }

    }
}
