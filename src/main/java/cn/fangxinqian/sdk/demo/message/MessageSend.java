package cn.fangxinqian.sdk.demo.message;

import cn.fangxinqian.operator.sdk.sms.SmsService;
import cn.fangxinqian.operator.sdk.sms.dto.SmsDTO;
import cn.fangxinqian.operator.sdk.sms.vo.SmsResultVO;

import java.io.IOException;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019-10-16 9:04 上午
 * 短信发送
 */
public class MessageSend {

    /** appid **/
    private static String APPID = "";

    /** appsecret **/
    private static String APPSECRET = "";


    /**
     * 短信发送
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        SmsDTO smsDTO = new SmsDTO();
        smsDTO.setAppId(APPID)
                .setAppSecret(APPSECRET)
                .setPhone("15900610143")
                .setMsg("【葫芦娃】您好，您的验证码是123456");
        SmsResultVO smsResultVO = SmsService.sendMsg(smsDTO);
        if (smsResultVO.getCode().equals("0")) {
            System.out.println("短信发送成功");
        } else {
            System.out.println("短信发送失败:"+smsResultVO.getErrorMsg());
        }
    }
}
