package com.depromeet.team5.dto;

import com.depromeet.team5.domain.Menu;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpdateDto {
    private Float latitude;

    private Float longitude;

    private String storeName;

    private List<MultipartFile> image;

    private List<Menu> menu;
}
