package pojekt.eightqueensproblem.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date playedAt;

    private boolean isSuccess;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Move> moves = new ArrayList<>();

    public Game(boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.playedAt = new Date();
    }
    
    // Metoda pomocnicza do dodawania ruchu i ustawiania relacji zwrotnej
    public void addMove(Move move) {
        moves.add(move);
        move.setGame(this);
    }

    @Override
    public String toString() {
        return "Game[id=" + id + ", date=" + playedAt + ", success=" + isSuccess + "]";
    }
}