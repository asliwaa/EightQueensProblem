package pojekt.eightqueensproblem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojekt.eightqueensproblem.model.ChessboardModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller servlet responsible for displaying the game history.
 * Fetches the shared model and passes the history list to the view.
 *
 * @author Adam
 * @version 5.0
 */
@WebServlet(name = "HistoryController", urlPatterns = {"/History"})
public class HistoryController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * Retrieves the history log from the shared model.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ChessboardModel model = (ChessboardModel) getServletContext().getAttribute("chessboardModel");
        
        List<String> historyList;
        
        if (model != null) {
            historyList = model.getHistory();
        } else {
            historyList = new ArrayList<>();
            historyList.add("Model has not been initialized yet.");
        }
        
        request.setAttribute("historyList", historyList);
        request.getRequestDispatcher("history.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}