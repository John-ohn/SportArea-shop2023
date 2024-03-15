package com.sportArea.controller;

import com.sportArea.entity.Blog;
import com.sportArea.entity.dto.BlogDTO;
import com.sportArea.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogService blogService;

    private BlogDTO blogDTOOne;

    private BlogDTO blogDTOSecond;

    @BeforeEach
    void createBlogDTOs() {
        blogDTOOne = BlogDTO.builder()
                .blogId(1L)
                .title("How be stronger")
                .subTitle("<p>Безумовно, одним з проявів любові до власного тіла є дбайливе ставлення до його фізичної форми, сили та витривалості. Це стосується не лише естетичного аспекту, але й загального самопочуття та здоров'я. Фізична активність розганяє біль, втому та поганий настрій.</p><br>")
                .text("<p>Безумовно, одним з проявів любові до власного тіла є дбайливе ставлення до його фізичної форми, сили та витривалості. Це стосується не лише естетичного аспекту, але й загального самопочуття та здоров'я. Фізична активність розганяє біль, втому та поганий настрій.</p><br>\n" +
                        "<p>Буває немало ситуацій у житті, коли виникає бажання займатися спортом, але відсутність часу на відвідування спортзалу стає перешкодою. Але це не вагомий привід відмовлятися від фізичних навантажень. Існують численні можливості якісно поліпшити свою фізичну форму навіть без виходу з дому.</p><br>\n" +
                        "\n" +
                        "<p><h3>Вправи для пружних сідниць</h3></p><br>\n" +
                        "\n" +
                        "<p>Важливо дотримуватися правильної техніки виконання вправ для уникнення травм та досягнення максимальних результатів.</p><br>\n" +
                        "<p>Якщо ви новачок у спорті, найкраще звернутися до професійного тренера, який допоможе вивчити правильну техніку та надасть рекомендації щодо обсягу та інтенсивності тренувань.</p><br>\n" +
                        "\n")
                .urlMainImage("https://www.dung.com/wp-content/uploads/2022/11/main-1.2.0.png")
                .build();

        blogDTOSecond = BlogDTO.builder()
                .blogId(2L)
                .title("How be goode")
                .subTitle("Безумовно, одним з проявів любові до власного тіла є дбайливе ставлення до його фізичної форми, сили та витривалості. Це стосується не лише естетичного аспекту, але й загального самопочуття та здоров'я. Фізична активність розганяє біль, втому та поганий настрій.</p><br>")
                .text("<p>Безумовно, одним з проявів любові до власного тіла є дбайливе ставлення до його фізичної форми, сили та витривалості. Це стосується не лише естетичного аспекту, але й загального самопочуття та здоров'я. Фізична активність розганяє біль, втому та поганий настрій.</p><br>\n" +
                        "<p>Буває немало ситуацій у житті, коли виникає бажання займатися спортом, але відсутність часу на відвідування спортзалу стає перешкодою. Але це не вагомий привід відмовлятися від фізичних навантажень. Існують численні можливості якісно поліпшити свою фізичну форму навіть без виходу з дому.</p><br>\n" +
                        "\n" +
                        "<p><h3>Вправи для пружних сідниць</h3></p><br>\n" +
                        "\n" +
                        "<p>Важливо дотримуватися правильної техніки виконання вправ для уникнення травм та досягнення максимальних результатів.</p><br>\n" +
                        "<p>Якщо ви новачок у спорті, найкраще звернутися до професійного тренера, який допоможе вивчити правильну техніку та надасть рекомендації щодо обсягу та інтенсивності тренувань.</p><br>\n" +
                        "\n")
                .urlMainImage("https://www.dung.com/wp-content/uploads/2022/11/main-1.2.10.png")
                .build();
    }


    @Test
    @DisplayName("Test BlogController method findAll")
    void testMethodFindAll() throws Exception {
        List<BlogDTO> blogList = List.of(blogDTOOne, blogDTOSecond);

        when(blogService.findAll()).thenReturn(blogList);

        mockMvc.perform(get("/api/v1/blogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].blogId", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[0]").value(blogDTOOne))
                .andExpect(jsonPath("$[1]").value(blogDTOSecond));
    }

    @Test
    @DisplayName("Test BlogController method findById")
    void testMethodFindById() throws Exception {

        when(blogService.findById(1L)).thenReturn(blogDTOOne);

        mockMvc.perform(get("/api/v1/blogs/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.blogId", is(1) ))
                .andExpect(jsonPath("$").value(blogDTOOne));
    }
}