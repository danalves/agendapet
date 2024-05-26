package com.danalves.agendapet.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.danalves.agendapet.dto.AnimalReceived;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
public class S3Service {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.key}")
    private String fileKey;

    public List readJsonFromS3(JavaType type) throws IOException {
        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileKey);
            S3Object s3Object = s3Client.getObject(getObjectRequest);
            try (Reader reader = new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.UTF_8)) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.getFactory().disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
                return objectMapper.readValue(reader, type);
            }
        } catch (AmazonS3Exception e) {
            if (e.getStatusCode() == 404) {
                log.info("FileKey {} não existe no bucket {}. Criando novo arquivo.", fileKey, bucketName);
                return null;
            } else {
                throw e;
            }
        }
    }

    public void updateJsonInS3(Object data) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(data);
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);

        File file = File.createTempFile("temp", ".json");
        FileOutputStream outputStream = new FileOutputStream(file);

        try {
            outputStream.write(jsonBytes);
        } catch (IOException e) {
            log.error("Error writing to file: {}", e.getMessage());
        }

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileKey, file);
        s3Client.putObject(putObjectRequest);

        outputStream.close();
        file.deleteOnExit();
    }
}