package com.sportArea.controller;

import com.sportArea.entity.GuestUser;
import com.sportArea.entity.Role;
import com.sportArea.entity.dto.BasketDTO;
import com.sportArea.entity.dto.BasketItemDTO;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.service.BasketService;
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

    private final GeneralLogg generalLogg;

    //    private final GuestUser guestUser;
    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService, GeneralLogg generalLogg) {
        this.basketService = basketService;
        this.generalLogg = generalLogg;
//        this.guestUser=guestUser;
    }

    @GetMapping("/{id}")
    public BasketDTO findById(@PathVariable("id") Long id) {

        BasketDTO basket = basketService.findByUserId(id);

        generalLogg.getLoggerControllerInfo("BasketController",
                "findById",
                "/baskets/{id}",
                " Basket of id " + id);

        return basket;


    }

//    @GetMapping("/products")
//    public List<BasketDTO> findByGuestId(@RequestParam("id")Long id){
////                                         @RequestParam("role") String role) {
//
//        return basketService.findByGuestId(id);
//    }


//    @PostMapping
//    public ResponseEntity<String> addedBasket(@RequestBody BasketItemDTO basketItem) {
//        basketService.save(basketItem);
//        return ResponseEntity.ok("Product was added in basket successfully.");
//    }

    @PostMapping
    public ResponseEntity<BasketDTO> addedBasket(@RequestParam("userId") Long userId,
                                                 @RequestParam("productId") Long productId,
                                                 @RequestParam("productQuantity") Integer productQuantity) {

        BasketDTO basketDTO = basketService.createUpdateBasket(userId, productId, productQuantity);

        generalLogg.getLoggerControllerInfo("BasketController",
                "addedBasket",
                "/baskets",
                " Basket and add item of basket with user id " + userId);

        return ResponseEntity.ok(basketDTO);
//        return ResponseEntity.ok("Product was added in basket successfully.");
    }

//    @DeleteMapping ("/{id}")
//    public ResponseEntity<String> deleteBasket(@PathVariable("id") Long id) {
//        basketService.deleteByGuestId(id);
//        return ResponseEntity.ok("Baskets delete successfully.");
//
//    }

//    @GetMapping("/guest/id")
//    public ResponseEntity<Customer>  getNewGuestId(){
//        Random random = new Random();
//        Long guestId=random.nextLong(1,1000);
//      while(guestUser.containsGuestId(guestId)) {
//          guestId=random.nextLong(1,1000);
//      }
//        if(!guestUser.containsGuestId(guestId)) {
//            guestUser.setGuestId(guestId);
//        }else{
//           logger.info("This guestId: {} is already exist {} ", guestId, HttpStatus.BAD_REQUEST.name());
//        }
//        Set<Long> list = guestUser.getGuestIdList();
//        logger.info("List guestId: {} is already exist {} ", list, HttpStatus.CREATED.name());
//
//        Customer customer = new Customer(guestId, Role.ROLE_GUEST);
//
//      return ResponseEntity.ok(customer);
//    }

//    @DeleteMapping ("/guest/{guestId}")
//    public ResponseEntity<?> deleteGuestId(@PathVariable("guestId") Long guestId){
//
//        if (guestUser.containsGuestId(guestId)) {
//            guestUser.deleteGuestId(guestId);
//        } else {
//            logger.info("This guestId: {} is not exist {}", guestId, HttpStatus.BAD_REQUEST.name());
//            return new ResponseEntity<>("This guestId: " + guestId + " is not exist ", HttpStatus.BAD_REQUEST);
//        }
//
//        Set<Long> list = guestUser.getGuestIdList();
//        logger.info("List guestId: {} is already exist {} ", list, HttpStatus.OK.name());
//        return new ResponseEntity<>("guestId was delete successfully.", HttpStatus.OK);
//    }

}
