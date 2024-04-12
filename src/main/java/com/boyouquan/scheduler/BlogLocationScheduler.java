package com.boyouquan.scheduler;

import com.boyouquan.helper.IPInfoHelper;
import com.boyouquan.model.Blog;
import com.boyouquan.model.BlogLocation;
import com.boyouquan.service.BlogLocationService;
import com.boyouquan.service.BlogService;
import com.boyouquan.service.BlogStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class BlogLocationScheduler {

    private final Logger logger = LoggerFactory.getLogger(BlogLocationScheduler.class);

    @Autowired
    private IPInfoHelper ipInfoHelper;
    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogStatusService blogStatusService;
    @Autowired
    private BlogLocationService blogLocationService;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void refreshBlogLocationsJob() {
        logger.info("blog location scheduler start!");

        refreshBlogLocations();

        logger.info("blog location scheduler end!");
    }

    private void refreshBlogLocations() {
        List<Blog> blogs = blogService.listAll();
        for (Blog blog : blogs) {
            try {
                String blogDomainName = blog.getDomainName();
                boolean statusOK = blogStatusService.isStatusOkByBlogDomainName(blogDomainName);

                if (statusOK) {
                    logger.info("process for {}", blogDomainName);

                    BlogLocation blogLocation = ipInfoHelper.getIpInfoByDomainName(blogDomainName);
                    if (null != blogLocation) {
                        boolean exists = blogLocationService.existsByDomainName(blogDomainName);
                        if (exists) {
                            BlogLocation blogLocationStored = blogLocationService.getByDomainName(blogDomainName);
                            blogLocationStored.setLocation(blogLocation.getLocation());
                            blogLocationStored.setIsp(blogLocation.getIsp());
                            blogLocationService.update(blogLocationStored);
                        } else {
                            blogLocationService.save(blogLocation);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("refresh failed", e);
            }
        }
    }

}

