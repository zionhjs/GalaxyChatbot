package com.galaxy.demo.Constant;

public final class Constant {

    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_PATTERN_YMD = "yyyy-MM-dd";

    public static final String CITY_NAME="task_city_name";

    public static final String REGION_NAME="task_region_name";

    public static final String AREA_NAME="task_area_name";

    public static final String BAIDU_AK="mK9nSOduGP38GgZZK8jhr78NBpoPZ008";

    public static final String BAIDU_URL="http://api.map.baidu.com/geocoder/v2/";

    public static final String REMARK_OPERATION = "remark";

    public static final String OPTIONS_METHOD = "OPTIONS";

    public static final String REDIS_KEY_LOGIN = "galaxy_mag_login";

    public static final String REDIS_KEY_VERFIY = "ytotask_mag_verfiycode";

    public static final String TOKEN_NAME = "accessToken";

    public static final long verifyCodeForTempValidTime = 5 * 60 * 1000;

    public static final String CREATE_UPDATE_BY="-10000";

    public static final String INITIAL_PWD="12345678";

    //待审核（总）
    public static final String DATA_WAIT_AUDIT_COUNT = "data_wait_audit_count";
    //待审核商家
    public static final String DATA_WAIT_AUDIT_MERCHANT = "data_wait_audit_merchant";
    //待审核业务员任务
    public static final String DATA_WAIT_AUDIT_SALE_MISSION = "data_wait_audit_sale_mission";
    //待审核任务
    public static final String DATA_WAIT_AUDIT_TASK = "data_wait_audit_task";
    //待审核财务后台加款
    public static final String DATA_WAIT_AUDIT_BACKSTAGE_SUPPORTER_MONEY = "data_wait_audit_backstage_supporter_money";
    //通过审核商家入驻（总）
    public static final String DATA_MERCHANT_ENTER_COUNT = "data_merchant_enter_count";
    //通过审核业务员总量
    public static final String DATA_SALESMAN_COUNT = "data_salesman_count";
    //通过审核完成业务员任务量（总）
    public static final String DATA_ACCOMPLISH_TASK_COUNT = "data_accomplish_task_count";
    //通过审核发布任务量（总）
    public static final String DATA_ISSUE_TASK_COUNT = "data_issue_task_count";
    //提现金额（总）
    public static final String DATA_WITHDRAW_DEPOSIT_MONEY_COUNT = "data_withdraw_deposit_money_count";
    //通过审核总预付款（商家充值付款）
    public static final String DATA_MERCHANT_MONEY_COUNT = "data_merchant_money_count";

    /** 正常。 */
    public static final Boolean DELETE_SIGN_NORMAL = false;
    /** 已删除。 */
    public static final Boolean DELETE_SIGN_DELETE = true;

    public static final Integer VERSION_SIGN_NORMAL=0;

    public static String encoding = "UTF-8";

    //成功
    public static final String SUCCESS = "操作成功";

    //失败
    public static final String ERROR = "操作失败";

    //文章管理模块
    public static final String ARTICLE = "文章管理";

    //商家管理模块
    public static final String MERCHANT = "商家管理";

    //任务管理模块
    public static final String TASK = "任务管理";

    //轮播图管理
    public static final String SLIEDE_SHOW = "轮播图管理";

    //业务员管理
    public static final String SALES_MAN = "业务员管理";

    //商家类型管理
    public static final String MERCHANT_TYPE = "商家类型管理";

    //业务员类型管理
    public static final String SALE_TYPE = "业务员类型管理";

    //审核类型管理
    public static final String AUDIT_TYPE= "审核类型管理";

    //任务类型管理
    public static final String MISSION_TYPE ="任务类型管理";

    //审核管理
    public static final String AUDIT = "审核管理";

    //下载管理
    public static final String DOWNLOAD = "下载管理";

    //操作功能（删除）
    public static final String DELETE = "删除";

    //操作功能（修改）
    public static final String UPDATE = "修改";

    //操作功能（新增）
    public static final String INSERT = "新增";

    /**
     * httpclient连接池最大连接数200
     */
    public static final Integer MAX_TOTAL = 300;

    /**
     * httpclient默认的每个路由的最大连接数100
     */
    public static final Integer MAX_PERROUTE = 200;

    public static final String LOG_MARK = "==>>";

    public static final String LEFT_BRACE = "{";

    public static final String RIGHT_BRACE = "}";

    public static final String BLANK = "";

    public static final String ACCESS_TOKEN="JS_ACCESS_TOKEN";

    public static final String param_order = "transType";

    public static final String AMPERSAND = "&";

    /*-----------------rabbitmq------------------------------*/

    public static final String phone_fanouta_queue="phone.fanouta.queue";
    public static final String phone_fanoutb_queue="phone.fanoutb.queue";
    public static final String phone_fanoutc_queue="phone.fanoutc.queue";
    public static final String phone_fanoutaExchange="fanoutaExchange";
    public static final String phone_fanoutbExchange="fanoutbExchange";
    public static final String phone_fanoutcExchange="fanoutcExchange";


    /*-----------------rabbitmq------------------------------*/


    public static final String GATHER_REDIS_FAIL="gather_redis_fail";

    public static final String CONSUM_REDIS_FAIL="consum_redis_fail";

    public static final String CONSUM_ERROR_REDIS_FAIL="consum_error_redis_fail";

    public static final String CONSUM_PHONE_COUNT="consum_phone_count";

    public static final String CONSUM_REDIS_AREA="consum_redis_area";

    public static final String CONSUM_REDIS_CITY="consum_redis_city";

    public static final String CONSUM_REDIS_PROVICE="consum_redis_provice";

    public static final String CONSUM_REDIS_QUERY="consum_redis_query";

    public static final String ERROR_REJECT_REDIS_FAIL="error_reject_redis_fail";

    /*------------------------------------------rabbitmq结束-----------------------------------------------*/



}
