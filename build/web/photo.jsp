<%-- 
    Document   : photo
    Created on : Oct 12, 2016, 9:01:56 PM
    Author     : Rodrigo
--%>

<%@page import="beans.PhotoAlbum" %>
<%@page contentType="image/jpeg" %>
<%java.io.OutputStream binaryOut = response.getOutputStream();
    String indexString = request.getParameter("photo");
    int index = (new Integer(indexString.trim())).intValue();
    PhotoAlbum photo = PhotoAlbum.getPhotoAlbum(session);
    byte[] bytes = photo.getPhotoData(index);
    
    for (int i = 0; i < bytes.length; i++) 
    {
        binaryOut.write(bytes[i]);
    }
%>
