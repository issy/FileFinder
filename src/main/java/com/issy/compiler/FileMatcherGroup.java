package com.issy.compiler;

import java.util.List;

public record FileMatcherGroup(GroupMode groupMode, List<FileMatcher> fileMatchers) {

    public enum GroupMode {
        AND,
        OR
    }

    public FileMatcher compile() {
        return switch (groupMode) {
            case AND:
                yield filepath -> fileMatchers.stream().allMatch(fileMatcher -> fileMatcher.matches(filepath));
            case OR:
                yield filepath -> fileMatchers.stream().anyMatch(fileMatcher -> fileMatcher.matches(filepath));
        };
    }

}
