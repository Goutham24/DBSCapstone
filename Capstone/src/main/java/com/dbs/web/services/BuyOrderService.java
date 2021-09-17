package com.dbs.web.services;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dbs.web.beans.BuyOrder;
import com.dbs.web.beans.Client;
import com.dbs.web.beans.SellOrder;
import com.dbs.web.repository.BuyOrderRepository;
import com.dbs.web.repository.SellOrderRepository;
@Service
public class BuyOrderService {
    @Autowired
    private BuyOrderRepository buyRepo;
    @Autowired
    private SellOrderService sellservice;
    @Autowired
    private SellOrderRepository sellRepo;
    public List<BuyOrder> getBuyOrderByOrderDate() {
        try { 
            List<BuyOrder> buyorder =this.buyRepo.findByOrderDate().get();
            return buyorder; 
        }
        catch(IllegalArgumentException e ) { 
        	return null; 
        	} 
    	}
    
    public boolean insertBuyOrder(BuyOrder bo)    {
        if(this.buyRepo.findById(bo.getBid()).isPresent())
            return false;
        try {
        	bo.setOrderdate(LocalDate.now());
        	bo.setRemainingquantity(bo.getQuantity());
        	Client bc=bo.getClientid();
        	double amount=bo.getPrice()*bo.getQuantity();
        	if(bc.getRemainingtransactionlimit()-amount<=0)
        		return false;
            this.buyRepo.save(bo);
            buymatch(bo,amount);
        }catch(IllegalArgumentException e )
        {
            return false;
        }
        return true;
    }
    public void buymatch(BuyOrder bo,double amount) {
        
        List<SellOrder> sellod=this.sellservice.getSellOrderByOrderDate();
        System.out.println("Method called");
        for(SellOrder so:sellod) {
            if(so.getInstrumentid().getInstrumentid().equals(bo.getInstrumentid().getInstrumentid())&&so.getPrice()==bo.getPrice()) {
            	double amountseller=so.getPrice()*so.getQuantity();
                if(so.getRemainingquantity()==bo.getRemainingquantity()) {
                    int tempqunatity=bo.getRemainingquantity()-so.getRemainingquantity();
                    bo.setRemainingquantity(tempqunatity);
                    so.setRemainingquantity(tempqunatity);
                    bo.getClientid().setRemainingtransactionlimit(amount);
                    so.getClientid().setRemainingtransactionlimit(amountseller);
                    bo.setStatus("Completed");
                    so.setStatus("Completed");
                }
                else if(so.getRemainingquantity()>bo.getRemainingquantity()) {
                    int tempqunatity=so.getRemainingquantity()-bo.getRemainingquantity();
                    so.setRemainingquantity(tempqunatity);
                    bo.setRemainingquantity(0);
                    bo.getClientid().setRemainingtransactionlimit(amount);
                    so.getClientid().setRemainingtransactionlimit(amountseller);
                    bo.setStatus("Completed");
                    so.setStatus("Partially Completed");
    
                }
                else {
                    int tempqunatity=bo.getRemainingquantity()-so.getRemainingquantity();
                    bo.setRemainingquantity(tempqunatity);
                    so.setRemainingquantity(0);
                    bo.getClientid().setRemainingtransactionlimit(amount);
                    so.getClientid().setRemainingtransactionlimit(amountseller);
                    bo.setStatus("Partially Completed");
                    so.setStatus("Completed");
                    
                }
             this.sellRepo.save(so);
             this.buyRepo.save(bo);
             System.out.println("Method called");
               // break;
            }    
        }
        
        
    }
}