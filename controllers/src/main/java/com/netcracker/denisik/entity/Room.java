package com.netcracker.denisik.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_seats")
    private Integer seatsAmount;

    @Column(name = "cost_day")
    private Integer dayCost;

    @ManyToOne
    @JoinColumn(name = "type")
    private TypeRoom typeRoom;
}
