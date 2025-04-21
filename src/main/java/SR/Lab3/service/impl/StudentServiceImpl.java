package SR.Lab3.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SR.Lab3.entity.Group;
import SR.Lab3.entity.Student;
import SR.Lab3.repository.GroupRepository;
import SR.Lab3.repository.StudentRepository;
import SR.Lab3.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Student read(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + id));
    }

    @Override
    public List<Student> read() {
        return studentRepository.findAll();
    }

    @Override
    public void save(Student student) {
        if (student.getGroup() == null || student.getGroup().getId() == null) {
            throw new IllegalArgumentException("Student must be associated with a group");
        }

        Group group = groupRepository.findById(student.getGroup().getId())
                .orElseThrow(() -> new NoSuchElementException("Group not found with id: " + student.getGroup().getId()));

        student.setGroup(group);
        studentRepository.save(student);

        // Обновляем коллекцию только если она была инициализирована
        if (group.getStudents() != null) {
            group.getStudents().add(student);
        }
    }

    @Override
    public void delete(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + id));

        Group group = student.getGroup();
        if (group != null && group.getStudents() != null) {
            group.getStudents().remove(student);
            groupRepository.save(group);
        }

        studentRepository.delete(student);
    }

    @Override
    public List<Student> readByGroup(Long groupId) {
        return studentRepository.findByGroup(groupId);
    }

    @Override
    public List<Student> readBySurname(String surname) {
        return studentRepository.findBySurname(surname);
    }

    @Override
    public void edit(Student student) {
        if (student.getId() == null) {
            throw new IllegalArgumentException("Student id cannot be null for edit operation");
        }

        Student existingStudent = studentRepository.findById(student.getId())
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + student.getId()));

        // Обновляем только изменяемые поля
        existingStudent.setName(student.getName());
        existingStudent.setSurname(student.getSurname());
        existingStudent.setPhoneNumber(student.getPhoneNumber());

        // Если нужно изменить группу
        if (student.getGroup() != null && student.getGroup().getId() != null) {
            Group newGroup = groupRepository.findById(student.getGroup().getId())
                    .orElseThrow(() -> new NoSuchElementException("Group not found with id: " + student.getGroup().getId()));

            // Удаляем из старой группы
            Group oldGroup = existingStudent.getGroup();
            if (oldGroup != null && oldGroup.getStudents() != null) {
                oldGroup.getStudents().remove(existingStudent);
                groupRepository.save(oldGroup);
            }

            // Добавляем в новую группу
            existingStudent.setGroup(newGroup);
            if (newGroup.getStudents() != null) {
                newGroup.getStudents().add(existingStudent);
            }
        }

        studentRepository.save(existingStudent);
    }
}