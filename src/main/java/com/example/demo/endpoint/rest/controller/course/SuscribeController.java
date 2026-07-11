package com.example.demo.endpoint.rest.controller.course;

import com.example.demo.endpoint.event.EventProducer;
import com.example.demo.endpoint.event.model.SendEmailRequested;
import com.example.demo.entity.Suscribe;
import com.example.demo.repository.model.SuscribeRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SuscribeController {

  private final SuscribeRepository suscribeRepository;
  private final EventProducer<SendEmailRequested> eventProducer;

  @PostMapping("/suscribes")
  @SneakyThrows
  public Suscribe createSubscription(@RequestBody Suscribe suscribe) {

    Suscribe saved = suscribeRepository.save(suscribe);

    String userEmail = saved.getUser().getMail();
    var event = SendEmailRequested.builder().to(userEmail).build();
    eventProducer.accept(List.of(event));

    return saved;
  }
}
