/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.InvoiceController;
import DAO.InvoiceDAO;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import Model.InvoiceModel;
import Model.StaffModel;
import javax.swing.DefaultComboBoxModel;
/**
 *
 * @author BUIDUCQUYNH
 */
public class InvoiceView extends JPanel {

    public JTable tableqa;
    public JButton btnAdd, btnDelet, btnEdit,btnDetails;
    public JScrollPane jScrollPaneqaTable;
    public JTextField codeinvoice , countbuy, textFieldSearch,total;
    public JDateChooser purchasedate;
    public JComboBox namestaff,namecustomer;
    
    public InvoiceController controller;
    public InvoiceModel invoiceModel;
    InvoiceDAO invoiceDAO;
    
    
    public void showListInvoice(InvoiceModel invoiceModel) {
        tableqa.setModel(invoiceModel);
        tableqa.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
//        tableqa.getColumnModel().getColumn(0).setPreferredWidth(110);
//        tableqa.getColumnModel().getColumn(1).setPreferredWidth(120);
//        tableqa.getColumnModel().getColumn(2).setPreferredWidth(120);
//        tableqa.getColumnModel().getColumn(3).setPreferredWidth(200);
    }
     public void showComboboxNameCustommer(DefaultComboBoxModel defaultComboBoxModel){
        namecustomer.setModel(defaultComboBoxModel);
    }
     public void showComboboxNameStaff(DefaultComboBoxModel defaultComboBoxModel){
        namestaff.setModel(defaultComboBoxModel);
     }
    public InvoiceView() {
        namecustomer = new JComboBox();
        namestaff = new JComboBox();
        tableqa = new JTable();
        controller = new InvoiceController(this);
//        controller.showInvoice();
        InvoiceController.myEventTable mEventTable = controller.new myEventTable();
        tableqa.getSelectionModel().addListSelectionListener(mEventTable);
        
        setBorder(new EmptyBorder(8, 8, 8, 8));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        Input1 input = new Input1();
        input.setBorder(new CompoundBorder(new TitledBorder("Nhập thông tin hoá đơn"), new EmptyBorder(4, 4, 4, 4)));
        add(input, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        Table1 table = new Table1();
        table.setBorder(new CompoundBorder(new TitledBorder("Bảng thông tin hoá đơn"), new EmptyBorder(4, 4, 4, 4)));
        add(table, gbc);

    }

    class Input1 extends JPanel {

        public Input1() {
            this.setPreferredSize(new Dimension(600,200));
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            add(new JLabel("Mã hoá đơn"), gbc);

            gbc.gridx += 2;
            add(new JLabel("Tên khách hàng"), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Ngày mua hàng"), gbc);

            gbc.gridx += 2;
            add(new JLabel("Nhân viên lập hoá đơn"), gbc);
            
            gbc.gridx = 1;
            gbc.gridy = 0;
//      giãn chiều ngang đối tượng sao cho khít
            gbc.fill = GridBagConstraints.VERTICAL;
//      neo vị trí đặt đối tượng theo một vị trí nào đó trong ô đặt
            gbc.anchor = GridBagConstraints.WEST;
//      khoảng cách lớn ra giữa các đối tượng 
            gbc.weightx = 0.5;
            codeinvoice = new JTextField(10);
            codeinvoice.setEnabled(false);
            add(codeinvoice, gbc);

            gbc.gridx += 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            namecustomer.setBackground(Color.white);
            namecustomer.setSelectedItem(null);
            add(namecustomer, gbc);

            gbc.gridx = 1;
            gbc.gridy++;
            gbc.fill = GridBagConstraints.VERTICAL;
            purchasedate = new JDateChooser();
            add(purchasedate,gbc);

            gbc.gridx += 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            namestaff.setBackground(Color.white);
            namestaff.setSelectedItem(null);
            add(namestaff,gbc);
        }
    }

    class Table1 extends JPanel {

        public Table1() {
           
            tableqa.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
            jScrollPaneqaTable = new JScrollPane();
            jScrollPaneqaTable.setViewportView(tableqa);
            jScrollPaneqaTable.setPreferredSize(new Dimension(600, 300));

            ImageIcon icon1 = FormView.createImageIcon("images/delete_30px.png");
            ImageIcon icon2 = FormView.createImageIcon("images/add_64px.png");
            ImageIcon icon3 = FormView.createImageIcon("images/edit_24px.png");

            JPanel detainJPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            detainJPanel.add(jScrollPaneqaTable, gbc);
            JPanel serach = new JPanel(new FlowLayout());

            serach.add(new JLabel("Tìm kiếm"));

            textFieldSearch = new JTextField(15);
            textFieldSearch.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(77, 77, 77)));
            textFieldSearch.getDocument().addDocumentListener(controller);
            serach.add(textFieldSearch);

            btnAdd = new JButton("Thêm",icon2);
            btnAdd.addActionListener(controller);
            btnEdit = new JButton("Sửa",icon3);
            btnEdit.addActionListener(controller);
            btnDelet = new JButton("Xoá",icon1);
            btnDelet.addActionListener(controller);
            btnDetails = new JButton("Chi tiết");
            btnDetails.addActionListener(controller);
            btnDetails.setPreferredSize(new Dimension(40,36));
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
            
            gbc.gridy++;
            buttonsPane.add(btnDetails, gbc);

            setLayout(new BorderLayout());
            add(detainJPanel);
            add(serach, BorderLayout.NORTH);
            add(buttonsPane, BorderLayout.EAST);

        }
    }
}