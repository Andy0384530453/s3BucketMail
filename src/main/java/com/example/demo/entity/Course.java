package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
  @Id
  @Column(name = "course_id")
  private UUID CourseID;

  @Column(name = "course_name")
  private String CourseName;

  @Column(name = "start_date")
  private Instant StartDate;

  @Column(name = "end_date")
  private Instant EndDate;

  @OneToMany(mappedBy = "course")
  @JsonIgnore
  private List<Suscribe> suscribeList;
}
