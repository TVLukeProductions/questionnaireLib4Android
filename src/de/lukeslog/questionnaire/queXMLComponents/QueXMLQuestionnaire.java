/*
 * Copyright (C) Lukas Ruge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.lukeslog.questionnaire.queXMLComponents;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import de.lukeslog.questionnaire.questionnaireComponents.*;



/**
 * QueXML is a XML standard that can be used to store questionnaires. This class parses the document given a valid document ressource
 * It creates questions and documents according to the specification of QueXML
 * 
 * @author lukas ruge
 *
 */
public class QueXMLQuestionnaire extends Questionnaire
{

	/**
	 * constructor, strting the parsing proces of the root and sending bits to other methods
	 * 
	 * @param ressource a valid url to a QueXML file
	 */
	public QueXMLQuestionnaire(final String ressource)
	{
		final SAXBuilder builder = new SAXBuilder();
		//loading a document from the web can not be done in the UI Thread
		new Thread(new Runnable() 
    	{
    	    public void run() 
    	    {
			    // command line should offer URIs or file names
			    try 
			    {
			    	Document doc = builder.build(ressource);
			    	Element root = doc.getRootElement();
			    	createQuestionnaire(root);
			      // If there are no well-formedness errors, 
			      // then no exception is thrown
			      System.out.println(ressource + " is well-formed.");
			    }
			    // indicates a well-formedness error
			    catch (JDOMException e) 
			    { 
			      System.out.println(ressource + " is not well-formed.");
			      System.out.println(e.getMessage());
			      e.printStackTrace();
			    }  
			    catch (IOException e) 
			    { 
			      System.out.println("Could not check " + ressource);
			      System.out.println(" because " + e.getMessage());
			      e.printStackTrace();
			      //TODO: show some kind of error here...
			    } 
    	    }
    	}).start();

	}

	/**
	 * Given the root element of a QueXML file, this starts making a QueXMLQuestionnaire Object
	 * 
	 * @param root
	 */
	private void createQuestionnaire(Element root) 
	{
		List children = root.getChildren();
		Iterator childrenIterator = children.iterator();
		while(childrenIterator.hasNext())
		{
			Element child = (Element) childrenIterator.next(); 
			String name= child.getName();
			//found the title element
			if(name.equals("title"))
			{
				setTitle(child.getText());
			}
			//subtitle
			if(name.equals("subtitle"))
			{
				setSubTitle(child.getText());
			}
			//investigaor
			if(name.equals("investigator"))
			{
				setInvestigator(createPerson(child));
			}
			if(name.equals("dataCollector"))
			{
				setDataCollector(createPerson(child));
			}
			if(name.equals("questionnaireInfo"))
			{
				setquestionnaireinfo(child);
			}
			if(name.equals("section"))
			{
				section(child);
			}
			if(name.equals("question"))
			{
				question("", child);
			}
		}
		
	}

	/**
	 * Sections in QueXML are areas of similar questions, they do not have much of a function and except for the section title they are mostly ignored.
	 * 
	 * @param element
	 */
	private void section(Element element) 
	{
		System.out.println("New Section");
		String sectiontext="";
		List children = element.getChildren();
		Iterator childrenIterator = children.iterator();
		//first on has to find the title
		while(childrenIterator.hasNext())
		{
			Element child = (Element) childrenIterator.next(); 
			String name= child.getName();
			if(name.equals("sectionInfo"))
			{
				Element gc2 = child.getChild("text");
				sectiontext=gc2.getText();
			}
		}
		//now generate all the questions using that title
		Iterator childrenIterator2 = children.iterator();
		while(childrenIterator2.hasNext())
		{
			Element child = (Element) childrenIterator2.next(); 
			String name= child.getName();
			if(name.equals("question"))
			{
				addQuestion(question(sectiontext, child));
			}
		}
	}

