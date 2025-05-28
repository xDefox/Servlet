package SR.Lab3.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student extends AbstractEntity { // Предполагаем, что Student тоже наследуется
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY) // Рекомендуется LAZY для ManyToOne
    @JoinColumn(name = "gr_id")
    @JsonBackReference("group-students") // <--- ИСПРАВЛЕНО: Указано имя, совпадающее с Group
    private Group group;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student - " + (id != null ? id : "null_id") + ": [name=" + name + ", surname=" + surname + ", phoneNumber=" + phoneNumber +
                ", groupId=" + (group != null && group.getId() != null ? group.getId() : "null") + "]"; // Добавил groupId в toString
    }
}
