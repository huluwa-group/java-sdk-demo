package cn.fangxinqian.sdk.demo.convert;

import cn.fangxinqian.operator.sdk.convert.FileConvertService;
import cn.fangxinqian.operator.sdk.convert.dto.ConvertDTO;
import cn.fangxinqian.operator.sdk.replace.vo.ConvertVO;
import com.liumapp.qtools.file.base64.Base64FileTool;

import java.io.File;
import java.io.IOException;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019-10-15 3:32 下午
 * 文件转换
 */
public class FileConvert {
    /** 文件路径 **/
    private static String FILE_PATH = "./data/";

    /** 文件名称 **/
    private static String FILE_NAME = "test.doc";

    /** 转换后的文件名 **/
    private static String FILE_NAME_CONVERT = "test.pdf";

    /** 工单申请预留的手机号码 **/
    private static String PHONE = "157****631";

    /**
     * 文件转换pdf
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ConvertDTO convertDTO = new ConvertDTO();
        convertDTO.setPhone(PHONE)
                .setBase64(Base64FileTool.FileToBase64(new File(FILE_PATH+FILE_NAME)));
        //调用sdk的文件转换方法
        ConvertVO convertVO = FileConvertService.convert(convertDTO);
        //只有状态为10000时是转换成功，其余状态码均为失败
        if (convertVO.getCode().equals(10000)) {
            //将得到的base64转换为pdf文件保存到data目录下
            Base64FileTool.saveBase64File(convertVO.getData(),FILE_PATH+FILE_NAME_CONVERT);
            System.out.println("文档转换成功");
        } else {
            System.err.println("文档转换异常,错误信息如下: " + convertVO.getMessage());
        }
    }
}
