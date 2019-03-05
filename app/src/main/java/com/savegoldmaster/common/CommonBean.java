package com.savegoldmaster.common;

import com.savegoldmaster.base.BaseBean;

public class CommonBean extends BaseBean  {

    /**
     * content :
     */

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean isSuccess() {
        return getCode() == 100;
    }
}
