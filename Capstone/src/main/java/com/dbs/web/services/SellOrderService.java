package com.dbs.web.services;

 

import java.time.LocalDate;
import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 

import com.dbs.web.beans.BuyOrder;
import com.dbs.web.beans.Client;
import com.dbs.web.beans.ClientStocks;
import com.dbs.web.beans.ClientStocksUtility;
import com.dbs.web.beans.SellOrder;
import com.dbs.web.beans.TradeHistory;
import com.dbs.web.repository.BuyOrderRepository;
import com.dbs.web.repository.ClientStocksRepository;
import com.dbs.web.repository.SellOrderRepository;

 

@Service
public class SellOrderService {
    
	@Autowired
	private ClientStocksService clientstockService;
    @Autowired
    private SellOrderRepository sellRepo;
    
    @Autowired
    private BuyOrderService buyservice;
    
    @Autowired
    private BuyOrderRepository buyRepo;

    @Autowired
    private TradeHistoryService tradeService;
    
    @Autowired
    private ClientStocksRepository  cskrepo;

    public List<SellOrder> getSellOrderByOrderDate() {
        try { 
            List<SellOrder> sellorder =this.sellRepo.findByOrderDate().get();
    return sellorder; 
    }
        catch(IllegalArgumentException e ) { return null; } 
    }
    
    public boolean insertSellOrder(SellOrder so)    {
        if(this.sellRepo.findById(so.getSellid()).isPresent())
            return false;
        try {
        	so.setOrderdate(LocalDate.now());
        	so.setRemainingquantity(so.getQuantity());
        	Client bc=so.getClientid();
        	ClientStocksUtility cskid= new ClientStocksUtility(so.getClientid(),so.getInstrumentid());
        	ClientStocks csSeller=this.clientstockService.getClientStockById(cskid);
        	double amount=so.getPrice()*so.getQuantity();
        	if(bc.getRemainingtransactionlimit()-amount<=0 && csSeller.getQuantity()<so.getQuantity())
        		return false;
        	if(cskrepo.existsById(cskid))
        		return false;
            this.sellRepo.save(so);
            sellmatch(so,amount,cskid);
        }catch(IllegalArgumentException e )
        {
            return false;
        }
        return true;
    }
public void assign(ClientStocksUtility cskidseller,ClientStocksUtility cskidbuyer,int qty) {
	ClientStocks csSeller=this.clientstockService.getClientStockById(cskidseller);
	ClientStocks csbuyer=this.clientstockService.getClientStockById(cskidbuyer);
	if(csSeller.getQuantity()-qty==0)
		this.clientstockService.deleteClientStock(cskidseller);
	else {
		csSeller.setQuantity(csSeller.getQuantity()-qty);
		this.clientstockService.updateClientStock(csSeller);
	}
	csbuyer.setQuantity(csbuyer.getQuantity()+qty);
	this.clientstockService.deleteClientStock(cskidbuyer);
	
	
}
public void sellmatch(SellOrder so,double amount,ClientStocksUtility cskid) {
        
        List<BuyOrder> buyod=this.buyservice.getBuyOrderByOrderDate();
        
        for(BuyOrder bo:buyod) {
            if(so.getInstrumentid().getInstrumentid().equals(bo.getInstrumentid().getInstrumentid())&&so.getPrice()==bo.getPrice()) {
            	double amountbuyer=bo.getPrice()*bo.getQuantity();
            	ClientStocksUtility cskidbuyer= new ClientStocksUtility(bo.getClientid(),bo.getInstrumentid());
            	int qty;
                if(so.getRemainingquantity()==bo.getRemainingquantity()) {
                    int tempqunatity=bo.getRemainingquantity()-so.getRemainingquantity();
                    qty=bo.getRemainingquantity();
                    bo.setRemainingquantity(tempqunatity);
                    so.setRemainingquantity(tempqunatity);
                    so.getClientid().setRemainingtransactionlimit(amount);
                    bo.getClientid().setRemainingtransactionlimit(amountbuyer);
                    bo.setStatus("Completed");
                    so.setStatus("Completed");
                }
                else if(so.getRemainingquantity()>bo.getRemainingquantity()) {
                    int tempqunatity=so.getRemainingquantity()-bo.getRemainingquantity();
                    qty=bo.getRemainingquantity();
                    so.setRemainingquantity(tempqunatity);
                    bo.setRemainingquantity(0);
                    so.getClientid().setRemainingtransactionlimit(amount);
                    bo.getClientid().setRemainingtransactionlimit(amountbuyer);
                    bo.setStatus("Completed");
                    so.setStatus("Partially Completed");
    
                }
                else {
                    int tempqunatity=bo.getRemainingquantity()-so.getRemainingquantity();
                    qty=so.getRemainingquantity();
                    so.getClientid().setRemainingtransactionlimit(amount);
                    bo.getClientid().setRemainingtransactionlimit(amountbuyer);
                    bo.setRemainingquantity(tempqunatity);
                    so.setRemainingquantity(0);
                    bo.setStatus("Partially Completed");
                    so.setStatus("Completed");      
                }
             assign(cskid,cskidbuyer,qty);
             this.buyRepo.save(bo);
             this.sellRepo.save(so);
             this.tradeService.insertTradeHistory(new TradeHistory(bo,so,LocalDate.now(),bo.getPrice()*qty));
             //break;    
            }    
        }
        
    }
}