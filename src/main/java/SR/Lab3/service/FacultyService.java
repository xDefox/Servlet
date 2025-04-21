package SR.Lab3.service;

import SR.Lab3.entity.Faculty;
import java.util.List;

public interface FacultyService {
    Faculty read(Long id);
    List<Faculty> read();
    void save(Faculty entity);
    void delete(Long id);
    Faculty readByName(String name);
    void edit(Faculty entity);
}