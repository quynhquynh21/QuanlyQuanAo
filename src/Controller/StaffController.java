/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.StaffDAO;
import Entity.Staff;
import View.StaffView;
import Model.StaffModel;
import com.sun.javafx.scene.control.behavior.TableRowBehavior;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.Normalizer;
import java.util.regex.Pattern;
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
public class StaffController implements ActionListener, DocumentListener {

    StaffDAO DAO;
    SQL check_number;
    private StaffView staffView;
    private StaffModel staffModel;

    public StaffController(StaffView _sStaffView) {
        staffView = _sStaffView;
        DAO = new StaffDAO();
        check_number = new SQL();
    }

    public StaffController() {
    }

    public void showStaffView() {
        if (!DAO.getAllStaff().isEmpty()) {
            staffView.showListStaff(new StaffModel(DAO.getAllStaff()));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == staffView.btnAdd) {
            Staff staff = getStaffInfo();
            if (staff != null) {
                if (check_number.checkPhone(staff.getNumberstaff())) {
                    DAO.insert(staff);
                    clearStaffInfo();
                    staffView.showListStaff(new StaffModel(DAO.getAllStaff()));
                    showMessage("Thêm thành công!");
                } else {
                    showMessage("Sai định dạng số điện thoại");
                }

            }
        } else if (e.getSource() == staffView.btnEdit) {
            Staff staff = getStaffInfo();
            if (staff != null) {
                DAO.updateStaff(staff);
                staffView.showListStaff(new StaffModel(DAO.getAllStaff()));
                showMessage("Thay đổi thành công");
            }

        } else if (e.getSource() == staffView.btnDelet) {
            Staff staff = getStaffInfo();

            if (staff != null) {
                if (DAO.CheckDelete(staff.getCodestaff())) {
                    DAO.deleteStaff(staff);
                    clearStaffInfo();
                    ArrayList<Staff> ds = DAO.getAllStaff();
                    if (ds != null) {
                        staffView.showListStaff(new StaffModel(ds));
                    } else {
                        showMessage("Dữ liệu rỗng");
                    }
                    showMessage("Xóa thành công!");
                } else {
                    showMessage("Nhân viên đang làm việc");
                }
            }
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(staffView, message);
    }

    public void clearStaffInfo() {
        staffView.codestaff.setText("");
        staffView.namestaff.setText("");
        staffView.numberstaff.setText("");
        staffView.addressstaff.setText("");
        // disable Edit and Delete buttons
//        staffView.btnDelet.setEnabled(false);
//        staffView.btnEdit.setEnabled(false);
        // enable Add button
//        staffView.btnAdd.setEnabled(true);
    }

    public Staff getStaffInfo() {
        // validate student        
        try {
            Staff staff = new Staff();
            if (staffView.codestaff.getText() != null && !"".equals(staffView.codestaff.getText())) {
                staff.setCodestaff(Integer.parseInt(staffView.codestaff.getText()));
            }
            staff.setNamestaff(staffView.namestaff.getText().trim());
            staff.setNumberstaff(staffView.numberstaff.getText().trim());
            staff.setAddressstaff(staffView.addressstaff.getText().trim());
            return staff;
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
        return null;
    }

    public void search() {
        String txtString = staffView.textFieldSearch.getText();
        staffModel = (StaffModel) staffView.tableqa.getModel();
        TableRowSorter<StaffModel> sorter = new TableRowSorter<>(staffModel);
        staffView.tableqa.setRowSorter(sorter);
//        sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + txtString)));
        

        
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
            int row = staffView.tableqa.getSelectedRow();
            if (row >= 0) {
                staffView.codestaff.setText(staffView.tableqa.getModel().getValueAt(row, 0).toString());
                staffView.namestaff.setText(staffView.tableqa.getModel().getValueAt(row, 1).toString());
                staffView.numberstaff.setText(staffView.tableqa.getModel().getValueAt(row, 2).toString());
                staffView.addressstaff.setText(staffView.tableqa.getModel().getValueAt(row, 3).toString());
                // enable Edit and Delete buttons
                staffView.btnAdd.setEnabled(true);
                staffView.btnDelet.setEnabled(true);
                staffView.btnEdit.setEnabled(true);
            }
        }

    }

    public static void main(String[] args) {
        StaffDAO a = new StaffDAO();
        System.out.println(a.getAllStaff());
    }
}
