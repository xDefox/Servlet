package SR.Lab3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import SR.Lab3.entity.Group;
import SR.Lab3.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByGroup(Group group); // Принимаем объект Group вместо Long

    List<Student> findBySurname(String surname);
}
