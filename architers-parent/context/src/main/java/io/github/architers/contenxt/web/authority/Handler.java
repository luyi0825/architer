package io.github.architers.contenxt.web.authority;


import io.github.architers.contenxt.exception.PermissionException;
import org.springframework.http.HttpRequest;
import org.springframework.util.CollectionUtils;

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
