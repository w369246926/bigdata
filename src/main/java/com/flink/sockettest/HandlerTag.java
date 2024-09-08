package com.flink.sockettest;

/**
 * socket transfer handler tag.
 * it represent the different business deal.
 * if you want to add the business deal you can add the handler tag.
 * @author Ding Peng
 */
public class HandlerTag {
	
	/**
	 * file transfer handler.
	 */
	public final static int FILE_HANDLE_TAG = 1;
	
	/**
	 * literal transfer handler.
	 */
	public final static int LITERAL_HANDLE_TAG = 2;
	
}
