package SR.Lab3.controller;

import SR.Lab3.entity.Student;
import SR.Lab3.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController extends AbstractController<Student>{
    @Autowired
    private StudentService service;

    @Override
    public StudentService getService() {
        return service;
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<Student>> getStudentsByGroup(@PathVariable long id) {
        List<Student> students = service.readByGroup(id);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(students, headers, HttpStatus.OK);
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<List<Student>> getStudentsBySurname(@PathVariable String surname) {
        List<Student> students = service.readBySurname(surname);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(students, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> put(@RequestBody Student entity){
        service.save(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> post(@RequestBody Student entity) {
        service.save(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
