package com.apple.weatherforecastservice.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;


/**
 * Custom rest template config
 *
 * @author sroy
 */
public class CustomRestTemplatePoolConfig {
    /**
     * Configurations related to connection pooling.
     */
    private PoolConfig pool;
    /**
     * Time to wait to establish a connection to host.
     */
    private int requestConnectTimeoutMillis;
    /**
     * Time to wait for data to be read from socket
     */
    private int requestSocketTimeoutMillis;
    /**
     * Time to wait for a a connection from pool
     */
    private int requestConnectionRequestTimeoutMillis;

    private int evictConnectionIdleTime;

    public PoolConfig getPool() {
        return pool;
    }

    public void setPool(PoolConfig pool) {
        this.pool = pool;
    }

    public int getRequestConnectTimeoutMillis() {
        return requestConnectTimeoutMillis;
    }

    public void setRequestConnectTimeoutMillis(int requestConnectTimeoutMillis) {
        this.requestConnectTimeoutMillis = requestConnectTimeoutMillis;
    }

    public int getRequestSocketTimeoutMillis() {
        return requestSocketTimeoutMillis;
    }

    public void setRequestSocketTimeoutMillis(int requestSocketTimeoutMillis) {
        this.requestSocketTimeoutMillis = requestSocketTimeoutMillis;
    }

    public int getRequestConnectionRequestTimeoutMillis() {
        return requestConnectionRequestTimeoutMillis;
    }

    public void setRequestConnectionRequestTimeoutMillis(int requestConnectionRequestTimeoutMillis) {
        this.requestConnectionRequestTimeoutMillis = requestConnectionRequestTimeoutMillis;
    }

    public int getEvictConnectionIdleTime() {
        return evictConnectionIdleTime;
    }

    public void setEvictConnectionIdleTime(int evictConnectionIdleTime) {
        this.evictConnectionIdleTime = evictConnectionIdleTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("pool", pool)
            .append("requestConnectTimeoutMillis", requestConnectTimeoutMillis)
            .append("requestSocketTimeoutMillis", requestSocketTimeoutMillis)
            .append("requestConnectionRequestTimeoutMillis", requestConnectionRequestTimeoutMillis)
                .append("evictConnectionIdleTime", evictConnectionIdleTime)
            .toString();
    }

    public static class PoolConfig {
        /**
         * Total number of connections cached in pool.
         */
        private int maxTotal;
        /**
         * The default maximum amount of connections per endpoint.
         */
        private int defaultMaxPerRoute;

        public int getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }

        public int getDefaultMaxPerRoute() {
            return defaultMaxPerRoute;
        }

        public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
            this.defaultMaxPerRoute = defaultMaxPerRoute;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .append("maxTotal", maxTotal)
                .append("defaultMaxPerRoute", defaultMaxPerRoute)
                .toString();
        }
    }

    public static ClientHttpRequestFactory createRequestFactory(CustomRestTemplatePoolConfig configuration) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(configuration.getPool().getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(configuration.getPool().getDefaultMaxPerRoute());

        RequestConfig config = RequestConfig.copy(RequestConfig.DEFAULT)
            .setConnectTimeout(configuration.getRequestConnectTimeoutMillis())
            .setSocketTimeout(configuration.getRequestSocketTimeoutMillis())
            .setConnectionRequestTimeout(configuration.getRequestConnectionRequestTimeoutMillis())
            .build();

        // Added connection  eviction time for idle/stale connections to resolve connection reset error
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config)
                .build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
}