	/**
	 *creates a Question Object 
	 * 
	 * This may be called from the section() method, the question() method or the anwer-method, since questions appear either in sections, as subquestions in matrixquestions or as contingientquestions in answers
	 * 
	 * @param sectiontext
	 * @param element
	 * @return the generated Question
	 */
	private Question question(String sectiontext, Element element)
	{
		System.out.println("New Question");
		String questiontext="";
		String questionID="";
		String questionType="";
		boolean randomize=false;
		List children = element.getChildren();
		Iterator childrenIterator = children.iterator();
		ArrayList<Question> subquestions = new ArrayList<Question>();
		ArrayList<Answer> answers = new ArrayList<Answer>();
		//the varName is sually an attribute not a child
		Attribute atr = element.getAttribute("varName");
		if(atr!=null)
		{
			questionID = atr.getValue();
		}
		while(childrenIterator.hasNext())
		{
			Element child = (Element) childrenIterator.next(); 
			String name= child.getName();
			//the actual question
			if(name.equals("text"))
			{
				questiontext=child.getText();
			}
			if(name.equals("ID"))
			{
				questionID=child.getText();
			}
			if(name.equals("randomize"))
			{
				String temp=child.getText();
				if(temp.equals("true"))
				{
					randomize=true;
				}
			}
			if(name.equals("qualifier"))
			{
				//TODO: Implement qualifiers
			}
			if(name.equals("specifier"))
			{
				//TODO. Implement specifiers
			}
			if(name.equals("directive"))
			{
				//TODOD: Implement directives
			}
			//matrix questions can have subquestions.
			if(name.equals("subQuestion"))
			{
				questionType="Matrix";
				//subquestions are not as complex but they are aswell handled by this method, the are alwas anduerstood as checkBox Questions
				Question q = question(sectiontext, child);
				subquestions.add(q);
				
			}
			if(name.equals("vas"))
			{
				questionType="Visual Analog Scale";
			}
			if(name.equals("davas"))
			{
				questionType="Discrete Visual Analog Scale";
			}
			if(name.equals("response"))
			{
				System.out.println("resonse");
				//if there is one fixed category its a radio question
				//if there are several responses, it may be a either several free responses or a checkBox question
				//there is also vas and dvas responses... this is tricky...
				Answer answer = answer(child);
				answers.add(answer);
			}
		}
		System.out.println("End of the question section:"+questiontext);
		
		QueXMLQuestion question = new QueXMLQuestion(sectiontext, questionID, questiontext, answers, randomize);
		question.addSubQuestions(subquestions);
		return question;
	}
	
