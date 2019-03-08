package site.razz.ui.home;

import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import site.razz.common.spring.entity.PingEvent;

@Controller
@RequestMapping("/")
public class HomeUiController {

  private final RestTemplate restTemplate;

  public HomeUiController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @RequestMapping("")
  public ModelAndView index() {
    return new ModelAndView("index");
  }

  @GetMapping("ping/list/lan")
  @PreAuthorize("hasAuthority('SCOPE_profile')")
  public ResponseEntity<List<PingEvent>> listLan() {
    return restTemplate.exchange(
        "http://ping-service/ping/list/lan",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<PingEvent>>() {});
  }

  @GetMapping("ping/list/wan")
  @PreAuthorize("hasAuthority('SCOPE_profile')")
  public ResponseEntity<List<PingEvent>> listWan() {
    return restTemplate.exchange(
        "http://ping-service/ping/list/wan",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<PingEvent>>() {});
  }
}
