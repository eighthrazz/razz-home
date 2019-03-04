package site.razz.service.ping;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.razz.common.spring.entity.PingEvent;

@RestController
@RequestMapping("/ping")
public class PingController {

  @Autowired private PingEventRepository pingEventRepository;

  @Value("${service.ping.host.wan}")
  private String hostWan;

  @Value("${service.ping.host.lan}")
  private String hostLan;

  @RequestMapping("/list")
  public List<PingEvent> list() {
    return pingEventRepository.findAll();
  }

  @RequestMapping("/list/lan")
  public List<PingEvent> listLan() {
    return pingEventRepository.findByHost(hostLan);
  }

  @RequestMapping("/list/wan")
  public List<PingEvent> listWan() {
    return pingEventRepository.findByHost(hostWan);
  }
}
