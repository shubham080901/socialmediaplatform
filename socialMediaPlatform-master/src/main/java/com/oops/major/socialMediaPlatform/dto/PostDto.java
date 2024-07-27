package com.oops.major.socialMediaPlatform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDto {
    private int postID;

    private String postBody;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private List<CommentDto> comments;
}
