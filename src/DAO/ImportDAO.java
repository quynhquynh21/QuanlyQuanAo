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
import Entity.Suppllier;
import Entity.Import;
import Entity.Invoice;
import Entity.Staff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import Controller.ImportController;

/**
 *
 * @author BUIDUCQUYNH
 */
public class ImportDAO {

    private String selectasupplier = "SELECT * FROM quanao.supplier";
    private String selectastaff = "SELECT * FROM quanao.staff";
    private String sqlInsert = "Insert into quanao.import (nameclothes,priceclothes,countclothes,unit,dateimport,codesupplier,codestaff) values (?,?,?,?,?,?,?)";
    private String sqlUpdate = "UPDATE quanao.import SET nameclothes = ?, priceclothes = ?, "
            + "countclothes =?, unit=?, dateimport=?,codestaff=?, codesupplier=?    WHERE (codeclothes = ?)";
    private String sqlDelete = "delete from quanao.import where codeclothes=?";
    private String check_add = "SELECT * FROM quanao.import"
            + " WHERE  quanao.import.nameclothes = ? ";
    private String selectallqa = " SELECT * FROM\n"
            + "	quanao.import \n"
            + "INNER JOIN quanao.staff ON quanao.import.codestaff = quanao.staff.codestaff\n"
            + "INNER JOIN quanao.supplier ON quanao.import.codesupplier = quanao.supplier.codesupplier\n"
            + "WHERE quanao.import.codesupplier = quanao.supplier.codesupplier AND quanao.import.codestaff = quanao.staff.codestaff ORDER BY codeclothes";
    private String check_import = "SELECT * FROM quanao.import,quanao.invoicedetails"
            + " WHERE  quanao.import.codeclothes = quanao.invoicedetails.codeclothes AND quanao.import.codeclothes =? ";
            

    public ImportDAO() {

    }

    public ArrayList<Import> getAllImports() {
        Connection con = getConnection();
        Statement st = null;
        ArrayList<Import> listAll = new ArrayList<Import>();
        ResultSet rs = null;
        if (con != null) {
            try {
                st = con.createStatement();
                rs = st.executeQuery(selectallqa);
                while (rs.next()) {
                    String codeImport = rs.getString(9) + rs.getInt(1);
                    Import impor = new Import(codeImport, rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString("namestaff"), rs.getString("namesupplier"));
                    listAll.add(impor);
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

    public DefaultComboBoxModel getCBboxNameSupplier() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        Connection con = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pr = con.prepareStatement(selectasupplier);
            rs = pr.executeQuery();
            while (rs.next()) {
                String codeIp = rs.getString(5) + rs.getInt(1);
                model.addElement(new Suppllier(codeIp, rs.getString(2), rs.getString(3), rs.getString(4)));
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
            pr = con.prepareStatement(selectastaff);
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

    public void insert(Import ip) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        if (con != null) {
            try {
                pr = con.prepareStatement(sqlInsert);
                pr.setString(1, ip.getNameclothes());
                pr.setFloat(2, ip.getPriceclothes());
                pr.setInt(3, ip.getCountclothes());
                pr.setString(4, ip.getUnit());
                pr.setString(5, ip.getDateimport());
                pr.setInt(6, ip.getCodestaff());
                pr.setInt(7, ip.getCodesupplier());
                pr.executeUpdate();
                pr.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnec(con);
            }
        }
    }

    public void update(Import ip) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        try {
            pr = con.prepareStatement(sqlUpdate);
            pr.setString(1, ip.getNameclothes());
            pr.setFloat(2, ip.getPriceclothes());
            pr.setInt(3, ip.getCountclothes());
            pr.setString(4, ip.getUnit());
            pr.setString(5, ip.getDateimport());
            pr.setInt(6, ip.getCodestaff());
            pr.setInt(7, ip.getCodesupplier());
            pr.setString(8, ip.getCodeclothes().replaceAll("[^\\d.]", ""));
            System.out.println(pr.toString());
            pr.executeUpdate();
            pr.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            closeConnec(con);
        }
    }

    public boolean delete(Import ip) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        int k = 0;
        try {
            pr = con.prepareStatement(sqlDelete);
            pr.setString(1, ip.getCodeclothes().replaceAll("[^\\d.]", ""));
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

    public Boolean CheckDelete(String check_id) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = getConnection();
            stmt = con.prepareStatement(check_import);
            stmt.setString(1, check_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rs.close();
                stmt.close();
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            closeConnec(con);
        }
    }
    public Boolean CheckAdd(String check_id) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = getConnection();
            stmt = con.prepareStatement(check_add);
            stmt.setString(1, check_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rs.close();
                stmt.close();
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            closeConnec(con);
        }
    }
}
