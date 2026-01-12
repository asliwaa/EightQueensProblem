package pojekt.eightqueensproblem.service;

import java.util.List;
import pojekt.eightqueensproblem.entities.Game;
import pojekt.eightqueensproblem.entities.Move;

/**
 * Prosta klasa do manualnego przetestowania działania bazy danych.
 * Uruchom ją jako zwykłą aplikację Java (Run File), a nie na serwerze.
 */
public class DatabaseTest {
    
    public static void main(String[] args) {
        System.out.println("=== ROZPOCZĘCIE TESTU BAZY DANYCH ===");
        
        // 1. Inicjalizacja serwisu (to uruchomi JPA i stworzy bazę w pamięci)
        DatabaseService service = new DatabaseService();
        
        // 2. Symulacja rozegranej gry
        System.out.println("-> Tworzenie przykładowej gry...");
        Game game = new Game(true); // Zakładamy, że gra zakończona sukcesem
        
        // Dodajemy kilka ruchów
        game.addMove(new Move("A1", 1));
        game.addMove(new Move("B3", 2));
        game.addMove(new Move("C5", 3));
        game.addMove(new Move("D2", 4));
        
        // 3. Zapis do bazy
        System.out.println("-> Zapisywanie gry do bazy danych...");
        service.saveGame(game);
        System.out.println("-> Zapisano pomyślnie.");
        
        // 4. Odczyt z bazy (sprawdzenie czy dane tam są)
        System.out.println("-> Pobieranie wszystkich gier z bazy...");
        List<Game> gamesFromDb = service.findAllGames();
        
        System.out.println("-> Liczba gier w bazie: " + gamesFromDb.size());
        
        // 5. Wypisanie szczegółów
        for (Game g : gamesFromDb) {
            System.out.println("   [GRA ID=" + g.getId() + "] Data: " + g.getPlayedAt() + ", Sukces: " + g.isSuccess());
            System.out.println("   Ruchy:");
            for (Move m : g.getMoves()) {
                System.out.println("     - Ruch nr " + m.getMoveOrder() + ": " + m.getPosition());
            }
        }
        
        System.out.println("=== KONIEC TESTU (Jeśli widzisz dane powyżej, baza działa!) ===");
        
        // Wymuszenie zakończenia wątków JPA (czasem potrzebne przy aplikacjach konsolowych)
        System.exit(0);
    }
}