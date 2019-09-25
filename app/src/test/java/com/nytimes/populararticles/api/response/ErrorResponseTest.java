package com.nytimes.populararticles.api.response;

import com.nytimes.populararticles.NyTimeApp;
import com.nytimes.populararticles.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test class for {@link ErrorResponse}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({NyTimeApp.class})
public class ErrorResponseTest {

    @Mock
    protected NyTimeApp nyTimeApp;

    private ErrorResponse errorResponse;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(NyTimeApp.class);
        when(NyTimeApp.getAppContext()).thenReturn(nyTimeApp);
        when(nyTimeApp.getString(R.string.unknownError)).thenReturn("unknownError");
        errorResponse = new ErrorResponse();
    }

    @After
    public void tearDown() {
        errorResponse = null;
    }

    @Test
    public void getStatus() {
        errorResponse.setStatus("ERROR");

        Assert.assertEquals("ERROR", errorResponse.getStatus());
    }

    @Test
    public void message() {
        errorResponse.setErrors(null);

        Assert.assertEquals("unknownError", errorResponse.getMessage());

        List<String> errors = new ArrayList<>();
        errors.add("Param 'period' is invalid.");
        errors.add("Bad Request");

        errorResponse.setErrors(errors);

        Assert.assertEquals("Bad Request", errorResponse.getMessage());
    }
}