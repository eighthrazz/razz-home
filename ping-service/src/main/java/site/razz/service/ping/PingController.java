package site.razz.service.ping;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import site.razz.common.spring.entity.PingEvent;

@RestController
@RequestMapping("/ping")
public class PingController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PingController.class);

  @Autowired private PingEventRepository pingEventRepository;

  @Value("${service.ping.host.wan}")
  private String hostWan;

  @Value("${service.ping.host.lan}")
  private String hostLan;

  @GetMapping("/list")
  @ResponseBody
  public List<PingEvent> list() {
    return pingEventRepository.findAll();
  }

  @GetMapping("/list/lan")
  public List<PingEvent> listLan(@RequestParam(name = "offset", defaultValue = "0") int offset) {
    LOGGER.info("{}.listLan : offset={}", PingEventRepository.class.getSimpleName(), offset);
    return pingEventRepository.findByHostOrderByDate(hostLan).stream()
        .skip(offset)
        .collect(Collectors.toList());
  }

  @GetMapping("/list/wan")
  public List<PingEvent> listWan(@RequestParam(name = "offset", defaultValue = "0") int offset) {
    LOGGER.info("{}.listWan : offset={}", PingEventRepository.class.getSimpleName(), offset);
    return pingEventRepository.findByHostOrderByDate(hostWan).stream()
        .skip(offset)
        .collect(Collectors.toList());
  }
}
