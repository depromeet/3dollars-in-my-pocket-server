package com.depromeet.team5.domain;

import lombok.Value;

import java.io.InputStream;

@Value(staticConstructor = "of")
public class ImageUploadValue {
    InputStream inputStream;
    String filename;
    String contentType;
}
