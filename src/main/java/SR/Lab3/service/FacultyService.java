package SR.Lab3.service;

import SR.Lab3.entity.Faculty;
import java.util.List;

public interface FacultyService {
    List<Faculty> readAll();
    Faculty read(long id);
    Faculty save(Faculty faculty);
    void delete(long id);
    Faculty readByName(String name);
    Faculty update(Faculty faculty);
}