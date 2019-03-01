package com.en.adback.controller.dic;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.dic.Blank;
import com.en.adback.service.dic.IBlankService;
import io.swagger.annotations.*;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/10.
 */
@Api(value="行业管理",tags={"行业管理webapi 接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/blank", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class BlankCtrl {

    @Autowired
    private IBlankService svr;
    @Autowired
    private UserLogs ulogs;
    @Autowired
    private BlankLogs logs;

    @ApiOperation( value = "查询品牌信息",notes = "" +
            " 返回字段：{" +
            "   data : null ,   " +
            "    resultCode," +
            "    resultMsg : 'ok' ：成功 ，否则返回错误信息" +
            "}")
    @ApiImplicitParams({
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getBlankList")
    public MessageModel getBlankList(int pageNo,int pageSize,String loginUserId,
                                     String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        re.put("pageBegin",(pageNo-1)*pageSize);
        re.put("pageSize",pageSize);
        List<Blank> list = svr.getBlankList(re);
        re.clear();
        re.put("list",list);
        m.setData(re);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询所有品牌信息");
        return m;
    }

    @GetMapping(value = "/getBlankListTotal")
    public MessageModel getBlankListTotal(){
        MessageModel m = new MessageModel();
        int total = svr.getBlankListTotal();
        m.setData(total);
        m.setResultCode("1");
        m.setResultMsg("ok");
        return m;
    }

    @ApiOperation( value = "插入品牌信息",notes = "" +
            " 返回字段：{" +
            "   data : null ,   " +
            "    resultCode," +
            "    resultMsg : 'ok' ：成功 ，否则返回错误信息" +
            "}")
    @ApiImplicitParams({
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value = "/insertBlank")
    public synchronized MessageModel insertBlank(@RequestBody Blank blank,HttpServletRequest request){
        MessageModel m = new MessageModel();
        //取出数据库中blankId最大值，如为空  则赋值T0001 如果不为空  则数值加一
        String blankId = svr.getMaxBlankId();
        if(null==blankId){
            blankId = "B0001";
        }else{
            blankId = blankId.substring(1);
            int id = Integer.parseInt(blankId);
            id = id + 1;
            int length = String.valueOf(id).length();
            blankId = "B";
            if(length<4){
                for(int i =0;i<4-length;i++){
                    blankId += "0";
                }
            }
            blankId = blankId + id;
        }
        blank.setBlankId(blankId);
        int i = svr.upsertBlank(blank);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip = Common.getIpAddr(request);
        logs.postBlankLogs(blank,ip,"新增品牌信息");
        return m;
    }


    @ApiOperation( value = "修改品牌信息",notes = "" +
            " 返回字段：{" +
            "   data : null ,   " +
            "    resultCode," +
            "    resultMsg : 'ok' ：成功 ，否则返回错误信息" +
            "}")
    @ApiImplicitParams({
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value = "/updateBlank")
    public synchronized MessageModel updateBlank(@RequestBody Blank blank,HttpServletRequest request){
        MessageModel m = new MessageModel();
        int i = svr.upsertBlank(blank);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip = Common.getIpAddr(request);
        logs.postBlankLogs(blank,ip,"修改品牌信息");
        return m;
    }

    @ApiOperation( value = "删除品牌信息",notes = "" +
            " 返回字段：{" +
            "   data : null ,   " +
            "    resultCode," +
            "    resultMsg : 'ok' ：成功 ，否则返回错误信息" +
            "}")
    @ApiImplicitParams({
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/deleteBlank")
    public MessageModel deleteBlank(String blankId,String loginUserId,
                                    String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,String> re = new HashMap<>();
        re.put("blankId",blankId);
        int i = svr.deleteBlank(re);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"删除品牌信息");
        return m;
    }

}
