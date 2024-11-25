package com.issy.compiler;

import java.util.List;
import java.util.function.Predicate;

public record FileMatcherGroup(GroupMode groupMode, List<FileMatcher> fileMatchers) {

    public enum GroupMode {
        AND,
        OR
    }

    public Predicate<String> compile() {
        var predicate = fileMatchers.stream().reduce((a, b) -> (FileMatcher) a.and(b));

        return switch (groupMode) {
            case AND:
                yield filepath -> fileMatchers.stream().reduce(fileMatcher -> fileMatcher.test(filepath));
            case OR:
                yield filepath -> fileMatchers.stream().anyMatch(fileMatcher -> fileMatcher.test(filepath));
        };
    }

}
