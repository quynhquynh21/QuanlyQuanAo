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
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author BUIDUCQUYNH
 */
public class InvoiceDetailsDAO {

    private String selectall = "SELECT * FROM quanao.invoicedetails\n"
            + "INNER JOIN quanao.import ON quanao.invoicedetails.codeclothes = quanao.import.codeclothes\n"
            + "WHERE quanao.invoicedetails.codeinvoice=? AND quanao.import.codeclothes = quanao.invoicedetails.codeclothes ";
    private String selectallqa = "SELECT * FROM quanao.import";
    private String sqlInsert = "Insert into quanao.invoicedetails (codeinvoice,codeclothes,countbuy) values (?,?,?)";
    private String sqlUpdate = "UPDATE quanao.invoicedetails SET  countbuy = ? WHERE (codeinvoice = ? AND codeclothes=?)";
    private String sqlDelete = "delete from quanao.invoicedetails where codeinvoice=? AND codeclothes =? ";
    private String check_InvoiceDetails = "SELECT * FROM quanao.invoicedetails"
            + " WHERE  quanao.invoicedetails.codeinvoice = ? AND quanao.invoicedetails.codeclothes =? ";
    private String check_invoice = "SELECT * FROM quanao.invoice,quanao.import,quanao.invoice"
            + " WHERE  quanao.staff.codestaff = quanao.import.codestaff AND quanao.staff.codestaff =? "
            + "OR quanao.staff.codestaff = quanao.invoice.codestaff AND quanao.staff.codestaff =? ";
    private String getcount = "SELECT countclothes FROM quanao.import where quanao.import.codeclothes = ?";
    private String updatcountclothes = "UPDATE quanao.import SET  countclothes = countclothes -? WHERE (codeclothes=?)";
    private String selectall2 = "SELECT countbuy FROM quanao.invoicedetails WHERE (codeinvoice = ? AND codeclothes=?)";

    public InvoiceDetailsDAO() {

    }

    public ArrayList<InvoiceDetails> getAllInvoiceDetail(String id) {
        Connection con = null;
        PreparedStatement stmt = null;
        ArrayList<InvoiceDetails> listAll = new ArrayList<>();
        ResultSet rs = null;
        float toal = 0;
        try {
            con = getConnection();
            stmt = con.prepareStatement(selectall);
            stmt.setString(1, id);

            rs = stmt.executeQuery();
            while (rs.next()) {
                float sum = rs.getInt(3) * rs.getFloat("priceclothes");
                toal = sum + toal;
                InvoiceDetails invoiceDetails = new InvoiceDetails(rs.getString(1), rs.getInt(2), rs.getString("nameclothes"), rs.getInt(3), rs.getFloat("priceclothes"), sum);
                listAll.add(invoiceDetails);
            }
        } catch (SQLException e) {
            e.getMessage();
        } finally {
            closeStatement(stmt);
            closeResultSet(rs);
            closeConnec(con);
        }
        return listAll;
    }

    public int getCountclothes(String id, int code_clothes) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int sum = 0;
        try {
            con = getConnection();
            stmt = con.prepareStatement(selectall2);
            stmt.setString(1, id);
            stmt.setInt(2, code_clothes);

            rs = stmt.executeQuery();
            while (rs.next()) {
                sum += rs.getInt(1);
            }
        } catch (SQLException e) {
            e.getMessage();
        } finally {
            closeStatement(stmt);
            closeResultSet(rs);
            closeConnec(con);
        }
        return sum;
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
                model.addElement(new Import(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8)));
            }
        } catch (SQLException e) {
        } finally {
            closeResultSet(rs);
            closeStatement(pr);
            closeConnec(con);
        }
        return model;
    }

    public void insert(InvoiceDetails invoiceDetails) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        if (con != null) {
            try {
                pr = con.prepareStatement(sqlInsert);
                pr.setString(1, invoiceDetails.getCodeinvoice());
                pr.setInt(2, invoiceDetails.getCodeclothes());
                pr.setInt(3, invoiceDetails.getCountbuy());
                pr.executeUpdate();
                pr.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnec(con);
            }
        }
    }

    public void update(InvoiceDetails invoiceDetails) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        try {
            pr = con.prepareStatement(sqlUpdate);
            pr.setInt(1, invoiceDetails.getCountbuy());
            pr.setString(2, invoiceDetails.getCodeinvoice());
            pr.setInt(3, invoiceDetails.getCodeclothes());
            pr.executeUpdate();
            pr.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            closeConnec(con);
        }
    }

    public boolean delete(InvoiceDetails invoiceDetails) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        int k = 0;
        try {
            pr = con.prepareStatement(sqlDelete);
            pr.setString(1, invoiceDetails.getCodeinvoice());
            pr.setInt(2, invoiceDetails.getCodeclothes());
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

    public Boolean CheckAdd(InvoiceDetails invoiceDetails) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = getConnection();
            stmt = con.prepareStatement(check_InvoiceDetails);
            stmt.setString(1, invoiceDetails.getCodeinvoice());
            stmt.setInt(2, invoiceDetails.getCodeclothes());
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

    public Boolean CheckDelete(String check_id) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = getConnection();
            stmt = con.prepareStatement(check_invoice);
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

    public String getToalPrice(String id) {
        Connection con = null;
        PreparedStatement stmt = null;
        ArrayList<InvoiceDetails> listAll = new ArrayList<InvoiceDetails>();
        ResultSet rs = null;
        float toal = 0;
        try {
            con = getConnection();
            stmt = con.prepareStatement(selectall);
            stmt.setString(1, id);

            rs = stmt.executeQuery();
            while (rs.next()) {
                float sum = rs.getInt(3) * rs.getFloat("priceclothes");
                toal = sum + toal;
                InvoiceDetails invoiceDetails = new InvoiceDetails(rs.getString(1), rs.getInt(2), rs.getString("nameclothes"), rs.getInt(3), rs.getFloat("priceclothes"), sum);
                listAll.add(invoiceDetails);
            }
        } catch (SQLException e) {
        } finally {
            closeConnec(con);
        }
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(toal);
       
        return str1;
    }

    public int getcountclothes(int id) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int getcountclothes = 0;
        try {
            con = getConnection();
            stmt = con.prepareStatement(getcount);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();
            if (rs.next()) {
                getcountclothes += rs.getInt(1);

            }
        } catch (SQLException e) {
        } finally {
            closeConnec(con);
        }
        return getcountclothes;

    }

    public int getsumCountclothes(int id, int count) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int getcountclothes = 0;
        try {
            con = getConnection();
            stmt = con.prepareStatement(getcount);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();
            if (rs.next()) {
                getcountclothes = count + rs.getInt(1);
//                System.out.println(getcountclothes);
            }
        } catch (SQLException e) {
        } finally {
            closeConnec(con);
        }
        return getcountclothes;

    }

    public void updatecoutnclothes(int id, int count) {
        Connection con = getConnection();
        PreparedStatement pr = null;
        try {
            pr = con.prepareStatement(updatcountclothes);
            pr.setInt(1, count);
            pr.setInt(2, id);
            pr.executeUpdate();
            pr.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            closeConnec(con);
        }
    }

    public static void main(String[] args) {
        InvoiceDetailsDAO invoiceDetailsDAO = new InvoiceDetailsDAO();
        System.out.println(invoiceDetailsDAO.getAllInvoiceDetail("24"));
    }
}
