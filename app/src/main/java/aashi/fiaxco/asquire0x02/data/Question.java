package aashi.fiaxco.asquire0x02.data;

public class Question {

    private final String question;
    private final String[] options;

    private String answer;
    private boolean answered = false;

    public Question(String question, String[] options ) {
        this.question = question;
        this.options = options;
    }

    public String getQuestion() {
        return this.question;
    }

    public String[] getOptions() {
        return this.options;
    }

    public boolean isAnswered() {
        return this.answered;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        this.answered = true;
    }

}
