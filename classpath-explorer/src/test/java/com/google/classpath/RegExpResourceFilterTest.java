package com.google.classpath;

import org.junit.jupiter.api.Test;

import static com.google.classpath.RegExpResourceFilter.ANY;
import static org.assertj.core.api.Assertions.assertThat;

class RegExpResourceFilterTest {

    @Test
    void any_package_name_and_any_resource_name_matches_everything() {
        RegExpResourceFilter filter = new RegExpResourceFilter(ANY, ANY);

        boolean matches = filter.match("X", "X");

        assertThat(matches).isTrue();
    }

    @Test
    void specific_package_name_and_any_resource_name_matches_any_resource_with_same_package() {
        RegExpResourceFilter filter = new RegExpResourceFilter("my_package", ANY);

        boolean samePackage = filter.match("my_package", "X");
        boolean differentPackage = filter.match("X", "X");

        assertThat(samePackage).isTrue();
        assertThat(differentPackage).isFalse();
    }

    @Test
    void specific_package_name_and_any_resource_name_does_not_match_when_package_differs() {
        RegExpResourceFilter filter = new RegExpResourceFilter("my_package", ANY);

        boolean samePackage = filter.match("my_package", "X");
        boolean differentPackage = filter.match("X", "X");

        assertThat(samePackage).isTrue();
        assertThat(differentPackage).isFalse();
    }

    @Test
    void any_package_name_and_specific_resource_name_matches_any_package_with_same_resource() {
        RegExpResourceFilter filter = new RegExpResourceFilter(ANY, "my_resource");

        boolean differentResource = filter.match("X", "X");
        boolean sameResource = filter.match("X", "my_resource");

        assertThat(differentResource).isFalse();
        assertThat(sameResource).isTrue();
    }

    @Test
    void specific_package_name_and_resource_match_only_exact_match() {
        RegExpResourceFilter filter = new RegExpResourceFilter("my_package_name", "my_resource");

        boolean matches = filter.match("my_package_name", "my_resource");

        assertThat(matches).isTrue();
    }
}
