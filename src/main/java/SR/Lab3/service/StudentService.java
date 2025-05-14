package SR.Lab3.service;

import java.util.List;

import SR.Lab3.entity.Student;

public interface StudentService extends Service<Student> {

    List<Student> readAllWithGroupAndFacultyInfo();
    
    List<Student> readByGroup(Long groupId);

    List<Student> readBySurname(String surname);

}