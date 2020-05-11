package com.netcracker.denisik.controllers;

import com.netcracker.denisik.entity.Car;
import com.netcracker.denisik.entity.CarStatus;
import com.netcracker.denisik.repository.CarRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/car")
@Setter(onMethod_ = @Autowired)
public class CarController {

    private CarRepository carRepository;

    @RequestMapping("/addCar")
    public String addCar(@RequestParam String mark, @RequestParam Integer price){
        Car car = new Car();
        car.setMark(mark);
        car.setPrice(price);
        car.setCarStatus(CarStatus.FREE);
        carRepository.save(car);
        return "redirect:/addEntity";
    }
}