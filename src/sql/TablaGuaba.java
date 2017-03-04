/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

/**
 *
 * @author Liquid
 */

import java.util.Map;
import java.util.Set;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class TablaGuaba {

public void create(){
    //Table<R,C,V> == Map<R,Map<C,V>>
      /*
      *  Company: IBM, Microsoft, TCS
      *  IBM 		-> {101:Mahesh, 102:Ramesh, 103:Suresh}
      *  Microsoft 	-> {101:Sohan, 102:Mohan, 103:Rohan } 
      *  TCS 		-> {101:Ram, 102: Shyam, 103: Sunil } 
      * 
      * */
      
      //create a table
      Table<String, String, String> employeeTable = HashBasedTable.create();

      //initialize the table with employee details
      employeeTable.put("IBM", "101","Rene");
      employeeTable.put("IBM", "102","Joel");
      employeeTable.put("IBM", "103","Luis");

      employeeTable.put("Microsoft", "111","Joiner");
      employeeTable.put("Microsoft", "112","Juan");
      employeeTable.put("Microsoft", "113","Jose");

      employeeTable.put("TCS", "121","Rafael");
      employeeTable.put("TCS", "122","Genil");
      employeeTable.put("TCS", "123","Isabel");

      Table<String, String, String> employeeTableClone = HashBasedTable.create();

      //initialize the table with employee details
      employeeTableClone.put("IBM", "101","Rene");
      employeeTableClone.put("IBM", "102","Joel");
      employeeTableClone.put("IBM", "103","Luis");

      employeeTableClone.put("Microsoft", "111","Joiner");
      employeeTableClone.put("Microsoft", "112","Juan");
      employeeTableClone.put("Microsoft", "113","Jose");

      employeeTableClone.put("TCS", "121","Rafael");
      employeeTableClone.put("TCS", "122","Genil");
      employeeTableClone.put("TCS", "123","Isabel");

      
   }	

public void getEmployeesByCompany(Table employeeTable, String company){//SELECT * FROM employees where company=XXXX;
    //get Map corresponding to IBM
      Map<String,String> ibmEmployees =  employeeTable.row(company);

      System.out.println("List of records related to: "+company);
      
      for(Map.Entry<String, String> entry : ibmEmployees.entrySet()){
         System.out.println("Emp Id: " + entry.getKey() + ", Name: " + entry.getValue());
      }
}

public void getUKs(Table employeeTable){// Select distinct company FROM employees;
          //get all the unique keys of the table
      Set<String> employers = employeeTable.rowKeySet();
      System.out.print("Employers: ");
      
      for(String employer: employers){
         System.out.print(employer + " ");
      }
}


public void getEmployeeByID(Table employeeTable, String id){ // SELECT * FROM employees where id=XXXX;
//get a Map corresponding to 102
      Map<String,String> EmployerMap =  employeeTable.column(id);
      
      for(Map.Entry<String, String> entry : EmployerMap.entrySet()){
         System.out.println("Employer: " + entry.getKey() + ", Name: " + entry.getValue());
      }		
}




}    

