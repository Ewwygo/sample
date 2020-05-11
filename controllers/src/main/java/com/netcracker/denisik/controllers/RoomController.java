package com.netcracker.denisik.controllers;

import com.netcracker.denisik.entity.Status;
import com.netcracker.denisik.entity.TypeRoom;
import com.netcracker.denisik.entity.User;
import com.netcracker.denisik.repository.ResidenceRepository;
import com.netcracker.denisik.repository.RoomRepository;
import com.netcracker.denisik.repository.TypeRoomRepository;
import com.netcracker.denisik.services.RoomService;
import com.netcracker.denisik.userDetails.UserDetailsImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Setter(onMethod_ = @Autowired)
public class RoomController {

    private RoomService roomService;
    private TypeRoomRepository typeRoomRepository;
    private RoomRepository roomRepository;
    private ResidenceRepository residenceRepository;

    @GetMapping(value = "/rooms")
    public String getRooms(){
        return "hotelRoomsInfo";
    }

    @PostMapping("/addRoom")
    public String addRoom(@RequestParam Integer costDay,@RequestParam Integer numSeats, @RequestParam Long typeId){
        TypeRoom typeRoom = typeRoomRepository.findOne(typeId);
        roomService.addRoom(costDay, numSeats, typeRoom);
        return "redirect:/hello";
    }

    @GetMapping(value = "/getFilteredRooms")
    public String getFilteredRooms(@RequestParam Integer dayCost, @RequestParam Integer seatsAmount, Model model){
        model.addAttribute("rooms",roomService.findFilteredRoom(dayCost, seatsAmount));
        return "ResultSearch";
    }

    @PostMapping(value = "/bookRoom")
    public String bookRoom(@RequestParam Long roomId, @AuthenticationPrincipal UserDetailsImpl user) throws Exception {
        if (!residenceRepository.existsByUserAndStatus(user.getUser(), Status.ACTIVE)){
            roomService.bookRoom(user.getUser(),roomId);
        } else {
            throw new Exception("user already has active residence");
        }
        return "redirect:/getActiveResidence";
    }
}
