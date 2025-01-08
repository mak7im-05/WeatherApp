package org.maxim.weatherapp.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    private UUID id;

    @Column(name = "user_Id")
    private int userId;

    @Column(name = "expiresAt")
    private LocalDateTime expiresAt;
}
