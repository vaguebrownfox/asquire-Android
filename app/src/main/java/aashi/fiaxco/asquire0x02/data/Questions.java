package aashi.fiaxco.asquire0x02.data;

import java.util.HashMap;

public class Questions {

    public static HashMap<String, Question> questionHashMap = new HashMap<>();

    public static Question question1  = new Question(
            "What is your smoking status?",
            new String[] {"Ex-smoker(1 year)", "Current smoker", "Non-smoker"});

    public static Question question2  = new Question(
            "Do you have repeated episodes of cough?",
            new String[] {"Yes", "No"});


    public static Question question3  = new Question(
            "How many times in a year cough-episode occurs?",
            new String[] {"1", "2", "3", ">3"});


    public static Question question4  = new Question(
            "How long does each episode last?",
            new String[] {"<3", ">3"});


    public static Question question5  = new Question(
            "Are you a known case of Asthma?",
            new String[] {"Yes", "No"});


    public static Question question6  = new Question(
            "Have you suffered from lung TB in the past?",
            new String[] {"Yes", "No"});


    public static Question question7  = new Question(
            "Do you have any other respiratory illness?",
            new String[] {"Yes", "No"});


    public static Question question8  = new Question(
            "Write in brief About it",
            new String[] {});


    public static Question question9  = new Question(
            "Are you a known case of high blood pressure?",
            new String[] {"Yes", "No"});


    public static Question question10 = new Question(
            "Your high blood pressure is",
            new String[] {"Well controlled", "Nearly controlled"});


    public static Question question11 = new Question(
            "Area you a known case of diabetes?",
            new String[] {"Yes", "No"});


    public static Question question12 = new Question(
            "Your diabetes is",
            new String[] {"Well controlled", "Nearly controlled"});


    public static Question question13 = new Question(
            "Are you a known case of heart disease?",
            new String[] {"Yes", "No"});


    public static Question question14 = new Question(
            "Any other health problems?",
            new String[] {"Yes", "No"});


    public static Question question15 = new Question(
            "What health problems?",
            new String[] {});


    public static Question question16 = new Question(
            "Wheeze and chest tightness present?",
            new String[] {"Yes", "No"});


    public static Question question17 = new Question(
            "For how long wheeze is present?",
            new String[] {});


    public static Question question18 = new Question(
            "Do you experience episodic or continuous wheeze?",
            new String[] {"Episodic", "Continuous", "Can't say"});


    public static Question question19 = new Question(
            "Does your wheeze vary with seasons?",
            new String[] {"Yes", "No"});


    public static Question question20 = new Question(
            "Does your wheeze vary across the day?",
            new String[] {"Yes", "No"});


    public static Question question21 = new Question(
            "Do you have cough?",
            new String[] {"Yes", "No"});


    public static Question question22 = new Question(
            "Do you have dry or wet cough?",
            new String[] {"Dry", "Wet"});


    public static Question question23 = new Question(
            "Sputum color",
            new String[] {"White", "Yellow", "Green"});


    public static Question question24 = new Question(
            "Are you under any medication for asthma?",
            new String[] {"Yes", "No"});


    public static Question question25 = new Question(
            "Do you use inhalers or nebulizers?",
            new String[] {"Inhalers", "Nebulizers", "Both", "Don't use anything"});


    public static Question question26 = new Question(
            "Do you have family history of asthma?",
            new String[] {"Yes", "No"});


    public static Question question27 = new Question(
            "Do you have allergies?",
            new String[] {"Yes", "No"});


    public static Question question28 = new Question(
            "Triggers for allergies?",
            new String[] {"No Triggers", "Pollution", "Strong smells", "Pollen", "Other"});


    public static HashMap<String, Question> getQuestionHashMap() {
        questionHashMap.put("1", question1);
        questionHashMap.put("2", question2);
        questionHashMap.put("3", question3);
        questionHashMap.put("4", question4);
        questionHashMap.put("5", question5);
        questionHashMap.put("6", question6);
        questionHashMap.put("7", question7);
        questionHashMap.put("8", question8);
        questionHashMap.put("9", question9);
        questionHashMap.put("10", question10);
        questionHashMap.put("11", question11);
        questionHashMap.put("12", question12);
        questionHashMap.put("13", question13);
        questionHashMap.put("14", question14);
        questionHashMap.put("15", question15);
        questionHashMap.put("16", question16);
        questionHashMap.put("17", question17);
        questionHashMap.put("18", question18);
        questionHashMap.put("19", question19);
        questionHashMap.put("20", question20);
        questionHashMap.put("21", question21);
        questionHashMap.put("22", question22);
        questionHashMap.put("23", question23);
        questionHashMap.put("24", question24);
        questionHashMap.put("25", question25);
        questionHashMap.put("26", question26);
        questionHashMap.put("27", question27);
        questionHashMap.put("28", question28);

        return questionHashMap;
    }





}
