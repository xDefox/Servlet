package SR.Lab3.controller;

import SR.Lab3.entity.Faculty;
import SR.Lab3.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/faculties")
public class FacultyController extends AbstractController<Faculty> {

    @Autowired
    private FacultyService facultyService;

    @Override
    public FacultyService getService() {
        return facultyService;
    }
}