package com.sportArea.controller;

import com.sportArea.entity.GuestUser;
import com.sportArea.entity.Role;
import com.sportArea.entity.dto.BasketDTO;
import com.sportArea.entity.dto.GuestDTO;
import com.sportArea.service.BasketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/baskets")
public class BasketController {

    Logger logger= LoggerFactory.getLogger(BasketController.class);
    private final GuestUser guestUser;
    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService,GuestUser guestUser) {
        this.basketService = basketService;
        this.guestUser=guestUser;
    }

    @GetMapping("/{id}")
    public BasketDTO findById(@PathVariable("id") Long id) {


        return basketService.findById(id);

    }

    @GetMapping("/products/{guestId}")
    public List<BasketDTO> findByGuestId(@PathVariable("guestId") Long guestId) {

        return basketService.findByGuestId(guestId);
    }


    @PostMapping
    public ResponseEntity<String> addedBasket(@RequestBody BasketDTO basket) {
        basketService.save(basket);
        return ResponseEntity.ok("Product was added in basket successfully.");
    }


    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deleteBasket(@PathVariable("id") Long id) {
        basketService.deleteByGuestId(id);
        return ResponseEntity.ok("Baskets delete successfully.");

    }

    @GetMapping("/guest/id")
    public ResponseEntity<GuestDTO>  guestId(){
        Random random = new Random();
        Long guestId=random.nextLong(1,1000);
      while(guestUser.containsGuestId(guestId)) {
          guestId=random.nextLong(1,1000);
      }
        if(!guestUser.containsGuestId(guestId)) {
            guestUser.setGuestId(guestId);
        }else{
           logger.info("This guestId: {} is already exist {} ", guestId, HttpStatus.BAD_REQUEST.name());
        }
        Set<Long> list = guestUser.getGuestIdList();
        logger.info("List guestId: {} is already exist {} ", list, HttpStatus.CREATED.name());

        GuestDTO guest = new GuestDTO(guestId, Role.ROLE_GUEST);

      return ResponseEntity.ok(guest);
    }

    @DeleteMapping ("/guest/{guestId}")
    public ResponseEntity<?> deleteGuestId(@PathVariable("guestId") Long guestId){

        if (guestUser.containsGuestId(guestId)) {
            guestUser.deleteGuestId(guestId);
        } else {
            logger.info("This guestId: {} is not exist {}", guestId, HttpStatus.BAD_REQUEST.name());
            return new ResponseEntity<>("This guestId: " + guestId + " is not exist ", HttpStatus.BAD_REQUEST);
        }

        Set<Long> list = guestUser.getGuestIdList();
        logger.info("List guestId: {} is already exist {} ", list, HttpStatus.OK.name());
        return new ResponseEntity<>("guestId was delete successfully.", HttpStatus.OK);
    }

}
