package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Game")
public class Game extends BaseEntity implements Serializable {
    public Game() {

    }


}
