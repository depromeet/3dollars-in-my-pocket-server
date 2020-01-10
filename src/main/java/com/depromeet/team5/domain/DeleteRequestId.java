package com.depromeet.team5.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class DeleteRequestId {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
}
