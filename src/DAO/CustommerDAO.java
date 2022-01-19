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
import Entity.Custommer;
import static DAO.SQL.*;
import java.sql.PreparedStatement;

/**
 *
 * @author BUIDUCQUYNH
 */
public class CustommerDAO {

    private String selectall = "SELECT * FROM quanao.custommer ORDER BY codecustommer";
    private String sqlInsert = "Insert into quanao.custommer (namecustommer,numbercustommer,addresscustommer) values (?,?,?)";
    private String sqlUpdate = "UPDATE quanao.custommer SET namecustommer = ?, numbercustommer = ?, addresscustommer =? WHERE (codecustommer = ?)";
    private String sqlDelete = "delete from quanao.custommer where codecustommer=?";
    private String check_add = "SELECT * FROM quanao.custommer"
            + " WHERE  quanao.custommer.numbercustommer = ? ";
    private String check_custommer = "SELECT * FROM quanao.custommer,quanao.invoice"
            + " WHERE  quanao.custommer.codecustommer = quanao.invoice.codecustommer AND quanao.custommer.codecustommer =? ";
    public CustommerDAO() {
    }

    public ArrayList<Custommer> getAllCustommers() {
        Connection con = getConnection();
        Statement st = null;
        ArrayList<Custommer> listAll = new ArrayList<Custommer>();
        ResultSet rs = null;
        if (con != null) {
            try {
                st = con.createStatement();
                rs = st.executeQuery(selectall);
                
                while (rs.next()) {
                    String codeCustommer = rs.getString(5) + rs.getInt(1);
                    Custommer custommer = new Custommer(codeCustommer, rs.getString(2), rs.getString(3), rs.getString(4));
                    listAll.add(custommer);
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

    public void insert(Custommer custommer) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        if (con != null) {
            try {
                pr = con.prepareStatement(sqlInsert);
                pr.setString(1, custommer.getNamecustomer());
                pr.setString(2, custommer.getNumbercustomer());
                pr.setString(3, custommer.getAddresscustomer());
                pr.executeUpdate();
                pr.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnec(con);
            }
        }
    }

    public void updateCustommer(Custommer custommer) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        try {
            pr = con.prepareStatement(sqlUpdate);
            pr.setString(1, custommer.getNamecustomer());
            pr.setString(2, custommer.getNumbercustomer());
            pr.setString(3, custommer.getAddresscustomer());
            pr.setString(4, custommer.getCodecustommer().replaceAll("[^\\d.]", ""));
            pr.executeUpdate();
            pr.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            closeConnec(con);
        }
    }

    public boolean deleteCustommer(Custommer custommer) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        int k = 0;
        try {
            pr = con.prepareStatement(sqlDelete);
            pr.setString(1, custommer.getCodecustommer().replaceAll("[^\\d.]", ""));
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
            stmt = con.prepareStatement(check_custommer);
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
