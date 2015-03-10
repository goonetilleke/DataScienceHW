package hw1;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/*
*	1b. [3 points] Which brand is the mode producer in this data?
*	1c. [3 points] Of the reviews whose reviewers used their real names, what percentage had a
*	verified purchase?
*	1d. [3 points] Of the reviews that had a verified purchase, what percentage had a reviewer who
*	used his/her real name?
*	1e. [4 points] For release_to_review_time, what are its minimum, Q1, median, Q3,
*	maximum, and interquartile range values? What is the appropriate plot to show these numbers?
*	1f. [6 points] Plot the histogram for length_of_review_text. Use bins of size 200.
*	1g. [6 points] Is the distribution of length_of_review_text skewed? Are their outliers
*	in the histogram for length_of_review_text? If so, give the data vectors associated with
*	the top 3 outliers.
*	1h. [4 points] Compute Pearson Correlation between length_of_review_text and
*	helpfulness. Briefly explain the association between these two variables based on the
*	correlation value you computed.
*	1i. [3 points] Plot the scatter plot for length_of_review_text and helpfulness.
*/
public class Purchases {
	/* Line format:
	 * {producer, release_to_review, used_real_name, verified_purchase, rating, helpfulness,#_of_votes,length_of_review_text}*/
	//keep hashtable of every producer name (purchase[0]), and its count
	static Hashtable<String, Integer> producerCount = new Hashtable<String, Integer>();
	static int maxProducerCount=0;
	static String maxProducer;
	float verifiedPurchaseCount=0; //purchase[3]
	float usedRealNameANDVerifiedCount=0; //purchase[2] and purchase[3]
	float usedRealNameCount=0; //purchase[2]
	int minReleaseToReviewTime=0;
	int maxReleaseToReviewTime=0;
	//keep array of all values for releasetoreview (purchase[1])
	int[] releaseToReview;
	//keep array of all the values of the lengths of reviews (purchase[7])
	int[] lengthOfReviewText;
	//store arraylist of integer pairs (length of review text, helpfulness) 
	ArrayList<float[]> lengthOfReviewText_helpfulness= new ArrayList <float[]>(); //(purchase[7] and purchase[5])
	
	public static void main(String[] args) {
		 
		Purchases obj = new Purchases();
		ArrayList<String[]> purchaseList=new ArrayList<String[]>();
		purchaseList=obj.run();
		obj.iterateListForValues(purchaseList);
		System.out.println(modeBrand(producerCount));
		System.out.println(obj.verifiedPurchasePercent());
		System.out.println(obj.realNamePercent());
		obj.getReleaseToReview();
		obj.getLengthOfReviewText();
		obj.getLengthOfReviewTextHelpfulness();
	  }
	public void iterateListForValues(ArrayList<String[]> list){
	int count=1;
	int listSize=list.size();
	releaseToReview=new int[list.size()];
	lengthOfReviewText=new int[list.size()];
		while (listSize > count) {
			//producer is in hash table, update count by one
			String[] line = list.get(count);
			String key=line[0];
			if (producerCount.containsKey(key)){
				producerCount.put(key, producerCount.get(key)+1);
			}
			//put new producer into hash table, and initialize its count to 1
			else{
				producerCount.put(key, 1);
			}
			//System.out.println(line[3]);
			if (Integer.parseInt(line[3])==1){
				verifiedPurchaseCount++;
			}
			if (Integer.parseInt(line[2])==1){
				usedRealNameCount++;
			}
			if ((Integer.parseInt(line[3])==1)&&(Integer.parseInt(line[2])==1)){
				usedRealNameANDVerifiedCount++;
			}
			//System.out.println(releaseToReview[count]);
			releaseToReview[count]=Integer.parseInt(line[1]);
			lengthOfReviewText[count]=Integer.parseInt(line[7]);
			//System.out.println((line[7]));
			//System.out.println((line[5]));
			float[] review_helpfulness={Float.parseFloat(line[7]),Float.parseFloat(line[5])};
			lengthOfReviewText_helpfulness.add(review_helpfulness);		
			
		count++;
		}
	}
	//Question 1b)
	public static String modeBrand(Hashtable<String, Integer> producerCount){

				Enumeration<String> enumKey = producerCount.keys();
				while(enumKey.hasMoreElements()) {
				    String key = enumKey.nextElement();
				    int val = producerCount.get(key);

				    if(val>maxProducerCount){
				    	maxProducerCount=val;
				    	maxProducer=key;
				    }
				}
		return maxProducer;
	}
	//Question 1c)
	public float verifiedPurchasePercent(){
		float percentage= usedRealNameANDVerifiedCount/usedRealNameCount;
		return percentage;
	}
	//Question 1d)
	public float realNamePercent(){
		float percentage= usedRealNameANDVerifiedCount/verifiedPurchaseCount;
		return percentage;
	}
	//Question 1e)
	public int[] release_to_review_stats(){
		//minimum, Q1, median, Q3,maximum, and interquartile range values
		int stats[] = new int[6]; 
		return null;
	}
	public void getLengthOfReviewText(){
		PrintWriter writer;
		try {
			writer = new PrintWriter("LengthOfReview.txt", "UTF-8");
			for (int i=1; i<lengthOfReviewText.length; i++){
				writer.println(lengthOfReviewText[i]);
			}
			writer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}
	public void getReleaseToReview(){
		PrintWriter writer;
		try {
			writer = new PrintWriter("ReleaseToReview.txt", "UTF-8");
			for (int i=1; i<releaseToReview.length; i++){
				writer.print(releaseToReview[i]+" ");
			}
			writer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}
	public void getLengthOfReviewTextHelpfulness(){
		PrintWriter writer;
		try {
			writer = new PrintWriter("LengthOfReviewText_Helpfulness.txt", "UTF-8");
			for (int i=1; i<lengthOfReviewText_helpfulness.size(); i++){
				writer.print(lengthOfReviewText_helpfulness.get(i)[0]);
				writer.print(",");
				writer.println(lengthOfReviewText_helpfulness.get(i)[1]);
			}
			writer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}

	 
	  public ArrayList<String[]> run() {
	 
		String csvFile = "/Users/School/Downloads/laptops.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<String[]> purchaseList=new ArrayList<String[]>();

		try {
	 
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] purchaseLine = line.split(cvsSplitBy);
				/*
				 * Format: {producer, relase_to_review, used_real_name, verified_purchase, rating, helpfulness, #_of_votes, length_of_review_text}
				 */
				purchaseList.add(purchaseLine);
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return purchaseList;
	 
	  }


}
