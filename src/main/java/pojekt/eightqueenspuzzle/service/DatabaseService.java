package pojekt.eightqueensproblem.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import pojekt.eightqueensproblem.entities.Game;

/**
 * Serwis realizujący operacje zapisu i odczytu z bazy danych.
 * Zgodnie z wymaganiami prototypu: brak obsługi wyjątków (try-catch).
 * * @author Adam
 */
public class DatabaseService {

    // Fabryka EntityManagerów - tworzona na podstawie nazwy z persistence.xml
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EightQueensPU");

    /**
     * Zapisuje obiekt Game (wraz z powiązanymi ruchami) do bazy danych.
     */
    public void saveGame(Game game) {
        EntityManager em = emf.createEntityManager();
        
        // Rozpoczęcie transakcji
        em.getTransaction().begin();
        
        // Operacja zapisu (INSERT). Dzięki kaskadzie w encji Game, zapiszą się też Moves.
        em.persist(game);
        
        // Zatwierdzenie transakcji
        em.getTransaction().commit();
        
        // Zamknięcie managera
        em.close();
    }

    /**
     * Pobiera listę wszystkich gier z bazy danych.
     */
    public List<Game> findAllGames() {
        EntityManager em = emf.createEntityManager();
        
        // Wykonanie zapytania JPQL (Java Persistence Query Language)
        List<Game> games = em.createQuery("SELECT g FROM Game g", Game.class).getResultList();
        
        em.close();
        
        return games;
    }
}