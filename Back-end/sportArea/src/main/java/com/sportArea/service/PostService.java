package com.sportArea.service;

import com.sportArea.entity.dto.novaPost.PostResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PostService {




    List<String> getRegions() throws IOException;

    Set<String> getCity(String regionName, String findByString) throws IOException;

    Set<String> getDepartment(String citeName);

    Set<String> getPoshtomat(String citeName);
}
