package com.depromeet.team5.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.depromeet.team5.domain.ImageUploadValue;
import com.depromeet.team5.exception.FailedToUploadImageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
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

    public String upload(ImageUploadValue imageUploadValue) {
        String savedFileName = getSavedFileName(imageUploadValue.getFilename());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imageUploadValue.getContentType());
        try (InputStream inputStream = imageUploadValue.getInputStream()) {
            amazonS3Client.putObject(bucketName, savedFileName, inputStream, metadata);
        } catch (IOException e) {
            log.error("Failed to upload image", e);
            throw new FailedToUploadImageException();
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
