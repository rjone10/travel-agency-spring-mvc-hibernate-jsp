package com.travel.agency.dao.impl;

import com.travel.agency.dao.BookingDao;
import com.travel.agency.dto.BookingDto;
import com.travel.agency.model.Booking;
import com.travel.agency.model.Room;
import com.travel.agency.model.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
@Slf4j
public class BookingDaoImpl implements BookingDao {

    private final SessionFactory sessionFactory;

    public BookingDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public BookingDto save(Booking booking) {
        Serializable serializable = sessionFactory.getCurrentSession().save(booking);
        if (serializable == null) {
            log.info("Booking with id {} is failed! ", booking.getId());
            return null;
        }
        log.info("Booking with id {} saved successfully! ", booking.getId());
        return new BookingDto(booking.getRoom().getId(), booking.getCheckIn(), booking.getCheckOut());
    }

    @Override
    public Booking getByUserId(Long id) {
        return sessionFactory.getCurrentSession().load(Booking.class, id);
    }

    @Override
    public void delete(Long id) {
        Booking booking = sessionFactory.getCurrentSession().load(Booking.class, id);

        if (booking != null) {
            booking.setRoom(null);
            booking.setUser(null);
            sessionFactory.getCurrentSession().delete(booking);
            log.info("Booking with id {} canceled successfully!", id);
        } else {
            log.info("Cancelling booking with id {} was failed!", id);
        }
    }

    @Override
    public List<Booking> getAllBookings() {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT b FROM Booking b ORDER BY b.id", Booking.class)
                .getResultList();
    }
}
