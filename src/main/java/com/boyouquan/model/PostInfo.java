package com.boyouquan.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostInfo extends Post {

    private String blogName;
    private String blogAddress;
    private Long linkAccessCount;
    private String blogAdminMediumImageURL;

}
