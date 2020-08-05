package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entities.RsEvent;
import com.thoughtworks.rslist.entities.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
    private final List<RsEvent> rsList = init();

    private List<RsEvent> init() {
        List<RsEvent> rsEvents = new ArrayList<>();
        rsEvents.add(new RsEvent("第一条事件", "分类一", new User()));
        rsEvents.add(new RsEvent("第二条事件", "分类二", new User()));
        rsEvents.add(new RsEvent("第三条事件", "分类三", new User()));
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
    public void addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
        rsList.add(rsEvent);
//        如果userName已存在在user列表中的话则只需添加热搜事件到热搜事件列表，
//        如果userName不存在，则将User添加到热搜事件列表中（相当于注册用户）
    }

    @PostMapping("/rs/update/{index}")
    public void updateRsEvent(@PathVariable int index, @RequestBody RsEvent rsEvent) {
        if (index > rsList.size()) {
            throw new RuntimeException("索引超出列表长度");
        }
        RsEvent rsEventToUpdate = rsList.get(index - 1);
        String eventNameToUpdate = rsEvent.getEventName() == null ? rsEventToUpdate.getEventName() : rsEvent.getEventName();
        String keywordToUpdate = rsEvent.getKeyword() == null ? rsEventToUpdate.getKeyword() : rsEvent.getKeyword();
        rsList.set(index - 1, new RsEvent(eventNameToUpdate, keywordToUpdate, rsEvent.getUser()));
    }

    @PostMapping("/rs/delete/{index}")
    public void deleteRsEvent(@PathVariable int index) {
        if (index > rsList.size()) {
            throw new RuntimeException("索引超出列表长度");
        }
        rsList.remove(index - 1);
    }
}
