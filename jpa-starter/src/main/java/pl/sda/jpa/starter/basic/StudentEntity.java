package pl.sda.jpa.starter.basic;

import javax.persistence.*;
import java.time.LocalDate;


@Entity

@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String fullName;
    @Column(name = "yearOfStudy")
    private String yearOfStudy;

    public StudentEntity(String fullName, String yearOfStudy) {
        this.fullName = fullName;
        this.yearOfStudy = yearOfStudy;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    StudentEntity() { }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", yearOfStudy=" + yearOfStudy +
                '}';
    }


}
