package com.oops.major.socialMediaPlatform.model;

import lombok.Getter;
import lombok.Setter;

//import javax.persistence.*;
import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Post extends Base{

    private String postBody;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

}
