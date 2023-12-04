package com.sportArea.service.Imp;

import com.sportArea.dao.DeliveryAddressRepository;
import com.sportArea.dao.OrderInfoRepository;
import com.sportArea.entity.DeliveryAddress;
import com.sportArea.entity.OrderInfo;
import com.sportArea.entity.dto.DeliveryAddressDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.DeliveryAddressService;
import com.sportArea.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DeliveryAddressServiceImp implements DeliveryAddressService {

    Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

    private final DeliveryAddressRepository deliveryAddressRepository;

    private final OrderInfoRepository orderInfoRepository;

//    private final UserService userService;

    @Autowired
    public DeliveryAddressServiceImp(DeliveryAddressRepository deliveryAddressRepository,OrderInfoRepository orderInfoRepository) {
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.orderInfoRepository =orderInfoRepository;

    }


    @Override
    public DeliveryAddressDTO findById(Long deliveryId) {

        Optional<DeliveryAddress > address = deliveryAddressRepository.findById(deliveryId);

        if(address.isPresent()){

            DeliveryAddressDTO deliveryAddress = createFromDeliveryAddress(address.get());

            logger.info("From DeliveryAddressServiceImp method -findById- return DeliveryAddress by id: {} ", deliveryId);

            return deliveryAddress;
        }else {
            logger.warn("From DeliveryAddressServiceImp method -findById- send war message " +
                    "( DeliveryAddress with deliveryId: {} is not available. ({}))", deliveryId, HttpStatus.NOT_FOUND);
            throw new GeneralException("DeliveryAddress with deliveryId: " + deliveryId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public DeliveryAddressDTO findByUserId(Long userId) {

        Optional<OrderInfo> address = orderInfoRepository.findByUserId(userId);

        if(address.isPresent()){

            DeliveryAddressDTO deliveryAddress = createFromDeliveryAddress(address.get().getDelivery());

            logger.info("From DeliveryAddressServiceImp method -findByUserId- return DeliveryAddress by userId: {} ", userId);

            return deliveryAddress;
        }else {
            logger.warn("From DeliveryAddressServiceImp method -findByUserId- send war message " +
                    "( DeliveryAddress with userId: {} is not available. ({}))", userId, HttpStatus.NOT_FOUND);
            throw new GeneralException("DeliveryAddress with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void save(DeliveryAddressDTO address){

        if (address != null) {
            DeliveryAddress deliveryAddress = createFromDeliveryAddressDTO(address);
            deliveryAddressRepository.save(deliveryAddress);

            logger.info("From DeliveryAddressServiceImp method -save- made new save DeliveryAddress from Data Base.");
        }else {
            logger.warn("From DeliveryAddressServiceImp method -save- send war message " +
                    "( DeliveryAddress is not available or his is empty. ({})))", HttpStatus.NOT_FOUND);

            throw new GeneralException("User is not available or his is empty. ", HttpStatus.NOT_FOUND);
        }
    }


    public DeliveryAddressDTO createFromDeliveryAddress(DeliveryAddress deliveryAddress){
        return  DeliveryAddressDTO.builder()
                .deliveryId(deliveryAddress.getDeliveryId())
//                .name(deliveryAddress.getName())
//                .phoneNumber(deliveryAddress.getPhoneNumber())
                .region(deliveryAddress.getRegion())
                .city(deliveryAddress.getCity())
                .department(deliveryAddress.getDepartment())
//                .userId(deliveryAddress.getUser().getUserId())
                .build();
    }

    public DeliveryAddress createFromDeliveryAddressDTO(DeliveryAddressDTO deliveryAddress){
        if(deliveryAddress.getDeliveryId()!=null){
            return  DeliveryAddress.builder()
                    .deliveryId(deliveryAddress.getDeliveryId())
//                .phoneNumber(deliveryAddress.getPhoneNumber())
                    .region(deliveryAddress.getRegion())
                    .city(deliveryAddress.getCity())
                    .department(deliveryAddress.getDepartment())
//                .user(userService.findByIdInUser(deliveryAddress.getUserId()))
                    .build();
        }else {
            return DeliveryAddress.builder()
//                .name(deliveryAddress.getName())
//                .phoneNumber(deliveryAddress.getPhoneNumber())
                    .region(deliveryAddress.getRegion())
                    .city(deliveryAddress.getCity())
                    .department(deliveryAddress.getDepartment())
//                .user(userService.findByIdInUser(deliveryAddress.getUserId()))
                    .build();
        }
    }



}
