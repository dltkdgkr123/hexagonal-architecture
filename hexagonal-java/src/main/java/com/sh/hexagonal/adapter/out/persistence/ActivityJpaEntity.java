package com.sh.hexagonal.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "activity")
@NoArgsConstructor
@AllArgsConstructor
public class ActivityJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long ownerAccountId;

    @Column
    private Long sourceAccountId;

    @Column
    private Long targetAccountId;

    @Column
    private Long amount;

    @Column
    private LocalDateTime timestamp;
}
