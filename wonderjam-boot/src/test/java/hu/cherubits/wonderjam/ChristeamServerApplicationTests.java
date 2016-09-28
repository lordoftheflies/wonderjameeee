package hu.cherubits.wonderjam;

import java.nio.charset.Charset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ChristeamServerApplicationTests {

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
//
//    protected MockMvc mockMvc;
//
//    @Autowired
//    protected WebApplicationContext webApplicationContext;
//
//    protected HttpMessageConverter mappingJackson2HttpMessageConverter;
//
//    @Autowired
//    void setConverters(HttpMessageConverter<?>[] converters) {
//
//        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
//                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
//
//        Assert.assertNotNull("the JSON message converter must not be null",
//                this.mappingJackson2HttpMessageConverter);
//    }
//
//    @Before
//    public void setup() throws Exception {
//        this.mockMvc = webAppContextSetup(webApplicationContext).build();
//    }
//
    @Test
    public void createContentWithBasicTypes() {
////        mockMvc.perform(get("/" + userName + "/bookmarks"))
////                .andExpect(status().isOk())
////                .andExpect(content().contentType(contentType))
////                .andExpect(jsonPath("$", hasSize(2)))
////                .andExpect(jsonPath("$[0].id", is(this.bookmarkList.get(0).getId().intValue())))
////                .andExpect(jsonPath("$[0].uri", is("http://bookmark.com/1/" + userName)))
////                .andExpect(jsonPath("$[0].description", is("A description")))
////                .andExpect(jsonPath("$[1].id", is(this.bookmarkList.get(1).getId().intValue())))
////                .andExpect(jsonPath("$[1].uri", is("http://bookmark.com/2/" + userName)))
////                .andExpect(jsonPath("$[1].description", is("A description")));
    }

}
