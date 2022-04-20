package com.sami.hotel.entity;

import com.sami.hotel.entity.BedType;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class RoomType {

    @Id
    @SequenceGenerator(
            name = "roomtype_sequnce",
            sequenceName = "roomtype_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "roomtype_sequence"
    )
    private Long id;
    private String name;

    private Long bedTypeId;
    private int numberOfBeds;

    //Transient variables
    @Transient private BedType bedType;

    public RoomType(String name,
                    Long bedTypeId,
                    int numberOfBeds) {
        this.name = name;
        this.bedTypeId = bedTypeId;
        this.numberOfBeds = numberOfBeds;
    }

}
