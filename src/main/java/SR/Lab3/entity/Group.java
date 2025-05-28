package SR.Lab3.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "study_groups")
@AttributeOverride(name = "id", column = @Column(name = "`gr_id`"))
public class Group extends AbstractEntity {

    @Column(unique = true)
    @JsonProperty("gr_name")
    private String grName;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("group-students")
    private Set<Student> students;

    @ManyToOne(fetch = FetchType.LAZY) // Рекомендуется LAZY для ManyToOne
    @JoinColumn(name = "faculty_id")
    @JsonBackReference("faculty-groups") // <--- ИСПРАВЛЕНО: Указано имя, совпадающее с Faculty
    private Faculty faculty;

    @Transient
    @JsonProperty(value = "faculty_id", access = JsonProperty.Access.WRITE_ONLY) // Только для записи из JSON
    private Long facultyIdInput;


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
        return "GROUP - " + (id != null ? id : "null_id") + ": [name=" + grName + // Проверка на null для id, если он из AbstractEntity
                ", studentsCount=" + (students != null ? students.size() : 0) +
                ", facultyId=" + (faculty != null && faculty.getId() != null ? faculty.getId() : "null") + "]"; // Добавил facultyId в toString
    }
}
