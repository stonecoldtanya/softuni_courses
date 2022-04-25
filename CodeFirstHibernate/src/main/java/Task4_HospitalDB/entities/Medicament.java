package Task4_HospitalDB.entities;

import javax.persistence.*;

@Entity
@Table(name = "Task4_medicaments")
public class Medicament {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "medicament_name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
