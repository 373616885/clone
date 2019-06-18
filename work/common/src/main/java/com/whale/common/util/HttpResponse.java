package com.whale.common.util;

import lombok.Data;
import org.apache.http.Header;

/**
 * @author dengjihai
 * @create 2019-02-18
 **/
@Data
public class HttpResponse {

    private String body;

    private Header[] headers;

    private String reasonPhrase;

    private int statusCode;
}
