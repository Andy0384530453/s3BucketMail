package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "suscribe")
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Suscribe {
  @Id
  @Column(name = "suscribe_uuid")
  private UUID Suscribe_uuid;

  @ManyToOne()
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;
}