	/**
	 * Creating the answers for a question
	 * 
	 * The Answer is what actually divides questions, since there are diferent kinds of answers like free, checkbox, radio, matrix...
	 * 
	 * @param element
	 * @return the answer that is generated
	 */
	private Answer answer(Element element) 
	{
		System.out.println("Answer:");
		String variable="";
		List children = element.getChildren();
		Iterator childrenIterator = children.iterator();
		Answer answer = null;
		//the varName (which is impoortand as it is later mached to the valuie for vealuation of the questionnaire) is often an attribute, not a child
		Attribute atr = element.getAttribute("varName");
		variable = atr.getValue();
		//first get the varName for sure
		while(childrenIterator.hasNext())
		{
			Element child = (Element) childrenIterator.next(); 
			String name= child.getName();
			if(name.equals("varName"))
			{
				variable=child.getText();
			}
		}
		//Now get all the other info from the question
		Iterator childrenIterator2 = children.iterator();
		while(childrenIterator2.hasNext())
		{
			Element child = (Element) childrenIterator2.next(); 
			String name= child.getName();
			//based on the name we know what kind of question it is.
			if(name.equals("free"))
			{
				//System.out.println("Freitext");
				List grandchildren = child.getChildren();
				Iterator grandChildrenIterator = grandchildren.iterator();
				String format="";
				String length="";
				String label="";
				String max="";
				String min="";
				while(grandChildrenIterator.hasNext())
				{
					Element grandchild = (Element) grandChildrenIterator.next();
					String name2=grandchild.getName();
					
					if(name2.equals("format"))
					{
						format= grandchild.getText();
					}
					if(name2.equals("length"))
					{
						length=grandchild.getText();
					}
					if(name2.equals("label"))
					{
						label=grandchild.getText();
					}
					if(name2.equals("min"))
					{
						min=grandchild.getText();
					}
					if(name2.equals("max"))
					{
						max=grandchild.getText();
					}
				}
				QueXMLFreeAnswer a = new QueXMLFreeAnswer(variable, "", format, length, label);
				a.setMinValue(min);
				a.setMaxValue(max);
				answer=a;
			}
			//fixed answers may be in one category or in several distinguishing between checkbox and radio questions
			//for this, a radioquestion will have several checkBoxQuestions in it, which are the options
			if(name.equals("fixed"))
			{
				//if there is only one category per response this indiacates that it is a checkbox answer
				//otherwise it might be a radio answer... (Or is it the other way around... I get confused)x... actually seems like neither is true.
				int ccount = countCategories(child);
				if(ccount==1)
				{
					System.out.println("Checkbox Answer");
					Element bla=child.getChild("category");
					List grandchildren = bla.getChildren(); 
					Iterator grandChildrenIterator = grandchildren.iterator();
					String label="";
					String value="";
					String image="";
					String skipTo="";
					while(grandChildrenIterator.hasNext())
					{
						Element grandchild = (Element) grandChildrenIterator.next();
						String name2=grandchild.getName();
						if(name2.equals("label"))
						{
							label=grandchild.getText();
							System.out.println(label);
						}
						if(name2.equals("value"))
						{
							value=grandchild.getText();
						}
						if(name2.equals("image"))
						{
							value=grandchild.getText();
						}
						if(name2.equals("skipTo"))
						{
							value=grandchild.getText();
						}
						if(name2.equals("contigentQuestions"))
						{
							//TODO: implement contingent question
							answer.addContigentQuestion(question("", grandchild));
						}
					}
					QueXMLCheckBoxAnswer cba = new QueXMLCheckBoxAnswer(variable, label, value, image, skipTo);
					answer=cba;
				}
				if(ccount>1)
				{
					List grandchildren=child.getChildren("category");
					Iterator grandChildrenIterator = grandchildren.iterator();
					QueXMLRadioAnswer a2 = new QueXMLRadioAnswer(variable);
					while(grandChildrenIterator.hasNext())
					{
						Element grandchild = (Element) grandChildrenIterator.next();
						List ggc = grandchild.getChildren();
						Iterator ggcIterator = ggc.iterator();
						String label="";
						String value="";
						String image="";
						String skipTo="";
						while(ggcIterator.hasNext())
						{
							Element x = (Element) ggcIterator.next();
							String name2=x.getName();
							if(name2.equals("label"))
							{
								label=x.getText();
								System.out.println("RadioLabel "+label);
							}
							if(name2.equals("value"))
							{
								value=x.getText();
							}
							if(name2.equals("image"))
							{
								value=x.getText();
							}
							if(name2.equals("skipTo"))
							{
								value=x.getText();
							}
							if(name2.equals("contigentQuestions"))
							{
								answer.addContigentQuestion(question("", x));
							}
						}
						System.out.println("RadioLabel2 "+label);
						QueXMLCheckBoxAnswer cba = new QueXMLCheckBoxAnswer(variable, label, value, image, skipTo);
						a2.addAnswer(cba);
					}
					answer=a2;
				}
			}
			if(name.equals("vas"))
			{
				//TODO: implement
				List grandchildren = child.getChildren();
				Iterator grandChildrenIterator = grandchildren.iterator();
				String leftSide="";
				String rightSide="";
				while(grandChildrenIterator.hasNext())
				{
					Element grandchild = (Element) grandChildrenIterator.next();
					String name2=grandchild.getName();
					if(name2.equals("labelleft"))
					{
						leftSide=grandchild.getText();
					}
					if(name2.equals("labelright"))
					{
						rightSide=grandchild.getText();
					}
				}
				QueXMLAnalogScaleAnswer asa = new QueXMLAnalogScaleAnswer(variable, leftSide, rightSide);
				answer=asa;
			}
			if(name.equals("dvas"))
			{
				//TODO: implement 
				List grandchildren = child.getChildren();
				Iterator grandChildrenIterator = grandchildren.iterator();
				while(grandChildrenIterator.hasNext())
				{
					Element grandchild = (Element) grandChildrenIterator.next();
					String name2=grandchild.getName();
				}
			}
			//it might be a contingient question or a subquestion, that is not 
			// formated like normal questions..., that's what this part handles.
			//in QueXML A subquestion or contingientQuestion can not have the same complexity
			if(name.equals("text"))
			{
				String text = child.getText();
				//Element c2 = element.getChild("length");
				//String length = c2.getText();
				QueXMLFreeAnswer a = new QueXMLFreeAnswer(variable, text, "", "", "");
			}
			
		}
		return answer;
	}

