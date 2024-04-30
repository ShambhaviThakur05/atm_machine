import java.io.*;
import java.util.*;

class Account{

    static int acc_number = 1111;   //unique for every account

    String acc_holder_name;     

    int pin;

    double balance;

    String unique_id;       // unique id as there is no debit card user need to remeber this

    int a_no;


    void createAcc(){

        a_no = acc_number;

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the account holder name?");
        acc_holder_name = sc.nextLine();
        System.out.println("\nEnter the Username?");
        unique_id = sc.nextLine();

        int length_pin = 0;
        do{
            System.out.println("\nPlease enter secret 4 digit pin?");
            pin = sc.nextInt();
            length_pin = String.valueOf(pin).length();
        }while(length_pin != 4);

        System.out.println("\nEnter initial deposit amount?");
        balance = sc.nextDouble();
        System.out.println("\nCongratulations Account Successfully Created !!\n");
        System.out.println("Account Details \n" + "Account Number - " + a_no + 
            "\nAccount Holder Name - " + acc_holder_name + "\nBalance - " + balance + "\n\nThank You!" 
        );

        // Create a file with account number
        String fileName = acc_number + ".txt";
        File file = new File(fileName);
        
        try{
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("Account Created\n");
            writer.write("Account Number : " + acc_number + "\n");
            writer.write("User Id- r: " +unique_id + "\n");
            writer.write("Account Holder Name : " + "acc_holder_name" + "\n");
            writer.write("PIN : " + pin + "\n");
            writer.write("Balance : " + balance + "\n");
            writer.write("Date : " + new Date() + "\n\n\n");
            writer.close();
            
        }catch (IOException e){
            System.out.println("An error has occured while creating the file " + fileName);
            e.printStackTrace();
        }

        try{    // try to remove this try catch block afterwards
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        acc_number++;
    }

}


class ATM{

