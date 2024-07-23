package com.example.hotel.repository;

import com.example.hotel.domain.BookingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRoomRepository extends JpaRepository<BookingRoom, Long> {
    @Query("select b from BookingRoom b LEFT JOIN FETCH b.dichVu d where b.user.id = ?1")
    List<BookingRoom> findByUserId(Long id);
}