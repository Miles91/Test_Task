import com.codeborne.selenide.Selenide;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.jetty7.util.log.Log;
import java.util.Properties;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

/**
 * Created by mikhaill on 5/6/2016.
 */
public class TestTask2 {

    static Properties properties;
    static Properties pathes;

    @BeforeClass
    public static void init(){
        System.out.println("Properties initialized");
        properties = ReadProperties.getProperties();
        pathes = ReadPath.getUIMapping();
        WebDriver driver;
    }


    @Test
    public void loginUser() throws InterruptedException {
        try {
            Selenide.open (properties.getProperty("MAIL_URL1"));
            Log.info ("Main page opened");
           if  ($(pathes.getProperty("Gmail_change_acc")).exists())
           {
               $(pathes.getProperty("Gmail_change_acc")).click();
               $(pathes.getProperty("Gmail_add_acc")).click();
               $(pathes.getProperty("Gmail_email")).sendKeys(properties.getProperty("MAIL1"));
           }
           else {
               $(pathes.getProperty("Gmail_email")).sendKeys(properties.getProperty("MAIL1"));
           }
            $(pathes.getProperty("Gmail_next")).click();
            $(pathes.getProperty("Gmail_pass")).sendKeys(properties.getProperty("PASSWORD"));
            $(pathes.getProperty("Gmail_signin")).click();
            $(pathes.getProperty("Gmail_compose_message")).click();
            $(pathes.getProperty("Gmail_adresser")).sendKeys(properties.getProperty("MAIL2"));
            $(pathes.getProperty("Gmail_subject")).sendKeys(properties.getProperty("TEST_SUBJ"));
            $(pathes.getProperty("Gmail_msg_text_area")).sendKeys(properties.getProperty("TETS_MSG"));
            getFocusedElement().sendKeys(Keys.TAB);
            getFocusedElement().sendKeys(Keys.ENTER);
            sleep(300);


            //Open new Tab and login with second user
            $("body").sendKeys(Keys.CONTROL + "t");
            Selenide.open(properties.getProperty("MAIL_URL2"));
            $(pathes.getProperty("Ya_login")).sendKeys(properties.getProperty("MAIL2"));
            $(pathes.getProperty("Ya_pass")).sendKeys(properties.getProperty("PASSWORD"));
            $(pathes.getProperty("Ya_signin_button")).click();

            //Next cycle is for waiting for email from user1
            for (int count = 0;count < 5000; count ++) {
                if ($(pathes.getProperty("Ya_message_header")).exists()) {
                    sleep(300);
                    $(pathes.getProperty("Ya_message_header")).click();
                    break;                }
                else {
                    sleep(2000);
                    refresh();
                }
            }

            $(pathes.getProperty("Ya_reply_link")).click();
            $(pathes.getProperty("Ya_text_area")).sendKeys(properties.getProperty("TEST_REPLY"));
            $(pathes.getProperty("Ya_send_button")).click();
            sleep(500);
            $(pathes.getProperty("Ya_inbox")).click();
            $(pathes.getProperty("Ya_message_checkbox")).click();
            $(pathes.getProperty("Ya_delete_message")).click();

            //go back to gmail
            $("body").sendKeys(Keys.CONTROL + "1");

            //Next cycle is for waiting reply email from user2
            for (int count = 0;count < 5000; count ++) {
                if ($(By.xpath(pathes.getProperty("Gmail_inbox_msg_title"))).exists()) {
                    $(By.xpath(pathes.getProperty("Gmail_inbox_msg_title"))).click();
                    break;
                } else {
                    sleep(2000);
                    refresh();
                }
            }
            $(pathes.getProperty("Gmail_inbox_msg_body")).click();
            sleep(100);
            $(pathes.getProperty("Gmail_msg_text_area")).sendKeys(properties.getProperty("TEST_RE_REPLY"));
            sleep(100);
            $(pathes.getProperty("Gmail_msg_text_area")).sendKeys(Keys.TAB);
            getFocusedElement().sendKeys(Keys.ENTER);

            //Go to 2nd mail to check that reply from user 1 will be delivered
            $(By.cssSelector("body")).sendKeys(Keys.CONTROL + "2");

            for (int count = 0;count < 5000; count ++) {
                if ($(pathes.getProperty("Ya_message_header")).exists()) {
                    sleep(300);
                    $(pathes.getProperty("Ya_message_header")).click();
                    break;                }
                else {
                    sleep(2000);
                    refresh();
                }
            }
          $(pathes.getProperty("Ya_message_body")).shouldHave(text(properties.getProperty("TEST_RE_REPLY"))).exists();
            $(pathes.getProperty("Ya_inbox")).click();
            $(pathes.getProperty("Ya_message_checkbox")).click();
            $(pathes.getProperty("Ya_delete_message")).click();

        }
        catch (NoSuchElementException ex) {
            System.out.println("Test is failed " + ex);

        }
    }
}
