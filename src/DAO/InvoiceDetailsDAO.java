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
import Entity.InvoiceDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import Entity.Import;

/**
 *
 * @author BUIDUCQUYNH
 */
public class InvoiceDetailsDAO {

    private String selectall = "SELECT * FROM\n"
            + "	quanao.invoicedetails\n"
            + "INNER JOIN quanao.import ON quanao.invoicedetails.codeclothes = quanao.import.codeclothes\n"
            + "WHERE quanao.import.codeclothes = quanao.invoicedetails.codeclothes ORDER BY codeinvoice ";
    private String selectallqa = "SELECT * FROM quanao.import";
    private String sqlInsert = "Insert into quanao.invoicedetails (codeinvoice,purchasedate,codestaff) values (?,?,?)";
    private String sqlUpdate = "UPDATE quanao.invoicedetails SET codecustommer = ?, purchasedate = ?, codestaff =? WHERE (codeinvoice = ?)";
    private String sqlDelete = "delete from quanao.invoicedetails where codeinvoice=?";

    public InvoiceDetailsDAO() {

    }

    public ArrayList<InvoiceDetails> getAllInvoiceDetail() {
        Connection con = getConnection();
        Statement st = null;
        ArrayList<InvoiceDetails> listAll = new ArrayList<InvoiceDetails>();
        ResultSet rs = null;
         float toal =0;
        if (con != null) {
            try {
                st = con.createStatement();
                rs = st.executeQuery(selectall);
               
                while (rs.next()) {
                    float sum = rs.getInt(3) * rs.getFloat(4);
                    InvoiceDetails invoiceDetails = new InvoiceDetails(rs.getInt(1), rs.getInt(2), rs.getString("nameclothes"), rs.getInt(3), rs.getFloat(4), sum);
                    toal = sum +toal;
                    listAll.add(invoiceDetails);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResultSet(rs);
                closeStatement(st);
                closeConnec(con);
            }
            System.out.println(toal);
        }
        return listAll;
    }

    public DefaultComboBoxModel getCBboxNameclothes() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        Connection con = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pr = con.prepareStatement(selectallqa);
            rs = pr.executeQuery();
            while (rs.next()) {
                model.addElement(new Import(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8)));
            }
        } catch (SQLException e) {
        } finally {
            closeResultSet(rs);
            closeStatement(pr);
            closeConnec(con);
        }
        return model;
    }

    public String getToalPrice() {
        Connection con = getConnection();
        Statement st = null;
        ArrayList<InvoiceDetails> listAll = new ArrayList<InvoiceDetails>();
        ResultSet rs = null;
        float toal =0;
        if (con != null) {
            try {
                st = con.createStatement();
                rs = st.executeQuery(selectall);
               
                while (rs.next()) {
                    float sum = rs.getInt(3) * rs.getFloat(4);
                    InvoiceDetails invoiceDetails = new InvoiceDetails(rs.getInt(1), rs.getInt(2), rs.getString("nameclothes"), rs.getInt(3), rs.getFloat(4), sum);
                    toal = sum +toal;
                    listAll.add(invoiceDetails);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResultSet(rs);
                closeStatement(st);
                closeConnec(con);
            }
        }
        String sumtotal = String.valueOf(toal); 
        return sumtotal;
    }
    public static void main(String[] args) {
        InvoiceDetailsDAO a = new InvoiceDetailsDAO();
        System.out.println(a.getToalPrice());
    }
}
