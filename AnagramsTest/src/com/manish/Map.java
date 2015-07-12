package com.manish;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map extends Mapper<LongWritable, Text, Text, Text>{

	ArrayList<String> l = new ArrayList<String>();
	HashMap<String, ArrayList<String>> m = new HashMap<String, ArrayList<String>>();
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {

	String s = value.toString();
	char[] ch = new char[s.length()];
	for(int i = 0;i<s.length();i++){
		ch[i] = s.charAt(i);
	}
	Arrays.sort(ch);
	ArrayList<String> list = new ArrayList<String>();
	String sorted = new String(ch);
	if(!(l.contains(sorted))){
		l.add(sorted);
		list.add(s);
		m.put(sorted, list);
	}
	else{
		list = m.get(sorted);
		if(!(list.contains(s))){
			list.add(s);
			m.put(sorted, list);
		}
	}
	
	}
	
	
	@Override
	protected void cleanup(
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		for (Entry<String, ArrayList<String>> entry : m.entrySet())
		{
		    //System.out.println(entry.getKey() + "/" + entry.getValue());
			ArrayList<String> list = new ArrayList<String>();
			list = entry.getValue();
			String res = "";
			for(String s : list){
				res = res + s + " ";
			}
			Text key = new Text(entry.getKey());
			Text value = new Text(res);
			context.write(key, value);
		}
		
	}
}
