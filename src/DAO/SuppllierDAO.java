/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import Entity.Suppllier;
import static DAO.SQL.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 *
 * @author BUIDUCQUYNH
 */
public class SuppllierDAO {
     private String selectall = "SELECT * FROM quanao.supplier ORDER BY codesupplier ";
     private String sqlInsert = "Insert into quanao.supplier (namesupplier,numbersupplier,addresssupplier) values (?,?,?)";
     private String sqlUpdate = "UPDATE quanao.supplier SET namesupplier = ?, numbersupplier = ?, addresssupplier =? WHERE (codesupplier = ?)";
     private String sqlDelete = "delete from quanao.supplier where codesupplier=?";
     private String check_supplier = "SELECT * FROM quanao.supplier,quanao.import"
            + " WHERE  quanao.supplier.codesupplier = quanao.import.codesupplier AND quanao.supplier.codesupplier =? ";
    public SuppllierDAO() {
    }

    public ArrayList<Suppllier> getAllSuppllier() {
        Connection con = getConnection();
        Statement st = null;
        ArrayList<Suppllier> listAll = new ArrayList<Suppllier>();
        ResultSet rs = null;
        if (con != null) {
            try {
                st = con.createStatement();
                rs = st.executeQuery(selectall);
                while (rs.next()) {
                    Suppllier staff = new Suppllier(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                    listAll.add(staff);
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
    
   public void insert(Suppllier suppllier) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        if (con != null) {
            try {
                pr = con.prepareStatement(sqlInsert);
                pr.setString(1, suppllier.getNamesupplier());
                pr.setString(2, suppllier.getNumbersupplier());
                pr.setString(3, suppllier.getAddresssupplier());
                pr.executeUpdate();
                pr.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnec(con);
            }
        }
    }

    public void updateSupplier(Suppllier suppllier) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        try {
            pr = con.prepareStatement(sqlUpdate);
            pr.setString(1, suppllier.getNamesupplier());
            pr.setString(2, suppllier.getNumbersupplier());
            pr.setString(3, suppllier.getAddresssupplier());
            pr.setInt(4, suppllier.getCodesupplier());
            pr.executeUpdate();
            pr.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            closeConnec(con);
        }
    }

    public boolean deleteSupplier(Suppllier suppllier) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        int k = 0;
        try {
            pr = con.prepareStatement(sqlDelete);
            pr.setInt(1, suppllier.getCodesupplier());
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
    
    public Boolean CheckDelete(int check_id) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = getConnection();
            stmt = con.prepareStatement(check_supplier);
            stmt.setInt(1, check_id);
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
