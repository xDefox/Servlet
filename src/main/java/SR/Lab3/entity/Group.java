package SR.Lab3.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "study_groups")
@AttributeOverride(name = "id", column = @Column(name = "`gr_id`"))
public class Group extends AbstractEntity {

    @Column(unique = true)
    private String grName;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Student> students;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    @JsonBackReference
    private Faculty faculty;


    public String getGrName() {
        return grName;
    }

    public void setGrName(String grName) {
        this.grName = grName;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "GROUP - " + id + ": [name=" + grName +
                ", studentsCount=" + (students != null ? students.size() : 0) + "]";
    }
}
