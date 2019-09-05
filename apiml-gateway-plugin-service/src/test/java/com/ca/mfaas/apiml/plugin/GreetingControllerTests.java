/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package com.ca.mfaas.apiml.plugin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-servlet.xml"})
@WebAppConfiguration
public class GreetingControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void givenGreetingUriWithoutName_whenMockMVC_thenVerifyResponse() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/greeting")
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").value("Hello, World!"))
            .andReturn();

        Assert.assertEquals("application/json;charset=UTF-8",
            mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenGreetingUriWithName_whenMockMVC_thenVerifyResponse() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/greeting?name=petr")
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").value("Hello, petr!"))
            .andReturn();

        Assert.assertEquals("application/json;charset=UTF-8",
            mvcResult.getResponse().getContentType());
    }

}
