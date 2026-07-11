package com.example.demo.service.event;

import com.example.demo.endpoint.event.model.SendEmailRequested;
import com.example.demo.mail.Email;
import com.example.demo.mail.Mailer;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendEmailRequestedService implements Consumer<SendEmailRequested> {

  private final Mailer mailer;

  @SneakyThrows
  @Override
  public void accept(SendEmailRequested sendEmailRequested) {
    InternetAddress recipientAddress = new InternetAddress(sendEmailRequested.getTo());

    mailer.accept(
        new Email(
            recipientAddress,
            List.of(), // CC
            List.of(), // BCC
            "Validation de votre inscription au cours",
            "Bonjour ! Votre inscription au cours a bien été prise en compte.",
            List.of() // Pièces jointes
            ));
  }
}
