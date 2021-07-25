import java.util.*;
import java.util.concurrent.TimeUnit;

public class game {
    static int points = 1000;
    static int roundCounter = 1;
    static Scanner console = new Scanner(System.in);
    static Random generator = new Random(); 
    static boolean play=true; 
    static boolean reverse = false; 
    static int difficulty = 7; 


    public static void startMenu() throws InterruptedException{
        System.out.println("Welcome to the Typing Game\nType \"easy\" or \"hard\" to change the difficulty\nType \"rules\" to read the rules otherwise");
        System.out.print("Press enter to start with \"normal\" difficulty: ");
        String input = console.nextLine();
        if(input.equals("easy")){
            difficulty = 4; 
            System.out.println("Playing easy mode, you only have to worry about 4 characters!");
        } else if(input.equals("hard")){
            difficulty = 10; 
            System.out.println("Playing hard mode, you need to worry about 10 characters!");
        } else if(input.equals("rules")){
            System.out.println("-----------------------RULES------------------------------");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("In the regular mode, you get a string of 7 random letters, you have to order them from start to finish");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("Capital letters are first then lower cased letters");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("For example, zZaA should become AZaz to get the max points");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("If you get the correct answer within 12 seconds, you get 500 points, get to 5000 to win!");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("If you get the answer wrong or take too long, you'll lose points. If you get to below 0 points, you lose.");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("In the easy mode, the string is 4 long. In hard mode, its 10 letters long!");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("In reversed mode, your objective is to write the string back in order but completely reversed!");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("For example, zZaA should become zaZA");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("Those are all the rules!");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("-----------------------RULES END------------------------------");
            TimeUnit.SECONDS.sleep(2);
            startMenu();
        }else{
            System.out.println("Playing normal mode, you need to worry about 7 characters!");
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.print("Type \"reverse\" to add in reversed mode as well or press enter to continue: ");
        input = console.nextLine();
        if(input.equals("reverse")){
            reverse=true;
            System.out.println("Playing reversed mode");
            TimeUnit.SECONDS.sleep(1);
        } 
        System.out.print("Press enter to start:");
        console.nextLine();
    }


    public static String generateRandomString(int difficulty){
        char[] charArr = new char[difficulty];
        for(int i = 0; i<charArr.length; i++){
            int randomCapitalized = generator.nextInt(26)+65; 
            //System.out.println(i+" randomCap: "+randomCapitalized);
            int randomLowerCase = generator.nextInt(26)+97;
            //System.out.println(i+" randomLower: "+randomLowerCase);
            int randomCharNum = generator.nextInt(2) == 0 ? randomCapitalized : randomLowerCase;
            char randomChar = (char)randomCharNum;
            charArr[i] = randomChar;
        }   
        //System.out.println(Arrays.toString(charArr));
        return String.valueOf(charArr);
    }

    public static char[] sort(char[] obj){
        int[] objArr = new int[obj.length];
        for(int i=0; i<obj.length; i++){
            objArr[i]=(int)obj[i];
        }
        Arrays.sort(objArr, 0, obj.length);

        //flips to descending order - maybe later for optional game feature 
        if(reverse){
            for(int i=0; i<objArr.length/2; i++){
                int temp = (int)objArr[i];
                objArr[i]=(int)objArr[obj.length-1-i];
                objArr[objArr.length-1-i] = temp;  
            }
        }

        char[] output = new char[objArr.length];
        for(int i=0; i<obj.length; i++){
            output[i]=(char)objArr[i];
        }
        System.out.println("Objective: " + Arrays.toString(output));
        return output;
    }

    public static int calculateScore(String obj, String input, long time){
        
       if(input==null)
            input = "";    

        //size diff 
        char[] objArr = sort(obj.toCharArray());
        char[] inputArr = input.toCharArray();
        int calcSize = inputArr.length>objArr.length ? inputArr.length : objArr.length; 
        int[] calcArr = new int[calcSize];

        //finding diff
        if(objArr.length>=inputArr.length){
            for(int i=0; i<calcArr.length; i++){
                if(i<inputArr.length)
                    calcArr[i]=Math.abs((int)objArr[i]-(int)inputArr[i]);
                else
                    calcArr[i]=(int)objArr[i];
            }
        }else{
            for(int i=0; i<calcArr.length; i++){
                if(i<objArr.length)
                    calcArr[i]=Math.abs((int)objArr[i]-(int)inputArr[i]);
                else
                    calcArr[i]=(int)inputArr[i];
            }
        }
        System.out.println("Numerical point difference: "+Arrays.toString(calcArr));

        int letterPointsDiff = 0;
        for (int i : calcArr) {
            letterPointsDiff+=i;
        }
        int output=0;
        System.out.println("Points diff: " + letterPointsDiff);
        if(time<=12000){
            if(letterPointsDiff==0)
                output = 500;
            else 
                output = -letterPointsDiff;
        } else
            output = ((12000-(int)time)-letterPointsDiff);
            

        System.out.println("Time: "+time+"ms\n"+ "Real Points diff: " + output);
        return output;
    }

    public static int score(String obj){
        long startTime = System.currentTimeMillis();
        
        System.out.print("You entered: ");
        String input = console.nextLine();

        long endTime = System.currentTimeMillis();
        long time = endTime-startTime;

        return calculateScore(obj, input,time);
    }

    
    public static void round(){
        String randomStr = generateRandomString(difficulty); 
        if(!reverse)
            System.out.println("Your current points is: "+points+", type in order [" + randomStr+"]");
        else    
            System.out.println("Your current points is: "+points+", type in reversed order [" + randomStr+"]");
        int pointDiff = score(randomStr);
        points+=pointDiff;
        System.out.println("Your points is now: "+points);
        System.out.print("Press enter to continue: ");
        console.nextLine();

        //System.out.println("Started at "+ startTime + ", Ended at " + endTime);
        //System.out.println("You took " + (endTime-startTime));

    }

    public static void finished(){
        System.out.println("--------------------Game Over-----------------------");
        if(points<=0){
            System.out.println("Sorry, you lost :(");
        } else{
            System.out.println("Congratulations, you beat the game!");
        }
        System.out.print("Write \"play\" to try again: ");
        String input = console.nextLine();
        if(input.equals("play")||input.equals("Play"))
            play = true;
        else
            play = false;
        reset();
    }

    public static void reset(){
        points = 1000;
        roundCounter = 0;
        reverse = false; 
    }

    public static void run(){
        while(play){
            try {
                startMenu();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            while(points>0&&points<5000){
                System.out.println("---------------------Round "+roundCounter+"-------------------------");
                round();
                roundCounter++;
            }
            finished();
            System.out.println("-----------------------------------------------------");
        }
    }


   public static void main(String[] args) {
       run();
   }
}

// Start
/*
 * Game - gameRun - - Score Checker - time - compare tme to when submitted &
 * calculate score
 */

// Game Over