package site.razz.service.ping;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {

  @Autowired
  private PingEventRepository pingEventRepository;

  @RequestMapping("/list")
  public List<PingEvent> list() {
    return pingEventRepository.findAll();
  }

}
