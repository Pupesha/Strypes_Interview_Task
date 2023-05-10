package eu.strypes.helpers;

public class UserHelper {

    public static String nameToEmail(String string, boolean makeUnique) {
        if(makeUnique) {
            int unique = MathHelper.randomInt(10000, 99999);
            string+= Integer.toString(unique);
        }
        string+= "@test.com";

        return string;
    }

    public static String emailToName(String string) {
        int nameEnd = string.indexOf('@');
        string = string.substring(0, nameEnd);

        return string;
    }

    public static String newPassword(int minLength, int maxLength) {
        String charSet = "0123456789!@#$%^&*()abcdefghijklmnopgrstuvwxyz!@#$%^&*()ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String password = "";

        int passLength = MathHelper.randomInt(minLength, maxLength);
        for(int i = 0; i < passLength; i++) {
            int charIndex = MathHelper.randomInt(0, charSet.length()-1);
            password+=charSet.charAt(charIndex);
        }

        return password;
    }
}