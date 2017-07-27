package com.lucifer.controller;



import com.lucifer.service.QiniuCloudService;
import com.lucifer.utils.Result;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lijc on 15/8/27.
 */
@Api(basePath = "/cloud", value = "cloud", description = "云存储接口", produces = "application/json",position = 1)
@RestController
@RequestMapping("/cloud")
public class CloudController {

    @Autowired
    QiniuCloudService qiniuCloudService;

//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @ApiImplicitParams(@ApiImplicitParam(dataType = "file", name = "file", paramType = "body"))
    @ApiOperation(httpMethod = "POST", value = "文件上传",response = String.class)
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file){

        try {
            String uploadUrl;
            uploadUrl = qiniuCloudService.simpleUploadWithoutKey(file);
            return  Result.ok(uploadUrl);
//            cloud.test();
        } catch (Exception e) {
            e.printStackTrace();
            return  Result.fail(e.getMessage());
        }
        //System.out.println(JSON.toJSONString(map));
       // return Result.fail();
    }

}
