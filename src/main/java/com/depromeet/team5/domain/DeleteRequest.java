package com.depromeet.team5.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class DeleteRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;

    private Long userId;

    @Enumerated(value = EnumType.STRING)
    private DeleteReasonType reason;
}
