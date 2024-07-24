package com.example.hotel;

import com.example.hotel.domain.BookingRoom;
import com.example.hotel.repository.BookingRoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BookingRoomRepositoryTest {

    @Autowired
    private BookingRoomRepository bookingRoomRepository;

    @Test
    void testBookingRoomRepository() {
        List<BookingRoom> rooms = bookingRoomRepository.findByUserId(4L);
        rooms.forEach(bookingRoom -> System.out.println(bookingRoom.getDichVu()));
    }
}
