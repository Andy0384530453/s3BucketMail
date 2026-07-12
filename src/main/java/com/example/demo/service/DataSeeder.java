package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.repository.model.CourseRepository;
import com.example.demo.repository.model.UserRepository;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class DataSeeder {

  private final UserRepository userRepository;
  private final CourseRepository courseRepository;

  @PostConstruct
  void seed() {
    long userCount = userRepository.count();
    long courseCount = courseRepository.count();
    log.info("Found {} users and {} courses in DB", userCount, courseCount);
    if (userCount > 0 && courseCount > 0) {
      log.info("Data already present, skipping seed");
      updateClaireEmail();
      return;
    }

    log.info("Seeding users and courses...");

    var users =
        List.of(
            User.builder()
                .Userid(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .FirstName("Alice")
                .LastName("Martin")
                .Username("alice.m")
                .Mail("alice.martin@example.com")
                .build(),
            User.builder()
                .Userid(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .FirstName("Bob")
                .LastName("Durand")
                .Username("bob.d")
                .Mail("bob.durand@example.com")
                .build(),
            User.builder()
                .Userid(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                .FirstName("Claire")
                .LastName("Petit")
                .Username("claire.p")
                .Mail("hei.andy.100@gmail.com")
                .build());

    var courses =
        List.of(
            Course.builder()
                .CourseID(UUID.fromString("00000000-0000-0000-0000-000000000010"))
                .CourseName("Spring Boot Fundamentals")
                .StartDate(Instant.now())
                .EndDate(Instant.now().plus(30, ChronoUnit.DAYS))
                .build(),
            Course.builder()
                .CourseID(UUID.fromString("00000000-0000-0000-0000-000000000011"))
                .CourseName("AWS Lambda & Serverless")
                .StartDate(Instant.now().plus(7, ChronoUnit.DAYS))
                .EndDate(Instant.now().plus(37, ChronoUnit.DAYS))
                .build(),
            Course.builder()
                .CourseID(UUID.fromString("00000000-0000-0000-0000-000000000012"))
                .CourseName("Java Persistence with Hibernate")
                .StartDate(Instant.now().plus(14, ChronoUnit.DAYS))
                .EndDate(Instant.now().plus(44, ChronoUnit.DAYS))
                .build());

    userRepository.saveAll(users);
    courseRepository.saveAll(courses);

    log.info("Seeded {} users and {} courses", users.size(), courses.size());
  }

  private void updateClaireEmail() {
    var claireId = UUID.fromString("00000000-0000-0000-0000-000000000003");
    Optional<User> claire = userRepository.findById(claireId);
    claire.ifPresent(
        user -> {
          user.setMail("hei.andy.100@gmail.com");
          userRepository.save(user);
          log.info("Updated Claire email to hei.andy.100@gmail.com");
        });
  }
}
