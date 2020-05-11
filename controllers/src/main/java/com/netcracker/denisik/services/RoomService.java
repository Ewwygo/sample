package com.netcracker.denisik.services;

import com.netcracker.denisik.entity.*;
import com.netcracker.denisik.repository.ResidenceRepository;
import com.netcracker.denisik.repository.RoomRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Setter(onMethod_ = @Autowired)
public class RoomService {

    private RoomRepository roomRepository;
    private ResidenceRepository residenceRepository;

    @Transactional
    public void addRoom(Integer cost, Integer numSeats, TypeRoom type){
        Room room = new Room();
        room.setDayCost(cost);
        room.setSeatsAmount(numSeats);
        room.setTypeRoom(type);
        roomRepository.save(room);
    }

    public List<Room> findFilteredRoom(Integer dayCost, Integer seatsAmount){
        List<Room> rooms = roomRepository.findAllByDayCostAndSeatsAmount(dayCost,seatsAmount);
        return rooms;
    }

    @Transactional
    public void bookRoom(User user, Long roomId){
        Residence residence = new Residence();
        residence.setStatus(Status.ACTIVE);
        residence.setUser(user);
        residence.setRoom(roomRepository.findOne(roomId));
        residenceRepository.save(residence);
    }
}
