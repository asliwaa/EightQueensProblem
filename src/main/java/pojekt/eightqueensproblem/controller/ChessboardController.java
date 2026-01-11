package pojekt.eightqueensproblem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojekt.eightqueensproblem.model.ChessboardModel;
import pojekt.eightqueensproblem.model.InvalidPositionException;

import java.io.IOException;

/**
 * Main controller servlet for the Eight Queens Puzzle application.
 * Handles user inputs, game logic execution, and updates the shared model state.
 * Supports cookies to remember the last valid move.
 *
 * @author Adam
 * @version 5.0
 */
@WebServlet(name = "ChessboardController", urlPatterns = {"/Input"})
public class ChessboardController extends HttpServlet {

    /**
     * Initializes the servlet and the shared application model.
     * This method is called once during the servlet lifecycle.
     *
     * @throws ServletException if an error occurs during initialization.
     */
    @Override
    public void init() throws ServletException {
        ChessboardModel model = new ChessboardModel();
        getServletContext().setAttribute("chessboardModel", model);
        getServletContext().setAttribute("queenCounter", 1);
        getServletContext().setAttribute("gameFinished", false);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * Handles game reset, move validation, queen placement, and game completion checks.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ChessboardModel model = (ChessboardModel) getServletContext().getAttribute("chessboardModel");
        
        synchronized (model) {
            String action = request.getParameter("action");
            String position = request.getParameter("inputPos");
            
            request.setAttribute("errorMessage", null);

            // --- RESET LOGIC ---
            if ("reset".equals(action)) {
                model.clearBoard();
                model.addHistoryLog("=== GAME RESET ===");
                
                getServletContext().setAttribute("queenCounter", 1);
                getServletContext().setAttribute("gameFinished", false);
                getServletContext().setAttribute("gameMessage", null);
                getServletContext().setAttribute("messageColor", null);
                
                // Optional: clear cookie
                Cookie killCookie = new Cookie("eqp_last_move", "");
                killCookie.setMaxAge(0);
                response.addCookie(killCookie);
                
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            boolean isFinished = (Boolean) getServletContext().getAttribute("gameFinished");

            // --- MOVE LOGIC ---
            if (!isFinished) {
                if (position == null || position.trim().isEmpty()) {
                    request.setAttribute("errorMessage", "No position provided! Enter a value (e.g., A1).");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    return; 
                }

                try {
                    int counter = (Integer) getServletContext().getAttribute("queenCounter");

                    // 1. Validation
                    model.isValidPlacement(position);
                    
                    // 2. Action
                    model.placeQueen(position);
                    model.addHistoryLog("Placed queen no. " + counter + " at: " + position.toUpperCase());

                    // 3. Cookie Management
                    Cookie moveCookie = new Cookie("eqp_last_move", position.toUpperCase());
                    moveCookie.setMaxAge(3600); // 1 hour
                    response.addCookie(moveCookie);

                    // 4. Update State
                    counter++;
                    getServletContext().setAttribute("queenCounter", counter);

                    // 5. Check End Condition
                    if (counter > 8) {
                        getServletContext().setAttribute("gameFinished", true);
                        if (model.isSolutionValid()) {
                            getServletContext().setAttribute("gameMessage", "SUCCESS! Solution is valid.");
                            getServletContext().setAttribute("messageColor", "green");
                            model.addHistoryLog("GAME OVER: Success");
                        } else {
                            getServletContext().setAttribute("gameMessage", "FAILURE. Queens are attacking each other.");
                            getServletContext().setAttribute("messageColor", "red");
                            model.addHistoryLog("GAME OVER: Failure");
                        }
                    }

                } catch (InvalidPositionException ex) {
                    String err = "Error (" + position + "): " + ex.getMessage();
                    request.setAttribute("errorMessage", err);
                    model.addHistoryLog("User Error: " + err);
                }
            }
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
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