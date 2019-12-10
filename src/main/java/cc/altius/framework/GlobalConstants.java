/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.framework;

/**
 *
 *
 * This class has definition of all the constants used across the project. To
 * access these variables from jsp file use following code
 *
 *
 * <jsp:useBean id="GlobalConstants" class="cc.altius.domain.GlobalConstants"
 * scope="application"/> (should be kept in taglibs.jsp)
 *
 *
 * ${GlobalConstants.ORDER_STATUS_NETWORK_COMPLETED} (EL statement to access
 * variables)
 *
 *
 *
 *
 * @author shrutika
 */
public class GlobalConstants {

    // General values
    public static String majorVersion;
    public static String minorVersion;
    /**
     * sets the value for array of ALLOWED_IP_RANGE={"127.0.0.1",
     * "10.1.2.1-10.1.2.254","10.1.3.1-10.1.3.254"}
     *
     */
    public static String[] ALLOWED_IP_RANGE = new String[]{"127.0.0.1", "10.1.0.1-10.1.0.254"};
}
