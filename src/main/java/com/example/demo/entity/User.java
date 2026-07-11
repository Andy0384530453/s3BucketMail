package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "user")
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class User {
  @Id
  @Column(name = "user_id", nullable = false, updatable = false)
  private UUID Userid;

  @Column(name = "first_name", nullable = false, updatable = false)
  private String FirstName;

  @Column(name = "last_name", nullable = false, updatable = false)
  private String LastName;

  @Column(name = "user_name")
  private String Username;

  @Column(name = "mail")
  private String Mail;

  @OneToMany(mappedBy = "user")
  private List<Suscribe> suscribeList;
}
