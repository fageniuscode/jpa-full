package com.fageniucode.springdatajpa;

import com.fageniucode.springdatajpa.entities.Student;
import com.fageniucode.springdatajpa.repositories.StudentRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootApplication
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            //generateRandomStudents(studentRepository);

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
