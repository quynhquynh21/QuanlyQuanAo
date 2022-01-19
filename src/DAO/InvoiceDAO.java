/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.SQL.closeConnec;
import static DAO.SQL.closeResultSet;
import static DAO.SQL.closeStatement;
import static DAO.SQL.getConnection;
import Entity.Invoice;
import Entity.Staff;
import Entity.Custommer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author BUIDUCQUYNH
 */
public class InvoiceDAO {

    private String selectallcustommer = "SELECT * FROM quanao.custommer";
    private String selectallstaff = "SELECT * FROM quanao.staff";
    private String sqlInsert = "Insert into quanao.invoice (codecustommer,purchasedate,codestaff) values (?,?,?)";
    private String sqlUpdate = "UPDATE quanao.invoice SET codecustommer = ?, purchasedate = ?, codestaff =? WHERE (codeinvoice = ?)";
    private String sqlDelete = "delete from quanao.invoice where codeinvoice=?";
    private String sqlDeleteinvoice = "delete from quanao.invoicedetails where codeinvoice=?";
    
    private String selectallqa = "SELECT * FROM\n"
            + "	quanao.invoice \n"
            + "INNER JOIN quanao.staff ON quanao.invoice.codestaff = quanao.staff.codestaff\n"
            + "INNER JOIN quanao.custommer ON quanao.invoice.codecustommer = quanao.custommer.codecustommer\n"
            + "WHERE quanao.invoice.codecustommer = quanao.custommer.codecustommer AND quanao.invoice.codestaff = quanao.staff.codestaff ORDER BY codeinvoice";

    public InvoiceDAO() {
    }

    public ArrayList<Invoice> getAllInvoice() {
        Connection con = getConnection();
        Statement st = null;
        ArrayList<Invoice> listAll = new ArrayList<Invoice>();
        ResultSet rs = null;
        if (con != null) {
            try {
                st = con.createStatement();
                rs = st.executeQuery(selectallqa);
                while (rs.next()) {
                    String codeinvoice = rs.getString(5) + rs.getInt(1);
                    Invoice invoice = new Invoice(codeinvoice, rs.getInt(2), rs.getString("namecustommer"), rs.getString(3), rs.getInt(4), rs.getString("namestaff"));
                    listAll.add(invoice);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResultSet(rs);
                closeStatement(st);
                closeConnec(con);
            }
        }
        return listAll;
    }

    public DefaultComboBoxModel getCBboxNameCustommer() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        Connection con = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pr = con.prepareStatement(selectallcustommer);
            rs = pr.executeQuery();
            while (rs.next()) {
                model.addElement(new Custommer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
        } finally {
            closeResultSet(rs);
            closeStatement(pr);
            closeConnec(con);
        }
        return model;
    }

    public DefaultComboBoxModel getCBboxNameStaff() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        Connection con = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pr = con.prepareStatement(selectallstaff);
            rs = pr.executeQuery();
            while (rs.next()) {
                model.addElement(new Staff(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
        } finally {
            closeResultSet(rs);
            closeStatement(pr);
            closeConnec(con);
        }
        return model;
    }

    public void insert(Invoice invoice) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        if (con != null) {
            try {
                pr = con.prepareStatement(sqlInsert);
                pr.setInt(1, invoice.getCodecustomer());
                pr.setString(2, invoice.getPurchasedate());
                pr.setInt(3, invoice.getCodestaff());
                pr.executeUpdate();
                pr.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnec(con);
            }
        }
    }

    public void update(Invoice invoice) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        try {
            pr = con.prepareStatement(sqlUpdate);
            pr.setInt(1, invoice.getCodecustomer());
            pr.setString(2, invoice.getPurchasedate());
            pr.setInt(3, invoice.getCodestaff());
            pr.setString(4, invoice.getCodeinvoice().replaceAll("[^\\d.]", ""));
            System.out.println(pr.toString());
            pr.executeUpdate();
            pr.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            closeConnec(con);
        }
    }

    public boolean delete(Invoice invoice) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        
        int k = 0;
        try {
            pr = con.prepareStatement(sqlDeleteinvoice);
            pr.setString(1, invoice.getCodeinvoice());
            k = pr.executeUpdate();
            pr.close();
            
            pr = con.prepareStatement(sqlDelete);
            pr.setString(1, invoice.getCodeinvoice());
            k = pr.executeUpdate();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnec(con);
        }
        if (k > 0) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        InvoiceDAO a = new InvoiceDAO();
        System.out.println(a.getCBboxNameStaff());
    }
}
