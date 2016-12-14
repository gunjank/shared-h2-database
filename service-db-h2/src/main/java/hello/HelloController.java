package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;

@RestController
public class HelloController {

    private Server server;
    private boolean serverRunning = false;

    private JdbcDataSource dataSource;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot H2 Server Mode, call /serverStart to start server and /stopServer to stopServer!";
    }
    

@RequestMapping("/startServer")
    public String startServer(){       
       System.out.println(" ------------------------ ********  Greetings from Spring Boot startServer method");    
        String msg = "";
        if(serverRunning){
            return "Server already running and the url is ::: "+ server.getURL();
        }
        try{

        dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:./data/db");
        dataSource.setUser("sa");
        dataSource.setPassword("");




            server = Server.createTcpServer("-tcpAllowOthers").start();
            serverRunning = true;
            msg = "Server started successfully"+"  -  "+server.getURL();
        }catch(SQLException e)
        {
            e.printStackTrace();
            msg = "server starting failed ";
        }        
        return msg;
    }

    @RequestMapping("/stopServer")
    public String stopServer() {
        if(!serverRunning || null == server){
            return "Server is not running yet or never started !!"+serverRunning;
        }
        server.stop();
        serverRunning = false;
        return "server stopped";        
    }   

}