package com.example.demo.service.event;

import com.example.demo.endpoint.event.model.SendEmailRequested;
import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.mail.Email;
import com.example.demo.mail.Mailer;
import com.example.demo.repository.model.CourseRepository;
import com.example.demo.repository.model.UserRepository;
import com.example.demo.service.PdfTicketService;
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
  private final PdfTicketService pdfTicketService;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;

  @SneakyThrows
  @Override
  public void accept(SendEmailRequested sendEmailRequested) {
    InternetAddress recipientAddress = new InternetAddress(sendEmailRequested.getTo());

    // Fetch real data for the PDF
    User user = userRepository.findById(sendEmailRequested.getUserId()).orElseThrow();
    Course course = courseRepository.findById(sendEmailRequested.getCourseId()).orElseThrow();

    // Generate the ticket PDF
    var ticketPdf =
        pdfTicketService.generateTicket(user, course, sendEmailRequested.getSuscribeId());

    mailer.accept(
        new Email(
            recipientAddress,
            List.of(),
            List.of(),
            "Validation de votre inscription au cours",
            "Bonjour ! Votre inscription au cours a bien \u00e9t\u00e9 prise en compte.<br/>"
                + "Veuillez trouver votre ticket d'inscription en pi\u00e8ce jointe.",
            List.of(ticketPdf)));
  }
}
