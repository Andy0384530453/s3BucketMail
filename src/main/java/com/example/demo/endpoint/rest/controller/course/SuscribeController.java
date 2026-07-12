package com.example.demo.endpoint.rest.controller.course;

import com.example.demo.endpoint.event.EventProducer;
import com.example.demo.endpoint.event.model.SendEmailRequested;
import com.example.demo.entity.Course;
import com.example.demo.entity.Suscribe;
import com.example.demo.entity.User;
import com.example.demo.repository.model.CourseRepository;
import com.example.demo.repository.model.SuscribeRepository;
import com.example.demo.repository.model.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SuscribeController {

  private final SuscribeRepository suscribeRepository;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final EventProducer<SendEmailRequested> eventProducer;

  @PostMapping("/suscribes")
  public Suscribe createSubscription(@RequestBody Suscribe suscribe) {

    User managedUser = userRepository.findById(suscribe.getUser().getUserid()).orElseThrow();
    Course managedCourse =
        courseRepository.findById(suscribe.getCourse().getCourseID()).orElseThrow();

    suscribe.setUser(managedUser);
    suscribe.setCourse(managedCourse);

    Suscribe saved = suscribeRepository.save(suscribe);

    String userEmail = saved.getUser().getMail();
    var event = SendEmailRequested.builder().to(userEmail).build();
    eventProducer.accept(List.of(event));

    return saved;
  }
}
