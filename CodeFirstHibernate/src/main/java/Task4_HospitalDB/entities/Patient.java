package Task4_HospitalDB.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Task4_patients")
public class Patient {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String address;
    private String email;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String picture;

    @Column(name = "has_insurance")
    private boolean insured;

    @OneToMany(targetEntity = Visitation.class)
    private Set<Visitation> visitations;

    @OneToMany(targetEntity = Medicament.class)
    private Set<Medicament> usedMedicament;

    @OneToMany(targetEntity = Diagnose.class)
    private Set<Diagnose> diagnoses;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String address, boolean insured, Set<Medicament> usedMedicament, Set<Diagnose> diagnoses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.insured = insured;
        this.usedMedicament = usedMedicament;
        this.diagnoses = diagnoses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isInsured() {
        return insured;
    }

    public void setInsured(boolean insured) {
        this.insured = insured;
    }

    public Set<Visitation> getVisitations() {
        return visitations;
    }

    public void setVisitations(Set<Visitation> visitations) {
        this.visitations = visitations;
    }

    public Set<Medicament> getUsedMedicament() {
        return usedMedicament;
    }

    public void setUsedMedicament(Set<Medicament> usedMedicament) {
        this.usedMedicament = usedMedicament;
    }

    public Set<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }
}
