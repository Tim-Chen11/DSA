import java.util.*;

/**
Step 1: Create a method called get_hotel_price_info that uses the hotel_data and hotel_supplier_markup_info methods to return a list of hotels. Each hotel should contain a key called 'selling_price'. The value of selling_price is determined according to the hotel's supplier (see hotel_supplier_markup_info). 

  See sample output: 
  [
      {
          'hotel_id': 100,
          'hotel_name': 'The Colorado Overlook Hotel',
          'supplier_price': 100,
          'supplier': 'supplierA',
          'selling_price': 110
      },
      {
          'hotel_id': 150,
          'hotel_name': 'The Pennsylvanian Factory Hotel',
          'supplier_price': 200,
          'supplier': 'supplierB',
          'selling_price': 250
      }, ...
  ]
  
  Please use the HotelMarkup Class when creating the output.
**/

// ==== PART2

/**
Step 2: Let's add a condition on when we should apply the markup. Apply markup to the supplier price only if the following rules are true else default to
the existing supplier price. 

See the rules and rule_set objects below. You are provided with a ruleset which consists of multiple rules and each rule consists of a list of conditions. 

To determine if the markup should be applied, the rules in the ruleset need to be ORd together and the conditions in each rule needs to be AND together.
**/

class Condition {
  public String conditionName;
  public String conditionComparison;
  public int conditionValue;
  
  public Condition(String conditionName, String conditionComparison, int conditionValue) {
    this.conditionName = conditionName;
    this.conditionComparison = conditionComparison;
    this.conditionValue = conditionValue;
  }
}



class Question2 {
  public static List<HashMap<Integer, Condition>> getRuleSet() {
    var rule1 = new HashMap<Integer, Condition>() {{ // AND
      put(1, new Condition("hotel_id", "notEquals", 100));
    }};    
    
    var rule2 = new HashMap<Integer, Condition>() {{ // AND
      put(1, new Condition("supplier_price", "lessThan", 200));
      put(2, new Condition("hotel_id", "lessThan", 200));
    }};    
    
    var rule3 = new HashMap<Integer, Condition>() {{ // AND
      put(1, new Condition("supplier_price", "greaterThan", 500));
      put(2, new Condition("hotel_id", "greaterThan", 300));
    }};
    
    return List.of(rule1, rule2); // OR
    // return List.of(rule1, rule2, rule3); // OR

  }
}

// PART 3===

/**
Step 3: Implement a process to change the static markup and penalize the suppliers not performing well

Implement a function that takes in the following request and determines the new markup for a supplier

Conditions:
  How to determine new markup? Request has 3 alert types:
    Success - Do nothing
    Warn - Markup original by 20%
    Alert - Markup original by 40%

Penalize the supplier only if penalize flag for the supplier is true in the static data
IGNORE --- Determine the markup for the supplier and only valid for 2 hrs, after that default to the original markup percentage
**/
  
class Question3 {
  public static Map<String, String> getPenaltyRequestBody() {
     return new HashMap<String, String>() {{
      put("alert_type", "alert");
      put("alert_supplier", "supplierA");
    }};
  }
}

  


class Solution {

