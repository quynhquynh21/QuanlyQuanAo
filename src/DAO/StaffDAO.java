/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Entity.Staff;
import static DAO.SQL.*;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;

/**
 *
 * @author BUIDUCQUYNH
 */
public class StaffDAO {

    private String selectall = "SELECT * FROM quanao.staff ORDER BY codestaff ";
    private String sqlInsert = "Insert into quanao.staff (namestaff,numberstaff,addressstaff) values (?,?,?)";
    private String sqlUpdate = "UPDATE quanao.staff SET namestaff = ?, numberstaff = ?, addressstaff =? WHERE (codestaff = ?)";   
    private String sqlDelete = "delete from quanao.staff where codestaff=?";
     private String check_add = "SELECT * FROM quanao.staff"
            + " WHERE  quanao.staff.numberstaff = ? ";
    private String check_staff = "SELECT * FROM quanao.staff,quanao.import,quanao.invoice"
            + " WHERE  quanao.staff.codestaff = quanao.import.codestaff AND quanao.staff.codestaff =? "
            + "OR quanao.staff.codestaff = quanao.invoice.codestaff AND quanao.staff.codestaff =? ";
    
    public StaffDAO() {
    }

    public ArrayList<Staff> getAllStaff() {
        Connection con = getConnection();
        Statement st = null;
        ArrayList<Staff> listAll = new ArrayList<Staff>();
        ResultSet rs = null;
        if (con != null) {
            try {
                st = con.createStatement();
                rs = st.executeQuery(selectall);
                while (rs.next()) {
                    String codeStaff = rs.getString(5)+ rs.getInt(1);
                    Staff staff = new Staff(codeStaff, rs.getString(2), rs.getString(3), rs.getString(4));
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

    public void insert(Staff staff) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        if (con != null) {
            try {
                pr = con.prepareStatement(sqlInsert);
                pr.setString(1, staff.getNamestaff());
                pr.setString(2, staff.getNumberstaff());
                pr.setString(3, staff.getAddressstaff());
                pr.executeUpdate();
                pr.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnec(con);
            }
        }
    }
    
    public void updateStaff(Staff staff) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        try {
            pr = con.prepareStatement(sqlUpdate);
            pr.setString(1, staff.getNamestaff());
            pr.setString(2, staff.getNumberstaff());
            pr.setString(3, staff.getAddressstaff());
            pr.setString(4, staff.getCodestaff().replaceAll("[^\\d.]", ""));
            System.out.println(pr.toString());
            pr.executeUpdate();
            pr.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            closeConnec(con);
        }
    }
    
    public boolean deleteStaff(Staff staff){
        Connection con = getConnection();
        PreparedStatement pr = null;
        int k = 0;
        try {
            pr = con.prepareStatement(sqlDelete);
            pr.setString(1, staff.getCodestaff().replaceAll("[^\\d.]", ""));
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
            stmt = con.prepareStatement(check_staff);
            stmt.setString(1, check_id);
            stmt.setString(2, check_id);
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
