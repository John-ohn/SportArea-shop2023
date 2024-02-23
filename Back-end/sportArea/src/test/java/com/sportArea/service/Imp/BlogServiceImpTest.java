package com.sportArea.service.Imp;

import com.sportArea.dao.BlogRepository;
import com.sportArea.entity.Blog;
import com.sportArea.entity.dto.BlogDTO;
import com.sportArea.exception.GeneralException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(MockitoExtension.class)
class BlogServiceImpTest {

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogServiceImp blogServiceImp;

    private Blog blogOne;

    private Blog blogSecond;

    @BeforeEach
    public void setup() {
//        MockitoAnnotations.openMocks(this);

        blogOne = Blog.builder()
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

        blogSecond = Blog.builder()
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
    }

    @Test
    @DisplayName("Test BlogServiceImp method findAll")
    void testMethodFindAll() {
        when(blogRepository.findAll()).thenReturn(List.of());

        Throwable error = assertThrows(GeneralException.class, () ->
                blogServiceImp.findAll());

        assertThrows(GeneralException.class, () -> blogServiceImp.findAll());
        assertEquals("Don't find any Blog. Blogs list is empty.", error.getMessage());

        List<Blog> list = new ArrayList<>();


        list.add(blogOne);
        list.add(blogSecond);

        when(blogRepository.findAll()).thenReturn(List.of(blogOne, blogSecond));

        List<BlogDTO> blogs = blogServiceImp.findAll();

        assertAll(
                () -> assertFalse(blogs.isEmpty()),
                () -> assertEquals(2, blogs.size())
        );

        verify(blogRepository, times(3)).findAll();
    }

    @Test
    @DisplayName("Test BlogServiceImp method findAll")
    void testMethodFindById() {

        when(blogRepository.findById(blogOne.getBlogId())).thenReturn(Optional.ofNullable(blogOne));

        BlogDTO blogDTO = blogServiceImp.findById(blogOne.getBlogId());

        assertAll(
                () -> assertNotNull(blogDTO),
                () -> assertEquals(blogOne.getBlogId(), blogDTO.getBlogId())
        );

        Throwable error = assertThrows(GeneralException.class, () ->
                blogServiceImp.findById(blogOne.getBlogId() + 2));

        assertThrows(GeneralException.class, () -> blogServiceImp.findById(blogOne.getBlogId() + 2));
        assertEquals("Blog with blogId: " + (blogOne.getBlogId() + 2) + " is not available.", error.getMessage());

        verify(blogRepository, times(3)).findById(any());
    }

    @Test
    @DisplayName("Test BlogServiceImp method createBlogDTOFromBlog")
    void testMethodCreateBlogDTOFromBlog() {

        BlogDTO blogDTO = blogServiceImp.createBlogDTOFromBlog(blogOne);

        assertNotNull(blogDTO);
        assertEquals(blogOne.getBlogId(), blogDTO.getBlogId());

        GeneralException error = assertThrows(GeneralException.class, () ->
                blogServiceImp.createBlogDTOFromBlog(null));

        assertThrows(GeneralException.class, () -> blogServiceImp.createBlogDTOFromBlog(null));
        assertEquals("Blog is null and not available.", error.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, error.getHttpStatus());

    }

    @Test
    @DisplayName("Test BlogServiceImp method convertToProductDTOList")
    void testMethodConvertToProductDTOList() {

        List<Blog> list = new ArrayList<>();

        assertTrue(list.isEmpty());

        list.add(blogOne);
        list.add(blogSecond);

        List<BlogDTO> blogList = blogServiceImp.convertToProductDTOList(list);

        assertAll(
                () -> assertFalse(blogList.isEmpty()),
                () -> assertEquals(2, blogList.size())
        );
    }

    @Test
    @DisplayName("Test BlogServiceImp method addSubTitle")
    void testMethodAddSubTitle() {

        BlogDTO blogDTOFirst = blogServiceImp.createBlogDTOFromBlog(blogOne);
        BlogDTO blogDTOSecond = blogServiceImp.createBlogDTOFromBlog(blogSecond);

        List<BlogDTO> listBlogs = new ArrayList<>();

        listBlogs.add(blogDTOFirst);
        listBlogs.add(blogDTOSecond);

        blogServiceImp.addSubTitle(listBlogs);

        assertFalse(listBlogs.isEmpty());
        assertTrue(listBlogs.get(listBlogs.indexOf(blogDTOFirst)).getSubTitle().length() <= 165);
        assertEquals("Безумовно, одним з проявів любові до власного тіла є дбайливе ставлення до його фізичної форми, сили та витривалості. " +
                        "Це стосується не лише естетичного аспекту,...",
                listBlogs.get(listBlogs.indexOf(blogDTOFirst)).getSubTitle());
    }

    @Test
    @DisplayName("Test BlogServiceImp method cutText")
    void testMethodCutText() {

        String cutText = BlogServiceImp.cutText(blogOne.getText());

        assertNotNull(cutText);
        assertEquals("Безумовно, одним з проявів любові до власного тіла є дбайливе ставлення до його фізичної форми, сили та витривалості. " +
                "Це стосується не лише естетичного аспекту,...", cutText);

    }

    @Test
    @DisplayName("Test BlogServiceImp method cutText2")
    void testMethodCutText2() {
        String cutText = BlogServiceImp.cutText2(blogOne.getText());

        assertNotNull(cutText);
        assertEquals("Безумовно, одним з проявів любові до власного тіла є дбайливе ставлення до його фізичної форми, сили та витривалості. " +
                "Це стосується не лише естетичного аспекту, але...", cutText);

    }
}