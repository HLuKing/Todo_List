package hello.hello_spring.controller;



// 회원 정보는 받아오는 컨트롤러
public class ListForm {       // 2 여기서 값을 넣는다.

    private String title;

    private String content;

    private String newTitle;

    private String newContent;

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

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }
}
