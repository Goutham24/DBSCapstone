package com.dbs.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbs.web.beans.ClientStocks;
import com.dbs.web.beans.ClientStocksUtility;
import com.dbs.web.services.ClientStocksService;

@RestController
@RequestMapping("/clientstocks")
public class ClientStocksRestController {
	
	@Autowired
	private ClientStocksService service;
	
	@PostMapping
    public ResponseEntity<Map<String, String>> insertClientStock(@RequestBody ClientStocks clientstocks)
    {
        boolean b=this.service.insertClientStock(clientstocks);
        Map<String,String> map=new HashMap<>();
        if(b==true)
        {
            
            //System.out.println("if block");
            map.put("message", "success");
            return  ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(map);
        }
        //System.out.println("outside");
        map.put("message","Unsuccessful");
        return  ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(map);
    }
	@PutMapping
    public ResponseEntity<Map<String, String>> updateClientStock(@RequestBody ClientStocks clientstocks)
    {
        boolean b=this.service.updateClientStock(clientstocks);
        Map<String,String> map=new HashMap<>();
        if(b==true)
        {
            
            //System.out.println("if block");
            map.put("message", "success");
            return  ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(map);
        }
        //System.out.println("outside");
        map.put("message","Unsuccessful");
        return  ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(map);
    }
	@DeleteMapping
    public ResponseEntity<Map<String, String>> deleteClientStock(@RequestBody ClientStocksUtility clientstockid)
    {
        boolean b=this.service.deleteClientStock(clientstockid);
        Map<String,String> map=new HashMap<>();
        if(b==true)
        {
            
            //System.out.println("if block");
            map.put("message", "success");
            return  ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(map);
        }
        //System.out.println("outside");
        map.put("message","Unsuccessful");
        return  ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(map);
    }

}
