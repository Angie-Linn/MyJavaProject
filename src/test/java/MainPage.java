import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    private WebDriver driver;
    @FindBy(id = "home-btn")
    private WebElement homeButton;

    @FindBy(id = "trash-btn")
    private WebElement trashButton;

    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div/div")
    private WebElement newNote;

    @FindBy(xpath = "/html/body/div[3]/div/div/div[3]/button[2]")
    private WebElement exitButton;

    @FindBy(id = "logout-btn")
    private WebElement logoutButton;

    @FindBy(id = "note-modal-save-btn-new_empty")
    private WebElement saveNote;

    @FindBy(id = "note-modal-title-new_empty")
    private WebElement noteTitle;


    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickHomeButton() {
        homeButton.click();
    }

    public void clickTrashButton() {
        trashButton.click();
    }

    public void createNote() {
        newNote.click();
    }

    public void logout() {
        logoutButton.click();
    }

    public void setSaveNote() {
        saveNote.click();
    }

    public void setNoteTitle() {
        noteTitle.sendKeys("хоть бы получилось");
    }

    public void setExitButton () {
        exitButton.click();
    }

    public Boolean logoutButtonIsDisplayed() {
        if (logoutButton.isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }
}




