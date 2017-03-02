# Recommendation Service

# Problem Description:
Recommendation Service is a service which will provide a list of questions to understand user's problem and in term to provide a recommendation. The service will start with a single question and based on the answers, it will provide with another questions. If the answers lead to a recommendation then we will provide the recommendation.

# Exercise:
For simplicity, we will build a class instead of a web service. Class methods will represent the different APIs in the service. Please also provide a brief example of how this class will be used, you may also want to provide a sample graph for your example.

# Files:
- questions.txt: questions to be inserted into the recommendation service
- answers.txt: answers to questions be inserted into the recommendation service
- RecommendationService.java

# Purpose of this exercise:
Data Modeling:
- How would you structure your node, what does the object looks like? 
> Design:
I modeled the recommendation service as a graph using a HashMap data structure where a key represents a question used in the service and a value represents the possible answers to a given question. 
Key: Question (node in a graph)
Value: Possible answers to a given question (edges between nodes in a graph)

- How would you define nodes relationship based on answers?
> In the answers.txt, one line represents an edge. 
Example: Given the question and next question, "What would you like to drink?" and "Would you like ice/and lemon?" it finds the first question and next question in the graph and creates an edge between those question using an answer.

Concerns:
- Is there a situation where there are two nodes with the same question but are supposed to be uniquely different in memory?

