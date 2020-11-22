package com.depromeet.team5.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public String upload(MultipartFile multipartFile) throws IOException {
        String savedFileName = getSavedFileName(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            File file = convertMultiPartToFile(multipartFile);
            uploadFileToS3(savedFileName, file);
            file.delete();
        } catch (StringIndexOutOfBoundsException e) {
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

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private void uploadFileToS3(final String fileName, final File file) {
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
        } catch(AmazonServiceException e) {
            log.error(e.getMessage());
        }
    }
}
