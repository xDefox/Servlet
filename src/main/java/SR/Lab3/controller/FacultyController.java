package SR.Lab3.controller;

import SR.Lab3.entity.Faculty;
import SR.Lab3.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping
    public ResponseEntity<List<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.read(id));
    }


    // POST - Создать новый факультет
    // Только для ADMIN
    @PreAuthorize("hasRole('ADMIN')") // или hasAuthority('ROLE_ADMIN')
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        // Предполагаем, что метод save вернет сохраненную сущность с ID
        Faculty createdFaculty = facultyService.save(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaculty);
    }

    // PUT - Обновить существующий факультет
    // Только для ADMIN
    @PreAuthorize("hasRole('ADMIN')") // или hasAuthority('ROLE_ADMIN')
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE) // Часто PUT делается на конкретный ресурс /api/faculty/{id}
    public ResponseEntity<Faculty> updateFaculty(@PathVariable long id, @RequestBody Faculty facultyDetails) {
        // Устанавливаем ID из пути, чтобы быть уверенным, что обновляем правильный ресурс
        // и избегаем ситуации, когда ID в теле отличается от ID в пути.
        facultyDetails.setId(id); // Важно, если сервис не делает этого сам.
        // Или ваш сервис update(Faculty faculty) должен внутри проверять faculty.getId()

        Faculty updatedFaculty = facultyService.update(facultyDetails); // Предполагаем, что update вернет обновленную сущность
        // или null/исключение, если сущность с таким ID не найдена.
        if (updatedFaculty == null) {
            // Это поведение зависит от реализации вашего facultyService.update()
            // Если он кидает исключение при отсутствии, то здесь будет обработка через @ControllerAdvice
            // Если возвращает null, то так:
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    /*
    // Альтернативный вариант PUT без ID в пути, если ID всегда есть в теле Faculty
    // PUT - Обновить существующий факультет (ID берется из тела запроса)
    // Только для ADMIN
    @PreAuthorize("hasRole('ADMIN')") // или hasAuthority('ROLE_ADMIN')
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        // Сервис facultyService.update(faculty) должен будет проверить,
        // что faculty.getId() не null и что такой факультет существует.
        Faculty updatedFaculty = facultyService.update(faculty);
        if (updatedFaculty == null) { // Если сервис возвращает null для ненайденного id
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }
    */


    // DELETE - Удалить факультет по ID
    // Только для ADMIN
    @PreAuthorize("hasRole('ADMIN')") // или hasAuthority('ROLE_ADMIN')
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable long id) {
        // Сервис facultyService.delete(id) должен либо успешно удалять,
        // либо кидать исключение (например, EntityNotFoundException), если факультет не найден,
        // которое потом можно обработать глобальным обработчиком исключений.
        // Либо, если метод delete возвращает boolean (true если удалено, false если не найдено):
        // boolean deleted = facultyService.delete(id);
        // if (!deleted) {
        //     return ResponseEntity.notFound().build();
        // }
        facultyService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content - стандартный ответ для успешного DELETE
    }
}