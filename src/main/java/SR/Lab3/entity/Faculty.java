package SR.Lab3.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "faculties")
public class Faculty extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Group> groups;

    // Конструкторы
    public Faculty() {}

    public Faculty(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Group> getGroups() {
        return (List<Group>) groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = (Set<Group>) groups;
    }

    // Методы для управления связями
    public void addGroup(Group group) {
        groups.add(group);
        group.setFaculty(this);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
        group.setFaculty(null);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", groupsCount=" + (groups != null ? groups.size() : 0) +
                '}';
    }
}