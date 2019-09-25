package com.nytimes.populararticles.api;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static org.mockito.ArgumentMatchers.any;

/**
 * Unit test class for the {@link RequestInterceptor}
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestInterceptorTest {
    @Mock
    private Interceptor.Chain chainInterceptor;
    private RequestInterceptor requestInterceptor;

    @Before
    public void setUp() {
        requestInterceptor = new RequestInterceptor();
    }

    @After
    public void tearDown() {
        requestInterceptor = null;
        chainInterceptor = null;
    }

    @Test
    public void intercept() throws IOException {
        final Request request = new Request.Builder()
                .url(ApiConstants.SERVICES_BASE_URL)
                .build();
        final Response interceptorResponse = new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("Intercepted!")
                .body(ResponseBody.create(MediaType.parse("text/plain; charset=utf-8"), "abc"))
                .build();

        Mockito.when(chainInterceptor.request()).thenReturn(request);
        Mockito.when(chainInterceptor.proceed(any())).thenReturn(interceptorResponse);

        Response response = requestInterceptor.intercept(chainInterceptor);

        TestCase.assertSame(interceptorResponse, response);
    }

}