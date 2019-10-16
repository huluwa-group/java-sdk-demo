package cn.fangxinqian.sdk.demo.sign;

import cn.fangxinqian.operator.sdk.sign.SignService;
import cn.fangxinqian.operator.sdk.sign.dto.SignDTO;
import cn.fangxinqian.operator.sdk.sign.dto.Signer;
import cn.fangxinqian.operator.sdk.sign.dto.SignerArea;
import cn.fangxinqian.operator.sdk.sign.dto.SignerInfo;
import cn.fangxinqian.operator.sdk.sign.vo.SignResultVO;
import com.liumapp.qtools.file.base64.Base64FileTool;

import java.io.File;
import java.io.IOException;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019-10-15 4:06 下午
 * 合同签署
 */
public class ContractSign {

    /** 文件路径 **/
    private static String FILE_PATH = "./data/";

    /** 文件名称 **/
    private static String FILE_NAME = "test.pdf";

    /** 签署成功后的文件名 **/
    private static String FILE_NAME_SIGNED = "signed.pdf";

    /** 个人签章图片文件名称 **/
    private static String PERSONAL_SIGNATURE = "personal.png";

    /** 企业签章图片文件名称 **/
    private static String COMPANY_SIGNATURE = "company.png";

    /** 合同签署的appid **/
    private static String APPID = "";

    /** 合同签署的appsecret **/
    private static String APPSECRET = "";

    /**
     * 合同签署接口
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        boolean flag = true;
        if (flag) {
            companySign();
        } else {
            personalSign();
        }

    }

    /**
     * 个人签署
     */
    private static void personalSign() throws IOException {
        //签署人信息
        Signer signer = new Signer()
                //签署人真实姓名
                .setName("张三丰")
                //签署人身份证号码
                .setIdentityCode("123456789987654321")
                //签署人邮箱
                .setEmail("zhangsanfeng@fangxinqian.cc")
                //签署人手机号码
                .setMobile("15900000000");

        //签署区域
        SignerArea area = new SignerArea()
                //签署区域左下角x坐标
                .setFirstX(442f)
                //签署区域左下角y坐标
                .setFirstY(399f)
                //签署区域右上角x坐标
                .setSecondX(552f)
                //签署区域右上角y坐标
                .setSecondY(434f)
                //pdf文件第几页
                .setPage(1)
                //签章图片
                .setSignPic(Base64FileTool.FileToBase64(new File(FILE_PATH+PERSONAL_SIGNATURE)));


        SignerInfo signerInfo = new SignerInfo()
                //个人签署
                .setType((byte)1)
                //签署人信息
                .setPersonal(signer)
                //签署人签署区域
                .setArea(area);

        //签署具体参数
        SignDTO signDTO = new SignDTO()
                .setAppId(APPID)
                .setAppSecret(APPSECRET)
                //合同名称
                .setTitle("测试合同")
                //合同文件的base64
                .setContent(Base64FileTool.FileToBase64(new File(FILE_PATH + FILE_NAME)))
                .setSigner(signerInfo);

        //调用签署的方法
        SignResultVO resultVO = SignService.doSign_1(signDTO);
        if (resultVO.getStatus().equals(10001)) {
            Base64FileTool.saveBase64File(resultVO.getContent(),FILE_PATH + FILE_NAME_SIGNED);
            System.out.println("合同签署成功");

        } else {
            System.out.println("合同签署异常：" + resultVO.getMsg());
        }
    }

    /**
     * 企业签署
     */
    private static void companySign() throws IOException {
        //签署人信息
        Signer signer = new Signer()
                .setName("浙江葫芦娃网络集团有限公司")
                .setCreditCode("123456789987")
                .setEmail("huluwa@huluwa.cc")
                .setMobile("15900000000");

        //签署区域
        SignerArea area = new SignerArea()
                .setFirstX(50f)
                .setFirstY(50f)
                .setSecondX(100f)
                .setSecondY(100f)
                //签署所在的pdf页数
                .setPage(1)
                .setSignPic(Base64FileTool.FileToBase64(new File(FILE_PATH+COMPANY_SIGNATURE)));

        //签署
        SignerInfo signerInfo = new SignerInfo()
                //企业签署
                .setType((byte)2)
                .setCompany(signer)
                .setArea(area);

        //签署具体参数
        SignDTO signDTO = new SignDTO()
                .setAppId(APPID)
                .setAppSecret(APPSECRET)
                .setTitle("测试合同")
                .setContent(Base64FileTool.FileToBase64(new File(FILE_PATH + FILE_NAME)))
                .setSigner(signerInfo);

        //调用签署的方法
        SignResultVO resultVO = SignService.doSign_1(signDTO);
        if (resultVO.getStatus().equals(10001)) {
            Base64FileTool.saveBase64File(resultVO.getContent(),FILE_PATH + FILE_NAME_SIGNED);
            System.out.println("合同签署成功");

        } else {
            System.out.println("合同签署异常：" + resultVO.getMsg());
        }
    }


}
