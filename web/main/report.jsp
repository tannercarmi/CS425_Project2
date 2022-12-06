<%-- 
    Document   : report
    Created on : Dec 1, 2022, 3:03:11 AM
    Author     : tanto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="edu.jsu.mcis.project2.dao.*"%>

<jsp:useBean id="daoFactory" class="edu.jsu.mcis.project2.dao.DAOFactory" scope="application"/>

<%
    SearchDAO dao = daoFactory.getSearchDAO();
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Schedule Report</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="scripts/jquery-3.6.1.min.js"></script>
        <script src="scripts/Project2.js"></script>
    </head>
    <body>
        <p>
            <a href="<%= request.getContextPath() %>/main/landing.jsp">Back to Home</a>
        </p>
        
        <form id="reportform" name="reportform" method="GET" action="report" target="_blank">
            
            <fieldset>
                
                <legend>View Schedule Report</legend>
        
                <p>
                    <label for="termid"><strong>View Schedule Report by Term:</strong><br /></label>
                    <%= dao.getTermListAsHTML() %>
                </p>

                <input type="submit" value="Submit">
                <input type="reset" value="Reset">
                
            </fieldset>
        
        </form>
        <p>
            <input type="button" value="Logout" onclick="window.open('<%= request.getContextPath() %>/main/logout.jsp', '_self', false);" />
        </p>
    </body>
</html>
