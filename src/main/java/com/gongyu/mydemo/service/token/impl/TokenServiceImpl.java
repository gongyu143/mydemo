package com.gongyu.mydemo.service.token.impl;

import com.gongyu.mydemo.bean.result.ResponseCode;
import com.gongyu.mydemo.component.RedisService;
import com.gongyu.mydemo.enums.BaseException;
import com.gongyu.mydemo.service.token.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {


    @Autowired
    private RedisService redisService;


    /**
     * 创建token
     *
     * @return
     */
    @Override
    public String createToken() {
        String str = UUID.randomUUID().toString();
        StringBuilder token = new StringBuilder();
        try {
            token.append(str);
            redisService.setEx("token", token.toString(),10000L);
            boolean notEmpty = StringUtils.isNotEmpty(token.toString());
            if (notEmpty) {
                return token.toString();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 检验token
     *
     * @param request
     * @return
     */
    @Override
    public boolean checkToken(HttpServletRequest request) throws Exception {

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {// header中不存在token
            token = request.getParameter("token");
            if (StringUtils.isBlank(token)) {// parameter中也不存在token
                throw new BaseException(ResponseCode.TOKEN_FAIL);
            }
        }

        if (!redisService.exists(token)) {
            throw new BaseException(ResponseCode.REPETITIVE_OPERATION);
        }

        boolean remove = redisService.remove(token);
        if (!remove) {
            throw new BaseException(ResponseCode.REPETITIVE_OPERATION);
        }
        return true;
    }
}
