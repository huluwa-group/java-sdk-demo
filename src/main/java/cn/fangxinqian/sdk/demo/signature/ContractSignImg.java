package cn.fangxinqian.sdk.demo.signature;

import cn.fangxinqian.operator.sdk.seal.SealService;
import cn.fangxinqian.operator.sdk.seal.dto.CompanySealDTO;
import cn.fangxinqian.operator.sdk.seal.dto.PersonalSealDTO;
import cn.fangxinqian.operator.sdk.seal.vo.CompanySealResultVO;
import cn.fangxinqian.operator.sdk.seal.vo.PersonalSealVO;
import com.liumapp.qtools.file.base64.Base64FileTool;

import java.io.IOException;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019-10-15 4:10 下午
 * 签署图片生成
 */
public class ContractSignImg {
    /** 文件路径 **/
    private static String FILE_PATH = "./data/";

    /** 个人签章图片文件名称 **/
    private static String FILE_NAME_PERSONAL = "personal.png";

    /** 企业签章图片文件名称 **/
    private static String FILE_NAME_COMPANY = "company.png";

    /** 工单申请预留的手机号码 **/
    private static String PHONE = "157*****31";


    /**
     * 签章图片生成
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //flag为true表示调用企业公章生成，false表示调用个人签章生成
        boolean flag = false;
        if (flag) {
            companySignImg();
        } else {
            personalSignImg();
        }
    }


    /**
     * 生成企业签章图片
     * @throws IOException
     */
    private static void companySignImg() throws IOException {
        CompanySealDTO companySealDTO = new CompanySealDTO()
                .setPhone(PHONE)
                .setCompanyName("浙江葫芦娃网络集团有限公司")
                .setSerNo("123456789987")
                .setTitle("合同专用章");

        CompanySealResultVO resultVO = SealService.companySeal(companySealDTO);
        //状态为10000则表示生成成功，其余均表示失败
        if (resultVO.getCode().equals(10000)) {
            Base64FileTool.saveBase64File(resultVO.getBaseStr(),FILE_PATH+FILE_NAME_COMPANY);
            System.out.println("企业公章生成成功");
        } else {
            System.out.println("企业公章生成异常："+resultVO.getMsg());
        }
    }

    /**
     * 生成个人签章图片
     */
    private static void personalSignImg() throws IOException {
        PersonalSealDTO personalSealDTO = new PersonalSealDTO()
                .setPhone("15757125631")
                .setFontFamilyTag(3)
                .setFontText("张三丰");

        PersonalSealVO resultVO = SealService.personalSeal(personalSealDTO);
        //状态为10000则表示生成成功，其余均表示失败
        if (resultVO.getCode().equals(10000)) {
            Base64FileTool.saveBase64File(resultVO.getData(),FILE_PATH+FILE_NAME_PERSONAL);
            System.out.println("个人签章生成成功");
        } else {
            System.out.println("个人签章生成异常："+resultVO.getMsg());
        }
    }

}
