public class UserInfo {

    private static UserInfo instance;

    protected String myNickname;

    protected String myPort;

    private UserInfo(){}

    protected static UserInfo getInstance() {
        if(instance==null) {
            instance = new UserInfo();
        }
        return instance;
    }
}
