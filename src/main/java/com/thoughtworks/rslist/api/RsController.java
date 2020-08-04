package com.thoughtworks.rslist.api;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/rs/list")
    public List<RsEvent> getRsEvent(@RequestParam(required = false) Integer start,
                                    @RequestParam(required = false) Integer end) {
        if (start == null || end == null) {
            return rsList;
        }
        return rsList.subList(start - 1, end);
    }

    @PostMapping("/rs/add")
    public void addRsEvent(@RequestBody RsEvent rsEvent) {
        rsList.add(rsEvent);
    }
}
