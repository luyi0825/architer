package io.github.architers.context.webmvc.authority;

import org.springframework.http.HttpRequest;

import java.util.Set;

public interface AuthorityLoader {

    Set<String> load(HttpRequest httpRequest);

}
