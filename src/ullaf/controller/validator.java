package ullaf.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN="((?=.a*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";
    public validator(){pattern=Pattern.compile(PASSWORD_PATTERN);}
    public boolean validate(final String password){
        matcher=pattern.matcher(password);
        return matcher.matches();
    }

}
