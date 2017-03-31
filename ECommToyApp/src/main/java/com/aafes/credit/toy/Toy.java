/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aafes.credit.toy;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 *
 * @author Ramya
 */
@Table(keyspace = "ecomm", name = "test")
public class Toy {
    
    @PartitionKey
    private String column1;
    private String column2;

    /**
     * @return the column1
     */
    public String getColumn1() {
        return column1;
    }
    
    public String getColumn2() 
    {
        return column2;
    }

    /**
     * @param response the column1 to set
     */
    public void setColumn1(String response) {
        this.column1 = response;
    }

    public void setColumn2(String strHostName) 
    {
        this.column2 = strHostName;
    }
}
