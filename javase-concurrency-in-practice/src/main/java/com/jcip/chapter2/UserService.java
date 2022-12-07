package com.jcip.chapter2;

public class UserService {

    User getUserFromDB(String name){
        return new User(name,"132567809000");
    }

    int updateUserByName(User user){
        if(!user.getName().equals("tom")){
            throw new RuntimeException("该数据不存在");
        }
        user.setTel("110");
        return 1;
    }

    public static void main(String[] args) {
        final UserService userService = new UserService();
        final User user = userService.getUserFromDB("tom");
        System.out.println("从数据库检索数据=>"+user);
        new Thread(()->{
            user.setName("kobe");
        }).start();

       new Thread(()->{
           userService.updateUserByName(user);
       }).start();
    }
}
