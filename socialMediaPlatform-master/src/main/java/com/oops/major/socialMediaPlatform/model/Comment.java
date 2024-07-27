package com.oops.major.socialMediaPlatform.model;


import lombok.Getter;
import lombok.Setter;

//import javax.persistence.*;
import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class Comment extends Base {

    private String commentBody;

    @ManyToOne()
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private Users user;

}
