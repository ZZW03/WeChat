package com.zzw.common;

public class Const {

    public static class ACCOUNT{
        public static final String ATTR_USER_NAME = "username";
        public static final String ATTR_USER_ID = "id";
    }


    public static class ORDER{
        public  static final int ORDER_CORS = -102;
    }

    public static class MQ{
        public static final String MQ_QUEUE_NAME_MAIL = "queue:mail";

        public static final String MessageService2Im = "messageservice2im";
        public static final String FriendshipService2Im = "friendshipservice2im";


        public static final String Im2UserService = "pipeline2UserService";
        public static final String Im2FriendshipService = "pipeline2FriendshipService";
        public static final String Im2GroupService = "pipeline2GroupService";
        public static final String Im2MessageService = "im2MessageService";


    }

    public static class REDIS{
        public static final String LIMIT_IP_BLACK ="limit:ip:black";
        public static final String BLACK_LIST = "blacklist";
        public static final String VERIFY_EMAIL_CODE = "verify:email:code";
        public static final String IMAGE_UPDATE_BLACK_LIST = "image:update:black:list";
        public static final String USER_SESSION = "user:session";


    }

    public static  class CHANNEL{
        public static final String UserId = "userId";
    }



}
