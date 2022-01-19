/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.CustommerDAO;
import Entity.Custommer;
import Model.CustommerModel;
import Model.StaffModel;
import View.CustommerView;
import java.awt.event.*;
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
public class CustommerController implements ActionListener, DocumentListener {

    CustommerDAO DAO;
    SQL checkNumber;
    private CustommerView custommerView;
    private CustommerModel custommerModel;

    public CustommerController(CustommerView _CustommerView) {
        custommerView = _CustommerView;
        DAO = new CustommerDAO();
        checkNumber = new SQL();
    }

    public void showCustommerView() {
        custommerView.showListCustommer(new CustommerModel(DAO.getAllCustommers()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == custommerView.btnAdd) {
            Custommer custommer = getCustommerInfo();
            if (custommer != null) {
                if (checkNumber.checkPhone(custommer.getNumbercustomer())) {
                    if (DAO.CheckAdd(custommer.getNumbercustomer())) {
                        DAO.insert(custommer);
                        clearStaffInfo();
                        custommerView.showListCustommer(new CustommerModel(DAO.getAllCustommers()));
                        showMessage("Thêm thành công!");
                    } else {
                        showMessage("Khách hàng đã tồn tại");
                        
                    }
                } else {
                    showMessage("Sai định dạng số điện thoại");
                }

            }
        } else if (e.getSource() == custommerView.btnEdit) {
            Custommer custommer = getCustommerInfo();
            if (custommer != null) {
                DAO.updateCustommer(custommer);
                custommerView.showListCustommer(new CustommerModel(DAO.getAllCustommers()));
                showMessage("Thay đổi thành công");
            }

        } else if (e.getSource() == custommerView.btnDelet) {
            Custommer custommer = getCustommerInfo();

            if (custommer != null) {
                if (DAO.CheckDelete(custommer.getCodecustommer())) {
                    DAO.deleteCustommer(custommer);
                    clearStaffInfo();
                    ArrayList<Custommer> ds = DAO.getAllCustommers();
                    if (ds != null) {
                        custommerView.showListCustommer(new CustommerModel(ds));
                    } else {
                        showMessage("Dữ liệu rỗng");
                    }
                    showMessage("Xóa thành công!");
                } else {
                    showMessage("<html><center>Vui lòng xoá thông tin hoá đơn<br>của khách hàng");
                }
            }
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(custommerView, message);
    }

    public void clearStaffInfo() {
        custommerView.codecustommer.setText("");
        custommerView.namecustomer.setText("");
        custommerView.numbercustomer.setText("");
        custommerView.addresscustomer.setText("");
        // disable Edit and Delete buttons
//        custommerView.btnDelet.setEnabled(false);
//        custommerView.btnEdit.setEnabled(false);
        // enable Add button
//        custommerView.btnAdd.setEnabled(true);
    }

    public Custommer getCustommerInfo() {
        try {
            Custommer custommer = new Custommer();
            if (custommerView.codecustommer.getText() != null && !"".equals(custommerView.codecustommer.getText())) {
                custommer.setCodecustommer(custommerView.codecustommer.getText().trim().replaceAll("[^\\d.]", ""));
            }
            custommer.setNamecustomer(custommerView.namecustomer.getText().trim());
            custommer.setNumbercustomer(custommerView.numbercustomer.getText().trim());
            custommer.setAddresscustomer(custommerView.addresscustomer.getText().trim());
            return custommer;
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
        return null;
    }

    public void search() {
        String txtString = custommerView.textFieldSearch.getText();
        custommerModel = (CustommerModel) custommerView.tableqa.getModel();
        TableRowSorter<CustommerModel> sorter = new TableRowSorter<>(custommerModel);
        custommerView.tableqa.setRowSorter(sorter);
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
            int row = custommerView.tableqa.getSelectedRow();
            if (row >= 0) {
                custommerView.codecustommer.setText(custommerView.tableqa.getModel().getValueAt(row, 0).toString());
                custommerView.namecustomer.setText(custommerView.tableqa.getModel().getValueAt(row, 1).toString());
                custommerView.numbercustomer.setText(custommerView.tableqa.getModel().getValueAt(row, 2).toString());
                custommerView.addresscustomer.setText(custommerView.tableqa.getModel().getValueAt(row, 3).toString());
                // enable Edit and Delete buttons
                custommerView.btnAdd.setEnabled(true);
                custommerView.btnDelet.setEnabled(true);
                custommerView.btnEdit.setEnabled(true);
            }
        }

    }
}
