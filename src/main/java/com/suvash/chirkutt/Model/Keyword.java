package com.suvash.chirkutt.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "keywords")
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String keyword;
}