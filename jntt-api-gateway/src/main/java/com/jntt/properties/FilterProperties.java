package com.jntt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@Data
@ConfigurationProperties(prefix = "jn.filter")
public class FilterProperties {

    private List<String> allowPaths;


}
