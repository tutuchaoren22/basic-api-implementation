package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entities.RsEvent;
import com.thoughtworks.rslist.entities.User;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.exception.InvalidParamException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
    public static List<RsEvent> rsList = init();

    public static List<RsEvent> init() {
        List<RsEvent> rsEvents = new ArrayList<>();
        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");
        rsEvents.add(new RsEvent("第一条事件", "分类一", user));
        rsEvents.add(new RsEvent("第二条事件", "分类二", user));
        rsEvents.add(new RsEvent("第三条事件", "分类三", user));
        return rsEvents;
    }

    @GetMapping("/rs/list/{index}")
    public ResponseEntity getOneRsEvent(@PathVariable int index) throws InvalidIndexException {
        if (index > rsList.size()) {
            throw new InvalidIndexException("invalid index");
        }
        return new ResponseEntity<>(rsList.get(index - 1), HttpStatus.OK);
    }

    @GetMapping("/rs/list")
    public ResponseEntity<List<RsEvent>> getRsEvent(@RequestParam(required = false) Integer start,
                                                    @RequestParam(required = false) Integer end) throws InvalidIndexException {
        if (start == null || end == null) {
            return new ResponseEntity<>(rsList, HttpStatus.OK);
        }
        if (start < 1 || end > rsList.size()) {
            throw new InvalidIndexException("invalid request param");
        }
        return new ResponseEntity<>(rsList.subList(start - 1, end), HttpStatus.OK);
    }

    @PostMapping("/rs/add")
    public ResponseEntity addRsEvent(@RequestBody @Validated RsEvent rsEvent, BindingResult bindingResult) throws InvalidParamException {
        if (bindingResult.hasErrors()) {
            throw new InvalidParamException("invalid param");
        }
        boolean hasExist = false;
        for (User user : UserRegisterController.userList) {
            if (rsEvent.getUser().getUserName().equals(user.getUserName())) {
                hasExist = true;
                break;
            }
        }
        if (!hasExist) {
            UserRegisterController.userList.add(rsEvent.getUser());
        }
        rsList.add(rsEvent);
        return ResponseEntity.created(null)
                .header("index", String.valueOf(rsList.size() - 1))
                .build();
    }

    @PostMapping("/rs/update/{index}")
    public ResponseEntity updateRsEvent(@PathVariable int index, @RequestBody @Valid RsEvent rsEvent) {
        if (index > rsList.size()) {
            throw new RuntimeException("索引超出列表长度");
        }
        RsEvent rsEventToUpdate = rsList.get(index - 1);
        String eventNameToUpdate = rsEvent.getEventName() == null ? rsEventToUpdate.getEventName() : rsEvent.getEventName();
        String keywordToUpdate = rsEvent.getKeyword() == null ? rsEventToUpdate.getKeyword() : rsEvent.getKeyword();
        System.out.println(eventNameToUpdate);
        System.out.println(keywordToUpdate);
        rsList.set(index - 1, new RsEvent(eventNameToUpdate, keywordToUpdate, rsEvent.getUser()));
        return ResponseEntity.created(null)
                .header("index", String.valueOf(index - 1))
                .build();
    }

    @PostMapping("/rs/delete/{index}")
    public ResponseEntity deleteRsEvent(@PathVariable int index) {
        if (index > rsList.size()) {
            throw new RuntimeException("索引超出列表长度");
        }
        rsList.remove(index - 1);
        return ResponseEntity.created(null)
                .header("index", String.valueOf(index - 1))
                .build();
    }
}
