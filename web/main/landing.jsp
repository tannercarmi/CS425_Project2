<%-- 
    Document   : landing
    Created on : Dec 1, 2022, 3:02:06 AM
    Author     : tanto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Class System Main Page</title>
    </head>
    <body>
        <h1>JSU Class System</h1>
        <a href="<%= request.getContextPath() %>/main/term.jsp">Search for Classes</a>
        <br>
        <a href="<%= request.getContextPath() %>/main/term1.jsp">Register for Classes</a>
        <br>
        <a href="<%= request.getContextPath() %>/main/schedule.jsp">Look at Current Schedule</a>
        <br>
        <a href="<%= request.getContextPath() %>/main/drop.jsp">Drop a Course</a>
        <br>
        <a href="<%= request.getContextPath() %>/main/report.jsp">Generate Schedule as PDF</a>
        <p>
            <input type="button" value="Logout" onclick="window.open('<%= request.getContextPath() %>/main/logout.jsp', '_self', false);" />
        </p>
    </body>
</html>
