
import page_object.*;
import org.testng.annotations.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TheInternetAppTestsTwo extends BaseTest {

    @Test
    public void can_go_to_home_page() {
        HomePage homePage = new HomePage(driver.get());
        assertThat(homePage.isAt(), is(true));
    }

    @Test
    public void should_login_to_secure_area_with_valid_credentials() {
        HomePage homePage = new HomePage(driver.get());
        FormAuthenticationPage formAuthenticationPage = new FormAuthenticationPage(driver.get());

        homePage.clickFormAuthenticationLink();
        formAuthenticationPage.enterUsername("tomsmith");
        formAuthenticationPage.enterPassword("SuperSecretPassword!");
        formAuthenticationPage.clickSubmit();

        assertThat(formAuthenticationPage.isSuccessNotificationDisplayed(), is(true));
    }

    @Test
    public void should_login_to_secure_area_with_invalid_credentials() {
        HomePage homePage = new HomePage(driver.get());
        FormAuthenticationPage formAuthenticationPage = new FormAuthenticationPage(driver.get());

        homePage.clickFormAuthenticationLink();
        formAuthenticationPage.enterUsername("nublugameboytetris");
        formAuthenticationPage.enterPassword("oksana");
        formAuthenticationPage.clickSubmit();

        assertThat(formAuthenticationPage.isErrorNotificationDisplayed(), is(true));
    }

    @Test
    public void should_display_username_invalid_message_when_username_is_invalid() {
        HomePage homePage = new HomePage(driver.get());
        FormAuthenticationPage formAuthPage = new FormAuthenticationPage(driver.get());

        homePage.clickFormAuthenticationLink();
        formAuthPage.enterUsername("hello");
        formAuthPage.clickSubmit();

        assertThat(formAuthPage.isErrorNotificationText("Your username is invalid!"), is(true));
    }


    @Test
    public void should_display_successfully_logged_out_message() {
        HomePage homePage = new HomePage(driver.get());
        FormAuthenticationPage formAuthPage = new FormAuthenticationPage(driver.get());

        homePage.clickFormAuthenticationLink();
        formAuthPage.enterUsername("tomsmith");
        formAuthPage.enterPassword("SuperSecretPassword!");
        formAuthPage.clickSubmit();
        formAuthPage.clickLogout();

        assertThat(formAuthPage.isSuccessNotificationText("You logged out of the secure area!"), is(true));
    }


    @Test
    public void should_see_hello_world_after_loading() {
        HomePage homePage = new HomePage(driver.get());
        DynamicLoadingPage dynamicLoadingPage = new DynamicLoadingPage(driver.get());

        homePage.clickDynamicLoadingLink();
        dynamicLoadingPage.clickExampleOne();
        dynamicLoadingPage.clickStart();

        assertThat(dynamicLoadingPage.isHelloWorldVisible(), is(true));
    }

    @Test
    public void should_select_option_one() {
        HomePage homePage = new HomePage(driver.get());
        DropdownPage dropdownPage = new DropdownPage(driver.get());

        homePage.clickDropdownLink();
        dropdownPage.selectOptionOne();

        assertThat(dropdownPage.isOptionOneSelected(), is(true));
    }

    @Test
    public void should_see_view_profile_link_on_hover() {
        HomePage homePage = new HomePage(driver.get());
        HoversPage hoversPage = new HoversPage(driver.get());

        homePage.clickHoverLink();
        hoversPage.hoverOverFirstProfile();

        assertThat(hoversPage.isViewProfileLinkVisible(), is(true));
    }

    @Test
    public void should_change_location_of_blocks_after_drag_and_drop_to_BA() {
        HomePage homePage = new HomePage(driver.get());
        DragDropPage dragAndDropPage = new DragDropPage(driver.get());

        homePage.clickDragDropLink();
        dragAndDropPage.dragAndDrop(dragAndDropPage.getColumnA(), dragAndDropPage.getColumnB());

        assertThat(dragAndDropPage.isTheFirstElementsHeaderText("B"), is(true));
    }

    @Test
    public void should_not_change_location_of_blocks_when_drag_and_drop_on_footer() {
        HomePage homePage = new HomePage(driver.get());
        DragDropPage dragAndDropPage = new DragDropPage(driver.get());

        homePage.clickDragDropLink();
        dragAndDropPage.dragAndDrop(dragAndDropPage.getColumnA(), dragAndDropPage.getOtherElement());

        assertThat(dragAndDropPage.isTheFirstElementsHeaderText("A"), is(true));
    }
}
