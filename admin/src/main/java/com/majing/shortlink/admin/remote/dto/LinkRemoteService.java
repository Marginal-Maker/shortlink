package com.majing.shortlink.admin.remote.dto;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.majing.shortlink.admin.common.convention.result.Result;
import com.majing.shortlink.admin.remote.dto.req.LinkCreateReqDto;
import com.majing.shortlink.admin.remote.dto.req.LinkUpdateReqDto;
import com.majing.shortlink.admin.remote.dto.req.LinkedPageReqDto;
import com.majing.shortlink.admin.remote.dto.req.SaveRecycleBinReqDto;
import com.majing.shortlink.admin.remote.dto.resp.LinkCountRespDto;
import com.majing.shortlink.admin.remote.dto.resp.LinkCreateRespDto;
import com.majing.shortlink.admin.remote.dto.resp.LinkedPageRespDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author majing
 * @date 2024-04-22 14:45
 * @Description 短链接后管业务层
 */
public interface LinkRemoteService {
    default Result<IPage<LinkedPageRespDto>> pageLink(LinkedPageReqDto linkedPageReqDto){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gid", linkedPageReqDto.getGid());
        requestMap.put("current", linkedPageReqDto.getCurrent());
        requestMap.put("size", linkedPageReqDto.getSize());
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/page", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<Result<IPage<LinkedPageRespDto>>>() {
        });
    }
    default Result<LinkCreateRespDto> createShortLink(LinkCreateReqDto linkCreateReqDto){
        String resultBodyStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/create", JSON.toJSONString(linkCreateReqDto));
        return JSON.parseObject(resultBodyStr, new TypeReference<Result<LinkCreateRespDto>>() {
        });
    }
    default Result<List<LinkCountRespDto>> listGroupLinkCount(List<String> gidList){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gidList", gidList);
        String requestListStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/count", requestMap);
        return JSON.parseObject(requestListStr, new TypeReference<Result<List<LinkCountRespDto>>>() {
        });
    }

    default void updateLink(LinkUpdateReqDto linkUpdateReqDto) {
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/update", JSON.toJSONString(linkUpdateReqDto));
    }

    default Result<String> getUrlTitle(String url){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("url", url);
        String requestListStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/title", requestMap);
        return JSON.parseObject(requestListStr, new TypeReference<Result<String>>() {
        });
    }
    default void saveRecycleBin(SaveRecycleBinReqDto saveRecycleBinReqDto){
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/save", JSON.toJSONString(saveRecycleBinReqDto));
    }
}
