package com.czjy.chaozhi.model.bean;

import com.czjy.chaozhi.global.Const;

/**
 * Created by huyg on 2018/10/8.
 */
public class ProductBean {

    /**
     * id : 31
     * name : ACI注册心理咨询师  VIP火箭班
     * category_id : 2
     * price : 5880.00
     * original_price : 6880.00
     * sub_name : 独家通关秘籍
     * syllabus : <p><b>第一章普通心理学 （6课时）&nbsp;</b></p><p>第一节心理科学概述</p><p>第二节 心理行为的生物等基础</p><p>第三节 认识过程</p><p>第四节 情绪过程和意志过程</p><p>第五节 需要和动机</p><p>第六节 能力、气质和性格</p><p>第七节 意识研究</p><p><b>第二章 社会心理学（6课时）</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p><p>第一节 社会心理学概述</p><p>第二节 社会化与自我概念</p><p>第三节 杜会知觉与归因</p><p>第四节 社会动机与社交情绪</p><p>第五节 态度形成与态度转变</p><p>第六节 沟通与人际关系</p><p>第七节 社会影响</p><p>第八节 爱情，婚姻与家庭</p><p><b>第三章 发展心理学（6课时）</b></p><p>第一节 发展心理学概述</p><p>第二节 婴儿期的心理发展</p><p>第三节 幼儿期的心理发展</p><p>第四节 童年期的心理发展</p><p>第五节 青春发育期的心理发展</p><p>第六节 青年期的心理发展</p><p>第七节 中年期的心理发展变化</p><p>第八节 老年期的心理发展变化</p><p><b>第四章 异常心理学（6课时）</b></p><p>第一节 心理异常</p><p>第二节 引起心理异常的主要因素</p><p>第三节 一般心理问题</p><p>第四节 心理障碍</p><p><b>第五章 健康心理学（6课时）</b></p><p>第一节 心理健康及其评估标准</p><p>第二节 常见的躯体疾病患者的心理问题</p><p>第三节 压力与健康</p><p>第六章 心理测量学（6课时）</p><p>第一节 心理测量学概述</p><p>第二节 测验的常模</p><p>第三节 测验的信度</p><p>第四节 测验的效度</p><p>第五节 项目分析</p><p>第六节 测验编制的一般程序</p><p>第七节 心理测验的使用</p><p><b>第七章 咨询心理学（6课时）</b></p><p>第一节 咨询心理学概述</p><p>第二节 心理咨询的基本过程</p><p>第三节 心理咨询的重要学派</p><p><b>第八章 心理诊断技能（6课时）</b></p><p>第一节 初诊接待</p><p>第二节 初步诊断</p><p>第三节 识别病因</p><p><b>第九章 心理咨询技能（6课时）</b></p><p>第一节 心理咨询的基本技术</p><p>第二节 个体心理咨询技能</p><p>第三节 团体心理咨询技能</p><p><b>第十章 心理测验技能（12课时）</b></p><p>第一节 智力测验</p><p>第二节 人格测验</p><p>第三节 心理与行为问题评估</p><p>第四节 引进国外心理测验</p><p><br></p>
     * tags : 名师密训,结业证书,心理实操课2.0,真题解析2.0,考前预测,终身学习,不过退费,线下沙龙,VIP服务,私人心理医生
     * review_num : 5
     * img : //test-aci-api.chaozhiedu.com/api/file/31390
     * description : <p style="text-align: center; "><img src="//aci-api.chaozhiedu.com/api/file/26841" alt="心理咨询师-VIP火箭班" style="max-width:100%;"><br></p><p><br></p>
     * status : 1
     * purchase : false
     */

    private int id;
    private String name;
    private int category_id;
    private String price;
    private String original_price;
    private String sub_name;
    private String syllabus;
    private String tags;
    private int review_num;
    private String img;
    private String description;
    private int status;
    private boolean purchase;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getReview_num() {
        return review_num;
    }

    public void setReview_num(int review_num) {
        this.review_num = review_num;
    }

    public String getImg() {
        if (img.contains("http")) {
            return img;
        } else {
            return Const.HTTP + img;
        }
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isPurchase() {
        return purchase;
    }

    public void setPurchase(boolean purchase) {
        this.purchase = purchase;
    }
}
