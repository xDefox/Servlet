package SR.Lab3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SR.Lab3.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByGroup(Long id);

    List<Student> findBySurname(String name);
}
