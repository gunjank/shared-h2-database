package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class HelloController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    JdbcTemplate customer;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot H2 client mode !";
    };

    @RequestMapping("selectQuery")
    public List<String> selectQuery(){
        // String msg = "query result ...";
        //     jdbcTemplate.query(
        //         "SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[] { "Josh" },
        //         (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
        // ).forEach(customer -> log.info(customer.toString()));



        String sql = "SELECT * FROM customers";

	List<String> customers = new ArrayList<String>();

	List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
	for (Map row : rows) {
Integer i = (Integer)row.get("id"); //example

Long l = Long.valueOf(i.longValue());


		Customer customer = new Customer(l,(String)row.get("first_name"),(String)row.get("last_name"));
	
		customers.add(customer.toString());
	}

	return customers;

    }
    

// @RequestMapping("/startServer")
//     public String startServer(){       
//        System.out.println(" ------------------------ ********  Greetings from Spring Boot startServer method");    
//         String msg = "";
//         if(serverRunning){
//             return "Server already running and the url is ::: "+ server.getURL();
//         }
//         try{
//             server = Server.createTcpServer("-tcpAllowOthers").start();
//             serverRunning = true;
//             msg = "Server started successfully"+"  -  "+server.getURL();
//         }catch(SQLException e)
//         {
//             e.printStackTrace();
//             msg = "server starting failed ";
//         }        
//         return msg;
//     }

//     @RequestMapping("/stopServer")
//     public String stopServer() {
//         if(!serverRunning || null == server){
//             return "Server is not running yet or never started !!";
//         }
//         server.stop();
//         serverRunning = false;
//         return "server stopped";        
//     }   

}