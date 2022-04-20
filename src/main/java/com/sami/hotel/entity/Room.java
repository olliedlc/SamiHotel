package com.sami.hotel.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Id
    @SequenceGenerator(
            name = "room_sequence",
            sequenceName = "room_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "room_sequence"
    )
    private Long id;
    private Long roomTypeId;
    private int roomsAvailable;
    private boolean hasWifi;
    private String notes;
    private Double roomRate;

    //Transient variables
    @Transient private RoomType roomType;

    public Room(Long roomTypeId,
                int roomsAvailable,
                boolean hasWifi,
                String notes,
                Double roomRate) {
        this.roomTypeId = roomTypeId;
        this.roomsAvailable = roomsAvailable;
        this.hasWifi = hasWifi;
        this.notes = notes;
        this.roomRate = roomRate;
    }

}//2486.36
