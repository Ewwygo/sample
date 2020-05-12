package com.netcracker.denisik.controllers;

import com.netcracker.denisik.services.ServicesService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/services")
@Setter(onMethod_ = @Autowired)
public class ServicesController {


}
