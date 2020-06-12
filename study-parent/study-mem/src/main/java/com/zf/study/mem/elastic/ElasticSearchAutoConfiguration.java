package com.zf.study.mem.elastic;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpPost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ConditionalOnClass(ESClientSpringFactory.class)
@EnableConfigurationProperties({ElasticConfig.class})
public class ElasticSearchAutoConfiguration {

    @Bean(initMethod = "init",destroyMethod = "close")
    public ESClientSpringFactory esClientSpringFactory(ElasticConfig elasticConfig){

        try {
            final String[] hosts = elasticConfig.hosts;
            if (hosts == null || hosts.length == 0){
                final HttpHost httpHost = new HttpHost("localhost",9200,"http");
                return ESClientSpringFactory.build(new HttpHost[]{httpHost});
            }
            HttpHost[] httpHosts = Arrays.stream(hosts).map(HttpHost::create).toArray(HttpHost[]::new);
            return ESClientSpringFactory.build(httpHosts,
                    elasticConfig.getConnectTimeoutMillis(),
                    elasticConfig.getSocketTimeoutMillis(),
                    elasticConfig.getConnectionRequestTimeoutMillis(),
                    elasticConfig.getMaxConnTotal(),
                    elasticConfig.getMaxConnPerRoute(),
                    elasticConfig.getUsername(),
                    elasticConfig.getPassword()
            );
        } finally {
            System.out.println("init Elasticsearch complete");
        }
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(ESClientSpringFactory esClientSpringFactory) {
        return esClientSpringFactory.getRhlClient();
    }

    @Bean
    public RestClient restClient(ESClientSpringFactory esClientSpringFactory) {
        return esClientSpringFactory.getClient();
    }


}
