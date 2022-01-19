/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ImportDAO;
import Entity.Custommer;
import Entity.Invoice;
import Entity.Staff;
import Entity.*;
import Model.ImporModel;
import Model.InvoiceModel;
import Model.StaffModel;
import View.ImportView;
import View.InvoiceDetailsView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import DAO.SQL;

/**
 *
 * @author BUIDUCQUYNH
 */
public class ImportController implements ActionListener, DocumentListener {

    ImportDAO DAO;
    SQL checkNumber;
    private ImportView importView;
    private ImporModel imporModel;

    public ImportController() {
    }

    public ImportController(ImportView _importView) {
        importView = _importView;
        DAO = new ImportDAO();
        checkNumber = new SQL();
    }

    public void showImportView() {
        importView.showListImport(new ImporModel(DAO.getAllImports()));
        importView.showComboboxNameSupplier(DAO.getCBboxNameSupplier());
        importView.showComboboxNameStaff(DAO.getCBboxNameStaff());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == importView.btnAdd) {
            Import ip = getImportInfo();
            if (ip != null) {
                if (DAO.CheckAdd(ip.getNameclothes())) {
                    DAO.insert(ip);
                    clearStaffInfo();
                    importView.showListImport(new ImporModel(DAO.getAllImports()));
                    showMessage("Thêm thành công!");
                } else {
                    showMessage("Quần áo đã tồn tại");
                }

            }
        } else if (e.getSource() == importView.btnEdit) {
            Import ip = getImportInfo();
            if (ip != null) {
                DAO.update(ip);
                clearStaffInfo();
                importView.showListImport(new ImporModel(DAO.getAllImports()));
                showMessage("Thay đổi thành công");
            }

        } else if (e.getSource() == importView.btnDelet) {
            Import ip = getImportInfo();

            if (ip != null) {
                if (DAO.CheckDelete(ip.getCodeclothes())) {
                    DAO.delete(ip);
                    clearStaffInfo();
                    ArrayList<Import> ds = DAO.getAllImports();
                    if (ds != null) {
                        importView.showListImport(new ImporModel(ds));
                    } else {
                        showMessage("Dữ liệu rỗng");
                    }
                    showMessage("Xóa thành công!");
                } else {
                    showMessage("<html><center>Vui lòng xoá quần áo trong bảng<br>chi tiết hoá đơn");
                }
            }

        }
    }

    public Import getImportInfo() {

        // validate student        
        try {
            Import ip = new Import();
            if (importView.codeclothes.getText() != null && !"".equals(importView.codeclothes.getText())) {
                ip.setCodeclothes(importView.codeclothes.getText().trim().replaceAll("[^\\d.]", ""));
            }
            ip.setNameclothes(importView.nameclothes.getText().trim());
            ip.setPriceclothes(Float.parseFloat(importView.priceclothes.getText()));
            ip.setCountclothes(Integer.parseInt(importView.countclothes.getText()));
            ip.setUnit(importView.unit.getText().trim());

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            ip.setDateimport(df.format(importView.dateimport.getDate()));

            Staff staff = (Staff) importView.importer.getSelectedItem();
            int codeStaff = Integer.parseInt(staff.getCodestaff().replaceAll("[^\\d.]", ""));
            ip.setCodestaff(codeStaff);

            Suppllier suppllier = (Suppllier) importView.supplier.getSelectedItem();
            int codeSupplier = Integer.parseInt(suppllier.getCodesupplier().replaceAll("[^\\d.]", ""));
            ip.setCodesupplier(codeSupplier);

            return ip;
        } catch (Exception e) {
            showMessage("Sai địng dạng nhập vào");
        }
        return null;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(importView, message);
    }

    public void clearStaffInfo() {
        importView.codeclothes.setText("");
        importView.nameclothes.setText("");
        importView.priceclothes.setText("");
        importView.countclothes.setText("");
        importView.unit.setText("");
//        importView.dateimport.setDate(null);
        importView.importer.setSelectedItem(null);
        importView.supplier.setSelectedItem(null);
        // disable Edit and Delete buttons
//        staffView.btnDelet.setEnabled(false);
//        staffView.btnEdit.setEnabled(false);
        // enable Add button
//        staffView.btnAdd.setEnabled(true);
    }

    public void search() {
        String txtString = importView.textFieldSearch.getText();
        imporModel = (ImporModel) importView.tableqa.getModel();
        TableRowSorter<ImporModel> sorter = new TableRowSorter<>(imporModel);
        importView.tableqa.setRowSorter(sorter);
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
            int row = importView.tableqa.getSelectedRow();
            if (row >= 0) {
                importView.codeclothes.setText(importView.tableqa.getModel().getValueAt(row, 0).toString());
                importView.nameclothes.setText(importView.tableqa.getModel().getValueAt(row, 1).toString());
                importView.priceclothes.setText(importView.tableqa.getModel().getValueAt(row, 2).toString().replaceAll("[^\\d]", ""));
                importView.countclothes.setText(importView.tableqa.getModel().getValueAt(row, 3).toString());
                importView.unit.setText(importView.tableqa.getModel().getValueAt(row, 4).toString());

                Date date;
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy").parse((String) importView.tableqa.getModel().getValueAt(row, 5).toString());
                    importView.dateimport.setDate(date);
                } catch (ParseException ex) {
                    Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }

                String namestaff = importView.tableqa.getValueAt(row, 6).toString();
                for (int i = 0; i < importView.importer.getItemCount(); i++) {
                    if (importView.importer.getItemAt(i).toString().equalsIgnoreCase(namestaff)) {
                        importView.importer.setSelectedIndex(i);
                    }
                }
                String nameSupplier = importView.tableqa.getValueAt(row, 7).toString();
                for (int i = 0; i < importView.supplier.getItemCount(); i++) {
                    if (importView.supplier.getItemAt(i).toString().equalsIgnoreCase(nameSupplier)) {
                        importView.supplier.setSelectedIndex(i);
                    }
                }
                // enable Edit and Delete buttons
                importView.btnAdd.setEnabled(true);
                importView.btnDelet.setEnabled(true);
                importView.btnEdit.setEnabled(true);
            }
        }

    }
}
