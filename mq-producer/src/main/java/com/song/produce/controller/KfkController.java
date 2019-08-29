package com.song.produce.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.song.produce.service.KfkProduceService;
import javax.annotation.Resource;

/**
 * Created by Song on 2019/08/23.
 */
@RestController
@Slf4j
public class KfkController {
    @Resource
    private KfkProduceService kfkProduceService;

    @GetMapping(value = "/test/send")
    public void send(@RequestParam(value = "id")Integer id,
                     @RequestParam(value = "value")String value) {
        kfkProduceService.send(id, value);
    }
}
