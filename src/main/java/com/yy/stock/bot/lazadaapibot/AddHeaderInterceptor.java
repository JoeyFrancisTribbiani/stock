package com.yy.stock.bot.lazadaapibot;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Configuration
public class AddHeaderInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add("Host", "member.lazada.sg");
        headers.add("Connection", "keep-alive");
        headers.add("Content-Length", "85");
        headers.add("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"");
        headers.add("sec-ch-ua-mobile", "?0");
        headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
        headers.add("Content-Type", "application/json");
        headers.add("bx-sys", "ua-l:no__um-l:lazada__js:https://laz-g-cdn.alicdn.com/");
        headers.add("Accept", "application/json, text/plain, */*");
        headers.add("X-Requested-With", "XMLHttpRequest");
        headers.add("bx-v", "2.2.3");
        headers.add("sec-ch-ua-platform", "\"macOS\"");
        headers.add("Origin", "https://member.lazada.sg");
        headers.add("Sec-Fetch-Site", "same-origin");
        headers.add("Sec-Fetch-Mode", "cors");
        headers.add("Sec-Fetch-Dest", "empty");
        headers.add("Referer", "https://member.lazada.sg/user/login?spm=a2o42.home.header.d5.100546b5LfhQvN&redirect=https%3A%2F%2Fwww.lazada.sg%2F%23hp-flash-sale");
        headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("Accept-Language", "zh-CN,zh;q=0.9");
        headers.add("Cookie", "client_type=desktop; client_type=desktop; _uab_collina=167569835553632006748474; __wpkreporterwid_=9ebae07e-4fa4-42cb-1934-28359271b07a; lzd_cid=edab87d3-5f0d-424a-c304-7b0bea67f8d2; t_uid=edab87d3-5f0d-424a-c304-7b0bea67f8d2; hng=SG|en-SG|SGD|702; anon_uid=89b744bc14424d134f22c266b230380b; _tb_token_=ee04057b45eed; t_fv=1675698006818; cna=WA1oHDopYFQCAS1MsFPkeA57; _gcl_au=1.1.53033905.1675698009; xlly_s=1; _fbp=fb.1.1675698016640.168846977; AMCVS_126E248D54200F960A4C98C6%40AdobeOrg=1; AMCV_126E248D54200F960A4C98C6%40AdobeOrg=-1124106680%7CMCIDTS%7C19395%7CMCMID%7C86649901444800933773531435856600519789%7CMCAAMLH-1676302829%7C9%7CMCAAMB-1676302829%7C6G1ynYcLPuiQxYZrsz_pkqfLG9yMXBpb2zX5dvJdYQJzPXImdj0y%7CMCOPTOUT-1675705229s%7CNONE%7CvVersion%7C5.2.0; _m_h5_tk=d51c75d883691cf93c3ebfcca1ad886a_1675708136308; _m_h5_tk_enc=31d1a5de08c3a17863b593217c57b1ef; cto_bundle=YKf1XV9TbE1RaFp4OFR5cHVyTEJPT0FOT3pVN2djeDk3SlhFNURGWEdJakxwTWhGZkpNSzI1UDBQUW92eVhDUXhJQU83VHU3OFdycXhRY0JidCUyQmp1eGFRWkpuMjZRZFhmTklpODJFeVpPODJwVGxHVTFMQTh2WHlvdkllM090TzBaOEk0TnpVTkolMkJnY3phOUdHRGF1NkVqYjZnJTNEJTNE; G_ENABLED_IDPS=google; _bl_uid=t9lhFddCs9Fz00jp1p0vwXX644Fn; lzd_sid=148d00da0428cb1b8b31f5d8c9937792; lzd_uid=1184384097; lzd_b_csg=f97b4f93; sgcookie=E100CrcNxK6ybu4vNxAevZzcUKgtnOTf%2F1vHvGUS%2F0ZLW%2BsYuBGAu0SyQrh9soxk%2BZVpZaGoL2Nvd3V%2Bg64ftuGmhAE1rkCDO0vOlgO1wElJpkY%3D; lzd_uti=%7B%22fpd%22%3A%229999-99-99%22%2C%22lpd%22%3A%229999-99-99%22%2C%22cnt%22%3A%220%22%7D; t_sid=42GRoMRes0XRoMaE2BxlTykzpJXl8ONi; utm_channel=NA; _uetsid=8c100c00a63411ed86b77df4824197f2; _uetvid=8c103bd0a63411edb242e11e8036845b; tfstk=cT_NBN4lcEXQpyCYeeYV4sjZmAVOaB1Gi2RWj-sXEfTnGoI9UsvKHBxvuBRjTxxG.; isg=BFxc5lpWU47jNSfS-RM-IlBwLXwO1QD_BtXCDjZYSccqgfYLUeF5juJ34el5CThX; l=fBObBMxIT9F4QSmhBO5a-urza779NCOflsPzaNbMiIEGa6ilMFNWcNCecqpXydtjgT5VJE-y58T6Ydeyy8z3WxTSmdTuOrVUHHJwJeZ-cS1V.");
        headers.add("x-ua", "140#MXXr79znzzZylzo2wot+u3S0KiJD88Z4HDYpbEVFjwgzjHSegmup2mg6xgCTTlERnyXiYnAnLtRkHl4fDbg0JGtT6uYKMRjGZUUfiJVew+7I84afN3ap8yD/iy/p0VLVVw94NVi1xIAjtxhJlp1zzqt/bHtIXQzxDH8r73Etzzrb22U3lp1xxfqaXULdbSo/p0xTvG8txz2i2Duvlp1xDVP6V2l28qKzqPcMpAACzXri2FM+lIfxzRMiVxntlLzx2Ba+VW1gz/Xi2PIJlpkbzvxeUbZLMZ2aMXKkqiiv/XPK23Co2503N/kXaKlC8+a1EuZXe3GXZsN7WrBfIt4+n44qTUEGJQovNapVYJZVDrQMHfUOchZUCAO54PBhrOnjAWIImvIknBqKay24hwLURBm37+oH5PsWS695GmSuA9fZpD3l9gPUZLhcrEADGRKhxQ/ldtA0cdii3HotNL9RB5ubpcdVN9pbjmD2Jb1VXuUBEaqQcA/PtgM3QTRpndjb+m1MT6XkB9ID2BTLFwJo5c7/KMMEb8v4jjkZN+d0B/gR9lAwGpa00VNdZrS8Wh7xfx40wr23tv9CMzgiQUipPP8IQBUe2SXJ+ScgDOBCjmHXlud/e9ci/fJj+fioa+liNqXX9U4U0NpOeCcjOD76C3RIo41gHkGvvCrgOO10hY57x8DjQfSiUcy4jmBHeIBn2vzULrsDzxSvBhXzLK65wrbAn7HQnHUFQKcTIwI5ksEvUAaU11OwYibRle1qNAckvqCz8r40NuCxO92p/baxA9qVtW5ujrjiDP5XmFeRCnjr3A7GEoZNyQ2YPyLOqYq8nT5bPRCKedEvGEORT50qI1QtCdBat1ZyfvI7Cam3yIdQwcDQSc6WQWShiUL3bvnrFBewGMQ8n+LlcbCPNQcbFm7yIi0pxQAy7Aai4FGopGKdx3XrsoZzHyNMuxSvUWTFzK3mQWJ8dBqVbocn0ttYKmoyQzoPr2Xil+uHiJd6BxH6TK1G49AN8HmeDAuDX89J0fMQja7w8+jofDkDL6i3T05I78cmHQUPwqP3iKs0NNFNicP+AjdEJAndWBu6EVhhsZRmoamsS/vTbFuDV/X1olzip3B55qoGmBAcLWJQFVo03wU2RSaVgtpLvPKaC+rSUgoWeFCyugPIRLxH7RdskVTTFlCygu2v3XLkTFYE1vLmWf1lltYmtReucommfPW2l/MIexI3IFrBrb66jj+FKV7u62e2QZ7WOOWTMCtMH2i3Ml0yopgnNNwO5t16xEO0tw1MbITlylZsWZNnHLfvbEEVOJXb6TjjR3+cueYymwyox5btS3tKMk6x6Popn214slI/3riTm924RipWXA/NS+cqo53bXb/Z4kYCM7zDt/6L54N3XeSu+N0QokpHkc5paNArbrDq30rQN3MBxrDhSaeIyHgjC83AzXj0+k/Q//xtkWqYGrlKkUgHQtSwF+QjUyEGenwyU7ctvZNwId5b1saOd1u2xMbasATnw2RcazQMeW10NMuKticNidvYE/15lahmsW2aYNhkbXCTOUb5fdOZArvygYytMHxL85OlHWHNncVfZcpJ3TSdKjruGV6keTvfIn3l3nJMrCyK8jenLa+03aoIPKsZp0QwwaBuIiOYm6vfoum8/TbtjmywhKolYoBlT3Sb8RqFwzGTBuYjBSKBP9g7K7SyqCr8j3sl1zPS2EiszwQk");
        headers.add("X-CSRF-TOKEN", "ee04057b45eed");
        headers.add("x-umidtoken", "T2gAEXjAlB4-DnvcTHdccl2vYHtuuLnPOpM4mdffqK_GMyy7CwU_rrzXhlvV5sr6PLY=");
        headers.add("bx-umidtoken", "G84AB4CC4A086736389E4EC09265F3E107FA2DAD131385C1BDF");
        headers.add("bx-ua", "225!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");
        return execution.execute(request, body);
    }
}
