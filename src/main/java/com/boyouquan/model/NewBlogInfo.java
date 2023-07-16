package com.boyouquan.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewBlogInfo extends Blog {

    private Long postsCount;
    private Long accessCount;
    private Date latestUpdatedAt;
    private List<BlogPost> latestPosts = Collections.emptyList();
    private List<BlogPost> posts = Collections.emptyList();

}
