package SR.Lab3.service.impl;

import SR.Lab3.entity.Faculty;
import SR.Lab3.repository.FacultyRepository;
import SR.Lab3.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;

    @Autowired
    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Faculty> readAll() {
        return repository.findAll();
    }

    @Override
    public Faculty read(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found with id: " + id));
    }

    @Override
    public void save(Faculty faculty) {
        validateFaculty(faculty);
        repository.save(faculty);
    }

    @Override
    public void delete(long id) {
        Faculty faculty = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found with id: " + id));
        repository.delete(faculty);
    }

    @Override
    public Faculty readByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found with name: " + name));
    }

    @Override
    public void update(Faculty faculty) {
        Faculty existing = repository.findById(faculty.getId())
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found"));

        if (!existing.getName().equals(faculty.getName()) &&
                repository.existsByName(faculty.getName())) {
            throw new IllegalArgumentException("Faculty name must be unique");
        }

        existing.setName(faculty.getName());
        existing.setPhone(faculty.getPhone());
        repository.save(existing);
    }

    private void validateFaculty(Faculty faculty) {
        if (faculty.getName() == null || faculty.getName().isEmpty()) {
            throw new IllegalArgumentException("Faculty name cannot be empty");
        }

        if (repository.existsByName(faculty.getName())) {
            throw new IllegalArgumentException("Faculty with name '" + faculty.getName() + "' already exists");
        }
    }
}