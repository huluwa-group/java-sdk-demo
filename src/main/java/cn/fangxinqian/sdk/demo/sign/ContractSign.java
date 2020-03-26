package cn.fangxinqian.sdk.demo.sign;

import cn.fangxinqian.operator.sdk.sign.SignService;
import cn.fangxinqian.operator.sdk.sign.dto.*;
import cn.fangxinqian.operator.sdk.sign.vo.SignResultVO;
import com.liumapp.qtools.file.base64.Base64FileTool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mqz
 * @date 2020/3/13 10:29 上午
 * @description 电子合同签署2.0版本
 */
public class ContractSign {

    /** 文件路径 **/
    private static String FILE_PATH = "./data/";

    /** 文件名称 **/
    private static String FILE_NAME = "test.pdf";

    /** 签署成功后的文件名 **/
    private static String FILE_NAME_SIGNED = "signed.pdf";

    /** 云储存地址 **/
    private static String OSS_PDF_URL = "http://fangxin-docs.oss-cn-hangzhou.aliyuncs.com/%E5%85%A8%E7%BD%91%E6%89%8B%E6%9C%BA%E4%B8%89%E8%A6%81%E7%B4%A0%E9%AA%8C%E8%AF%81.pdf";

    /** 企业或个人签章图片文件名称 **/
    private static String SIGNATURE = "company.png";

    /** 企业或个人签章图片文件名称 **/
    private static String SIGNATURE_PERSONAL = "personal-mqz.png";

    /** 合同签署的appid **/
    private static String APPID = "";

    /** 合同签署的appsecret **/
    private static String APPSECRET = "";

    /**
     * 合同签署接口2.0
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //返回base64，并且不带骑缝章
        //signForBase64();
        //返回云链接
        signForUrl();
    }

    /**
     * 签署，返回base64
     */
    private static void signForBase64() throws IOException {


        List<SignerArea> areaList = new ArrayList<SignerArea>();
        List<Signer> signerList = new ArrayList<Signer>();
        /**
         * 签署区域
         */
        SignerArea area = new SignerArea()
                            //签署区域左下⻆点X坐标
                            .setX1(442f)
                            //签署区域左下⻆点Y坐标
                            .setY1(399f)
                            //签署区域右上⻆点X坐标(建议在左下⻆X基础上加100)
                            .setX2(552f)
                            //签署区域右上⻆点X坐标(建议在左下⻆X基础上加100)
                            .setY2(434f)
                            //签署⻚数 第⼀⻚为1
                            .setPage(1);
        //存入list
        areaList.add(area);

        /**
         *  签署人信息
         */
        Signer signer = new Signer()
                        //真实姓名/公司名称
                        .setName("蒙大拿")
                        //该值控制骑缝章位置 ⼤于100 表示需要签署骑缝章 双⽅骑缝章位置间隔建议⼤于100 最⼤不能超过650
                        //.setGapMargin(200f)
                        //真实姓名/公司名称
                        .setIdcard("123456789987654321")
                        //签章图⽚⽂件(base64/云存储⽂件链接)
                        .setImg(Base64FileTool.FileToBase64(new File(FILE_PATH+SIGNATURE)))
                        //个⼈传⼿机号码，企业传法⼈⼿机号码
                        .setPhone("13100002222")
                        //签署区域
                        .setAreas(areaList);

        //存入list
        signerList.add(signer);

        /**
         * 签署具体参数
         */
        SignDTO signDTO = new SignDTO()
                            .setAppId(APPID)
                            .setAppSecret(APPSECRET)
                            .setContract(Base64FileTool.FileToBase64(new File(FILE_PATH + FILE_NAME)))
                            .setReturnType("0")
                            .setSigners(signerList);

        /**
         * 调用签署的方法
         */
        SignResultVO resultVO = SignService.doSign(signDTO);
        if (resultVO.getCode().equals("10000")) {
            Base64FileTool.saveBase64File(resultVO.getData(),FILE_PATH + FILE_NAME_SIGNED);
            System.out.println("合同签署成功");
        } else {
            System.out.println("合同签署异常：" + resultVO.getMessage());
        }
    }


