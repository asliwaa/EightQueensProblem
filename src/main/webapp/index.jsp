<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="pojekt.eightqueensproblem.model.ChessboardModel"%>
<%@page import="pojekt.eightqueensproblem.model.SquareState"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html lang="pl">
    <head>
        <title>Eight Queens Puzzle - Cookies</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            body {
                display: flex;
                flex-direction: column;
                align-items: center;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f4f4f9;
                color: #333;
                margin-top: 30px;
            }
            h1 { color: #2c3e50; }
            .chess-board { 
                border-spacing: 0; 
                border-collapse: collapse; 
                margin-top: 20px; 
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                border: 2px solid #333;
            }
            .chess-board th { padding: 10px; background-color: #ddd; }
            .chess-board td { 
                border: 1px solid #555; 
                width: 60px; height: 60px; 
                text-align: center; 
                vertical-align: middle; 
                font-size: 36px; 
            }
            .chess-board .light { background: #f0d9b5; }
            .chess-board .dark { background: #b58863; color: white; }
            .error-box { 
                background-color: #ffe6e6; 
                color: #d8000c; 
                border: 1px solid #d8000c; 
                padding: 10px; 
                margin: 10px 0; 
                border-radius: 4px;
            }
            .result-box { 
                font-size: 1.4em; 
                font-weight: bold; 
                margin: 15px; 
                padding: 15px; 
                border-radius: 5px; 
                text-align: center;
            }
            .cookie-info {
                background-color: #d1ecf1;
                color: #0c5460;
                padding: 10px;
                border-radius: 5px;
                margin-bottom: 15px;
                border: 1px solid #bee5eb;
            }
            form { 
                background: white; 
                padding: 15px; 
                border-radius: 8px; 
                box-shadow: 0 2px 4px rgba(0,0,0,0.1); 
            }
            input[type="text"] { padding: 8px; font-size: 16px; width: 60px; text-align: center; }
            input[type="submit"] { 
                padding: 8px 15px; 
                font-size: 16px; 
                cursor: pointer; 
                background-color: #2c3e50; 
                color: white; 
                border: none; 
                border-radius: 4px; 
            }
            input[type="submit"]:hover { background-color: #34495e; }
        </style>
    </head>
    <body>
        <h1>Eight Queens Puzzle</h1>
        
        <%
            // --- Logika Modelu i Sesji ---
            ChessboardModel model = (ChessboardModel) application.getAttribute("chessboardModel");
            Integer queenNr = (Integer) application.getAttribute("queenCounter");
            Boolean isFinished = (Boolean) application.getAttribute("gameFinished");
            String gameMessage = (String) application.getAttribute("gameMessage");
            String msgColor = (String) application.getAttribute("messageColor");
            String errorMessage = (String) request.getAttribute("errorMessage");

            if (model == null) { 
                model = new ChessboardModel(); 
                queenNr = 1;
                isFinished = false;
            }
            
            ArrayList<ArrayList<SquareState>> board = model.getBoard();

            // ============================================
            // === CIASTKA: ODCZYT I WYŚWIETLENIE DANYCH ===
            // ============================================
            String lastMoveValue = "Brak (to Twój pierwszy ruch)";
            Cookie[] cookies = request.getCookies();
            
            if (cookies != null) {
                for (Cookie c : cookies) {
                    // Szukamy ciastka o naszej nazwie
                    if ("eqp_last_move".equals(c.getName())) {
                        lastMoveValue = c.getValue();
                        break;
                    }
                }
            }
            // ============================================
        %>

        <div class="cookie-info">
            Twoja ostatnia poprawna pozycja (z ciasteczka): <strong><%= lastMoveValue %></strong>
        </div>

        <% if (errorMessage != null) { %>
            <div class="error-box"><%= errorMessage %></div>
        <% } %>

        <% if (!isFinished) { %>
            <form action="Input" method="POST">
                <label for="posInput">Position used by Queen <strong><%= queenNr %></strong>:</label>
                <input type="text" id="posInput" name="inputPos" size="5" maxlength="2" autofocus required placeholder="e.g. A1">
                <input type="submit" value="Place Queen">
            </form>
        <% } else { %>
            <div class="result-box" style="background-color: <%= "green".equals(msgColor) ? "#d4edda" : "#f8d7da" %>; color: <%= "green".equals(msgColor) ? "#155724" : "#721c24" %>;">
                <%= gameMessage %>
            </div>
            <form action="Input" method="POST">
                <input type="hidden" name="action" value="reset">
                <input type="submit" value="RESET GAME">
            </form>
        <% } %>

        <table class="chess-board">
            <tbody>
                <tr>
                    <th></th>
                    <% for(char c='A'; c<='H'; c++) { %> <th><%= c %></th> <% } %>
                </tr>
                <% for(int row=0; row<8; row++) { %>
                <tr>
                    <th><%= 8 - row %></th>
                    <% for(int col=0; col<8; col++) { 
                        String cssClass = ((row + col) % 2 == 0) ? "light" : "dark";
                        SquareState state = board.get(row).get(col);
                        String symbol = (state != null && state.toString().equals("X")) ? "♛" : "";
                    %>
                        <td class="<%= cssClass %>"><%= symbol %></td>
                    <% } %>
                </tr>
                <% } %>
            </tbody>
        </table>
        
        <div style="margin-top: 30px;">
            <form action="History" method="GET">
                <input type="submit" value="Zobacz Historię Ruchów" style="background-color: #7f8c8d;">
            </form>
        </div>
    </body>
</html>