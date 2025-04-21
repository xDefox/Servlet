package SR.Lab3.runner;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import SR.Lab3.entity.Faculty;
import SR.Lab3.entity.Group;
import SR.Lab3.entity.Student;
import SR.Lab3.service.FacultyService;
import SR.Lab3.service.GroupService;
import SR.Lab3.service.StudentService;

@SpringBootApplication
@EnableJpaRepositories("SR.Lab3.repository")
@ComponentScan("SR.Lab3.service")
@EntityScan("SR.Lab3.entity")
public class Main {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(Main.class);
		FacultyService facultyService = ctx.getBean(FacultyService.class);
		GroupService groupService = ctx.getBean(GroupService.class);
		StudentService studentService = ctx.getBean(StudentService.class);

		try {
			// 1. Очистка базы данных (для тестирования)
			clearDatabase(facultyService, groupService, studentService);

			// 2. Создание факультетов
			Faculty fitr = createFaculty(facultyService, "ФИТР", "+375291234567");
			Faculty febu = createFaculty(facultyService, "ФЭБУ", "+375291234568");

			// 3. Создание групп и привязка к факультетам
			Group fitrGroup1 = createGroup(groupService, "ФИТР-101", fitr);
			Group fitrGroup2 = createGroup(groupService, "ФИТР-102", fitr);
			Group febuGroup1 = createGroup(groupService, "ФЭБУ-201", febu);
			Group febuGroup2 = createGroup(groupService, "ФЭБУ-202", febu);

			// 4. Создание студентов
			createStudent(studentService, fitrGroup1, "Иван", "Иванов", "+375291111111");
			createStudent(studentService, fitrGroup1, "Петр", "Петров", "+375292222222");
			createStudent(studentService, fitrGroup2, "Алексей", "Сидоров", "+375293333333");
			createStudent(studentService, febuGroup1, "Мария", "Кузнецова", "+375294444444");
			createStudent(studentService, febuGroup2, "Анна", "Смирнова", "+375295555555");

			// 5. Демонстрация данных
			demonstrateData(facultyService, groupService, studentService);

		} catch (Exception e) {
			System.err.println("Critical error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			ctx.close();
		}
	}

	private static void clearDatabase(FacultyService facultyService,
									  GroupService groupService,
									  StudentService studentService) {
		studentService.read().forEach(s -> studentService.delete(s.getId()));
		groupService.read().forEach(g -> groupService.delete(g.getId()));
		facultyService.read().forEach(f -> facultyService.delete(f.getId()));
	}

	private static Faculty createFaculty(FacultyService service, String name, String phone) {
		try {
			Faculty faculty = new Faculty(name, phone);
			service.save(faculty);
			System.out.println("Created faculty: " + name);
			return faculty;
		} catch (Exception e) {
			System.err.println("Failed to create faculty: " + e.getMessage());
			throw e;
		}
	}

	private static Group createGroup(GroupService service, String name, Faculty faculty) {
		try {
			Group gr = new Group();
			gr.setGrName(name);
			gr.setFaculty(faculty);
			service.save(gr);
			System.out.println("Created group: " + name + " for faculty " + faculty.getName());
			return gr;
		} catch (Exception e) {
			System.err.println("Failed to create group: " + e.getMessage());
			throw e;
		}
	}

	private static Student createStudent(StudentService service, Group group,
										 String name, String surname, String phone) {
		try {
			Student student = new Student();
			student.setName(name);
			student.setSurname(surname);
			student.setPhoneNumber(phone);
			student.setGroup(group);
			service.save(student);
			System.out.println("Created student: " + name + " " + surname +
					" in group " + group.getGrName() +
					" (" + group.getFaculty().getName() + ")");
			return student;
		} catch (Exception e) {
			System.err.println("Failed to create student: " + e.getMessage());
			throw e;
		}
	}

	private static void demonstrateData(FacultyService facultyService,
										GroupService groupService,
										StudentService studentService) {
		System.out.println("\n=== Faculties ===");
		facultyService.read().forEach(f -> {
			System.out.println(f);
			f.getGroups().forEach(g -> {
				System.out.println("  Group: " + g.getGrName());
				g.getStudents().forEach(s ->
						System.out.println("    Student: " + s.getName() + " " + s.getSurname()));
			});
		});

		System.out.println("\n=== All Groups ===");
		groupService.read().forEach(System.out::println);

		System.out.println("\n=== All Students ===");
		studentService.read().forEach(s -> {
			System.out.println(s + " (Group: " + s.getGroup().getGrName() +
					", Faculty: " + s.getGroup().getFaculty().getName() + ")");
		});
	}
}