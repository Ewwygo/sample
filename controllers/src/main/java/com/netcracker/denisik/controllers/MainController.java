package com.netcracker.denisik.controllers;

import com.netcracker.denisik.entity.Residence;
import com.netcracker.denisik.repository.ResidenceRepository;
import com.netcracker.denisik.userDetails.UserDetailsImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@Setter(onMethod_ = @Autowired)
public class MainController {

    private ResidenceRepository residenceRepository;

    @GetMapping(value = {"/hotel","/"})
    public String hello(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        if (userDetails != null){
            model.addAttribute("user",userDetails.getUser());
        } else {
            model.addAttribute("user",null);

        }
        return "index";
    }

    @GetMapping(value = "/rooms")
    public String getRooms(){
        return "hotelRoomsInfo";
    }

    @GetMapping(value = "/leisure")
    public String getLeisure(){return "leisureInfo";}

    @GetMapping(value = "/facilities")
    public String getFacilities(){return "facilitiesInfo";}

}
