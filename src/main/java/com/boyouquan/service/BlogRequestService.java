package com.boyouquan.service;

import com.boyouquan.model.BlogRequest;
import com.boyouquan.model.BlogRequestInfo;

import java.util.List;

public interface BlogRequestService {

    void processNewRequest(String rssAddress);

    List<BlogRequestInfo> listBlogRequestInfosBySelfSubmittedAndStatuses(boolean selfSubmitted, List<BlogRequest.Status> statuses);

    List<BlogRequestInfo> listBlogRequestInfosByStatuses(List<BlogRequest.Status> statuses);

    BlogRequestInfo getBlogRequestInfoById(Long id);

    List<BlogRequest> listByStatus(BlogRequest.Status status);

    BlogRequest getById(Long id);

    BlogRequest getByRssAddress(String rssAddress);

    void approveById(Long id);

    void rejectById(Long id, String reason);

    void update(BlogRequest blogRequest);

    void submit(BlogRequest blogRequest);

    void deleteByRssAddress(String rssAddress);

}