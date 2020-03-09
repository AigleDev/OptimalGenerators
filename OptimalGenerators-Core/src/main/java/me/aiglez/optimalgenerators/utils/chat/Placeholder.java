package me.aiglez.optimalgenerators.utils.chat;

public class Placeholder {

    private String string;
    private Object replacement;

    public Placeholder(String string, Object replacement) {
        this.string = string;
        this.replacement = replacement;
    }

    public String getString() {
        return string;
    }

    public Object getReplacement() {
        return replacement;
    }

    public String replace(String message) {
        if(message.contains(string)) message = message.replace(string, replacement.toString());
        return message;
    }
}
