/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.SuppllierDAO;
import Entity.Suppllier;
import Model.StaffModel;
import View.SupplierView;
import Model.SupplierModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
public class SupplierController implements ActionListener, DocumentListener {
    
    SuppllierDAO DAO;
    SQL checkNumber;
    private SupplierView supplierView;
    private SupplierModel supplierModel;
    
    public SupplierController(SupplierView _supplierView) {
        supplierView = _supplierView;
        DAO = new SuppllierDAO();
        checkNumber = new SQL();
    }
    
    public void showSupplierView() {
        supplierView.showListSupplier(new SupplierModel(DAO.getAllSuppllier()));
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == supplierView.btnAdd) {
            Suppllier custommer = getSupplierInfo();
            if (custommer != null) {
                if (checkNumber.checkPhone(custommer.getNumbersupplier())) {
                    if (DAO.CheckAdd(custommer.getNumbersupplier())) {
                        DAO.insert(custommer);
                        clearStaffInfo();
                        supplierView.showListSupplier(new SupplierModel(DAO.getAllSuppllier()));
                        showMessage("Thêm thành công!");
                    } else {
                        showMessage("Nhà cung cấp đã tồn tại");
                    }
                    
                }
            }
        } else if (e.getSource() == supplierView.btnEdit) {
            Suppllier suppllier = getSupplierInfo();
            if (suppllier != null) {
                DAO.updateSupplier(suppllier);
                supplierView.showListSupplier(new SupplierModel(DAO.getAllSuppllier()));
                showMessage("Thay đổi thành công");
            }
            
        } else if (e.getSource() == supplierView.btnDelet) {
            Suppllier suppllier = getSupplierInfo();
            
            if (suppllier != null) {
                if (DAO.CheckDelete(suppllier.getCodesupplier())) {
                    DAO.deleteSupplier(suppllier);
                    clearStaffInfo();
                    ArrayList<Suppllier> ds = DAO.getAllSuppllier();
                    if (ds != null) {
                        supplierView.showListSupplier(new SupplierModel(ds));
                    } else {
                        showMessage("Dữ liệu rỗng");
                    }
                    showMessage("Xóa thành công!");
                } else {
                    showMessage("Nhà cung cấp đang sử dụng");
                }
            }
        }
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(supplierView, message);
    }
    
    public void clearStaffInfo() {
        supplierView.codesupplier.setText("");
        supplierView.namesupplier.setText("");
        supplierView.numbersupplier.setText("");
        supplierView.addresssupplier.setText("");
        // disable Edit and Delete buttons
//        custommerView.btnDelet.setEnabled(false);
//        custommerView.btnEdit.setEnabled(false);
        // enable Add button
//        custommerView.btnAdd.setEnabled(true);
    }
    
    public Suppllier getSupplierInfo() {
        // validate student        
        try {
            Suppllier suppllier = new Suppllier();
            if (supplierView.codesupplier.getText() != null && !"".equals(supplierView.codesupplier.getText())) {
                suppllier.setCodesupplier(supplierView.codesupplier.getText().trim().replaceAll("[^\\d.]", ""));
            }
            suppllier.setNamesupplier(supplierView.namesupplier.getText().trim());
            suppllier.setNumbersupplier(supplierView.numbersupplier.getText().trim());
            suppllier.setAddresssupplier(supplierView.addresssupplier.getText().trim());
            return suppllier;
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
        return null;
    }
    
    public void search() {
        String txtString = supplierView.textFieldSearch.getText();
        supplierModel = (SupplierModel) supplierView.tableqa.getModel();
        TableRowSorter<SupplierModel> sorter = new TableRowSorter<>(supplierModel);
        supplierView.tableqa.setRowSorter(sorter);
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
            int row = supplierView.tableqa.getSelectedRow();
            if (row >= 0) {
                supplierView.codesupplier.setText(supplierView.tableqa.getModel().getValueAt(row, 0).toString());
                supplierView.namesupplier.setText(supplierView.tableqa.getModel().getValueAt(row, 1).toString());
                supplierView.numbersupplier.setText(supplierView.tableqa.getModel().getValueAt(row, 2).toString());
                supplierView.addresssupplier.setText(supplierView.tableqa.getModel().getValueAt(row, 3).toString());
                // enable Edit and Delete buttons
                supplierView.btnAdd.setEnabled(true);
                supplierView.btnDelet.setEnabled(true);
                supplierView.btnEdit.setEnabled(true);
            }
        }
    }
}
