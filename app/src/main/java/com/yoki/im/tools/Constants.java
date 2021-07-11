package com.yoki.im.tools;

import com.yoki.im.R;

public class Constants {
    public static final String API_CENTER = "http://192.168.1.10:8360";
    public static final String APP_FILE_PROVIDER = "com.yoki.im.fileProvider";
    public static final String FILE_PATH_USER_AVATAR = PathUtils.getCachePicturesPath(FILE_NAME.USER_AVATAR);
    public static final String H5_CENTER = "http://h5.acarbang.com/";
    public static final String SP_APP_CONFIG_NAME = "appConfig";
    public static final String SP_APP_IM_CACHE = "imCache";
    public static final String URL_FILE = "http://192.168.1.10/files/";

    public static class API {
        public static String ACCOUNT_RECHARGE = "http://192.168.1.10:8360/finance/recharge";
        public static String AGENT_CREATE = "http://192.168.1.10:8360/user/create";
        public static String AGENT_INCOME_STATISTIC = "http://192.168.1.10:8360/statistic/userIncome";
        public static String AGENT_PROMOTION_STATISTIC = "http://192.168.1.10:8360/statistic/promotionStatistic";
        public static String AREA_LIST = "http://192.168.1.10:8360/area/allCities";
        public static String AUTHENTICATION_GET = "http://192.168.1.10:8360/authentication/get";
        public static String AUTHENTICATION_SAVE = "http://192.168.1.10:8360/authentication/save";
        public static String BALANCE_LIST = "http://192.168.1.10:8360/finance/balanceList";
        public static String BANKCARD_ADD = "http://192.168.1.10:8360/bankcard/add";
        public static String BANKCARD_DELETE = "http://192.168.1.10:8360/bankcard/delete";
        public static String BANKCARD_GET = "http://192.168.1.10:8360/bankcard/get";
        public static String BANKCARD_LIST = "http://192.168.1.10:8360/bankcard/list";
        public static String BANKCARD_UPDATE = "http://192.168.1.10:8360/bankcard/update";
        public static String CAR_ADD = "http://192.168.1.10:8360/cos/add";
        public static String CAR_DELETE = "http://192.168.1.10:8360/cos/delete";
        public static String CAR_DETAIL = "http://192.168.1.10:8360/cos/get";
        public static String CAR_GET = "http://192.168.1.10:8360/car/getCarInfo";
        public static String CAR_LIST = "http://192.168.1.10:8360/cos/list";
        public static String CAR_UPDATE = "http://192.168.1.10:8360/cos/update";
        public static String CHECK_NEW_VERSION = "http://192.168.1.10:8360/release/getLatest";
        public static String DATA_DICTIONARY = "http://192.168.1.10:8360/dictionary/findByName";
        public static String DATA_DICTIONARY_ALL = "http://192.168.1.10:8360/dictionary/getAll";
        public static String DRIVING_LICENCE_GET = "http://192.168.1.10:8360/drivinglicence/get";
        public static String DRIVING_LICENCE_QUERY = "http://192.168.1.10:8360/drivinglicence/query";
        public static String DRIVING_LICENCE_QUERY_HISTORY = "http://192.168.1.10:8360/drivinglicence/queryHistory";
        public static String DRIVING_LICENCE_UPDATE_NOTIFY_POLICY = "http://192.168.1.10:8360/drivinglicence/updateNotifyPolicy";
        public static String ENCASHMENT_APPLY = "http://192.168.1.10:8360/finance/encashmentCreate";
        public static String ENCASHMENT_LIST = "http://192.168.1.10:8360/finance/encashmentList";
        public static String FINANCE_INFO_GET = "http://192.168.1.10:8360/user/getFinanceInfo";
        public static String GET_QR_CODE = "http://192.168.1.10:8360/user/getShareQRCode";
        public static String INTRODUCE_ADD = "http://192.168.1.10:8360/introduce/add";
        public static String INTRODUCE_DELETE = "http://192.168.1.10:8360/introduce/delete";
        public static String INTRODUCE_LIST = "http://192.168.1.10:8360/introduce/list";
        public static String INTRODUCE_REJECT = "http://192.168.1.10:8360/introduce/reject";
        public static String MESSAGE_LIST = "http://192.168.1.10:8360/message/list";
        public static String ORDER_AMOUNT_PAYABLE = "http://192.168.1.10:8360/order/getAmountPayable";
        public static String ORDER_COMMIT = "http://192.168.1.10:8360/order/commit";
        public static String ORDER_DETAIL = "http://192.168.1.10:8360/order/detail";
        public static String ORDER_DOCS_UPLOAD = "http://192.168.1.10:8360/order/uploadFile";
        public static String ORDER_HANDLE_NODES = "http://192.168.1.10:8360/order/getOrderHandleNodes";
        public static String ORDER_LIST = "http://192.168.1.10:8360/order/profilePaginate";
        public static String ORDER_MASTER_DETAIL = "http://192.168.1.10:8360/order/masterDetail";
        public static String ORDER_NEED_DOCS = "http://192.168.1.10:8360/order/getNeedDocs";
        public static String ORDER_USER_HANDLE = "http://192.168.1.10:8360/order/getUserOrderHandleNodes";
        public static String PARTNER_APPLY = "http://192.168.1.10:8360/partner/apply";
        public static String PARTNER_LIST = "http://192.168.1.10:8360/partner/list";
        public static String PARTNER_UPDATE = "http://192.168.1.10:8360/partner/updateApply";
        public static String PAY = "http://192.168.1.10:8360/pay/cashier";
        public static String PAY_GET_PAY_RECORD = "http://192.168.1.10:8360/pay/getPayRecord";
        public static String PAY_GET_RESULT = "http://192.168.1.10:8360/pay/getPayResult";
        public static String PAY_GET_STATUS = "http://192.168.1.10:8360/pay/getPayStatus";
        public static String POINTS_LIST = "http://192.168.1.10:8360/points/tradeList";
        public static String POINTS_RECHARGE = "http://192.168.1.10:8360/points/recharge";
        public static String REFUND_DETAIL = "http://192.168.1.10:8360/refund/get";
        public static String REFUND_LIST = "http://192.168.1.10:8360/refund/list";
        public static String SMS = "http://192.168.1.10:8360/sms/send";
        public static String UPLOAD_FILE = "http://192.168.1.10:8360/upload/file";
        public static String UPLOAD_ID_CARD = "http://192.168.1.10:8360/authentication/uploadIDCard";
        public static String USER_DETAIL = "http://192.168.1.10:8360/user/get";
        public static String USER_LOGIN = "http://192.168.1.10:8360/user/login";
        public static String USER_PASSWORD_RESET = "http://192.168.1.10:8360/user/resetPassword";
        public static String USER_PASSWORD_UPDATE = "http://192.168.1.10:8360/user/updatePassword";
        public static String USER_PAY_PASSWORD_RESET = "http://192.168.1.10:8360/user/resetPayPassword";
        public static String USER_REGISTER = "http://192.168.1.10:8360/user/register";
        public static String USER_SAVE = "http://192.168.1.10:8360/user/save";
        public static String USER_SUBORDINATE = "http://192.168.1.10:8360/user/subordinatePaginate";
        public static String USER_UPDATE = "http://192.168.1.10:8360/user/updateInfo";
        public static String VIOLATION_QUERY = "http://192.168.1.10:8360/violation/query";
        public static String VIOLATION_QUERY_HISTORY_CAR = "http://192.168.1.10:8360/violation/queryHistory";
        public static String VIOLATION_QUERY_PRICE = "http://192.168.1.10:8360/violation/getPrice";
        public static String VIOLATION_USER_QUERY_LOG = "http://192.168.1.10:8360/violation/queryLog";
        public static String VOUCHERS_LIST = "http://192.168.1.10:8360/finance/vouchersList";
    }

