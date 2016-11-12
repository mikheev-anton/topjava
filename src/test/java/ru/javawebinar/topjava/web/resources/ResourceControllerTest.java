package ru.javawebinar.topjava.web.resources;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.UserTestData.USER;

public class ResourceControllerTest extends AbstractControllerTest {

    @Test
    public void testCss()throws Exception{
        mockMvc.perform(get("meals"))
                .andDo(print())
                .andExpect(status().is(302));
//                .andExpect(content().)
    }
}
