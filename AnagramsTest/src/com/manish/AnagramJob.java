package com.manish;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;



public class AnagramJob implements Tool{

	private Configuration conf;
	@Override
	public Configuration getConf() {
		return this.conf;
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;		
	}

	@Override
	public int run(String[] args) throws Exception {

		Job angramJob = new Job(getConf());
		angramJob.setJobName("Kelly Word Map");
		angramJob.setJarByClass(this.getClass());
		angramJob.setMapperClass(Map.class);
		angramJob.setReducerClass(Reduce.class);
		angramJob.setMapOutputKeyClass(Text.class);
		angramJob.setMapOutputValueClass(Text.class);
		angramJob.setOutputKeyClass(Text.class);
		angramJob.setNumReduceTasks(1);
//		wordCountJob.setPartitionerClass(WordCountPartitioner.class);
		//mapJob.setCombinerClass(WordCountCombiner.class);
		angramJob.setOutputValueClass(NullWritable.class);
		FileInputFormat.setInputPaths(angramJob, new Path(args[0]));
		FileOutputFormat.setOutputPath(angramJob, new Path(args[1]));
		return angramJob.waitForCompletion(true) == true ? 0 : -1;
	}
public static void main(String[] args) throws Exception {
	ToolRunner.run(new Configuration(), new AnagramJob(), args);
}



}
