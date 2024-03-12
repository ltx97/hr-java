package com.forme.alan.controller;

import com.forme.alan.model.*;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MyController {
    private static String allWords = "abcdefghijklmnopqrstuvwxzy";
    private static List<String> imgList = new ArrayList();
    private static List<ArticeleModel> articeleList = new ArrayList<>();

    private static List<ChannelModel> myChannels = new ArrayList<>();
    private static List<ChannelModel> recommendChannels = new ArrayList<>();

    private static List<CommentModel> commentModels = new ArrayList<>();

    static {
        imgList.add("https://img.pconline.com.cn/images/upload/upc/tx/photoblog/1812/26/c7/125539584_1545834974811_mthumb.jpg");
        imgList.add("https://img1.baidu.com/it/u=420421608,846052722&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=473");
        imgList.add("https://img1.baidu.com/it/u=405603230,2358037581&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=334");

        for (int i = 0; i < 42; i++) {
            articeleList.add(initArticele());
        }

        for (int i = 0; i < 4; i++) {
            myChannels.add(initChannelModel());
        }

        for (int i = 0; i < 45; i++) {
            recommendChannels.add(initRecChannelModel());
        }

        for (int i = 0; i < 13; i++) {
            commentModels.add(initComment());
        }

    }

    @PostMapping("/login")
    public CommonRepsonse login(@Validated @RequestBody LoginUser user){
        if (user.getMobile().equals("15706813737")&&user.getCode().equals("000000")){
            Map<String, String> mytoken = new HashMap<String, String>();
            mytoken.put("token", "我是token啊");
            mytoken.put("refresh-token", "我是刷新token啊");
            return CommonRepsonse
                    .builder()
                    .code("200")
                    .data(mytoken)
                    .build();
        }else {
            return CommonRepsonse
                    .builder()
                    .code("400")
                    .data("手机号或者验证码有误")
                    .build();
        }
    }

    @GetMapping("/code/{mobile}")
    public CommonRepsonse getCode(@PathVariable("mobile")String mobile) {
        return CommonRepsonse
                .builder()
                .code("200")
                .data(mobile)
                .build();
    }

    @GetMapping("/login")
    public CommonRepsonse loginByGet(@Validated @RequestBody LoginUser user){
        if (user.getMobile().equals("15706813737")&&user.getCode().equals("000000")){
            return CommonRepsonse
                    .builder()
                    .code("200")
                    .data("ok")
                    .build();
        }else {
            return CommonRepsonse
                    .builder()
                    .code("400")
                    .data("手机号或者验证码有误")
                    .build();
        }
    }

    @PostMapping("/userInfo/{mobile}")
    public CommonRepsonse getUserInfo(@PathVariable("mobile")String mobile, HttpServletRequest request){
        String header = request.getHeader("access-token");
        if (StringUtils.isBlank(header)) {
            return CommonRepsonse
                    .builder()
                    .data("token丢失")
                    .code("401")
                    .build();
        }
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("name", "蓝lll");
        userMap.put("toutiao", RandomUtils.nextInt(1, 100));
        userMap.put("guanzhu", RandomUtils.nextInt(1, 100));
        userMap.put("fensi", RandomUtils.nextInt(1, 100));
        userMap.put("huozan", RandomUtils.nextInt(1, 100));
        return CommonRepsonse
                .builder()
                .data(userMap)
                .code("200")
                .build();
    }

    /**
     * 文章列表
     * @param channelId
     * @return
     */
    @PostMapping("/article/list/{channelId}")
    public CommonRepsonse getArticleList(@PathVariable("channelId")String channelId){
        return CommonRepsonse
                .builder()
                .data(articeleList.subList(0, 10))
                .code("200")
                .build();
    }

    public static ArticeleModel initArticele() {
        ArticeleModel articeleModel = new ArticeleModel();
        articeleModel.setTitle("标题"+randomTitle());
        articeleModel.setAuthor("作者" + randomAuthor());
        articeleModel.setComments(RandomUtils.nextInt(99,999));
        articeleModel.setPublishTime(new Date());
        randomImgs(articeleModel);
        return articeleModel;
    }

    public static String randomTitle() {
        int i = RandomUtils.nextInt(0, 26);
        int i2 = RandomUtils.nextInt(0, 26);
        int i3 = RandomUtils.nextInt(0, 26);
        return String.valueOf(allWords.charAt(i) + allWords.charAt(i2) + allWords.charAt(i3));
    }

    public static String randomAuthor() {
        int i = RandomUtils.nextInt(0, 26);
        int i2 = RandomUtils.nextInt(0, 26);
        int i3 = RandomUtils.nextInt(0, 26);
        return String.valueOf(allWords.charAt(i) + allWords.charAt(i2) + allWords.charAt(i3));
    }

    public static void randomImgs(ArticeleModel articeleModel) {
        if (RandomUtils.nextBoolean()) {
            articeleModel.setType(3);
            articeleModel.setImgs(imgList);
        }else {
            int imgIdx = RandomUtils.nextInt(0, 3);
            articeleModel.setType(0);
            articeleModel.setImgs(Collections.singletonList(imgList.get(imgIdx)));
        }
    }

    @PostMapping("/user/channel")
    public  CommonRepsonse getMyChannel(HttpServletRequest request){
        //TODO  校验token
        return CommonRepsonse
                .builder()
                .code("200")
                .data(myChannels)
                .build();
    }

    @PostMapping("/recommend/channel")
    public  CommonRepsonse getRecommendChannel(){
        return CommonRepsonse
                .builder()
                .code("200")
                .data(recommendChannels)
                .build();
    }

    @PostMapping("/addchannel/{channelId}")
    public CommonRepsonse addChannel(@PathVariable("channelId")String channelId){
        for (ChannelModel recommendChannel : recommendChannels) {
            if (recommendChannel.getChannelId().equals(channelId)) {
                myChannels.add(recommendChannel);
                break;
            }
        }
        return CommonRepsonse
                .builder()
                .code("200")
                .build();
    }

    @PostMapping("/deletechannel/{channelId}")
    public CommonRepsonse deleteChannel(@PathVariable("channelId")String channelId){
        int matchIdx = -1;
        for (int i = 0; i < myChannels.size(); i++) {
            ChannelModel channelModel = myChannels.get(i);
            if (channelModel.getChannelId().equals(channelId)) {
                matchIdx = i;
                break;
            }
        }
        myChannels.remove(matchIdx);
        return CommonRepsonse
                .builder()
                .code("200")
                .build();
    }

    public static ChannelModel initChannelModel() {
        String channeId = String.valueOf(RandomUtils.nextInt(100, 200));
        ChannelModel channelModel = new ChannelModel();
        channelModel.setChannelId(channeId);
        channelModel.setChannelName("我的 " + channeId);
        return channelModel;
    }

    public static ChannelModel initRecChannelModel() {
        String channeId = String.valueOf(RandomUtils.nextInt(100, 200));
        ChannelModel channelModel = new ChannelModel();
        channelModel.setChannelId(channeId);
        channelModel.setChannelName("推荐 " + channeId);
        return channelModel;
    }

    @PostMapping("/associate/list")
    public CommonRepsonse  asList(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        List<String> asStrs = new ArrayList<>();
        asStrs.add("wc");
        asStrs.add("wcnm");
        asStrs.add("wcn");
        asStrs.add("wccc");
        asStrs.add("al");
        asStrs.add("all");

        String asWord = reqMap.get("asWord");
        List<String> matchStrs = asStrs
                .stream()
                .filter(item -> item.startsWith(asWord))
                .collect(Collectors.toList());
        return CommonRepsonse
                .builder()
                .data(matchStrs)
                .code("200")
                .build();
    }

    @PostMapping("/search/list")
    public CommonRepsonse  searchList(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        List<String> asStrs = new ArrayList<>();
        asStrs.add("文章标题1");
        asStrs.add("文章标题2");
        asStrs.add("文章标题3");
        asStrs.add("文章标题4");
        asStrs.add("文章标题5");
        String asWord = reqMap.get("asWord");
        return CommonRepsonse
                .builder()
                .data(asStrs)
                .code("200")
                .build();
    }

    @PostMapping("/comment/list/{articleId}")
    public CommonRepsonse commentList(HttpServletRequest request,@PathVariable("articleId")String articleId){
        return CommonRepsonse
                .builder()
                .code("200")
                .data(commentModels)
                .build();
    }

    public static CommentModel initComment() {
        CommentModel commentModel = new CommentModel();
        commentModel.setAuthoName("评论作者名");
        commentModel.setCommentText("我是评论正文呀");
        return commentModel;
    }

}
