/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import sun.font.EAttribute;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import Model.ImporModel;
import DAO.ImportDAO;
import Controller.ImportController;
import Controller.InvoiceController;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author BUIDUCQUYNH
 */

public class ImportView extends JPanel {

    public JTable tableqa;
    public JButton btnAdd, btnDelet, btnEdit;
    public JScrollPane jScrollPaneqaTable;
    public JTextField codeclothes, nameclothes, priceclothes,countclothes,unit, textFieldSearch;
    public JComboBox importer, supplier;
    public JDateChooser dateimport;
    
    ImportController controller;
    public void showListImport(ImporModel imporModel) {
        tableqa.setModel(imporModel);
        tableqa.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
        tableqa.getColumnModel().getColumn(0).setPreferredWidth(110);
        tableqa.getColumnModel().getColumn(1).setPreferredWidth(120);
        tableqa.getColumnModel().getColumn(2).setPreferredWidth(120);
        tableqa.getColumnModel().getColumn(3).setPreferredWidth(120);
        tableqa.getColumnModel().getColumn(4).setPreferredWidth(120);
        tableqa.getColumnModel().getColumn(5).setPreferredWidth(120);
        tableqa.getColumnModel().getColumn(6).setPreferredWidth(120);
        tableqa.getColumnModel().getColumn(7).setPreferredWidth(120);
    }
    public void showComboboxNameSupplier(DefaultComboBoxModel defaultComboBoxModel){
        supplier.setModel(defaultComboBoxModel);
    }
    public void showComboboxNameStaff(DefaultComboBoxModel defaultComboBoxModel){
        importer.setModel(defaultComboBoxModel);
    }
    public ImportView() {
        tableqa = new JTable();
        supplier = new JComboBox();
        importer = new JComboBox();
        controller = new ImportController(this);
        controller.showImportView();
        ImportController.myEventTable mEventTable = controller.new myEventTable();
        tableqa.getSelectionModel().addListSelectionListener(mEventTable);
        
        setBorder(new EmptyBorder(8, 8, 8, 8));
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        Input input = new Input();
        input.setBorder(new CompoundBorder(new TitledBorder("Nhập thông tin"), new EmptyBorder(4, 4, 4, 4)));
        
        add(input, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        Table table = new Table();
        table.setBorder(new CompoundBorder(new TitledBorder("Bảng thông tin"), new EmptyBorder(4, 4, 4, 4)));
        add(table, gbc);

        this.setSize(900, 300);
        this.setVisible(true);

    }

    class Input extends JPanel {

        public Input() {
            this.setPreferredSize(new Dimension(600,250));
            String[] item = {"Việt Nam", "Adidas", "Thành Công"};
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            add(new JLabel("Mã quần áo"), gbc);

            gbc.gridx += 2;
            add(new JLabel("Tên quần áo"), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Giá"), gbc);

            gbc.gridx += 2;
            add(new JLabel("Số lượng"), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Đơn vị tính"), gbc);

            gbc.gridx += 2;
            add(new JLabel("Ngày nhập"), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Người nhập"), gbc);

            gbc.gridx += 2;
            add(new JLabel("Nhà cung cấp"), gbc);
            
            gbc.gridx = 1;
            gbc.gridy = 0;
//      giãn chiều ngang đối tượng sao cho khít
            gbc.fill = GridBagConstraints.HORIZONTAL;
//      neo vị trí đặt đối tượng theo một vị trí nào đó trong ô đặt
            gbc.anchor = GridBagConstraints.WEST;
//      khoảng cách lớn ra giữa các đối tượng 
            gbc.weightx = 0.5;
            codeclothes= new JTextField(10);
            codeclothes.setEnabled(false);
            add(codeclothes, gbc);

            gbc.gridx += 2;
            nameclothes = new JTextField(10);
            add(nameclothes, gbc);

            gbc.gridx = 1;
            priceclothes = new JTextField(10);
            gbc.gridy++;
            add(priceclothes, gbc);

            gbc.gridx += 2;
            countclothes = new JTextField(10);
            add(countclothes, gbc);

            gbc.gridx = 1;
            gbc.gridy++;
            unit = new JTextField(10);
            add(unit, gbc);

            gbc.gridx += 2;
            dateimport = new JDateChooser();
            add(dateimport, gbc);

            gbc.gridx = 1;
            gbc.gridy++;
            importer.setBackground(Color.white);
            importer.setSelectedItem(null);
            add(importer, gbc);
            
            gbc.gridx += 2;
            supplier.setBackground(Color.white);
            supplier.setSelectedItem(null);
            add(supplier, gbc);
            
        }

    }

    class Table extends JPanel {

        public Table() {
            
            tableqa.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
            jScrollPaneqaTable = new JScrollPane();
            jScrollPaneqaTable.setViewportView(tableqa);
            jScrollPaneqaTable.setPreferredSize(new Dimension(700, 300));
          

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
