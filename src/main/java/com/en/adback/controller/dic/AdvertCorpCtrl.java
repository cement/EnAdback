package com.en.adback.controller.dic;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.dic.AdvertCorp;
import com.en.adback.service.dic.IAdvertCorpService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/9.
 */
@Api(value="广告公司管理",tags={"广告公司管理webapi 接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/advertCorp", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")

public class AdvertCorpCtrl {
    
    @Autowired
    private IAdvertCorpService svr;
    @Autowired
    private UserLogs ulogs;
    @Autowired
    private AdvertCorpLogs logs;

    @ApiOperation( value = "查询广告公司信息",notes = "" +
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
    @GetMapping(value = "/getAdvertCorpList")
    public MessageModel getAdvertCorpList(int pageNo,int pageSize,String loginUserId,
                                          String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        re.put("pageBegin",(pageNo-1)*pageSize);
        re.put("pageSize",pageSize);
        List<AdvertCorp> list = svr.getAdvertCorpList(re);
        re.clear();
        re.put("list",list);
        m.setData(re);
        m.setResultCode(list.size()>0?"1":"2");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询广告公司");
        return m;
    }
    
    @GetMapping(value = "/getAdvertCorpTotal")
    public MessageModel getAdvertCorpTotal(){
        MessageModel m = new MessageModel();
        int total = svr.getAdvertCorpTotal();
        m.setData(total);
        m.setResultCode(total>0?"1":"2");
        m.setResultMsg("ok");
        return m;
        
    }

    //根据公司编号查询该社会统一信用代码是否已存在
    @ApiOperation( value = "插入广告公司信息",notes = "" +
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
    @GetMapping(value = "/findByadCorpId")
    public MessageModel findByadCorpId(String adCorpId,String loginUserId,
                                       String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,String > re = new HashMap<>();
        re.put("adCorpId",adCorpId);
        List<AdvertCorp> list = svr.findByadCorpId(re);
        m.setData(list.size());
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"根据统一社会信用代码查询");
        return m;
    }


    @ApiOperation( value = "插入广告公司信息",notes = "" +
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
    @PostMapping(value = "/insertAdvertCorp")
    public MessageModel insertAdvertCorp(@RequestBody AdvertCorp advertCorp,HttpServletRequest request){
        MessageModel m = new MessageModel();
        int i = svr.insertAdvertCorp(advertCorp);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip = Common.getIpAddr(request);
        logs.postAdvertCorpLogs(advertCorp,ip,"新增广告公司信息");
        return m;
    }

    @ApiOperation( value = "修改广告公司信息",notes = "" +
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
    @PostMapping(value = "/updateAdCorp")
    public MessageModel updateAdCorp(@RequestBody AdvertCorp advertCorp,HttpServletRequest request){
        MessageModel m = new MessageModel();
        int i = svr.updateAdvertCorp(advertCorp);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip = Common.getIpAddr(request);
        logs.postAdvertCorpLogs(advertCorp,ip,"修改广告公司信息");
        return m;
    }

    @ApiOperation( value = "删除广告公司信息",notes = "" +
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
    @GetMapping(value = "/deleteAdCorp")
    public MessageModel deleteAdCorp(String adCorpId,String loginUserId,
                                     String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,String > re = new HashMap<>();
        re.put("adCorpId",adCorpId);
        int i = svr.deleteAdCorp(re);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"删除广告公司");
        return m;
    }

    //查询公司 -- 广告公司选择用
    @GetMapping(value = "/advertCorpChoosedList")
    public  MessageModel  advertCorpChoosedList() {
        MessageModel m = new MessageModel();
        Map<String,Object > re = new HashMap<>();
        List<AdvertCorp> list = svr.advertCorpChoosedList();
        re.put("list",list);
        m.setData(re);
        m.setResultCode("1");
        m.setResultMsg("ok");
        return m;
    }
}
