package aditya.gate.results.gateOverflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GateOverflowMarks {
	
	String gatescore;
	String estimatedRank;
	String totalNoOfcandidatesChecked;
	String normalizedMarks;
	
	private void writeToFile(String filename) throws IOException
	{
		File f = new File(filename);
		FileWriter fw = new FileWriter(f,true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(this.totalNoOfcandidatesChecked+","+this.estimatedRank+","+this.gatescore+","+this.normalizedMarks+"\r\n");
		bw.close();
		fw.close();
		
		
	}
	
	private void runSelenium() throws InterruptedException, IOException
	{
		WebDriver driver =this.createDriver();
		driver.get("http://gateoverflow.in/mymarks/VisualizeMarks.php?marks=56.00&set=1");
		Thread.sleep(20*1000);
		WebElement 	element= driver.findElement(By.id("rank-normalized"));
		this.estimatedRank = element.getText();
		element = driver.findElement(By.id("normalized-marks"));
		this.normalizedMarks = element.getText();
		element = driver.findElement(By.id("score"));
		this.gatescore = element.getText();
		element = driver.findElement(By.id("all-total"));
		this.totalNoOfcandidatesChecked = element.getText().replace("(","");
		this.totalNoOfcandidatesChecked = this.totalNoOfcandidatesChecked.replace(")","");
		this.writeToFile("GateMarks.txt");
		if(driver!=null)
		{
			driver.quit();
		}
	}
	
	private WebDriver createDriver()
	{
		WebDriver driver = new FirefoxDriver();
		return driver;
	}
	
	public static void main(String args[]) throws InterruptedException, IOException
	{
		// This has to be run continuosly
		GateOverflowMarks marks = new GateOverflowMarks();
		while(true)
		{
		marks.runSelenium();
		Thread.sleep(10*60*1000);
		}
	}

}
