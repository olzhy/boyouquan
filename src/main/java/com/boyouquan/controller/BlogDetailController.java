package com.boyouquan.controller;

import com.boyouquan.model.DayAccess;
import com.boyouquan.model.NewBlogInfo;
import com.boyouquan.service.BlogAccessService;
import com.boyouquan.service.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/blogs")
public class BlogDetailController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogAccessService blogAccessService;

    @GetMapping("/{domainName}/**")
    public String list(@PathVariable("domainName") String domainName, Model model, HttpServletRequest request) {
        // parse domain from request URL
        String requestURL = request.getRequestURL().toString();
        domainName = requestURL.split("/blogs/")[1];

        // get blog info
        NewBlogInfo blogInfo = blogService.getBlogInfoByDomainName(domainName);
        if (null == blogInfo) {
            return "error/404";
        }

        model.addAttribute("blogInfo", blogInfo);

        // for charts
        List<DayAccess> dayAccessList = blogAccessService.getBlogAccessSeriesInLatestOneMonth(blogInfo.getAddress());
        String[] monthlyAccessDataLabels = dayAccessList.stream().map(DayAccess::getDay).toArray(String[]::new);

        Integer[] monthlyAccessDataValues = dayAccessList.stream().map(DayAccess::getCount).toArray(Integer[]::new);

        model.addAttribute("monthlyAccessDataLabels", monthlyAccessDataLabels);
        model.addAttribute("monthlyAccessDataValues", monthlyAccessDataValues);

        // for summary
        // FIXME
        model.addAttribute("totalBlogs", 0L);
        model.addAttribute("totalBlogPosts", 0L);
        model.addAttribute("accessTotal", 0L);

        return "blog_detail/item";
    }

}
