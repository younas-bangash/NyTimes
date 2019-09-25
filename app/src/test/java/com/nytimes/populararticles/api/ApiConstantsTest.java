package com.nytimes.populararticles.api;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the {@link ApiConstants}
 */
public class ApiConstantsTest {

    @Test
    public void testVariables(){
        Assert.assertEquals("http://api.nytimes.com/", ApiConstants.SERVICES_BASE_URL);
        Assert.assertEquals(30000, ApiConstants.CONNECT_TIMEOUT);
        Assert.assertEquals(30000, ApiConstants.READ_TIMEOUT);
        Assert.assertEquals(30000, ApiConstants.WRITE_TIMEOUT);
        Assert.assertEquals("GO2uSGakYyev27GpasLUDVDNmBAtdZPj", ApiConstants.API_KEY);
    }
}