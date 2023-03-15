package com.gongyu.mydemo.bean.event;

import com.gongyu.mydemo.bean.UserDo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;




@Getter
@Setter
public class DemoEvent extends ApplicationEvent {

    private UserDo userDo;

    public DemoEvent(Object source,UserDo userDo) {
        super(source);
        this.userDo = userDo;
    }
}
