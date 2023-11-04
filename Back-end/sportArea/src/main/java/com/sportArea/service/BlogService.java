package com.sportArea.service;

import com.sportArea.entity.dto.BlogDTO;

import java.util.List;

public interface BlogService {

    List<BlogDTO> findAll();

    BlogDTO findById(Long blogId);
}