    public static class BUSINESS_TYPE {
        public static int ACCOUNT_ACTIVATION = 3;
        public static int ACCOUNT_ACTIVATION_PD = 6;
        public static int ACCOUNT_RECHARGE = 2;
        public static int AGENT_UPGRADE = 7;
        public static int ORDER_PAY = 1;
        public static int ORDER_PAY_PD = 5;
        public static int POINTS_RECHARGE = 4;
    }

    public static class COLORS {
        public static int BLACK = -13290187;
        public static int BLUE = -16355126;
//        public static int GRAY = R.color.error_color_material;
        public static int GREEN = -16609258;
        public static int LINE_BLUE = R.color.design_fab_shadow_mid_color;
        public static int LINE_GRAY = R.color.design_fab_stroke_end_inner_color;
        public static int ORANGE = -565705;
        public static int RED = -5636096;
        public static int WHITE = -1;
    }

    public static class ORDER_STATUS {
        public static final int CANCELED = 14;
        public static final int COMPLETE = 12;
        public static final int FILL_UPLOAD_DOCS = 6;
        public static final int HANDLING = 4;
        public static final int LOCKED = 3;
        public static final int WAITING_CHECK_DOCS = 2;
        public static final int WAITING_DISPATCH = 8;
        public static final int WAITING_PAY = 0;
        public static final int WAITING_UPLOAD_DOCS = 1;
    }

    public static class SMS_TYPE {
        public static String COMMON = "1";
        public static String RESET_PASSWORD = "2";
        public static String RESET_PAY_PASSWORD = "7";
    }

    public static class TAG {
        public static boolean IS_CHECK_ROLE = false;
        public static boolean IS_LOAD_QR_CODE = false;
        public static final boolean IS_SAVE_CAMERA_PICTURES = true;
    }

    public class FILE_NAME {
        public static final String IMG = "IMG";
        public static final String USER_AVATAR = "user_avatar";

        public FILE_NAME() {
        }
    }
}
