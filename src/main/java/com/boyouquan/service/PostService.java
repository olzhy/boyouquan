package com.boyouquan.service;

import com.boyouquan.model.BlogDomainNamePublish;
import com.boyouquan.model.Post;
import com.boyouquan.util.Pagination;

import java.util.Date;
import java.util.List;

public interface PostService {

    BlogDomainNamePublish getMostPublishedInLatestOneMonth();

    Pagination<Post> listWithKeyWord(String keyword, int page, int size);

    Long countAll();

    Date getLatestPublishedAtByBlogDomainName(String blogDomainName);

    Long countByBlogDomainName(String blogDomainName);

    List<Post> listByDraftAndBlogDomainName(boolean draft, String blogDomainName, int limit);

    boolean existsByLink(String link);

    Post getByLink(String link);

    boolean batchSave(List<Post> posts);

    boolean batchUpdateDraftByBlogDomainName(String blogDomainName, boolean draft);

    void deleteByBlogDomainName(String blogDomainName);

}
