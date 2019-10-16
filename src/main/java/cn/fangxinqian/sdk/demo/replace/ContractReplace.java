package cn.fangxinqian.sdk.demo.replace;

import cn.fangxinqian.operator.sdk.replace.TemplateService;
import cn.fangxinqian.operator.sdk.replace.dto.KeyValue;
import cn.fangxinqian.operator.sdk.replace.dto.TemplateDTO;
import cn.fangxinqian.operator.sdk.replace.vo.TemplateResultVO;
import com.liumapp.qtools.file.base64.Base64FileTool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019-10-15 3:51 下午
 * 合同关键字替换
 */
public class ContractReplace {

    /** 文件路径 **/
    private static String FILE_PATH = "./data/";

    /** 文件名称 **/
    private static String FILE_NAME = "test.pdf";

    /** 替换后的文件名 **/
    private static String FILE_NAME_REPLACE = "replace.pdf";

    /** 工单申请预留的手机号码 **/
    private static String PHONE = "1575****31";

    /**
     * 合同关键字替换
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //这些都是要替换的对象 oldText为pdf中需要替换的关键字，newText为该关键字替换后的内容
        KeyValue keyValueBO1 = new KeyValue("name","张三");
        KeyValue keyValueBO2 = new KeyValue("sfzno","123456789987654321");
        KeyValue keyValueBO3 = new KeyValue("Y","2019");
        KeyValue keyValueBO4 = new KeyValue("M","10");
        KeyValue keyValueBO5 = new KeyValue("D","15");

        List<KeyValue> list = new ArrayList<KeyValue>(5);
        list.add(keyValueBO1);
        list.add(keyValueBO2);
        list.add(keyValueBO3);
        list.add(keyValueBO4);
        list.add(keyValueBO5);

        TemplateDTO templateDTO = new TemplateDTO()
                .setFileType(1)
                .setPhone(PHONE)
                .setFileBase64(Base64FileTool.FileToBase64(new File(FILE_PATH+FILE_NAME)))
                .setKeyValues(list);

        //调用sdk的关键字替换方法
        TemplateResultVO templateResultVO = TemplateService.contentReplace(templateDTO);

        //如果状态为10000，则表示替换成功 其余状态码均表示替换失败
        if (templateResultVO.getCode().equals(10000)) {
            Base64FileTool.saveBase64File(templateResultVO.getData(),FILE_PATH+FILE_NAME_REPLACE);
            System.out.println("合同文件关键字替换成功");
        } else {
            System.out.println("替换合同文件异常：" + templateResultVO.getMsg());
        }

    }

}
