package com.sportArea.service.Imp;

import com.sportArea.dao.BlogRepository;
import com.sportArea.entity.Blog;
import com.sportArea.entity.dto.BlogDTO;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(MockitoExtension.class)

class BlogServiceImpTest {

    @Mock
    private  BlogRepository blogRepository;

    @InjectMocks
    private BlogServiceImp blogServiceImp;

    private Blog blogOne;

    @BeforeEach
    public void setup() {
//        MockitoAnnotations.openMocks(this);

        blogOne= Blog.builder()
                .blogId(1L)
                .title("How be stronger")
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
    }

    @Test
    @DisplayName("Test BlogServiceImpTest method findAll")
    void findAll() {
        List<Blog> list = new ArrayList<>();
        Blog blogSecond = Blog.builder()
                .blogId(2L)
                .title("How be goode")
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

        list.add(blogOne);
        list.add(blogSecond);

        when(blogRepository.findAll()).thenReturn(List.of(blogOne,blogSecond));

        List<BlogDTO> blogs =blogServiceImp.findAll();

        assertAll(
                ()-> assertFalse(blogs.isEmpty()),
                ()-> assertEquals(2,blogs.size())
        );

        verify(blogRepository, times(1)).findAll();
    }

    @Test
    void findById() {
    }

    @Test
    void createBlogDTOFromBlog() {
    }

    @Test
    void convertToProductDTOList() {
    }

    @Test
    void titek() {
    }

    @Test
    void cutText() {
    }

    @Test
    void cutText2() {
    }
}