package Question1;

/*
 * 形如“12321”、“789987”的数字称为“回文”，请写一个函数来判断输入的数字是否为回文
 * The input type is integer or string?
 * if the input is integer, could a negative integer be a palindrome? e.g. -101
 */
public class Solution {
    
    /*
     * This method check if the input string as a literal number is a palindrome
     */
    public boolean isPalindrome(String num) {
        if (null == num)
            return false;
        if (0 == num.length())
            return false;
        char[] digits = num.toCharArray();
        int i = 0, j = digits.length - 1;
        while (i <= j) {
            if (digits[i] != digits[j])
                return false;
        }
        
        return true;
    }
    
    /*
     * check if an integer is a palindrome, in this case, negative integer is
     * not palindrome
     */
    public boolean isPalindrome(int num) {
        String number = Integer.toString(num);
        return isPalindrome(number);
    }
}
