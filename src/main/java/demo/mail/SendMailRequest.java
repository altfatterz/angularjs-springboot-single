package demo.mail;

public class SendMailRequest {

    private final String to;
    private final String subject;
    private final String text;

    public SendMailRequest(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public static class Builder {
        private String to;
        private String subject;
        private String text;

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public SendMailRequest build() {
            return new SendMailRequest(to, subject, text);
        }
    }


}
