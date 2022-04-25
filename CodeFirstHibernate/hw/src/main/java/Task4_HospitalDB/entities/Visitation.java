package Task4_HospitalDB.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Task4_visitations")
public class Visitation {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "visitation_date")
    private LocalDateTime visitationDate;
    private String notes;

    @ManyToOne
    private Patient patient;

    public Visitation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getVisitationDate() {
        return visitationDate;
    }

    public void setVisitationDate(LocalDateTime visitationDate) {
        this.visitationDate = visitationDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
