package com.yy.stock.bot.lazadaapibot;

//@Configuration
public class RestTemplateConfig {
//    private final AddHeaderInterceptor addHeaderInterceptor;
//    private final LogInterceptor logInterceptor;
//
//    public RestTemplateConfig(AddHeaderInterceptor addHeaderInterceptor, LogInterceptor logInterceptor) {
//        this.logInterceptor = logInterceptor;
//        this.addHeaderInterceptor = addHeaderInterceptor;
//    }
//
//    //    @Bean
////    @ConditionalOnMissingBean(RestTemplate.class)
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
//        List<ClientHttpRequestInterceptor> list = new ArrayList<ClientHttpRequestInterceptor>();
//        list.add(addHeaderInterceptor);
//        list.add(logInterceptor);
//        restTemplate.setInterceptors(list);
//        return restTemplate;
//    }
//
//    private ClientHttpRequestFactory getClientHttpRequestFactory() {
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(5, TimeUnit.SECONDS)
//                .build();
//        return new OkHttp3ClientHttpRequestFactory(okHttpClient);
//    }
}
