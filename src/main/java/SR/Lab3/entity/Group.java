package SR.Lab3.entity;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "study_groups")
@AttributeOverride(name = "id", column = @Column(name = "`gr_id`"))
public class Group extends AbstractEntity {
    @Column(unique = true)
    private String grName;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "group", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Student> students;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
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
        return "GROUP - " + id + ": [name=" + grName + ", students=" + students + "]";
    }
}
