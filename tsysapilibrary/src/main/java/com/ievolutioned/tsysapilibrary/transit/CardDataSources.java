package com.ievolutioned.tsysapilibrary.transit;

/**
 * {@link CardDataSources} interface.
 * Contains the main data sources allowed by the service.
 * <p>
 * Created by Daniel on 16/12/2016.
 * </p>
 */

public interface CardDataSources {
    String SWIPE = "SWIPE";
    String NFC = "NFC";
    String EMV = "EMV";
    String EMV_CONTACTLESS = "EMV_CONTACTLESS";
    String BAR_CODE = "BAR_CODE";
    String MANUAL = "MANUAL";
    String PHONE = "PHONE";
    String MAIL = "MAIL";
    String INTERNET = "INTERNET";
    String FALLBACK_SWIPE = "FALLBACK_SWIPE";
}
