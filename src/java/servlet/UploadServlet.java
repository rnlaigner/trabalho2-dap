/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import beans.PhotoAlbum;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Photo;
import test.ConnectionFactory;

/**
 *
 * @author Rodrigo
 */
@WebServlet(name = "UploadServlet", urlPatterns = {"/UploadServlet"})
@MultipartConfig()
public class UploadServlet extends HttpServlet 
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession(true);
        PhotoAlbum pa = PhotoAlbum.getPhotoAlbum(session);
        if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) 
        {
            try 
            {
                this.uploadPhoto(request, pa);
            } 
            catch (SQLException ex) 
            {
                RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");      
                rd.forward(request, response);
            }
        } 
    
        RequestDispatcher rd = request.getRequestDispatcher("/album.jsp");      
        rd.forward(request, response);

    }
    
    private void uploadPhoto(HttpServletRequest request,
            PhotoAlbum pa)
            throws IOException, ServletException, SQLException 
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String filename = null;
        for (Part p : request.getParts()) 
        {
            this.copyBytes(p.getInputStream(), baos);
            filename = p.getSubmittedFileName();
        }
        
        String subtitle = request.getParameter("subtitle");
        String author = request.getParameter("author");
        String local = request.getParameter("local");
        
        Photo photo = new Photo(baos.toByteArray(),subtitle, author, local);

        Connection connection = ConnectionFactory.getConnection();
        System.out.println("Connected successfully");
        
        String SQL = "INSERT INTO ROOT.PHOTO (DATA, AUTHOR, SUBTITLE, LOCAL) VALUES (?,?,?,?)";

        PreparedStatement pst = connection.prepareStatement(SQL);
        
        //Blob blob = new javax.sql.rowset.serial.SerialBlob(photo.getData());
//        Blob blob = connection.createBlob();
//        blob.setBytes(1, photo.getData());
//
//        pst.setBlob(1, blob);
        byte[] bytes = photo.getData();
//        pst.setBinaryStream(1,new ByteArrayInputStream(bytes),bytes.length);
        
        pst.setBytes(1, bytes);
        pst.setString(2, photo.getAuthor());
        pst.setString(3, photo.getSubtitle());
        pst.setString(4, photo.getLocal());

        int rowsaffected = pst.executeUpdate();
        
        System.out.println(rowsaffected);
        
        pst.close();
        
        connection.close();

        System.out.println("SQL executed");
        
        pa.addPhoto(photo);
    }

    private void copyBytes(InputStream is, OutputStream os) throws IOException {
        int i;
        while ((i = is.read()) != -1) 
        {
            os.write(i);
        }
        is.close();
        os.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
