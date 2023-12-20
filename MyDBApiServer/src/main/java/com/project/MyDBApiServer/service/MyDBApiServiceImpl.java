package com.project.MyDBApiServer.service;


import ch.qos.logback.classic.spi.IThrowableProxy;
import com.project.MyDBApiServer.dto.DBInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Log4j2
public class MyDBApiServiceImpl implements MyDBApiService{



    @Override
    public Boolean connectDB(DBInfoDTO dbInfoDTO) {

        Connection conn = null;
        boolean DBchk = false;

        try {
            conn = DriverManager.getConnection(dbInfoDTO.getUrl(), dbInfoDTO.getId(), dbInfoDTO.getPswd());
            DBchk = true;

        } catch (SQLException e) {
            log.info(e.getMessage());
            DBchk = false;
        }

        return DBchk;
    }

    @Override
    public List<String> getDataBase(DBInfoDTO dbInfoDTO) {

        Connection conn = null;
        List<String> DBNameList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(dbInfoDTO.getUrl(), dbInfoDTO.getId(), dbInfoDTO.getPswd());

        } catch (SQLException e) {
            log.info(e.getMessage());
        }


        String query = "SHOW DATABASES";



        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String databaseName = rs.getString("database");
                DBNameList.add(databaseName);


            }
            System.out.println("Query executed successfully!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot execute the query!", e);
        }



        return DBNameList;
    }

    @Override
    public List<String> getTable(DBInfoDTO dbInfoDTO) {

        Connection conn = null;
        List<String> TableNameList = new ArrayList<>();

        String query = "";

        try {

            if(dbInfoDTO.getUrl().contains("jdbc:mariadb://")){
                query = "SELECT table_name FROM information_schema.tables WHERE table_type = 'BASE TABLE' AND table_schema ='" +  dbInfoDTO.getDbName() + "'";
                conn = DriverManager.getConnection(dbInfoDTO.getUrl() + dbInfoDTO.getDbName(), dbInfoDTO.getId(), dbInfoDTO.getPswd());

            }else if(dbInfoDTO.getUrl().contains("jdbc:oracle:thin:@")){
                query = "SELECT table_name FROM user_tables;";
                conn = DriverManager.getConnection(dbInfoDTO.getUrl(), dbInfoDTO.getId(), dbInfoDTO.getPswd());


            }else{
                log.info("오류");
            }


        } catch (SQLException e) {
            log.info(e.getMessage());
        }



        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                TableNameList.add(tableName);


            }
            System.out.println("Query executed successfully!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot execute the query!", e);
        }


        return TableNameList;

    }

    @Override
    public List<List<String>> getTableInfo(DBInfoDTO dbInfoDTO) {


        Connection conn = null;
        List<String> columnNameList = new ArrayList<>();
        List<List<String>> dataList = new ArrayList<>();


        String query = "";
        String query2 = "";


        try {
            if(dbInfoDTO.getUrl().contains("jdbc:mariadb://")){
                conn = DriverManager.getConnection(dbInfoDTO.getUrl() + dbInfoDTO.getDbName() , dbInfoDTO.getId(), dbInfoDTO.getPswd());
                query = "SELECT column_name FROM information_schema.columns WHERE table_name ='"  + dbInfoDTO.getTableName() + "'";
                query2 = "SELECT * from " + dbInfoDTO.getTableName();


            }else if(dbInfoDTO.getUrl().contains("jdbc:oracle:thin:@")){
                conn = DriverManager.getConnection(dbInfoDTO.getUrl(), dbInfoDTO.getId(), dbInfoDTO.getPswd());
                query = "SELECT column_name FROM all_tab_columns WHERE table_name ='"  + dbInfoDTO.getTableName().toUpperCase() + "'";
                query2 = "SELECT * FROM " + dbInfoDTO.getTableName();
            }else{
                log.info("오류");
            }
        } catch (SQLException e) {

        }




        try {
            Statement stmt = conn.createStatement();
            ResultSet columnNameResult = stmt.executeQuery(query);

            while (columnNameResult.next()) {
                String columnName = columnNameResult.getString("column_name");
                columnNameList.add(columnName);
            }

            dataList.add(columnNameList);
            ResultSet tableDataResult = stmt.executeQuery(query2);

            while (tableDataResult.next()) {
                List<String> dataList2 = new ArrayList<>();

                for(int i = 0; i < columnNameList.size(); i++){
                    dataList2.add(tableDataResult.getString(columnNameList.get(i)));
                }

                dataList.add(dataList2);
            }

            System.out.println("Query executed successfully!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot execute the query!", e);
        }


        return dataList;




    }


}
