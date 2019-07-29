package com.dchip.cloudparking.api.serviceImp;

import com.dchip.cloudparking.api.iRepository.ICardRepository;
import com.dchip.cloudparking.api.iService.ICardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImp extends BaseService implements ICardService {

    @Autowired
    private ICardRepository cardRepository;

    @Override
    public Integer isFixedByLicensePlate(String licensePlate, Integer parkingId) {
        return cardRepository.isFixedByLicensePlate(licensePlate,parkingId);
    }
}
