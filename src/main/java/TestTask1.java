import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.seleniumhq.jetty7.util.log.Log;
import java.util.Properties;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * Created by mikhaill on 5/6/2016.
 */
public class TestTask1 {

        static Properties properties;
        static Properties pathes;

    @BeforeClass
        public static void init(){
            System.out.println("Properties initialized");
            properties = ReadProperties.getProperties();
            pathes = ReadPath.getUIMapping();
        }


        @Test
        public void loginUser() throws InterruptedException {
          try {
              Selenide.open (properties.getProperty("SITE_URL"));
              Log.info ("Main page opened");
              $(pathes.getProperty("Header_search_input")).click();
              $(pathes.getProperty("Header_search_input")).sendKeys(properties.getProperty("BRAND_NAME"));
              sleep(500);
              Log.info("Search data entered");
              $(pathes.getProperty("Header_search_button")).click();
              Log.info("Search data submitted");
              $(By.partialLinkText("Мобильные телефоны")).click();
              Log.info("Mobile phones group selected");
              
              for (int count = 0;count < 100; count ++) {
              if ($(By.partialLinkText(properties.getProperty("TARGET"))).exists())

                 {
                     $(By.partialLinkText(properties.getProperty("TARGET"))).waitUntil(visible, 500).click();
                  Log.info("Nokia Lumia 520 White foud and selected");}

              else { if ($(pathes.getProperty("Next_page")).exists())
                  {SelenideElement nextPage = $(pathes.getProperty("Next_page"));
                  nextPage.click();}
                  else
                System.out.println("Target good is not exists");
                  Log.info("Nokia Lumia 520 White not found. Proceed to the next page");
              }
                  }
             }
           catch (NoSuchElementException ex) {
               System.out.println("Test is failed " + ex);
           }
        }
}
