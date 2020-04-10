package top.theanything;


import top.theanything.core.server.NQ_Server;

/**
 * @param
 * @author zhou
 * @Description
 * @createTime 2020-03-20
 */
public class ServerStart {

    public static void main(String[] args) {

        new NQ_Server().run();
    }
}
