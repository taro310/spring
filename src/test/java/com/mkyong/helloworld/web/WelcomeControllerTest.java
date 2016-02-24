package com.mkyong.helloworld.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ModelResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;

import com.mkyong.helloworld.model.AddModel;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * WelcomeController test(with Spring MVC Test).
 * <p>
 * ref：http://kuwalab.hatenablog.jp/entry/20130402/p1
 * </p>
 * 
 * @author Satoshi Taromaru
 * @since 1.5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring-mvc-config.xml" })
public class WelcomeControllerTest {
    
    @Autowired
    private WebApplicationContext wac;
    
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        // OPTIONメソッドのテストをする場合、「.dispatchOptions(true)」を付ける
        // 参考：http://kakakikikeke.blogspot.jp/2015/07/receive-options-method-with-spring.html
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).dispatchOptions(true).build();
    }

    @Test
    public void slash_GET() throws Exception {
        // status
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/"));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        
        // view
        ViewResultMatchers view = MockMvcResultMatchers.view();
        result.andExpect(view.name("index"));

        // model
        ModelResultMatchers model = MockMvcResultMatchers.model();
        result.andExpect(model.hasNoErrors());

        // model(instance)
        ModelMap modelMap = result.andReturn().getModelAndView().getModelMap();
        Object object = modelMap.get("addModel");
        assertThat(object, is(not(nullValue())));
        assertThat(object, is(instanceOf(AddModel.class)));
        AddModel addModel = (AddModel) object;
        assertThat("titleの初期値が正しいこと", addModel.getTitle(), is("Hello World"));
        assertThat("msgの初期値が正しいこと", addModel.getMsg(), is("Gradle + Spring MVC Hello World Example"));
    }

    @Test
    public void slash_notAllowedMethod() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/"));
        result.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        result = mockMvc.perform(MockMvcRequestBuilders.put("/"));
        result.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        result = mockMvc.perform(MockMvcRequestBuilders.delete("/"));
        result.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/"));
        result.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        result = mockMvc.perform(MockMvcRequestBuilders.head("/"));
        result.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        result = mockMvc.perform(MockMvcRequestBuilders.options("/"));
        result.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        result = mockMvc.perform(MockMvcRequestBuilders.patch("/"));
        result.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    public void hello_GET() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/hello/Name"));

        // status
        result.andExpect(MockMvcResultMatchers.status().isOk());

        // view
        ViewResultMatchers view = MockMvcResultMatchers.view();
        result.andExpect(view.name("index"));

        // model
        ModelResultMatchers model = MockMvcResultMatchers.model();
        result.andExpect(model.hasNoErrors());
        result.andExpect(model.attribute("title", is("Hello Name")));
        result.andExpect(model.attribute("msg", is("Gradle + Spring MVC Hello World Example")));
    }
}
