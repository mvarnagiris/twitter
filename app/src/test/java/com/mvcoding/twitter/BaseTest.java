package com.mvcoding.twitter;

import org.junit.After;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class BaseTest {
    @Before public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After public void tearDown() throws Exception {

    }
}
