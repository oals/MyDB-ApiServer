package com.project.MyDBApiServer.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DBInfoDTO {


    private String url;
    private String id;
    private String pswd;

    private String dbName;

    private String tableName;




}
