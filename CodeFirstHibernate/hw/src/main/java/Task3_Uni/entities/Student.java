package Task3_Uni.entities;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Task3_students")
public class Student extends Person {
    @Column(name = "average_grade")
    private double averageGrade;

    private String attendance;

    @ManyToMany
    @JoinTable(
            name = "Task3_students_courses",
            joinColumns =
            @JoinColumn(name = "students_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "courses_id", referencedColumnName = "id")
    )
    private Set<Course> courses;

    public Student() {
        super();
    }

    public Student(String firstName, String lastName, String phoneNumber, float averageGrade, String attendance) {
        super(firstName, lastName, phoneNumber);

        this.averageGrade = averageGrade;
        this.attendance = attendance;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(float averageGrade) {
        this.averageGrade = averageGrade;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

}