  public static void main(String[] args) {
    List<Hotel> hotelList = Question1.getHotelData();
    List<HotelSupplierMarkupInfo> MarkupInfo = Question1.getMarkupInfo();
    for(HotelSupplierMarkupInfo info : MarkupInfo){
      if(info.shouldPenalize){
        if(info.supplierName.equals(Question3.getPenaltyRequestBody().get("alert_supplier"))){
          if(Question3.getPenaltyRequestBody().get("alert_type").equals("warn")){
            info.markupPercentage += 20;
          }else if(Question3.getPenaltyRequestBody().get("alert_type").equals("alert")){
            info.markupPercentage += 40;
          }
        }
      }
    }

    Map<String, HotelSupplierMarkupInfo> map = new HashMap<>();

    for(HotelSupplierMarkupInfo info : MarkupInfo){
      map.put(info.supplierName, info);
    }


    List<HashMap<Integer, Condition>> conditions = Question2.getRuleSet();
    List<HotelMarkup> hotelMarkups = new ArrayList<>();


    for(Hotel hotel : hotelList){
      // put(1, new Condition("supplier_price", "greaterThan", 500));
      // put(2, new Condition("hotel_id", "greaterThan", 300));
      boolean check = false;
      for(HashMap<Integer, Condition> hashmap : conditions){
        for (Map.Entry<Integer, Condition> entry : hashmap.entrySet()) {

            if(entry.getValue().conditionComparison.equals("greaterThan")){
              if(entry.getValue().conditionName.equals("hotel_id")){
                check = hotel.hotelId > entry.getValue().conditionValue;
              }
              else if(entry.getValue().conditionName.equals("supplier_price")){
                check = hotel.supplierPrice > entry.getValue().conditionValue;
              }
            }
            if(entry.getValue().conditionComparison.equals("lessThan")){
              if(entry.getValue().conditionName.equals("hotel_id")){
                check = hotel.hotelId < entry.getValue().conditionValue;
              }
              else if(entry.getValue().conditionName.equals("supplier_price")){
                check = hotel.supplierPrice < entry.getValue().conditionValue;
              }
            }
            if(entry.getValue().conditionComparison.equals("notEquals")){
              if(entry.getValue().conditionName.equals("hotel_id")){
                
                check = hotel.hotelId != entry.getValue().conditionValue;
              }
              else if(entry.getValue().conditionName.equals("supplier_price")){
                check = hotel.supplierPrice != entry.getValue().conditionValue;
              }
            }

            if(!check){
              break;
            }
        }

        if(check){
          break;
        }
        
      }

      double sellingprice;
      if(check){
        HotelSupplierMarkupInfo info = map.get(hotel.supplierName);
        sellingprice = hotel.supplierPrice * (100+info.markupPercentage)/100;
      }
      else{
        sellingprice = hotel.supplierPrice;
      }

      HotelMarkup markup = new HotelMarkup(hotel.hotelId, hotel.hotelName, hotel.supplierPrice, hotel.supplierName, sellingprice);
      hotelMarkups.add(markup);
    }

    for(HotelMarkup markup : hotelMarkups){
      System.out.println(markup.toString());
    }
  }


}

class Question1 {
  
  public static List<Hotel> getHotelData() {
    return List.of(
      new Hotel(100,"The Colorado Overlook Hotel", 100, "supplierA"),
      new Hotel(150, "The Pennsylvanian Factory Hotel", 200, "supplierB")
    );
  }

  public static List<HotelSupplierMarkupInfo> getMarkupInfo() {
    return List.of(
      new HotelSupplierMarkupInfo("supplierA", 10, false),
      new HotelSupplierMarkupInfo("supplierB", 25, false)
    );
  } 
}


class Hotel {
  public int hotelId;
  public String hotelName;
  public double supplierPrice;
  public String supplierName;

  public Hotel(int hotelId, String hotelName, double supplierPrice, String supplierName) {
    this.hotelId = hotelId;
    this.hotelName = hotelName;
    this.supplierPrice = supplierPrice;
    this.supplierName = supplierName;
  }
}

class HotelMarkup {
  public int hotelId;
  public String hotelName;
  public double supplierPrice;
  public String supplierName;
  public double sellingPrice;

  public HotelMarkup(int hotelId, String hotelName, double supplierPrice, String supplierName, double sellingPrice) {
    this.hotelId = hotelId;
    this.hotelName = hotelName;
    this.supplierPrice = supplierPrice;
    this.supplierName = supplierName;
    this.sellingPrice = sellingPrice;
  }

  @Override
  public String toString() {
    return "HotelMarkup{" +
           "hotelId=" + hotelId +
           ", hotelName='" + hotelName + '\'' +
           ", supplierPrice=" + supplierPrice +
           ", supplierName='" + supplierName + '\'' +
           ", sellingPrice=" + sellingPrice +
           '}';
  }
}

class HotelSupplierMarkupInfo {
  public String supplierName;
  public double markupPercentage;
  public boolean shouldPenalize;
  
  public HotelSupplierMarkupInfo(String supplierName, double markupPercentage, boolean shouldPenalize) {
    this.supplierName = supplierName;
    this.markupPercentage = markupPercentage;
    this.shouldPenalize = shouldPenalize; 
  }
}
