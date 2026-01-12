package pojekt.eightqueensproblem.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Move implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position; // np. "A1"
    private int moveOrder;   // Kolejność ruchu: 1, 2, 3...

    //Many to one
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public Move(String position, int moveOrder) {
        this.position = position;
        this.moveOrder = moveOrder;
    }

    @Override
    public String toString() {
        return "Move[pos=" + position + ", order=" + moveOrder + "]";
    }
}