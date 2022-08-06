package com.example.membershipsystem.domain.model.users.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthyear", nullable = false)
    private String birthyear;

    @Column(name = "birthday", nullable = false)
    private String birthday;

    @Column(name = "age")
    private Integer age;

    @Column(name = "hand_phone", nullable = false)
    private String handPhone;

    @Column(name = "reg_date")
    @CreatedDate
    private LocalDate regDate;

    public User(Long id) {
        this.id = id;
    }
}
