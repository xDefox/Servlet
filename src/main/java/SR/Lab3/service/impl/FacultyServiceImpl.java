package SR.Lab3.service.impl;

import SR.Lab3.entity.Faculty;
import SR.Lab3.repository.FacultyRepository;
import SR.Lab3.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;

    @Autowired
    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty read(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found with id: " + id));
    }

    @Override
    public List<Faculty> read() {
        return repository.findAll();
    }

    @Override
    public void save(Faculty faculty) {
        if (faculty.getName() == null || faculty.getName().isEmpty()) {
            throw new IllegalArgumentException("Faculty name cannot be empty");
        }

        if (repository.existsByName(faculty.getName())) {
            throw new IllegalArgumentException("Faculty with name '" + faculty.getName() + "' already exists");
        }

        repository.save(faculty);
    }

    @Override
    public void delete(Long id) {
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
    public void edit(Faculty faculty) {
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
}