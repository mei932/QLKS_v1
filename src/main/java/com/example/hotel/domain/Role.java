package com.example.hotel.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "tbl_role")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Role {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "role_name")
  private String roleName;

  @Builder.Default
  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
  private Set<User> users = new HashSet<>();

  public Role(String roleName) {
    this.roleName = roleName;
  }
}
