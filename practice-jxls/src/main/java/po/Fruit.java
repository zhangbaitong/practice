package po;

/** 
 * 水果. 
 */  
public class Fruit {  
    /** 
     * 水果名称. 
     */  
    private String name;  
    /** 
     * 水果价格. 
     */  
    private float price;  
      
      
    public Fruit() {  
        super();  
    }  
      
    public Fruit(String name, float price) {  
        super();  
        this.name = name;  
        this.price = price;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public float getPrice() {  
        return price;  
    }  
    public void setPrice(float price) {  
        this.price = price;  
    }  
      
} 