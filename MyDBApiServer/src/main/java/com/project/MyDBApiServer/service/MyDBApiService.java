package com.project.MyDBApiServer.service;

import com.project.MyDBApiServer.dto.DBInfoDTO;

import java.util.List;

public interface MyDBApiService {


    Boolean connectDB(DBInfoDTO dbInfoDTO);


    List<String> getDataBase(DBInfoDTO dbInfoDTO);


    List<String> getTable(DBInfoDTO dbInfoDTO);


    List<List<String>> getTableInfo(DBInfoDTO dbInfoDTO);




}
