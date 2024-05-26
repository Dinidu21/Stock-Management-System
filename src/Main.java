import java.util.*;
public class Main {

    static Scanner sc = new Scanner(System.in);
    static String uName = "dinidu";
    static int pw = 1234;
    static String[][] sp = new String[0][2];
    static String[] cate = new String[0];
    static String[][] itemsDetails = new String [0][6];

    // Method to clear the console
    private final static void clearConsole() {
        final String os = System.getProperty("os.name");
        try {
            if (os.contains("Linux") || os.contains("Mac")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            } else if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            // Handle any exceptions that occur
            System.err.println(e.getMessage());
        }
    }

    public static void rankItemsPerUnitPrice() {
        clearConsole();
        System.out.printf("-------------------------------------------------------------------------%n");
        System.out.printf("| %-85s |%n", tableCenter(85, "RANKED UNIT PRICE"));
        System.out.printf("-------------------------------------------------------------------------%n\n");

        // Sort the itemsDetails array based on unit price
        sortItemsByUnitPrice(itemsDetails);

        System.out.printf("--------------------------------------------------------------------------------------------%n");
        System.out.printf("| %-10s | %-18s | %-10s | %-10s | %-15s | %-10s |%n",
                tableCenter(10, "SID"),
                tableCenter(18, "CODE"),
                tableCenter(10, "DESC"),
                tableCenter(10, "PRICE"),
                tableCenter(15, "QTY"),
                tableCenter(10, "CAT"));
        System.out.printf("--------------------------------------------------------------------------------------------%n");

        for (int i = 0; i < itemsDetails.length; i++) {
            System.out.printf("| %-10s | %-18s | %-10s | %-10s | %-15s | %-10s |%n",
                    tableCenter(10, itemsDetails[i][0]),
                    tableCenter(18, itemsDetails[i][1]),
                    tableCenter(10, itemsDetails[i][2]),
                    tableCenter(10, itemsDetails[i][3]),
                    tableCenter(15, itemsDetails[i][4]),
                    tableCenter(10, itemsDetails[i][5]));
            System.out.printf("--------------------------------------------------------------------------------------------%n");
        }
        handleUserChoice();
    }