	/**
	 * helper methods that counts the subelements named categroy
	 * 
	 * @param element
	 * @return
	 */
	private int countCategories(Element element) 
	{
		List children = element.getChildren();
		Iterator childrenIterator = children.iterator();
		int ccount =0;
		while(childrenIterator.hasNext())
		{
			Element child = (Element) childrenIterator.next(); 
			String name= child.getName();
			if(name.equals("category"))
			{
				ccount++;
			}
		}
		return ccount;
	}

	/**
	 * helper method that extracts the info
	 * 
	 * @param element
	 */
	private void setquestionnaireinfo(Element element) 
	{
		String position = "";
		List children = element.getChildren();
		Iterator childrenIterator = children.iterator();
		while(childrenIterator.hasNext())
		{
			Element child = (Element) childrenIterator.next(); 
			String name= child.getName();
			if(name.equals("position"))
			{
				position=child.getText();
			}
		}
		Iterator childrenIterator2 = children.iterator();
		while(childrenIterator2.hasNext())
		{
			Element child = (Element) childrenIterator2.next(); 
			String name= child.getName();
			if(name.equals("text"))
			{
				if(position.equals("before"))
				{
					addToBeforeText(child.getText());
				}
			}
		}

	}

	/**
	 * This method creates a person element
	 * 
	 * @param element
	 * @return
	 */
	private QueXMLPerson createPerson(Element element) 
	{
		List children = element.getChildren();
		String firstName="";
		String lastName="";
		String salutation="";
		String organisation="";
		String phoneNumber="";
		String faxNumber="";
		String emailAddress="";
		String website="";
		QueXMLAddress address = null;
		Iterator childrenIterator = children.iterator();
		while(childrenIterator.hasNext())
		{
			Element child = (Element) childrenIterator.next(); 
			String name= child.getName();
			if(name.equals("name"))
			{
				List grandchildren = child.getChildren();
				Iterator grandchildrenIterator = grandchildren.iterator();
				while(grandchildrenIterator.hasNext())
				{
					Element grandchild =(Element)grandchildrenIterator.next();
					String gname= grandchild.getName();
					if(gname.equals("salutation"))
					{
						salutation = grandchild.getText();
					}
					if(gname.equals("firstName"))
					{
						firstName = grandchild.getText();
					}
					if(gname.equals("lastName"))
					{
						lastName = grandchild.getText();
					}
				}
			}
			if(name.equals("organisation"))
			{
				organisation = child.getText();
			}
			if(name.equals("phoneNumber"))
			{
				phoneNumber=child.getText();
			}
			if(name.equals("faxNumber"))
			{
				faxNumber=child.getText();
			}
			if(name.equals("emailAddress"))
			{
				emailAddress=child.getText();
			}
			if(name.equals("address"))
			{
				address = createAddress(child);
			}
			if(name.equals("website"))
			{
				website = child.getText();
			}
		}
		QueXMLPerson p = new QueXMLPerson(firstName, lastName);
		p.setSalutation(salutation);
		p.setOrganisation(organisation);
		p.setPhoneNumber(phoneNumber);
		p.setFaxNumber(faxNumber);
		p.setAddress(address);
		p.setEmailadress(emailAddress);
		p.setWebsite(website);
		return p;
	}

	/**
	 * This method creates the address
	 * 
	 * @param element
	 * @return
	 */
	private QueXMLAddress createAddress(Element element) 
	{
		List children = element.getChildren();
		String street="";
		String city="";
		String suburb="";
		String postalcode="";
		String country="";
		Iterator childrenIterator = children.iterator();
		while(childrenIterator.hasNext())
		{
			Element child = (Element) childrenIterator.next(); 
			String name= child.getName();
			if(name.equals("street"))
			{
				street = child.getText();
			}
			if(name.equals("suburb"))
			{
				suburb = child.getText();
			}
			if(name.equals("postcode"))
			{
				postalcode = child.getText();
			}
			if(name.equals("country"))
			{
				country = child.getText();
			}
		}
		QueXMLAddress address = new QueXMLAddress(street, suburb, postalcode, country);
		return address;
	}
}
