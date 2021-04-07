package main.test;

import main.api.response.UserResponse;
import main.model.User;
import main.repo.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void gettingUserInfo(){
        User user = new User();
        Optional<User> optionalUser = userRepository.findByName("admin");
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        UserResponse userResponse = restTemplate.getForObject("/getuser/?name=admin", UserResponse.class);
        assertThat(user.getName(), equalTo(userResponse.getName()));
    }
}
