package com.sportArea.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Component
@Data
public final class GuestUser {

    private Set<Long> guestIdList= new TreeSet<>();

    private GuestUser(){

    }

    private GuestUser(Set<Long> guestIdList){
        this.guestIdList=guestIdList;
    }

    public boolean containsGuestId(Long guestId){
        return this.guestIdList.contains(guestId);
    }

    public void setGuestId(Long guestId ){
        guestIdList.add(guestId);
    }

    public void deleteGuestId(Long guestId){
        guestIdList.remove(guestId);
    }

}
