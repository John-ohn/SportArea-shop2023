package com.sportArea.service.Imp;

import com.sportArea.entity.dto.novaPost.Data;
import com.sportArea.entity.dto.novaPost.MethodProperties;
import com.sportArea.entity.dto.novaPost.PostRequest;
import com.sportArea.entity.dto.novaPost.PostResponse;
import com.sportArea.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FileParser fileParser;

    @Value("${nova.post.url}")
    private String urlNovaPost;

    @Value("${nova.post.private.key}")
    private String prKeyNovaPost;


    public List<PostResponse> writeToFileRegions() throws IOException {

        Set<PostResponse> listCite = new TreeSet<>();
        MethodProperties methodProperties = new MethodProperties();
        methodProperties.setLimit("200");


        PostRequest request = new PostRequest();
        request.setApiKey(prKeyNovaPost);
        request.setModelName("Address");
        request.setCalledMethod("getSettlements");
        request.setMethodProperties(methodProperties);

        int count = 1;
        boolean next = true;

        while (next) {
            methodProperties.setPage(Integer.toString(count));
//            request.setMethodProperties(methodProperties);
            ResponseEntity<Data> responseEntity = restTemplate.postForEntity(urlNovaPost, request, Data.class);


            Set<PostResponse> list = new HashSet<>(responseEntity
                    .getBody()
                    .getData());

            if (!list.isEmpty()) {
                listCite.addAll(list);
                System.out.println(listCite);
                count++;
            } else {
                next = false;
            }
        }

        TreeMap<String, String> convertToMap = fileParser.convertToMap(listCite);
        fileParser.writeToFile("regions2.txt", convertToMap);

        return new ArrayList<>(listCite);
    }

    @Override
    public List<String> getRegions() throws IOException {

        List<String> list = fileParser.getRegion().keySet().stream().toList();

        return list;
    }


    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        java.util.Map<Object, Boolean> seen = new java.util.concurrent.ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Override
    public Set<String> getCity(String regionName, String findByString) {

        Stream<Map.Entry<String, String>> entryStream = fileParser.getRegion().entrySet().stream();
        Optional<String> ref = entryStream
                .filter(a -> a.getKey().equals(regionName))
                .map(Map.Entry::getValue)
                .findFirst();

        Set<String> listCite = new TreeSet<>();
        MethodProperties methodProperties = new MethodProperties();
//        methodProperties.setPage(pageNumber);
        methodProperties.setLimit("80");
        ref.ifPresent(methodProperties::setAreaRef);
        if (!findByString.isEmpty()) {
            methodProperties.setFindByString(findByString);
        }

        PostRequest request = new PostRequest();
        request.setApiKey(prKeyNovaPost);
        request.setModelName("Address");
        request.setCalledMethod("getSettlements");
        request.setMethodProperties(methodProperties);

        int count = 1;
        boolean next = true;
        while (next) {

            methodProperties.setPage(Integer.toString(count));
            ResponseEntity<Data> responseEntity = restTemplate.postForEntity(urlNovaPost, request, Data.class);

            Set<String> list = responseEntity.getBody().getData().stream().map(PostResponse::getDescription).collect(Collectors.toSet());
            if (!list.isEmpty()) {
                listCite.addAll(list);

                count++;
            } else {
                next = false;
            }

        }


        return listCite;
    }

    @Override
    public Set<String> getDepartment(String citeName) {

        Set<String> listCite = new TreeSet<>();
        MethodProperties methodProperties = new MethodProperties();
        methodProperties.setLimit("400");
        methodProperties.setCityName(citeName);
        methodProperties.setLanguage("UA");
        methodProperties.setTypeOfWarehouseRef("9a68df70-0267-42a8-bb5c-37f427e36ee4");


        PostRequest request = new PostRequest();
        request.setApiKey(prKeyNovaPost);
        request.setModelName("Address");
        request.setCalledMethod("getWarehouses");
        request.setMethodProperties(methodProperties);
//
//        MethodProperties methodProperties2= new MethodProperties();
//        methodProperties.setLimit("500");
//        methodProperties.setCityName(citeName);
//        methodProperties.setLanguage("UA");
//        methodProperties.setTypeOfWarehouseRef("95dc212d-479c-4ffb-a8ab-8c1b9073d0bc");
//
//        RegionRequest request2 = new RegionRequest();
//        request2.setApiKey("3cf5a9f115e270e82e38581272625187");
//        request2.setModelName("Address");
//        request2.setCalledMethod("getWarehouses");
//        request2.setMethodProperties(methodProperties2);

        int count = 1;
        boolean next = true;
        while (next) {
            methodProperties.setTypeOfWarehouseRef("9a68df70-0267-42a8-bb5c-37f427e36ee4");
            methodProperties.setPage(Integer.toString(count));
//            methodProperties2.setPage(Integer.toString(count));

            ResponseEntity<Data> responseEntity = restTemplate.postForEntity(urlNovaPost, request, Data.class);
//            ResponseEntity<Data> responseEntity2 = restTemplate.postForEntity(url, request2, Data.class);
            Set<String> list = responseEntity.getBody()
                    .getData()
                    .stream()
                    .map(PostResponse::getDescription)
                    .collect(Collectors.toSet());

//            Set<String> list2 = responseEntity2.getBody().getData().stream().map(RegionResponse::getDescription).collect(Collectors.toSet());
            if (!list.isEmpty()) {
                listCite.addAll(list);
                System.out.println(listCite);
            }
            methodProperties.setTypeOfWarehouseRef("841339c7-591a-42e2-8233-7a0a00f0ed6f");
            ResponseEntity<Data> responseEntity2 = restTemplate.postForEntity(urlNovaPost, request, Data.class);
            Set<String> list2 = responseEntity2.getBody()
                    .getData()
                    .stream()
                    .map(PostResponse::getDescription)
                    .collect(Collectors.toSet());

            if (!list2.isEmpty()) {
                listCite.addAll(list2);
                System.out.println(listCite);
            }

            if (list.isEmpty() && list2.isEmpty()) {
                next = false;
            }
            count++;


        }

        return listCite;
    }


    @Override
    public Set<String> getPoshtomat(String citeName) {

        Set<String> listCite = new TreeSet<>();
        MethodProperties methodProperties = new MethodProperties();
        methodProperties.setLimit("400");
        methodProperties.setCityName(citeName);
        methodProperties.setLanguage("UA");
        methodProperties.setTypeOfWarehouseRef("f9316480-5f2d-425d-bc2c-ac7cd29decf0");


        PostRequest request = new PostRequest();
        request.setApiKey(prKeyNovaPost);
        request.setModelName("Address");
        request.setCalledMethod("getWarehouses");
        request.setMethodProperties(methodProperties);

        int count = 1;
        boolean next = true;
        while (next) {
            methodProperties.setTypeOfWarehouseRef("f9316480-5f2d-425d-bc2c-ac7cd29decf0");
            methodProperties.setPage(Integer.toString(count));
//            methodProperties2.setPage(Integer.toString(count));

            ResponseEntity<Data> responseEntity = restTemplate.postForEntity(urlNovaPost, request, Data.class);
//            ResponseEntity<Data> responseEntity2 = restTemplate.postForEntity(url, request2, Data.class);
            Set<String> list = responseEntity.getBody()
                    .getData()
                    .stream()
                    .map(PostResponse::getDescription)
                    .collect(Collectors.toSet());

//            Set<String> list2 = responseEntity2.getBody().getData().stream().map(RegionResponse::getDescription).collect(Collectors.toSet());
            if (!list.isEmpty()) {
                listCite.addAll(list);
                System.out.println(listCite);
            }
            methodProperties.setTypeOfWarehouseRef("95dc212d-479c-4ffb-a8ab-8c1b9073d0bc");
            ResponseEntity<Data> responseEntity2 = restTemplate.postForEntity(urlNovaPost, request, Data.class);
            Set<String> list2 = responseEntity2.getBody()
                    .getData()
                    .stream()
                    .map(PostResponse::getDescription)
                    .collect(Collectors.toSet());

            if (!list2.isEmpty()) {
                listCite.addAll(list2);
                System.out.println(listCite);
            }

            if (list.isEmpty() && list2.isEmpty()) {
                next = false;
            }
            count++;


        }

        return listCite;
    }



}
