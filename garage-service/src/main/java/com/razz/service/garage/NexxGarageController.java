package com.razz.service.garage;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/garage")
public class NexxGarageController {

  @Autowired
  private NexxGarageEventRepository repository;

  @RequestMapping("/status")
  String getStatus() {
    final Sort sort = Sort.by(Direction.DESC, "date");
    final PageRequest pageRequest = PageRequest.of(0, 1, sort);
    final Page<NexxGarageEvent> page = repository.findAll(pageRequest);
    final StringBuilder strBld = new StringBuilder();
    page.forEach(p -> strBld.append(p.getState()).append(", ").append(new Date(p.getDate())));
    return strBld.toString();
  }

}
