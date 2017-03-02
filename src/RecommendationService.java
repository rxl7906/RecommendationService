import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Neighbor represents a potential answer to a given question
 * and provides the next question to ask the user
 */
class Neighbor {
	String answer;
	String nextQuestion; // identifier to vertex it points to
	public Neighbor(String answer, String nextQuestion){
		this.answer=answer;
		this.nextQuestion=nextQuestion;
	}
}
/*
 * Graph holds key value pairs where:
 * Key: Question (node in graph)
 * Value: Potential answers to a given question (edges between nodes in a graph)
 */
class Graph {
	String firstQuestion;
	Map<String, List<Neighbor>> adjList; 
	public Graph(){
		this.adjList = new HashMap<String, List<Neighbor>>();
	}
	// add question (adding a vertex)
	public void addQuestion(String question){
		adjList.put(question, new ArrayList<Neighbor>());
	}
	// add answer for a question (adding an edge)
	public void addAnswer(String question, String answer, String nextQuestion) throws QuestionNotFoundException {
		if(adjList.containsKey(question)){ // find question in graph
			adjList.get(question).add(new Neighbor(answer, nextQuestion)); // add it to its neighbors
		} else {
			throw new QuestionNotFoundException("Question does not exist in the service.");
		}
	}
	public void setFirstQuestion(String firstQuestion){
		this.firstQuestion=firstQuestion;
	}
}

/*
 * Recommendation Service provides a list of questions to 
 * understand a user's problem and in term provide a recommendation
 * 
 * @author Robin Li
 */
public class RecommendationService {

	/* 
	 * parseInputFile - takes in filename and does file i/o to
	 * read in input text file.
	 * @param graph Graph of the recommendation service
	 * @param fileName String of the filename being read in
	 */
	public static void parseInputFile(Graph graph, String fileName){
		String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // check if its questions or answers
            if(fileName.equals("questions.txt")){
            	 while((line = bufferedReader.readLine()) != null) {
            		 if(line.startsWith("first question:")){
            			 graph.setFirstQuestion(line.split("[:]+")[1]);
            			 graph.addQuestion(line.split("[:]+")[1]);
            		 } else if(!line.startsWith("#")){ // ignore line with #
            			 graph.addQuestion(line);
            		 }
                 }   
            }
            if(fileName.equals("answers.txt")){
            	while((line = bufferedReader.readLine()) != null) {
                    if(!line.startsWith("#")){ // ignore line with #
                    	String[] answer = line.split("[|]+");
                    	try {
							graph.addAnswer(answer[0], answer[1], answer[2]);
						} catch (QuestionNotFoundException e) {
							e.printStackTrace();
						}
                    }
                }   
            }
            bufferedReader.close();         
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
	}
	
	/*
	 * Function to run recommendation service 
	 * In progress: work on providing a coherent recommendation
	 * 
	 * @param graph Graph of the recommendation service
	 */
	public static void runRecommendationService(Graph graph){
		// ask the user the first question
		String currQuestion = graph.firstQuestion;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(!currQuestion.equals("Output Recommendation")){
			// retrieve question from graph and print the possible answers to the question
			System.out.println(currQuestion);
			for(Neighbor neighbor : graph.adjList.get(currQuestion)){
				System.out.print(neighbor.answer + " ");
			}
			String userInput = null;
	        try {
				userInput = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        // save user input
	        sb.append(userInput + " ");
			// given user input match it against a neighbor
	        for(Neighbor neighbor : graph.adjList.get(currQuestion)){
				if(neighbor.answer.equals(userInput)){
					// set the new question
					currQuestion = neighbor.nextQuestion;
				}
			}
		}
		System.out.println(sb.toString());
	}
	
	public static void main(String args[]){
		Graph graph = new Graph();
		// add questions and answers
		parseInputFile(graph, "questions.txt");
		parseInputFile(graph, "answers.txt");
		
		runRecommendationService(graph);
	}
}

class QuestionNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	public QuestionNotFoundException(String message) {
		super(message);
	}
}