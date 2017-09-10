package com.gangnam4bungate.nuviseoul.config;

/**
 * Created by wsseo on 2017. 7. 2..
 */

public class CODES {
    public static String TAG = "NuviSeoul";
    public static String DefaultDomain = "http://api.visitkorea.or.kr";
    public static String NaverDomain = "https://openapi.naver.com";
    public static String Dev_ServiceKey = "Wasn0OGY43ReOgSy5nz8ZNURGTw4Y5MRPt%2B1rJw0xZCEbQG07s6n5hjqHHqzzIRBCP8U1H64Q0RIgAZPRWSPlA%3D%3D";
    public static String NAVER_CLIENT_ID = "WA672XziAZbalmbhFN_9";
    public static String NAVER_CLIENT_SECRET = "IVvaGuS7Tb";

    public static class URLCodes {
        public static String URL_AREABASELIST = "/openapi/service/rest/KorService/areaBasedList";
        public static String URL_SEARCH = "/v1/search/blog?query=";
    }

    public static class CommonCodes {
        public static String SERVICEKEY = "ServiceKey";
        public static String AREACODE = "areaCode";
        public static String CONTENTTYPEID = "contentTypeId";
        public static String MOBILEOS = "MobileOS";
        public static String MOBILEAPP = "MobileApp";
        public static String _TYPE = "_type";
    }

    public static class RequestCode {
        public static String REQUEST_AREABASELIST = "REQUEST_AREABASELIST";
        public static String REQUEST_SEARCH = "REQUEST_SEARCH";
    }

    public static class API_CONTENTTYPE {
        public static String TOUR_SIGHTS = "12";//관광지
        public static String CULTURAL_FACILITIES = "14";//문화시설
        public static String FESTIVAL = "15";//축제,공연,행사
        public static String TOUR_COURSE = "25";//여행코스
        public static String REPORTS = "28";//레포츠
        public static String ACCOMMODATION = "32";//숙박
        public static String SHOPPING = "38";//쇼핑
        public static String FOOD = "39";//음식
    }
}