    void withdraw(Account acc){
        Scanner sc = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("WITHDRAW MONEY MODE\n");
        System.out.println("Enter amount in the multiples of 100 -");
        double amount = sc.nextDouble();

        if(amount%100 == 0.0){
            if(acc.balance >= amount){
                acc.balance -= amount;
                System.out.print("\nYour Transaction is Processing\n");
                try{
                    //Open txt file for appending data
                    String filename = acc.a_no + ".txt";
                    FileWriter fileWriter = new FileWriter(filename, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    // Write transaction details to text file
                    bufferedWriter.write("Date : " + new Date() + "\n");
                    bufferedWriter.write("Withdrawal : " + amount + "\n");
                    bufferedWriter.write("Account Number : " + acc.a_no + "\n");
                    bufferedWriter.write("Remaining Balance : " + acc.balance + "\n\n");
                    // Close the file writer and buffered writer
                    bufferedWriter.close();
                    fileWriter.close();
                }catch(IOException e){
                    System.out.println("An error occured while writing to the file. ");
                    e.printStackTrace();
                }
                System.out.println("THANK YOU FOR BANKING WITH US! ");
                try{
                    Thread.sleep(6000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("\033[H\033[2J");
                System.out.flush();
            }else{
                System.out.print("Insufficient Funds");
            }
        }else{
            System.err.println("Amount is not in multiples of 100!");
            System.err.println("Try Again!");
        }
    }


    void deposit_by_transfer(Account acc,double amount){

        acc.balance += amount;
        try{
            String fileName = acc.a_no + ".txt";
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Deposit : " + amount + "\n");
            bufferedWriter.write("Date : " + new Date() + "\n");
            bufferedWriter.write("Account Number : " + acc.a_no + "\n");
            bufferedWriter.write("Remaining Balance : " + acc.balance + "\n\n");
            bufferedWriter.close();
            fileWriter.close();
        }catch (IOException e){
            System.out.println("An error occured while writing to the file. ");
            e.printStackTrace();
        }
    }


    void deposit(Account acc){

        Scanner sc = new Scanner(System.in);
        System.out.println("\033[H\033[2J");        // what is this 033h
        System.out.flush();             // flush
        System.out.println("MONEY DEPOSIT MODE\n");
        System.out.println("Enter amount you want to Deposit? ");
        double amount = sc.nextDouble();
        acc.balance += amount ;
        System.out.print("\033[H\033[2J");
        System.out.flush();

        try{
            String fileName = acc.a_no + ".txt";
           // System.out.println("The File Name - " + fileName);
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Deposit : " + amount + "\n");
            bufferedWriter.write("Date : " + new Date() + "\n");
            bufferedWriter.write("Account Number : " + acc.a_no + "\n");
            bufferedWriter.write("Remaining Balance : " + acc.balance + "\n\n");
            bufferedWriter.close();
            fileWriter.close();
        }catch (IOException e){
            System.out.println("An error occured while writing to the file. ");
            e.printStackTrace();
        }
        System.out.println("Processing Your Request! Please Wait");
        try{
            Thread.sleep(4000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Transaction completed Successfully!");
        System.out.println("\n\nTHANK YOU FOR BANKING WITH US! ");
        System.out.println("---Going To Login Page---");
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }


    void Transfer(Account acc1, Account acc2, double amount){

        if(acc1.balance >= amount){
            acc1.balance -= amount;
            ATM a = new ATM();
            a.deposit_by_transfer(acc2,amount);
            try{
                String fileName = acc1.a_no + ".txt";
                FileWriter fileWriter = new FileWriter(fileName, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("Transfered : " + amount + "\n");
                bufferedWriter.write("Date : " + new Date() + "\n");
                bufferedWriter.write("Account Number : " + acc1.a_no + "\n");
                bufferedWriter.write("Remaining Balance : " + acc1.balance + "\n\n");
                bufferedWriter.close();
                fileWriter.close();
            }catch (IOException e){
                System.out.println("An error occured while writing to the file. ");
                e.printStackTrace();
            }
            try{
                Thread.sleep(4000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("\nAccount Transfer completed Successfully!\n---Going To Login Page---");
            try{
                Thread.sleep(3000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    void display_details(Account acc){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Displaying account Details\n");
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        String file_name = String.valueOf(acc.a_no) + ".txt";
        File file = new File(file_name);
        try{
            FileReader fileReader = new FileReader (file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while((line = bufferedReader.readLine())!=null){
                System.out.println(line);
            }
            bufferedReader.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found : " + e.getMessage());
        }catch(IOException e){
            System.out.println("Error reading file: " + e.getMessage());
        }
        System.out.println("Screen will automatically timeout after 30 seconds. . .");
        try{
            Thread.sleep(30000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    

    void quit(){
        System.out.println("THANK YOU FOR BANKING WITH US!!\n");
        return;
    }

}

class run_atm{

    int account_search_by_unique_id(Account[]ac,String unique_id){
        for(int i=0;i<5;i++){
            if(Objects.equals(unique_id,ac[i].unique_id)){
                return i;
            }
        }
        return -1;
    }

    int account_search_by_unique_id(Account[]ac,int account_number){
        for(int i=0;i<5;i++){
            if(Objects.equals(account_number,ac[i].a_no)){
                return i;
            }
        }
        return -1;
    }

    void demo(Account[]ac){
        Scanner sc = new Scanner(System.in);
        System.out.print("\n\n\nWELCOME TO THE ATM\n\n");
        System.out.println("\nEnter Your Card/Username");
        String unique_id = sc.nextLine();
        int i = account_search_by_unique_id(ac,unique_id);
        if(i == -1){
            System.out.println("Account not registered Yet!");
            try{
                Thread.sleep(3000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            return;
        }else{
            System.out.println("Enter Your PIN? ");
            int pin = sc.nextInt();
            if(pin == ac[i].pin){
                System.out.print("\n\nSelect the Options as Given Below!\n\nWithdraw----1\nDeposit----2\nAccount Transfer----3\nDisplay Account Details----4\nQuit----5\n\n");
                int ch;
                ATM atm = new ATM();
                ch=sc.nextInt();
                switch(ch){
                    case 1: atm.withdraw(ac[i]);break;
                    case 2: atm.deposit(ac[i]);break;
                    case 3: {
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("Enter the account number to transfer funds? ");
                        int account_transfer = sc.nextInt();
                        int j = account_search_by_unique_id(ac, account_transfer);
                        if(j == -1){
                            System.out.println("Account not yet registered!");
                            return;
                        }else{
                            System.out.println("Enter Amount for Transfering Funds?");
                            double amount = sc.nextDouble();
                            atm.Transfer(ac[i],ac[j],amount);
                        }
                        break;
                    }
                    case 4: atm.display_details(ac[i]);break;
                    case 5: atm.quit();break;
                }

            }else{
                System.out.println("Wrong PIN Entered!\n Try Again");
                try{
                    Thread.sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                return;
            }
        }
    }
}


public class Main{
    public static void main(String[]args){
        Scanner sc = new Scanner(System.in);
        Account[]ac = new Account[5];
        for(int i=0;i<5;i++){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("CREATING ACCOUNT MODE \n");
            ac[i]=new Account();
            ac[i].createAcc();
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        run_atm ob = new run_atm();
        for(; ;){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            ob.demo(ac);
        }
    }
    
}