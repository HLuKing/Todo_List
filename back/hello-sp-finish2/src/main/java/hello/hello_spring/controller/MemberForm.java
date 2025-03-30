package hello.hello_spring.controller;

// 회원 정보는 받아오는 컨트롤러

public class MemberForm {       // 2 여기서 값을 넣는다.
    private String name;

    private String password;

    private String accountId;

    public String getName() {
        return name;
    }

    public void setName(String name) {      // 1 여기서 값을 가져와서
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
