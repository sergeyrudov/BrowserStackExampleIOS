package tests;

import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import screens.CartScreen;
import screens.LoginScreen;
import screens.ProductsScreen;
import screens.ScreenFactory;
import tests.base.BaseAppTest;

import static org.assertj.core.api.Assertions.assertThat;

public class FirstTests extends BaseAppTest {

    @Test
    void checkThatUserCanBuyFewProducts()  {
        Faker faker = new Faker();
        // open login screen, and wait for some elements
        LoginScreen login = ScreenFactory.getScreenByClass(driver, LoginScreen.class);
        login.screenComponentSoftAssert();

        // sign in as STANDARD_USER
        login.signInAs(LoginScreen.User.STANDARD);

        // check that products screen is opened
        ProductsScreen products = ScreenFactory.getScreenByClass(driver, ProductsScreen.class);
        products.screenComponentSoftAssert();

        // add all products to card which are available in viewpoint
        products.putAllViewableProductsToCart();

        // check that at least 2 products are in cart
        SoftAssertions.assertSoftly(sa ->
            sa.assertThat(products.getListOfProductsInCart() >= 2).isTrue()
        );

        // open cart
        products.tapOnCart();

        // switch to product checkout and scroll down
        products.scrollDown();

        // tap on checkout button
        products.tapOnCheckout();

        // init cart screen
        CartScreen cart = ScreenFactory.getScreenByClass(driver, CartScreen.class);
        cart.screenComponentSoftAssert();

        // fill some fields on checkout/cart screen
        cart.fillInCheckoutInfo(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().zipCode()
        );

        // scroll down, and tap finish button
        cart.scrollDown();
        cart.tapFinishButton();

        // check that header and message on screen reflects that order is placed
        assertThat(cart.getOrderSuccessHeader()).isEqualTo("CHECKOUT: COMPLETE!");
        assertThat(cart.getOrderSuccessMessage()).isEqualTo("THANK YOU FOR YOU ORDER");

    }
}
