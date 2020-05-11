package com.netcracker.denisik.services;

import com.netcracker.denisik.entity.Residence;
import com.netcracker.denisik.entity.User;
import com.netcracker.denisik.repository.ResidenceRepository;
import com.netcracker.denisik.repository.ServicesRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Setter(onMethod_ = @Autowired)
public class ResidenceService {

    private ResidenceRepository residenceRepository;
    private ServicesRepository servicesRepository;

    public Residence findActiveResidence(User user) throws Exception {
        Optional<Residence> byUser = residenceRepository.findByUser(user);
        if (byUser.isPresent()){
            return byUser.get();
        }
        throw new Exception("residence not found");
    }

    @Transactional
    public void addService(Long serviceId, User user){
        Optional<Residence> residence = residenceRepository.findByUser(user);
        if (residence.isPresent()){
            residence.get().addService(servicesRepository.findOne(serviceId));
            residenceRepository.save(residence.get());
        }
    }
}
