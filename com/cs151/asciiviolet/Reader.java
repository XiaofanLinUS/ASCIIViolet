package com.cs151.asciiviolet;

/**
 * A reader that reads a command and do some modification on a graph
 * 
 * @author linxiaofan
 *
 */
public abstract class Reader
{
   /**
    * Read the input and execute it
    * 
    * @param input
    *           the given input
    */
   abstract void read(String command);

}
