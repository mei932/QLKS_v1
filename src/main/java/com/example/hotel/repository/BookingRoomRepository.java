package com.example.hotel.repository;

import com.example.hotel.domain.BookingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRoomRepository extends JpaRepository<BookingRoom, Long> {
}