/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import Entity.Staff;
import Model.StaffModel;
import DAO.StaffDAO;
import Controller.StaffController;
/**
 *
 * @author BUIDUCQUYNH
 */
public class StaffView extends JPanel {

    public JTable tableqa;
    public JButton btnAdd, btnDelet, btnEdit;
    JScrollPane jScrollPaneqaTable;
    public JTextField codestaff, textFieldSearch, namestaff, numberstaff, addressstaff;
    StaffModel staffModel;
    StaffController controller;
    StaffDAO sinhVienDAO;
    
    public void showListStaff(StaffModel staffModel) {
        tableqa.setModel(staffModel);
        tableqa.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
        tableqa.getColumnModel().getColumn(0).setPreferredWidth(110);
        tableqa.getColumnModel().getColumn(1).setPreferredWidth(120);
        tableqa.getColumnModel().getColumn(2).setPreferredWidth(120);
        tableqa.getColumnModel().getColumn(3).setPreferredWidth(200);
    }

    public StaffView() {
        tableqa = new JTable();
       
        controller = new StaffController(this);
//        controller.showStaffView();
        StaffController.myEventTable mEventTable = controller.new myEventTable();
        tableqa.getSelectionModel().addListSelectionListener(mEventTable);
        
        setBorder(new EmptyBorder(8, 8, 8, 8));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        Input2 input = new Input2();
        input.setBorder(new CompoundBorder(new TitledBorder("Nhập thông tin nhân viên"), new EmptyBorder(4, 4, 4, 4)));
        add(input, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        Table2 table = new Table2();
        table.setBorder(new CompoundBorder(new TitledBorder("Bảng thông tin nhân viên"), new EmptyBorder(4, 4, 4, 4)));
        add(table, gbc);

    }

    class Input2 extends JPanel {

        public Input2() {
            this.setPreferredSize(new Dimension(600, 150));
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            add(new JLabel("Mã nhân viên"), gbc);

            gbc.gridx += 2;
            add(new JLabel("Tên nhân viên"), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Số điện thoại"), gbc);

            gbc.gridx += 2;
            add(new JLabel("Địa chỉ"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
//      giãn chiều ngang đối tượng sao cho khít
            gbc.fill = GridBagConstraints.HORIZONTAL;
//      neo vị trí đặt đối tượng theo một vị trí nào đó trong ô đặt
            gbc.anchor = GridBagConstraints.WEST;
//      khoảng cách lớn ra giữa các đối tượng 
            gbc.weightx = 0.5;
            codestaff = new JTextField(10);
            codestaff.setEnabled(false);
            add(codestaff, gbc);

            gbc.gridx += 2;
            namestaff = new JTextField(10);
            add(namestaff, gbc);

            gbc.gridx = 1;
            gbc.gridy++;
            numberstaff = new JTextField(10);
            add(numberstaff, gbc);

            gbc.gridx += 2;
            addressstaff = new JTextField(10);
            add(addressstaff, gbc);
        }
    }

    class Table2 extends JPanel {
        public Table2() {
            
            ImageIcon icon1 = FormView.createImageIcon("images/delete_30px.png");
            ImageIcon icon2 = FormView.createImageIcon("images/add_64px.png");
            ImageIcon icon3 = FormView.createImageIcon("images/edit_24px.png");

            JPanel detainJPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            jScrollPaneqaTable = new JScrollPane(tableqa);
            detainJPanel.add(jScrollPaneqaTable, gbc);

            JPanel serach = new JPanel(new FlowLayout());
            serach.add(new JLabel("Tìm kiếm"));

            textFieldSearch = new JTextField(15);
            textFieldSearch.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(77, 77, 77)));
            textFieldSearch.getDocument().addDocumentListener(controller);
            serach.add(textFieldSearch);

            btnAdd = new JButton("Thêm", icon2);
            btnEdit = new JButton("Sửa",icon3);
            btnDelet = new JButton("Xoá",icon1);
            
            btnAdd.addActionListener(controller);
            btnAdd.setFocusPainted(false);
            btnEdit.addActionListener(controller);
            btnEdit.setFocusPainted(false);
            btnDelet.addActionListener(controller);
            btnDelet.setFocusPainted(false);
            JPanel buttonsPane = new JPanel(new GridBagLayout());
            gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            buttonsPane.add(btnAdd, gbc);

            gbc.gridy++;
            buttonsPane.add(btnEdit, gbc);

            gbc.gridy++;
            buttonsPane.add(btnDelet, gbc);

            setLayout(new BorderLayout());
            add(detainJPanel, BorderLayout.CENTER);
            add(serach, BorderLayout.NORTH);

            add(buttonsPane, BorderLayout.EAST);

        }
    }
}
