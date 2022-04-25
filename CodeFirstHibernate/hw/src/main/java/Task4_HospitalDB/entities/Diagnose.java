package Task4_HospitalDB.entities;

import javax.persistence.*;

@Entity(name = "Task4_diagnoses")
public class Diagnose {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String notes;


    public Diagnose() {

    }

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
