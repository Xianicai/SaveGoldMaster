package com.savegoldmaster.home.model.bean;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

public class XBannerBean extends SimpleBannerInfo {
    public String imagerUrls;

    public XBannerBean(String imagerUrls) {
        this.imagerUrls = imagerUrls;
    }

    @Override
    public Object getXBannerUrl() {
        return imagerUrls;
    }
}
