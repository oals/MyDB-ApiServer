package com.project.MyDBApiServer.restcontroller;

import com.project.MyDBApiServer.dto.DBInfoDTO;
import com.project.MyDBApiServer.service.MyDBApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MyDBApiRestController {

    private final MyDBApiService myDBApiService;


    @GetMapping("/test")
    public Map<String, String> test(){

        Map<String, String> map = new HashMap<>();
        map.put("connectState", "테스트");

        return map;
    }


    @GetMapping("/connectDB")
    public  ResponseEntity< Map<String, Boolean>> connectDB(DBInfoDTO dbInfoDTO){

        log.info(dbInfoDTO);

        boolean DBchk = myDBApiService.connectDB(dbInfoDTO);

        Map<String, Boolean> map = new HashMap<>();
        map.put("connectState", DBchk);



        return new ResponseEntity<>(map, HttpStatus.OK);

    }


    @GetMapping("/getDataBase")
    public  Map<String, List<String>> getDataBase(DBInfoDTO dbInfoDTO){

        log.info(dbInfoDTO);



        List<String> list =  myDBApiService.getDataBase(dbInfoDTO);

        Map<String, List<String>> map = new HashMap<>();
        map.put("DBList", list);

        return map;
    }


    @GetMapping("/getTable")
    public  Map<String, List<String>> getTable(DBInfoDTO dbInfoDTO){

        log.info(dbInfoDTO);

        List<String> list =  myDBApiService.getTable(dbInfoDTO);


        Map<String, List<String>> map = new HashMap<>();
        map.put("tableList", list);

        return map;
    }


    @GetMapping("getTableInfo")
    public Map<String, List<List<String>>>  getTableInfo(DBInfoDTO dbInfoDTO){

        log.info(dbInfoDTO);

        List<List<String>> list =  myDBApiService.getTableInfo(dbInfoDTO);


        Map<String, List<List<String>>> map = new HashMap<>();
        map.put("tableList", list);

        return map;
    }


}
