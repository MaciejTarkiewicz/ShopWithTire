package pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.command.TireWithDetailsWebCommand;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.dto.TireDto;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor.CreateTireExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor.DeleteTireExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.executor.UpdateTireExecutor;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.service.TireGetService;
import pl.tarkiewicz.springsecuritysimplefactorauth.user.UserDetailsServiceImpl;

@WebMvcTest(TireController.class)
class TireControllerTest {

    @MockBean
    private TireGetService tireGetService;

    @MockBean
    CreateTireExecutor createTireExecutor;

    @MockBean
    DeleteTireExecutor deleteTireExecutor;

    @MockBean
    UpdateTireExecutor updateTireExecutor;

    @MockBean
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    private TireWithDetailsWebCommand tireWithDetailsWebCommand;
    private TireDto tireDto;
    private TireDto tireDto1;
    private TireDto tireDto2;

    private List<TireDto> tireDtosList = new ArrayList<>();

    @BeforeEach
    void setUp() {

        tireWithDetailsWebCommand = TireWithDetailsWebCommand.builder()
                .wide(3)
                .season(Season.SUMMER)
                .profile(2)
                .price(BigDecimal.valueOf(20.00))
                .mark("BMW")
                .diameter(5)
                .count(4)
                .build();

        tireDto = TireDto.builder()
                .season(Season.SUMMER)
                .diameter(5)
                .mark("BMW")
                .wide(3)
                .profile(2)
                .price(BigDecimal.valueOf(20.00))
                .build();

        tireDto1 = TireDto.builder()
                .season(Season.SUMMER)
                .diameter(5)
                .mark("BMW")
                .wide(3)
                .profile(2)
                .price(BigDecimal.valueOf(20.00))
                .build();

        tireDto2 = TireDto.builder()
                .season(Season.WINTER)
                .diameter(5)
                .mark("BMW")
                .wide(3)
                .profile(2)
                .price(BigDecimal.valueOf(20.00))
                .build();

        tireDtosList.add(tireDto);
        tireDtosList.add(tireDto1);
        tireDtosList.add(tireDto2);

    }

    @Test
    void example() throws Exception {

    /*    when(tireGetService.getAllTiresByDtoParameters(tireDto)).thenReturn(tireDtosList);

        this.mockMvc.perform(get("/example"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andDo(print());*/
    }

    @Test
    void getAllTiresWithDetails() throws Exception {

        when(tireGetService.getAllTire()).thenReturn(tireDtosList);

        this.mockMvc.perform(get("/tire/details/all"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test
    void getAllTiresWithDetailsBySeason() {
    }

    @Test
    void addTiresWithDetails() {
    }

    @Test
    void deletedTires() {
    }

    @Test
    void deletedAllTiresWithDetails() {
    }

    @Test
    void updateTirePrice() {
    }

    @Test
    void updateAllTiresWithDetails() {
    }
}