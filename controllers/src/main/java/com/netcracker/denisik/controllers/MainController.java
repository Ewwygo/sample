package com.netcracker.denisik.controllers;

import com.netcracker.denisik.entity.Residence;
import com.netcracker.denisik.repository.ResidenceRepository;
import com.netcracker.denisik.userDetails.UserDetailsImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@Setter(onMethod_ = @Autowired)
public class MainController {

    private ResidenceRepository residenceRepository;

    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        if (userDetails != null){
            model.addAttribute("user",userDetails.getUser());
        } else {
            model.addAttribute("user",null);

        }
        return "index";
    }

    @GetMapping("/addEntity")
    public String addEntity(){
        return "admin/addEntity";
    }

    @GetMapping("/personalArea")
    public String personalArea(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){

        Optional<Residence> activeByUser = residenceRepository.findActiveByUser(userDetails.getUser());
        if (activeByUser.isPresent()){
            Residence o = activeByUser.get();
            model.addAttribute("activeResidence", o);
        } else {
            model.addAttribute("activeResidence", null);
        }
        model.addAttribute("residences",residenceRepository.findAllByUser(userDetails.getUser()));
        model.addAttribute("user", userDetails.getUser());
        return "personalArea";
    }
}
