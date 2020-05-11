package com.netcracker.denisik.controllers;

import com.netcracker.denisik.entity.Facilities;
import com.netcracker.denisik.entity.Status;
import com.netcracker.denisik.entity.TypeRoom;
import com.netcracker.denisik.entity.User;
import com.netcracker.denisik.repository.FacilitiesRepository;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Setter(onMethod_ = @Autowired)
public class RoomController {

    private RoomService roomService;
    private TypeRoomRepository typeRoomRepository;
    private RoomRepository roomRepository;
    private ResidenceRepository residenceRepository;
    private FacilitiesRepository facilitiesRepository;


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
    public String getFilteredRooms(@RequestParam Integer dayCost, @RequestParam Integer seatsAmount,
                                   @RequestParam String checkInDate, @RequestParam String checkOutDate, Model model){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate checkIn = LocalDate.parse(checkInDate,formatter);
        LocalDate checkOut = LocalDate.parse(checkOutDate,formatter);
        model.addAttribute("rooms",roomService.findFilteredRoom(dayCost, seatsAmount,checkIn,checkOut));
        model.addAttribute("checkIn",checkIn);
        model.addAttribute("checkOut",checkOut);
        return "ResultSearch";
    }

    @PostMapping(value = "/bookRoom")
    public String bookRoom(@RequestParam Long roomId, @AuthenticationPrincipal UserDetailsImpl user,
                           @RequestParam String checkIn, @RequestParam String checkOut) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkInDate = LocalDate.parse(checkIn,formatter);
        LocalDate checkOutDate = LocalDate.parse(checkOut,formatter);
        if (!residenceRepository.existsByUserAndStatus(user.getUser(), Status.ACTIVE)){
            roomService.bookRoom(user.getUser(),roomId,checkInDate,checkOutDate);
        } else {
            throw new Exception("user already has active residence");
        }
        return "redirect:/getActiveResidence";
    }

    @GetMapping("/facility")
    public String getFacility(@RequestParam Long typeRoom,Model model){
        TypeRoom typeRoom1 = typeRoomRepository.findOne(typeRoom);
        model.addAttribute("facility", typeRoom1.getFacilitiesSet());
        return "Facility";
    }
}
