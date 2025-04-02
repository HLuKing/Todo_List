package hello.hello_spring.controller;



// 회원 정보는 받아오는 컨트롤러
public class ListForm {       // 2 여기서 값을 넣는다.

    private String title;

    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
