package com.architecture.context.web.authority;


import com.architecture.context.exception.PermissionException;
import org.springframework.http.HttpRequest;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.AnnotatedElement;
import java.util.Set;

public class Handler {
    private AuthorityLoader authorityLoader;

    public void hander(Authority authority, HttpRequest request) {
        Set<String> sets = authorityLoader.load(request);
        if (CollectionUtils.isEmpty(sets)) {
            throw new PermissionException();
        }
        for (String s : authority.value()) {
            if (sets.contains(s)) {
                return;
            }
        }
        throw new PermissionException();

    }
}
