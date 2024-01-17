package com.fageniucode.springdatajpa;

import com.fageniucode.springdatajpa.entities.*;
import com.fageniucode.springdatajpa.repositories.StudentIdCardRepository;
import com.fageniucode.springdatajpa.repositories.StudentRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository){
        return args -> {
            //generateRandomStudents(studentRepository);

            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@fageniuscode.edu", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17,55));

            student.addBook(
                    new Book("Clean Code", LocalDateTime.now().minusDays(4)));

            student.addBook(
                    new Book("Think and grow Rich", LocalDateTime.now()));

            student.addBook(
                    new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

            StudentIdCard studentIdCard = new StudentIdCard("123456789", student);

            student.setStudentIdCard(studentIdCard);

            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 1L),
                    student,
                    new Course("Computer Science", "IT"),
                    LocalDateTime.now()
            ));

            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 2L),
                    student,
                    new Course("Amigoscode spring data JPA", "IT"),
                    LocalDateTime.now().minusDays(3)
            ));

            /*student.enrolToCourse(
                    new Course("Computer Science", "IT"));

            student.enrolToCourse(
                    new Course("Amigoscode spring data JPA", "IT"));*/

            studentRepository.save(student);

            // Avec @OneToOne(cascade = CascadeType.ALL),
            // En enregistrant dans la table StudentIdCard, on enregistre en même temps
            // dans la table Student
            //studentIdCardRepository.save(studentIdCard);

            studentRepository.findById(1L).
                    ifPresent(s -> {
                        System.out.println("fetch book lazy...");
                        List<Book> books = student.getBooks();
                        books.forEach(book -> {
                            System.out.println(
                                    s.getFirstName() + " borrowed " + book.getBookName());
                        });
                    });

            /*studentIdCardRepository.findById(1L).
                    ifPresent(System.out::println);*/

            // studentRepository.deleteById(1L);

            /* System.out.println("Adding ibrah and Ahmed");
            Student ibrah = new Student("Ibrahima",
                                        "FAYE",
                                        "fageniuscode@gmail.com",
                                        29);

            Student ahmed = new Student("Ahmed",
                    "FAYE",
                    "ahmed@gmail.com",
                    19);

            Student oumy = new Student("Oumy",
                    "FAYE",
                    "oumy@gmail.com",
                    19);
            studentRepository.saveAll(List.of(ibrah,ahmed));

            studentRepository
                    .findStudentByEmail("fageniuscode@gmail.com")
                    .ifPresentOrElse(System.out::println,()->{
                        System.out.println("Student with email : fageniuscode@gmail.com not found");
                    });

            studentRepository
                    .findStudentsByFirstNameEqualsAndAgeEqualsNative("Ibrahima", 29)
                    .forEach(System.out::println);

            System.out.println("Deleting Student");
            System.out.println(studentRepository.deleteStudentById(3L)); */
        };
    }

    private void sortingAndPaging(StudentRepository studentRepository){
        PageRequest pageRequest = PageRequest.of(0,5, Sort.by("firstName").ascending());
        Page<Student> page = studentRepository.findAll(pageRequest);
        System.out.println(page);
    }

    private void sorting(StudentRepository studentRepository){
        Sort sort = Sort.by("firstName").ascending()
                .and(Sort.by("age").descending());
        studentRepository.findAll(sort)
                .forEach(student -> {
                    System.out.println(student.getFirstName()+ " "+student.getAge() );
                });
    }

    private void generateRandomStudents(StudentRepository studentRepository){
        Faker faker = new Faker();
        for(int i = 1; i <= 20; i++){
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@fageniuscode.edu", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17,55));
            studentRepository.save(student);
        }
    }
}
