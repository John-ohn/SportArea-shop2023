package com.sportArea.service.Imp;

import com.sportArea.entity.dto.novaPost.PostResponse;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Getter
public class FileParser {

    private final  TreeMap<String,String> region;


    public FileParser() throws IOException {
        region = parseFile("regions2.txt");
    }

    public  TreeMap<String, String> parseFile(String filePath) throws IOException {
        TreeMap<String, String> resultMap = new TreeMap<>();

        // Load the file from the classpath
        ClassPathResource resource = new ClassPathResource(filePath);
        InputStream inputStream = resource.getInputStream();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            String currentKey = null;
            while ((line = reader.readLine()) != null) {
                // Split the line into key and value using ":"
                String[] parts = StringUtils.split(line, ":");
                if (parts != null && parts.length == 2) {
                    // Trim to remove any leading or trailing whitespaces
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    if ("Area".equals(key)) {
                        // If key is "Area", store it as the current key
                        currentKey = value;
                    } else if ("AreaDescription".equals(key) && currentKey != null) {
                        // If key is "AreaDescription" and we have a current key, store the pair in the map
                        resultMap.put(currentKey, value);
                        currentKey = null; // Reset currentKey
                    }
                }
            }
        }

        return resultMap;
    }

    public  void writeToFile(String filePath, TreeMap<String, String> dataMap) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                // Write "Area" and its value
                writer.write(String.format("Area:%s", entry.getKey()));
                writer.newLine();
                // Write "AreaDescription" and its value
                writer.write(String.format("AreaDescription:%s", entry.getValue()));
                writer.newLine();
            }
        }
    }

    public  TreeMap<String, String> convertToMap(Set<PostResponse> postRespons) {

        return postRespons.stream()
                .collect(Collectors.toMap(PostResponse::getAreaDescription, PostResponse::getArea,
                        (existingValue, newValue) -> existingValue, TreeMap::new));

    }

}
