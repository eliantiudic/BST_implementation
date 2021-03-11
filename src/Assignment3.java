//Elian Tiudic
//TY2 CISC 3130
import java.io.*;
import java.util.*;
public class Assignment3 {

	static class Movie { //class that will serve as a node
		  private String title;
		  private int releaseYear;
		  private Movie left;
		  private Movie right;
		  
		  //constructor
		  public Movie(String title,int releaseYear) {
			  this.title=title;
			  this.releaseYear=releaseYear;
		  }
		  
		  //used for printing
		  public String toString(){
			  return title+", "+releaseYear;
	      }
		  
		  //set methods
		  public void setTitle(String title) {
			  this.title=title;
		  }
		  
		  public void setReleaseYear(int releaseYear) {
			  this.releaseYear=releaseYear;
		  }
		  
		}
	
	static class MovieBST {
		  private Movie root;
		  
		  
		  /*recursive method used to print the movie titles in a given alphabetical range
		   *the method basically works similar to an inOrder traversal,
		   *but it only print the value if it's in the range given
		   */
		  public void subSet(Movie movie, String begin, String end,PrintWriter output) { 
	        
		        if (movie == null) { 
		            return; 
		        } 
		        
		        //if the title is larger than the beginning value we go down the left path
		        if (begin.compareTo(movie.title)<0) { 
		            subSet(movie.left, begin, end,output); 
		        } 
		  
		        //if the movie title lies in range the method prints it
		        if (begin.compareTo(movie.title) <= 0 && end.compareTo(movie.title) >=0) { 
		            output.println(movie); 
		        } 
		        
		        //if the title is smaller that the end we go down the right path
		        if (end.compareTo(movie.title) > 0) { 
		            subSet(movie.right, begin, end,output);
		        }
		        
		  } 
		  
		// recursive insert method inspired from the lecture slides
		  public void insert(String title,int releaseYear) {
			  root=insert(root, title, releaseYear);
		  }
		  
		  /*this method will recursively go down a tree, comparing the title 
		   *of the movie with the existing nodes and going in the correct
		   *direction until it hits a null node, in which case it assigns it the movie
		   */
		  public Movie insert(Movie m, String title, int releaseYear) {
			  //if the method gets to a null node, a movie is assigned to it
			  if(m==null)
				  return new Movie(title,releaseYear);
			  
			  //comparing the title (key) with that of the movie to be inserted
			  int compare=title.compareTo(m.title);
			  
			  //if the title is lower alphabetically, recursively go down left the tree
			  if(compare<0)
				  m.left=insert(m.left,title,releaseYear);
			  //if the title is higher alphabetically, recursively go down the left tree
			  else if(compare>0)
				  m.right=insert(m.right,title,releaseYear);
			  else
				  m.releaseYear=releaseYear;
			  
			  return m;
		  }
		  
		  //this method finds the movie based on a given title
		  //or it returns null if it doesn't find one
		  public Movie find(String title) {
			      Movie current = root;          
			      while(!current.title.equals(title))     
			         {
			         if(title.compareTo(current.title)<0)         
			            current = current.left;
			         else                            
			            current = current.right;
			         if(current == null)             
			            return null;               
			         }
			      return current;                  
			      }  
		  
		}
	
	public static void main(String[] args) throws IOException {
		
		//creating an input file
		File input=new File("input.txt");
		Scanner scan=new Scanner(input);
		
		//creating an output file
		File output=new File("output.txt");
		PrintWriter out=new PrintWriter(output);
		
		//creating the movielist
		MovieBST movieList=new MovieBST();
		String title=null;
		int releaseYear=0;
		
		//parsing the movie titles
		while(scan.hasNextLine()) {
			String s = scan.nextLine();
			//splitting the line using the comma as a separator
			String[] tokens=s.split("[,]");
			//if the title doesn't begin with " it is added to the tree
			//this takes care of the cases where there are multiple commas in the title
			if(tokens[1].charAt(0)!='"') {
				title = tokens[1].substring(0, tokens[1].length()-7);
				releaseYear=Integer.parseInt(tokens[2]+
						tokens[1].substring(tokens[1].length()-5, tokens[1].length()-1));
				movieList.insert(title, releaseYear);
			}
		}
		
		//printing a few examples of the subSet function being used
		movieList.subSet(movieList.root,"Bliss","Dead Man",out);
		out.println();
		out.println("----------------------------------------");
		out.println();
		
		movieList.subSet(movieList.root, "Taxi Driver","Twister", out);
		out.println();
		out.println("----------------------------------------");
		out.println();
		
		movieList.subSet(movieList.root, "Poetic Justice","Rudy", out);
		scan.close();
		out.close();
	}

}
