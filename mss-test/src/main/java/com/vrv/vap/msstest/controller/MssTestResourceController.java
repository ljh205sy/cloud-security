package com.vrv.vap.msstest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author liujinhui
 * date 2021/3/19 13:22
 */
@RestController
public class MssTestResourceController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/testRes")
    public String getTestResource(Principal principal) {
        logger.info("权限判别,principal:" + principal);
        return "abcdeft";
    }

    @RequestMapping("/testauth")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public String getDemo() {
        return "good";
    }

    @RequestMapping("/xxx")
    @PreAuthorize("hasAuthority('ROLE_xxx')")
    public String getXXX() {
        return "XXX";
    }
}
