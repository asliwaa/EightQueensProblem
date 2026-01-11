<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="pl">
<head>
    <title>Historia Gry</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        body { font-family: sans-serif; background-color: #f4f4f9; padding: 20px; text-align: center;}
        h1 { color: #2c3e50; }
        .log-container { 
            background: white; 
            width: 50%; 
            margin: 0 auto; 
            border: 1px solid #ccc; 
            border-radius: 8px; 
            padding: 20px; 
            text-align: left;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        ul { list-style-type: none; padding: 0; }
        li { padding: 8px; border-bottom: 1px solid #eee; }
        li:last-child { border-bottom: none; }
        .btn { 
            display: inline-block; margin-top: 20px; padding: 10px 20px; 
            background-color: #2c3e50; color: white; text-decoration: none; border-radius: 4px; 
        }
        .btn:hover { background-color: #34495e; }
    </style>
</head>
<body>
    <h1>Historia Zdarzeń</h1>
    
    <div class="log-container">
        <ul>
            <%
                List<String> history = (List<String>) request.getAttribute("historyList");
                if (history != null && !history.isEmpty()) {
                    for (String log : history) {
            %>
                        <li><%= log %></li>
            <%
                    }
                } else {
            %>
                    <li style="color: grey;">Brak wpisów w historii.</li>
            <% } %>
        </ul>
    </div>
    
    <a href="index.jsp" class="btn">Powrót do Gry</a>
</body>
</html>