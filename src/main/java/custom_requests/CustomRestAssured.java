package custom_requests;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CustomRestAssured {

    private CustomRestAssured() {
    }

    public static RequestSpecification givenSoap(final String url, final String action) {
        return given()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .contentType("text/xml; charset=UTF-8;")
                .header(new Header("SOAPAction", url + "/" + action))
                .header(new Header("Connection", "Keep-Alive"))
                .baseUri(url);

    }

    public static RequestSpecification given() {
        return RestAssured.given()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());
    }
}
