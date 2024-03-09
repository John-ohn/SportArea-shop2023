package com.sportArea.service.Imp;

import com.sportArea.dao.BannerRepository;
import com.sportArea.entity.Banner;
import com.sportArea.entity.dto.BlogDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.BannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BannerServiceImp implements BannerService {

    Logger logger = LoggerFactory.getLogger(BannerServiceImp.class);

    private final BannerRepository bannerRepository;

    @Autowired
    public BannerServiceImp(BannerRepository bannerRepository){
        this.bannerRepository=bannerRepository;
    }

    @Override
    public List<Banner> findAll(){

        List<Banner> bannerList = bannerRepository.findAll();

        if (!bannerList.isEmpty()) {
            logger.info("From BannerServiceImp method -findAll- return List of Banners.");

            return bannerList;
        } else {
            logger.warn("From BannerServiceImp method -findAll- send war message " +
                    "(Don't find any Banner. Banners list is empty. ({}))", HttpStatus.NOT_FOUND);
            throw new GeneralException("Don't find any Banner. Banners list is empty.", HttpStatus.NOT_FOUND);
        }
    }


}
