package site.razz.ui.home;

import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import site.razz.common.spring.entity.PingEvent;

@Controller
@RequestMapping("/")
public class HomeUiController {

  static final String PING_LIST_LAN = "http://ping-service/ping/list/lan";
  static final String PING_LIST_WAN = "http://ping-service/ping/list/wan";

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
  public ResponseEntity<List<PingEvent>> listLan(
      @RequestParam(name = "offset", defaultValue = "0") int offset) {

    final UriComponentsBuilder uriComponentsBuilder =
        UriComponentsBuilder.fromHttpUrl(PING_LIST_LAN).queryParam("offset", offset);

    return restTemplate.exchange(
        uriComponentsBuilder.toUriString(),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<PingEvent>>() {});
  }

  @GetMapping("ping/list/wan")
  @PreAuthorize("hasAuthority('SCOPE_profile')")
  public ResponseEntity<List<PingEvent>> listWan(
      @RequestParam(name = "offset", defaultValue = "0") int offset) {

    final UriComponentsBuilder uriComponentsBuilder =
        UriComponentsBuilder.fromHttpUrl(PING_LIST_WAN).queryParam("offset", offset);

    return restTemplate.exchange(
        uriComponentsBuilder.toUriString(),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<PingEvent>>() {});
  }
}
