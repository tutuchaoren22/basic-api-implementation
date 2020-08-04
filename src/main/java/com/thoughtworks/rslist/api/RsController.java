package com.thoughtworks.rslist.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
    private final List<RsEvent> rsList = init();

    private List<RsEvent> init() {
        List<RsEvent> rsEvents = new ArrayList<>();
        rsEvents.add(new RsEvent("第一条事件", "分类一"));
        rsEvents.add(new RsEvent("第二条事件", "分类二"));
        rsEvents.add(new RsEvent("第三条事件", "分类三"));
        return rsEvents;
    }

    @GetMapping("/rs/list/{index}")
    public RsEvent getOneRsEvent(@PathVariable int index) {
        return rsList.get(index - 1);
    }
}