    public static void sortItemsByUnitPrice(String[][] items) {

        for (int i = 0; i < items.length - 1; i++) {
            for (int j = 0; j < items.length - 1 - i; j++) {
                try {
                    double price1 = Double.parseDouble(items[j][3]);
                    double price2 = Double.parseDouble(items[j + 1][3]);

                    if (price1 > price2) {
                        String[] temp = items[j];
                        items[j] = items[j + 1];
                        items[j + 1] = temp;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error: Unable to parse unit price to double. Skipping comparison.");
                }
            }
        }
    }


    public static void viewItems() {
        clearConsole();
        System.out.printf("-----------------------------------------------------------------------------------------%n");
        System.out.printf("| %-85s |%n", tableCenter(85, "VIEW ITEMS"));
        System.out.printf("-----------------------------------------------------------------------------------------%n\n");

        if (itemsDetails.length == 0) {
            System.out.println("No items found in the system.");
        } else {
            String currentCategory = "";
            for (int i = 0; i < itemsDetails.length; i++) {
                String category = itemsDetails[i][5];
                if (!category.equals(currentCategory)) {
                    currentCategory = category;
                    System.out.printf("\n\n%-1s : \n\n", currentCategory);
                    System.out.printf("------------------------------------------------------------------------%n");
                    System.out.printf("| %-11s | %-11s | %-11s | %-11s | %-11s |%n",
                            tableCenter(11, "SID"),
                            tableCenter(11, "CODE"),
                            tableCenter(11, "DESC"),
                            tableCenter(11, "PRICE"),
                            tableCenter(11, "QTY"));
                    System.out.printf("-------------------------------------------------------------------------%n");
                }

                System.out.printf("| %-11s | %-11s | %-11s | %-11s | %11s  |%n",
                        tableCenter(11, itemsDetails[i][0]),
                        tableCenter(11, itemsDetails[i][1]),
                        tableCenter(11, itemsDetails[i][2]),
                        tableCenter(11, itemsDetails[i][3]),
                        tableCenter(11, itemsDetails[i][4]));
                System.out.printf("---------------------------------------------------------------------%n");
            }
        }

        handleUserChoice();
    }

    public static String tableCenter(int tableWidth, String str) {
        int strLen = str.length();
        int totalPadding = tableWidth - strLen;
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        String padding = " ".repeat(leftPadding);
        String paddedStr = String.format("%s%s%s", padding, str, " ".repeat(rightPadding));

        return paddedStr;
    }

    public static void handleUserChoice() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Do you want to go to Stock Management Page (Y/N) ? ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("Y")) {
                stockManage();
                break;
            } else if (input.equalsIgnoreCase("N")) {
                itemManage();
                break;
            } else {
                System.out.println("Invalid input! Please enter 'Y' or 'N'.");
            }
        }
    }

    public static void addItem() {
        System.out.printf("------------------------------------------------------------------------%n");
        System.out.printf("|                           ADD ITEM                                   |%n");
        System.out.printf("------------------------------------------------------------------------%n\n");

        System.out.println();

        boolean hasItemCategories = cate.length > 0;
        boolean hasSuppliers = sp.length > 0;

        while (true) {
            if (!hasItemCategories) {
                System.out.println("OOPS! It seems that you don't have any item categories in the system.");
                System.out.print("Do you want to add a new item category? (Y/N) : ");
                char op = sc.next().charAt(0);
                sc.nextLine(); // Consume the newline character

                System.out.println();

                if (op == 'Y' || op == 'y') {
                    addItemCaF();
                    hasItemCategories = true; // Update the flag after adding a category
                } else if (op == 'N' || op == 'n') {
                    stockManage();
                    return; // Exit the method if the user doesn't want to add a category
                } else {
                    System.out.println("Invalid Input " + op + "! Please enter (Y/N) ");
                    continue;
                }
            }

            if (!hasSuppliers) {
                System.out.println("OOPS! It seems that you don't have any suppliers in the system.");
                System.out.print("Do you want to add a new supplier? (Y/N) : ");
                char op = sc.next().charAt(0);
                sc.nextLine(); // Consume the newline character

                System.out.println();

                if (op == 'Y' || op == 'y') {
                    addSupplier();
                    hasSuppliers = true; // Update the flag after adding a supplier
                } else if (op == 'N' || op == 'n') {
                    stockManage();
                    return; // Exit the method if the user doesn't want to add a supplier
                } else {
                    System.out.println("Invalid Input " + op + "! Please enter (Y/N) ");
                    continue;
                }
            }

            // Ask for item code after ensuring item categories and suppliers are available
            System.out.print("Item Code : ");
            String itemCode = sc.next();

            // Check for null or duplicate item code
            if (itemCode == null || itemCode.isEmpty()) {
                System.out.println("Item code cannot be null or empty!\n");
                continue;
            }

            boolean isDuplicate = false;
            for (String[] item : itemsDetails) {
                if (item[1].equals(itemCode)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                System.out.println("Item code already exists! Please enter a different code.\n");
                continue;
            }

            System.out.println("Supplier list : ");

            System.out.printf("----------------------------------------------%n");
            System.out.printf("| %-5s | %-10s | %-20s |%n" , " # " , "SUPPLIER ID" , "SUPPLIER NAME");
            System.out.printf("----------------------------------------------%n");

            for (int i = 0; i < sp.length; i++) {
                String name = sp[i][1];
                String code = (i+1) + " ";
                String id = sp[i][0];
                System.out.printf("| %-5s | %-10s | %-20s |%n" , code , id , name);
            }
            System.out.printf("----------------------------------------------%n");

            System.out.print("Enter the supplier number > ");
            int suppNum = Integer.parseInt(sc.next());

            if (suppNum < 1 || suppNum > sp.length) {
                System.out.println("Invalid inputs!\n");
                continue;
            }

            System.out.println("Item categories : ");

            System.out.printf("--------------------------------%n");
            System.out.printf("| %-5s | %-20s |%n" , " # " , "CATEGORY NAME" );
            System.out.printf("--------------------------------%n");

            for (int i = 0; i < cate.length; i++) {
                String name = cate[i];
                String code = (i+1) + " ";
                System.out.printf("| %-5s | %-20s |%n" , code , name);
            }
            System.out.printf("--------------------------------%n");

            System.out.println();

            System.out.print("Enter the category number > ");
            int catNum  = Integer.parseInt(sc.next());

            if (catNum < 1 || catNum > cate.length) {
                System.out.println("Invalid inputs!\n");
                continue;
            }

            System.out.print("Description : ");
            String desc = sc.next();

            System.out.print("Unit price : ");
            double price = sc.nextDouble();

            System.out.print("Qty on hand : ");
            int qty = sc.nextInt();

            itemsDetails = addItemDetails(itemsDetails, sp[suppNum - 1][0], itemCode, desc, String.valueOf(price), String.valueOf(qty) , cate[catNum - 1]);

            System.out.println("Item added successfully!");

            System.out.print("Do you want to add another item? (Y/N) : ");
            char OP = sc.next().charAt(0);

            System.out.println();

            if (OP == 'Y' || OP == 'y') {
                continue;
            } else if (OP == 'N' || OP == 'n') {
                clearConsole();
                stockManage();
            }
        }

    }

    public static String[][] addItemDetails(String[][] itemsDetails, String supplierId, String itemCode, String desc, String price, String qty, String category) {
        String[][] newArr = new String[itemsDetails.length + 1][6];
        System.arraycopy(itemsDetails, 0, newArr, 0, itemsDetails.length);
        newArr[itemsDetails.length] = new String[]{supplierId, itemCode, desc, price, qty, category};
        return newArr;
    }
    
		/*
		public static String[][] addItemDetails(String[][] itemsDetails, String supplierId, String itemCode, String desc, String price, String qty, String category) {
			String[][] newArr = new String[itemsDetails.length + 1][6];

			// Copy existing items to the new array
			for (int i = 0; i < itemsDetails.length; i++) {
				for (int j = 0; j < 6; j++) {
					newArr[i][j] = itemsDetails[i][j];
				}
			}

			// Add the new item details
			newArr[itemsDetails.length][0] = supplierId;
			newArr[itemsDetails.length][1] = itemCode;
			newArr[itemsDetails.length][2] = desc;
			newArr[itemsDetails.length][3] = price;
			newArr[itemsDetails.length][4] = qty;
			newArr[itemsDetails.length][5] = category;

			return newArr;
		}*/

    public static void getItemSupplierWise(){
        System.out.printf("------------------------------------------------------------------------%n");
        System.out.printf("|                           SEARCH SUPPLIER                            |%n");
        System.out.printf("------------------------------------------------------------------------%n\n");

        while(true){
            String suppId = "";

            for (int i = 0; i < sp.length; i++){
                suppId = sp[i][0];
            }

            System.out.print("Enter Supplier id : ");
            suppId = sc.next();

            boolean found = false;
            int supplierIndex = -1;

            for (int i = 0; i < sp.length; i++){
                if(sp[i][0].equals(suppId)){
                    found =  true;
                    supplierIndex = i;
                    System.out.println("supplier Name : " + sp[i][1]);
                    break;
                }

            }
            System.out.println();

            System.out.printf("----------------------------------------------------------------------------------%n");
            System.out.printf("| %-11s | %-18s | %-11s | %-11s | %-15s |%n" ,
                    tableCenter(11, "ITEM CODE"),
                    tableCenter(18, "DESCRIPTION"),
                    tableCenter(11, "UNIT PRICE"),
                    tableCenter(11, "QTY ON HAND"),
                    tableCenter(15, "CATEGORY"));
            System.out.printf("----------------------------------------------------------------------------------%n");

            for (int i = 0; i < itemsDetails.length; i++) {
                if (itemsDetails[i][0].equals(suppId)) {
                    System.out.printf("| %-11s | %-18s | %-11s | %-11s | %-15s |%n",
                            itemsDetails[i][1], itemsDetails[i][2], itemsDetails[i][3], itemsDetails[i][4], itemsDetails[i][5]);
                    System.out.printf("----------------------------------------------------------------------------------%n");
                }
            }
            System.out.print("Search succesfully. Do you want to another serach? (Y/N) :");
            char answer = sc.next().charAt(0);

            System.out.println();

            if(answer == 'Y' || answer == 'y'){
                continue;
            }else if(answer == 'N' || answer == 'n'){
                clearConsole();
                stockManage();
            }

            if(found){
                System.out.println("Can't find supplier id. Try again!\n");
                continue;
            }
        }
    }

    public static String[] delCate(int index) {
        if (cate == null || index < 0 || index >= cate.length) {
            return cate;
        }
        String[] newCate = new String[cate.length - 1];
        for (int i = 0, k = 0; i < cate.length; i++) {
            if (i == index) {
                continue;
            }
            newCate[k++] = cate[i];
        }
        return newCate;
    }

    public static void deleteCategory() {
        clearConsole();
        Scanner sc = new Scanner(System.in);
        boolean addMore = true;
        System.out.printf("+------------------------------------------------------------------+%n");
        System.out.printf("|                         DELETE CATEGORY                          |%n");
        System.out.printf("+------------------------------------------------------------------+%n%n");

        while (addMore) {
            boolean isExists = false;
            System.out.print("Category Name   : ");
            String name = sc.nextLine();
            for (int i = 0; i < cate.length; i++) {
                if (name.equals(cate[i])) {
                    cate = delCate(i);
                    isExists = true;
                    System.out.print("Deleted successfully! ");
                    break;
                }
            }
            if (!isExists) {
                System.out.printf("Can't find Category name. Try again! %n");
            }
            while (true) {
                System.out.print("Do you want to delete another Category name? (Y/N): ");
                char chr = sc.next().charAt(0);
                sc.nextLine(); // Consume the newline character
                if (chr == 'y' || chr == 'Y') {
                    break; // Continue to delete another category
                } else if (chr == 'n' || chr == 'N') {
                    clearConsole();
                    itemManage();
                    addMore = false;
                    return; // Exit the method to stop deleting categories
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }
    }

    public static void updateCategory() {
        clearConsole();
        Scanner sc = new Scanner(System.in);
        boolean addMore = true;
        System.out.printf("+------------------------------------------------------------------+%n");
        System.out.printf("|                         UPDATE CATEGORY                          |%n");
        System.out.printf("+------------------------------------------------------------------+%n%n");

        while (addMore) {
            boolean isExists = false; // Reset isExists to false at the beginning of each attempt
            System.out.print("\nCategory Name: ");
            String name = sc.nextLine();

            for (int i = 0; i < cate.length; i++) {
                if (name.equals(cate[i])) {
                    System.out.print("\nEnter the new category name: ");
                    String newName = sc.nextLine();
                    cate[i] = newName; // Update the category name
                    System.out.print("Updated successfully! ");
                    isExists = true;
                    break; // Exit the loop once the category is found and updated
                }
            }

            if (!isExists) {
                System.out.printf("Can't find category name. Try again! %n");
            }

            char chr;
            while (true) {
                System.out.print("Do you want to update another category name? (Y/N): ");
                chr = sc.next().charAt(0);
                sc.nextLine(); // Consume the newline character
                if (chr == 'y' || chr == 'Y') {
                    break; // Continue to update another category
                } else if (chr == 'n' || chr == 'N') {
                    clearConsole();
                    itemManage();
                    addMore = false;
                    return; // Exit the method to stop updating categories
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }
    }

    public static String[] addCategory(String name) {
        String[] newList = new String[cate.length + 1];
        for (int i = 0; i < cate.length; i++) {
            newList[i] = cate[i];
        }
        newList[cate.length] = name;
        return newList;
    }

    public static void addItemCaF() {
        Scanner sc = new Scanner(System.in);
        boolean addMore = true;

        while (addMore) {
            clearConsole();
            System.out.printf("+------------------------------------------------------------------+%n");
            System.out.printf("|                       ADD ITEM CATEGORY                          |%n");
            System.out.printf("+------------------------------------------------------------------+%n%n");
            String name = "";

            boolean check = true;

            while (check) {
                System.out.print("Enter the new item category: ");
                name = sc.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Category cannot be empty. Try again!\n");
                    continue; // Restart the loop if the category name is empty
                }

                // Check for duplicate category name
                boolean isDuplicate = false;
                for (String category : cate) {
                    if (category.equalsIgnoreCase(name)) {
                        isDuplicate = true;
                        break;
                    }
                }

                if (isDuplicate) {
                    System.out.println("Category already exists. Try again!\n");
                    continue; // Restart the loop if the category name is a duplicate
                }

                cate = addCategory(name); // Add the new category
                check = false;
            }

            while (true) {
                System.out.print("Added successfully! Do you want to add another category? (Y/N): ");
                char chr = sc.next().charAt(0);
                sc.nextLine(); // Consume the newline character
                if (chr == 'y' || chr == 'Y') {
                    break; // Continue to add another category
                } else if (chr == 'n' || chr == 'N') {
                    addMore = false;
                    clearConsole();
                    itemManage();
                    return; // Exit the method to stop adding categories
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }
    }

    public static void itemManage() {
        Scanner sc = new Scanner(System.in);
        clearConsole();
        System.out.printf("+------------------------------------------------------------------+%n");
        System.out.printf("|                       MANAGE ITEM CATEGORY                       |%n");
        System.out.printf("+------------------------------------------------------------------+%n%n");
        System.out.printf("[1] Add New Item Category");
        System.out.printf("\t\t[2] Delete Item Category%n");
        System.out.printf("[3] Update Item Category");
        System.out.printf("\t\t[4] Stock Management%n");
        System.out.println();

        boolean flag = false;
        do {
            try {
                System.out.printf("Enter an option to continue > ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume the newline character

                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid Input! Please enter a number between 1 and 4.");
                    continue;
                }

                switch (choice) {
                    case 1: {
                        clearConsole();
                        addItemCaF();
                        flag = true;
                    } break;
                    case 2: {
                        clearConsole();
                        deleteCategory();
                        flag = true;
                    } break;
                    case 3: {
                        clearConsole();
                        updateCategory();
                        flag = true;
                    } break;
                    case 4: {
                        clearConsole();
                        stockManage();
                        flag = true;
                    } break;
                    default: {
                        System.out.printf("Invalid Input! Please enter a number between 1 and 4.%n");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input! Please enter a valid number.");
                sc.next(); // Clear the invalid input
            }
        } while (!flag);
    }

    public static void stockManage() {
        Scanner sc = new Scanner(System.in);
        clearConsole();
        System.out.printf("+------------------------------------------------------------------+%n");
        System.out.printf("|                         STOCK MANAGEMENT                         |%n");
        System.out.printf("+------------------------------------------------------------------+%n%n");
        System.out.printf("[1] Manage Item Categories");
        System.out.printf("\t\t[2] Add Item%n");
        System.out.printf("[3] Get Items Supplier Wise");
        System.out.printf("\t\t[4] View Items%n");
        System.out.printf("[5] Rank Items Per Unit Price");
        System.out.printf("\t\t[6] Home Page%n");

        boolean flag = false;
        do {
            try {
                System.out.printf("%nEnter an option to continue > ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume the newline character

                if (choice < 1 || choice > 6) {
                    System.out.println("Invalid Input! Please enter a number between 1 and 6.");
                    continue;
                }

                switch (choice) {
                    case 1: {
                        clearConsole();
                        itemManage();
                        flag = true;
                    } break;
                    case 2: {
                        clearConsole();
                        addItem();
                        flag = true;
                    } break;
                    case 3: {
                        clearConsole();
                        getItemSupplierWise();
                        flag = true;
                    } break;
                    case 4: {
                        clearConsole();
                        viewItems();
                        flag = true;
                    } break;
                    case 5: {
                        clearConsole();
                        rankItemsPerUnitPrice();
                        flag = true;
                    } break;
                    case 6: {
                        clearConsole();
                        homePage();
                        flag = true;
                    } break;
                    default: {
                        System.out.printf("Invalid Input! Please enter a number between 1 and 6.%n");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input! Please enter a valid number.");
                sc.next(); // Clear the invalid input
            }
        } while (!flag);
    }

    public static String[][] delSupp(int index) {
        if (sp == null || index < 0 || index >= sp.length) {
            return sp; // Return the original array if index is out of bounds
        }
        String[][] newSp = new String[sp.length - 1][2];
        for (int i = 0, k = 0; i < sp.length; i++) {
            if (i == index) {
                continue; // Skip the element at the specified index
            }
            newSp[k++] = sp[i];
        }
        return newSp;
    }

    public static void deleteSupplier() {
        clearConsole();
        Scanner sc = new Scanner(System.in);
        boolean addMore = true;
        System.out.printf("+------------------------------------------------------------------+%n");
        System.out.printf("|                         DELETE SUPPLIER                          |%n");
        System.out.printf("+------------------------------------------------------------------+%n%n");

        while (addMore) {
            System.out.print("Supplier ID   : ");
            String id = sc.nextLine().trim(); // Trim whitespace from input

            boolean isExists = false;
            for (int i = 0; i < sp.length; i++) {
                if (id.equals(sp[i][0])) {
                    sp = delSupp(i); // Delete the supplier at index i
                    isExists = true;
                    System.out.println("\nDeleted successfully!");
                    break;
                }
            }

            if (!isExists) {
                System.out.printf("Supplier ID '%s' not found. Try again! %n", id);
            }

            while (true) {
                System.out.print("Do you want to delete another supplier? (Y/N): ");
                char chr = sc.next().charAt(0);
                sc.nextLine(); // Consume the newline character
                if (chr == 'y' || chr == 'Y') {
                    break; // Continue to delete another supplier
                } else if (chr == 'n' || chr == 'N') {
                    clearConsole();
                    suppManage();
                    addMore = false;
                    return; // Exit the method to stop deleting suppliers
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.\n");
                }
            }
        }
    }

    public static void searchSupplier() {
        clearConsole();
        Scanner sc = new Scanner(System.in);
        boolean addMore = true;
        System.out.printf("+------------------------------------------------------------------+%n");
        System.out.printf("|                         SEARCH SUPPLIER                          |%n");
        System.out.printf("+------------------------------------------------------------------+%n%n");

        while (addMore) {
            boolean isExists = false; // Reset isExists to false at the beginning of each attempt
            System.out.print("\nSupplier ID   : ");
            String id = sc.nextLine().trim(); // Trim whitespace from input

            if (id.isEmpty()) {
                System.out.println("Supplier ID cannot be empty. Try again!");
                continue; // Restart the loop if the Supplier ID is empty
            }

            // Check if the Supplier ID exists in the list of suppliers
            for (int i = 0; i < sp.length; i++) {
                if (id.equals(sp[i][0])) {
                    System.out.println("Supplier Name : " + sp[i][1]);
                    isExists = true;
                    break; // Exit the loop once the supplier is found
                }
            }

            if (!isExists) {
                System.out.printf("Supplier ID '%s' not found. Try again! %n", id);
            }

            char chr;
            while (true) {
                System.out.print("\nDo you want to search another supplier? (Y/N): ");
                chr = sc.next().charAt(0);
                sc.nextLine(); // Consume the newline character
                if (chr == 'y' || chr == 'Y') {
                    break; // Continue to search another supplier
                } else if (chr == 'n' || chr == 'N') {
                    clearConsole();
                    suppManage();
                    addMore = false;
                    return; // Exit the method to stop searching suppliers
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }
    }

    public static void updateSupplier() {
        clearConsole();
        Scanner sc = new Scanner(System.in);
        boolean addMore = true;
        System.out.printf("+------------------------------------------------------------------+%n");
        System.out.printf("|                         UPDATE SUPPLIER                          |%n");
        System.out.printf("+------------------------------------------------------------------+%n%n");

        while (addMore) {
            boolean isExists = false; // Reset isExists to false at the beginning of each attempt

            while (true) {
                System.out.print("\nSupplier ID   : ");
                String id = sc.nextLine().trim(); // Trim whitespace from input

                if (id.isEmpty()) {
                    System.out.println("Supplier ID cannot be empty. Try again!");
                    continue;
                }

                // Check if the ID exists in the list of suppliers
                for (int i = 0; i < sp.length; i++) {
                    if (id.equals(sp[i][0])) {
                        System.out.println("Supplier Name : " + sp[i][1]);
                        System.out.print("\nEnter the new supplier name : ");
                        String name = sc.nextLine();
                        sp[i][1] = name; // Update the supplier's name
                        System.out.print("Updated successfully!");
                        isExists = true;
                        break; // Exit the loop once the supplier is found and updated
                    }
                }

                if (!isExists) {
                    System.out.printf("Supplier ID '%s' not found. Try again!%n", id);
                } else {
                    break; // Exit the loop if the supplier is found and updated
                }
            }

            char chr;
            while (true) {
                System.out.print("Do you want to update another supplier? (Y/N): ");
                chr = sc.next().charAt(0);
                sc.nextLine(); // Consume the newline character
                if (chr == 'y' || chr == 'Y') {
                    break; // Continue to update another supplier
                } else if (chr == 'n' || chr == 'N') {
                    clearConsole();
                    suppManage();
                    addMore = false;
                    return; // Exit the method to stop updating suppliers
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }
    }



    public static void viewSuppliers() {
        clearConsole();
        Scanner sc = new Scanner(System.in);
        boolean flag = true;

        System.out.printf("+------------------------------------------------------------------+%n");
        System.out.printf("|                          VIEW SUPPLIER                           |%n");
        System.out.printf("+------------------------------------------------------------------+%n%n");

        System.out.printf("+----------------------+--------------------+%n");
        System.out.printf("|      SUPPLIER ID     |    SUPPLIER NAME   |%n");
        System.out.printf("+----------------------+--------------------+%n");

        for (int i = 0; i < sp.length; i++) {
            String id = sp[i][0];
            String name = sp[i][1];

            int idPadding = Math.max(0, (20 - id.length()) / 2); // Prevent negative padding
            String idPadded = tableCenter(20, id);

            int namePadding = Math.max(0, (18 - name.length()) / 2); // Prevent negative padding
            String namePadded = tableCenter(18, name);

            System.out.printf("| %-20s | %-18s |%n", idPadded, namePadded);
        }

        System.out.printf("+----------------------+--------------------+%n");


        while (flag) {
            System.out.print("Do you want to go to the supplier manage page (Y/N) : ");
            String input = sc.nextLine().trim(); // Trim whitespace from input

            if (input.length() != 1 || (input.charAt(0) != 'Y' && input.charAt(0) != 'y' && input.charAt(0) != 'N' && input.charAt(0) != 'n')) {
                System.out.println("Invalid Input! Please enter 'Y' or 'N'.");
            } else {
                char chr = Character.toUpperCase(input.charAt(0)); // Convert to uppercase for uniformity
                switch (chr) {
                    case 'Y':
                        clearConsole();
                        suppManage();
                        flag = false;
                        break;
                    case 'N':
                        clearConsole();
                        suppManage();
                        flag = false;
                        break;
                }
            }
        }
    }

    public static String[][] addSupplier(String id, String name) {
        String[][] newList = new String[sp.length + 1][2];
        for (int i = 0; i < sp.length; i++) {
            newList[i] = sp[i];
        }
        newList[sp.length][0] = id;
        newList[sp.length][1] = name;
        return newList;
    }

    public static void addSupplier() {
        boolean addMore = true;
        Scanner sc = new Scanner(System.in);
        while (addMore) {
            clearConsole();
            System.out.printf("+------------------------------------------------------------------+%n");
            System.out.printf("|                         ADD SUPPLIER                             |%n");
            System.out.printf("+------------------------------------------------------------------+%n%n");

            String id = "";
            boolean isExists = true; // Reset isExists to true at the beginning of each attempt

            while (isExists) {
                System.out.print("Supplier ID   : ");
                id = sc.nextLine();

                if (id.isEmpty()) {
                    System.out.println("Supplier ID cannot be empty. Try again!\n");
                    continue; // Restart the loop if the Supplier ID is empty
                }

                isExists = false; // Assume the ID doesn't exist until proven otherwise
                for (int i = 0; i < sp.length; i++) {
                    if (id.equals(sp[i][0])) {
                        System.out.println("Already exists. Try another supplier ID!");
                        isExists = true; // Set isExists to true if the ID already exists
                        break;
                    }
                }
            }

            System.out.print("Supplier Name : ");
            String name = sc.nextLine();
            sp = addSupplier(id, name); // Replace with the actual method to add supplier
            System.out.print("Added successfully! ");

            char chr;
            while (true) {
                System.out.print("Do you want to add another supplier? (Y/N): ");
                chr = sc.next().charAt(0);
                sc.nextLine(); // Consume the newline character
                if (chr == 'y' || chr == 'Y') {
                    break; // Continue to add another supplier
                } else if (chr == 'n' || chr == 'N') {
                    addMore = false;
                    clearConsole();
                    suppManage();
                    return; // Exit the method to stop adding suppliers
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }
    }

    public static void suppManage() {
        Scanner sc = new Scanner(System.in);
        System.out.printf("+------------------------------------------------------------------+%n");
        System.out.printf("|                      SUPPLIER MANAGE                             |%n");
        System.out.printf("+------------------------------------------------------------------+%n%n");
        System.out.printf("[1] Add Supplier");
        System.out.printf("\t\t[2] Update Supplier %n");
        System.out.printf("[3] Delete Supplier");
        System.out.printf("\t\t[4] View Suppliers%n");
        System.out.printf("[5] Search Supplier");
        System.out.printf("\t\t[6] Home Page%n");

        boolean flag = false;

        do {
            try {
                System.out.printf("%nEnter an option to continue > ");
                String input = sc.next();
                if (input.length() != 1 || !Character.isDigit(input.charAt(0))) {
                    System.out.println("Invalid Input! Please enter a number between 1 and 6.");
                    continue;
                }
                int op = Integer.parseInt(input);

                switch (op) {
                    case 1 -> {
                        clearConsole();
                        addSupplier();
                        flag = true;
                    }
                    case 2 -> {
                        clearConsole();
                        updateSupplier();
                        flag = true;
                    }
                    case 3 -> {
                        clearConsole();
                        deleteSupplier();
                        flag = true;
                    }
                    case 4 -> {
                        clearConsole();
                        viewSuppliers();
                        flag = true;
                    }
                    case 5 -> {
                        clearConsole();
                        searchSupplier();
                        flag = true;
                    }
                    case 6 -> {
                        clearConsole();
                        homePage();
                        flag = true;
                    }
                    default -> System.out.printf("Invalid Input! Please enter a number between 1 and 6.%n");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                sc.next(); // clear the invalid input
            }
        } while (!flag);
    }

    public static void verify() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.printf("\nPlease enter the user name to verify it's you: ");
            String userInput = sc.nextLine();

            if (userInput != null && !userInput.isEmpty() && userInput.equals(uName)) {
                System.out.println("Hey " + userInput);
                break;
            } else {
                System.out.printf("Invalid user name. Please try again!\n");
            }
        }

        while (true) {
            System.out.print("Enter your current password: ");
            String input = sc.nextLine();
            try {
                int pwd = Integer.parseInt(input);

                if (pwd == pw) {
                    break;
                } else {
                    System.out.println("Incorrect password. Please try again!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Password must contain only numbers. Please try again!\n");
            }
        }
    }

    public static void changeCred() {
        Scanner sc = new Scanner(System.in);
        System.out.printf("+------------------------------------------------------------------+%n");
        System.out.printf("|                           CREDENTIAL MANAGE                      |%n");
        System.out.printf("+------------------------------------------------------------------+%n%n");
        verify(); // Verify the user
        System.out.print("Enter your new password: ");
        String pw = sc.nextLine();
        System.out.println();

        System.out.print("Password changed successfully! Do you want to go to the home page? (Y/N): ");
        char chr = sc.next().charAt(0);
        sc.nextLine(); // Consume newline character
        switch (chr) {
            case 'y': clearConsole();
                homePage();
                break;
            case 'Y':
                clearConsole();
                homePage();
                break;
            case 'n': clearConsole();
                loginPage();
                break;
            case 'N':
                clearConsole();
                loginPage();
                break;
            default:
                System.out.printf("Invalid Input!");
        }
    }

    public static void homePage() {
        Scanner sc = new Scanner(System.in);
        System.out.printf("+----------------------------------------------------------------+%n");
        System.out.printf("|            WELCOME TO IJSE STOCK MANAGEMENT SYSTEM             |%n");
        System.out.printf("+----------------------------------------------------------------+%n%n");
        System.out.printf("[1] Change the Credentials");
        System.out.printf("\t[2] Supplier Manage%n");
        System.out.printf("[3] Stock Manage");
        System.out.printf("\t\t[4] Log out%n");
        System.out.printf("[5] Exit the System%n");
        boolean flag = false;

        do {
            try {
                System.out.printf("%nEnter an option to continue > ");
                String input = sc.next();
                if (input.length() != 1 || !Character.isDigit(input.charAt(0))) {
                    System.out.println("Invalid Input! Please enter a number between 1 and 5.");
                    continue;
                }
                char chr = input.charAt(0);

                switch (chr) {
                    case '1': {
                        clearConsole();
                        changeCred();
                        flag = true;
                    } break;
                    case '2': {
                        clearConsole();
                        suppManage();
                        flag = true;
                    } break;
                    case '3': {
                        clearConsole();
                        stockManage();
                        flag = true;
                    } break;
                    case '4': {
                        clearConsole();
                        loginPage();
                        flag = true;
                    } break;
                    case '5': {
                        System.out.printf("Exiting the System. . . ");
                        System.exit(0);
                    } break;
                    default: {
                        System.out.println("Invalid Input! Please enter a number between 1 and 5.");
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                sc.next(); // clear the invalid input
            }
        } while (!flag);
    }

    public static void loginPage() {
        Scanner sc = new Scanner(System.in);
        System.out.printf("+----------------------------------------------------------------+%n");
        System.out.printf("|                           LOGIN PAGE                           |%n");
        System.out.printf("+----------------------------------------------------------------+%n%n");

        boolean isAuthenticated = false;

        do {
            System.out.printf("%nUser Name  : ");
            String EName = sc.nextLine();
            if (EName.equals(uName)) { // Compare strings using the equals() method
                while (!isAuthenticated) {
                    System.out.println();
                    System.out.printf("Password   : ");
                    try {
                        int Epw = sc.nextInt();
                        sc.nextLine(); // Consume the newline character
                        if (Epw == pw) { // Compare integers using ==
                            clearConsole();
                            homePage();
                            isAuthenticated = true; // Set flag to true to exit the loop
                        } else {
                            System.out.println("Incorrect password. Please try again!");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Incorrect password. Please try again!");
                        sc.nextLine(); // Consume the invalid input
                    }
                }
            } else {
                System.out.println("Invalid user name. Please try again!");
            }
        } while (!isAuthenticated);
        // Clear the console after successful login
        clearConsole();
    }

    public static void main(String[] args) {
        //loginPage();
        //homePage();
        //addSupplier();
        //stockManage();
        //viewSuppliers();
        //updateSupplier();
        //deleteSupplier();
        //stockManage();
        //addItemCaF();
        //addItem();
    }
}