    /**
     * 签署，返回云链接
     */
    private static void signForUrl() throws IOException {
        List<Signer> signerList = new ArrayList<Signer>();
        /**
         * 第一个签署人签署区域
         */
        SignerArea area = new SignerArea()
                //签署区域左下⻆点X坐标
                .setX1(442f)
                //签署区域左下⻆点Y坐标
                .setY1(150f)
                //签署区域右上⻆点X坐标(建议在左下⻆X基础上加100)
                .setX2(552f)
                //签署区域右上⻆点X坐标(建议在左下⻆X基础上加100)
                .setY2(250f)
                //签署⻚数 第⼀⻚为1
                .setPage(1);
        //存入list
        List<SignerArea> areaList = new ArrayList<SignerArea>();
        areaList.add(area);

        /**
         *  第一个签署人信息
         */
        Signer signer = new Signer()
                //真实姓名/公司名称
                .setName("蒙大拿")
                //该值控制骑缝章位置 ⼤于100 表示需要签署骑缝章 双⽅骑缝章位置间隔建议⼤于100 最⼤不能超过650
                .setGapMargin(320f)
                //真实姓名/公司名称
                .setIdcard("123456789987654321")
                //签章图⽚⽂件(base64/云存储⽂件链接)
                .setImg(Base64FileTool.FileToBase64(new File(FILE_PATH+SIGNATURE_PERSONAL)))
                //个⼈传⼿机号码，企业传法⼈⼿机号码
                .setPhone("13100002222")
                //签署区域
                .setAreas(areaList);
        //存入list
        signerList.add(signer);

        /**
         * 第二个签署人的签署区域
         */
        SignerArea area2 = new SignerArea()
                //签署区域左下⻆点X坐标
                .setX1(442f)
                //签署区域左下⻆点Y坐标
                .setY1(150f)
                //签署区域右上⻆点X坐标(建议在左下⻆X基础上加100)
                .setX2(552f)
                //签署区域右上⻆点X坐标(建议在左下⻆X基础上加100)
                .setY2(250f)
                //签署⻚数 第⼀⻚为1
                .setPage(2);
        //存入list
        List<SignerArea> areaList2 = new ArrayList<SignerArea>();
        areaList2.add(area2);

        /**
         * 第二个签署人
         */
        Signer singer2 = new Signer()
                    //真实姓名/公司名称
                    .setName("浙江葫芦娃网络集团有限公司")
                    //该值控制骑缝章位置 ⼤于100 表示需要签署骑缝章 双⽅骑缝章位置间隔建议⼤于100 最⼤不能超过650
                    .setGapMargin(420f)
                    //真实姓名/公司名称
                    .setIdcard("432112345678998765")
                    //签章图⽚⽂件(base64/云存储⽂件链接)
                    .setImg(Base64FileTool.FileToBase64(new File(FILE_PATH+SIGNATURE)))
                    //个⼈传⼿机号码，企业传法⼈⼿机号码
                    .setPhone("15100002222")
                    //签署区域
                    .setAreas(areaList2);
        //存入list
        signerList.add(singer2);


        /**
         * 签署具体参数
         */
        SignDTO sign2DTO = new SignDTO()
                .setAppId(APPID)
                .setAppSecret(APPSECRET)
                .setContract(OSS_PDF_URL)
                .setReturnType("1")
                .setSigners(signerList);

        /**
         * 调用签署的方法
         */
        SignResultVO resultVO = SignService.doSign(sign2DTO);
        if (resultVO.getCode().equals("10000")) {
            System.out.println(resultVO.getData());
            System.out.println(resultVO.getMessage());
            System.out.println("合同签署成功");
        } else {
            System.out.println("合同签署异常：" + resultVO.getMessage());
        }
    }




}
