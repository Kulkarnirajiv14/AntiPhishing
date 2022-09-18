package com.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.constant.ServerConstants;
	 
 public class Test {
		
	public static void main(String[] args) {
		CountClassSelector();
		CountIdSelector();
	}
    public static void CountIdSelector()
    {	
    	char character ='#';  
    	int count=0;
    	try {
    		File file=new File(ServerConstants.PARSING_FILE+"scratchpads.eu.css.parse.txt");  
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) !=null) {
                 for(int i=0; i<line.length();i++){
                    if(line.charAt(i) == character){
                        count++;
                    }
                    
                }
            }
        } catch (FileNotFoundException e) {
            // File not found
        } catch (IOException e) {
            // Couldn't read the file
        }
    	System.out.println("Id Selector "+count);
    }
    public static void CountClassSelector()
    {
    	char character ='.';  
    	int count=0;
    	int idCount=0;
    	char test=':';
    	try {
    		File file=new File(ServerConstants.PARSING_FILE+"scratchpads.eu.css.parse.txt");  
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) !=null) {
                 for(int i=0; i<line.length();i++){
                    if(line.charAt(i) == character){
                         count++;
                      //int j=i;      
                  }  
              }
            }
        } catch (FileNotFoundException e) {
            // File not found
        } catch (IOException e) {
            // Couldn't read the file
        }
    	System.out.println("Class selector "+count);
    	//System.out.println("count "+idCount);
    }
 
 }   

 