package com.groot.base.config;

import com.groot.base.bean.CurrentUser;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CurrentUserStore {

    /**
     * 各个线程的用户信息
     */
    private ThreadLocal<CurrentUser> users = new ThreadLocal<>();

    public void setCurrentUser(CurrentUser currentUser) {
        users.set(currentUser);
    }

    public CurrentUser getCurrentUser() {
        return users.get();
    }

    /**
     * 清除当前用户信息
     */
    public void removeCurrentUser() {
        users.remove();
    }

}
