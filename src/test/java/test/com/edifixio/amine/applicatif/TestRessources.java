package test.com.edifixio.amine.applicatif;

public class TestRessources {
	/****************************************** global ****************************/
	public static final String RESOURCE_FLODER="src/resource/";
	public static final String PACKAGE="com/edifixio/amine/";
	public static final String RESOURCE="resource/";
	public static final String SPRING_CONFIG="spring/config/";
	public static final String JSON_QUERY="json/query/";
	public static final String JSON_RESPONSE="json/response/";
	public static final String JSON_TEST="json/test/";
	/********************************** composed var*****************************/
	public static final String JSON_QUERIES=RESOURCE_FLODER+PACKAGE+RESOURCE+JSON_QUERY;
	public static final String SPRING_CONFIGS=RESOURCE_FLODER+PACKAGE+RESOURCE+SPRING_CONFIG;
	public static final String JSON_RESPONSES=RESOURCE_FLODER+PACKAGE+RESOURCE+JSON_RESPONSE;
	public static final String JSON_TEST_RESOURCE=RESOURCE_FLODER+PACKAGE+RESOURCE+JSON_TEST;
	public static void main(String args[]){
		System.out.println(SPRING_CONFIGS);
	}

}
