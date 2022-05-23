package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class RestartSelfListener {


    private static Properties properties;

    public RestartSelfListener() {


        final String restartCmd = "service xxxx start";

        new Thread(new Runnable() {

            @Override
            public void run() {
                int i = 0;
                while (i < 60) {
                    i++;
                    //检测service服务是否存在，当结果为0时，不存在
                    String checkCmd = "jps -l |grep ProcessControlServer|wc -l";
                    String result = RestartSelfListener.execCommand(checkCmd);
                    //结果为0，service服务停掉，启动程序
                    if ("0".equals(result)) {
                        RestartSelfListener.execCommand(restartCmd);
                        break;
                    }
                    try {
                        // 5 秒钟检测一下服务的状态
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public static String execCommand(String cmd) {
        StringBuffer sb = new StringBuffer();
        BufferedReader in = null;
        try {
            Runtime rt = Runtime.getRuntime();
            String[] cmdA = { "/bin/sh", "-c", cmd };
            Process p = rt.exec(cmdA);
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
            if (null != in) {
                in.close();
            }
        } catch (IOException e) {
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        new RestartSelfListener();
    }}
