package com.czjy.chaozhi.model.bean;

import java.util.List;

/**
 * Created by huyg on 2018/10/21.
 */
public class PlanSubject {


    /**
     * id : 410
     * name : 测试课程
     * teacher : 测试课程
     * teacher_description : 测试课程
     * teacher_img : 0
     * total_unit : 1
     * update_at : 2018-10-19 10:35:32
     * teach_plan : [{"time":"2018-10-19 10:28:15","view_url":"http://open.talk-fun.com/player.php?accessAuth=eyJvcGVuSUQiOiIxMTkyOSIsInRpbWVzdGFtcCI6MTU0MDA1MjY3NSwiY291cnNlX2lkIjoiMTQwNzk3IiwidWlkIjoxMDIzOCwibmlja25hbWUiOiIxODIqKioqNjUxMSIsInJvbGUiOiJ1c2VyIiwiZXhwaXJlIjoyODgwMCwib3B0aW9ucyI6IntcInRpbWVzXCI6MTB9Iiwic2lnbiI6ImQwMGMzN2UwZDUwNTNiMTY4ZTU5YzE5NDg5MzA2MmU0In0","tid":1,"name":"第1节课"}]
     * teacher_img_url : //test-aci-api.chaozhiedu.com/api/file/49970z3e0-210024
     * question_num : 5
     * answer_question_num : 2
     */

    private int id;
    private String name;
    private String teacher;
    private String teacher_description;
    private int teacher_img;
    private int total_unit;
    private String update_at;
    private String teacher_img_url;
    private int question_num;
    private int answer_question_num;
    private List<TeachPlan> teach_plan;

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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeacher_description() {
        return teacher_description;
    }

    public void setTeacher_description(String teacher_description) {
        this.teacher_description = teacher_description;
    }

    public int getTeacher_img() {
        return teacher_img;
    }

    public void setTeacher_img(int teacher_img) {
        this.teacher_img = teacher_img;
    }

    public int getTotal_unit() {
        return total_unit;
    }

    public void setTotal_unit(int total_unit) {
        this.total_unit = total_unit;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getTeacher_img_url() {
        return teacher_img_url;
    }

    public void setTeacher_img_url(String teacher_img_url) {
        this.teacher_img_url = teacher_img_url;
    }

    public int getQuestion_num() {
        return question_num;
    }

    public void setQuestion_num(int question_num) {
        this.question_num = question_num;
    }

    public int getAnswer_question_num() {
        return answer_question_num;
    }

    public void setAnswer_question_num(int answer_question_num) {
        this.answer_question_num = answer_question_num;
    }

    public List<TeachPlan> getTeachPlan() {
        return teach_plan;
    }

    public void setTeachPlan(List<TeachPlan> teach_plan) {
        this.teach_plan = teach_plan;
    }
}
