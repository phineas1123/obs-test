## Database script : obs-test/src/main/resources/schema.sql

## Running Application:
1. Clone Into Local Repository
2. Open with Java Editor (IntellijIDEA/VSCode/Eclipse)
3. Run: mvn clean install to install the project into local .m2 repository
4. Open & Run Main class ObsTestApplication at **obs-test/src/main/resources/src/main/java/com.example.obstest**


Logic for adding order:
1. check if the stock is available : **CURRENT_STOCK = TOTAL_TOP_UP_STOCK - TOTAL_WITHDRAW_STOCK**
2. if the NEW_STOCK > CURRENT_STOCK -> Throw Error **NOT_ENOUGHT_STOCK**, else: insert to order table, insert to inventory with qty = NEW_STOCK and type = 'W'

Logic for update order:
1. get existing data order
2. calculate DIFF_STOCK (EXISTING_DATA_ORDER_QTY - NEW_STOCK)
3. if DIFF_STOCK > 0 : Withdraw, else: TopUp
5. check if the stock is available : **CURRENT_STOCK = TOTAL_TOP_UP_STOCK - TOTAL_WITHDRAW_STOCK**
6. if the NEW_STOCK > CURRENT_STOCK -> Throw Error **NOT_ENOUGHT_STOCK**, else: insert to order table
7. IF Withdraw, then insert to inventory with qty = NEW_STOCK and type = 'W'. Else insert to inventory with qty = NEW_STOCK and type = 'T'

