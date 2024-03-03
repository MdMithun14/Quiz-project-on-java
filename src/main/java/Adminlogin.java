import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Adminlogin {
    private static final String filename = "./src/main/resources/users.json ";
    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("System:> Enter your username");
      String username=  scanner.nextLine();
        System.out.println("User:>" + username);
        System.out.println("System:> Enter your Password");
      String password =  scanner .nextLine();
        System.out.println("User:>" + password);

        JSONParser jsonParser = new JSONParser();
       JSONArray userarry = (JSONArray) jsonParser.parse(new FileReader(filename));

      JSONObject adminObject = (JSONObject) userarry.get(0);
        JSONObject Studentobject= (JSONObject) userarry.get(1);

        if (adminObject.get("username").equals(username) && adminObject.get("password").equals(password)){
            System.out.println("System:> Welcome admin! Please create new questions in the question bank.");
            CreateQuiz();
        } else if (Studentobject.get("username").equals(username) && Studentobject.get("password").equals(password)) {

            System.out.println("System:> Welcome salman to the quiz! We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' to start.\n");
            getStudentQuiz();
        }
        else {
            System.out.println("Invalid User. Please Enter Valid User!");
        }

    }
    private static void CreateQuiz() throws IOException, ParseException {
        while (true){
            System.out.println("System:> Input your question");
            Scanner scanner = new Scanner(System.in);
           String question= scanner.nextLine();

           String [] option = new String[4];
           for(int i=0;i<option.length;i++){
               System.out.println("System: Input option"+(i+1)+":");
             option[i]=  scanner.nextLine();
           }

            System.out.println("What is the answer key? ");
            scanner = new Scanner(System.in);
            int answer = scanner.nextInt();
            saveQuestion(question,option,answer);
            System.out.println("System:> Saved successfully! Do you want to add more questions? (press s for start and q for quit)");
            scanner = new Scanner(System.in);
          String pressQ =  scanner.nextLine();
          if(pressQ.equals("q")){
              break;
          }
        }
    }

    private static void saveQuestion(String question, String[] option, int answer) throws IOException, ParseException {
        JSONParser jsonParser=new JSONParser();
        JSONArray quizarry  = (JSONArray) jsonParser.parse(new FileReader(filename));

        JSONObject quizObject=new JSONObject();
        quizObject.put("question",question);
        quizObject.put("option",option[0]);
        quizObject.put("option",option[1]);
        quizObject.put("option",option[2]);
        quizObject.put("option",option[3]);

        quizObject.put("answer",answer);

        quizarry.add(quizObject);

        FileWriter fw = new FileWriter(filename);
        fw.write(quizarry.toJSONString());
        fw.close();
        fw.close();

    }
    private static void getStudentQuiz() throws IOException, ParseException {
       String fileName = "./src/main/resources/quiz.json";
        System.out.print("Student:> ");
        Scanner scanner = new Scanner(System.in);
        while (true){

          String press_S =  scanner.nextLine();
            System.out.println("Student:> ");
          if(press_S.equals("s")){
              break;
          }
        }
        int score = 0;
        int allquestion=0;
         JSONParser jsonParser = new JSONParser();
        JSONArray qusetionarry = (JSONArray) jsonParser.parse(new FileReader(fileName));

        for(int i=0; i<10 && i<qusetionarry.size();i++){
            int  randomquestion = (int) (Math.random() * qusetionarry.size());
            JSONObject questionObject  = (JSONObject) qusetionarry.get(randomquestion);
//            System.out.println("[Question " + (allquestion + 1) + "] " + questionObject.get("question"));

            System.out.println("[Question " + (allquestion + 1) + "] " + questionObject.get("question"));
            for (int j = 1; j <= 4; j++) {
                System.out.println(j + ". " + questionObject.get("option " + j));
            }

            System.out.print("Answer: ");
            int userAnswer;
            userAnswer = scanner.nextInt();

            int correctAnswer = ((Long) questionObject.get("answerKey")).intValue();
            if (userAnswer == correctAnswer) {
                score++;
            }

            allquestion++;
        }

        System.out.println("Quiz Completed!");
        System.out.println(" Your Score: " + score + " out of " + allquestion);

        if (score >= 8) {
            System.out.println(" Excellent! You have got " + score + " out of " + allquestion);
        } else if (score >= 5) {
            System.out.println("Good. You have got " + score + " out of " + allquestion);
        } else if (score >= 2) {
            System.out.println("Very poor! You have got " + score + " out of " + allquestion);
        } else {
            System.out.println("Very sorry, you are failed. You have got " + score + " out of " + allquestion);
        }


    }
    }

