package cn.fangxinqian.sdk.demo.identity;

import cn.fangxinqian.operator.sdk.identity.IdentityChkService;
import cn.fangxinqian.operator.sdk.identity.dto.*;
import cn.fangxinqian.operator.sdk.identity.vo.CompanyInfoVO;
import cn.fangxinqian.operator.sdk.identity.vo.IdentityChkResultVO;
import cn.fangxinqian.operator.sdk.identity.vo.IdentityPoliceChkResultVO;

import java.io.IOException;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019-10-15 5:10 下午
 * 用户认证
 */
public class UserIdentity {

    /** appid **/
    private static String APPID = "";

    /** appsecret **/
    private static String APPSECRET = "";


    public static void main(String[] args) throws IOException {


        //企业工商信息核验
        //companyInfo();

        //公安部实名认证
        policeChk();

        //手机三要素认证
        //phoneIdentity();

        //银行卡三要素
        //bankCard3Chk();

        //银行卡四要素
        //bankCard4Chk();

    }



    /**
     * 企业工商信息查询
     */
    private static void companyInfo() throws IOException {
        CompanyInfoDTO companyInfoDTO = new CompanyInfoDTO()
                .setAppId(APPID)
                .setAppSecret(APPSECRET)
                .setCompanyName("企业名称");

        CompanyInfoVO companyInfoVO = IdentityChkService.queryCompanyInfo(companyInfoDTO);

        //由于接口异常返回的状态变量名是用status来接收的，为了统一状态码都用code来接受
        //我在getCode()方法中加了判断，如果code == null 就表示调用异常  我就将status的值赋予给了code 所以在debug的模式下
        //看到的phoneChkResultVO对象的code和message的值为空 但是在get方法获取的时候就可以获取到值
        if (companyInfoVO.getCode().equals("10000")) {
            System.out.println("企业工商信息查询成功");
            System.out.println(companyInfoVO);
        } else {
            System.out.println("企业工商信息查询异常: " +companyInfoVO.getMessage());
        }

    }


    /**
     * 公安部实名认证
     */
    public static void policeChk() throws IOException {
        PoliceChkDTO policeChkDTO = new PoliceChkDTO()
                .setAppId(APPID)
                .setAppSecret(APPSECRET)
                .setName("张三")
                .setIdentityNumber("362202*******2315");

        IdentityPoliceChkResultVO policeChkResultVO =  IdentityChkService.policeChk(policeChkDTO);
        //由于接口调用异常返回的状态变量名是用status来接收的，为了统一状态码都用code来接受
        //我在getCode()方法中加了判断，如果code == null 就表示调用异常  我就将status的值赋予给了code 所以在debug的模式下
        //看到的phoneChkResultVO对象的code和message的值为空 但是在get方法获取的时候就可以获取到值
        if (policeChkResultVO.getCode().equals("10000")) {
            if (policeChkResultVO.getData().getResult().equals(1)) {
                System.out.println("公安部实名认证通过");
                System.out.println(policeChkResultVO);
            } else {
                System.out.println("公安部实名认证不通过:"+policeChkResultVO.getMessage());
            }
        } else {
            System.out.println("调用接口异常,不扣余额 异常如下:"+policeChkResultVO.getMessage());
        }
    }


    /**
     * 手机三要素认证
     */
    private static void phoneIdentity() throws IOException {
        PhoneChkDTO phoneChkDTO = new PhoneChkDTO()
                .setAppId(APPID)
                .setAppSecret(APPSECRET)
                //真实姓名
                .setName("张三")
                //手机号码
                .setPhone("159000000")
                //身份证号码
                .setIdentityNumber("123456789987654321");

        IdentityChkResultVO phoneChkResultVO = IdentityChkService.phoneChk(phoneChkDTO);

        //由于接口调用异常返回的状态变量名是用status来接收的，为了统一状态码都用code来接受
        //我在getCode()方法中加了判断，如果code == null 就表示调用异常  我就将status的值赋予给了code 所以在debug的模式下
        //看到的phoneChkResultVO对象的code和message的值为空 但是在get方法获取的时候就可以获取到值
        if (phoneChkResultVO.getCode().equals("10000")) {
            if (phoneChkResultVO.getData().getState().equals(1)) {
                System.out.println("手机三要素实名认证通过");
            } else {
                System.out.println("手机三要素实名认证不通过:"+phoneChkResultVO.getMessage());
            }
        } else {
            System.out.println("调用接口异常,不扣余额 异常如下:"+phoneChkResultVO.getMessage());
        }
    }

    /**
     * 银行卡三要素
     */
    private static void bankCard3Chk() throws IOException {
        BankCard3ChkDTO bankCard3ChkDTO = new BankCard3ChkDTO()
                .setAppId(APPID)
                .setAppSecret(APPSECRET)
                .setName("张三")
                .setIdentityCode("3622021******3126")
                .setBankcardNumber("6222031******5346");

        IdentityChkResultVO phoneChkResultVO = IdentityChkService.bankcard3Chk(bankCard3ChkDTO);
        //由于接口异常返回的状态变量名是用status来接收的，为了统一状态码都用code来接受
        //我在getCode()方法中加了判断，如果code == null 就表示调用异常  我就将status的值赋予给了code 所以在debug的模式下
        //看到的phoneChkResultVO对象的code和message的值为空 但是在get方法获取的时候就可以获取到值
        if (phoneChkResultVO.getCode().equals("10000")) {
            if (phoneChkResultVO.getData().getState().equals(1)) {
                System.out.println("银行卡三要素实名认证通过");
            } else {
                System.out.println("银行卡三要素实名认不通过:"+phoneChkResultVO.getMessage());
            }
        } else {
            System.out.println("调用接口异常,不扣余额 异常如下:"+phoneChkResultVO.getMessage());
        }
    }

    /**
     * 银行卡四要素
     */
    private static void bankCard4Chk() throws IOException {
        BankCard4ChkDTO bankCard4ChkDTO = new BankCard4ChkDTO()
                .setAppId(APPID)
                .setAppSecret(APPSECRET)
                .setName("聂志良")
                .setIdentityCode("362202******15")
                .setBankcardNumber("622203******5347")
                .setPhoneNumber("159*****143");

        IdentityChkResultVO phoneChkResultVO = IdentityChkService.bankcard4Chk(bankCard4ChkDTO);
        //由于接口异常返回的状态变量名是用status来接收的，为了统一状态码都用code来接受
        //我在getCode()方法中加了判断，如果code == null 就表示调用异常  我就将status的值赋予给了code 所以在debug的模式下
        //看到的phoneChkResultVO对象的code和message的值为空 但是在get方法获取的时候就可以获取到值
        if (phoneChkResultVO.getCode().equals("10000")) {
            if (phoneChkResultVO.getData().getState().equals(1)) {
                System.out.println("银行卡四要素实名认证通过");
            } else {
                System.out.println("银行卡四要素实名认不通过:"+phoneChkResultVO.getMessage());
            }
        } else {
            System.out.println("调用接口异常,不扣余额 异常如下:"+phoneChkResultVO.getMessage());
        }
    }


}
