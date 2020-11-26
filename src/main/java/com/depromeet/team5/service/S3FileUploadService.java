package com.depromeet.team5.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileUploadService {

    @Value("${aws.credentials.accessKey}")
    private String accessKey;

    @Value("${aws.credentials.secretKey}")
    private String secretKey;

    @Value("${aws.credentials.region}")
    private String region;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.bucket.url}")
    private String defaultUrl;

    private AmazonS3 amazonS3Client;

    @PostConstruct
    private void init() {
       BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
       amazonS3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    public String upload(MultipartFile multipartFile) {
        String savedFileName = getSavedFileName(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        MediaType mediaType = MediaType.parseMediaType(multipartFile.getContentType());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(mediaType.toString());
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(bucketName, savedFileName, inputStream, metadata);
        } catch (IOException | StringIndexOutOfBoundsException | SdkClientException e) {
            log.error("Failed to upload file", e);
            return null;
        }
        return defaultUrl + savedFileName.replaceAll("/", "");
    }

    private static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private static String getSavedFileName(String origName) {
        return getUuid() + origName.substring(origName.lastIndexOf('.'));
    }
}
