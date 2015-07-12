package com.manish;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce extends Reducer<Text, Text, Text, NullWritable>{

	
	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Reducer<Text, Text, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		Iterator x = values.iterator();
		Set<String> s = new HashSet<String>();
		for(Text t : values){
			String sample = t.toString();
			StringTokenizer toc = new StringTokenizer(sample, " ");
			while(toc.hasMoreTokens()){
				String res = toc.nextToken();
				s.add(res);
			}
		}
		String result = "";
		for(String res : s){
			result = result + res + " ";
		}
		Text m = new Text(result);
		context.write(m, null);
	}

	
}
