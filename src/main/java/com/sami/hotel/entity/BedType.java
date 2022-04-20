package com.sami.hotel.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class BedType {

    @Id
    @SequenceGenerator(
            name = "bedtype_sequence",
            sequenceName = "bedtype_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bedtype_sequence"
    )
    private Long id;
    private String name;

    public BedType (String name) {
        this.name = name;
    }

}
