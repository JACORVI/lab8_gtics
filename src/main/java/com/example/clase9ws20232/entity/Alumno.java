import com.example.clase9ws20232.entity.Facultad;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "estudiante") // Matches the table name in your schema
@Getter
@Setter
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestudiante", nullable = false) // Matches the ID column in the schema
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 45) // Updated to match schema column name
    private String name;

    @Column(name = "GPA", precision = 4, scale = 2, nullable = false) // Precision 4, scale 2 for GPA
    private BigDecimal gpa;

    @Column(name = "creditos", nullable = false) // Matches the schema column name for credits
    private Integer creditsCompleted;

    @ManyToOne
    @JoinColumn(name = "facultad_idfacultad", nullable = false) // Matches schema column for the foreign key
    private Facultad facultad;
}
