package constant;

import java.util.Arrays;
import java.util.List;

public interface IConstantSecondPart {
    public static final String QWG = "QWG";
    public static final String AWG = "AWG";
    public static final String OCWG = "OCWG";
    public static final String SPWG = "SPWG";
    public static final String Security = "Security";
    public static final String Configuration = "Configuration";
    public static final String BestPractice = "BestPractice";
    public static final List<String> listOfCVE = Arrays.asList(QWG, AWG, OCWG, SPWG);
    public static final List<String> ticketsTypes = Arrays.asList(Security, Configuration, BestPractice);
}
