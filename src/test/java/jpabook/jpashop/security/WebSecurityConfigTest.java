package jpabook.jpashop.security;

import jpabook.jpashop.sessioin.WebSecurityConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // junit 할때 spring이랑 엮어서 테스트할래
@WebAppConfiguration
public class WebSecurityConfigTest {

    WebSecurityConfig webSecurityConfig = new WebSecurityConfig();

    @Test
    @DisplayName("패스워드 일치 테스트")
    public void matchTest() {
        // given
        String rawPW = "1234";
        String encodePW = webSecurityConfig.getPasswordEncoder().encode(rawPW);
        String inputPW = "1234";

        // when
        Boolean check = webSecurityConfig.getPasswordEncoder().matches(inputPW, encodePW);

        // then
        assertEquals(check, true);
    }
}
