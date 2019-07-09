package com.qin.clond.feign.one.web;


import com.netflix.client.RequestSpecificRetryHandler;
import com.netflix.client.config.IClientConfig;
import com.qin.clond.feign.one.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    @Qualifier("clientService")
    private ClientService clientService;

    /**
     * feign:
     *   client:
     *     config:
     *       default:           #服务名，填写default为所有服务
     *         connectTimeout: 10000
     *         readTimeout: 10000
     * 配置这个属性后
     * 关键代码： LoadBalancerFeignClient
     *  IClientConfig getClientConfig(Request.Options options, String clientName) {
     * 		IClientConfig requestConfig;
     * 		if (options == DEFAULT_OPTIONS) {
     * 			requestConfig = this.clientFactory.getClientConfig(clientName);
     *      } else {
     * 			requestConfig = new FeignOptionsClientConfig(options);
     *      }
     * 		return requestConfig;
     * 	}
     * 将使用 FeignOptionsClientConfig 这个作为配置属性
     * 构造器里默认只有这两个属性生效：connectTimeout 和 readTimeout
     * requestConfig = new FeignOptionsClientConfig(options);
     *
     * 重试的关键属性 MaxRetriesOnSameServer 和 MaxRetriesOnNextServer 都默认是 0 ;
     *
     * 如果没有配置 feign.client.config:
     *
     * 就默认使用 RibbonClientConfiguration 类的
     *    @Bean
     *    @ConditionalOnMissingBean
     *    public IClientConfig ribbonClientConfig() {
     * 		DefaultClientConfigImpl config = new DefaultClientConfigImpl();
     * 		config.loadProperties(this.name);
     * 		config.set(CommonClientConfigKey.ConnectTimeout, DEFAULT_CONNECT_TIMEOUT);
     * 		config.set(CommonClientConfigKey.ReadTimeout, DEFAULT_READ_TIMEOUT);
     * 		config.set(CommonClientConfigKey.GZipPayload, DEFAULT_GZIP_PAYLOAD);
     * 		return config;
     *    }
     * 重试的关键属性 MaxRetriesOnSameServer 默认 0 , MaxRetriesOnNextServer 默认是 1 ;
     *
     * MaxRetriesOnNextServer 默认 1 导致 超时重试的关键
     *
     * RibbonClientConfiguration里面的属性的默认值会被下面的属性覆盖
     *  ribbon:
     *   OkToRetryOnAllOperations: false
     *   SocketTimeout: 16000
     *   ReadTimeout: 16000
     *   ConnectTimeout: 16000
     *   # 同一台实例重试次数，不包括首次调用
     *   MaxAutoRetries: 0
     *   # 其他负载均衡实例重试次数，不包括首次server调用
     *   MaxAutoRetriesNextServer: 0
     *
     * 重试处理器： AbstractLoadBalancingClient
     *  RequestSpecificRetryHandler handler = getRequestSpecificRetryHandler(request, config);
     *  config 就是上面的配置类
     @Override
     public RequestSpecificRetryHandler getRequestSpecificRetryHandler(final S request,
        final IClientConfig requestConfig) {
        if (this.okToRetryOnAllOperations) {
            return new RequestSpecificRetryHandler(true, true, this.getRetryHandler(), requestConfig);
        }
        if (!request.getContext().getMethod().equals("GET")) {
            return new RequestSpecificRetryHandler(true, false, this.getRetryHandler(), requestConfig);
        } else {
            return new RequestSpecificRetryHandler(true, true, this.getRetryHandler(), requestConfig);
        }
     }

     this.getRetryHandler() = = new DefaultLoadBalancerRetryHandler();
     里面
     public DefaultLoadBalancerRetryHandler() {
         this.retrySameServer = 0;
         this.retryNextServer = 0;
         this.retryEnabled = false;
     }

     public RequestSpecificRetryHandler(boolean okToRetryOnConnectErrors, boolean okToRetryOnAllErrors, RetryHandler baseRetryHandler, @Nullable IClientConfig requestConfig) {
         Preconditions.checkNotNull(baseRetryHandler);
         this.okToRetryOnConnectErrors = okToRetryOnConnectErrors;
         this.okToRetryOnAllErrors = okToRetryOnAllErrors;
         this.fallback = baseRetryHandler;
         if (requestConfig != null) {
            if (requestConfig.containsProperty(CommonClientConfigKey.MaxAutoRetries)) {
                retrySameServer = requestConfig.get(CommonClientConfigKey.MaxAutoRetries);
            }
             if (requestConfig.containsProperty(CommonClientConfigKey.MaxAutoRetriesNextServer)) {
                retryNextServer = requestConfig.get(CommonClientConfigKey.MaxAutoRetriesNextServer);
             }
         }
     }

     RequestSpecificRetryHandler 里面的超时重试的关键属性 retryNextServer = 就是从配置类获取

     LoadBalancerCommand.submit() 重试
     关键参数：
     final int maxRetrysSame = retryHandler.getMaxRetriesOnSameServer();
     final int maxRetrysNext = retryHandler.getMaxRetriesOnNextServer();

     */

    @RequestMapping(value = "/sample")
    public String home(@RequestParam String name) {
        long start = System.currentTimeMillis();
        String result = clientService.sample(name);
        long end = System.currentTimeMillis();
        System.out.println("========= feigin run :" + (end - start) + " =========");
        return result;
    }
}
