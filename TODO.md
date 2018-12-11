# To-Do List


* Disable leftswipt to edit
  * Change in SwipeControllerActions.java
  * Change in SwipeController.java



* Change (add from v1.0) store data to phone's local storage instead of new array list upon app opening each time
  * (Done within: MainActivity.java) -> around line 43/44 of v1.0:
    * Diable: itemsList = new ArrayList<Item>();
    * Enable: itemsList = getArrayVal(getApplicationContext());
    * Also change behavior in addItem() & removeItem()
    * Add: storeArrayVal() & getArrayVal()



* Add boolean isPriority to Item.java
  * Add in ViewHolder.java
  * Add in RecyclerViewAdapter -> onBindViewHolder()
  * Add in res -> layout -> low_layout.xml
  * Add in res -> layout -> dialog_add_item.xml
