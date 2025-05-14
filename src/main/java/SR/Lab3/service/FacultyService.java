package SR.Lab3.service;

import SR.Lab3.entity.Faculty;
import java.util.List;

public interface FacultyService extends Service<Faculty> {
    Faculty readByName(String name);
    void edit(Faculty entity);
}