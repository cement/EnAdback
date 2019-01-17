package com.en.adback.controller;


import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.advertmgr.Advert;
import com.en.adback.entity.advertmgr.AdvertFiles;
import com.en.adback.entity.advertmgr.AdvertMedia;
import com.en.adback.entity.advertmgr.AdvertStateHis;
import com.en.adback.entity.dic.AdvertCorp;
import com.en.adback.service.advertmgr.IAdvertService;
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

@Api(value="广告查询统计",tags={"广告查询统计webapi接口"})
@RestController
@RequestMapping(value = "/api/advert", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class AdvertCtrl {
    @Autowired
    private IAdvertService svr;
    @Autowired
    private AdvertLogs logs;
    @Autowired
    private UserLogs ulogs;


    @ApiOperation( value = "广告按条件查询",notes = "广告按条件查询" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state", value = "广告当前状态(1. 加入未提交审核，2.审核中，3.审核通过，4.审核不通过,5.设置策略,6.待分发， 7.已下发到设备,8.替换9.被替换，10.自动下刊,11.手动下刊)", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "advertName", value = "广告名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tradeId", value = "行业ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uploadTimeBegin", value = "上传日期起", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uploadTimeEnd", value = "上传日期止", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = false, dataType = "int", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/advertList")
    public MessageModel advertList(String state , String advertName,String tradeId,String adCorpName,String blankId ,
                                   String uploadTimeBegin,String uploadTimeEnd,int pageSize,int pageNo,String loginUserId,
                                   String loginGroupRoleId,String loginRoleId,HttpServletRequest request)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("state", state);
        map.put("advertName",advertName);
        map.put("tradeId",tradeId);
        map.put("adCorpName",adCorpName);
        map.put("blankId",blankId);
        map.put("uploadTimeBegin",uploadTimeBegin);
        //把截止日期加长一天
        if (uploadTimeEnd !=null && !uploadTimeEnd.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(uploadTimeEnd).getTime() + 24 * 3600 * 1000);
                uploadTimeEnd = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//
        }

        map.put("uploadTimeEnd",uploadTimeEnd);
        map.put("pageSize",pageSize);
        map.put("pageBegin",(pageNo-1)*pageSize);
        List<Advert> list = svr.advertList(map);
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"广告查询");
        return model;
    }


    @ApiOperation( value = "广告按条件查询总数",notes = "广告按条件查询总数" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state", value = "广告当前状态(1. 加入未提交审核，2.审核中，3.审核通过，4.审核不通过,5.设置策略,6.待分发， 7.已下发到设备,8.替换9.被替换，10.自动下刊,11.手动下刊)", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "advertName", value = "广告名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tradeId", value = "行业ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uploadTimeBegin", value = "上传日期起", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uploadTimeEnd", value = "上传日期止", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/advertCount")
    public MessageModel advertCount(String state , String advertName,String tradeId,String adCorpName,String blankId ,
                                    String uploadTimeBegin,String uploadTimeEnd)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("state",state);
        map.put("advertName",advertName);
        map.put("tradeId",tradeId);
        map.put("adCorpName",adCorpName);
        map.put("blankId",blankId);
        map.put("uploadTimeBegin",uploadTimeBegin);
        //把截止日期加长一天
        if (uploadTimeEnd !=null && !uploadTimeEnd.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(uploadTimeEnd).getTime() + 24 * 3600 * 1000);
                uploadTimeEnd = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//
        }

        map.put("uploadTimeEnd",uploadTimeEnd);
        int total = svr.advertCount(map);
        map.put("total",total);
        model.setData(map);
        model.setResultMsg("success");
        return model;
    }


    @ApiOperation( value = "条件查询，广告审核",notes = "条件查询，广告审核" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertName", value = "广告名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tradeId", value = "行业ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uploadTimeBegin", value = "上传日期起", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uploadTimeEnd", value = "上传日期止", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = false, dataType = "int", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/advertAuditList")
    public MessageModel advertAuditList(String advertName,String tradeId,String adCorpName,String blankId ,
                                        String uploadTimeBegin,String uploadTimeEnd,int pageSize,int pageNo,String loginUserId,
                                        String loginGroupRoleId,String loginRoleId,HttpServletRequest request)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("advertName",advertName);
        map.put("tradeId",tradeId);
        map.put("adCorpName",adCorpName);
        map.put("blankId",blankId);
        map.put("uploadTimeBegin",uploadTimeBegin);
        //把截止日期加长一天
        if (uploadTimeEnd !=null && !uploadTimeEnd.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(uploadTimeEnd).getTime() + 24 * 3600 * 1000);
                uploadTimeEnd = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//
        }
        map.put("uploadTimeEnd",uploadTimeEnd);
        map.put("pageSize",pageSize);
        map.put("pageBegin",(pageNo-1)*pageSize);
        List<Advert> list = svr.advertAuditList(map);
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"广告审核查询");
        return model;
    }


    @ApiOperation( value = "条件查询，广告审核总数",notes = "条件查询，广告审核总数" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertName", value = "广告名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tradeId", value = "行业ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uploadTimeBegin", value = "上传日期起", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uploadTimeEnd", value = "上传日期止", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/advertAuditCount")
    public MessageModel advertAuditCount(String advertName,String tradeId,String adCorpName,String blankId ,
                                         String uploadTimeBegin,String uploadTimeEnd)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("advertName",advertName);
        map.put("tradeId",tradeId);
        map.put("adCorpName",adCorpName);
        map.put("blankId",blankId);
        map.put("uploadTimeBegin",uploadTimeBegin);
        //把截止日期加长一天
        if (uploadTimeEnd !=null && !uploadTimeEnd.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(uploadTimeEnd).getTime() + 24 * 3600 * 1000);
                uploadTimeEnd = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//
        }
        map.put("uploadTimeEnd",uploadTimeEnd);
        int i = svr.advertAuditCount(map);
        map.put("total",i);
        model.setData(map);
        model.setResultMsg("success");
        return model;
    }



    // 根据广告编号， 查询广告媒体文件
    @GetMapping(value = "/advertMediaList")
    public MessageModel advertMediaList(String advertId,String loginUserId,
                                        String loginGroupRoleId,String loginRoleId,HttpServletRequest request)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("advertId",advertId);
        List<AdvertMedia> list = svr.advertMediaList(map);
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询广告媒体文件");
        return model;
    }
    // 根据广告编号， 查询广告资料文件
    @GetMapping(value = "/advertFileList")
    public MessageModel advertFileList(String advertId,String loginUserId,
                                       String loginGroupRoleId,String loginRoleId,HttpServletRequest request)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("advertId",advertId);
        List<AdvertFiles> list = svr.advertFilesList(map);
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询广告资料文件");
        return model;
    }


    @ApiOperation( value = "插入广告",notes = "插入广告" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告编号）", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "advertName", value = "广告名称）", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpId", value = "所属企业id）", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uploadTime", value = "上传时间）", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value = "/insertAdvert")
    public MessageModel insertAdvert(@RequestBody Advert advert, HttpServletRequest request)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        advert.setUploadTime(sdf.format(new Date()));
        int i = svr.insertAdvert(advert);

        // 插入广告媒体
          svr.insertAdvertMedia(advert.getMedia());
        //插入广告资料表
         for(int j=0; j<advert.getFiles().size() ;j++)
         {
             svr.insertAdvertFile(advert.getFiles().get(j));
         }
       // 插入广告历史状态表
        AdvertStateHis advertStateHis = new AdvertStateHis();
        advertStateHis.setAdvertId(advert.getAdvertId());
        advertStateHis.setNowState(1);
        advertStateHis.setMaker("admin");
        int i1 = svr.insertAdvertStateHis(advertStateHis);
        model.setResultCode("1");
        model.setData(map);
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        logs.postAdvertLogs(advert,ip,"添加广告");
        return model;
    }


    @ApiOperation( value = "修改广告",notes = "修改广告" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告编号）", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "advertName", value = "广告名称）", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpId", value = "所属企业id）", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uploadTime", value = "上传时间）", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value = "/updateAdvert")
    public MessageModel updateAdvert(@RequestBody Advert advert, HttpServletRequest request)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        advert.setUploadTime(sdf.format(new Date()));
        int i = svr.updateAdvert(advert);
        // next value for my_advert_state_his
        AdvertStateHis advertStateHis = new AdvertStateHis();
        advertStateHis.setAdvertId(advert.getAdvertId());
        advertStateHis.setNowState(advert.getNowState());
        advertStateHis.setMaker("");
        advertStateHis.setMakeTimer(sdf.format(new Date()));
        model.setResultCode("1");
        model.setData(map);
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        logs.postAdvertLogs(advert,ip,"修改广告");
        return model;
    }

    @ApiOperation( value = "删除广告",notes = "删除广告" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告编号）", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/deleteAdvert")
    public MessageModel deleteAdvert(String advertId,String loginUserId,
                                     String loginGroupRoleId,String loginRoleId,HttpServletRequest request)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("advertId",advertId);
        int i = svr.deleteAdvert(map);
        model.setResultCode(i==1?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"删除广告");
        return model;
    }



    // 获取该状态下的广告
    @ApiOperation( value = "获取该状态下的广告",notes = "获取该状态下的广告" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nowState", value = "广告状态）", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/advertListByNowState")
    public MessageModel advertListByNowState(int nowState,String loginUserId,
                                             String loginGroupRoleId,String loginRoleId,HttpServletRequest request )
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("nowState",nowState);
        List<Advert> list = svr.advertListByNowState(map);
        map.clear();
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询特定状态下的广告");
        return model;
    }


    // 设置广告状态-- 通用
    @ApiOperation( value = "设置广告状态",notes = "设置广告状态" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告编号", required = true, dataType = "String", paramType = "upsert"),
            @ApiImplicitParam(name = "nowState", value = "广告状态", required = true, dataType = "String", paramType = "upsert"),
            @ApiImplicitParam(name = "maker", value = "操作人）", required = true, dataType = "String", paramType = "upsert"),
            @ApiImplicitParam(name = "memo", value = "备注）", required = true, dataType = "String", paramType = "upsert"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value="/setUpAdvertState")
    public MessageModel setUpAdvertState( String advertId ,String nowState,String  maker,String memo,String loginUserId,
                                          String loginGroupRoleId,String loginRoleId,HttpServletRequest request)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("advertId",advertId);
        map.put("nowState",nowState);
        map.put("maker",maker);
        AdvertStateHis his = new AdvertStateHis();
        his.setAdvertId(advertId);
        his.setMaker(maker);
        his.setNowState( Integer.parseInt( nowState));
        his.setMemo(memo);
        int i = svr.insertAdvertStateHis(his);
        svr.updateAdvertState(map);
        model.setResultCode("1");
        model.setData(map);
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"设置广告状态");
        return model;
    }


    // 根据广告id获取最新的状态历史（一条数据）-- 通用
    @ApiOperation( value = "根据广告id获取最新的状态历史",notes = "根据广告id获取最新的状态(1,2,3,4)历史" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告编号", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value="/getStateHisByAdvertId")
    public MessageModel getStateHisByAdvertId(String advertId,String loginUserId,
                                              String loginGroupRoleId,String loginRoleId,HttpServletRequest request)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("advertId",advertId);
        List<AdvertStateHis> list= svr.getStateHisByAdvertId(map);
        AdvertStateHis advertHis=new AdvertStateHis();
        advertHis=list.get(0);
        map.clear();
        map.put("advertHis",advertHis);
        model.setResultCode("1");
        model.setData(map);
        model.setResultMsg("success");String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"根据广告编号获取最新的状态历史");

        return model;

    }


}
