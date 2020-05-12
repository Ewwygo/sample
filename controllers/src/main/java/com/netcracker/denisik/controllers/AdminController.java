package com.netcracker.denisik.controllers;

import com.netcracker.denisik.entity.Car;
import com.netcracker.denisik.entity.CarStatus;
import com.netcracker.denisik.entity.DBFile;
import com.netcracker.denisik.entity.TypeRoom;
import com.netcracker.denisik.repository.CarRepository;
import com.netcracker.denisik.repository.DBFileRepository;
import com.netcracker.denisik.repository.TypeRoomRepository;
import com.netcracker.denisik.services.DBFileService;
import com.netcracker.denisik.services.RoomService;
import com.netcracker.denisik.services.ServicesService;
import lombok.Setter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@Setter(onMethod_ = @Autowired)
public class AdminController {

    private CarRepository carRepository;
    private ServicesService servicesService;
    private RoomService roomService;
    private TypeRoomRepository typeRoomRepository;
    private DBFileRepository fileRepository;
    private DBFileService dbFileService;

    @GetMapping("/addEntity")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addEntity(){
        return "admin/addEntity";
    }

    @PostMapping("/addCar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addCar(@RequestParam String mark, @RequestParam Integer price){
        Car car = new Car();
        car.setMark(mark);
        car.setPrice(price);
        car.setCarStatus(CarStatus.FREE);
        carRepository.save(car);
        return "redirect:/admin/addEntity";
    }

    @PostMapping(value = "/addService")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addService(@RequestParam String name, @RequestParam Integer price){
        servicesService.addService(name,price);
        return "redirect:/admin/addEntity";
    }

    @PostMapping("/addRoom")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addRoom(@RequestParam Integer costDay,@RequestParam Integer numSeats, @RequestParam Long typeId){
        TypeRoom typeRoom = typeRoomRepository.findOne(typeId);
        roomService.addRoom(costDay, numSeats, typeRoom);
        return "redirect:/admin/addEntity";
    }

    @PostMapping("/addTypeRoom")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addTypeRoom(@RequestParam String name, @RequestParam MultipartFile[] picture) throws Exception {
        if(!typeRoomRepository.existsByTypeRoom(name)){
            TypeRoom typeRoom = new TypeRoom();
            typeRoom.setTypeRoom(name);
            Set<DBFile> files = new HashSet<>();
            for (MultipartFile pic: picture
                 ) {
                DBFile file = new DBFile();
                file.setData(pic.getBytes());
                file.setFileType(pic.getContentType());
                file.setFileName(pic.getOriginalFilename());
                dbFileService.save(file);
                files.add(file);
            }
            typeRoom.setPictures(files);
            typeRoomRepository.save(typeRoom);

        } else {
            throw new Exception("TypeRoom with such name already exists");
        }
        return "admin/addEntity";
    }

//    @GetMapping("/product/image/{id}")
//    public void showProductImage(@PathVariable Long id,
//                               HttpServletResponse response) throws IOException {
//        response.setContentType("image/jpeg"); // Or whatever format you wanna use
//
//        TypeRoom product = typeRoomRepository.findOne(id);
//
//        InputStream is = new ByteArrayInputStream(product.getPicture().getData());
//        IOUtils(is, response.getOutputStream());
//    }
}
