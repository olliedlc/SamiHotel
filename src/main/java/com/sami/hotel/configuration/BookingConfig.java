package com.sami.hotel.configuration;

import com.sami.hotel.entity.*;
import com.sami.hotel.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BookingConfig {

    @Bean
    CommandLineRunner lineRunnerRoom(RoomRepository roomRepository) {
        return args -> {

            Room standardRoom = new Room(
                    1L,
                    1,
                    true,
                    "MOUNTAIN VIEW",
                    100.00
            );

            Room deluxeRoom = new Room(
                    2L,
                    1,
                    true,
                    "MOUNTAIN VIEW",
                    120.00
            );

            Room premiereRoom = new Room(
                    3L,
                    1,
                    true,
                    "SEA VIEW",
                    150.00
            );

            Room superiorRoom = new Room(
                    4L,
                    1,
                    true,
                    "SEA VIEW",
                    180.00
            );

            roomRepository.saveAll(
                    List.of(standardRoom,
                            deluxeRoom,
                            premiereRoom,
                            superiorRoom)
            );
        };
    }

    @Bean
    CommandLineRunner lineRunnerRoomType(RoomTypeRepository roomTypeRepository) {
        return args -> {
            RoomType standardRoomType = new RoomType(
                    "STANDARD ROOM",
                    1L,
                    1
            );

            RoomType deluxeRoomType = new RoomType(
                    "DELUXE ROOM",
                    1L,
                    2
            );

            RoomType premiereRoomType = new RoomType(
                    "PREMIERE ROOM",
                    2L,
                    1
            );

            RoomType superiorRoomType = new RoomType(
                    "SUPERIOR ROOM",
                    3L,
                    1
            );

            roomTypeRepository.saveAll(
                    List.of(
                            standardRoomType,
                            deluxeRoomType,
                            premiereRoomType,
                            superiorRoomType)
            );
        };
    }
    @Bean
    CommandLineRunner lineRunnerBedType(BedTypeRepository bedTypeRepository) {
        return args -> {
            BedType doubleBedType = new BedType(
                    "DOUBLE"
            );

            BedType queenBedType = new BedType(
                    "QUEEN"
            );

            BedType kingBedType = new BedType(
                    "KING"
            );

            bedTypeRepository.saveAll(
                    List.of(
                            doubleBedType,
                            queenBedType,
                            kingBedType
                    )
            );
        };
    }

    @Bean
    CommandLineRunner lineRunnerBookingStatus(BookingStatusRepository bookingStatusRepository) {
        return args -> {
            BookingStatus bookedStatus = new BookingStatus(
                    "BOOKED"
            );

            BookingStatus checkedinStatus = new BookingStatus(
                    "CHECKED_IN"
            );

            BookingStatus checkedOutStatus = new BookingStatus(
                    "CHECKED_OUT"
            );

            BookingStatus cancelledStatus = new BookingStatus(
                    "CANCELLED"
            );

            bookingStatusRepository.saveAll(
                    List.of(
                            bookedStatus,
                            checkedinStatus,
                            checkedOutStatus,
                            cancelledStatus
                    )
            );
        };
    }

    @Bean
    CommandLineRunner lineRunnerUser(UserRepository userRepository) {
        return args -> {
            User johnnyUser = new User("customer1",
                    "Johnny",
                    "Depp",
                    false,
                    "johnnydepp@mail.com"
            );

            User keanuUser = new User("customer2",
                    "Keanu",
                    "Reeves",
                    false,
                    "keanureeves@mail.com"
            );

            User adminUser = new User("admin01",
                    "Admin",
                    "User",
                    true,
                    "admin01@mail.com"
            );

            userRepository.saveAll(
                    List.of(
                            johnnyUser,
                            keanuUser,
                            adminUser
                    )
            );
        };
    }
}
