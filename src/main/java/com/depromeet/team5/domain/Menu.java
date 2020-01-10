package com.depromeet.team5.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;
}
