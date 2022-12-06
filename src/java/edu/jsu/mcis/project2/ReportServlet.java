/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.project2;

import edu.jsu.mcis.project2.dao.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import org.json.simple.*;

/**
 *
 * @author tanto
 */
public class ReportServlet extends HttpServlet {

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
        
        // Acquire DAOFactory
        DAOFactory daoFactory = null;

        ServletContext context = request.getServletContext();

        if (context.getAttribute("daoFactory") == null) {
            System.err.println("*** Creating new DAOFactory ...");
            daoFactory = new DAOFactory();
            context.setAttribute("daoFactory", daoFactory);
        }
        else {
            daoFactory = (DAOFactory) context.getAttribute("daoFactory");
        }

        // Open Report File and Set Response Type
        InputStream reportFile = this.getClass().getResourceAsStream("resources" + File.separator + "ScheduleReport.jasper");

        response.setContentType("application/pdf");

        // Open ServletOutputStream
        try ( ServletOutputStream out = response.getOutputStream()) {

            // Acquire Registration DAO
            RegistrationDAO dao = daoFactory.getRegistrationDAO();

            // Get Student/Term IDs
            int studentid = dao.getStudentId(request.getRemoteUser());
            int termid = Integer.parseInt(request.getParameter("term"));

            // Get Schedule Data and Convert to JasperReports Data Source
            JSONObject json = (JSONObject) JSONValue.parse(dao.find(termid, studentid));
            JSONArray schedule = (JSONArray) json.get("sections");

            JRDataSource jasperDataSource = new JRMapArrayDataSource(schedule.toArray());

            // Initialize Report Parameter Collection
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/dd/yyyy h:mm:ss a");
            String now = dtf.format(LocalDateTime.now());
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("subtitle", "Created by JasperReports on: " + now);

            // Run Report and Get PDF Data
            byte[] pdf = new byte[0];

            if (reportFile != null) {
                pdf = JasperRunManager.runReportToPdf(reportFile, parameters, jasperDataSource);
                reportFile.close();
            }
            else {
                System.err.println("\n\nERROR: Report file missing or invalid!\n");
            }

            // Set Response Length and Send PDF Data to Client
            if (pdf.length > 0) {
                response.setContentLength(pdf.length);
                out.write(pdf);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
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
        doGet(request, response);
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
